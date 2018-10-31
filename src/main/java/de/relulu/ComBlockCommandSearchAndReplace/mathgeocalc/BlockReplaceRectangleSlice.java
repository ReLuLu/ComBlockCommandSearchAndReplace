package de.relulu.ComBlockCommandSearchAndReplace.mathgeocalc;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.CommandBlock;
import org.bukkit.scheduler.BukkitRunnable;

import de.relulu.ComBlockCommandSearchAndReplace.helpers.BlockReplaceCommandHandler;

public class BlockReplaceRectangleSlice extends BukkitRunnable {

	private BlockReplaceCommandHandler blrch;
	private Location vec;
	private int xref;
	private int yref;
	private int zref;
	
	/**
	 * Konstruktor mit zwei Kantenlängen
	 * @param y
	 * @param z
	 */
	public BlockReplaceRectangleSlice(BlockReplaceCommandHandler blrch, Location vec, int xref, int yref, int zref) {
		this.blrch = blrch;
		this.vec = vec;
		this.xref = xref;
		this.yref = yref;
		this.zref = zref;
	}
	
	@Override
	public void run() {
		for(int y = yref; y <= (yref + vec.getBlockY()); y++) { // arbeitet schichtweise
			for(int z = zref; z <= (zref + vec.getBlockZ()); z++) { // und geht von links nach rechts
				Location l = new Location(vec.getWorld(), xref, y, z);
				Block bl = l.getBlock();
				BlockState bst = bl.getState();
				if(bst instanceof CommandBlock) {
					CommandBlock cbl = (CommandBlock)bst;
					cbl.setCommand(blrch.doCommandModification(cbl.getCommand(), blrch.getCommandArgs()));
					cbl.update(true);
					bst.update(true); // falls Updaten des CommandBlock fehlschlägt, update den state auch mal mit
				}
				bst = null; // in der Hoffnung dass der GarbageCollector die Objekte asap wegräumt
				bl = null;
				l = null; 
			}
		}
		cancel();
	}

}
