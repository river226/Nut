package ain.tolva.nut.backend.alerts;

import javax.swing.JOptionPane;

public class Alerter implements Runnable {

	private static final long serialVersionUID = 1L;
	private AlertType alert;
	private String message;
	private JOptionPane alertWindow;

	public Alerter(AlertType a, String m) {
		setAlert(a);
		setMessage(m);
	}

	public void setAlert(AlertType a) {
		alert = a;
	}

	public void setMessage(String m) {
		message = m;
	}

	private void createAlert() {
		alertWindow.setMessageType(alert.getType(alert));
		alertWindow.createDialog(message);
	}

	@Override
	public void run() {
		createAlert();
	}
}
