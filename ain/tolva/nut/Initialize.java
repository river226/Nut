package ain.tolva.nut;

/**
 * This class builds and launches the App.
 *
 * @author river226
 */

import java.io.FileNotFoundException;

import ain.tolva.nut.backend.ErrorLog;
import ain.tolva.nut.exceptions.*;

public class Initialize {

	/**
	 * Main function, uses methods to build, catches exceptions to
	 * close program gracefully
	 */
	private static ErrorLog erlog;
	private static Nut prog;
	private static final String THIS_CLASS = "Initialize.java";

	public static void main(String[] args) {
		Thread in = null;
		
		try {
			erlog = ErrorLog.getInstance();
			prog = new Nut();
			in = new Thread(prog);
			in.run();
		} catch (NoTrayAccessException | FileNotFoundException e) {
			erlog.log(THIS_CLASS, e.toString());
			// Let use know that there is no Tray Access.
			System.exit(0);
		} catch (Exception e) {
			erlog.log(THIS_CLASS, e.toString());
			if (in != null) in.interrupt();
			System.exit(0);
		}
	}
}
