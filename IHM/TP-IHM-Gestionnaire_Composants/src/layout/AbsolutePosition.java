package layout;


import java.awt.Dimension;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class AbsolutePosition {

	public AbsolutePosition() {
		JFrame frame = new JFrame("Absolute Position");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JPanel panneau = new JPanel();
		panneau.setLayout(null);
		
		JButton bouton1 = new JButton("bouton1");
		Dimension size_b1 = bouton1.getPreferredSize();
		JButton bouton2 = new JButton("bouton2");
		Dimension size_b2 = bouton2.getPreferredSize();
		JButton bouton3 = new JButton("bouton3");
		Dimension size_b3 = bouton3.getPreferredSize();
		JButton bouton4 = new JButton("bouton4");
		Dimension size_b4 = bouton4.getPreferredSize();
		JButton bouton5 = new JButton("bouton5");
		Dimension size_b5 = bouton5.getPreferredSize();
		
		// positionnement et dimensionnement manuel des boutons
		Insets insets = panneau.getInsets();
        bouton1.setBounds(insets.left, insets.top, size_b1.width, size_b1.height);
        bouton2.setBounds((insets.left + size_b2.width) - 30, (size_b2.height + insets.top) + 10, size_b2.width, size_b2.height);
        bouton3.setBounds(((insets.left + size_b3.width) * 2) - 60, ((size_b2.height + insets.top) * 2) + 20, size_b3.width, size_b3.height);
        bouton4.setBounds(((insets.left + size_b3.width) * 3) - 90, ((size_b2.height + insets.top) * 3) + 30, size_b4.width, size_b4.height);
        bouton5.setBounds(((insets.left + size_b4.width) * 4) - 120, ((size_b2.height + insets.top) * 4) + 40, size_b5.width, size_b5.height);
		
        panneau.add(bouton1);
        panneau.add(bouton2);
        panneau.add(bouton3);
        panneau.add(bouton4);
        panneau.add(bouton5);
        
		frame.setContentPane(panneau);
		frame.setLocationRelativeTo(null);
		frame.setSize(600, 300);
		frame.setVisible(true);
		//frame.pack();
		frame.setResizable(true);
	}
}