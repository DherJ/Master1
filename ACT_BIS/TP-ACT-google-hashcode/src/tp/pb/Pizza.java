package tp.pb;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import tp.MIS.MISProblem;
import tp.MIS.Node;

/** 
 * Class which is the instance of the pizza problem
 */
public class Pizza {

	private int height;
	private int width;
	private int maxSurfaceOfPart;
	private int nbJambonPerPart;
	private char[][] pizza;
	private boolean[][] boolean_pizza;
	private ArrayList<Part> allParts;
	
	
	/** Default Constructor **/
	public Pizza() {}
	
	/** Constructor **/
	public Pizza(int _height, int _width, int _maxSurfaceOfPart, int _nbJambonPerPart, char[][] _pizza) {
		height = _height;
		width = _width;
		maxSurfaceOfPart = _maxSurfaceOfPart;
		nbJambonPerPart = _nbJambonPerPart;
		pizza = _pizza;
		initFalseBooleanPizzaTab();
	}

	public void initFalseBooleanPizzaTab(){
		boolean_pizza = new boolean[height][width];
		for(int i=0; i<height; i++){
			for(int j=0; j<width; j++){
				boolean_pizza[i][j] = false;
			}
		}
	}
	
	public int calculScore() {
		int score = 0;
		for(int i = 0; i < this.height; i++) {
			for(int j = 0; j < this.width; j++) {
				if(boolean_pizza[i][j])
					score++;
			}
		}
		return score;
	}
	
	
	
	public ArrayList<Part> generateAllParts() {
		ArrayList<Part> parts = new ArrayList<Part>();
		int nbJambon = 0;
		for(int ligne = 0; ligne < this.height; ligne++) {
			for(int colonne = 0; colonne < this.width; colonne++) {
				for(int ligneEnd = ligne; ligneEnd < this.height; ligneEnd++) {
					for(int colonneEnd = colonne; colonneEnd < this.width; colonneEnd++) {
						nbJambon = 0;
						for(int u = ligne; u <= ligneEnd; u++) {
							for(int v = colonne; v <= colonneEnd; v++) {
								if(this.getPizza()[u][v] == 'H') {
									nbJambon++;
								}
							}
						}
						if((nbJambon >= getNbJambonPerPart() && ((ligneEnd - ligne) + 1) * ((colonneEnd - colonne) + 1) <= getMaxSurfaceOfPart())){
							parts.add(new Part(ligne, colonne, ligneEnd, colonneEnd, nbJambon));
						}
					}
				}
			}
		}
		allParts = parts;
		return parts;
	}
	
	
	public ArrayList<Part> mixedRoyalParts(ArrayList<Part> parts) {
		ArrayList<Part> mixedParts = new ArrayList<Part>();
		boolean [] listePartsPrises = new boolean [parts.size()];
		int indiceAlea = 0;
		for(int i = 0; i < parts.size(); i++) {
			listePartsPrises[i] = false;
		}
		while(mixedParts.size() < parts.size()) {
			Random rand = new Random();
			int max = parts.size() - 1;
			int min = 0;
			indiceAlea = rand.nextInt(max - min) + min;
			if(mixedParts.size() == parts.size() - 1) {
				for(int j = 0; j < listePartsPrises.length; j++) {
					if(!listePartsPrises[j]) {
						mixedParts.add(parts.get(j));
						listePartsPrises[j] = true;
						break;
					}
				}
			}
			if(!listePartsPrises[indiceAlea]) {
				listePartsPrises[indiceAlea] = true;
				mixedParts.add(parts.get(indiceAlea));
			}	
		}
		return mixedParts;
	}
	
	/**
	 * Return true if part can be taken, false if it is hover an other part
	 * @param part
	 * @return true or false
	 */
	public boolean isCompatible(Part part) {
		for(int ligne = part.getLigne(); ligne <= part.getLigneEnd(); ligne++) {
			for(int colonne = part.getColonne(); colonne <= part.getColonneEnd(); colonne++) {
				// if there is already a royal part here
				if(boolean_pizza[ligne][colonne]) {
					return false;
				}
			}	
		}
		return true;
	}
	
	public ArrayList<Part> solutionAlea(ArrayList<Part> parts) {
		initFalseBooleanPizzaTab();
		boolean partCanBeAdd = true;
		ArrayList<Part> solution = new ArrayList<Part>();
		for(Part partToAdd : parts){
			partCanBeAdd = true;
//			System.out.println("Can add " + partToAdd.toString() + " ?");
			for(int ligne = partToAdd.getLigne(); ligne <= partToAdd.getLigneEnd(); ligne++){
				for(int colonne = partToAdd.getColonne(); colonne <= partToAdd.getColonneEnd(); colonne++){
					if(boolean_pizza[ligne][colonne] == true){
						partCanBeAdd = false;
						break;
					}
				}
				if(!partCanBeAdd) break;
			}
			
			if(partCanBeAdd){
//				System.out.println("added : " + partToAdd.toString());
				solution.add(partToAdd);
				fillBooleanPizzaTabWithPartToAdd(partToAdd);
			}
		}
		
		return solution;
	}
	
	public void fillBooleanPizzaTabWithPartToAdd(Part part){
		for(int ligne = part.getLigne(); ligne <= part.getLigneEnd(); ligne++){
			for(int colonne = part.getColonne(); colonne <= part.getColonneEnd(); colonne++){
				boolean_pizza[ligne][colonne] = true;
			}
		}
	}
	
	public void removePartFromBooleanPizzaTab(Part part){
		for(int ligne = part.getLigne(); ligne <= part.getLigneEnd(); ligne++){
			for(int colonne = part.getColonne(); colonne <= part.getColonneEnd(); colonne++){
				boolean_pizza[ligne][colonne] = false;
			}
		}
	}
	
//	public ArrayList<Part> heuristicSolution(ArrayList<Part> solution){
//		ArrayList<Part> saveSolution = (ArrayList<Part>) solution.clone();
//		ArrayList<Part> tmpSolution = (ArrayList<Part>) saveSolution.clone();
//		boolean[][] saveBoolean = boolean_pizza;
//		int saveScore = calculScore();
//		
//		// Try to replace each part in the solution by an another one
//		for(Part part : solution){
//			saveScore = calculScore();
//			tmpSolution = (ArrayList<Part>) saveSolution.clone();
//			saveBoolean = boolean_pizza;
//			removePartFromBooleanPizzaTab(part);
//			saveSolution.remove(part);
//			
//			// Try to replace it by an other
//			for(Part replace_part : allParts){
//				if(isCompatible(replace_part)){
//					saveSolution.add(replace_part);
//					fillBooleanPizzaTabWithPartToAdd(replace_part);
//					if(saveScore > calculScore()){
//						saveSolution.remove(replace_part);
//						removePartFromBooleanPizzaTab(replace_part);
//					}
//					else System.out.println("New best");
//				}
//			}
//			
////			if(saveScore < calculScore()){
////				saveSolution = (ArrayList<Part>) tmpSolution.clone();
////				boolean_pizza = saveBoolean;
////			}
//			
//		}
//		return saveSolution;
//	}
	
	public ArrayList<Part> heuristicSolution(ArrayList<Part> solution){
		ArrayList<Part> saveSolution = (ArrayList<Part>) solution.clone();
		int saveScore = calculScore();
		
		for(Part part : solution){
			saveSolution.remove(part);
			saveScore = calculScore();
			removePartFromBooleanPizzaTab(part);
			
			for(Part replace_part : allParts){
				if(isCompatible(replace_part)){
					fillBooleanPizzaTabWithPartToAdd(replace_part);
					if(saveScore < calculScore()){
						saveSolution.add(replace_part);
						saveScore = calculScore();
					} else {
						removePartFromBooleanPizzaTab(replace_part);
					}
				}
			}
		}
		return saveSolution;
	}
	
	
	public ArrayList<Part> pavageLeftToRightAndTopToBottom(ArrayList<Part> allParts) {
		initFalseBooleanPizzaTab();
		Collections.sort((List<Part>) allParts); // on trie les parts par rapport a leur y puis leur x
		return solutionAlea(allParts);
	}
	
	public MISProblem redToMIS(ArrayList<Part> allRoyalParts) {
		MISProblem misP;
		int sizeAllParts = allRoyalParts.size(), sizePartI = 0, sizePartJ = 0;
		Node node, compatible_node;
		ArrayList<Node> list_of_nodes = new ArrayList<>();
	
		for(int i = 0; i < sizeAllParts; i++) {
			
			sizePartI = (allRoyalParts.get(i).getLigneEnd() 
					- allRoyalParts.get(i).getLigne() + 1) 
					* (allRoyalParts.get(i).getColonneEnd() 
					- allRoyalParts.get(i).getColonne() + 1);
			
			node = new Node(i, sizePartI);
			
			for(int j = i; j < sizeAllParts; j++) {		
				
				sizePartJ = (allRoyalParts.get(j).getLigneEnd() 
						- allRoyalParts.get(j).getLigne() + 1) 
						* (allRoyalParts.get(j).getColonneEnd() 
						- allRoyalParts.get(j).getColonne() + 1);
				
				compatible_node = new Node(j, sizePartJ);
				
				// Else if the two Nodes are compatible
				if(isCompatible(allRoyalParts.get(i), allRoyalParts.get(j))) {
//					System.out.println("--Add: " + node.toString());
					node.addCompatibleNode(compatible_node);
				}
			}
			
			list_of_nodes.add(node);
		}
		misP = new MISProblem(this, list_of_nodes);
		return misP;
	}
	
	public ArrayList<Part> greedyChooseTheGoodPart(ArrayList<Part> allRoyalParts){
		ArrayList<Part> best_choices = new ArrayList<Part>();
		Part keeperRight = null, keeperBelow = null, keeperStart = null;
		
		// We start by find the best part to start with
		for(Part first_part : allRoyalParts){
			if(first_part.getLigne() == 0 && first_part.getColonne() == 0){
				if(keeperStart == null 
						|| (keeperStart.getSurface() <= first_part.getSurface()
								&& keeperStart.getNbPieceHam() >= first_part.getNbPieceHam())){
					keeperStart = first_part;
				}
			}
		}
		
		// Now that we have the best first part, we add it to best_choices
		// and fill the boolean tab of the pizza
		best_choices.add(keeperStart);
		fillBooleanPizzaTabWithPartToAdd(keeperStart);
		
		// For each Part
		for(Part main_part : allRoyalParts){
			// Check if the main_part can be add in the list by
			// checking if it is not above an other part
			if(isCompatible(main_part)){
				best_choices.add(main_part);
				fillBooleanPizzaTabWithPartToAdd(main_part);
			}
			
			keeperBelow = null;
			keeperRight = null;
			
			// We search in the all the other parts the best fit
			for(Part best_fit : allRoyalParts){
				
				// If this is the same parts, or if there aren't compatible 
				// we go to the next one
				if(best_fit == main_part && !isCompatible(main_part, best_fit))
					break;
				
				// Else we need to check if best_fit is the best part
				// we can choose to fit on the right of main_part
				if(best_fit.getLigne() == main_part.getLigne() 
						&& best_fit.getColonne() == main_part.getColonneEnd() + 1){
					
					// Check if this best_fit is better than keeper
					if(keeperRight == null 
							|| (keeperRight.getSurface() < best_fit.getSurface()
									&& keeperRight.getNbPieceHam() < best_fit.getNbPieceHam())){
						keeperRight = best_fit;
					}
				}
				
				// Else we need to check if best_fit is the best part
				// we can choose to fit below the main_part
				if(best_fit.getLigne() == main_part.getLigne() + 1
						&& best_fit.getColonne() == main_part.getColonne()){
					
					// Check if this best_fit is better than keeper
					if(keeperBelow == null 
							|| (keeperBelow.getSurface() < best_fit.getSurface()
									&& keeperBelow.getNbPieceHam() < best_fit.getNbPieceHam())){
						keeperBelow = best_fit;
					}
				}
				
				// Check if keeperBelow and keeperRight are not the same Part
				// if not we add them in best_choices & fill the boolean tab of the pizza
				if(keeperBelow != keeperRight && isCompatible(keeperBelow, keeperRight)){
					best_choices.add(keeperBelow);
					best_choices.add(keeperRight);
					fillBooleanPizzaTabWithPartToAdd(keeperBelow);
					fillBooleanPizzaTabWithPartToAdd(keeperRight);
				}
			}
		}
		System.out.println("best_choices size: " + best_choices.size());
		return best_choices;
	}
	
	
	public boolean isCompatible(Part part1, Part part2) {
		boolean res = true;
		boolean tmp[][] = new boolean [this.height][this.width];
		for(int ligne = part1.getLigne(); ligne <= part1.getLigneEnd(); ligne++) {
			for(int colonne = part1.getColonne(); colonne <= part1.getColonneEnd(); colonne++) {
				tmp[ligne][colonne] = true;
			}
		}
		for(int ligne = part2.getLigne(); ligne <= part2.getLigneEnd(); ligne++) {
			for(int colonne = part2.getColonne(); colonne <= part2.getColonneEnd(); colonne++) {
				if(tmp[ligne][colonne]) {
					res = false;
					break;
				}
			}
			if(!res) {
				break;
			}
		}
		return res;
	}
	
	public ArrayList<Part> getImprovedSolution(ArrayList<Part> solution){
		int keeper = 0;
		boolean correct = true;
		for(Part part : solution){
			// If there is free space on the left of the part, we can increase its width
			if(part.getColonne() > 0){
				for(int i = part.getColonne() - 1; i >= 0 && correct; i--){
					for(int j = part.getLigne(); j <= part.getLigneEnd() && correct; j++){
						if(boolean_pizza[j][i] == true){
							correct = false;
						}
					}
					if(correct){
						keeper = part.getColonne();
						part.setColonne(i);
						if(part.getSurface() > maxSurfaceOfPart){
							correct = false;
							part.setColonne(keeper);
						}
						for(int j = part.getLigne(); j <= part.getLigneEnd() && correct; j++){
							boolean_pizza[j][i] = true;
						}
					}
				}
			}
			
			correct = true;
			
			// If there is free space above the part, we can increase its height
			if(part.getLigne() > 0){
				for(int i = part.getLigne() - 1; i >= 0 && correct; i--){
					for(int j = part.getColonne(); j <= part.getColonneEnd() && correct; j++){
						if(boolean_pizza[i][j] == true){
							correct = false;
						}
					}
					if(correct){
						keeper = part.getLigne();
						part.setLigne(i);
						if(part.getSurface() > maxSurfaceOfPart){
							part.setLigne(keeper);
							correct = false;
						}
						for(int j = part.getColonne(); j <= part.getColonneEnd() && correct; j++){
							boolean_pizza[i][j] = true;
						}
					}
				}
			}
			
			correct = true;
			
			// If there is free space on the right of the part, we can increase its width
			if(part.getColonneEnd() < width - 1){
				for(int i = part.getColonneEnd() + 1; i < width && correct; i++){
					for(int j = part.getLigne(); j <= part.getLigneEnd() && correct; j++){
						if(boolean_pizza[j][i] == true){
							correct = false;
						}
					}
					if(correct){
						keeper = part.getColonneEnd();
						part.setColonneEnd(i);
						if(part.getSurface() > maxSurfaceOfPart){
							correct = false;
							part.setColonneEnd(keeper);
						}
						for(int j = part.getLigne(); j <= part.getLigneEnd() && correct; j++){
							boolean_pizza[j][i] = true;
						}
					}
				}
			}
			
			correct = true;
			
			// If there is free space below the part, we can increase its height
			if(part.getLigneEnd() < height -1){
				for(int i = part.getLigneEnd() + 1; i < height && correct; i++){
					for(int j = part.getColonne(); j <= part.getColonneEnd() && correct; j++){
						
						if(boolean_pizza[i][j] == true){
							correct = false;
						}
					}
					if(correct){
						keeper = part.getLigneEnd();
						part.setLigneEnd(i);
						if(part.getSurface() > maxSurfaceOfPart){
							correct = false;
							part.setLigneEnd(keeper);
						}
						for(int j = part.getColonne(); j <= part.getColonneEnd() && correct; j++){
							boolean_pizza[i][j] = true;
						}
					}
				}
			}
		}
		return solution;
	}
	
	
	public void boolean_pizza_tab_toString(){
		for(int i=0; i<height; i++){
			for(int j=0; j<width; j++){
				System.out.print(boolean_pizza[i][j] + " ");
			}
			System.out.println("");
		}
		System.out.println("");
	}
	
	
	
	
	/** Getters & Setters **/
	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getMaxSurfaceOfPart() {
		return maxSurfaceOfPart;
	}

	public void setMaxSurfaceOfPart(int maxSurfaceOfPart) {
		this.maxSurfaceOfPart = maxSurfaceOfPart;
	}

	public int getNbJambonPerPart() {
		return nbJambonPerPart;
	}

	public void setNbJambonPerPart(int nbJambonPerPart) {
		this.nbJambonPerPart = nbJambonPerPart;
	}

	public char[][] getPizza() {
		return pizza;
	}

	public void setPizza(char[][] pizza) {
		this.pizza = pizza;
	}

	public ArrayList<Part> getAllParts() {
		return allParts;
	}

	public void setAllParts(ArrayList<Part> allParts) {
		this.allParts = allParts;
	}
	
	
	
	
}
