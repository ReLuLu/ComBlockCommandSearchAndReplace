package de.relulu.ComBlockCommandSearchAndReplace.mathgeocalc;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class BlockPositionManager {
	
	private static BlockPositionManager bposman;
	
	private HashMap<UUID, Location> blockpos1 = new HashMap<UUID, Location>();
	private HashMap<UUID, Location> blockpos2 = new HashMap<UUID, Location>();
	
	/**
	 * Setzt den [ Spieler | BlockPosition ] Datensatz.
	 * 
	 * @param uuid UUID des ausführenden Spielers
	 * @param loc
	 * @param pos1 der boolean, der zwischen Position 1 und 2 unterscheidet (true 1, false 2)
	 * @return
	 */
	public boolean setBlockPosition(UUID uuid, Location loc, boolean pos1) {
		
		// speichere den vorherigen Wert falls vorhanden ab (sollte nicht existieren)
		Location loctemp;
		//wenn pos1
		if(pos1) {
			loctemp = blockpos1.put(uuid, loc);
		//sonst pos2
		} else {
			loctemp = blockpos2.put(uuid, loc);
		}
		// wenn vorher kein Datensatz vorhanden war
		if(loctemp == null) {
			return true;
		} else return false;
	}
	
	/**
	 * Holt einen [ Spieler | BlockPosition ] Datensatz.
	 * 
	 * @param uuid UUID des ausführenden Spielers
	 * @param pos1 der boolean, der zwischen Position 1 und 2 unterscheidet (true 1, false 2)
	 * @return
	 */
	public Location getBlockPosition(UUID uuid, boolean pos1) {
		if(pos1) {
			if(hasValidBlockPosition(uuid, true)) {
				return blockpos1.get(uuid);
			}
		} else {
			if(hasValidBlockPosition(uuid, false)) {
				return blockpos2.get(uuid);
			}
		}
		return null;
	}
	
	/**
	 * Löscht den [ Spieler | BlockPosition ] Datensatz.
	 * 
	 * @param uuid UUID des ausführenden Spielers
	 * @param pos1 der boolean, der zwischen Position 1 und 2 unterscheidet (true 1, false 2)
	 * @return
	 */
	private boolean removeBlockPosition(UUID uuid, boolean pos1) {
		
		// speichert beim Löschen den bisher gespeicherten Datensatz zwischen
		Location temploc;
		// wenn pos1
		if(pos1) {
			temploc = blockpos1.remove(uuid);
		// sonst pos2
		} else {
			temploc = blockpos2.remove(uuid);
		}
		// wenn ein Datensatz vorhanden war
		if(temploc != null) {
			return true;
		} else return false;
	}
	
	/**
	 * Setzt den [ Spieler | BlockPosition2 ] Datensatz.
	 * @deprecated
	 * 
	 * @param uuid UUID des ausführenden Spielers
	 * @param loc1
	 * @return
	 */
	@SuppressWarnings("unused")
	private boolean setBlockPositionLegacy(UUID uuid, Location loc1) {
		// speichere den vorherigen Wert falls vorhanden ab (sollte nicht existieren)
		Location loctemp = blockpos2.put(uuid, loc1);
		if(loctemp == null) {
			return true;
		} else return false;
	}
	
	/**
	 * Löscht den [ Spieler | BlockPosition2 ] Datensatz.
	 * @deprecated
	 * 
	 * @param uuid UUID des ausführenden Spielers
	 * @return
	 */
	@SuppressWarnings("unused")
	private boolean removeBlockPositionLegacy(UUID uuid) {
		if(blockpos2.containsKey(uuid)) {
			blockpos2.remove(uuid);
			return true;
		} else return false;
	}
	
	/**
	 * Checkt, ob zu einem Spieler das Mapping der BlockPosition valide ist.
	 * 
	 * @param uuid UUID des ausführenden Spielers
	 * @param pos1 der boolean, der zwischen Position 1 und 2 unterscheidet (true 1, false 2)
	 * @return
	 */
	public boolean hasValidBlockPosition(UUID uuid, boolean pos1) {
		if(pos1) {
			if(blockpos1.containsKey(uuid)) {
				if(blockpos1.get(uuid) != null) {
					return true;
				}
			}
		} else {
			if(blockpos2.containsKey(uuid)) {
				if(blockpos2.get(uuid) != null) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Prüft, ob beide Blockpositionen gesetzt sind.
	 * 
	 * @param uuid UUID des ausführenden Spielers
	 * @return
	 */
	public boolean hasAllBlockPositions(UUID uuid) {
		// potenziell fehleranfällig wenn ein Mapping zu einer Key null ist
		// return (blockpos1.containsKey(uuid) && blockpos2.containsKey(uuid));
		
		// jedes Mapping auf validen Inhalt überprüfen
		// nur wenn beide Positionen ein valides Mapping haben wird true zurückgegeben
		// mit true und false wird zwischen blockpos1 und blockpos2 unterschieden
		return (hasValidBlockPosition(uuid, true) && hasValidBlockPosition(uuid, false));
	}
	
	/**
	 * Löscht die Datensätze von Spieler und BlockPositionen
	 * 
	 * @param uuid UUID des ausführenden Spielers
	 * @return
	 */
	private boolean cleanBlockPositions(UUID uuid) {
		if(removeBlockPosition(uuid, true) && removeBlockPosition(uuid, false)) {
			return true;
		} else return false;
	}
	
	/**
	 * Erzeugt eine Location anhand von Koordinaten
	 * 
	 * @param w die Welt, in der sich der ausführende Spieler befindet
	 * @param x die X Koordinate für die Location
	 * @param y die Y Koordinate für die Location
	 * @param z die Z Koordinate für die Location
	 * @return
	 */
	public Location xyzToLocation(World w, double x, double y, double z) {
		
		Location loc = new Location(w, x,y,z);
		return loc;
	}
	
	/**
	 * Berechnet die Blockrelationen, gibt diese als Location zurück und vernichtet die Datensätze.
	 * Berechnungsweise: Strecke AB = B-A
	 * Beispiel: A{1 4 2}, B{5 7 8} | {5 7 8} - {1 4 2} | {(5-1) (7-4) (8-2)} | = {4 3 6}
	 * 
	 * @param p Player-Objekt des ausführenden Spielers
	 * @return
	 */
	public Location calculate(Player p) {
		UUID uuid = p.getUniqueId();
		Location loc1 = getBlockPosition(uuid, true); // A
		Location loc2 = getBlockPosition(uuid, false); // B
		
		Location vector = loc1; // um null zu vermeiden
		
		// doch lieber mit try-catch
		try {
			// Strecke AB -> B-A
			vector = loc2.subtract(loc1);
			cleanBlockPositions(uuid);
			return vector;
			
		} catch(NullPointerException e) {
			
		}
		return vector;
	}
	
	/**
	 * Gibt das Singleton zurück.
	 * @return
	 */
	public static BlockPositionManager getInstance() {
		if(bposman == null) {
			bposman = new BlockPositionManager();
		}
		return bposman;
	}
}
