package editeur;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class EditeurText {
	
	Color[] colors = { Color.CYAN, Color.BLUE, Color.GRAY, Color.GREEN, Color.ORANGE, Color.MAGENTA, Color.WHITE };
	
	public static void buildMenu(JFrame frame) {
		JMenuBar menu_bar = new JMenuBar();
		JMenu fichier = new JMenu("Fichier");
		JMenuItem nouveau = new JMenuItem("Nouveau");
		JMenuItem ouvrir = new JMenuItem("Ouvrir");
		JMenuItem enregistrer = new JMenuItem("Enregistrer");
		JMenuItem enregistrer_sous = new JMenuItem("Enregistrer sous");
		JMenuItem mise_en_page = new JMenuItem("Mise en page");
		JMenuItem imprimer = new JMenuItem("Imprimer");
		JMenuItem quitter = new JMenuItem("Quitter");
		
		fichier.add(nouveau);
		fichier.add(ouvrir);
		fichier.add(enregistrer);
		fichier.add(enregistrer_sous);
		fichier.add(mise_en_page);
		fichier.add(imprimer);
		fichier.add(quitter);
		
		JMenu editer = new JMenu("Edition");
		JMenu format = new JMenu("Format");
		
		menu_bar.add(fichier);
		menu_bar.add(editer);
		menu_bar.add(format);
		
		frame.setJMenuBar(menu_bar);
	}
	
	public EditeurText() {
		JFrame frame = new JFrame("Editeur de text");
		frame.setSize(400, 400);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JPanel panneau = new JPanel();
		panneau.setLayout(new BorderLayout(5, 5));
		buildMenu(frame);
		
		JTextArea saisie = new JTextArea(20, 30); // zone de saisie de texte
		saisie.setBackground(Color.LIGHT_GRAY);
		panneau.add(saisie, BorderLayout.NORTH); 
		
		JPanel bot = new JPanel();
		FlowLayout flowLayout = new FlowLayout(); 
		
		bot.setLayout(flowLayout);
		
		JLabel recherche = new JLabel("Rechercher : ");
		JTextField saisie_recherche = new JTextField(10);
		JButton gauche = new JButton(new ImageIcon("ressources/gauche.gif"));
		
		gauche.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent actionEvent) {
	        	saisie.setBackground(colors[(int) (Math.random() * colors.length)]);
	        }
	    });
		
		JButton droite = new JButton(new ImageIcon("ressources/droite.gif"));
		
		droite.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent actionEvent) {
	        	saisie.setBackground(colors[(int) (Math.random() * colors.length)]);
	        }
	    });
		
		JButton surligner = new JButton("Surligner");
		
		String text = saisie.getText();
		surligner.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent actionEvent) {
	        	String resu = text.toUpperCase();
	        	saisie.setText(resu);
	        }
	    });
		
		bot.add(recherche);
		bot.add(saisie_recherche);
		bot.add(gauche);
		bot.add(droite);
		bot.add(surligner);
		
		panneau.add(bot, BorderLayout.WEST);
		
		frame.setContentPane(panneau);
		frame.setLocationRelativeTo(null);
		frame.setSize(600, 300);
		frame.setVisible(true);
		frame.pack();
		frame.setResizable(true);
	}
}