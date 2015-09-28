package ain.tolva.nut.backend.exceptions;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Hashtable;
import java.util.Map;

public class ErrorLog {
	
	private Map<Integer, String> errors  = new Hashtable<Integer, String>();;
	private final String ERROR_LOG_FILE = "error.log";
	private final String FILE_FORMAT = "UTF-8";

	private ErrorLog() {}

	public void log(String c, String e) {
		add(c + ": " + e);
	}

	public void log(String c, Exception e) {
		add(c + ": " + e.toString());
	}
	
	public void log(String c, Throwable e) {
		add(c + ": " + e.toString());
		
	}
	
	private void add(String er) {
		int k = errors.size();
		
		if(k < 1) 
			errors.put(0, er);
		
		errors.put(k, er);
		
		// For Testing
		System.out.println(er);
	}
	
	public void produceLog() {
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(ERROR_LOG_FILE, FILE_FORMAT);
			for(int i = 0; i < errors.size(); i++) {
				writer.println(i + " --- /n" + errors.get(i));
			}
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			// TODO: Handle Exception without bothering user
		} finally {
			if(writer != null)
				writer.close();
		}
		
	}
	
	public static ErrorLog getInstance() {
		return ErrorLogHolder.el;
	}
	
	private static class ErrorLogHolder { // Singleton Class
		private static final ErrorLog el = new ErrorLog();
	}

}