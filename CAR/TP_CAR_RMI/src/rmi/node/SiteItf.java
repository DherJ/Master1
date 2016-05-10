package rmi.node;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
*
* Interface RMI coté serveur représentant un des noeuds de la structure
* Liste des méthodes accessibles par l'interface :
* 	<table border='1'>
* 		<tr> <td>  createEdge(SiteItf site1, SiteItf site2) </td> <td> void   </td> </tr>
* 		<tr> <td>  getName()                                </td> <td> String </td></tr>
* 		<tr> <td>  getString()                              </td> <td> String </td></tr>
* 		<tr> <td>  broadcast(int source, byte[] datas)      </td> <td> void   </td></tr>
* 		<tr> <td>  getIdent()                               </td> <td> int    </td></tr>
* 		<tr> <td>  setEnd(SiteItf end)                      </td> <td> void   </td></tr>
* 	</table>
* 
* @author Dhersin Jérôme Knapik Christopher
*/
public interface SiteItf extends Remote {
	
	/**
	 * Crée un arc entre deux noeud de la structure
	 *
	 * @param site1 : SiteItf -> noeud à partir du quel on va créer un lien (noeud père)
	 * @param site2 : SiteItf -> noeud destination pour le lien (noeud fils)
	 * @throws RemoteException
	 */
	public void createEdge(SiteItf site1, SiteItf site2) throws RemoteException;
	/**
	 * Donne le nom de l'objet, c'est le nom utilisé pour le registre RMI
	 *
	 * @return Retourne une String correspondant au nom de l'objet
	 * @throws RemoteException
	 */
	public String getName() throws RemoteException;
	/**
	 * Donne une chaine représentant l'objet avec des informations sur le noeud et ses voisins/fils/pere
	 *
	 * @return Retourne une String contenant l'ensemble de ces informations
	 * @throws RemoteException
	 */
	public String getString() throws RemoteException;
	/**
	 * Envoit un message à ce noeud qui sera diffusé à tous les autres noeuds.
	 *
	 * @param source : int -> 
	 * La source du message, peut être -1 dans certains cas :              </br>
	 * 	-Dans un arbre si le noeud est (ou doit etre) consideré comme la
	 * 		source (le message ne remontera pas dans la hiérarchie)        </br>
	 *  -Dans un graphe, dans tous les cas (source pas necessaire)
	 * @param datas : String -> Le message à envoyer
	 * @throws RemoteException
	 */
	public void broadcast(int source, String datas) throws RemoteException;
	/**
	 * Méthode qui retourne l'identifiant du noeud
	 *
	 * @return Retourne un int correspondant à l'identifiant du noeud
	 * @throws RemoteException
	 */
	public int getId() throws RemoteException;
	/**
	 * Définie le site (noeud) spécifié comme étant un voisin
	 *
	 * @param end : SiteItf -> Le voisin
	 * @throws RemoteException
	 */
	public void setEnd(SiteItf end) throws RemoteException;
	/**
	 * Méthode qui permet de supprimer le lien entre le noeud passé en paramètre et son père
	 * @param node : SiteItf -> noeud dont on veut supprimer le lien avec son parent
	 * @throws NotBoundException 
	 * @throws RemoteException 
	 * @throws AccessException 
	 */
	public void removeFather(SiteItf node) throws AccessException, RemoteException, NotBoundException;
	/**
	 * Méthode qui permet de supprimer un noeud passé en paramètre
	 * @param node : SiteItf -> noeud que l'on veut supprimer
	 * @throws NotBoundException 
	 * @throws RemoteException 
	 * @throws AccessException 
	 */
	public void removeSon(SiteItf node) throws AccessException, RemoteException, NotBoundException;
	
	public boolean isBlockingMessage() throws RemoteException;
	
	public void setBlockingMessage(boolean b) throws RemoteException;
	
	public void addSon(SiteItf node) throws RemoteException;
	
	public ArrayList<SiteItf> getChildrens() throws RemoteException;
}