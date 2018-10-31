package de.relulu.ComBlockCommandSearchAndReplace.commands;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.relulu.ComBlockCommandSearchAndReplace.BlockRelationManager;
import de.relulu.ComBlockCommandSearchAndReplace.helpers.MessageHandler;
import de.relulu.ComBlockCommandSearchAndReplace.mathgeocalc.BlockPositionManager;

/**
 * Diese Klasse handhabt den /blockpos2 Befehl
 * 
 * @author ReLuLu
 *
 */
public class BlockPosition2 implements CommandExecutor {
	
	private BlockPositionManager bpman = BlockPositionManager.getInstance();
	private BlockRelationManager brman;
	private MessageHandler mh;
	
	/**
	 * Erzeugt die Befehlsklasse mit Instanz der Managerklasse
	 * 
	 * @param brman BlockRelationManager Objekt
	 */
	public BlockPosition2(BlockRelationManager brman) {
		this.brman = brman;
		this.mh = brman.getMesHand();
	}

	/**
	 * Handelt den blockpos2 Befehl ab
	 */
	@Override
	public boolean onCommand(CommandSender sender, Command command, String comname, String[] comparams) {
		
		if(sender instanceof Player) {
			
			Player p = (Player)sender;
			Block bl = p.getTargetBlock(null, 10);
			Location loc;
			
			// debug
			if(brman.getConfigHelper().isDebug()) {
				mh.tell(p, "comparams.length: §r" + comparams.length);
				for(int i = 0; i < comparams.length; i++) {
					mh.tell(p, "comparams[§r" + i + mh.getPrimaryColor() + "]: §r" + comparams[i]);
				}
			}
			
			// wenn der Befehl ohne Parameter daherkommt
			if(comparams.length < 1) {
				loc = bl.getLocation();
				mh.tell(p, "Position 2 an §r" + loc.getX() 
						+ mh.getPrimaryColor() + " / §r" + loc.getY() 
						+ mh.getPrimaryColor() + " / §r" + loc.getZ() 
						+ mh.getPrimaryColor() + " gesetzt.");
				bpman.setBlockPosition(p.getUniqueId(), loc, false);
				return true;
				
			// wenn der Befehl mit 3 Parametern für eine Location daherkommt
			} else if(comparams.length == 3) {
				
				double givenX = 0.0;
				double givenY = 0.0;
				double givenZ = 0.0;
				
				// wegen Array und Typenkonvertierung lieber mit try catch
				try {
					givenX = Double.valueOf(comparams[0]);
					givenY = Double.valueOf(comparams[1]);
					givenZ = Double.valueOf(comparams[2]);
				} catch(ArrayIndexOutOfBoundsException e) {
					mh.tell(p, "Die Befehlsparameter wurden nicht erkannt!");
					mh.tell(p, "Syntax /blockpos2 | /blockpos2 <X> <Y> <Z>");
					if(brman.getConfigHelper().isDebug()) {
						brman.getLogger().warning(e.getMessage());
					}
					return true;
				} catch(Exception e) {
					mh.tell(p, "Die Koordinaten konnten nicht gelesen werden!");
					mh.tell(p, "Gültige Werte sind Ganzzahlen <0 8 15> oder <47 1 11>");
					if(brman.getConfigHelper().isDebug()) {
						brman.getLogger().warning(e.getMessage());
					}
					return true;
				}
				
				// Location durch übergebene Koordinaten setzen
				loc = bpman.xyzToLocation(p.getWorld(), givenX, givenY, givenZ);
				mh.tell(p, "Position 2 an §r" + givenX 
						+ mh.getPrimaryColor() + " / §r" + givenY 
						+ mh.getPrimaryColor() + " / §r" + givenZ 
						+ mh.getPrimaryColor() + " gesetzt.");
				bpman.setBlockPosition(p.getUniqueId(), loc, false);
				return true;
			}
		} else {
			mh.tell(sender, "Nur ein Spieler kann diesen Befehl nutzen!");
			return true;
		}
		
		return false;
	}
}