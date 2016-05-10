

public interface Certificat {

	
	/**
	 * Algo de verification A de la definition NP du cours.
	 * @return Retourne un boolean indiquant si le certificat est correct (si il correspond a une solution du probleme).
	 */
	public boolean isCorrect(); 
	
	
	/**
	 * Methode qui permet de passer au certificat suivant.
	 */
	public void suivant();
	
	
	/**
	 * Methode qui indique si on se trouve au dernier certificat.
	 * @return Retourne un boolean : vrai si on se trouve sur le dernier certificat, faux sinon.
	 */
	public boolean estDernier();
	
	
	/**
	 * Methode qui genere un certificat aleatoirement.
	 * Remplit le tableau de repartition aleatoirement.
	 */
	public void alea();
	
	
	/**
	 * Methode qui affiche la valeur du certificat.
	 */
	public void afficheCertificat();
	
	
	/**
	 * Methode qui permet de revenir au 1er certificat.
	 */
	public void reset();
	
	
	/**
	 * Methode qui permet a l utilisateur de saisir la valeur d un certificat.
	 * Donc qui permet a l utilisateur de choisir dans quel sac mettre quel objet.
	 */
	public void saisie();
}
