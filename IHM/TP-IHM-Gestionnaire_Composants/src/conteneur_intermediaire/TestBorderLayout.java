package conteneur_intermediaire;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class TestBorderLayout {

	public static void main(String[] args) {
		JFrame frame = new JFrame("BorderLayout");
		
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

//		Placements des boutons avec le layout BorderLayout
		JPanel nord_panel = new JPanel();
		JButton bouton1 = new JButton("bouton1");
		JButton bouton2 = new JButton("bouton2");
		JButton bouton3 = new JButton("bouton3");
		
		nord_panel.add(bouton1);
		nord_panel.add(bouton2);
		nord_panel.add(bouton3);
		
		JButton est = new JButton("Est");
		JButton sud = new JButton("Sud");
		JButton oeust = new JButton("Ouest");
		JButton center = new JButton("Centre");
		
		JPanel panneau = new JPanel();
		BorderLayout experimentLayout = new BorderLayout();
		panneau.setLayout(experimentLayout);
		panneau.add(nord_panel, BorderLayout.NORTH);
		panneau.add(sud, BorderLayout.SOUTH);
		panneau.add(est, BorderLayout.EAST);
		panneau.add(oeust, BorderLayout.WEST);
		panneau.add(center, BorderLayout.CENTER);
		
		frame.setContentPane(panneau);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setSize(600, 300);
		frame.setVisible(true);
		frame.setResizable(true);
	}
}