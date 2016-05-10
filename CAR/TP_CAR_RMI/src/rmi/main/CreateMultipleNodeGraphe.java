package rmi.main;

import java.net.MalformedURLException;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

import rmi.node.SiteImplGraphe;

/**
* Crée les serveurs (noeuds) et les publie sur le registre
*
* @author Dhersin Jérôme Knapik Christopher
*/
public class CreateMultipleNodeGraphe {
	
	/**
	 * Créé le registre avec le port 10000 pour les objects distants et y ajoute (publie) les noeuds du graphe
	 * 
	 * @param args
	 * @throws RemoteException
	 * @throws MalformedURLException
	 * @throws AlreadyBoundException
	 */
	public static void main(String[] args) throws RemoteException,
		MalformedURLException, AlreadyBoundException {
			Registry registre = LocateRegistry.createRegistry(10000);
			ArrayList<SiteImplGraphe> sites = new ArrayList<SiteImplGraphe>();
			for (int i = 1; i < 7; i++) {
				sites.add(new SiteImplGraphe(i));
			}
			System.out.println("objects added");
			for (SiteImplGraphe site : sites) {
				site.exportServer(registre);
			}
			System.out.println("objects exported");
	}
}