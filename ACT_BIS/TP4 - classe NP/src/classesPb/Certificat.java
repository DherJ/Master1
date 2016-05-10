package classesPb;

/**
 * Interface qui �num�re l'ensemble des op�ration possibles sur une certificat
 * @author j�r�me
 *
 */
public interface Certificat {

	//saisie au clavier de la valeur du certificat
	/**
	 * Permet de saisir les valeur du certificat
	 */
	public void saisie();
	
	//affichage du certificat
	/**
	 * Affiche les valeurs du certificat
	 */
	public void display();
	
	//modification aleatoire de la valeur du certificat
	//chaque valeur possible doit pouvoir etre generee
	/**
	 * Permet de g�n�rer al�atoirement un certificat <br>
	 * Pour chaque valeur on tire un nombre al�atoire compris entre 0 et le nombre d'�l�ment du probl�me
	 */
	public  void alea();
	
	//on munira les valeurs possibles du certificat d'un ordre total

	//affecte au  certificat la premiere valeur pour l'ordre choisi
	/**
	 * Permet de r�initialiser les valeurs du certificat
	 */
	public void reset();
	
	//retourne vrai si la valeur est la derniere dans l'ordre choisi, faux sinon
	/**
	 * Permet de v�rifier si on se trouve au dernier certificat
	 * @return Retourne un boolean, true si c'est le dernier, false sinon
	 */
	public boolean estDernier();
	
	//modifie la valeur du certificat en la suivante pour l'ordre
	//comportement non defini si la certificat est le dernier
	/**
	 * Permet de passer au certificat suivant 
	 */
	public void suivant();
}