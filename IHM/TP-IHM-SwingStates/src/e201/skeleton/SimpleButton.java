package e201.skeleton ;

import fr.lri.swingstates.canvas.CExtensionalTag;
import fr.lri.swingstates.canvas.CRectangle;
import fr.lri.swingstates.canvas.CStateMachine;
import fr.lri.swingstates.canvas.Canvas ;
import fr.lri.swingstates.canvas.CShape ;
import fr.lri.swingstates.canvas.CText ;
import fr.lri.swingstates.debug.StateMachineVisualization;
import fr.lri.swingstates.sm.State;
import fr.lri.swingstates.sm.Transition;
import fr.lri.swingstates.sm.transitions.TimeOut;

import javax.swing.JFrame ;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font ;
import java.awt.Paint;



public class SimpleButton {

    private CText label ;
    private CRectangle rect;
    
    SimpleButton(Canvas canvas, String text) {
	   label = canvas.newText(0, 0, text, new Font("verdana", Font.PLAIN, 12)) ;
	   rect = new CRectangle(label.getMinX() - 10,label.getMinY() - 10 ,100 ,30); // d�finition du Cshape rectangle
	   rect.setFillPaint(Color.CYAN);
	   canvas.addShape(rect);                  // ajoute le shape au canvas 
	   label.above(rect);                      // affiche le text par dessus le rectangle
	   label.addChild(rect);                   // on lie l'objet label � l'objet rect pour lier leurs coordonn�es
	   										   // si on d�place l'objet label, l'objet rect sera d�plac� avec les m�me coordonn�es
	   final Paint DEFCOLOR_RECT = rect.getFillPaint();
	   final Paint DEFCOLOR_LABEL = label.getFillPaint();
	   
	   // tag qui indique si le curseur de souris se trouve sur un �l�ment
	   CExtensionalTag select = new CExtensionalTag(canvas) {
		    public void added(CShape s) { 
		        s.setOutlined(true).setStroke(new BasicStroke(3));
		    }
		    public void removed(CShape s) {
		        s.setStroke(new BasicStroke(1));
		    }
		};
		
		// tag qui indique si on clik sur un �l�ment
		CExtensionalTag clik = new CExtensionalTag(canvas) {
		    public void added(CShape s) { 
		    	s.setFillPaint(Color.YELLOW);
		    }
		    public void removed(CShape s) {
		    	if(CRectangle.class.isInstance(s)) {
		    		s.setFillPaint(DEFCOLOR_RECT);
		    	} else if(CText.class.isInstance(s)){
		    		s.setFillPaint(DEFCOLOR_LABEL);
		    	}
		    }
		};
		
		/* la m�chine d'�tats est constitu�e de 3 �tats :
				- init : le curseur de la souris se trouve en dehores des �l�ments
				- on   : le curseur de la sours est situ� sur un �l�ment
				- pr : on clik sur un �l�ment
				
		*/
	   CStateMachine sm = new CStateMachine() {
		    @SuppressWarnings("unused")
			public State init = new State() {
		    	// si on place le curseur de souris sur un �l�ment
		        Transition enterBox = new EnterOnShape(">> on") {
		            public void action() {
		                getShape().addTag(select); // on ajoute le tag correspondant � l'action voulue
		            }
		        };
		    };
		    @SuppressWarnings("unused")
			public State on = new State() {
		    	// si on clik sur un �l�ment
		        Transition press = new PressOnShape(BUTTON1, ">> pr") { 
		            public void action() {
		                getShape().addTag(clik);
		                armTimer(2000, true);
		            }
		        };
		        Transition timeOut = new TimeOut() {
		        	public void action() {
		        		System.out.println("b ok");
		        	}
		        };
		        // si on place le curseur en dehors d'un �l�ment
		        Transition leave = new LeaveOnShape(">> init") {
		            public void action() {
		                getShape().removeTag(select);
		            }
		        };
		    };
		    @SuppressWarnings("unused")
			public State pr = new State() {
		    	// si on relache le clik de souris sur un �l�ment
		        Transition release = new ReleaseOnShape(BUTTON1, ">> on") {
		            public void action() {
		                getShape().removeTag(clik);
		            }
		        };
		        // si on place le curseur de la souris en dehors d'un �l�ment
		        Transition leave = new LeaveOnShape(">> init") {
		            public void action() {
		                getShape().removeTag(select);
		            }
		        };
		        Transition press = new PressOnShape(BUTTON1, ">> on") {
		            public void action() {
		                getShape().addTag(clik);
		                armTimer(2000, true);
		            }
		        };
		        Transition timeOut = new TimeOut() {
		        	public void action() {
		        		disarmTimer();
		        		if(CRectangle.class.isInstance(getShape())) {
		        			getShape().setFillPaint(DEFCOLOR_RECT);
				    	} else if(CText.class.isInstance(getShape())) {
				    		getShape().setFillPaint(DEFCOLOR_LABEL);
				    	}
		        	}
		        };
		    };
		};
		sm.attachTo(canvas);
		
		// affichage de la machine d'�tats
		JFrame vm = new JFrame();
		vm.getContentPane().add(new StateMachineVisualization(sm));
		vm.pack();
		vm.setVisible(true);
    }

    public void action() {
	   System.out.println("ACTION!") ;
    }

    public CShape getShape() {
	   return label ;
    }

    static public void main(String[] args) {
	   JFrame frame = new JFrame() ;
	   Canvas canvas = new Canvas(400,400) ;
	   frame.getContentPane().add(canvas) ;
	   frame.pack() ;
	   frame.setVisible(true) ;

	   SimpleButton simple = new SimpleButton(canvas, "simple") ;
	   simple.getShape().translateBy(100,100) ;
    }

}
