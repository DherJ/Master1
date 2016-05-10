
public class PblSum {

	int n;                    // nombre d elements dans l ensemble
	static int ensemble[];    // tableau correspondant aux elements de l'ensemble
	int c;                    // entier cible
	
	
	public PblSum (int _n, int [] _ensemble, int _c) {
		n = _n;
		ensemble = _ensemble;
		c = _c;
	}
	
	
	/**
	 * Methode qui effectue la somme des elements de l ensemble donnee dans l instance du probleme.
	 * @return Retourne un entier qui correspond a la somme des elements de l ensemble.
	 */
	public int sommeElmts() {
		int i = 0;
		int somme = 0;
		for(i = 0; i < ensemble.length; i++) {
			somme += ensemble[i];
		}
		return somme;
	}
	
	
	public void affichElemt() {
		int i = 0;
		System.out.print("Sum : [ " + ensemble[0]);
		for(i = 1; i < ensemble.length; i++) {
			System.out.print(", " + ensemble[i]);
		}
		System.out.print(" ]\n");
	}
	
	
	/**
	 * Cette methode permet de construire une instance du pb Partition a partir de l instance du pb Sum. <br>
	 * Est de complexite polynomiale.
	 * @return Retourne une instance du pb de sac a dos.
	 */
	public PblPartition redPolyTo() {
		int cap = sommeElmts() / 2;                   
		return new PblPartition(n,ensemble);
	}


	public boolean aUneSolution() {
		PblPartition pbp = redPolyTo();
		if(pbp.aUneSolution()) {
			return true;
		} else { return false; }
	}
}