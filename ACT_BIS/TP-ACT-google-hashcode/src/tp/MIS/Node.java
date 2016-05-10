package tp.MIS;

import java.io.Serializable;
import java.util.ArrayList;

public class Node implements Serializable {
	
	private int id;
	private int surface;
	private ArrayList<Node> compatibles_nodes;
	
	/** Default constructor **/
	public Node() {}
	
	/** Constructor **/
	public Node(int _id) {
		id = _id;
		compatibles_nodes = new ArrayList<Node>();
	}
	
	public Node(int _id, int _surface) {
		id = _id;
		surface = _surface;
		compatibles_nodes = new ArrayList<Node>();
	}
	
	public String toString() {
		return "Node : " + this.getId() + " -- Surface : " + this.getSurface();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSurface() {
		return surface;
	}

	public void setSurface(int _surface) {
		surface = _surface;
	}
	
	public void addCompatibleNode(Node node){
		compatibles_nodes.add(node);
	}
	
	public ArrayList<Node> getListCompatibleNodes(){
		return compatibles_nodes;
	}

}
