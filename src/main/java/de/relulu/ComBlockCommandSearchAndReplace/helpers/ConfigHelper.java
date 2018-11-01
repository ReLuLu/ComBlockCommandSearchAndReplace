package de.relulu.ComBlockCommandSearchAndReplace.helpers;

import org.bukkit.configuration.file.FileConfiguration;

/**
 * Diese Klasse kümmert sich um das Auslesen und Bereitstellen von Konfigurationsparametern
 * 
 * @author ReLuLu
 *
 */
public class ConfigHelper {
	
	private FileConfiguration cfg;
	
	/**
	 * Konstruktor für den Konfigurationsmanager
	 * 
	 * @param fcfg
	 */
	public ConfigHelper(FileConfiguration fcfg) {
		this.cfg = fcfg;
	}
	
	/**
	 * Gibt zurück, ob debug-Ausgaben aktiv sind oder nicht
	 * 
	 * @return
	 */
	public boolean isDebug() {
		return cfg.getBoolean("debug", false);
	}

	/**
	 * Gibt zurück, bis zum wievielten Zeichen eines Befehls überprüft werden soll
	 *
	 * @return
	 */
	public int searchFirstCharacters() {
		return cfg.getInt("search-first-chars", 0);
	}
	
}
