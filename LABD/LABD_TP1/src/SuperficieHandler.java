import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import labd.LABDHandler;
import labd.XMLParser;

public class SuperficieHandler extends DefaultHandler{
	
	ArrayList<Maison> maisons;
	Maison tempMaison;
	Piece tempPiece;

	public void startElement(String nameSpaceURI, String localName, String rawName, Attributes attributs) throws SAXException {
		//System.out.println("Ouverture de la balise : " + localName) ;
		
		if(localName.equals("maison")) {
			tempMaison = new Maison();
		} else if(localName.equals("maisons")){
			System.out.println("Init...");
			maisons = new ArrayList<Maison>();
		} else if(!localName.equals("RDC") &&!localName.equals("étage") &&!localName.equals("alcove") ){
			tempPiece = new Piece(localName);
		}
		//if (attributs.getLength() != 0) System.out.println("  Attributs de la balise : ") ;
		for (int index = 0; index < attributs.getLength(); index++) { // on parcourt la liste des attributs
			//System.out.println("     - " +  attributs.getLocalName(index) + " = " + attributs.getValue(index));
			if(attributs.getLocalName(index).equals("id")) {
				if(tempMaison != null) {
					tempMaison.setId(Integer.valueOf(attributs.getValue(index)));
				}
			} else if (attributs.getLocalName(index).equals("surface-m2")) {
				if(tempPiece != null) {
						tempPiece.setSuperficie(Float.valueOf(attributs.getValue(index)));
				}
			}
		}
	}
	
	public void endElement(String nameSpaceURI, String localName, String rawName) throws SAXException {
		//System.out.println("Fermeture de la balise : " + localName);
		if(localName.equals("maison")) {
			maisons.add(tempMaison);
			tempMaison = null;
		} else if(localName.equals("maisons")){
			superficie();
			System.out.println("Terminated...");
		} else if(!localName.equals("RDC") &&!localName.equals("étage") &&!localName.equals("")){
			tempMaison.addPiece(tempPiece);
		}
	}
	
	public void superficie() {
		for(int i= 0; i< maisons.size() ;i++) {
			System.out.println("Maison " + maisons.get(i).id + ":");
			System.out.println("\tsuperficie totale:"+maisons.get(i).totalSuperficie());
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			XMLReader saxReader = XMLReaderFactory.createXMLReader();
			saxReader.setContentHandler(new SuperficieHandler());
			saxReader.parse(args[0]);
		} catch (Exception t) {
			t.printStackTrace();
		}
	}

}
