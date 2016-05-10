package classeInterne;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TestClicEvent {

	private static JLabel label;
	private static JButton bouton1;
	private static JButton bouton2;
	private static JButton bouton3;
	
	static int i = 0;
	
	public TestClicEvent () {
		JFrame frame = new JFrame("Test clic Event");
		frame.setSize(400, 400);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JPanel panneau = new JPanel();
		BoxLayout boxLayout = new BoxLayout(panneau, BoxLayout.Y_AXIS);
		panneau.setLayout(boxLayout);
	
		label = new JLabel("     ");
		bouton1 = new JButton("Bouton1");
		bouton2 = new JButton("Bouton2");
		bouton3 = new JButton("Bouton3");
		panneau.add(label);
		panneau.add(bouton1);
		panneau.add(bouton2);
		panneau.add(bouton3);
	
		bouton1.addActionListener(new EventClic());
		bouton2.addActionListener(new EventClic());
		bouton3.addActionListener(new EventClic());
		
		frame.setContentPane(panneau);
		frame.setLocationRelativeTo(null);
		frame.setSize(200, 200);
		frame.setVisible(true);
		frame.pack();
		frame.setResizable(true);
	}
	
	private static class EventClic implements ActionListener {
		EventClic() {}
		
		@Override
		public void actionPerformed(ActionEvent evt) {
			if(evt.getSource() == bouton1)
				label.setText(bouton1.getText());
			if(evt.getSource() == bouton2)
				label.setText(bouton2.getText());
			if(evt.getSource() == bouton3)
				label.setText(bouton3.getText());
		}
	}
}