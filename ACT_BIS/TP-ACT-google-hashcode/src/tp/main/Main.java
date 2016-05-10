package tp.main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import tp.MIS.MISProblem;
import tp.MIS.Node;
import tp.io.FileOperation;
import tp.pb.Certificate;
import tp.pb.Part;
import tp.pb.Pizza;

public class Main {

	
	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		String pathFile = args[0];
		FileOperation fo = new FileOperation();
		Pizza pizza = fo.readFile(pathFile);
		System.out.println("height = " + pizza.getHeight());
		System.out.println("width = " + pizza.getWidth());
		System.out.println("min jambon = " + pizza.getNbJambonPerPart());
		System.out.println("max surface = " + pizza.getMaxSurfaceOfPart());
		
		ArrayList<Part> royalParts = pizza.generateAllParts();
		ArrayList<Part> royalPartsMixed = pizza.mixedRoyalParts(royalParts);
		System.out.println("nb parts possibles = " + royalPartsMixed.size());
		//for(Part part: royalPartsMixed) System.out.println(part.toString());
		
		/* With solutionAlea */
//		ArrayList<Part> solutionAlea = pizza.solutionAlea(royalPartsMixed);
//		System.out.println("Score: " + pizza.calcuScore());
		
		
		/* With sorted array of Part */
//		ArrayList<Part> bestSolution = pizza.greedySolutionLeftToRightAndTopToBottom(royalPartsMixed);
//		System.out.println("Score: " + pizza.calcuScore());
		
		/* Solution improvement */
//		System.out.println("Solution improvement !");
//		solutionAlea = pizza.getImprovedSolution(solutionAlea);
//		System.out.println("New score: " + pizza.calcuScore());
		
		/* Solution with greedy algo & list sorted */
		// Generate all royals parts
		ArrayList<Part> parts = pizza.generateAllParts();
		System.out.println("nb parts possibles = " + parts.size());
		
		// Use the pavage to get a solution
		ArrayList<Part> pavage_parts = pizza.pavageLeftToRightAndTopToBottom(parts);
		System.out.println("Score = " + pizza.calculScore());
		
		// Improve the solution
//		System.out.println("Solution improvement !");
//		pavage_parts = pizza.getImprovedSolution(pavage_parts);
//		System.out.println("New score: " + pizza.calculScore());

		
		/* Test heuristic */
		ArrayList<Part> heuristic = pizza.heuristicSolution(pavage_parts);
		System.out.println("New score: " + pizza.calculScore());
		System.out.println("Solution improvement !");
		heuristic = pizza.getImprovedSolution(heuristic);
		System.out.println("New score: " + pizza.calculScore());
		
		/* With Reduction in MIS */
//		MISProblem mis = pizza.redToMIS(royalPartsMixed);
//		ArrayList<Node> best_nodes_choices = mis.getBestChoices();
//		ArrayList<Part> best_parts_choices = new ArrayList<>();
//		for(Node node : best_nodes_choices){
//			System.out.println(node.toString());
//			pizza.fillBooleanPizzaTabWithPartToAdd(royalPartsMixed.get(node.getId()));
//			best_parts_choices.add(royalPartsMixed.get(node.getId()));
//		}
//		System.out.println("Score: " + pizza.calcuScore());
		Certificate cert = new Certificate(pizza, heuristic);
		fo.setCertificat(cert);
		fo.writeFile("data/Dhersin-Malapel-heuristic.txt");
	}
	
}
