package ain.tolva.nut.backend;

import java.awt.MenuItem;
import java.util.LinkedList;
import java.util.Stack;

import ain.tolva.nut.plugin.NutPlugin;

/**
 * @author river
 * This class handles writing and importing plugins. 
 */
public class AddPlugin{
	 
	private Stack<NutPlugin> newPlugins;
	private Stack<NutPlugin> tooAddPlugins;
	private LinkedList<NutPlugin> plugins;
	
	private AddPlugin() {
		newPlugins = new Stack<NutPlugin>();
		tooAddPlugins = new Stack<NutPlugin>();
		plugins = new LinkedList<NutPlugin>();
	}

	public static AddPlugin newAP() {
		return buildAddPlugin.INSTANCE;
	}
	
	public void push(NutPlugin n) {
		newPlugins.push(n);
	}
	
	public MenuItem pop() {
		NutPlugin n = tooAddPlugins.pop();
		plugins.add(n);
		return n.getMenuItem();
	}
	
	public boolean peek() {
		return tooAddPlugins.peek() != null;
	}

	// TODO implement reading and writing plugin data

	private static class buildAddPlugin { // Thread safe way to create singleton
		private static final AddPlugin INSTANCE = new AddPlugin();	
	}

}
