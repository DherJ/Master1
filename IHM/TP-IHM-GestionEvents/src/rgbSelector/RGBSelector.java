package rgbSelector;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class RGBSelector implements ChangeListener, ActionListener {

	static final int RGB_MIN = 0;
	static final int RGB_MAX = 255;
	
	private static JSlider sliderR;
	private static JSlider sliderG;
	private static JSlider sliderB;
	
	private static JTextField saisieR = new JTextField();
	private static JTextField saisieG = new JTextField();
	private static JTextField saisieB = new JTextField();
	
	private static JTextField saisieHexa = new JTextField();
	
	JPanel colorContainer = new JPanel();


	public RGBSelector() {
		createFrame();
	}
	
	
	public void createFrame() {
		
		sliderR = new JSlider(JSlider.HORIZONTAL, RGB_MIN, RGB_MAX, 0);
		sliderR.addChangeListener(this);
		sliderG = new JSlider(JSlider.HORIZONTAL, RGB_MIN, RGB_MAX, 0);
		sliderG.addChangeListener(this);
		sliderB = new JSlider(JSlider.HORIZONTAL, RGB_MIN, RGB_MAX, 0);
		sliderB.addChangeListener(this);
		
		saisieR.setPreferredSize(new Dimension(80, 30));
		saisieR.addActionListener(this);
		saisieG.setPreferredSize(new Dimension(80, 30));
		saisieG.addActionListener(this);
		saisieB.setPreferredSize(new Dimension(80, 30));
		saisieB.addActionListener(this);
		saisieHexa.setPreferredSize(new Dimension(100, 30));
		saisieHexa.addActionListener(this);
		
		JFrame frame = new JFrame("Color Selector");
		frame.setSize(500, 400);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JPanel content = new JPanel();
		
		colorContainer.setPreferredSize(new Dimension(100, 100));
		colorContainer.setBackground(Color.CYAN);
		
		content.setBackground(Color.WHITE);
		content.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
	    gbc.gridx = 2;
	    gbc.gridwidth = GridBagConstraints.REMAINDER;
	    content.add(saisieHexa, gbc);
	    
	    gbc.gridx = 0;
	    gbc.gridy = 1;
	    gbc.gridheight = 1;
	    gbc.gridwidth = 1;
	    content.add(sliderR, gbc);
	    
	    gbc.gridx = 1;
	    gbc.gridy = 1;
	    gbc.gridheight = 1;
	    gbc.gridwidth = 1;
	    content.add(saisieR, gbc);
	    
	    gbc.gridx = 0;
	    gbc.gridy = 2;
	    gbc.gridheight = 1;
	    gbc.gridwidth = 1;
	    content.add(sliderG, gbc);
	    
	    gbc.gridx = 1;
	    gbc.gridy = 2;
	    gbc.gridheight = 1;
	    gbc.gridwidth = 1;
	    content.add(saisieG, gbc);
	    
	    gbc.gridx = 2;
	    gbc.gridy = 1;
	    gbc.gridheight = 3;
	    gbc.gridwidth = GridBagConstraints.REMAINDER;
	    gbc.fill = GridBagConstraints.VERTICAL;
	    content.add(colorContainer, gbc);
	    
	    gbc.fill = GridBagConstraints.NONE;
	    gbc.gridx = 0;
	    gbc.gridy = 3;
	    gbc.gridheight = 1;
	    gbc.gridwidth = 1;
	    content.add(sliderB, gbc);
	    
	    gbc.gridx = 1;
	    gbc.gridy = 3;
	    gbc.gridheight = 1;
	    gbc.gridwidth = 1;
	    content.add(saisieB, gbc);
	    
		frame.add(content);
		frame.setLocationRelativeTo(null);
		frame.setSize(500, 400);
		frame.setVisible(true);
		frame.pack();
		frame.setResizable(true);
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		if(e.getSource() == sliderR || e.getSource() == sliderG || e.getSource() == sliderB) {
			Color color;
			color = new Color(sliderR.getValue(), sliderG.getValue(), sliderB.getValue());
			colorContainer.setBackground(color);
			saisieR.setText(String.valueOf(sliderR.getValue()));
			saisieG.setText(String.valueOf(sliderG.getValue()));
			saisieB.setText(String.valueOf(sliderB.getValue()));
			saisieHexa.setText(String.valueOf(Integer.toHexString(color.getRGB())));
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == saisieR || e.getSource() == saisieG || e.getSource() == saisieB) {
			Color color;
			int r = Integer.parseInt(saisieR.getText());
			int g = Integer.parseInt(saisieG.getText());
			int b = Integer.parseInt(saisieB.getText());
			color = new Color(r, g, b);
			colorContainer.setBackground(color);
			sliderR.setValue(r);
			sliderG.setValue(g);
			sliderB.setValue(b);
			int rgb = color.getRGB();
			saisieHexa.setText(Integer.toHexString(rgb));
		}
		if(e.getSource() == saisieHexa) {
			Color color;
			color = Color.decode(saisieHexa.getText());
			colorContainer.setBackground(color);
			int r = color.getRed();
			int g = color.getGreen();
			int b = color.getBlue();
			sliderR.setValue(r);
			sliderG.setValue(g);
			sliderB.setValue(b);
			saisieR.setText(String.valueOf(r));
			saisieG.setText(String.valueOf(g));
			saisieB.setText(String.valueOf(b));
		}
	}
}