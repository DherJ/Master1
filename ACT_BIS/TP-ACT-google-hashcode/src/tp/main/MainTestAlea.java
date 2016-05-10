package tp.main;

import java.util.ArrayList;

import tp.io.FileOperation;
import tp.pb.Certificate;
import tp.pb.Part;
import tp.pb.Pizza;

public class MainTestAlea {

	public static void main(String[] args) {
		System.out.println("Start the generation of random solutions...");
		FileOperation fo = new FileOperation();
		String pathFile = args[0];
		
		// Get the problem from file
		Pizza pizza;
		pizza = fo.readFile(pathFile);
		
		/* Test random solution */
		ArrayList<Part> allParts = null;
		if(allParts == null)
			allParts = pizza.generateAllParts();
		
		long startTime = System.currentTimeMillis();
		int nbExec = 1000;
		
		FonctionAlea fAlea = new FonctionAlea(pathFile, allParts, nbExec);
		
		long endTime = System.currentTimeMillis();
		
		ArrayList<Part> maxParts = fAlea.getMaxSol();
		
		// Create the certificate to generate the final file
		Certificate cert = new Certificate(pizza, maxParts);
		fo.setCertificat(cert);
		fo.writeFile("data/Dhersin-Malapel-alea.txt");
		
		System.out.println("exec in " + ((endTime - startTime) * (Math.pow(10,  -3))) + " s");
	}	
}
