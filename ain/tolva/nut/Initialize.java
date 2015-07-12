package ain.tolva.nut;

/**
 * This class builds and launches the App.
 * 
 * @author river226
 */

import ain.tolva.alerter.backend.ErrorLog;
import ain.tolva.alerter.exceptions.*;

public class Initialize {

	/**
	 * Main function, uses methods to build, catches exceptions to 
	 * close program gracefully
	 */
	
	private static Nut prog;
	
	public static void main(String[] args) {
		try {
			buildNotificationTray();
			prog.run();
		} catch (NoTrayAccessException e) {
			err.log(e.toString());
			// Let use know that there is no Tray Access. 
			System.exit(0);
		} catch (Exception e) {
			err.log(e.toString());
			System.exit(0);
		} 
	}

	/**
	 * This function handles building the notification tray
	 */

	private static void buildNotificationTray() throws NoTrayAccessException {
		private static ErrorLog err;
		prog = new Nut(err);
	}

}
