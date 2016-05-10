package layout;

import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class TestBoxLayout {

	public TestBoxLayout() {
		JFrame frame = new JFrame("BoxLayout");
		
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

//		Placements des boutons avec le layout BorderLayout
		JButton bouton1 = new JButton("bouton1");
		JButton bouton2 = new JButton("bouton2");
		JButton bouton3 = new JButton("bouton3");
		JButton bouton4 = new JButton("bouton4");
		JButton bouton5 = new JButton("bouton5");
		
		JPanel panneau = new JPanel();
		BoxLayout experimentLayout = new BoxLayout(panneau, BoxLayout.Y_AXIS);
		
		panneau.setLayout(experimentLayout);
		panneau.add(bouton1);
		panneau.add(bouton2);
		
		Box.Filler glue = (Box.Filler) Box.createVerticalGlue();
	    glue.changeShape(glue.getMinimumSize(), 
	           new Dimension(0, Short.MAX_VALUE),    // espace entre les boutons 2 et 3
	           glue.getMaximumSize());
	    
	    panneau.add(glue);
	    
		panneau.add(bouton3);
		panneau.add(bouton4);
		panneau.add(bouton5);
	    
		frame.setContentPane(panneau);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setSize(600, 300);
		frame.setVisible(true);
		frame.setResizable(true);
	}
}