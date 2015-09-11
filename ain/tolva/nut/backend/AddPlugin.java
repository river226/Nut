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
public class AddPlugin {

	private static final String THIS_CLASS = "AddPlugin.java";
	
	private Stack<NutPlugin> tooAddPlugins = new Stack<NutPlugin>();
	private Stack<String> addedLocations = new Stack<String>();
	private LinkedList<NutPlugin> plugins = new LinkedList<NutPlugin>();
	private ErrorLog erlog = ErrorLog.getInstance();
	
	private boolean hasRun = false;
	private Document dom;

	private AddPlugin() {
		readInPlugins();
	}

	public MenuItem pop() {
		if(empty()) return null;
		plugins.add(tooAddPlugins.peek());
		return tooAddPlugins.pop().getMenuItem();
	}

	public boolean empty() {
		return tooAddPlugins.empty();
	}

	public void readInPlugins() {
		// Throws added plugins on the TooAddPlugins Stack
		if(!hasRun) {// if this has run once there is nothing to do

			try {
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
							addedLocations.push(loc);
						}
					}
				}

			} catch (SAXException 
					| IOException 
					| ParserConfigurationException e) {
				erlog.log(THIS_CLASS, e);
			} finally {
				hasRun = true;
			}
		}
	}
	
	private NutPlugin addJar(String loc) {
		try {
			File f = new File(loc);
			ClassLoader cl = URLClassLoader.newInstance(new URL[] { f.toURI().toURL() }); 
			NutPlugin plugin = (NutPlugin) cl.loadClass("plugins.authorized.Authorized").newInstance();
			return plugin;
		} catch (Exception e){
			erlog.log(THIS_CLASS, e);
		}
		
		return null;
	}

	public void saveToXML(String xml) {
	    Element e = null;

	    // instance of a DocumentBuilderFactory
	    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	    try {
	        // use factory to get an instance of document builder
	        DocumentBuilder db = dbf.newDocumentBuilder();
	        // create instance of DOM
	        dom = db.newDocument();

	        // create the root element
	        Element rootEle = dom.createElement("plugin");

	        //create data elements and place them under root
	        e = dom.createElement("location");
	        e.appendChild(dom.createTextNode(addedLocations.pop()));
	        rootEle.appendChild(e);

	        dom.appendChild(rootEle);

	        try {
	            Transformer tr = TransformerFactory.newInstance().newTransformer();
	            tr.setOutputProperty(OutputKeys.INDENT, "yes");
	            tr.setOutputProperty(OutputKeys.METHOD, "xml");
	            tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
	            tr.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "plug.dtd");
	            tr.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

	            // send DOM to file
	            tr.transform(new DOMSource(dom), 
	                                 new StreamResult(new FileOutputStream(xml)));

	        } catch (IOException | TransformerException ex) {
	            erlog.log(THIS_CLASS, ex);
	        }
	    } catch (ParserConfigurationException pce) {
	        erlog.log(THIS_CLASS, pce);
	    }
	}
	
	public static AddPlugin getInstance() { return buildAddPlugin.INSTANCE; }

	private static class buildAddPlugin { // Thread safe way to create singleton
		private static final AddPlugin INSTANCE = new AddPlugin();	
	}
}