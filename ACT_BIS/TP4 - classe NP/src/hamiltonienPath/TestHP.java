package hamiltonienPath;

import hamiltonienCycle.HamiltonienCycle;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;


/**
 * Classe qui permet de tester si le probl�me du chemin hamiltonien avec les donn�es du fichier pass� en param�tre � une solution <br>
 * 1. On r�cup�re les donn�es du fichier <br>
 * 2. On instancie le pb avec ces donn�es <br>
 * 3. On r�duit le probl�me du chemin hamiltonien dans le probl�me du circuit hamiltonien <br>
 * 4. On v�rifie si le probl�me du circuit hamiltonien � une solution avec les donn�es du probl�me du chemin hamiltonien
 * 
 * @author j�r�me
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
		    System.out.println("-----------------------  Test de la r�duction  -----------------------------");
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