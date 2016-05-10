package layout;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class TestGridBagLayout {

	public TestGridBagLayout() {
		JFrame frame = new JFrame("GridBagLayout");
		
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JPanel panneau = new JPanel();
		GridBagLayout experimentLayout = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		panneau.setLayout(experimentLayout);
		
//		Placements des boutons avec le layout GridBagLayout
		JButton bouton1 = new JButton("bouton1");
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		panneau.add(bouton1, c);
		
		JButton bouton2 = new JButton("bouton 2");
		c.gridx = 1;
		c.gridy = 0;
		panneau.add(bouton2, c);

		JButton bouton3 = new JButton("bouton3");
		c.gridx = 2;
		c.gridy = 0;
		panneau.add(bouton3, c);

		JButton bouton4 = new JButton("bouton4");
		c.gridx = 3;
		c.gridy = 0;
		panneau.add(bouton4, c);

		JButton bouton5 = new JButton("bouton5");
		c.gridx = 0;       
		c.gridy = 1;      
		c.gridwidth = 4;   //2 columns wide
		panneau.add(bouton5, c);
		
		JButton bouton6 = new JButton("bouton6");
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 3;
		panneau.add(bouton6, c);
		
		JButton bouton7 = new JButton("bouton7");
		c.gridx = 3;
		c.gridy = 2;
		panneau.add(bouton7, c);

		JButton bouton8 = new JButton("bouton8");
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 1;
		c.gridheight = 2;
		panneau.add(bouton8, c);

		JButton bouton9 = new JButton("bouton9");
		c.gridx = 1;
		c.gridy = 3;
		c.gridwidth = 3;
		c.gridheight = 1;
		panneau.add(bouton9, c);

		JButton bouton10 = new JButton("bouton10");
		c.gridx = 1; 
		c.gridy = 4; 
		c.gridwidth = 3;   
		panneau.add(bouton10, c);
		
		frame.setContentPane(panneau);
		frame.setLocationRelativeTo(null);
		frame.setSize(600, 300);
		frame.setVisible(true);
		frame.pack();
		frame.setResizable(true);
	}
}