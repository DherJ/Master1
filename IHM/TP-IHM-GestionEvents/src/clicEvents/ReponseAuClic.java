package clicEvents;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;

public class ReponseAuClic implements ActionListener {
	
	JLabel label;
	
	public ReponseAuClic(JLabel _label) {
		this.label = _label;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		this.label.setText(Integer.toString(Integer.parseInt(this.label.getText()) + 1));
	}
}