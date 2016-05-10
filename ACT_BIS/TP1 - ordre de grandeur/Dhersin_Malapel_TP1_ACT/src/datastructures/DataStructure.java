package datastructures;

public interface DataStructure {
	
	/**
	 * Complexit� en O(n)
	 * Place des éléments aléatoires dans la structure de données.
	 */
	public void alea();
	
	/**
	 * Complexit� en O(n)
	 * Test la présence de e dans la structure de données
	 * @param e Élément à rechercher
	 * @return vrai si l'élément est trouvé
	 */
	public boolean recherche(int e);
	
	/**
	 * Complexit� en O(n)
	 * Recherche l'élément minimum dans l'ensemble des élements de la structure
	 * @return l'élément minimum
	 */
	public int minimumSimple();
	/**
	 * Complexit� en O(log(n))
	 * Recherche l'élément minimum dans l'ensemble des élements de la structure
	 * en commencant par trier les éléments pour ensuite prendre le premier.
	 * @return l'élément minimum
	 */
	public int minimumTri();
	
	/**
	 *  Fonction qui tri un tableau
	 *  Complexit� en O(n * (n + log(n) + n) = O(2n� + nlog(n))
	 */
	public void mystere();
	
}
