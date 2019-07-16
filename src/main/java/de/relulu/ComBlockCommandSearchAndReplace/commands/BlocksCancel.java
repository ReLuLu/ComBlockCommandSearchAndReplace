package de.relulu.ComBlockCommandSearchAndReplace.commands;

import de.relulu.ComBlockCommandSearchAndReplace.BlockRelationManager;
import de.relulu.ComBlockCommandSearchAndReplace.helpers.MessageHandler;
import de.relulu.ComBlockCommandSearchAndReplace.mathgeocalc.BlockPositionManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Diese Klasse handhabt den /blockscancel Befehl
 *
 * @author ReLuLu
 *
 */
public class BlocksCancel implements CommandExecutor {

    private BlockPositionManager bpman = BlockPositionManager.getInstance();
    private BlockRelationManager brman;
    private MessageHandler mh;

    /**
     * Erzeugt die Befehlsklasse mit Instanz der Managerklasse
     *
     * @param brman BlockRelationManager Objekt
     */
    public BlocksCancel(BlockRelationManager brman) {
        this.brman = brman;
        this.mh = brman.getMesHand();
    }

    /**
     * Handelt den /blockscancel Befehl ab
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String comname, String[] comparams) {

        if(sender instanceof Player) {

            // Sender auf Spieler
            Player p = (Player) sender;

            bpman.removeBlockPositionAll(p.getUniqueId());
            mh.tell(sender, "Auswahl gelöscht!");
            return true;

        }
        // kein Player
        else {
            mh.tell(sender, "Nur ein Spieler kann diesen Befehl nutzen!");
            return true;
        }
    }
}
