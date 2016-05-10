package closeOperation;
import java.awt.event.*;


public class GestEventClose extends WindowAdapter implements WindowListener, ActionListener {

	public void windowClosing(WindowEvent e) {
		System.out.println("Fenetre en cours de fermeture...");
		System.exit(0);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
	}
}