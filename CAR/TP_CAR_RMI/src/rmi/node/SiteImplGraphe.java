package rmi.node;

import java.rmi.RemoteException;
import java.util.ArrayList;

/**
* Implémentation de l'interface SiteItf sous forme de graphe non orienté
* Liste des méthodes de la classe :
* 	<table border='1'>
* 		<tr> <td>  createEdge(SiteItf site1, SiteItf site2) </td> <td> void   </td> </tr>
* 		<tr> <td>  getString()                              </td> <td> String </td> </tr>
* 		<tr> <td>  broadcast(int source, byte[] datas)      </td> <td> void   </td> </tr>
* 		<tr> <td>  setEnd(SiteItf end)                      </td> <td> void   </td> </tr>
* 	</table>
* 
* @author Dhersin Jérôme Knapik Christopher
*/
public class SiteImplGraphe extends SiteImplTree {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<SiteItf> childrens;
	public boolean blockingMessage;
	
	/**
	 * Crée un noeud vide (sans voisins)
	 *
	 * @param id : int -> L'identifiant du noeud que l'on veut ajouter
	 * 
	 * @throws RemoteException
	 */
	public SiteImplGraphe(int id) throws RemoteException {
		super(id);
		childrens = new ArrayList<SiteItf>();
	}

	@Override
	public String getString() throws RemoteException {
		String result = name + " avec pour voisins :";
		for (SiteItf end : childrens) {
			result += end.getName();
		}
		return result;
	}

	protected void printTrace(String datas) {
		System.out.print(name + " a recu " + datas + " ");
		if(logger.isDebugEnabled()) {
			logger.debug("Le noeud " + name + " a recu : " + datas);
		}
	}
	
	/**
	 * Envoit un message à ce noeux qui sera diffusé à tous les autres noeuds.
	 * Dans cette implémentation, un message broadcasté peut être reçu plusieurs
	 * fois par un même noeud
	 *
	 * @param source : int    -> La source (peut etre egale à -1 dans le cas d'un graphe)
	 * @param datas  : String -> Le message à envoyer
	 *
	 */
	@Override
	public void broadcast(int source, String datas) throws RemoteException {
		synchronized(this) {
			if(this.blockingMessage)
				return;
			printTrace(datas);
			this.blockingMessage = true;
			for (final SiteItf child : childrens) {
				if (child.getId() != source && !child.isBlockingMessage()) {
					new Thread() {
						public void run() {
							try {
								child.broadcast(child.getId(), datas);
							} catch (RemoteException e) {
								logger.error("erreur envois de massage au fils");
								e.printStackTrace();
							}
						}
					}.start();
				}
			}
			if(this.getId() == source) {
				this.setBlockingMessage(true);
				for (SiteItf son : this.childrens) {
					son.setBlockingMessage(false);
				}
			}
		}
	}

	@Override
	public void createEdge(SiteItf site1, SiteItf site2) throws RemoteException {
		if (!this.childrens.contains(site2)) {
			this.childrens.add(site2);
		}
		site2.setEnd(site1);
	}

	@Override
	public void setEnd(SiteItf end) throws RemoteException {
		if (!this.childrens.contains(end)) {
			this.childrens.add(end);
		}
	}
}