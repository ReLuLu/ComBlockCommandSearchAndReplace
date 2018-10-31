package de.relulu.ComBlockCommandSearchAndReplace;

import java.util.logging.Logger;

import org.bukkit.plugin.Plugin;

import de.relulu.ComBlockCommandSearchAndReplace.helpers.ConfigHelper;
import de.relulu.ComBlockCommandSearchAndReplace.helpers.MessageHandler;

/**
 * Eine Klasse zur Verwaltung der Informationen und so.
 * 
 * @author ReLuLu
 *
 */
public class BlockRelationManager {
	
	private ConfigHelper 		confhelp;
	private MessageHandler 		mh;
	
	private Plugin 				plugin;
	
	private Logger 				log;
	

	
	/**
	 * Erzeugt das BlockRelationManager-Objekt und zudem den Konfigurationshelper.
	 */
	public BlockRelationManager(Plugin plugin) {
		
		this.plugin = plugin;
		this.confhelp = new ConfigHelper(plugin.getConfig());	
		this.mh = new MessageHandler(
				!plugin.getConfig().getString("message-prefix", "").replace("&", "§").equals("") ? (plugin.getConfig().getString("message-prefix", "").replace("&", "§") + " ") : plugin.getConfig().getString("message-prefix", "").replace("&", "§"), // leerer String als default
				plugin.getConfig().getString("primary-color", "§e").replace("&", "§"), // §e als default
				plugin.getConfig().getString("secondary-color", "§r").replace("&", "§") // §r als default
				);
	}
	
	/**
	 * Getter für's Log
	 * 
	 * @return
	 */
	public Logger getLogger() {
		return this.log;
	}
	
	/**
	 * Gibt den ConfigManager zurück, um Zugriff 
	 * auf Konfigurationsinhalte zu ermöglichen
	 * 
	 * @return
	 */
	public ConfigHelper getConfigHelper() {
		return this.confhelp;
	}
	
	/**
	 * Gibt den MessageHelper zurück, um mit 
	 * vereinfachten Chatausgaben arbeiten zu können.
	 * 
	 * @return
	 */
	public MessageHandler getMesHand() {
		return this.mh;
	}

	/**
	 * Gibt die Instanz des Plugins zurück
	 * @return
	 */
	public Plugin getPluginInstance() {
		return this.plugin;
	}
	
}
