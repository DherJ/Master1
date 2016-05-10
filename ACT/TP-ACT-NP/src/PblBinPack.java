
public class PblBinPack extends PblDec {
	
	int nb_objets;        //nb d objets
	static int poids[];   //poids des objets
	int cap;              //capacite du sac
	int nb_sacs;          //nb de sacs
	
	public PblBinPack(int n, int p[], int c, int nbs ) {
			nb_objets = n;
			poids = p;
			cap = c;
			nb_sacs = nbs;
	}
	
	
	public int getNb_objets() {
		return nb_objets;
	}


	public void setNb_objets(int nb_objets) {
		this.nb_objets = nb_objets;
	}


	public int[] getPoids() {
		return poids;
	}


	public void setPoids(int[] _poids) {
		poids = _poids;
	}


	public int getCap() {
		return cap;
	}


	public void setCap(int cap) {
		this.cap = cap;
	}


	public int getNb_sacs() {
		return nb_sacs;
	}


	public void setNb_sacs(int nb_sacs) {
		this.nb_sacs = nb_sacs;
	}
	
	
	/**
	 * Methode qui affiche le tableau contenant les poids des objets.
	 */
	public void afficherPoids() {
		int i = 0;
		System.out.print("[ " + poids[0]);
		for(i = 1; i < poids.length; i++) {
			System.out.print(", " +poids[i]);
		}
		System.out.print(" ]\n");
	}
	
	
	/**
	 * Methode qui calcule le poids total de tous les objets.
	 * @return Retourne un entier qui correspond au poids total des objets.
	 */
	public int sommePoids() {
		int i = 0;
		int somme = 0;
		for (i = 0; i < poids.length; i++) {
			somme += poids[i];
		}
		return somme;
	}
	
	
	/**
	 * Methode qui indique si le probleme possede une solution. <br>
	 * Fonctionne par recherche exhaustive.
	 * @return Retourne Vrai ssi il existe une mise en sachets possible i.e. si l instance du pb est positive.
	 */
	public boolean aUneSolution() {
		CertificatBinPack certificat = new CertificatBinPack(this);
		while(!certificat.isCorrect() && !certificat.estDernier()) {
		certificat.suivant();
		}
		if(certificat.isCorrect()) {
			certificat.afficheCertificat();
			System.out.println("Le certificat est correct.\n");
			return true;
		}
		else {
			System.out.println();
			System.out.println("Le certificat n est pas correct.\n");
		}
		return false;
	}

	
	/**
	 * Methode qui teste au hasard une mise en sachets et retourne Vrai si elle est valide. <br>
	 * Chaque mise en sachets doit pouvoir etre generee par une execution.
	 * @return Retourne un boolean, vrai si le pb a une solution non-deterministe, faux sinon.
	 */
	public boolean aUneSolutionNonDeterministe() {
		CertificatBinPack certificat = new CertificatBinPack(this);
		certificat.alea();
		if(certificat.isCorrect()) {
			certificat.afficheCertificat();
			return true;
		} else { 
			certificat.afficheCertificat();
			return false; 
			}
	}
}
