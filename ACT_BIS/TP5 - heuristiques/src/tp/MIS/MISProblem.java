package tp.MIS;

import java.util.ArrayList;

import tp.pb.Pizza;

public class MISProblem {

	private Pizza instPizza;
	private Sommet graphe[][];
	
	private ArrayList<Sommet> listOfIndepSommet;
	

	/** Default constructor **/
	public MISProblem() {
		this.graphe = new Sommet [this.instPizza.getAllParts().size()][this.instPizza.getAllParts().size()];
		this.listOfIndepSommet = new ArrayList<Sommet>();
	}
	
	/** Constructor **/
	public MISProblem(Pizza _pizza) {
		this.instPizza = _pizza;
		this.graphe = new Sommet [this.instPizza.getAllParts().size()][this.instPizza.getAllParts().size()];
		this.listOfIndepSommet = new ArrayList<Sommet>();
	}
	
	public MISProblem(Pizza _pizza, Sommet[][] _graphe) {
		this.instPizza = _pizza;
		this.graphe = new Sommet [this.instPizza.getAllParts().size()][this.instPizza.getAllParts().size()];
		this.graphe = _graphe;
		this.listOfIndepSommet = new ArrayList<Sommet>();
	}

	public ArrayList<Sommet> getVoisinsOfSommet(int idSommet) {
		ArrayList<Sommet> result = new ArrayList<Sommet>();
		for(int i = 0; i < this.getInstPizza().getAllParts().size(); i++) {
			if(this.getGraphe()[idSommet][i].isCompatible() && (i != idSommet)) {
				result.add(this.getGraphe()[idSommet][i]);
			}
		}
		return result;
	}
	
	public void getIndepSommets(int id_sommet, ArrayList<Sommet> indepSommets) {
		ArrayList<Sommet> voisins = new ArrayList<Sommet>();
		voisins = getVoisinsOfSommet(id_sommet);
		for(Sommet sommet : voisins) {
			if(sommet.getId() != id_sommet && !sommet.isCompatible()) {
				indepSommets.add(sommet);
				getIndepSommets(sommet.getId(), indepSommets);
			}
		}
	}
	
	public ArrayList<Sommet> getMaxIndepSommets() {
		ArrayList<ArrayList<Sommet>> allIndepSet = new ArrayList<ArrayList<Sommet>> ();
		ArrayList<Sommet> result = new ArrayList<Sommet>();
		ArrayList<Sommet> indepSommets = new ArrayList<Sommet>();
		for(int i = 0; i < this.instPizza.getAllParts().size(); i++) {
			getIndepSommets(i, indepSommets);
			allIndepSet.add(indepSommets);
		}
		int max = allIndepSet.get(0).size();
		for(int i = 1; i < allIndepSet.size(); i++) {
			if(allIndepSet.get(i).size() > max) {
				result = allIndepSet.get(i);
				max = allIndepSet.get(i).size();
			}
		}
		return result;
	}
	
	
	public void printGraphe() {
		for(int i = 0; i < this.graphe.length; i++) {
			for(int j = 0; j < this.graphe.length; j++) {
				System.out.print(this.graphe[i][j].toString() + "  ");
			}
			System.out.println();
		}
	}
	
	/** Getters & Setters **/
	public Pizza getInstPizza() {
		return instPizza;
	}

	public void setInstPizza(Pizza instPizza) {
		this.instPizza = instPizza;
	}

	public Sommet[][] getGraphe() {
		return graphe;
	}

	public void setGraphe(Sommet[][] graphe) {
		this.graphe = graphe;
	}
	
	public ArrayList<Sommet> getListOfIndepSommet() {
		return listOfIndepSommet;
	}

	public void setListOfIndepSommet(ArrayList<Sommet> listOfIndepSommet) {
		this.listOfIndepSommet = listOfIndepSommet;
	}
}