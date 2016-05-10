package tp.MIS;

public class Sommet {
	
	private int id;
	private int ponderation;
	private boolean compatible;
	

	/** Default constructor **/
	public Sommet() {}
	
	/** Constructor **/
	public Sommet(int _id) {
		this.id = _id;
	}
	
	public Sommet(int _id, int _ponderation) {
		this.id = _id;
		this.ponderation = _ponderation;
	}
	
	public String toString() {
		return "Sommet : " + this.getId() + " -- Pondération : " + this.getPonderation() + " Compatible : " + this.isCompatible();
	}
	
	/** Getters & Setters **/
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPonderation() {
		return ponderation;
	}
	public void setPonderation(int ponderation) {
		this.ponderation = ponderation;
	}
	
	public boolean isCompatible() {
		return compatible;
	}

	public void setCompatible(boolean compatible) {
		this.compatible = compatible;
	}
}