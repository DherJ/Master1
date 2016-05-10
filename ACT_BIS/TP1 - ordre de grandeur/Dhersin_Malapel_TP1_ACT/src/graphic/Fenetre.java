package graphic;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import comparaisons.ComparaisonStructures;

public class Fenetre implements ActionListener, ItemListener {

	int nbFile = 0;
	final Object[] nameFunctions = new Object[] { "mesureAlea", "mesureMinSimple", "mesureMinTri", "mesureRecherche", "mesureMystere" };
	ComparaisonStructures cs = new ComparaisonStructures();
	DateFormat dateFormat;
	JFrame frame;
	JLabel authors_label;
	JLabel date_label;
	JLabel path_label;
	JTextField pathField;
	JLabel fileNameLabel;
	JTextField fileNameField;
	JLabel nbExecLabel;
	JTextField nbExecField;
	JLabel nameFunction_label;
	JComboBox<Object> listeFunctions;
	JLabel search_label;
	JTextField searchField;
	JButton valider;
	JButton exit;
	int selectedIndex;
	String functionToCall = new String();
	
	public Fenetre() {
		buildApp();
	}
	
	public void buildApp() {
		cs.setListFunctions(nameFunctions);
		
		dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		frame = new JFrame("TP1 ACT");
		frame.setSize(500, 300);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JPanel panneau = new JPanel();
		GridBagLayout experimentLayout = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		panneau.setLayout(experimentLayout);
		
		searchField = new JTextField();
		
		authors_label = new JLabel("Dhersin Jérôme - Malapel Florian");
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		panneau.add(authors_label, c);
		
		date_label = new JLabel(dateFormat.format(date));
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 2;
		c.gridy = 0;
		panneau.add(date_label, c);
		
		path_label = new JLabel("Entrez le chemin de sauvegarde du fichier :  ");
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 1;
		panneau.add(path_label, c);
		pathField = new JTextField();
		pathField.addActionListener(this);
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 2;
		c.gridy = 1;
		panneau.add(pathField, c);
		
		fileNameLabel = new JLabel("Entrez le nom du fichier :  ");
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 2;
		panneau.add(fileNameLabel, c);
		fileNameField = new JTextField();
		fileNameField.addActionListener(this);
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 2;
		c.gridy = 2;
		panneau.add(fileNameField, c);
		
		nbExecLabel = new JLabel("Entrez le nombre d'exécution :  ");
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 3;
		panneau.add(nbExecLabel, c);
		nbExecField = new JTextField();
		nbExecField.addActionListener(this);
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 2;
		c.gridy = 3;
		panneau.add(nbExecField, c);
		
		nameFunction_label = new JLabel("Choisissez la fonction à tester : ");
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 4;
		panneau.add(nameFunction_label, c);
		
		listeFunctions = new JComboBox<Object>(nameFunctions);
		listeFunctions.addItemListener(this);
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 2;
		c.gridy = 4;
		panneau.add(listeFunctions, c);
		
		search_label = new JLabel("Entrez la valeur à rechercher :  ");
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 5;
		panneau.add(search_label, c);
		searchField.addActionListener(this);
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 2;
		c.gridy = 5;
		panneau.add(searchField, c);
		
		valider = new JButton("Valider");
		valider.addActionListener(this);
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 2;
		c.gridy = 6;
		panneau.add(valider, c);
		
		exit = new JButton("Quitter");
		exit.addActionListener(this);
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 2;
		c.gridy = 7;
		panneau.add(exit, c);
		frame.add(panneau, BorderLayout.CENTER);
		frame.setContentPane(panneau);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setResizable(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(valider)) {
			String path = new String();
			String nameFile = new String();
			int valueSearch = 0;
    		if (!"".equals(this.nbExecField.getText())) {
    			cs.setNbExec(Integer.parseInt(this.nbExecField.getText()));
    		} else cs.setNbExec(2);
    		if(!"".equals(this.searchField.getText())) {
    			valueSearch = Integer.parseInt(this.searchField.getText());
    		}
    		if (!"".equals(this.pathField.getText())) {
    			path = this.pathField.getText();
    		} else path = "data";
    		if (!"".equals(this.fileNameField.getText())) {
    			nameFile = this.fileNameField.getText();
    		} else nameFile = "results_" + nbFile;
    		cs.generateData(path, nameFile, nameFunctions[selectedIndex].toString(), cs.getSizes(), valueSearch);
    		nbFile++;
    		System.out.println("Fonction exécutée.");
    	} 
		else if(e.getSource().equals(exit)) {
				System.out.println("Exit program");
				System.exit(0);
			}
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getStateChange() == ItemEvent.SELECTED) {
		       Object item = e.getItem();
		       this.functionToCall = (String) item;
		       System.out.println("this.functionToCall = " + this.functionToCall);
		}
	}
}