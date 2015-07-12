package ain.tolva.alerter;

/**
 * This is the main class, builds out the app, and launces the GUI as well
 * as all other components and classes that are necessary.
 * 
 * @author river226
 */

// Java Imports
import java.awt.AWTException;
import java.awt.CheckboxMenuItem;
import java.awt.Image;
import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.util.ArrayList;
import javax.swing.JFrame;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import javax.print.DocFlavor.URL;
import javax.swing.ImageIcon;

import ain.tolva.alerter.backend.*;
import ain.tolva.alerter.exceptions.*;

@SuppressWarnings("unused")
public class Alerter extends Thread {
	
	// TODO add Tray icon

	// Mutable Variables
	private Menu displayMenu = new Menu("Display");
	private ArrayList<Alerts> alerts = new ArrayList<Alerts>();
	private ErrorLog err;

	// Final Global Variables
	private final PopupMenu popup = new PopupMenu();
	private final TrayIcon trayIcon = new TrayIcon(createImage("media/alert.gif", "tray icon"));
	private final SystemTray tray = SystemTray.getSystemTray();

	/**
	 * This Constructor manages exceptions for the program, and launches the program
	 * @throws NoTrayAccessException
	 */
	public Alerter (ErrorLog l) throws NoTrayAccessException {
		err = l;
		
		if(!test()) { // The System Tray is not supported
			throw new NoTrayAccessException("No System Support");
		}
	}

	/**
	 * Test if the system allows you to access to the System Tray
	 * @return boolean telling you if system allows access to System Tray
	 */
	private boolean test() { // Tests if System allows access to the System Tray
		return SystemTray.isSupported();
	}

	/**
	 * This launches and builds the Application
	 * @throws AWTException
	 * @throws InterruptedException 
	 */
	public void launch() throws AWTException, InterruptedException {
		System.out.println("launch");

		// Create a popup menu components
		// Pull Menu items from plugins
		MenuItem addPlugin = new MenuItem("Add Plugin");
		MenuItem exitItem = new MenuItem("Exit");
		
		// Define behavior
		
		// Add Popup window to add plugins control settings. 
		addPlugin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO
			}
		});

		// Exit
		exitItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tray.remove(trayIcon);
				System.exit(0);
			}
		});

		//Add components to popup menu
		// TODO

		popup.add(addPlugin);
		popup.add(exitItem);
		trayIcon.setPopupMenu(popup);
		tray.add(trayIcon);
	}

	/**
	 * Manages creating the Tray Icon
	 * @param path - File path for the Tray Icon
	 * @param description - Describes the icon being built
	 * @return Image file of the Tray Icon
	 */
	private Image createImage(String path, String description) {
		java.net.URL imageURL = Alerter.class.getResource(path);

		if (imageURL == null) {
			System.err.println("Resource not found: " + path);
			return null;
		} else {
			return (new ImageIcon(imageURL, description)).getImage();
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			launch();
		} catch (AWTException | InterruptedException e) {
			err.log(e);
		}
	}
}
