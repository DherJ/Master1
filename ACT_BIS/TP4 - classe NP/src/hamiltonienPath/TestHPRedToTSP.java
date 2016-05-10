package hamiltonienPath;

import hamiltonienCycle.HamiltonienCycle;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

import classesPb.NP;
import travellingSalemansProblem.TSP;


/**
 * Classe qui permet de tester si le probl�me du chemin hamiltonien avec les donn�es du fichier pass� en param�tre � une solution
 * en le r�duisant dans le probl�me du circuit hamiltonien puis dans le probl�me TSP <br>
 * 1. On r�cup�re les donn�es du fichier <br>
 * 2. On instancie le pb avec ces donn�es <br>
 * 3. On r�duit le probl�me du chemin hamiltonien dans le probl�me circuit hamiltonien puis dans le probl�me TSP <br>
 * 4. On v�rifie si le probl�me TSP � une solution avec les donn�es du probl�me du chemin hamiltonien
 * 
 * @author j�r�me
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
		    System.out.println("---------  Test de la r�duction : r�duction de chemin hamiltonien vers circuit hamiltonien --------");
		    NP hc = ((HamiltonienPath) hp).red();
		    System.out.println("nbSommet = " + ((HamiltonienCycle) hc).getNbSommet());
		    
		    for(int i = 0; i < ((HamiltonienCycle) hc).getNbSommet(); i++) {
		    	for(int j = 0; j < ((HamiltonienCycle) hc).getNbSommet(); j++) {
			    	System.out.print(((HamiltonienCycle) hc).getSommets()[i][j] + " ");
			    }
		    	System.out.println();
		    }
		    
		    System.out.println("\n---------  Test de la r�duction : r�duction de circuit hamiltonien vers TSP --------");
		    TSP tsp = (TSP) ((HamiltonienCycle) hc).red();
		    System.out.println("nbSommet = " + tsp.nbVilles);
		    for(int i = 0; i < tsp.nbVilles; i++) {
		    	for(int j = 0; j < tsp.nbVilles; j++) {
			    	System.out.print(tsp.distances[i][j] + " ");
			    }
		    	System.out.println();
		    }
		    System.out.println("Le probl�me Hamilton Path a une solution? " + tsp.aUneSolution());
		}
	}
}