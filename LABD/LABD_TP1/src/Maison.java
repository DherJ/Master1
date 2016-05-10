import java.util.ArrayList;


public class Maison {
	
	int id;
	int etages;
	ArrayList<Piece> pieces;
	
	public Maison () {
		this.etages = 0;
		pieces = new ArrayList<Piece>();
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void addPiece(Piece p){
		this.pieces.add(p);
	}
	
	public void addEtage() {
		this.etages ++;
	}
	
	public float totalSuperficie() {
		float s = 0;
		System.out.println("coucou");
		for( int i = 0 ; i< pieces.size() ; i++) {
			s = s + pieces.get(i).superficie;
			System.out.println(s);

		}
		System.out.println("coucou");

		return s;
	}

}
