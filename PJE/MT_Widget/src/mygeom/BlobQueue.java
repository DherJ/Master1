package mygeom;

import java.awt.Graphics2D;
import java.util.HashMap;

import com.sun.javafx.geom.Point2D;

public class BlobQueue {
	HashMap<Integer,Path> cursor;

    public BlobQueue() {
            cursor=new HashMap<Integer,Path>();
    }
    
    public void addCursor( int id, Point2 p ){
    	Path path = new Path(id);
    	path.addPoint(p);
    	cursor.put(id, path);
    }
    
    public void removeCursor( int id ){
    	cursor.remove(id);
    }
    
    public void updateCursor( int id, Point2 p){
    	cursor.get(id).addPoint(p);
    }
    
    public boolean checkIfCursorExist( int id ){
    	return cursor.containsKey(id);
    }
    
    public void drawAll(Graphics2D g){
    	for(int i=0; i<cursor.size(); i++){
    		cursor.get(i).draw(g);
    	}
    }
}
