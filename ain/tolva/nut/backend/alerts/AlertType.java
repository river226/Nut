package ain.tolva.nut.backend.alerts;

import java.awt.Image;

public enum AlertType {
	ERROR, ISSUE, ATTENTION;
	
	public Image getImage(AlertType at) {
		switch(at){
			case ERROR:
				break;
			case ISSUE:
				break;
			case ATTENTION:
				break;
			default:
				break;
		}
		
		return null;
	}
}
