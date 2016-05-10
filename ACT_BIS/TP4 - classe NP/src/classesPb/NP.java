package classesPb;

import travellingSalemansProblem.CertificatTSP;

/**
 * Classe abstraite qui regroupe l'ensemble des fonctions li�es aux probl�mes NP 
 * 
 * @author j�r�me
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
	 * M�thode qui d�termine si le certificat est correcte
	 * @param cert : Certificat -> Certificat dont on veut v�rifier la validit� par rapport aux contraintes du probl�me
	 * @return Retourne un boolean, true si le certificat est correcte, false sinon
	 */
	abstract public boolean estCorrect(Certificat cert);

//algo exponentiel de decision de la propriete basee sur l'enumeration
// NP est inclus dans EXPTime	
	/**
	 * M�thode qui permet de d�terminer si le probl�me � une solution en �num�rant tous les certificats possibles qui sont li�s au probl�me
	 * @return Retourne un boolean, true si le probl�me a une solution, false sinon
	 */
	public boolean aUneSolution() {
		boolean result = false;
		Certificat certificat = cert();
		
		// on parcourt tous les certificats jusqu'� en trouver un correct
		// retourn vrai si on en trouve un, faux sinon
		while(!((CertificatTSP) certificat).estCorrect() && !((CertificatTSP) certificat).estDernier()) {
			certificat.suivant();
		}
		if(((CertificatTSP) certificat).estCorrect()) { // D�s qu'on a trouv� un certificat qui correspond � une solution du pb
			((CertificatTSP) certificat).display();
			result = true;
		}
		return result;
    }

	
//algo non deterministe  polynomial:
//si il existe une solution AU MOINS UNE execution retourne Vrai
//si il n'en existe pas TOUTES les executions retournent faux!
	
// algo non-d�terministe : cnsiste � g�n�rer al�atoirement un certificat et de v�rifier s'il est correct 
// avec l'algo d�terministe de v�rification
	/**
	 * M�thode qui d�termine si le probl�me a une solution non d�terministe. <br>
	 * 1. On g�n�re un certificat al�atoire <br>
	 * 2. On v�rifie la validit� du certificat <br>
	 * 3. On retourne le r�sultat
	 * @return Retourne un boolean, true si le probl�me a une solution non d�terministe, false sinon
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