package de.relulu.ComBlockCommandSearchAndReplace.helpers;

import org.bukkit.configuration.file.FileConfiguration;

/**
 * Diese Klasse k�mmert sich um das Auslesen und Bereitstellen von Konfigurationsparametern
 * 
 * @author ReLuLu
 *
 */
public class ConfigHelper {
	
	private FileConfiguration cfg;
	
	/**
	 * Konstruktor f�r f�r den Konfigurationsmanager
	 * 
	 * @param fcfg
	 */
	public ConfigHelper(FileConfiguration fcfg) {
		this.cfg = fcfg;
	}
	
	/**
	 * Gibt zur�ck, ob debug-Ausgaben aktiv sind oder nicht
	 * 
	 * @return
	 */
	public boolean isDebug() {
		return cfg.getBoolean("debug", false);
	}
	
}
