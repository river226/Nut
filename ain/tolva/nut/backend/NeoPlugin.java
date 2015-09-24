package ain.tolva.nut.backend;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

import ain.tolva.nut.plugin.NutPlugin;

public class NeoPlugin {
	
	private static final String THIS_CLASS = "NeoPlugin.java";
	private final String location, class_name;
	private ErrorLog erlog;

	public NeoPlugin(String cl, String loc) {
		class_name = cl;
		location = loc;
		erlog = ErrorLog.getInstance();
	}	
	
	public NutPlugin newClass() {
		NutPlugin n = null;
		
		try {
			File f = new File(location);
			URL[] urls = new URL[] { f.toURI().toURL() };
			ClassLoader cl = URLClassLoader.newInstance(urls); 
			n = ((NutPlugin) cl.loadClass(class_name).newInstance());
		} catch (NoClassDefFoundError | Exception e){
			erlog.log(THIS_CLASS, e);
		}
		
		return n;
	}
	
	public String getLocation() {
		return location;
	}
	
	public String getClassName() {
		return class_name;
	}
}
