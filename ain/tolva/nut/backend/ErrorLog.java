package ain.tolva.nut.backend;

import java.util.Hashtable;
import java.util.Map;

public class ErrorLog {
	
	Map<Integer, String> errors;

	public ErrorLog(){
		errors = new Hashtable<Integer, String>();
	}
	
	public static ErrorLog getInstance() {
		return ErrorLogHolder.el;
	}

	public void log(String e) {
		add(e);
	}

	public void log(Exception e) {
		add(e.toString());
	}
	
	private void add(String er) {
		int k = errors.size();
		
		if(k < 1)
			errors.put(1, er);
		
		errors.put(k+1, er);
	}
	
	private static class ErrorLogHolder {
		private static final ErrorLog el = new ErrorLog();
	}

	private class ErrorReport {
		 // TODO
	}

}
