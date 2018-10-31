package de.relulu.ComBlockCommandSearchAndReplace.mathgeocalc;

import org.bukkit.Location;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import de.relulu.ComBlockCommandSearchAndReplace.helpers.BlockReplaceCommandHandler;

public class BlockReplaceProcessWorker extends BukkitRunnable {

	private BlockReplaceCommandHandler blrch;
	private Location ref;
	private Location vec;
	private Plugin plugin;
	
	/**
	 * Konstruktor
	 * @param ref
	 * @param vec
	 */
	public BlockReplaceProcessWorker(BlockReplaceCommandHandler blrch, Location reference, Location vector, Plugin plugin) {
		this.blrch = blrch;
		this.ref = reference.clone();
		this.vec = vector.clone();
		this.plugin = plugin;
	}
	
	@Override
	public void run() {
		
		// in x-Richtung ausdehnen
		for(int x = ref.getBlockX(); x <= (ref.getBlockX() + vec.getBlockX()); x++) {
			BukkitTask brrs = new BlockReplaceRectangleSlice(blrch, vec.clone(), x, ref.getBlockY(), ref.getBlockZ()).runTask(plugin);
		}
		
	}

}
