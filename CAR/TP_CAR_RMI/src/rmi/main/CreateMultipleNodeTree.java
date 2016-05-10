package rmi.main;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

import rmi.node.SiteImplTree;

/**
 * Crée les serveurs (noeuds) et les publie sur le registre
 * 
 * @author Dhersin Jérôme Knapik Christopher
 */
public class CreateMultipleNodeTree {

	/**
	 * Aide pour l'éxécution du programme
	 * 
	 * @return Retourne une chaine de charactères contenant l'aide du programme
	 */
	public static String contentHelper() {
		return " Exécution : CreateMultipleNodeTree -port=port -id={id} \n" +
		"\tport: port du registre qui va contenir les données RMI \n" +
		"\tid: id des noeuds que vous voulez ajouter au registre RMI \n";
	}
	
	/**
	 *
	 * Créé le registre avec le port 10000 pour les objects distants et y ajoute (publie) les noeuds de l'arbre
	 *
	 * @param args
	 * @throws RemoteException
	 * @throws MalformedURLException
	 * @throws AlreadyBoundException
	 */
	public static void main(String[] args) throws RemoteException,
		MalformedURLException, AlreadyBoundException {
			Registry registre = LocateRegistry.createRegistry(10000);
			if(registre == null) {                                         // Si le registre n'a pas été créé
				registre = LocateRegistry.createRegistry(10000);
			}
			ArrayList<SiteImplTree> sites = new ArrayList<SiteImplTree>();
			for (int i = 1; i < 7; i++) {
				sites.add(new SiteImplTree(i));
			}
			System.out.println("objects added");
			for (SiteImplTree site : sites) {
				site.exportServer(registre);
			}
			System.out.println("objects exported");
	}
}