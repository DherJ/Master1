package widget;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JPanel;

import mygeom.BlobQueue;
import mygeom.Path;
import mygeom.Point2;
import tuio.MTedt;

public class MTSurface extends JPanel {
	
	public MTedt handler;
	public BlobQueue listPath;
	
	public boolean visible = true;
	
	public MTSurface() {
		super();
		handler = new MTedt(this);
		listPath = new BlobQueue();
	}

	public synchronized void addCursor(int id, Point2 p){
		listPath.addCursor(id, p);
		repaint();
	}
	
	public synchronized void updateCursor(int id, Point2 p){
		listPath.updateCursor(id, p);
		repaint();
	}
	
	public synchronized void removeCursor(int id, Point2 p){
		listPath.removeCursor(id);
		repaint();
	}
	
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		if(visible) listPath.drawAll(g2);
	}

}
