package ardoise;
/**
 * ArdoiseMagique.java
 *
 * @author <a href="mailto:gery.casiez@lifl.fr">Gery Casiez</a>
 * @version
 */

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.*;

import java.util.ArrayList;
import java.util.Iterator;

class Point {
	public Integer x,y;

	Point() {
		x = 0;
		y = 0;
	}

	Point(Integer x, Integer y) {
		this.x = x;
		this.y = y;
	}
}

class Curve {
	public ArrayList<Point> points;

	Curve() {
		points = new ArrayList<Point>();
	}

	public void addPoint(Point P) {
		points.add(P);
	}

	public void clear() {
		points.clear();
	}
}

public class ArdoiseMagique extends JPanel implements MouseListener, MouseMotionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Curve> curves;

	Color[] colors = { Color.CYAN, Color.BLUE, Color.GRAY, Color.GREEN, Color.ORANGE, Color.MAGENTA, Color.WHITE };
	
	
	public ArdoiseMagique() {
		curves = new ArrayList<Curve>();
		curves.add(new Curve());
		setBackground(Color.white);
		addMouseListener(this);
		addMouseMotionListener(this);
	}

	public void addPoint(Integer x, Integer y) {
		curves.get(curves.size()-1).addPoint(new Point(x,y));
		repaint();
	}

	public void newCurve() {
		curves.add(new Curve());
	}

	public void clear() {
		curves.clear();
		curves.add(new Curve());
		repaint();
	}

	public void paintComponent(Graphics g) {
		Point Pprev, Pcurrent;
		super.paintComponent(g);

		Iterator<Curve> itcurve = curves.iterator();

		Pprev = new Point();

		// Pour chaque courbe
		while (itcurve.hasNext()) {
			Iterator<Point> it = itcurve.next().points.iterator();

			if (it.hasNext()) {
				Pprev = it.next();
			}

			// Dessine les points d'une courbe
			while (it.hasNext()) {
				Pcurrent = it.next();
				g.drawLine(Pprev.x,Pprev.y, Pcurrent.x, Pcurrent.y);
				Pprev = Pcurrent;
			}
		}
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("Ardoise magique");
		frame.setSize(400, 400);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JPanel panneau = new ArdoiseMagique();
		frame.add(panneau, BorderLayout.CENTER);
		
		frame.setContentPane(panneau);
		frame.setLocationRelativeTo(null);
		frame.setSize(200, 200);
		frame.setVisible(true);
		frame.setResizable(true);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		switch(e.getButton()) {
		case MouseEvent.BUTTON1 : 
			addPoint((int)e.getPoint().getX(), (int)e.getPoint().getY());
			break;
		case MouseEvent.BUTTON2 : 
			this.setBackground(colors[(int) (Math.random() * colors.length)]);
			break;
		case MouseEvent.BUTTON3 : 
			clear();
			repaint();
			break;
		}
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		addPoint((int)e.getPoint().getX(), (int)e.getPoint().getY());
		repaint();
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		newCurve();
	}
	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
	@Override
	public void mouseMoved(MouseEvent e) {}
}