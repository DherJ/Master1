package classesPb;

import travellingSalemansProblem.CertificatTSP;

/**
 * Classe abstraite qui regroupe l'ensemble des fonctions liées aux problèmes NP 
 * 
 * @author jérôme
 *
 */
public abstract class NP extends ExpTime {
    
//on doit pouvoir definir pour le pb la notion de  certificat 
//Attention: on doit pouvoir borner polynomialement la taille du certificat
//	par rapport a la taille du probleme
	abstract public Certificat cert(); 

//Algo de verification d'un certificat pour le probleme
//c'est l'algo A de la definition de NP par les certificats
	/**
	 * Méthode qui détermine si le certificat est correcte
	 * @param cert : Certificat -> Certificat dont on veut vérifier la validité par rapport aux contraintes du problème
	 * @return Retourne un boolean, true si le certificat est correcte, false sinon
	 */
	abstract public boolean estCorrect(Certificat cert);

//algo exponentiel de decision de la propriete basee sur l'enumeration
// NP est inclus dans EXPTime	
	/**
	 * Méthode qui permet de déterminer si le problème à une solution en énumérant tous les certificats possibles qui sont liés au problème
	 * @return Retourne un boolean, true si le problème a une solution, false sinon
	 */
	public boolean aUneSolution() {
		boolean result = false;
		Certificat certificat = cert();
		
		// on parcourt tous les certificats jusqu'à en trouver un correct
		// retourn vrai si on en trouve un, faux sinon
		while(!((CertificatTSP) certificat).estCorrect() && !((CertificatTSP) certificat).estDernier()) {
			certificat.suivant();
		}
		if(((CertificatTSP) certificat).estCorrect()) { // Dès qu'on a trouvé un certificat qui correspond à une solution du pb
			((CertificatTSP) certificat).display();
			result = true;
		}
		return result;
    }

	
//algo non deterministe  polynomial:
//si il existe une solution AU MOINS UNE execution retourne Vrai
//si il n'en existe pas TOUTES les executions retournent faux!
	
// algo non-déterministe : cnsiste à générer aléatoirement un certificat et de vérifier s'il est correct 
// avec l'algo déterministe de vérification
	/**
	 * Méthode qui détermine si le problème a une solution non déterministe. <br>
	 * 1. On génère un certificat aléatoire <br>
	 * 2. On vérifie la validité du certificat <br>
	 * 3. On retourne le résultat
	 * @return Retourne un boolean, true si le problème a une solution non déterministe, false sinon
	 */
	public boolean aUneSolutionNonDeterministe() {
		Certificat certificat = cert();
		((CertificatTSP) certificat).alea();
		
		if(((CertificatTSP) certificat).estCorrect()) {
			((CertificatTSP) certificat).display();
			return true;
		}
		else return false;
    }
}