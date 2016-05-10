package tp.pb;

import java.util.ArrayList;

/**
 * Classe qui correspond à un certificat du problème de pizza
 * @author jerome
 *
 */
public class Certificat {

	private ArrayList <Part> parts;
	private Pizza instancePb;
	

	/** Default Constructor **/
	public Certificat(){
		this.parts = new ArrayList<Part>();
	}
	
	/** Constructor **/
	public Certificat(Pizza _instancePb) {
		this.instancePb = _instancePb;
		this.parts = new ArrayList<Part>();
	}
	
	public Certificat(Pizza _instancePb, ArrayList<Part> parts) {
		this.instancePb = _instancePb;
		this.parts = parts;
	}
	
	public void addPart(Part _part) {
		this.parts.add(_part);
	}
	
	public void removePart(Part _part) {
		this.parts.remove(_part);
	}
	
	public void display() {
		for(Part part : this.parts) {
			part.toString();
		}
	}
	
	boolean estCorrect() {
		int nbJambon = 0;
		int surfaceParts = 0;
		int surfacePart = 0;
		boolean [][] estOccupe = new boolean [this.instancePb.getHeight()][this.instancePb.getWidth()];
		for(int i = 0; i < this.instancePb.getHeight(); i++) {
			for(int j = 0; j < this.instancePb.getWidth(); j++) {
				estOccupe[i][j] = false;
			}
		}
		for(Part part : this.parts) {
			surfacePart = ((part.getLigneEnd() - part.getLigne()) + 1) * ((part.getColonneEnd() - part.getColonne()) + 1);
			if(surfacePart > instancePb.getMaxSurface())
				return false;
			if(((part.getLigneEnd() - part.getLigne()) + 1) == ((part.getColonneEnd() - part.getColonne()) + 1))
				return false;
			for(int i = part.getLigne(); i <= part.getLigneEnd(); i++) {
				for(int j = part.getColonne(); j <= part.getColonneEnd(); j++) {
					if(instancePb.getPizza()[i][j] == 'H')
						nbJambon++;
					if(estOccupe[i][j])
						return false;
					else estOccupe[i][j] = true;
				}
			}
			if(nbJambon < instancePb.getnJambon())
				return false;
			nbJambon = 0;
			surfaceParts += surfacePart;
			if(surfaceParts > instancePb.getMaxSurface())
				return false;
		}
		return true;
	}
	
	
	/** Getters and Setters **/
	public ArrayList<Part> getParts() {
		return parts;
	}

	public Pizza getInstancePb() {
		return instancePb;
	}
}