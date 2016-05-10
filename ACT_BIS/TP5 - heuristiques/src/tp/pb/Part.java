package tp.pb;


public class Part implements Comparable<Part> {

	private int ligne;      // indice x de début de la part ( correspond à l'extrême gauche de la part)
	private int colonne;
	private int ligneEnd;  // indice x de fin de la part ( correspond à l'extrême droite de la part)
	private int colonneEnd;
	
	/** Default Constructor **/
	public Part() {};
	
	/** Constructor **/
	public Part(int ligne, int colonne, int ligneEnd, int colonneEnd) {
		this.ligne = ligne;
		this.colonne = colonne;
		this.ligneEnd = ligneEnd;
		this.colonneEnd = colonneEnd;
	}
	
	public String toString() {
		return ("[ " + this.ligne + " - " + this.colonne + " - " + this.ligneEnd + " - " + this.colonneEnd + " ]");
	}
	
	/** Getters & Setters **/
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

	@Override
	public int compareTo(Part otherPart) {
	Integer ligne1 = new Integer(this.ligne);
	Integer ligne2 = new Integer(otherPart.ligne);
    int sComp = ligne1.compareTo(ligne2);
    if (sComp != 0) {
       return sComp;
    } else {
       Integer colonne1 = this.colonne;
       Integer colonne2 = otherPart.colonne;
       if (sComp != 0) {
           return sComp;
        } else {
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
	
	// 8496
//	public int compareTo(Part otherPart) {
//	Integer ligne1 = new Integer(this.ligne);
//	Integer ligne2 = new Integer(otherPart.ligne);
//    int sComp = ligne1.compareTo(ligne2);
//    if (sComp != 0) {
//       return sComp;
//    } else {
//       Integer colonne1 = this.colonne;
//       Integer colonne2 = otherPart.colonne;
//       if (sComp != 0) {
//           return sComp;
//        } else {
//        	Integer colonneEnd1 = this.colonneEnd;
//            Integer colonneEnd2 = otherPart.colonneEnd;
//            Integer ligneEnd1 = this.colonneEnd;
//            Integer ligneEnd2 = otherPart.colonneEnd;
//            Integer surface1 = (colonneEnd1 - colonne1 + 1) * (ligneEnd1 - ligne1 + 1);
//            Integer surface2 = (colonneEnd2 - colonne2 + 1) * (ligneEnd2 - ligne2 + 1);
//            return surface1.compareTo(surface2);
//        }
//    }
//}
	
	// 8310
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
}