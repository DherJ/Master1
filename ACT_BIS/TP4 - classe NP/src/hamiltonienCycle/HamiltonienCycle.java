package hamiltonienCycle;

import classesPb.NP;
import classesPb.NPRed;
import travellingSalemansProblem.TSP;

/**
 * Classe qui correspond au probl�me du circuit hamiltonien
 * 
 * @author j�r�me
 *
 */
public class HamiltonienCycle extends NPRed {
	
	int nbSommet;
	boolean [][] sommets;
	
	/**
	 * Constructeur de la classe
	 * @param n : int -> nombre de sommet
	 * @param sommets : boolean[][] -> tableau � 2 dimensions qui indique si un sommet A est reli� � un sommet B 
	 * si sommets[A][B] = true
	 */
	public HamiltonienCycle(int n, boolean[][] sommets) {
		this.nbSommet = n;
		this.sommets = new boolean[nbSommet][nbSommet];
		this.sommets = sommets;
	}
	
	
	// pour savoir si le pb du cycle hamiltonien � une solution, on le r�duit dans TSP puis on v�rifie si on trouve une solution
	// pour le probl�me de TSP avec les donn�es du probl�me du cycle Hamiltonien
	// va parcourir tous les certificats possibles
	/**
	 * M�thode qui d�termine si le probl�me � une solution
	 * @return Retourne un bool�en : true si le probl�me a une solution, false sinon
	 */
	public boolean aUneSolution() {
		return red().aUneSolution();
	}

	/**
	 * M�thode qui permet de r�duire le probl�me du cycle hamiltonien dans le probl�me TSP
	 * @return Retourne une instance du probl�me TSP
	 */
	@Override
	public NP red() {
		int nbVilles = this.nbSommet;
		int l = nbVilles;     // l = nbSommets car tous les sommets sont reli�s entre eux et chaque sommet est reli� � 1 seul sommet
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