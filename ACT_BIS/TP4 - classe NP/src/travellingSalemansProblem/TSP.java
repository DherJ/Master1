package travellingSalemansProblem;

import classesPb.*;

/**
 * Classe qui correspond au problème du TSP (voyageur de commerce)
 * 
 * @author jérôme
 *
 */
public class TSP extends NP {
	public int nbVilles;
	public int[][] distances;
	public int longueurTournee;
	
	/**
	 * Constructeur par défaut
	 * @param nb : int -> nombre de villes
	 * @param dist : int[][] -> tableau à 2 dimensions qui donne les valeur des distances entre 2 villes
	 * @param lg : int -> la longeur maximale que le voyageur doit parcourir
	 */
	public TSP(int nb, int dist[][], int lg) {
		this.nbVilles = nb;
		this.distances = dist;
		this.longueurTournee = lg;
	}
	
	/**
	 * Retourne une instance du certificat lié au problème du TSP
	 */
	public CertificatTSP cert() { 
		return new CertificatTSP(this);
	}
	
	/**
	 * Méthode qui indique si le certificat passé en entrée correspond à une solution du problème
	 */
	public boolean estCorrect(Certificat c) {
		int longeur = 0;
		int i = 0;
		int listVilles [] = ((CertificatTSP) c).getListVilles();
		boolean visitedVilles[] = new boolean[listVilles.length];
		// si il y a plus d'éléments dans le certificat que dans le problème pas la peine de continuer
		if(listVilles.length > ((CertificatTSP) c).getInstancePb().nbVilles) 
			return false;
		else {
			int last = 0;  // indice du tableau correspondant à l'indice après lequel on ne rouve que des -1 dans le tableau des villes appartenant à la solution du certificat
			// recherche de doublon
			for(i = 0; i < listVilles.length; i++) {
				if(((CertificatTSP) c).getListVilles()[i] != -1) {
					if(visitedVilles[((CertificatTSP) c).getListVilles()[i]] == true) {
						 return false;
					}
					else visitedVilles[listVilles[i]] = true;
				} else {
					last = i - 1;
					break;
				}
			}
			// vérification de la longeur
			for(i = 0; i < listVilles.length - 1; i++) {
				if(((CertificatTSP) c).getListVilles()[i] != -1 && ((CertificatTSP) c).getListVilles()[i + 1] != -1) {
					longeur += distances[listVilles[i]][listVilles[i + 1]];
					if(longeur > longueurTournee)
						return false;
				} else break;
			}
			longeur += distances[last][listVilles[0]];
			if(longeur > longueurTournee)
				   return false;
			else ((CertificatTSP) c).setDist_parcourue(longeur);
		}
		return true;
	}
}