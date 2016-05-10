package classesPb;

import travellingSalemansProblem.CertificatTSP;


abstract public class NPRed extends NP {

	/**
	 * Méthode qui permet de réduire un problème dans un autre en adaptant les instances
	 * @return Retourne une instance du problème TSP
	 */
	abstract public NP red();

	public Certificat cert() {return red().cert();}

	public boolean estCorrect(Certificat cert) {
		int distance = 0;
		int listVilles [] = ((CertificatTSP) cert).getListVilles();
		boolean visitedVilles[] = new boolean[listVilles.length];
		if(listVilles.length > ((CertificatTSP) cert).getInstancePb().nbVilles)
			return false;
		else { 
				int i = 0;
				// vérification s'il n'y a pas de doublon
				for(i = 0; i < listVilles.length; i++) {
					if(visitedVilles[listVilles[i]] == true) 
						return false;
					else visitedVilles[listVilles[i]] = true;
				}
				// calcul de la distance
				for(i = 0; i < listVilles.length - 1; i++) {
					distance += ((CertificatTSP) cert).getInstancePb().
							distances[listVilles[i]][listVilles[i + 1]];
					if(distance > ((CertificatTSP) cert).getInstancePb().longueurTournee)
						return false;
				}
				distance += ((CertificatTSP) cert).getInstancePb().
						distances[listVilles[((CertificatTSP) cert).getInstancePb().nbVilles-1]][listVilles[0]];
				if(distance > ((CertificatTSP) cert).getInstancePb().longueurTournee) 
					return false;
		}
		return true;
	}
}