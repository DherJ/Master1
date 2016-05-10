package hamiltonienCycle;

import classesPb.NP;
import classesPb.NPRed;
import travellingSalemansProblem.TSP;

/**
 * Classe qui correspond au problème du circuit hamiltonien
 * 
 * @author jérôme
 *
 */
public class HamiltonienCycle extends NPRed {
	
	int nbSommet;
	boolean [][] sommets;
	
	/**
	 * Constructeur de la classe
	 * @param n : int -> nombre de sommet
	 * @param sommets : boolean[][] -> tableau à 2 dimensions qui indique si un sommet A est relié à un sommet B 
	 * si sommets[A][B] = true
	 */
	public HamiltonienCycle(int n, boolean[][] sommets) {
		this.nbSommet = n;
		this.sommets = new boolean[nbSommet][nbSommet];
		this.sommets = sommets;
	}
	
	
	// pour savoir si le pb du cycle hamiltonien à une solution, on le réduit dans TSP puis on vérifie si on trouve une solution
	// pour le problème de TSP avec les données du problème du cycle Hamiltonien
	// va parcourir tous les certificats possibles
	/**
	 * Méthode qui détermine si le problème à une solution
	 * @return Retourne un booléen : true si le problème a une solution, false sinon
	 */
	public boolean aUneSolution() {
		return red().aUneSolution();
	}

	/**
	 * Méthode qui permet de réduire le problème du cycle hamiltonien dans le problème TSP
	 * @return Retourne une instance du problème TSP
	 */
	@Override
	public NP red() {
		int nbVilles = this.nbSommet;
		int l = nbVilles;     // l = nbSommets car tous les sommets sont reliés entre eux et chaque sommet est relié à 1 seul sommet
		int D[][] = new int[nbVilles][nbVilles];
		for(int i = 0; i < nbVilles; i++) {
			for(int j = 0; j < nbVilles; j++) {
				if(this.sommets[i][j] == true) {
					D[i][j] = 1;
				} else D[i][j] = 2;
			}
		}
		return new TSP(nbVilles, D, l);
	}
	
	
	/** Getters and Setters **/
	public int getNbSommet() {
		return nbSommet;
	}

	public boolean[][] getSommets() {
		return sommets;
	}
}