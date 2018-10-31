package de.relulu.ComBlockCommandSearchAndReplace.commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.relulu.ComBlockCommandSearchAndReplace.BlockRelationManager;
import de.relulu.ComBlockCommandSearchAndReplace.helpers.MessageHandler;
import de.relulu.ComBlockCommandSearchAndReplace.mathgeocalc.BlockPositionManager;

/**
 * Diese Klasse handhabt den /blockpos1 Befehl
 * 
 * @author ReLuLu
 *
 */
public class BlockCalculate implements CommandExecutor {
	
	private BlockPositionManager bpman = BlockPositionManager.getInstance();
	private BlockRelationManager brman;
	private MessageHandler mh;
	
	/**
	 * Erzeugt die Befehlsklasse mit Instanz der Managerklasse
	 * 
	 * @param brman BlockRelationManager Objekt
	 */
	public BlockCalculate(BlockRelationManager brman) {
		this.brman = brman;
		this.mh = brman.getMesHand();
	}

	/**
	 * Handelt den blockpos1 Befehl ab
	 */
	@Override
	public boolean onCommand(CommandSender sender, Command command, String comname, String[] comparams) {
		
		if(sender instanceof Player) {
			
			Player p = (Player)sender;
			
			// debug
			if(brman.getConfigHelper().isDebug()) {
				mh.tell(p, "comparams.length: §r" + comparams.length);
				for(int i = 0; i < comparams.length; i++) {
					mh.tell(p, "comparams[§r" + i + mh.getPrimaryColor() + "]: §r" + comparams[i]);
				}
			}

			// wenn der Befehl ohne Parameter daherkommt
			if(comparams.length < 1) {
				
				boolean pos1exists = bpman.hasValidBlockPosition(p.getUniqueId(), true);
				boolean pos2exists = bpman.hasValidBlockPosition(p.getUniqueId(), false);
				
				// nur wenn beide Positionen gesetzt sind
				if(pos1exists && pos2exists) {
					Location vector;
					try {

						vector = bpman.calculate(p);
						mh.tell(p, "Der Vektor zum Zielblock ist { §r~" + vector.getBlockX() 
								+ mh.getPrimaryColor() + " / §r~" + vector.getBlockY()
								+ mh.getPrimaryColor() + " / §r~" + vector.getBlockZ() 
								+ mh.getPrimaryColor() + " }.");
						
					} catch(NullPointerException e) {
						mh.tell(p, "Fehler beim Berechnen des Vektors!");
						if(brman.getConfigHelper().isDebug()) {
							brman.getLogger().warning(e.getMessage());
						}
					}
				} 
				// wenn nicht beide Positionen gesetzt sind
				else {
					if(!pos1exists) {
						mh.tell(p, "Position 1 nicht gesetzt!");
					}
					if(!pos2exists) {
						mh.tell(p, "Position 2 nicht gesetzt!");
					}
				}
				return true;

			} else {
				mh.tell(p, "Dieser Befehl darf keinen Parameter haben.");
			}
		} else {
			mh.tell(sender, "Nur ein Spieler kann diesen Befehl nutzen!");
			return true;
		}
		
		return false;
	}
}