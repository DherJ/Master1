package e214.skeleton ;

import fr.lri.swingstates.canvas.Canvas ;
import fr.lri.swingstates.canvas.CShape ;
import fr.lri.swingstates.canvas.CRectangle ;
import fr.lri.swingstates.canvas.CSegment ;
import fr.lri.swingstates.canvas.CTag ;
import fr.lri.swingstates.canvas.CExtensionalTag ;
import fr.lri.swingstates.canvas.CStateMachine ;
import fr.lri.swingstates.debug.StateMachineVisualization;
import fr.lri.swingstates.sm.State;
import fr.lri.swingstates.sm.Transition;
import fr.lri.swingstates.sm.transitions.Drag ;
import fr.lri.swingstates.sm.transitions.Press;
import fr.lri.swingstates.sm.transitions.Release ;

import java.awt.Color ;
import java.awt.geom.Point2D ;

import javax.swing.JFrame ;


/**
 * @author Nicolas Roussel (roussel@lri.fr)
 * 		   Dhersin Jérôme
 *
 */
public class MagneticGuides extends JFrame {


	private static final long serialVersionUID = 1L;
	private Canvas canvas ;
    private CExtensionalTag oTag ;

    public MagneticGuides(String title, int width, int height) {
	   super(title) ;
	   canvas = new Canvas(width, height) ;
	   canvas.setAntialiased(true) ;
	   getContentPane().add(canvas) ;

	   oTag = new CExtensionalTag(canvas) {} ;

	   CStateMachine sm = new CStateMachine() {

			 private Point2D p ;
			 private CShape draggedShape ;

			 @SuppressWarnings("unused")
			public State start = new State() {
				    Transition pressOnObject = new PressOnTag(oTag, BUTTON1, ">> oDrag") {
						  public void action() {
							 p = getPoint() ;
							 draggedShape = getShape() ;
						  }
					   } ;
					   // clic gauche pour créer un guide horizontal
					   Transition create_guide_hori = new Press(BUTTON1) {
			                public void action() {
			                	MagneticGuide m = new MagneticGuide (canvas);
			                	m.drawGuideHoriz (getPoint(), oTag);
			                }
			            };
			            // clic droit pour créer un guide vertical
			            Transition create_guide_vert = new Press(BUTTON3) {
			                public void action() {
			                	MagneticGuide m = new MagneticGuide (canvas);
			                	m.drawGuideVert (getPoint(), oTag);
			                }
			            };
				} ;

			 @SuppressWarnings("unused")
			public State oDrag = new State() {
				 	// clic gauche pour déplacer les éléments
				    Transition drag = new Drag(BUTTON1) {
						  public void action() {
							 Point2D q = getPoint() ;
							 // déplacement d'un guide
							 if(CSegment.class.isInstance(draggedShape)) {
								 // pour chaque rectangle
								 for (CShape rectangle : canvas.getDisplayList()) {
									 if(CRectangle.class.isInstance(rectangle)) {
										 // on récupère la liste des tags
										 for(CTag tag : canvas.getAllTags()) {
											 // si le tag est un MagneticGuide et que le draggedShape contient ce tag
											 if(MagneticGuide.class.isInstance(tag) && rectangle.hasTag(tag)) {
												 // si c'est un guide horizontal
											 	if(((MagneticGuide) tag).isHorizontal()) {
											 		if(rectangle.getCenterY() >= (draggedShape.getCenterY() - 2) && 
											 				rectangle.getCenterY() <= (draggedShape.getCenterY() + 2)) {
											 			rectangle.translateBy(0, q.getY() - p.getY());
											 		}
											 	}
											 	else {
											 		// si c'est un guide vertical
											 		if(rectangle.getCenterX() >= (draggedShape.getCenterX() - 2) && 
											 				rectangle.getCenterX() <= (draggedShape.getCenterX() + 2)) {
											 			rectangle.translateBy(q.getX() - p.getX(), 0); 
											 		}
											 	}
											 }
										 }
									 }
								 }
							 } else {
								// déplacement d'un rectangle
								 // parcours de ous les guides
								 for (CShape line : canvas.getDisplayList()) {
									 if(CSegment.class.isInstance(line))
										 // si le rectangle est positionné sur un guide
										 if((line.getCenterY()+2>=draggedShape.getCenterY() && line.getCenterY()-2<=draggedShape.getCenterY()) ||
												 (line.getCenterX()+2>=draggedShape.getCenterX() && line.getCenterX()-2<=draggedShape.getCenterX())) {
											 for(CTag tag : canvas.getAllTags()) {
												 // on ajoute le tag d'un guide au rectangle qu'on déplace
												 if(MagneticGuide.class.isInstance(tag) && line.hasTag(tag)) {
													 draggedShape.addTag(tag); // on attache le rectangle au guide
												 }
											 }
										 } else {
											 // si on éloigne le rectangle d'un guide
											 for(CTag tag : canvas.getAllTags()) {
												 if(MagneticGuide.class.isInstance(tag) && line.hasTag(tag)) {
													 draggedShape.removeTag((CExtensionalTag) tag); // on détache le rectangle du guide
												 }
											 }
										 }
								}
							 }
							 draggedShape.translateBy(q.getX() - p.getX(), q.getY() - p.getY()) ;
							 p = q ;
					   }  // fin de void action()
				    } ; // fin de transition drag
				    Transition release = new Release(BUTTON1, ">> start") {} ;
				} ; // fin de state oDrag
		  } ; // fin de la state machine
	   
	   sm.attachTo(canvas);

	   pack() ;
	   setVisible(true) ;
	   canvas.requestFocusInWindow() ;
	   JFrame vm = new JFrame();
		vm.getContentPane().add(new StateMachineVisualization(sm));
		vm.pack();
		vm.setVisible(true);
    }

    public void populate() {
	   int width = canvas.getWidth() ;
	   int height = canvas.getHeight() ;

	   double s = (Math.random()/2.0+0.5)*30.0 ;
	   double x = s + Math.random()*(width-2*s) ;
	   double y = s + Math.random()*(height-2*s) ;

	   int red = (int)((0.8+Math.random()*0.2)*255) ;
	   int green = (int)((0.8+Math.random()*0.2)*255) ;
	   int blue = (int)((0.8+Math.random()*0.2)*255) ;

	   CRectangle r = canvas.newRectangle(x,y,s,s) ;
	   r.setFillPaint(new Color(red, green, blue)) ;
	   r.addTag(oTag) ;
    }

    public static void main(String[] args) {
	   MagneticGuides guides = new MagneticGuides("Magnetic guides",600,600) ;
	   for (int i=0; i<20; ++i) guides.populate() ;
	   guides.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE) ;
    }
}