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
	 * Konstruktor für für den Konfigurationsmanager
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
	
}
