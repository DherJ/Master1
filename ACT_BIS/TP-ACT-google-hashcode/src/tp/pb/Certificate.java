package tp.pb;

import java.util.ArrayList;
import java.util.Random;

/**
 * Class which is a certificate of the pizza problem.
 */
public class Certificate {
	private ArrayList<Part> royalParts, royalPartsMixed;
	private Pizza pizza_problem;
	
	
	/** Default Constructor **/
	public Certificate(){
		this.royalParts = new ArrayList<Part>();
	}
	
	/** Constructor **/
	public Certificate(Pizza _pizza_problem) {
		pizza_problem = _pizza_problem;
		royalParts = new ArrayList<Part>();
	}
	
	public Certificate(Pizza _pizza_problem, ArrayList<Part> _royalParts) {
		pizza_problem = _pizza_problem;
		royalParts = _royalParts;
	}
	
	public void addPart(Part _part) {
		royalParts.add(_part);
	}
	
	public void removePart(Part _part) {
		royalParts.remove(_part);
	}
	
	public void displayRoyalParts() {
		for(Part part : royalParts) {
			System.out.println(part.toString());
		}
	}
	
	public boolean isCertificateCorrect(){
		int nbJambon = 0, surfaceParts = 0, surfacePart = 0;
		for(Part part : this.royalParts) {
			// Check if the surface is Okay & if the part is not a square
			if(part.getSurface() > pizza_problem.getMaxSurfaceOfPart() || part.isSquare())
				return false;

			for(int i = part.getColonne(); i < part.getColonneEnd(); i++) {
				for(int j = part.getLigne(); j < part.getLigneEnd(); j++) {
					if(pizza_problem.getPizza()[i][j] == 'H')
						nbJambon++;
				}
			}
			
			// Check if the number of ham is okay
			if(nbJambon < pizza_problem.getNbJambonPerPart())
				return false;
			
			nbJambon = 0;
			surfaceParts += surfacePart;
			
			// Check if the max surface of each part is Okay
			if(surfaceParts > pizza_problem.getMaxSurfaceOfPart())
				return false;
		}
		return true;
	}

	public ArrayList<Part> getRoyalParts() {
		return royalParts;
	}

	public void setRoyalParts(ArrayList<Part> royalParts) {
		this.royalParts = royalParts;
	}

	public Pizza getPizza_problem() {
		return pizza_problem;
	}

	public void setPizza_problem(Pizza pizza_problem) {
		this.pizza_problem = pizza_problem;
	}
	
	
	
	public ArrayList<Part> getRoyalPartsMixed() {
		return royalPartsMixed;
	}

	public void setRoyalPartsMixed(ArrayList<Part> royalPartsMixed) {
		this.royalPartsMixed = royalPartsMixed;
	}
	
	
	
}
