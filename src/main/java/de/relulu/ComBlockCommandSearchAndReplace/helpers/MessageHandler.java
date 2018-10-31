package de.relulu.ComBlockCommandSearchAndReplace.helpers;

import org.bukkit.command.CommandSender;

/**
 * Handles chat messages to spare some code elsewhere and make formatting of messages easier to configure
 *
 * @author ReLuLu
 *
 */
public class MessageHandler {

        private String messageprefix = ""; // redundant init
        private String primarycolor = "§e"; // redundant init
        private String secondarycolor = "§r"; // redundant init

        /**
         * Constructor for the MessageHandler object with everything it needs to know
         *
         * @param prefix the message prefix if any, can be an empty string
         * @param primcolor the message primary color
         * @param seccolor the message secondary color
         */
        public MessageHandler(String prefix, String primcolor, String seccolor) {
            setPrefix(prefix);
            setPrimaryColor(primcolor);
            setSecondaryColor(seccolor);
        }

        /**
         * Alternate to sendMessage that deals with prefix and color
         * console usage
         *
         * @param cs sender, who receives the message
         * @param text text token
         */
        public void tell(CommandSender cs, String text) {
            cs.sendMessage(messageprefix + primarycolor + text);
        }
        
        /**
         * Alternate to sendMessage that deals with prefix and color
         * console usage
         *
         * @param cs sender, who receives the message
         * @param text1 first text token
         * @param var1 first variable token
         */
        public void tell(CommandSender cs, String text1, String var1) {
        	this.tell(cs, text1, var1, "");
        }

        /**
         * Alternate to sendMessage that deals with prefix and color
         * console usage
         *
         * @param cs sender, who receives the message
         * @param text1 first text token
         * @param var1 first variable token
         * @param text2 second text token
         */
        public void tell(CommandSender cs, String text1, String var1, String text2) {
            cs.sendMessage(messageprefix + primarycolor + text1 + secondarycolor + var1 + primarycolor + text2);
        }

        public void setPrefix(String prefix) {
            messageprefix = prefix;
        }

        public void setPrimaryColor(String color) {
            primarycolor = color;
        }

        public void setSecondaryColor(String color) {
            secondarycolor = color;
        }

        public String getPrefix() {
            return this.messageprefix;
        }

        public String getPrimaryColor() {
            return this.primarycolor;
        }

        public String getSecondaryColor() {
            return this.secondarycolor;
        }

}
