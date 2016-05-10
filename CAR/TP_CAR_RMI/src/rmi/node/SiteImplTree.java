package rmi.node;

import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import org.apache.log4j.Logger;

/**
*
* Implémentation de l'interface SiteItf sous forme d'arbre
* Liste des méthodes de la classe :
* 	<table border='1'>
* 		<tr> <td>  createEdge(SiteItf site1, SiteItf site2) </td> <td> void   </td> </tr>
* 		<tr> <td>  getName()                                </td> <td> String </td> </tr>
* 		<tr> <td>  getString()                              </td> <td> String </td> </tr>
* 		<tr> <td>  broadcast(int source, byte[] datas)      </td> <td> void   </td> </tr>
* 		<tr> <td>  getIdent()                               </td> <td> int    </td> </tr>
* 		<tr> <td>  setEnd(SiteItf end)                      </td> <td> void   </td> </tr>
* 		<tr> <td>  printTrace(byte[] datas)                 </td> <td> void   </td> </tr>
* 		<tr> <td>  exportServer(Registry registre)          </td> <td> void   </td> </tr>
* 	</table>
* @author Dhersin Jérôme Knapik Christopher
*
*/
public class SiteImplTree extends UnicastRemoteObject implements SiteItf {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	final static Logger logger = Logger.getLogger(SiteImplTree.class);
	
	private Registry reg;
	private ArrayList<SiteItf> children;
	private SiteItf parent;
	protected int id;
	protected String name;
	public boolean blockingMessage;
	
	/**
	 *
	 * Crée un noeud vide (sans parent ni fils)
	 *
	 * @param id
	 * L'identifiant du noeud
	 * @throws RemoteException
	 */
	public SiteImplTree(int id) throws RemoteException {
		super();
		children = new ArrayList<SiteItf>();
		parent = null;
		this.id = id;
		this.name = "Node" + id;
	}
	
	public ArrayList<SiteItf> getChildrens() {
		return children;
	}

	public SiteItf getFather() {
		return parent;
	}
	
	public void addSon(SiteItf node) {
		children.add(node);
	}
	
	public void removeSon(SiteItf son) throws AccessException, RemoteException, NotBoundException {
		if(!this.children.remove(son)) {
			System.err.println(son + " non present dans la liste");
			if(logger.isDebugEnabled()) {
				logger.debug("Problème pour la suppression du fils : " + son.getId());
			}
			return;
		}
		son.removeFather(this);
		System.out.println("suppresion du fils: " + son.getId());
		if(logger.isInfoEnabled()) {
			logger.info("Suppression du fils : " + son.getId());
		}
	}
	
	public void removeFather(SiteItf father) throws AccessException, RemoteException, NotBoundException {
		if(!this.children.remove(father)) {
			System.err.println(father + " non present dans la liste");
			if(logger.isDebugEnabled()) {
				logger.debug("Parent : " + father + " non present dans la liste");
			}
			return;
		}
		father.removeSon(this);
		System.out.println("suppresion du pere: " + father.getId());
		if(logger.isInfoEnabled()) {
			logger.info("Suppression du pere : " + father.getId());
		}
	}
	
	public boolean isBlockingMessage() throws RemoteException {
		return this.blockingMessage;
	}
	
	public void setBlockingMessage(boolean b) throws RemoteException {
		this.blockingMessage = b;
	}
	
	/**
	 *
	 * Permet de publier cet objet sur le registre RMI spécifié
	 *
	 * @param registre le registre sur lequel publier
	 * @throws RemoteException
	 * @throws AlreadyBoundException
	 */
	public void exportServer(Registry registre) throws RemoteException,
		AlreadyBoundException {
			if(reg == null) {
				reg = LocateRegistry.getRegistry(10000);
			}
			registre.bind(this.getName(), this);
	}

	/**
	 * Dans cette implémentation, le premier arguemnt est le parent, le second
	 * un enfant de ce parent
	 *
	 */
	@Override
	public void createEdge(SiteItf parent, SiteItf child)
			throws RemoteException {
		child.setEnd(this);
		if (!this.children.contains(child)) {
			this.children.add(child);
			if(logger.isInfoEnabled()) {
				logger.info("Ajout du lien entre le pere : " + parent.getId() + " et le fils " + child.getId());
			}
		}
	}

	@Override
	public void setEnd(SiteItf end) throws RemoteException {
		this.parent = end;
	}

	/**
	 *
	 * Envoit un message à ce noeux qui sera diffusé à tous les autres noeuds.
	 *
	 * @param source
	 * La source du message (peut etre egale à -1 si cette source
	 * doit etre considérée comme la racine de l'arbre)
	 * @param datas
	 * Le message à envoyer
	 *
	 */
	@Override
	public void broadcast(int source, String datas) throws RemoteException {
		synchronized(this) {
			if(this.blockingMessage)
				return;
			this.setBlockingMessage(true);
			this.printTrace(datas);
			if (source != -1 && parent != null && parent.getId() != source && !parent.isBlockingMessage()) {
				new Thread() {
					public void run() {
						try {
							parent.broadcast((int) getId(), datas);
							if(logger.isInfoEnabled()) {
								logger.info("Envois du message à partir du noeud : " + source);
							}
						} catch (RemoteException e) {
							logger.error("Erreur lors de l'envois du message à partir du noeud : " + source);
							e.printStackTrace();
						}
					}
				}.start();
			}
			for (final SiteItf child : children) {
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
				for (SiteItf son : this.children) {
					son.setBlockingMessage(false);
				}
			}
		}
	}

	@Override
	public String getString() throws RemoteException {
		String result = name + " fils de " + parent.getName() + " et pere de :";
		for (SiteItf child : children) {
			result += child.getName();
		}
		return result;
	}

	public String getName() throws RemoteException {
		return name;
	}

	protected void printTrace(String datas) {
		System.out.print(name + " a recu " + datas + " ");
		if(logger.isDebugEnabled()) {
			logger.debug("Le noeud " + name + " a recu : " + datas);
		}
	}

	public int getId() throws RemoteException {
		return id;
	}
}