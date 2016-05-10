package tp.pb;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import tp.MIS.MISProblem;
import tp.MIS.Sommet;


/** 
 * Classe qui correspond à l'instance du problème de pizza
 * @author jerome
 *
 */
public class Pizza {
	
	private int height;
	private int width;
	private int maxSurface;
	private int nJambon;
	private char[][] pizza;
	

	private boolean [][] estOccupe;
	private ArrayList<Part> allParts;


	/** Default Constructor **/
	public Pizza() {}
	
	/** Constructor **/
	public Pizza(int _height, int _width, int _maxSurface, int _nJambon, char[][] _pizza) {
		this.height = _height;
		this.width = _width;
		this.maxSurface = _maxSurface;
		this.nJambon = _nJambon;
		this.pizza = _pizza;
		estOccupe = new boolean [height][width];  /* tableau de boolean qui indique si la case de la pizza est prise */
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
				estOccupe[i][j] = false;
			}
		}
		this.allParts = new ArrayList<Part>();
	}
	
	
	public void initTabOccupe() {
		for(int i = 0; i < this.height; i++) {
			for(int j = 0; j < this.width; j++) {
				this.estOccupe[i][j] = false;
			}
		}
	}
	
	
	public int calculScore() {
		int score = 0;
		for(int i = 0; i < this.height; i++) {
			for(int j = 0; j < this.width; j++) {
				if(estOccupe[i][j])
					score++;
			}
		}
		return score;
	}
	
	
	/** 
	 * Méthode qui permet de générer toute les parts royale de pizza (105 000 parts à peut près)
	 * @return Retourne une liste d'objets Part qui contient la liste des parts de pizza
	 */
	public ArrayList<Part> generateAllParts() {
		ArrayList<Part> parts = new ArrayList<Part>();
		int nbJambon = 0;
		int surface = 0;
		for(int ligne = 0; ligne < this.height; ligne++) {
			for(int colonne = 0; colonne < this.width; colonne++) {
				for(int endLigne = ligne; endLigne < this.height; endLigne++) {
					for(int endColonne = colonne; endColonne < this.width; endColonne++) {
						nbJambon = 0;
						for(int u = ligne; u <= endLigne; u++) {
							for(int v = colonne; v <= endColonne; v++) {
								if(this.getPizza()[u][v] == 'H') {
									nbJambon++;
								}
							}
						}
						surface = ((endLigne - ligne) + 1) * ((endColonne - colonne) + 1);
						if((nbJambon >= this.nJambon) && (surface <= this.maxSurface)){
							parts.add(new Part(ligne, colonne, endLigne, endColonne));
						}
					}
				}
			}
		}
		this.allParts = parts;
		return parts;
	}
	
	
	public ArrayList<Part> trieAleaParts(ArrayList<Part> parts) {
		ArrayList<Part> partsTriee = new ArrayList<Part>();
		boolean [] listePartsPrises = new boolean [parts.size()];
		int indiceAlea = 0;
		for(int i = 0; i < parts.size(); i++) {
			listePartsPrises[i] = false;
		}
		while(partsTriee.size() < parts.size()) {
			Random rand = new Random();
			int max = parts.size() - 1;
			int min = 0;
			indiceAlea = rand.nextInt(max - min) + min;
			if(partsTriee.size() == parts.size() - 1) {
				for(int j = 0; j < listePartsPrises.length; j++) {
					if(!listePartsPrises[j]) {
						partsTriee.add(parts.get(j));
						listePartsPrises[j] = true;
						break;
					}
				}
			}
			if(!listePartsPrises[indiceAlea]) {
				listePartsPrises[indiceAlea] = true;
				partsTriee.add(parts.get(indiceAlea));
			}	
		}
		return partsTriee;
	}
	

	public boolean isCompatible(Part part1) {
		boolean res = true;
		for(int ligne = part1.getLigne(); ligne <= part1.getLigneEnd(); ligne++) {
			for(int colonne = part1.getColonne(); colonne <= part1.getColonneEnd(); colonne++) {
				if(estOccupe[ligne][colonne]) {
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
	
	
	public ArrayList<Part> solutionAlea(ArrayList<Part> partsTrieeAlea) {
		ArrayList<Part> parts = new ArrayList<Part>();
		parts.add(partsTrieeAlea.get(0));
		for(int ligne = partsTrieeAlea.get(0).getLigne(); ligne <= partsTrieeAlea.get(0).getLigneEnd(); ligne++) {
			for(int colonne = partsTrieeAlea.get(0).getColonne(); colonne <= partsTrieeAlea.get(0).getColonneEnd(); colonne++) {
				estOccupe[ligne][colonne] = true;
			}
		}
		//System.out.println("added : " + partsTrieeAlea.get(0).toString());
		boolean addPart = true;
		for(int i = 1; i < partsTrieeAlea.size(); i++) {
			//System.out.println("add? : " + partsTrieeAlea.get(i).toString());
			addPart = isCompatible(partsTrieeAlea.get(i));
			if(addPart) {
				parts.add(partsTrieeAlea.get(i));
				for(int ligne = partsTrieeAlea.get(i).getLigne(); ligne <= partsTrieeAlea.get(i).getLigneEnd(); ligne++) {
					for(int colonne = partsTrieeAlea.get(i).getColonne(); colonne <= partsTrieeAlea.get(i).getColonneEnd(); colonne++) {
						estOccupe[ligne][colonne] = true;
					}	
				}
				//System.out.println("added : " + partsTrieeAlea.get(i).toString());
			}
			addPart = true;
		}
		return parts;
	}
	
	
	public ArrayList<Part> solutionGloutonneGaucheDroiteHautBas(ArrayList<Part> allParts) {
		ArrayList<Part> parts = new ArrayList<Part>();
		Collections.sort((List<Part>) allParts); // on trie les parts par rapport a leur y puis leur x
		parts.add(allParts.get(0));
		return solutionAlea(allParts);
	}
	
	
	public MISProblem redToMIS(ArrayList<Part> allRoyalParts) {
		MISProblem misP;
		Sommet graphe[][] = new Sommet[allRoyalParts.size()][allRoyalParts.size()];
		Sommet s;
		int sizePart1 = 0;
		int sizePart2 = 0;
		for(int i = 0; i < this.allParts.size(); i++) {
			for(int j = i; j < this.allParts.size(); j++) {
				sizePart1 = (allParts.get(i).getLigneEnd() - allParts.get(i).getLigne() + 1) * (allParts.get(i).getColonneEnd() - allParts.get(i).getColonne() + 1);
				sizePart2 = (allParts.get(j).getLigneEnd() - allParts.get(j).getLigne() + 1) * (allParts.get(j).getColonneEnd() - allParts.get(j).getColonne() + 1);
				if(!isCompatible(allParts.get(i), allParts.get(j))) {
					s = new Sommet(j, sizePart2);
					s.setCompatible(false);
					graphe[i][j] = s;
					s = new Sommet(i, sizePart1);
					s.setCompatible(false);
					graphe[j][i] = s;
				}
				else {
					s = new Sommet(j, sizePart2);
					s.setCompatible(true);
					graphe[i][j] = s;
					s = new Sommet(i, sizePart1);
					s.setCompatible(true);
					graphe[j][i] = s;
				}
			}
		}
		misP = new MISProblem(this, graphe);
		return misP;
	}
	
	/** Getters & Setters  **/
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
	
	public int getMaxSurface() {
		return maxSurface;
	}
	
	public void setMaxSurface(int maxSurface) {
		this.maxSurface = maxSurface;
	}
	
	public int getnJambon() {
		return nJambon;
	}
	
	public void setnJambon(int nJambon) {
		this.nJambon = nJambon;
	}
	
	public char[][] getPizza() {
		return pizza;
	}

	public void setPizza(char[][] pizza) {
		this.pizza = pizza;
	}
	
	public boolean[][] getEstOccupe() {
		return estOccupe;
	}

	public void setEstOccupe(boolean[][] estOccupe) {
		this.estOccupe = estOccupe;
	}
	
	public ArrayList<Part> getAllParts() {
		return allParts;
	}

	public void setAllParts(ArrayList<Part> allParts) {
		this.allParts = allParts;
	}
}