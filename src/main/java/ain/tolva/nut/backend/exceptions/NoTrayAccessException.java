package ain.tolva.nut.backend.exceptions;

/**
 * This exception will be thrown if the program is unable
 * to run from the notification tray
 */
public class NoTrayAccessException extends Exception {

	private static final long serialVersionUID = 1L;

	String error = "Program was unable to access System Tray";

	public NoTrayAccessException(){
		// No input, do nothing
	}

	/**
	 * @param input additional error information
	 */
	public NoTrayAccessException(String input){
		error = error + "\n" + input;
	}

	public String getMessage() {
		return error;
	}
}
