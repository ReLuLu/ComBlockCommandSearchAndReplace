package de.relulu.ComBlockCommandSearchAndReplace.helpers;

import java.util.HashMap;

import de.relulu.ComBlockCommandSearchAndReplace.BlockRelationManager;
import org.bukkit.entity.Player;

public class BlockReplaceCommandHandler {

	private BlockRelationManager brman;
	private MessageHandler mh;
	private Player p;
	private boolean argsEmpty = true;
	private String[] comargs;
	private boolean isAsterisk;
	private boolean isEvenAmountOfSARTokens = false;
	private boolean hasQuotedTokens = false;
	private int quotedTokensCount = 0;
	private String newAsteriskCommand = "";
	private HashMap<String, String> sartokens;

	/**
	 * Constructor for the command helper object.
	 * @param mh
	 * @param player
	 * @param args
	 */
	public BlockReplaceCommandHandler(BlockRelationManager brman, MessageHandler mh, Player player, String[] args) {
		this.brman = brman;
	    this.mh = mh;
		this.p = player;
		if(args.length > 0) {
			this.argsEmpty = false;
			this.isAsterisk = determineAsteriskParameter(args);
			if(isAsterisk) {
				this.comargs = cutAsteriskArray(args);
				this.newAsteriskCommand = String.join(" ", comargs);
			} else {
				this.comargs = args;
				this.quotedTokensCount = countQuotes(comargs);
				this.hasQuotedTokens = hasQuotedTokens(quotedTokensCount);
				if(hasQuotedTokens) {
					this.comargs = organizeQuotedTokens(comargs);
				}
				this.isEvenAmountOfSARTokens = determineEvenAmountOfSearchAndReplaceTokens(comargs);
				if(isEvenAmountOfSARTokens) {
					sartokens = processSearchAndReplaceTokens(comargs);
				}
			}
		}

	}
	
	public String doCommandModification(String cblcommand, String[] args) {
		String newcblcommand = cblcommand;
		if(args.length < 1) {
			mh.tell(p, "Komblockbefehl unverändert: ", cblcommand);
		}
		
		else if(args.length >= 2) {
			
			try {
				
				// if Asterisk(Wildcard)-Parameter um einen ganz neuen Befehl einzutragen
				if(isAsterisk) {
					newcblcommand = newAsteriskCommand;
					mh.tell(p, "Komblockbefehl neu: ", newcblcommand);
				
				// else kein Asterisk-Parameter, ergo search and replace
				} else if(isEvenAmountOfSARTokens) {
					mh.tell(p, "Komblockbefehl vorher: ", cblcommand);
					newcblcommand = searchInCommandAndReplace(cblcommand, sartokens);
					mh.tell(p, "Komblockbefehl nachher: ", newcblcommand);
				}
				
			} catch (Exception e) {
				mh.tell(p, "Komblockbefehl wegen Fehler ungeändert: ", e.toString());
			}
		}
		return newcblcommand;
	}
	
	/**
	 * Checks whether the command array does have the asterisk in front 
	 * or not as that determines how the parameters will be handled.
	 * @param args String[] from player command
	 * @return true or false if * is present at args[0]
	 */
	private boolean determineAsteriskParameter(String[] args) {
		return (("\\" + args[0]).trim().equals("\\*")) ? true : false;
	}
	
	/**
	 * Returns if the command has been identified as asterisk command
	 * @return
	 */
	public boolean hasAsteriskParameter() {
		return isAsterisk;
	}
	
	/**
	 * Cuts the command array to delete the leading asterisk at 
	 * args[0] in order to work with a cleaned-up array
	 * @param args String[] with the * in front
	 * @return new
	 */
	private String[] cutAsteriskArray(String[] args) {
		String[] cutargs = new String[args.length-1];
		
		// altes Array mit dem unnötigen Asterisk am Anfang auf neues Array übertragen
		try {
			for(int i = 1; i< args.length; i++) {
				// i kann niemals < 0 sein weil bei 1 begonnen wird
				cutargs[i-1] = args[i];
			}
			
		// für den unwahrscheinlichen Fall dass eine ArrayIndexOutOfBoundsException auftritt
		} catch (Exception e) {
			mh.tell(p, "Fehler mit dem *-Parameter", e.toString());
		}
		
		return cutargs;
	}

	/**
	 * An even count of quotation marks makes sure the tokens are valid
	 * @param qt count of quotation marks inside the command
	 * @return
	 */
	private boolean hasQuotedTokens(int qt) {
	    if(qt == 0) {
	        return false;
        } else return (qt % 2) == 0;
	}

	/**
	 * Counts the occurences of a quotation mark (") to verify
	 * whether there are tokens defined by quotation marks
	 * @param args String array of command parameters
	 * @return
	 */
	private int countQuotes(String[] args) {
		String comparamstring = String.join(" ", args);
		int quotecount = comparamstring.length() - comparamstring.replace("\"", "").length();
		return quotecount;
	}

	/**
	 * Converts the command parameter array from being split
	 * at spaces to being split into tokens in quotes.
	 * before: {"effect; clear"; "minecraft:effect; clear"; "tp"; "minecraft:tp"}
	 * after: {effect clear; minecraft:effect clear; tp; minecraft:tp}
	 * @param args
	 * @return
	 */
	private String[] organizeQuotedTokens(String[] args) {
		String comparamstring = String.join(" ", args);
		String[] organizedargs = comparamstring.split("\"");

		int emptycount = 0;
		for(int i = 0; i < organizedargs.length; i++) {
			if(organizedargs[i].trim().isEmpty()) {
				emptycount++;
			}
		}
		String[] cleanorganizedargs = new String[organizedargs.length - emptycount];
		int b = 0;
		for(String s : organizedargs) {
			if(!s.trim().isEmpty()) {
				cleanorganizedargs[b] = s;
				b++;
			}
		}
		return cleanorganizedargs;
	}

	/**
	 * Checks if there player-command provides and even amount
	 * of args inside the String[] to make sure every search
	 * token comes with a replace token in case of a match
	 * @param args
	 * @return
	 */
	public boolean determineEvenAmountOfSearchAndReplaceTokens(String[] args) {
		return ((args.length % 2) == 0);
	}
	
	/**
	 * Returns if the command has an even amount of search and replace tokens
	 * because every search token needs a token to replace with
	 * @return
	 */
	public boolean hasEvenAmountOfSearchAndReplaceTokens() {
		return this.isEvenAmountOfSARTokens;
	}
	
	/**
	 * Turns the search and replace token string into a map
	 * with keys to look for and values to replace with
	 * @param args
	 * @return
	 */
	private HashMap<String, String> processSearchAndReplaceTokens(String[] args) {
		HashMap<String, String> searchAndReplaceTokens = new HashMap<String, String>();
		
		// try-catch weils echt wacklig ist mit den ArrayBounds
		try {
			// alle ungeraden, startend beim ersten
			for(int a = 0; a < args.length; a = a + 2) {
				searchAndReplaceTokens.put(args[a], args[a + 1]);
			}
			
		// weils wegen ArrayIndexOutOfBounds um die Ohren fliegen kann
		} catch (Exception e) {
			mh.tell(p, "Fehler beim Einlesen der Such & -Ersetzparameter", e.toString());
		}
		
		return searchAndReplaceTokens;
	}
	
	/**
	 * Automatically replaces the first occurence of a search token
	 * with the replace token if it finds a match in the supplied
	 * @param cblcommand
	 * @param sartokens
	 * @return
	 */
	private String searchInCommandAndReplace(String cblcommand, HashMap<String, String> sartokens) {
		String newcblcommand = cblcommand;
		for(String s : sartokens.keySet()) {
			if(cblcommand.contains(s)) {
				if(brman.getConfigHelper().searchFirstCharacters() == 0) {
                    newcblcommand = newcblcommand.replaceFirst(s, sartokens.get(s));
                } else if(newcblcommand.length() >= brman.getConfigHelper().searchFirstCharacters()) {
                    String firstpart = newcblcommand.substring(0, brman.getConfigHelper().searchFirstCharacters());
                    firstpart = firstpart.replaceFirst(s, sartokens.get(s));
                    String secondpart = newcblcommand.substring(brman.getConfigHelper().searchFirstCharacters());
                    newcblcommand = firstpart + secondpart;
                }
				return newcblcommand;
			}
		}
		return cblcommand;
	}
	
	/**
	 * Returns the cleaned-up player-command arguments array 
	 * as used inside this class
	 * @return comargs cleaned-up String[]
	 */
	public String[] getCommandArgs() {
		return this.comargs;
	}
	
	/**
	 * Returns whether there were player-command 
	 * arguments supplied or not (empty).
	 * @return
	 */
	public boolean ifArgsEmpty() {
		return this.argsEmpty;
	}
	
}
