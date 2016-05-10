package tp.pb;

public class Part implements Comparable<Part>{
	private int ligne, colonne;
	private int ligneEnd, colonneEnd;
	private int nbPieceHam;
	
	/** -------- CONSTRUCTORS -------- **/

	public Part(int _ligne, int _colonne, int _ligneEnd, int _colonneEnd, int _nbPieceHam){
		ligne = _ligne;
		colonne = _colonne;
		ligneEnd = _ligneEnd;
		colonneEnd = _colonneEnd;
		nbPieceHam = _nbPieceHam;
	}
	
	
	
	/** -------- GETTERS & SETTERS -------- **/
	
	public int getLigne() {
		return ligne;
	}
	public void setLigne(int ligne) {
		this.ligne = ligne;
	}
	public int getColonne() {
		return colonne;
	}
	public void setColonne(int colonne) {
		this.colonne = colonne;
	}
	public int getLigneEnd() {
		return ligneEnd;
	}
	public void setLigneEnd(int ligneEnd) {
		this.ligneEnd = ligneEnd;
	}
	public int getColonneEnd() {
		return colonneEnd;
	}
	public void setColonneEnd(int colonneEnd) {
		this.colonneEnd = colonneEnd;
	}
	
	public int getSurface(){
		return (ligneEnd - ligne + 1) * (colonneEnd - colonne + 1);
	}
	
	public boolean isSquare(){
		if( (ligneEnd - ligne + 1) == (colonneEnd - colonne + 1))
			return true;
		else return false;
	}
	
	public int getNbPieceHam() {
		return nbPieceHam;
	}



	public void setNbPieceHam(int nbPieceHam) {
		this.nbPieceHam = nbPieceHam;
	}



	/** -------- ToString -------- **/
	
	public String toString() {
		return ("[ " + this.ligne + " - " + this.colonne + " - " + this.ligneEnd + " - " + this.colonneEnd + " ]");
	}
	
	
	/** -------- OVERRIDE -------- **/
	
//	@Override
//	public int compareTo(Part otherPart) {
//		Integer y1 = new Integer(this.colonne);
//		Integer y2 = new Integer(otherPart.colonne);
//        int sComp = y1.compareTo(y2);
//        if (sComp != 0) {
//           return sComp;
//        } else {
//           Integer x1 = this.ligne;
//           Integer x2 = otherPart.ligne;
//           return x1.compareTo(x2);
//        }
//	}
	
	@Override
	public int compareTo(Part otherPart) {
		Integer ligne1 = new Integer(this.ligne);
		Integer ligne2 = new Integer(otherPart.ligne);
		int sComp = ligne1.compareTo(ligne2);
		if (sComp != 0) {
			return sComp;
		} 
		else {
			Integer colonne1 = this.colonne;
			Integer colonne2 = otherPart.colonne;
			if (sComp != 0) {
				return sComp;
			} 
			else {
				Integer colonneEnd1 = this.colonneEnd;
				Integer colonneEnd2 = otherPart.colonneEnd;
				Integer ligneEnd1 = this.colonneEnd;
				Integer ligneEnd2 = otherPart.colonneEnd;
				Integer surface1 = (colonneEnd1 - colonne1 + 1) * (ligneEnd1 - ligne1 + 1);
				Integer surface2 = (colonneEnd2 - colonne2 + 1) * (ligneEnd2 - ligne2 + 1);
				return surface1.compareTo(surface2);
			}
		}
	}
	
}
