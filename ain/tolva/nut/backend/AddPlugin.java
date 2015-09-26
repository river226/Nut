package ain.tolva.nut.backend;

import java.awt.MenuItem;
import java.util.LinkedList;
import java.util.Stack;

/**
 * @author river
 * This class handles writing and importing plugins. 
 * It uses a stack metaphor to describe the plugin storage.
 */
public class AddPlugin {

	//private static final String THIS_CLASS = "AddPlugin.java";
	//private ErrorLog erlog = ErrorLog.getInstance();
	
	private Stack<NeoPlugin> unimplementedPlugins = new Stack<NeoPlugin>();
	private LinkedList<NeoPlugin> plugins = new LinkedList<NeoPlugin>();
	private PluginFile io = new PluginFile();
	private boolean hasRun = false;

	private AddPlugin() {
		readInPlugins();
	}

	public MenuItem pop() {
		if(empty()) return null;
		plugins.add(unimplementedPlugins.peek());
		return unimplementedPlugins.pop().newClass().getMenuItem();
	}

	public boolean empty() {
		return unimplementedPlugins.empty();
	}

	public void readInPlugins() {
		if(!hasRun) {// if this has run once there is nothing to do
			unimplementedPlugins = io.readInData();
			hasRun = true;
		}
	}

	public void saveToXML(String xml) {
		
		Stack<NeoPlugin> np = new Stack<NeoPlugin>();
		for(NeoPlugin p : plugins) 
			np.push(p);
		
		io.writeDownData(np, xml);
	}

	public static AddPlugin getInstance() { return buildAddPlugin.INSTANCE; }

	private static class buildAddPlugin { // Thread safe way to create singleton
		private static final AddPlugin INSTANCE = new AddPlugin();	
	}

}