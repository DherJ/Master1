package comparaisons;

import io.EcritureFichier;

public class ComparaisonRefs {
	
	public static void main(String[] args) {
		// Les valeurs de x à tester pour tracer nos courbes
		int[] sizes = {1, 2,10,20,30,40,50,100,150,200,300,400,500,1000,1500,2000,5000,10000,25000,50000,75000,100000,250000,500000};
		@SuppressWarnings("unused")
		FonctionsReference fr = new FonctionsReference(sizes);
		
		// Création des résultats
		double[][] results = new double[7][];
		//results[0] = fr.puissance(1);
		//results[1] = fr.logarithme();
//		results[0] = fr.nlog();
//		results[1] = fr.exp();
//		results[2] = fr.kPowN(2);
//		results[3] = fr.fctFacto();
//		results[4] = fr.nPowN();
//		results[0] = logDivN(sizes, 1);
//		results[1] = logDivN(sizes, 0.5);
//		results[2] = logDivN(sizes, 0.2);
//		results[3] = logDivN(sizes, 0.1);
		results[0] = Q132(sizes, 1);
		results[1] = Q132(sizes, 2);
		results[2] = Q132(sizes, 3);
		results[3] = Q132(sizes, 4);
		results[4] = Q132(sizes, 5);
		results[5] = Q132(sizes, 6);
		results[6] = Q132(sizes, 10);
		
		// Écriture du fichier de résultats	
		EcritureFichier.output("data/comparaisons_refs.txt", results, sizes);
	}
	
	
	public static double[] logDivN(int[] sizes, double e){
		double[] values = new double[sizes.length];
		
		for(int i=0; i<sizes.length; i++){
			values[i] = Math.log(sizes[i]) / Math.pow(sizes[i], e);
		}
		
		return values;
	}
	
	
	public static double[] Q132(int[] sizes, double k){
		double[] values = new double[sizes.length];
		
		for(int i=0; i<sizes.length; i++){
			values[i] = Math.pow(sizes[i], k) / Math.pow(2, sizes[i]);
		}
		
		return values;
	}
}