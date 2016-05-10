package classeInterne;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class FenetreIncrementer {
	private static JLabel label;
	
	static int i = 0;
	
	public FenetreIncrementer () {
		JFrame frame = new JFrame("Compteur");
		frame.setSize(400, 400);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JPanel panneau = new JPanel();
		GridLayout experimentLayout = new GridLayout(2, 1);
		panneau.setLayout(experimentLayout);
	
		label = new JLabel(Integer.toString(i));
		JButton bouton = new JButton("Incrémenter");
		panneau.add(label, BorderLayout.CENTER);
		panneau.add(bouton, BorderLayout.SOUTH);
	
		bouton.addActionListener(new ReponseAuClic());
		/** Ou 
		bouton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
				label.setText(Integer.toString(Integer.parseInt(label.getText()) + 1));
			}
		});
		**/
		frame.setContentPane(panneau);
		frame.setLocationRelativeTo(null);
		frame.setSize(200, 200);
		frame.setVisible(true);
		frame.pack();
		frame.setResizable(true);
	}
	
	private static class ReponseAuClic implements ActionListener {
		ReponseAuClic() {}
		@Override
		public void actionPerformed(ActionEvent e) {
			label.setText(Integer.toString(Integer.parseInt(label.getText()) + 1));
		}
	}
}