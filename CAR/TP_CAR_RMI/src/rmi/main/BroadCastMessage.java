package rmi.main;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;

import rmi.node.SiteItf;
import rmi.utils.CommandLinePaser;

/**
* Class contenant le main pour envoyer un message entre les �l�ments (noeuds)
*
* @author Dhersin J�r�me Knapik Christopher
*/
public class BroadCastMessage {
	
	/**
	 * Aide pour l'�x�cution du programme
	 * 
	 * @return Retourne une chaine de charact�res contenant l'aide du programme
	 */
	public static String contentHelper() {
		return " Ex�cution : BroadCastMessage -port=port -id=id -mess=message\n" +
		"\tport: port du registre qui va contenir les donn�es RMI \n" +
		"\tid: id du noeud que vous voulez ajouter au registre RMI \n" +
		"\tid: mess message � envoyer \n";
	}
	
	/**
	 * Diffuse le message pass� comme 2eme argument � travers les �l�ments du registres (noeuds)
	 * @param args
	 * 		1er argument  : noeud source � partir du quel on va envoyer le message <br>
	 * 		2eme argument : message � envoyer
	 * 
	 * @throws MalformedURLException
	 * @throws RemoteException
	 * @throws NotBoundException
	 */
	public static void main(String[] args) throws MalformedURLException,
		RemoteException, NotBoundException {
			if("-h".equals(args[0]) || "-help".equals(args[0])) {
				System.out.println(contentHelper());
				System.exit(1);
			}
			HashMap <String, String> cmdParsed = CommandLinePaser.parseCmd(args);
			Registry registre = null;
			int port = 10000;
			int source = -1;
			String message = null;
			if((cmdParsed.size() > 1) && (cmdParsed.size() < 4)) {
				port = Integer.parseInt(cmdParsed.get("port"));
				source = Integer.parseInt(cmdParsed.get("id"));
				message = cmdParsed.get("mess");
				if(!cmdParsed.containsKey("port")) {
					registre = LocateRegistry.getRegistry(10000);
				} else {
					registre = LocateRegistry.getRegistry(port);     
				}
			}
			SiteItf site1;
			if (source == -1) {                             // si on envoie le message de la racine
				site1 = (SiteItf) registre.lookup("Node1"); // on se place sur le noeud racine
			}
			else {
					site1 = (SiteItf) registre.lookup("Node" + source);  // on se place sur le noeud indiqu� par le 1er argument
			}
			site1.broadcast(source, message);    // on diffuse le message
	}
}