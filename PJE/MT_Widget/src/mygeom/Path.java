package mygeom;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.GeneralPath;
import java.util.ArrayList;

public class Path {
	public int id;
	public ArrayList<Point2> way;	
	
	public Path( int idCursor ) {
		id = idCursor;
		way  = new ArrayList<Point2>();
	}
	
	public void addPoint( Point2 p ){
		way.add(p);
	}
	
	public void removePoint( int id ){
		way.remove(id);
	}
	
	public void clear(){
		way.clear();
		System.out.print("\nListe de point supprimé");
	}
	
	public Point2 getIsobarycentre(){
		double x=0, y=0;
		for(int i=0; i<way.size(); i++){
			x += way.get(i).getX();
			y += way.get(i).getY();
		}
		return new Point2( x/way.size() , y/way.size() );
	}
	
	public void drawIsobarycentre( Graphics2D g ){
		Point2 isoB = getIsobarycentre();
		g.setColor(Color.red);
		g.fillOval( (int) (isoB.getX() * 650), (int) (isoB.getY() * 550), 5, 5 );
		g.setColor(Color.blue);
	}
	
	public void draw(Graphics2D g){
		if( way.size() > 0 ){
			GeneralPath gp = new GeneralPath(GeneralPath.WIND_EVEN_ODD, way.size());
			g.setColor(Color.blue);
			double x, y;
			x = way.get(0).getX() * 650;
			y = way.get(0).getY() * 550;
			gp.moveTo(x,y);
			for(int i=1; i<way.size(); i++){
				x = way.get(i).getX() * 650;
				y = way.get(i).getY() * 550;
				gp.lineTo(x,y);
			}
			g.fillOval((int) x-3, (int) y-3, 6, 6);
			g.drawString(""+id, (int) x-5, (int) y-5);
			drawIsobarycentre(g);
			 
			g.draw(gp);
		}

		
	}
}
