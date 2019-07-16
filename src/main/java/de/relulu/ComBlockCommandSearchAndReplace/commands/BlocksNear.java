package de.relulu.ComBlockCommandSearchAndReplace.commands;

import de.relulu.ComBlockCommandSearchAndReplace.BlockRelationManager;
import de.relulu.ComBlockCommandSearchAndReplace.helpers.MessageHandler;
import de.relulu.ComBlockCommandSearchAndReplace.mathgeocalc.BlockPositionManager;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Diese Klasse handhabt den /blocksnear Befehl
 *
 * @author ReLuLu
 *
 */
public class BlocksNear implements CommandExecutor {

    private BlockPositionManager bpman = BlockPositionManager.getInstance();
    private BlockRelationManager brman;
    private MessageHandler mh;

    /**
     * Erzeugt die Befehlsklasse mit Instanz der Managerklasse
     *
     * @param brman BlockRelationManager Objekt
     */
    public BlocksNear(BlockRelationManager brman) {
        this.brman = brman;
        this.mh = brman.getMesHand();
    }

    /**
     * Handelt den /blocksnear Befehl ab
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String comname, String[] comparams) {

        if(sender instanceof Player) {

            // Sender auf Spieler
            Player p = (Player)sender;

            // ein Parameter (Zahl n) ist bevorzugt
            if(comparams.length == 1) {
                // Referenzlocation
                Location locref = p.getLocation();

                double vectorN = Double.valueOf(comparams[0]);

                Location loc1 = locref.clone().subtract(vectorN, vectorN, vectorN);
                Location loc2 = locref.clone().add(vectorN, vectorN, vectorN);

                bpman.setBlockPosition(p.getUniqueId(), loc1, true);
                mh.tell(p, "Position 1 an §r" + loc1.getX()
                        + mh.getPrimaryColor() + " / §r" + loc1.getY()
                        + mh.getPrimaryColor() + " / §r" + loc1.getZ()
                        + mh.getPrimaryColor() + " gesetzt.");
                bpman.setBlockPosition(p.getUniqueId(), loc2, false);
                mh.tell(p, "Position 2 an §r" + loc2.getX()
                        + mh.getPrimaryColor() + " / §r" + loc2.getY()
                        + mh.getPrimaryColor() + " / §r" + loc2.getZ()
                        + mh.getPrimaryColor() + " gesetzt.");

                return true;
            }
            // keine Parameter falls vergessen
            else if(comparams.length == 0) {
                mh.tell(sender, "Bitte einen Radius von N Blöcken angeben!");
                return true;
            }
            // zu viele Parameter falls übertrieben wurde
            else {
                mh.tell(sender, "Nur ein Parameter erlaubt!");
                return true;
            }

        }
        // kein Player
        else {
            mh.tell(sender, "Nur ein Spieler kann diesen Befehl nutzen!");
            return true;
        }
    }
}
