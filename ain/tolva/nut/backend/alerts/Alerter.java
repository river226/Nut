package ain.tolva.nut.backend.alerts;

import javax.swing.JFrame;

public class Alerter extends JFrame implements Runnable {
	
	private static final long serialVersionUID = 1L;
	AlertType alert;
	
	public Alerter(AlertType a) {
		alert = a;
	}
	
	private void createAlert() {
		switch (alert) {
			case ERROR:
				createErrorAlert();
				break;
			case ISSUE:
				createIssueAlert();
				break;
			case ATTENTION:
				createAttentionAlert();
				break;
			default:
				break;
		}
	}

	private void createAttentionAlert() {
		// TODO Auto-generated method stub
		
	}

	private void createIssueAlert() {
		// TODO Auto-generated method stub
		
	}

	private void createErrorAlert() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void run() {
		createAlert();
	}
	
}