package rmi.main;

import java.net.MalformedURLException;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;

import rmi.node.SiteImplGraphe;
import rmi.node.SiteImplTree;
import rmi.utils.CommandLinePaser;

/**
* Cr�e le noeud et le publie sur le registre RMI pour la partie graphe
*
* @author Dhersin J�r�me Knapik Christopher
*/
public class ServerMainGraph {
	
	/**
	 * Aide pour l'�x�cution du programme
	 * 
	 * @return Retourne une chaine de charact�res contenant l'aide du programme
	 */
	public static String contentHelper() {
		return " Ex�cution : ServerMainGraphe -port=port -id=id\n" +
		"\tport: port du registre qui va contenir les donn�es RMI \n" +
		"\tid: id du noeud que vous voulez ajouter au registre RMI\n";
	}
	
	/**
	 * Cr�� le registre avec le port donn� en argument pour les objects distants et y ajoute (publie) le noeud donn� en argument
	 *
	 * @param args
	 * @throws RemoteException
	 * @throws MalformedURLException
	 * @throws AlreadyBoundException
	 */
	public static void main(String[] args) throws RemoteException,
		MalformedURLException, AlreadyBoundException {
			if("-h".equals(args[0]) || "-help".equals(args[0])) {
				System.out.println(contentHelper());
				System.exit(1);
			}
			HashMap <String, String> cmdParsed = CommandLinePaser.parseCmd(args);
			if((cmdParsed.size() > 1) && (cmdParsed.size() < 3)) {
				int port = Integer.parseInt(cmdParsed.get("port"));
				int id = Integer.parseInt(cmdParsed.get("id"));
				Registry registre = LocateRegistry.createRegistry(port);
				if(registre == null) {                                         // Si le registre n'a pas �t� cr��
					if(!cmdParsed.containsKey("port")) {
						registre = LocateRegistry.createRegistry(10000);
					} else {
						registre = LocateRegistry.createRegistry(port);
					}
				}
				SiteImplTree site = new SiteImplGraphe(id);
				System.out.println("objects added");
				site.exportServer(registre);
				System.out.println("objects exported");
			}
	}
}