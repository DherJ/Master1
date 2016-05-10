package hamiltonienCycle;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

import travellingSalemansProblem.CertificatTSP;
import travellingSalemansProblem.TSP;

/**
 * Classe qui permet de tester si le problème du cycle hamiltonien avec les données du fichier passé en paramètre à une solution <br>
 * 1. On récupère les données du fichier <br>
 * 2. On instancie le pb avec ces données <br>
 * 3. On réduit le problème du circuit hamiltonien dans le problème TSP <br>
 * 4. On vérifie si le problème TSP à une solution avec les données du problème du circuit hamiltonien
 * 
 * @author jérôme
 *
 */
public class TestHC {
	
	public static void main(String[] args) throws FileNotFoundException {
		//saisie du probleme
		if (args.length < 1)
		    System.out.println("java TestHC data/file.ham");
		else {
		    //le probleme dans un fichier de donnees
		    Scanner donnee = new Scanner (new FileReader(args[1]));
		    for (int i=0; i<3; i++) donnee.nextLine();
		    donnee.next();
		    int nbSommet = donnee.nextInt();	 
		    for (int i=0; i<4; i++) donnee.nextLine();
		    boolean D[][]=new boolean[nbSommet][nbSommet];
		    String val = new String();
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
		    HamiltonienCycle pb = new HamiltonienCycle(nbSommet,D);
		    System.out.println("nbSommet = " + nbSommet);
		    
		    
		    System.out.println();
		    System.out.println("-----------------------  Test de la réduction  -----------------------------");
		    TSP tsp = (TSP) pb.red();
		    System.out.println("nbVilles = " + tsp.nbVilles);
		    System.out.println("longeur max = " + tsp.longueurTournee);
		    
		    for(int i = 0; i < tsp.nbVilles; i++) {
		    	for(int j = 0; j < tsp.nbVilles; j++) {
			    	System.out.print(tsp.distances[i][j] + " ");
			    }
		    	System.out.println();
		    }
		    
		    // les differents modes
		    if (args[0].equals("-verif")) { 
		    	CertificatTSP c = tsp.cert();
		    	System.out.print("Proposez un certificat;");
		    	c.saisie();
		    	System.out.print("votre certificat est-il correct? ");
		    	System.out.println(tsp.estCorrect(c));
		    	if(pb.estCorrect(c))
		    		c.display();
		    }
		    else if (args[0].equals("-nondet")) {
		        	System.out.println(tsp.aUneSolutionNonDeterministe());
		        }
		    else if (args[0].equals("-exhaust"))  { 
		    		System.out.println("le probleme a-t-il une solution?: ");
		    		System.out.println(tsp.aUneSolution());
		    	}
		    else
		    	System.out.println("erreur de mode");
		}
	}
}