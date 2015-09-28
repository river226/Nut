package ain.tolva.nut.backend.plugins;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Stack;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import ain.tolva.nut.backend.exceptions.ErrorLog;

public class PluginFile {
	private static final String DEFAULT_FILE = "plist.xml";
	private static final String THIS_CLASS = "PluginFile.java";
	
	public Document dom;
	private ErrorLog erlog;

	public PluginFile() {
		erlog = ErrorLog.getInstance();
	}
	
	public Stack<NeoPlugin> readInData() {
		return readInData(DEFAULT_FILE);
	}
	
	public Stack<NeoPlugin> readInData(String file) {
		Stack<NeoPlugin> n = null;
		
		try {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		dom = db.parse(new File(file));

		if (dom != null) {
			Element ele = dom.getDocumentElement();
			if (ele != null) {
				NodeList nlCLA = ele.getElementsByTagName("pluginclass");
				NodeList nlLOC = ele.getElementsByTagName("location");
				n = new Stack<NeoPlugin>();
				
				for(int i = 0; i < nlLOC.getLength() && i < nlCLA.getLength(); i++) {
					String cl = nlCLA.item(i).getFirstChild().getNodeValue().trim(); // class
					String loc = nlLOC.item(i).getFirstChild().getNodeValue().trim(); // location
					if(cl != null && loc != null)
						n.push(new NeoPlugin(cl, loc));
				}
			}
		}
		} catch (SAXException 
				| IOException 
				| ParserConfigurationException e) {
			erlog.log(THIS_CLASS, e);
			
		}
		
		return n;
	}
	
	public boolean writeDownData(Stack<NeoPlugin> n) {
		return writeDownData(n, DEFAULT_FILE);
	}
	
	public boolean writeDownData(Stack<NeoPlugin> n, String file) {
		Element e = null;
		boolean written = false;

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
	        //e.appendChild(dom.createTextNode(n.pop()));
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
	                                 new StreamResult(new FileOutputStream(file)));
	            written = true;

	        } catch (IOException | TransformerException ex) {
	            erlog.log(THIS_CLASS, ex);
	        }
	    } catch (ParserConfigurationException pce) {
	        erlog.log(THIS_CLASS, pce);
	    }
	    
	    return written;
	}
}