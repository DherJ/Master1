package hamiltonienPath;

import hamiltonienCycle.HamiltonienCycle;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;


/**
 * Classe qui permet de tester si le problème du chemin hamiltonien avec les données du fichier passé en paramètre à une solution <br>
 * 1. On récupère les données du fichier <br>
 * 2. On instancie le pb avec ces données <br>
 * 3. On réduit le problème du chemin hamiltonien dans le problème du circuit hamiltonien <br>
 * 4. On vérifie si le problème du circuit hamiltonien à une solution avec les données du problème du chemin hamiltonien
 * 
 * @author jérôme
 *
 */
public class TestHP {

	public static void main(String[] args) throws FileNotFoundException {
		//saisie du probleme
		if (args.length < 1)
		    System.out.println("java TestHP -mode data/file.ham");
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
		    HamiltonienPath hp = new HamiltonienPath(nbSommet, D);
		    System.out.println("nbSommet = " + nbSommet);
		    
		    
		    System.out.println();
		    System.out.println("-----------------------  Test de la réduction  -----------------------------");
		    HamiltonienCycle hc = (HamiltonienCycle) hp.red();
		    System.out.println("nbSommet = " + hc.getNbSommet());
		    
		    for(int i = 0; i < hc.getNbSommet(); i++) {
		    	for(int j = 0; j < hc.getNbSommet(); j++) {
			    	System.out.print(hc.getSommets()[i][j] + " ");
			    }
		    	System.out.println();
		    }
		    
		}
	}
}