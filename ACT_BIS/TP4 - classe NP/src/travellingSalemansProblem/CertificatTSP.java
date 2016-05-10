package travellingSalemansProblem;

import java.util.Scanner;

import classesPb.*;

public class CertificatTSP implements Certificat {
	
	public TSP instancePb;
	private int[] listVilles;
	private int dist_parcourue;
	
	public CertificatTSP(TSP instance) {
		this.instancePb = instance;
		listVilles = new int[instancePb.nbVilles];
	}
	
	@Override
	public void saisie() {
		Scanner sc = new Scanner(System.in);
		int val = -1;
		System.out.println("\n----------- Saisie du certificat -----------");
		for(int i = 0; i < instancePb.nbVilles; i++) {
			System.out.print("Saisir un entier compris entre 0 et " + instancePb.nbVilles + ", saisir -1 pour terminer :");
			val = sc.nextInt();
			if(val == -1) {   // si on saisie -1 on marque la fin de la saisie du certificat et on met toute les case suivantes à -1
				for(int j = i; j < instancePb.nbVilles; j++) {
					listVilles[j] = -1;
				}
				break;
			}
			else listVilles[i] = val;
		}
		sc.close();
	}

	@Override
	public void display() {
		System.out.print("Villes visitées : [ ");
		for(int i = 0; i < instancePb.nbVilles - 1; i++) {
			if(listVilles[i] != -1) {
				System.out.print(listVilles[i] + " - ");
			}
		}
		System.out.println(listVilles[instancePb.nbVilles - 1] + " ]");
		System.out.println("Distance parcourue : " + dist_parcourue);
	}

	@Override
	public void alea() {
		for(int i = 0; i < instancePb.nbVilles; i++) {
			listVilles[i] = (int) (Math.random() * (instancePb.nbVilles - 1));
		}
	}

	@Override
	public void reset() {
		for(int i = 0; i < instancePb.nbVilles; i++) {
			listVilles[i] = -1;
		}
	}

	@Override
	public boolean estDernier() {
		for(int i = 0; i < instancePb.nbVilles; i++) {
			if(listVilles[i] != (instancePb.nbVilles - 1)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void suivant() {
		for(int i = (instancePb.nbVilles - 1); i >= 0; i--) {
			if(listVilles[i] < instancePb.nbVilles - 1) {
				listVilles[i]++;
				break;
			}
			else {
				if(i > 0 && (listVilles[i - 1] < instancePb.nbVilles - 1)) {
					listVilles[i - 1]++;
					for(int j = i; j < instancePb.nbVilles; j++) {
						listVilles[j] = 0;
					}
					break;
				} 
			}
		}
	}
	
	
	public boolean estCorrect() {
		int longeur = 0;
		int i = 0;
		int listVilles [] = this.getListVilles();
		boolean visitedVilles[] = new boolean[listVilles.length];
		if(listVilles.length > this.getInstancePb().nbVilles)
			return false;
		else {
			int last = 0;
			// recherche de doublon
			for(i = 0; i < listVilles.length; i++) {
				if(listVilles[i] != -1) {
					if(visitedVilles[listVilles[i]] == true) {  // la ville a déjà été visitée
						 return false;
					}
					else visitedVilles[listVilles[i]] = true;  // on marque la ville comme étant visitée
				} else {
					last = i - 1;  // récupère la dernière valeur valable (!= -1) du tableau des villes visitées
					break;  // on sort de la boucle car plus de valeur valable à vérifier
				}
			}
			// vérification de la longeur
			for(i = 0; i < listVilles.length - 1; i++) {
				if(listVilles[i] != -1 && listVilles[i + 1] != -1) {
					longeur += instancePb.distances[listVilles[i]][listVilles[i + 1]];
					if(longeur > instancePb.longueurTournee)
						return false;
				} else break;
			}
			longeur += instancePb.distances[last][listVilles[0]];  // récupère la distance de la dernière ville avec celle de départ
			if(longeur > instancePb.longueurTournee)
				   return false;
		}
		this.setDist_parcourue(longeur);
		return true;
	}
	
	
	/** Getters & Setters **/
	public TSP getInstancePb() {
		return instancePb;
	}

	public int[] getListVilles() {
		return listVilles;
	}
	
	public int getDist_parcourue() {
		return dist_parcourue;
	}

	public void setDist_parcourue(int dist_parcourue) {
		this.dist_parcourue = dist_parcourue;
	}
}