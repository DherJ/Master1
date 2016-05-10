package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;

import widget.MTSurface;

public class Main {

	static MTSurface surface1;
	static int frameWidth = 700, frameHeight = 650;
	static JButton visible;
	
	public static void main(String args[]) {
		surface1 = new MTSurface();
		SwingUtilities.invokeLater(new Runnable() {
		    public void run() {
		        createGui();
		    }
		});
	}	
	
	static void createGui(){
		JFrame frame = new JFrame("Multi touch");
		frame.setVisible(true);
		frame.setSize(frameWidth, frameHeight);
		frame.setLayout(new FlowLayout());
		frame.getContentPane().setBackground(Color.BLACK);
		frame.getContentPane().add(surface1);
		frame.setLocation(700, 0);
		surface1.setPreferredSize(new Dimension(650,550));
		surface1.setBackground(Color.white);
		surface1.setBorder(new LineBorder(Color.RED, 2, true));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		visible = new JButton("Visible / Invisible");
		frame.add(visible);
		visible.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e) {
	             surface1.visible = !(surface1.visible);
	          }          
	       });
	}
}
