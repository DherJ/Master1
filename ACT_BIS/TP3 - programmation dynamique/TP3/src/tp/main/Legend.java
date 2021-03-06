package tp.main;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Legend {

	private JLabel clicLeftDesc;
	private JLabel clicRightDesc;
	private Image iconLeftClic;
	private Image iconRightClic;
	
	public Legend () {
		this.clicLeftDesc = new JLabel("Supprimer une ligne");
		this.clicRightDesc = new JLabel("Supprimer une colonne");
		this.clicLeftDesc = new JLabel("Supprimer une ligne");
		this.clicRightDesc = new JLabel("Supprimer une colonne");
		this.clicLeftDesc.setBounds(10, 300, 150, 300);
		this.clicRightDesc.setBounds(10, 330, 150, 330);
		this.iconLeftClic = new ImageIcon("images/clic-gauche.jpg").getImage();
		this.iconRightClic = new ImageIcon("images/clic-droit.jpg").getImage();
	}

	public Image getIconLeftClic() {
		return iconLeftClic;
	}

	public void setIconLeftClic(Image iconLeftClic) {
		this.iconLeftClic = iconLeftClic;
	}

	public Image getIconRightClic() {
		return iconRightClic;
	}

	public void setIconRightClic(Image iconRightClic) {
		this.iconRightClic = iconRightClic;
	}
	
	public JLabel getClicLeftDesc() {
		return clicLeftDesc;
	}

	public void setClicLeftDesc(JLabel clicLeftDesc) {
		this.clicLeftDesc = clicLeftDesc;
	}

	public JLabel getClicRightDesc() {
		return clicRightDesc;
	}

	public void setClicRightDesc(JLabel clicRightDesc) {
		this.clicRightDesc = clicRightDesc;
	}
}