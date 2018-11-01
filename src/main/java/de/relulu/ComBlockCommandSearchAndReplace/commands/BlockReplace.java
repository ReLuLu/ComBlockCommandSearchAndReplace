package de.relulu.ComBlockCommandSearchAndReplace.commands;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import de.relulu.ComBlockCommandSearchAndReplace.BlockRelationManager;
import de.relulu.ComBlockCommandSearchAndReplace.helpers.BlockReplaceCommandHandler;
import de.relulu.ComBlockCommandSearchAndReplace.helpers.MessageHandler;
import de.relulu.ComBlockCommandSearchAndReplace.mathgeocalc.BlockPositionManager;
import de.relulu.ComBlockCommandSearchAndReplace.mathgeocalc.BlockReplaceProcessWorker;
import de.relulu.ComBlockCommandSearchAndReplace.mathgeocalc.CuboidCalculation;

/**
 * Handelt den Befehl zur Massenmodifikation von Befehlsblöcken
 */
public class BlockReplace implements CommandExecutor {

	BlockPositionManager bpman = BlockPositionManager.getInstance();
	BlockRelationManager brman;
	private MessageHandler mh;

	public BlockReplace(BlockRelationManager brman) {
		this.brman = brman;
		this.mh = brman.getMesHand();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if(sender instanceof Player) {

			Player p = (Player)sender;

			if(p.isOp()) {

				boolean pos1exists = bpman.hasValidBlockPosition(p.getUniqueId(), true);
				boolean pos2exists = bpman.hasValidBlockPosition(p.getUniqueId(), false);

				// nur wenn beide pos gesetzt sind
				if(pos1exists && pos2exists) {
					
					BlockReplaceCommandHandler blrch = new BlockReplaceCommandHandler(brman, mh, p, args);
					
					CuboidCalculation cc = new CuboidCalculation(p,
							bpman.getBlockPosition(p.getUniqueId(), true),
							bpman.getBlockPosition(p.getUniqueId(), false)
							);

					Location smallestCorner = cc.getSmallestCorner(); // Startpunkt
					Location positiveVector = cc.getSpaceDiagonalVectorAbs(); // Vektor zum Endpunkt

					// nur für debug
					if(brman.getConfigHelper().isDebug()) {
						Location[] corners = cc.getAllCorners();
						mh.tell(p, "Ecken validiert: ", String.valueOf(cc.allCornersValid()), "");
						mh.tell(p, "kleinste Ecke: ", smallestCorner.toString(), "");
						mh.tell(p, "positiver Raumvektor: ", positiveVector.toString(), "");
						for(Location l : corners) {
							l.getBlock().setType(Material.RED_CONCRETE);
						}
						smallestCorner.getBlock().setType(Material.GREEN_CONCRETE);
					}
					
					if(!blrch.hasAsteriskParameter() && !blrch.hasEvenAmountOfSearchAndReplaceTokens()) {
						mh.tell(p, "Nur gerade Anzahl an Parametern erlaubt: ", "<searchfor replacewith> <ABC CBA> <Hef Haf> <Sheep Sheehp>");
						return true;
					}

					BukkitTask bpw = new BlockReplaceProcessWorker(blrch, smallestCorner, positiveVector, brman.getPluginInstance()).runTask(brman.getPluginInstance());
					
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
			}
		}
		return true;
	}

}