package tp.main;

import java.util.ArrayList;

import tp.io.FileOperation;
import tp.pb.Part;
import tp.pb.Pizza;

public class FonctionAlea {

	public ArrayList<Part> allParts;
	public ArrayList<Pizza> pizzas;
	public ArrayList<ArrayList<Part>> parts;
	public String pathFile;
	public int nbExec;
	

	public FonctionAlea(String pathFile, ArrayList<Part> allParts, int nbExec) {
		this.allParts = allParts;
		pizzas = new ArrayList<Pizza>();
		parts = new ArrayList<ArrayList<Part>>();
		this.pathFile = pathFile;
		this.nbExec = nbExec;
		execAlea(this.nbExec);
	}
	
	public void printAllScores() {
		for(int i = 0; i < this.pizzas.size(); i++) {
			System.out.println("Score " + i + " = " + this.pizzas.get(i).calculScore());
		}
	}
	
	ArrayList<Part> getMaxSol() {
		ArrayList<Part> res = new ArrayList<Part>();
		int max = this.pizzas.get(0).calculScore();
		int indMax = 0;
		for(int i = 0; i < this.pizzas.size(); i++) {
			if(pizzas.get(i).calculScore() > max) {
				indMax = i;
				max = pizzas.get(i).calculScore();
			}
		}
		res = parts.get(indMax);
		System.out.println("Score max = " + max);
		System.out.println("size parts = " + res.size());
		return res;
	}
	
	public void execAlea(int nbExec) {
		int i = 0;
		while(i < nbExec) {
			// Get the problem from the file 
			FileOperation fo = new FileOperation();
			Pizza pizza = fo.readFile(pathFile);
			pizza.setAllParts(this.allParts);
			ArrayList<Part> partsTrieeAleaTmp = null;
			ArrayList<Part> partsSolAleaTmp = null;
			System.out.println("exec : " + i);
			// Mixed the parts in the ArrayList
			partsTrieeAleaTmp = pizza.mixedRoyalParts(pizza.getAllParts());
			// Get a configuration of royal parts
			partsSolAleaTmp = pizza.solutionAlea(partsTrieeAleaTmp);
			// Improve the last solution if possible
			partsSolAleaTmp = pizza.getImprovedSolution(partsSolAleaTmp);
			this.parts.add(partsSolAleaTmp);
			this.pizzas.add(pizza);
			i++;
		}
	}
	
	
	
	public ArrayList<Part> getAllParts() {
		return allParts;
	}
	
	public void setAllParts(ArrayList<Part> allParts) {
		this.allParts = allParts;
	}
	
	public int getNbExec() {
		return nbExec;
	}

	public void setNbExec(int nbExec) {
		this.nbExec = nbExec;
	}
}
