package event;

import java.awt.AWTEvent;

public class ChangedSideEvent extends AWTEvent {
	public boolean leftSide; 
	
	public ChangedSideEvent(Object source, int id) {
		super(source, id);
		// TODO Auto-generated constructor stub
	}

}
