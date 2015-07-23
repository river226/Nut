package ain.tolva.nut.backend;

import java.awt.MenuItem;
import java.util.LinkedList;
import java.util.Stack;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import org.xml.sax.*;
import org.w3c.dom.*;

import ain.tolva.nut.plugin.NutPlugin;

/**
 * @author river
 * This class handles writing and importing plugins. 
 * It uses a stack metaphor to describe the plugin storage.
 */
public class AddPlugin{

	private Stack<NutPlugin> tooAddPlugins;
	private LinkedList<NutPlugin> plugins;
	private boolean hasRun;

	private AddPlugin() {
		tooAddPlugins = new Stack<NutPlugin>();
		plugins = new LinkedList<NutPlugin>();
		hasRun = false;
	}

	public static AddPlugin newAP() {
		return buildAddPlugin.INSTANCE;
	}

	public MenuItem pop() {
		if(!hasRun) readInPlugins();
		if(this.empty()) return null;

		plugins.add(tooAddPlugins.peek());
		return tooAddPlugins.pop().getMenuItem();
	}

	public boolean empty() {
		return tooAddPlugins.empty();
	}

	// TODO implement reading and writing plugin data

	public boolean readInPlugins() {
		// Throws added plugins on the TooAddPlugins Stack
		if(!hasRun) {// if this has run once there is nothing to do
			Document dom;
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			try {
				DocumentBuilder db = dbf.newDocumentBuilder();
				dom = db.parse("TODO");

				NodeList doc = dom.getElementsByTagName("plugin");

				for(int i = 0; i < doc.getLength(); i++) {
					// TODO: process plugin
				}
			} catch (Exception e) {
				// Log
			}
				
			if(!this.empty()) return true;
		}
		return false;
	}
	
	private static class buildAddPlugin { // Thread safe way to create singleton
		private static final AddPlugin INSTANCE = new AddPlugin();	
	}
}