


public class PblPartition extends PblDec {

	int n;                    // nombre d elements dans l ensemble
	static int ensemble[];    // tableau correspondant aux elements de l'ensemble
	
	
	
	public PblPartition (int _n, int [] _ensemble) {
		n = _n;
		ensemble = _ensemble;
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
		System.out.print("partition : [ " + ensemble[0]);
		for(i = 1; i < ensemble.length; i++) {
			System.out.print(", " + ensemble[i]);
		}
		System.out.print(" ]\n");
	}
	
	
	/**
	 * Cette methode permet de construire une instance du pb de sac a dos a partir de l instance du pb de partition. <br>
	 * Est de complexite polynomiale.
	 * @return Retourne une instance du pb de sac a dos.
	 */
	public PblBinPack redPolyTo() {
		int nSac = 2;
		int cap = sommeElmts() / 2;                   // On ajoute tous les elements de l ensemble puis on divise le resultat par 2.
		return new PblBinPack(n,ensemble,cap,nSac);
	}


	public boolean aUneSolution() {
		PblBinPack pbl = redPolyTo();
		if(pbl.aUneSolutionNonDeterministe()) {
			return true;
		} else { return false; }
	}
}
