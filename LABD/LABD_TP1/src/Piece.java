
public class Piece {

	float superficie = 0;
	String name;
	String contenu;

	public Piece (String name) {
		this.name = name;
	}
	
	public void setSuperficie(float m2) {
		if(this.superficie != 0) {
			//this.superficie += m2;
		} else {
			this.superficie = m2;
		}
	}
	
	public float getSuperficie() {
		return this.superficie;
	}
	
	public void setContenu(String contenu) {
		this.contenu = contenu;
	}
	
}
