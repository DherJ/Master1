package hamiltonienPath;

import hamiltonienCycle.HamiltonienCycle;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

import classesPb.NP;
import travellingSalemansProblem.TSP;


/**
 * Classe qui permet de tester si le problème du chemin hamiltonien avec les données du fichier passé en paramètre à une solution
 * en le réduisant dans le problème du circuit hamiltonien puis dans le problème TSP <br>
 * 1. On récupère les données du fichier <br>
 * 2. On instancie le pb avec ces données <br>
 * 3. On réduit le problème du chemin hamiltonien dans le problème circuit hamiltonien puis dans le problème TSP <br>
 * 4. On vérifie si le problème TSP à une solution avec les données du problème du chemin hamiltonien
 * 
 * @author jérôme
 *
 */
public class TestHPRedToTSP {

	public static void main(String[] args) throws FileNotFoundException {
		//saisie du probleme
		if (args.length < 1)
		    System.out.println("java TestHPRedToTSP data/file.ham");
		else {
		    //le probleme dans un fichier de donnees
		    Scanner donnee = new Scanner (new FileReader(args[0]));
		    for (int i=0; i<3; i++) donnee.nextLine();
		    donnee.next();
		    int nbSommet = donnee.nextInt();	 
		    for (int i=0; i<4; i++) donnee.nextLine();
		    boolean D[][]=new boolean[nbSommet][nbSommet];
		    String val = new String();
		    System.out.println("nbSommet = " + nbSommet);
			for (int i=0; i<nbSommet; i++) {
			   for (int j=0; j<nbSommet; j++) {
				    val = donnee.next();
				    if("True".equals(val) || "true".equals(val))
				    	D[i][j] = true;
				    else D[i][j] = false;
				    System.out.print(D[i][j] + " ");
			    }
			    System.out.println();
			}
		    donnee.close();
		    NP hp = new HamiltonienPath(nbSommet, D);
		    
		    System.out.println();
		    System.out.println("---------  Test de la réduction : réduction de chemin hamiltonien vers circuit hamiltonien --------");
		    NP hc = ((HamiltonienPath) hp).red();
		    System.out.println("nbSommet = " + ((HamiltonienCycle) hc).getNbSommet());
		    
		    for(int i = 0; i < ((HamiltonienCycle) hc).getNbSommet(); i++) {
		    	for(int j = 0; j < ((HamiltonienCycle) hc).getNbSommet(); j++) {
			    	System.out.print(((HamiltonienCycle) hc).getSommets()[i][j] + " ");
			    }
		    	System.out.println();
		    }
		    
		    System.out.println("\n---------  Test de la réduction : réduction de circuit hamiltonien vers TSP --------");
		    TSP tsp = (TSP) ((HamiltonienCycle) hc).red();
		    System.out.println("nbSommet = " + tsp.nbVilles);
		    for(int i = 0; i < tsp.nbVilles; i++) {
		    	for(int j = 0; j < tsp.nbVilles; j++) {
			    	System.out.print(tsp.distances[i][j] + " ");
			    }
		    	System.out.println();
		    }
		    System.out.println("Le problème Hamilton Path a une solution? " + tsp.aUneSolution());
		}
	}
}