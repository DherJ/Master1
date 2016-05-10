package layout_personnalized;

/**
 * demovbox.java
 *
 * @author <a href="mailto:gery.casiez@lifl.fr">Gery Casiez</a>
 * @version
 */

import javax.swing.*;

    public class DemvBox {

	public DemvBox() {
	    // Creation de la fenetre
	    JFrame fenetre = new JFrame("JFrame");
	    fenetre.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);    
	    fenetre.setLocation(100,100);

	    fenetre.setLayout(new VboxLayout());
	    fenetre.add(new JButton("Bouton 1"));
	    fenetre.add(new JButton("Tres long bouton 2"));
	    fenetre.add(new JButton("Super super long bouton 3"));
	    fenetre.add(new JButton("Court bouton 4"));

	    try { 
	    	UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel"); 
	    } 
	    catch (Exception e) {}
	    
	    fenetre.setVisible(true);
	    fenetre.pack();
	}
    }