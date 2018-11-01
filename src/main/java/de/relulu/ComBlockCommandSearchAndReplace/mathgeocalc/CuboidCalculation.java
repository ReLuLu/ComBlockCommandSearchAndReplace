package de.relulu.ComBlockCommandSearchAndReplace.mathgeocalc;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class CuboidCalculation {
	
	private Location p1;
	private Location p2;
	private Location p3;
	private Location p4;
	private Location p5;
	private Location p6;
	private Location p7;
	private Location p8;
	private Location pVec; // Raumdiagonale
	private Location pVecAbs; // Raumdiagonale vom kleinsten Punkt aus, so konzipiert dass sie nur in positive Richtungen läuft
	private Location pSmall; // damit wird der kleinste Punkt bestimmt
	private boolean cornersValid = false;

	/**
	 * Konstruktor für die Quaderberechnungslogik
	 * @param p
	 * @param pos1
	 * @param pos2
	 */
	public CuboidCalculation(Player p, Location pos1, Location pos2) {
		// Location direkt zuweisen geht nicht, weil es immer nur eine neue Referenz auf das selbe Location Objekt ist.
		//this.p1 = new Location(pos1.getWorld(), pos1.getX(), pos1.getY(), pos1.getZ()); // Referenzpunkt setzen
		//this.p8 = new Location(pos2.getWorld(), pos2.getX(), pos2.getY(), pos2.getZ()); // weil der letzte Punkt in der Zählung am anderen Ende der Raumdiagonale des Quaders ist
		
		this.p1 = pos1.clone();
		this.p8 = pos2.clone();
		
		this.pVec = determineSpaceDiagonalVector(pos1, pos2);
		this.pVecAbs = determineSpaceDiagonalVectorAbs(pVec);
		this.cornersValid = determineAllCorners();
		this.pSmall = determineSmallestCorner();
	}
	
	/**
	 * Bestimmt den Raumdiagonalvektor
	 * @return vec den Vektor als Location
	 */
	private Location determineSpaceDiagonalVector(Location pos1, Location pos8) {
		Location vec = null;
		// doch lieber mit try-catch
		try {
			// Strecke AB -> B-A
			vec = pos8.clone(); // um zu verhindern, dass die die Ausgangslocation im Speicher geändert wird, wird einer neuer durch Klonen erzeugt
			vec = vec.subtract(pos1);
		} catch(NullPointerException e) {
			
		}
		return vec;
	}
	
	/**
	 * Gibt den Betrag des Raumdiagonalvektors zurück
	 * @param vec den Vektor als Location mit positiven Koordinaten
	 * @return
	 */
	private Location determineSpaceDiagonalVectorAbs(Location vec) {
		return new Location(vec.getWorld(), Math.abs(vec.getX()), Math.abs(vec.getY()), Math.abs(vec.getZ()));
	}
	
	/**
	 * Gibt den positiven Raumdiagonalvektor zurück
	 * @return
	 */
	public Location getSpaceDiagonalVectorAbs() {
		return this.pVecAbs;
	}
	
	/**
	 * Bestimmt die verbleibenden 6 Ecken des Quaders
	 */
	private boolean determineAllCorners() {
		p2 = p1.clone().add(pVec.getX(), 0, 0); // ohne .clone() würde es die Ausgangslocation p1 editieren
		p3 = p1.clone().add(0, pVec.getY(), 0);
		p4 = p1.clone().add(pVec.getX(), pVec.getY(), 0);
		p5 = p1.clone().add(0, 0, pVec.getZ());
		p6 = p1.clone().add(pVec.getX(), 0, pVec.getZ());
		p7 = p1.clone().add(0, pVec.getY(), pVec.getZ());
		
		if(p1.clone().add(pVec.getX(), pVec.getY(), pVec.getZ()).equals(p8)) {
			return true;
		} else return false;
	}
	
	/**
	 * Gibt alle Ecken des Quaders als Location-Array zurück
	 * @return
	 */
	public Location[] getAllCorners() {
		return new Location[]{p1, p2, p3, p4, p5, p6, p7, p8};
	}
	
	public boolean allCornersValid() {
		return this.cornersValid;
	}
	
	/**
	 * Bestimmt die kleinste Ecke des Quaders, von der sich alles in positive Richtungen ausweitet
	 * @return
	 */
	private Location determineSmallestCorner() {
		Location pS = p1; // irgendwo muss man starten...
		Location[] allP = {p1, p2, p3, p4, p5, p6, p7, p8};
		for(int a = 0; a < allP.length; a++) {
			// irgendein Punkt muss ja in allen Koordinaten den kleinsten Wert haben...
			if((allP[a].getX() <= pS.getX()) 
					&& (allP[a].getY() <= pS.getY()) 
					&& (allP[a].getZ() <= pS.getZ())) {
				pS = allP[a];
			}
		}
		return pS;
	}
	
	/**
	 * Gibt die kleinste Ecke des Quaders zurück
	 * @return
	 */
	public Location getSmallestCorner() {
		return this.pSmall;
	}
	

}
