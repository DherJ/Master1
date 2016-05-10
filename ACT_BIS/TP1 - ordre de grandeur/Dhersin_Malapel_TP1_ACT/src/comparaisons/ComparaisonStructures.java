package comparaisons;

import datastructures.Liste;
import datastructures.Tableau;
import io.EcritureFichier;

public class ComparaisonStructures {

	Object[] nameFunctions;
	private int nbExe;
	int[] sizes = {2,10,20,30,40,50,100,150,200,300,400,500,1000,1500,2000,5000,10000,25000,50000,75000,100000,250000,500000};;
	
	public void setListFunctions(Object[] _nameFunctions) {
		this.nameFunctions = _nameFunctions;
	}
	
	public void setNbExec(int nb) {
		this.nbExe = nb;
	}
	
	public int[] getSizes() {
		return this.sizes;
	}
	
	public static void main(String[] args) {
		// Les valeurs de x à tester pour tracer nos courbes
		int nbExe = 2;
		int[] sizes = {2,10,20,30,40,50,100,150,200,300,400,500,1000,1500,2000,5000,10000,25000,50000,75000,100000,250000,500000};
		//double[] results = new double[sizes.length];
		//double[][] results = new double[6][sizes.length];
		double[][] results_alea = new double[6][sizes.length];
		double[][] results_simple = new double[6][sizes.length];
		double[][] results_tri = new double[6][sizes.length];
		double[][] results_rech = new double[6][sizes.length];
		double[][] results_myst = new double[6][sizes.length];
		double[][] tmp = new double[nbExe][sizes.length];
		//double[] tmp = new double[sizes.length];
		
		//ArrayList<double[]> calcul = new ArrayList<double[]>();
		
		
		/* ---------------------------------------------------------------------------------------------------------- */
		// Création d'une courbe correspondant à la moyenne des exécutions de la fonction mesureAlea()
		for(int i=0; i<nbExe; i++) {
			for (int idx=0 ; idx<sizes.length ; idx++) {
				Tableau l = new Tableau(sizes[idx]);
				tmp[i][idx] = Mesures.mesureAlea(l);
			}
		}
		results_alea[0] = getMin(tmp);
		results_alea[1] = getMoyenne(tmp);
		results_alea[2] = getMax(tmp);
		
		for(int i=0; i<nbExe; i++) {
			for (int idx=0 ; idx<sizes.length ; idx++) {
				Liste list = new Liste(sizes[idx]);
				tmp[i][idx] = Mesures.mesureAlea(list);
			}
		}
		results_alea[3] = getMin(tmp);
		results_alea[4] = getMoyenne(tmp);
		results_alea[5] = getMax(tmp);
		
		/* ---------------------------------------------------------------------------------------------------------- */
		// Création d'une courbe correspondant à la moyenne des exécutions de la fonction mesureMinimumSimple()
		for(int i=0; i<nbExe; i++) {
			for (int idx=0 ; idx<sizes.length ; idx++) {
				Tableau l = new Tableau(sizes[idx]);
				tmp[i][idx] = Mesures.mesureMinimumSimple(l);
			}
		}
		results_simple[0] = getMin(tmp);
		results_simple[1] = getMoyenne(tmp);
		results_simple[2] = getMax(tmp);
		
		for(int i=0; i<nbExe; i++) {
			for (int idx=0 ; idx<sizes.length ; idx++) {
				Liste list = new Liste(sizes[idx]);
				tmp[i][idx] = Mesures.mesureMinimumSimple(list);
			}
		}
		results_simple[3] = getMin(tmp);
		results_simple[4] = getMoyenne(tmp);
		results_simple[5] = getMax(tmp);
		
		/* ---------------------------------------------------------------------------------------------------------- */
		// Création d'une courbe correspondant à la moyenne des exécutions de la fonction mesureMinimumTri()
		for(int i=0; i<nbExe; i++) {
			for (int idx=0 ; idx<sizes.length ; idx++) {
				Tableau l = new Tableau(sizes[idx]);
				tmp[i][idx] = Mesures.mesureMinimumTri(l);
			}
		}
		results_tri[0] = getMin(tmp);
		results_tri[1] = getMoyenne(tmp);
		results_tri[2] = getMax(tmp);
		
		for(int i=0; i<nbExe; i++) {
			for (int idx=0 ; idx<sizes.length ; idx++) {
				Liste list = new Liste(sizes[idx]);
				tmp[i][idx] = Mesures.mesureMinimumTri(list);
			}
		}
		results_tri[3] = getMin(tmp);
		results_tri[4] = getMoyenne(tmp);
		results_tri[5] = getMax(tmp);
		
		/* ---------------------------------------------------------------------------------------------------------- */
		// Création d'une courbe correspondant à la moyenne des exécutions de la fonction mesureRecherche()
		int search_val = 250000;
		for(int i=0; i<nbExe; i++) {
			for (int idx=0 ; idx<sizes.length ; idx++) {
				Tableau l = new Tableau(sizes[idx]);
				tmp[i][idx] = Mesures.mesureRecherche(l, search_val);
			}
		}
		results_rech[0] = getMin(tmp);
		results_rech[1] = getMoyenne(tmp);
		results_rech[2] = getMax(tmp);
		
		for(int i=0; i<nbExe; i++) {
			for (int idx=0 ; idx<sizes.length ; idx++) {
				Liste list = new Liste(sizes[idx]);
				tmp[i][idx] = Mesures.mesureRecherche(list, search_val);
			}
		}
		results_rech[3] = getMin(tmp);
		results_rech[4] = getMoyenne(tmp);
		results_rech[5] = getMax(tmp);
		
		/* ---------------------------------------------------------------------------------------------------------- */
		// Création d'une courbe correspondant à la moyenne des exécutions de la fonction mesureMyst()
		/*
		System.out.println("execution de mystere sur les tableaux en cours ... c'est très long :( ...");
		for(int i=0; i<nbExe; i++) {
			for (int idx=0 ; idx<sizes.length ; idx++) {
				Tableau l = new Tableau(sizes[idx]);
				tmp[i][idx] = Mesures.mesureMystere(l);
			}
		}
		results_myst[0] = getMin(tmp);
		results_myst[1] = getMoyenne(tmp);
		results_myst[2] = getMax(tmp);
		
		System.out.println("execution de mystere sur les listes en cours ... c'est très long :( ...");
		for(int i=0; i<nbExe; i++) {
			for (int idx=0 ; idx<sizes.length ; idx++) {
				Liste list = new Liste(sizes[idx]);
				tmp[i][idx] = Mesures.mesureMystere(list);
			}
		}
		results_myst[3] = getMin(tmp);
		results_myst[4] = getMoyenne(tmp);
		results_myst[5] = getMax(tmp);
		*/
		
		/* ---------------------------------------------------------------------------------------------------------- */
		// Écriture du fichier de résultats
		//EcritureFichier.output("data/alea_tableau_1.txt", getMoyenne(calcul), sizes);
		EcritureFichier.output("data/mesuresAlea.txt", results_alea, sizes);
		EcritureFichier.output("data/mesureSimple.txt", results_simple, sizes);
		EcritureFichier.output("data/mesureTri.txt", results_tri, sizes);
		EcritureFichier.output("data/mesureRecherche.txt", results_rech, sizes);
		/*
		EcritureFichier.output("data/mesureMystere.txt", results_myst, sizes);
		*/
	}
	
	
	public void generateData(String pathFile, String nameFile, String nameFunction, int[] sizes, int searchValue) {
		double[][] results = new double[6][sizes.length];
		double[][] tmp = new double[nbExe][sizes.length];
		if(nameFunction.equals(this.nameFunctions[0])) {
			for(int i=0; i<this.nbExe; i++) {
				for (int idx=0 ; idx<sizes.length ; idx++) {
					Tableau l = new Tableau(sizes[idx]);
					tmp[i][idx] = Mesures.mesureAlea(l);
				}
			}
			results[0] = getMin(tmp);
			results[1] = getMoyenne(tmp);
			results[2] = getMax(tmp);
			for(int i=0; i<this.nbExe; i++) {
				for (int idx=0 ; idx<sizes.length ; idx++) {
					Liste list = new Liste(sizes[idx]);
					tmp[i][idx] = Mesures.mesureAlea(list);
				}
			}
			results[3] = getMin(tmp);
			results[4] = getMoyenne(tmp);
			results[5] = getMax(tmp);
		} else if(nameFunction.equals(this.nameFunctions[1])) {
			for(int i=0; i<this.nbExe; i++) {
				for (int idx=0 ; idx<sizes.length ; idx++) {
					Tableau l = new Tableau(sizes[idx]);
					tmp[i][idx] = Mesures.mesureMinimumSimple(l);
				}
			}
			results[0] = getMin(tmp);
			results[1] = getMoyenne(tmp);
			results[2] = getMax(tmp);
			for(int i=0; i<this.nbExe; i++) {
				for (int idx=0 ; idx<sizes.length ; idx++) {
					Liste list = new Liste(sizes[idx]);
					tmp[i][idx] = Mesures.mesureMinimumSimple(list);
				}
			}
			results[3] = getMin(tmp);
			results[4] = getMoyenne(tmp);
			results[5] = getMax(tmp);
		} else if(nameFunction.equals(this.nameFunctions[2])) {
			for(int i=0; i<this.nbExe; i++) {
				for (int idx=0 ; idx<sizes.length ; idx++) {
					Tableau l = new Tableau(sizes[idx]);
					tmp[i][idx] = Mesures.mesureMinimumTri(l);
				}
			}
			results[0] = getMin(tmp);
			results[1] = getMoyenne(tmp);
			results[2] = getMax(tmp);
			for(int i=0; i<nbExe; i++) {
				for (int idx=0 ; idx<sizes.length ; idx++) {
					Liste list = new Liste(sizes[idx]);
					tmp[i][idx] = Mesures.mesureMinimumTri(list);
				}
			}
			results[3] = getMin(tmp);
			results[4] = getMoyenne(tmp);
			results[5] = getMax(tmp);
		} else if(nameFunction.equals(this.nameFunctions[3])) {
			for(int i=0; i<this.nbExe; i++) {
				for (int idx=0 ; idx<sizes.length ; idx++) {
					Tableau l = new Tableau(sizes[idx]);
					tmp[i][idx] = Mesures.mesureRecherche(l, searchValue);
				}
			}
			results[0] = getMin(tmp);
			results[1] = getMoyenne(tmp);
			results[2] = getMax(tmp);
			for(int i=0; i<this.nbExe; i++) {
				for (int idx=0 ; idx<sizes.length ; idx++) {
					Liste list = new Liste(sizes[idx]);
					tmp[i][idx] = Mesures.mesureRecherche(list, searchValue);
				}
			}
			results[3] = getMin(tmp);
			results[4] = getMoyenne(tmp);
			results[5] = getMax(tmp);
		} else if(nameFunction.equals(this.nameFunctions[4])) {
			for(int i=0; i<this.nbExe; i++) {
				for (int idx=0 ; idx<sizes.length ; idx++) {
					Tableau l = new Tableau(sizes[idx]);
					tmp[i][idx] = Mesures.mesureMystere(l);
				}
			}
			results[0] = getMin(tmp);
			results[1] = getMoyenne(tmp);
			results[2] = getMax(tmp);
			for(int i=0; i<this.nbExe; i++) {
				for (int idx=0 ; idx<sizes.length ; idx++) {
					Liste list = new Liste(sizes[idx]);
					tmp[i][idx] = Mesures.mesureMystere(list);
				}
			}
			results[3] = getMin(tmp);
			results[4] = getMoyenne(tmp);
			results[5] = getMax(tmp);
		}
		EcritureFichier.output(pathFile + "/" + nameFile + ".txt", results, sizes);
	}
	
	public static double[] getMoyenne(double[][] tab) {
		double[] moyenne = new double[tab[1].length];
		double calcul;
		for(int i=0; i<tab[1].length; i++){ // pour chaque ligne
			calcul = 0;
			for(int j=0; j<tab.length; j++){ // pour chaque colonne
				calcul += tab[j][i];
			}
			moyenne[i] = calcul / tab.length;
		}
		return moyenne;
	}
	
	
	public static double[] getMin(double[][] tab) {
		double[] min = new double[tab[1].length];
		System.out.println(tab[1].length);
		System.out.println(tab.length);
		double test;
		for(int i=0; i<tab[1].length; i++) {
			test = tab[0][i];
			for(int j=0; j<tab.length; j++) {
				if(tab[j][i] < test ) {
					test = tab[j][i];
				}
			}
			min[i] = test;
		}
		return min;
	}
	
	public static double[] getMax(double[][] tab) {
		double[] max = new double[tab[1].length];
		double test;
		for(int i=0; i<tab[1].length; i++) {
			test = tab[0][i];
			for(int j=0; j<tab.length; j++) {
				if(tab[j][i] > test ) {
					test = tab[j][i];
				}
			}
			max[i] = test;
		}
		return max;
	}
}