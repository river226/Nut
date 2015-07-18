package ain.tolva.nut.backend;

/**
 * @author river226
 */

public class Alerts implements Runnable {

	public final int ERROR = 0;
	//public final int ERROR = 1;
	//public final int ERROR = 2;
	//public final int ERROR = 3;
	//public final int ERROR = 4;
	//public final int ERROR = 5;
	//public final int ERROR = 6;
	//public final int ERROR = 7;
	//public final int ERROR = 8;

	private int alert;

	public Alerts () {
		// TODO
	}

	public void sendAlert(int a) {
		alert = a;
		run();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		switch(alert){
			// TODO
			default:
				break;
		}
	}

	public void closePane() {
		System.exit(0);
	}

}
