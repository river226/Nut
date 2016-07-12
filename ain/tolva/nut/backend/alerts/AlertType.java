package ain.tolva.nut.backend.alerts;

import javax.swing.JOptionPane;

public enum AlertType {
	ERROR, ISSUE, ATTENTION;

	public int getType(AlertType a) {
		switch(a) {
			case ERROR:
				return JOptionPane.ERROR_MESSAGE;
			case ISSUE:
				return JOptionPane.WARNING_MESSAGE;
			case ATTENTION:
				return JOptionPane.INFORMATION_MESSAGE;
		}
		// Default
		return JOptionPane.PLAIN_MESSAGE;
	}
}
