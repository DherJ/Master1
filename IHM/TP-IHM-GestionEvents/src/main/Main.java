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

import rgbSelector.RGBSelector;
import classeInterne.FenetreIncrementer;
import classeInterne.TestClicEvent;
import clicEvents.ClicEventJButton;
import clicEvents.ClicEventJButtonPersonnalized;
import closeOperation.FermetureFenetre;
import ardoise.ArdoiseMagique;


public class Main {
	
	public static void buildMenu(JFrame frame) {
		JMenuBar menu_bar = new JMenuBar();
		
		JMenu ardoise_item = new JMenu("Ardoise");
		JMenuItem ardoise = new JMenuItem("Ardoise Magique");
		ardoise.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent actionEvent) {
				ArdoiseMagique.main(null);
	        }
	    });
		ardoise_item.add(ardoise);
		
		JMenu colorSelect_item = new JMenu("RGB Selector");
		JMenuItem color = new JMenuItem("Color Selector");
		color.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent actionEvent) {
				new RGBSelector();
	        }
	    });
		colorSelect_item.add(color);
		
		JMenu clicEvent = new JMenu("Clic Events");
		JMenu evtStandard = new JMenu("Event Standard");
		JMenuItem compteur = new JMenuItem("Compteur");
		compteur.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent actionEvent) {
	        	new ClicEventJButton();
	        }
	    });
		evtStandard.add(compteur);
		clicEvent.add(evtStandard);
		
		JMenu evtPersonnalized = new JMenu("Event Personnalized");
		JMenuItem close = new JMenuItem("Close Operation");
		close.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent actionEvent) {
	        	new FermetureFenetre();
	        }
	    });
		evtPersonnalized.add(close);
		
		JMenuItem compteur_perso = new JMenuItem("Compteur Personnalized");
		compteur_perso.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent actionEvent) {
	        	new ClicEventJButtonPersonnalized();
	        }
	    });
		evtPersonnalized.add(compteur_perso);
		clicEvent.add(evtPersonnalized);
		
		
		JMenu classeInt = new JMenu("Classe Interne");

		JMenuItem incrementer = new JMenuItem("Fenetre Incrémenter");
		incrementer.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent actionEvent) {
	        	new FenetreIncrementer();
	        }
	    });
		JMenuItem clicEvt = new JMenuItem("Test Clic Event");
		clicEvt.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent actionEvent) {
	        	new TestClicEvent();
	        }
	    });
		classeInt.add(incrementer);
		classeInt.add(clicEvt);
		
		menu_bar.add(ardoise_item);
		menu_bar.add(colorSelect_item);
		menu_bar.add(clicEvent);
		menu_bar.add(classeInt);
		
		frame.setJMenuBar(menu_bar);
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("TP gestion des évènements");
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		buildMenu(frame); 
		
		JLabel titre = new JLabel("Dhersin Jérôme : TP-IHM-gestion des évènements");
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