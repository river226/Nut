package ain.tolva.nut.backend;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
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

	public void log(String c, String e) {
		add(c + ": " + e);
	}

	public void log(String c, Exception e) {
		add(c + ": " + e.toString());
	}
	
	private void add(String er) {
		int k = errors.size();
		
		if(k < 1) 
			errors.put(0, er);
		
		errors.put(k, er);
	}
	
	public void produceLog() {
		PrintWriter writer = null;
		try {
			writer = new PrintWriter("error.log", "UTF-8");
			for(int i = 0; i < errors.size(); i++) {
				writer.println(i + " --- /n" + errors.get(i));
			}
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
		} finally {
			if(writer != null)
				writer.close();
		}
		
	}
	
	private static class ErrorLogHolder {
		private static final ErrorLog el = new ErrorLog();
	}

}
