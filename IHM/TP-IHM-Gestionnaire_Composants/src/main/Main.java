package main;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import dialogue.TestDialogue;
import layout.AbsolutePosition;
import layout.TestBorderLayout;
import layout.TestBoxLayout;
import layout.TestFlowLayout;
import layout.TestGridBagLayout;
import layout.TestGridLayout;
import layout_personnalized.DemvBox;
import editeur.EditeurText;


public class Main {
	
	public static void buildMenu(JFrame frame) {
		JMenuBar menu_bar = new JMenuBar();
		
		JMenu dialogue = new JMenu("Dialogue");
		JMenuItem modulable = new JMenuItem("Modulable");
		modulable.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent actionEvent) {
	        	TestDialogue testDialogue = new TestDialogue();
	            testDialogue.modulable();
	        }
	    });
		JMenuItem nonModulable = new JMenuItem("Non Modulable");
		nonModulable.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent actionEvent) {
	            TestDialogue dialogue = new TestDialogue();
	            dialogue.nonModulable();
	        }
	    });
		dialogue.add(modulable);
		dialogue.add(nonModulable);
		
		JMenu layout = new JMenu("Layouts");
		JMenuItem absolute = new JMenuItem("AbsolutePosition");
		absolute.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent actionEvent) {
	        	new AbsolutePosition();
	        }
	    });
		
		JMenuItem border = new JMenuItem("BorderLayout");
		border.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent actionEvent) {
	        	new TestBorderLayout();
	        }
	    });
		
		JMenuItem box = new JMenuItem("BoxLayout");
		box.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent actionEvent) {
	        	new TestBoxLayout();
	        }
	    });
		
		JMenuItem flow = new JMenuItem("FlowLayout");
		flow.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent actionEvent) {
	        	new TestFlowLayout();
	        }
	    });
		
		JMenuItem gbl = new JMenuItem("GridBagLayout");
		gbl.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent actionEvent) {
	        	new TestGridBagLayout();
	        }
	    });
		
		JMenuItem gl = new JMenuItem("GridLayout");
		gl.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent actionEvent) {
	            new TestGridLayout();
	        }
	    });
		
		JMenu personnalized_layout_item = new JMenu("Personnalized Layout");
		JMenuItem personnalized_layout = new JMenuItem("BoxLayoutPersonnalized");
		personnalized_layout.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent actionEvent) {
	            new DemvBox();
	        }
	    });
		
		personnalized_layout_item.add(personnalized_layout);
		layout.add(border);
		layout.add(box);
		layout.add(flow);
		layout.add(gbl);
		layout.add(gl);
		layout.add(personnalized_layout_item);
		
		JMenu editeur_item = new JMenu("Editeur de text");
		JMenuItem editeur = new JMenuItem("Editeur");
		editeur.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent actionEvent) {
	            new EditeurText();
	        }
	    });
		
		editeur_item.add(editeur);
		
		menu_bar.add(dialogue);
		menu_bar.add(layout);
		menu_bar.add(editeur_item);
		
		frame.setJMenuBar(menu_bar);
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("TP gestionnaires placements des composants");
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		buildMenu(frame); 
		
		JLabel titre = new JLabel("Dhersin Jérôme : TP-IHM-gestionnaire de placements des composants");
		JPanel panneau = new JPanel();

		panneau.add(titre, BorderLayout.CENTER);
		
		frame.setContentPane(panneau);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setSize(600, 300);
		frame.setVisible(true);
		frame.setResizable(true);
	}
}