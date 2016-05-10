
import java.util.Scanner;



public class CertificatBinPack implements Certificat {
	
	public int [] repartition;                  // tableau qui correspondant a la repartition des objets dans les sacs
	private PblBinPack pb;                      // le probleme associe au certificat
	int [] sac;                                 // tableau qui va contenir les poids de chaque sac
	int iterator;                               // iterateur qui permet de passer d un certificat a un autre

	
	public CertificatBinPack (PblBinPack _pb) {
		pb = _pb;
		repartition = new int [pb.getNb_objets()];
		sac = new int [pb.getNb_sacs()];
	}
	
	/**
	 * Methode qui remet le poids des sacs a zero.
	 */
	public void razSac() {
		int i = 0;
		for(i = 0; i < sac.length; i++) {
			sac[i] = 0;
		}
	}
	
	
	@Override
	public boolean isCorrect() {
		int i = 0;
		;
		for (i = 0; i < repartition.length; i++) {
			sac[repartition[i]] += pb.getPoids()[i];
		}
		for(i = 0; i < sac.length; i++) {
			if(sac[i] > pb.getCap()) {
				return false;
			}
		}
		return true;
	}

	
	@Override
	public void suivant() {
		
	}

	
	@Override
	public boolean estDernier() {
		return false;
	}

	
	@Override
	public void alea() {
		int i = 0;
		for(i = 0; i < pb.getNb_objets(); i++) {
			repartition[i] = (int) (Math.random() * pb.getNb_sacs() + 0);
		}
	}

	
	@Override
	public void afficheCertificat() {
		int i = 0;
		System.out.println("------ Affichage du certificat ------");
		System.out.println("Vous disposez de " + pb.getNb_sacs() + " de capacite " + pb.getCap());
		System.out.print("Liste des poids des objets : ");
		pb.afficherPoids();
		System.out.print("Repartition des objets dans les sacs : \n");
		for (i = 0; i < pb.getNb_objets(); i++) {
			System.out.println("Objet " + i + " dans le sac " + repartition[i]);
		}
		afficherSac();
	}

	
	/**
	 * Methode qui affiche les poids des differents sacs.
	 */
	private void afficherSac() {
		int i = 0;
		System.out.print("Tableau des sacs : [ " + sac[0]);
		for(i = 1; i < sac.length; i++) {
			System.out.print(" ," + sac[i]);
		}
		System.out.println(" ]");
	}


	@Override
	public void reset() {
		
	}

	
	@Override
	public void saisie() {
		repartition = new int [pb.getNb_objets()];
		System.out.println("------ Saisie du certificat ------");
		System.out.println("Les valeur acceptees sont comprises entre 0 et " + (pb.getNb_sacs() - 1));
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		int nbObjet = pb.getNb_objets();
		int i = 0;
		int a = 0;
		for (i = 0; i < nbObjet; i++) {
			System.out.println("Dans quel sac voulez-vous placer l objet " + i + " ?");
			a = sc.nextInt();
			if (a >= 0 && a < pb.getNb_sacs()) {
				repartition[i] = a;
			} else {
				System.out.println("Erreur de saisie, saisir un nouveau nombre");
				i--;
			}
		}
		System.out.println("Saisie du certificat terminee");
	}
}
