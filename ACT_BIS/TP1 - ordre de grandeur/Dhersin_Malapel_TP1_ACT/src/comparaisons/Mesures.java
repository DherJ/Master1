package comparaisons;

import datastructures.DataStructure;

public class Mesures {

	/**
	 * Permet de renvoyer la mesure de la complexité pour l'exécution de
	 * la fonction alea sur une structure de donnée.
	 * @param aMesurer Structure de données sur laquelle on exécute alea
	 * @return Valeur de la mesure
	 */
	public static long mesureAlea (DataStructure aMesurer) {
		long begin = System.nanoTime();
		aMesurer.alea();
		long end = System.nanoTime();
		return end - begin;
	}
	
	/**
	 * Permet de renvoyer la mesure de la complexité pour l'exécution de
	 * la fonction recherche sur une structure de donnée.
	 * @param aMesurer Structure de données sur laquelle on exécute recherche
	 * @return Valeur de la mesure
	 */
	public static long mesureRecherche (DataStructure aMesurer, int val) {
		long begin = System.nanoTime();
		aMesurer.recherche(val);
		long end = System.nanoTime();
		return end - begin;
	}
	
	/**
	 * Permet de renvoyer la mesure de la complexité pour l'exécution de
	 * la fonction mesureSimple sur une structure de donnée.
	 * @param aMesurer Structure de données sur laquelle on exécute mesureSimple
	 * @return Valeur de la mesure
	 */
	public static long mesureMinimumSimple (DataStructure aMesurer) {
		long begin = System.nanoTime();
		aMesurer.minimumSimple();
		long end = System.nanoTime();
		return end - begin;
	}
	
	/**
	 * Permet de renvoyer la mesure de la complexité pour l'exécution de
	 * la fonction minimumTri sur une structure de donnée.
	 * @param aMesurer Structure de données sur laquelle on exécute minimumTri
	 * @return Valeur de la mesure
	 */
	public static long mesureMinimumTri (DataStructure aMesurer) {
		long begin = System.nanoTime();
		aMesurer.minimumTri();
		long end = System.nanoTime();
		return end - begin;
	}
	
	/**
	 * Permet de renvoyer la mesure de la complexité pour l'exécution de
	 * la fonction mystere sur une structure de donnée.
	 * @param aMesurer Structure de données sur laquelle on exécute mystere
	 * @return Valeur de la mesure
	 */
	public static long mesureMystere (DataStructure aMesurer) {
		long begin = System.nanoTime();
		aMesurer.mystere();
		long end = System.nanoTime();
		return end - begin;
	}
}