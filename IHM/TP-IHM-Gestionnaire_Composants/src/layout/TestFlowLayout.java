package layout;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class TestFlowLayout {

	public TestFlowLayout() {
		JFrame frame = new JFrame("FlowLayout");
		
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
//		Placements des boutons avec le layout FlowLayout
		JButton bouton1 = new JButton("bouton1");
		JButton bouton2 = new JButton("bouton2");
		JButton bouton3 = new JButton("bouton3");
		JButton bouton4 = new JButton("bouton4");
		JButton bouton5 = new JButton("bouton5");
		JButton bouton6 = new JButton("bouton6");
		JButton bouton7 = new JButton("bouton7");
		JButton bouton8 = new JButton("bouton8");
		JButton bouton9 = new JButton("bouton9");
		JButton bouton10 = new JButton("bouton10");
		
		JPanel panneau = new JPanel();
		FlowLayout experimentLayout = new FlowLayout();
		
		panneau.setLayout(experimentLayout);
		panneau.add(bouton1);
		panneau.add(bouton2);
		panneau.add(bouton3);
		panneau.add(bouton4);
		panneau.add(bouton5);
		panneau.add(bouton6);
		panneau.add(bouton7);
		panneau.add(bouton8);
		panneau.add(bouton9);
		panneau.add(bouton10);
		
		frame.setContentPane(panneau);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setSize(600, 300);
		frame.setVisible(true);
		frame.setResizable(true);
	}
}