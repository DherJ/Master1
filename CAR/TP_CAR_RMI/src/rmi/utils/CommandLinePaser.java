package rmi.utils;

import java.util.HashMap;

/**
 * Classe Utils contenant une méthode pour parser la ligne de commande
 * 
 * @author Dhersin Jérôme Knapik Christopher
 *
 */
public class CommandLinePaser {

	/**
	 * Méthode qui permet de parser les commandes du programme <br>
	 * Les arguments sont de la forme -option=value :  <br>
	 * 		-option:String <br>
	 * 		-value:int     <br>
	 * Créé un hashmap <String, int> dont les clés sont les option et les valeur les valeur des arguments
	 * 
	 * @param args : String [] -> arguments du programme
	 * @return Retourne le hashmap consruit avec les arguments du programme
	 */
	public static HashMap <String, String> parseCmd(String args[]) {
		HashMap <String, String> cmdParsed = new HashMap <String, String> ();
		String opt;
		String value;
		for(int i = 0; i < args.length; i++) {
			String[] parts = args[i].split("=");
			opt = parts[0].substring(1);
			value = parts[1];
			cmdParsed.put(opt, value);
		}
		return cmdParsed;
	}
}