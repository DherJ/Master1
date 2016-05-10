package e214.skeleton;

import java.awt.BasicStroke;
import java.awt.geom.Point2D;

import fr.lri.swingstates.canvas.CExtensionalTag;
import fr.lri.swingstates.canvas.CSegment;
import fr.lri.swingstates.canvas.Canvas;

/**
 * 
 * @author Dhersin Jérôme
 *
 */
public class MagneticGuide extends CExtensionalTag {
	
	CSegment segment;
	boolean horizontal;
	
	final float EPAISSEUR = 1;              /** taille de la ligne */
	final float[] STYLE = {10,5};           /** les pointillés seront 2 fois plus long que les blancs */
	
	    
	public MagneticGuide (Canvas canvas) {
		super(canvas);
	}
	
	public boolean isHorizontal() {
		return this.horizontal;
	}
	
	public boolean isVertical() {
		return this.horizontal;
	}
	
	public void drawGuideHoriz (Point2D p, CExtensionalTag tag) {
		this.segment = (CSegment) canvas.newSegment (
			p.getX()-canvas.getWidth(),
			p.getY(),
			p.getX()+canvas.getWidth(),
			p.getY()).setStroke( new BasicStroke ( 
			         EPAISSEUR,
			         BasicStroke.CAP_BUTT,
			         BasicStroke.JOIN_MITER,
			         10.0f,
			         STYLE,
			         0
			         ));
		this.horizontal = true;
		this.segment.addTag(this);
		this.segment.addTag(tag);
	}
	
	public void drawGuideVert (Point2D p, CExtensionalTag tag) {
		this.segment = (CSegment) canvas.newSegment (
			p.getX(),
			p.getY()-canvas.getHeight(),
			p.getX(),
			p.getY()+canvas.getHeight()).setStroke( new BasicStroke ( 
			         EPAISSEUR,
			         BasicStroke.CAP_BUTT,
			         BasicStroke.JOIN_MITER,
			         10.0f,
			         STYLE,
			         0
			         ));
		this.horizontal = false;
		this.segment.addTag(this);
		this.segment.addTag(tag);
	}
}