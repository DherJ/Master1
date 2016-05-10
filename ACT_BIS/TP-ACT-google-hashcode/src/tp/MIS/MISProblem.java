package tp.MIS;

import java.util.ArrayList;

import tp.pb.Pizza;

public class MISProblem {

	private Pizza pizza;
	private Node[][] graph;
	
	private ArrayList<Node> listOfNodes;

	/** Default constructor **/
	public MISProblem() {
		int size = pizza.generateAllParts().size();
		graph = new Node[size][size];
		listOfNodes = new ArrayList<Node>();
	}
	
	/** Constructor **/
	public MISProblem(Pizza _pizza) {
		pizza = _pizza;
		int size = pizza.generateAllParts().size();
		graph = new Node[size][size];
		listOfNodes = new ArrayList<Node>();
	}
	
	public MISProblem(Pizza _pizza, Node[][] _graph) {
		pizza = _pizza;
		int size = pizza.generateAllParts().size();
		graph = new Node[size][size];
		graph = _graph;
		this.listOfNodes = new ArrayList<Node>();
	}
	
	public MISProblem(Pizza _pizza, ArrayList<Node> _nodes) {
		pizza = _pizza;
		int size = pizza.generateAllParts().size();
		listOfNodes = _nodes;
	}
	
	public void printGraphe() {
		for(int i = 0; i < graph.length; i++) {
			for(int j = 0; j < graph.length; j++) {
				System.out.print(graph[i][j].toString() + "  ");
			}
			System.out.println();
		}
	}

	
	/**** Getters & Setters *****/
	public Pizza getPizza() {
		return pizza;
	}

	public void setPizza(Pizza pizza) {
		this.pizza = pizza;
	}

	public Node[][] getGraph() {
		return graph;
	}

	public void setGraph(Node[][] graph) {
		this.graph = graph;
	}

	public ArrayList<Node> getListOfNode() {
		return listOfNodes;
	}

	public void setListOfNode(ArrayList<Node> _listOfNodes) {
		this.listOfNodes = _listOfNodes;
	}
	
//	public ArrayList<Node> getBestChoices(){
//		Node node_to_add = null;
//		ArrayList<Node> best_choices = new ArrayList<>();
//		for(int i=0; i<graph.length; i++){
//			node_to_add = null;
//			for(int j=i; j<graph.length; j++){
//				// If the two Nodes are compatible
//				if(i != j && graph[i][j].isCompatible()){
//					if(node_to_add == null)
//						node_to_add = graph[i][j];
//					else if(node_to_add.getSurface() < graph[i][j].getSurface() 
//							&& graph[i][j].isCompatible()){
//						node_to_add = graph[i][j];
//					}
//				}
//			}
//			
//			if(node_to_add != null)
//				best_choices.add(node_to_add);
//		}
//		return best_choices;
//	}
	
	public ArrayList<Node> getBestChoices(){
		ArrayList<Node> best_choices = new ArrayList<Node>();
		Node best_local = null;
		
		for(int i=0; i<listOfNodes.size(); i++){
			best_local = null;
			if(best_choices.size() == 0){
				best_choices.add(listOfNodes.get(i));
			}
			else {
				for(Node compatible_node: listOfNodes.get(i).getListCompatibleNodes()){
					if(best_local == null || compatible_node.getSurface() > best_local.getSurface()){
						best_local = compatible_node;
					}
				}
				best_choices.add(best_local);
			}
		}
		
//		for(Node node : listOfNodes){
//			best_local = null;
//			if(best_choices.size() == 0){
//				best_choices.add(node);
//				System.out.println("-Add: " + node.toString());
//			}
//			else {
//				for(Node compatible_node : node.getListCompatibleNodes()){
//					if(best_local == null || compatible_node.getSurface() > best_local.getSurface()){
//						best_local = new Node(compatible_node.getId(), compatible_node.getSurface());
//					}
//				}
//				System.out.println("Add: " + best_local.toString());
//				best_choices.add(best_local);
//			}
//		}
		return best_choices;
		
		
//		for(int i=0; i<graph.length; i++){
//			node_to_add = null;
//			for(int j=i; j<graph.length; j++){
//				// If the two Nodes are compatible
//				if(i != j && graph[i][j].isCompatible()){
//					if(node_to_add == null)
//						node_to_add = graph[i][j];
//					else if(node_to_add.getSurface() < graph[i][j].getSurface() 
//							&& graph[i][j].isCompatible()){
//						node_to_add = graph[i][j];
//					}
//				}
//			}
//			
//			if(node_to_add != null)
//				best_choices.add(node_to_add);
//		}
//		return best_choices;
	}
	
}
