package ain.tolva.nut.backend;

import java.awt.MenuItem;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
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
		if(!hasRun) readInPlugins();
		return tooAddPlugins.empty();
	}

	// TODO implement reading and writing plugin data
	// http://stackoverflow.com/questions/7373567/java-how-to-read-and-write-xml-files/7373596#7373596
	public void readInPlugins() {
		// Throws added plugins on the TooAddPlugins Stack
		if(!hasRun) {// if this has run once there is nothing to do

			try {
				Document dom;
				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				DocumentBuilder db = dbf.newDocumentBuilder();
				dom = db.parse(new File("plist.xml"));

				if (dom != null) {
					Element ele = dom.getDocumentElement();
					if (ele != null) {
						NodeList nl = ele.getElementsByTagName("location");
						
						for(int i = 0; i < nl.getLength(); i++) {
							String loc = nl.item(i).getFirstChild().getNodeValue();
							tooAddPlugins.push(addJar(loc));
						}
					}
				}

			} catch (SAXException 
					| IOException 
					| ParserConfigurationException e) {
				// Log
			} finally {
				hasRun = true;	
			}
		}
	}
	
	private NutPlugin addJar(String loc) {
		try {
			File f = new File(loc);
			ClassLoader cl = URLClassLoader.newInstance(new URL[] { f.toURL() });
			NutPlugin plugin = (NutPlugin) cl.loadClass("plugins.authorized.Authorized").newInstance();
			return plugin;
		} catch (Exception e){
			// log
		}
		
		return null;
	}

	public void saveToXML(String xml) {
	    Document dom;
	    Element e = null;

	    // instance of a DocumentBuilderFactory
	    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	    try {
	        // use factory to get an instance of document builder
	        DocumentBuilder db = dbf.newDocumentBuilder();
	        // create instance of DOM
	        dom = db.newDocument();

	        // create the root element
	        Element rootEle = dom.createElement("roles");

	        // create data elements and place them under root
	        //e = dom.createElement("role1");
	        //e.appendChild(dom.createTextNode(role1));
	        //rootEle.appendChild(e);

	        dom.appendChild(rootEle);

	        try {
	            Transformer tr = TransformerFactory.newInstance().newTransformer();
	            tr.setOutputProperty(OutputKeys.INDENT, "yes");
	            tr.setOutputProperty(OutputKeys.METHOD, "xml");
	            tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
	            tr.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "roles.dtd");
	            tr.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

	            // send DOM to file
	            tr.transform(new DOMSource(dom), 
	                                 new StreamResult(new FileOutputStream(xml)));

	        } catch (TransformerException te) {
	            System.out.println(te.getMessage());
	        } catch (IOException ioe) {
	            System.out.println(ioe.getMessage());
	        }
	    } catch (ParserConfigurationException pce) {
	        System.out.println("UsersXML: Error trying to instantiate DocumentBuilder " + pce);
	    }
	}

	private static class buildAddPlugin { // Thread safe way to create singleton
		private static final AddPlugin INSTANCE = new AddPlugin();	
	}
}