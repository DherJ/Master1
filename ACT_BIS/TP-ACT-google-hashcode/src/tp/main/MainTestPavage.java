package tp.main;

import java.util.ArrayList;

import tp.io.FileOperation;
import tp.pb.Certificate;
import tp.pb.Part;
import tp.pb.Pizza;

public class MainTestPavage {

	static Pizza pizza;
	static ArrayList<Part> parts;
	static ArrayList<Part> pavage_parts;
	
	static void execGloutPavage(String pathFile) {
		// Get the problem from file
		FileOperation fo = new FileOperation();
		pizza = fo.readFile(pathFile);
		
		// Display info on the problem
		System.out.println("height = " + pizza.getHeight());
		System.out.println("width = " + pizza.getWidth());
		System.out.println("min jambon = " + pizza.getNbJambonPerPart());
		System.out.println("max surface = " + pizza.getMaxSurfaceOfPart());
		
		// Generate all royals parts
		parts = pizza.generateAllParts();
		System.out.println("nb parts possibles = " + parts.size());
		
		// Use the pavage to get a solution
		pavage_parts = pizza.pavageLeftToRightAndTopToBottom(parts);
		System.out.println("Score = " + pizza.calculScore());
		
		// Improve the solution
		System.out.println("Solution improvement !");
		pavage_parts = pizza.getImprovedSolution(pavage_parts);
		System.out.println("New score: " + pizza.calculScore());
		
		Certificate cert = new Certificate(pizza, pavage_parts);
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
	}
}
