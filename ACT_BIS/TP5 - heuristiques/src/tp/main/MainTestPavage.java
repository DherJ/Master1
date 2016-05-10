package tp.main;

import java.util.ArrayList;
import tp.io.FileOperation;
import tp.pb.Certificat;
import tp.pb.Part;
import tp.pb.Pizza;

public class MainTestPavage {

	static Pizza pizza;
	static ArrayList<Part> parts;
	static ArrayList<Part> partsGlout;
	
	static void execGloutPavage(String pathFile) {
		FileOperation fo = new FileOperation();
		pizza = fo.readFile(pathFile);
		
		System.out.println("height = " + pizza.getHeight());
		System.out.println("width = " + pizza.getWidth());
		System.out.println("min jambon = " + pizza.getnJambon());
		System.out.println("max surface = " + pizza.getMaxSurface());
		
		parts = pizza.generateAllParts();
		System.out.println("nb parts possibles = " + parts.size());
		partsGlout = pizza.solutionGloutonneGaucheDroiteHautBas(parts);
		System.out.println("Score = " + pizza.calculScore());
		Certificat cert = new Certificat(pizza, partsGlout);
		fo.setCertificat(cert);
		fo.writeFile("data/Dhersin-Malapel-glout.txt");
	}
	
	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		String pathFile = args[0];
		
		/* Test solution gloutonne gauche à droite, haut en bas */
		execGloutPavage(pathFile);
		
		long endTime = System.currentTimeMillis();
		System.out.println("execution in : " + (endTime - startTime) * (10 * Math.pow(10,  -6)) + " s");
		/** Test réduction **/
		//MISProblem mis = new MISProblem(pizza);
		//mis = pizza.redToMIS(parts);
		//System.out.println(mis.getInstPizza().getHeight());
		//mis.printGraphe();
		//System.out.println(mis.getListOfIndepSommet().size());
		//System.out.println(mis.getGraphe()[1][1].toString());
	}
}