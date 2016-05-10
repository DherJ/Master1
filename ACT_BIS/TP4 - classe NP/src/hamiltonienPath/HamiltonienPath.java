package hamiltonienPath;

import hamiltonienCycle.HamiltonienCycle;
import classesPb.NP;
import classesPb.NPRed;

/**
 * Classe qui correspond au problème du chemin hamiltonien
 * 
 * @author jérôme
 *
 */
public class HamiltonienPath extends NPRed {
	
	int nbSommet;
	boolean [][] sommets;
	
	/**
	 * Constructeur de la classe
	 * @param n : int -> nombre de sommet
	 * @param sommets : boolean[][] -> tableau à 2 dimensions qui indique si un sommet A est relié à un sommet B 
	 * si sommets[A][B] = true
	 */
	public HamiltonienPath(int n, boolean[][] sommets) {
		this.nbSommet = n;
		this.sommets = new boolean[nbSommet][nbSommet];
		this.sommets = sommets;
	}
	
	
	// pour savoir si le pb du cycle hamiltonien à une solution, on le réduit dans TSP puis on vérifie si on trouve une solution
	// pour le problème de TSP avec les données du problème du chemin hamiltonien
	// va parcourir tous les certificats possibles
	/**
	 * Méthode qui détermine si le problème à une solution
	 * @return Retourne un booléen : true si le problème a une solution, false sinon
	 */
	public boolean aUneSolution() {
		return red().aUneSolution();
	}
	
	/**
	 * Méthode qui permet de réduire le problème du chemin hamiltonien dans le problème cycle hamiltonien <br>
	 * u est le 1er sommet, v est le dernier sommet du graphe <br>
	 * 1. On copie tous les sommets et les arrêtes <br>
	 * 2. On ajoute 1 sommet x <br>
	 * 3. ajouter 2 arrêtes entre (x et u) && (y et v) 
	 * @return Retourne une instance du problème HamiltonienCycle
	 */
	@Override
	public NP red() {
		int nbSommets = this.nbSommet;
		boolean D[][] = new boolean[nbSommets + 1][nbSommets + 1]; // +1 car on ajoute 1 sommet pour la transformation
		// 1. copie du graphe
		for(int i = 0; i < nbSommets; i++) {
			for(int j = 0; j < nbSommets; j++) {
				D[i][j] = this.sommets[i][j];
			}
		}
		
		// 2. ajout de 1 sommet x  ---- this.nbSommets - 1 = x
		nbSommets = this.nbSommet + 1;
		// 3. ajout de 2 arrêtes entre u,x & v,x   u = 0(premier sommet du graphe)  ----  v = this.nbSommet-2 (dernier sommet du graphe avant ajout du sommet)+
		D[0][nbSommets - 1] = true;                 // arrête entre u,x
		D[nbSommets - 1][0] = true;                 // arrête entre x,u
		D[nbSommets - 1][nbSommets - 2] = true;      // arrête entre v,y
		D[nbSommets - 2][nbSommets - 1] = true;      // arrête entre y,v
		return new HamiltonienCycle(nbSommets, D);
	}
}