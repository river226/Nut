package ain.tolva.nut;

/**
 * This class builds and launches the App.
 *
 * @author river226
 */

import ain.tolva.nut.backend.ErrorLog;
import ain.tolva.nut.exceptions.*;

public class Initialize {

	/**
	 * Main function, uses methods to build, catches exceptions to
	 * close program gracefully
	 */
	private static ErrorLog err;
	private static Nut prog;

	public static void main(String[] args) {
		Thread in = null;
		
		try {
			buildNotificationTray();
			in = new Thread(prog);
			in.run();
		} catch (NoTrayAccessException e) {
			err.log(e.toString());
			// Let use know that there is no Tray Access.
			System.exit(0);
		} catch (Exception e) {
			err.log(e.toString());
			if (in != null) in.interrupt();
			System.exit(0);
		}
	}

	/**
	 * This function handles building the notification tray
	 */

	private static void buildNotificationTray() throws NoTrayAccessException {
		err = new ErrorLog();
		prog = new Nut(err);
	}

}
