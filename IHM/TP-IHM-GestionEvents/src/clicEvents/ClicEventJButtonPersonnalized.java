package clicEvents;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ClicEventJButtonPersonnalized {
	
	static int i = 0;
	
	public ClicEventJButtonPersonnalized () {
		JFrame frame = new JFrame("Compteur");
		frame.setSize(400, 400);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JPanel panneau = new JPanel();
		GridLayout experimentLayout = new GridLayout(2, 1);
		panneau.setLayout(experimentLayout);
	
		JLabel text = new JLabel(Integer.toString(i));
		JButton bouton = new JButton("Incrémenter");
		panneau.add(text, BorderLayout.CENTER);
		panneau.add(bouton, BorderLayout.SOUTH);
	
		bouton.addActionListener(new ReponseAuClic(text));
	
		frame.setContentPane(panneau);
		frame.setLocationRelativeTo(null);
		frame.setSize(200, 200);
		frame.setVisible(true);
		frame.pack();
		frame.setResizable(true);
	}
}