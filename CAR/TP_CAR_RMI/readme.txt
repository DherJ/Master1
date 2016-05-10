/*******************************************************\
 *		Dhersin Jérôme                                 *
 *		Knapik Christopher                             *
/*******************************************************\

Ce projet implémente une API RMI où il est possible de créer un arbre ou un graphe et d'envoyer un message
 à chaque noeuds de ces structures.


Nôtre API est composée de plusieurs mains diférents :
	- un pour créer un noeud pour la partie arbre avec un id qui permettra de le stocker dans le registre RMI (Pareil pour les graphes)
	- un pour créer les liens entre les noeuds de l'abre (donc construire l'arbre) et       (Pareil pour les graphes)
		mettre à jour les informations de chaques noeuds de l'arbre dans le registre RMI.
	- un pour envoyer un message qui va parcourir l'ensemble des noeuds de l'arbre.

-------------------------------------------------------------------------------------------------------------------------
	
Excécutions :
	- Créer un noeud (createNodeTree.jar)
	javac -jar createNodeTree.jar -port=val -id=val
	
Le -port prend comme valeur un entier qui correspondra au port du registre RMI dans lequel on va stocker le noeud
(par défaut il sera à 10000).

Le -id est l'id du noeud que l'on souhaite créer et stocker sur le registre et prend comme valeur un entier.

	- javac -jar CreateTree.jar

Ce programme ne necessite aucuns arguments il va générer automatiquement les liens entre les noeuds stockés sur le registre.

	- javac -jar broadcast.jar -port=val -id=val -mess=val

Le -port prend comme valeur un entier qui correspondra au port du registre RMI dans lequel on va stocker le noeud
(par défaut il sera à 10000).

Le -id est l'id du noeud que l'on souhaite créer et stocker sur le registre et prend comme valeur un entier.

Le -mess qui prend comme valeur une String est le message que l'on veut faire passer dans la structure.

-------------------------------------------------------------------------------------------------------------------------

Architecture :
	/* package qui contient les mains de l'API */
	-Package rmi.main
		- Classe BroadCastMessage.java          // classe qui envoie le message dans la structure
		- Classe CreateGraphe.java              // classe qui créée les liens entre les noeuds du registre RMI (partie graphe)
		- Classe CreateMultipleNodeGraphe.java  // classe qui créée plusieurs nodes pour les stocker dans le registre RMI (utilisée pour les test)
		- Classe CreateMultipleNodeTree.java    // classe qui créée plusieurs nodes pour les stocker dans le registre RMI (utilisée pour les test)
		- Classe CreateTree.java                // classe qui créée les liens entre les noeuds du registre RMI (partie arbre)
		- Classe ServerMainGraph.java           // classe qui créée 1 node pour le stocker dans le registre RMI
		- Classe ServerMainTree.java            // classe qui créée 1 node pour le stocker dans le registre RMI
	
	/* package qui contient les classes qui implémentent les fonctionnalités de l'API */
	- Package rmi.node
		- Classe SiteImplGraphe.java    // classe qui implémente les actions RMI pour les graphes
		- Classe SiteImplTree.java      // classe qui implémente les actions RMI pour les arbres
		- Classe SiteItf.java           // interface qui définie les actions qui sont possibles d'appleler à distance
	
	- Package rmi.test
		-Classe Tests.java  // classe des tests unitaires
		
	- Package rmi.utils
		-Classe CommandLineParser.java  // classe qui permet de parser la ligne de commande 
										//	pour avoir un format de ligne de commande confortable et 
										//	afin de pouvoir en extraire les valeurs des arguments facilement 

-------------------------------------------------------------------------------------------------------------------------										
	Sample Code :
	
	Partie Arbre :
	
		public void broadcast(int source, String datas)
			throws RemoteException {
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
								child.broadcast((int) getId(), datas);
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
	
	
	Partie Graphe :
	
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
	