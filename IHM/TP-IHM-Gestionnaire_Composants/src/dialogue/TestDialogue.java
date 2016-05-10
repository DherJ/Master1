package dialogue;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TestDialogue {

	public void modulable() {
		JFrame frame = new JFrame("Dialogue modulable");
		JPanel panneau = new JPanel();
		JDialog dialog = new JDialog(frame);
		dialog.setModal(true);
		dialog.setVisible(true);
		JLabel label = new JLabel("Hello world!!");
		panneau.add(label);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setContentPane(panneau);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setSize(600, 300);
		frame.setVisible(true);
		frame.setResizable(true);
	}
	
	public void nonModulable() {
		JFrame frame = new JFrame("Dialogue non modulable");
		JPanel panneau = new JPanel();
		JDialog dialog = new JDialog(frame);
		dialog.setModal(false);
		dialog.setVisible(true);
		JLabel label = new JLabel("Hello world!!");
		panneau.add(label);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setContentPane(panneau);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setSize(600, 300);
		frame.setVisible(true);
		frame.setResizable(true);
	}
}