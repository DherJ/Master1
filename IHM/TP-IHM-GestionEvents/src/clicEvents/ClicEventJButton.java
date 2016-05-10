package clicEvents;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ClicEventJButton {
	
	static int i = 0;
	
	public ClicEventJButton () {
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
	
		bouton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				i++;
				text.setText(Integer.toString(i));
			}
		});
	
		frame.setContentPane(panneau);
		frame.setLocationRelativeTo(null);
		frame.setSize(200, 200);
		frame.setVisible(true);
		frame.pack();
		frame.setResizable(true);
	}
}