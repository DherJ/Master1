package rmi.main;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;

import rmi.node.SiteImplTree;
import rmi.utils.CommandLinePaser;

/**
 * Crée un noeud et le publie sur le registre RMI pour la partie arbre
 * 
 * @author Dhersin Jérôme Knapik Christopher
 */
public class ServerMainTree {

	/**
	 * Aide pour l'éxécution du programme
	 * 
	 * @return Retourne une chaine de charactères contenant l'aide du programme
	 */
	public static String contentHelper() {
		return " Exécution : ServerMainTree -port=port -id=id\n" +
		"\tport: port du registre qui va contenir les données RMI \n" +
		"\tid: id du noeud que vous voulez ajouter au registre RMI\n";
	}
	
	/**
	 * Créé le registre avec le port donné en argument pour les objects distants et y ajoute (publie) le noeud donné en argument
	 *
	 * @param args : String ->
	 * 		1er argument : le port du registre 
	 * 		2eme argument : l'id du noeud à ajouter au registre
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
				if(registre == null) {            // Si le registre n'a pas été créé 
					if(!cmdParsed.containsKey("port")) {
						registre = LocateRegistry.createRegistry(10000);
					} else {
						registre = LocateRegistry.createRegistry(port);     
					}
				}
				SiteImplTree site = new SiteImplTree(id);
				System.out.println("objects added");
				site.exportServer(registre);
				System.out.println("objects exported");
			}
	}
}