package ain.tolva.nut.backend.alerts;

import javax.swing.JOptionPane;

public class Alerter implements Runnable {

	private static final long serialVersionUID = 1L;
	private AlertType alert;
	private String message;

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
        JOptionPane.showMessageDialog(null, message, alert.toString(),
                alert.getType(alert));
	}

	@Override
	public void run() {
		createAlert();
	}
}
