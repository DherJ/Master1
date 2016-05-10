package comparaisons;

import io.EcritureFichier;

public class ComparaisonRefs {
	
	public static void main(String[] args) {
		// Les valeurs de x à tester pour tracer nos courbes
		int[] sizes = {1, 2,10,20,30,40,50,100,150,200,300,400,500,1000,1500,2000,5000,10000,25000,50000,75000,100000,250000,500000};
		FonctionsReference fr = new FonctionsReference(sizes);
		
		// Création des résultats
		double[][] results = new double[2][];
		results[0] = fr.puissance(1);
		results[1] = fr.logarithme();
		
		// Écriture du fichier de résultats
		EcritureFichier.output("data/comparaisons_refs.txt", results, sizes);
	}
	
}
