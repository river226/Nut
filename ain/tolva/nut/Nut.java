package ain.tolva.nut;

/**
 * @author river226
 */

// Java Imports
import java.awt.AWTException;
import java.awt.Image;
import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import ain.tolva.nut.backend.*;
import ain.tolva.nut.exceptions.*;
import ain.tolva.nut.plugin.*;


@SuppressWarnings("unused")
public class Nut implements Runnable {

	private static final String This_Class = "Nut.java";
	// Mutable Variables
	private Menu displayMenu = new Menu("Display");
	private ErrorLog erlog;
	private ArrayList<NutPlugin> plug;
	private TrayIcon trayIcon;
	// Final Global Variables
	private final PopupMenu popup = new PopupMenu();
	private final SystemTray tray = SystemTray.getSystemTray();


	/**
	 * This Constructor manages exceptions for the program, and launches the program
	 * @throws NoTrayAccessException
	 */
	public Nut() throws NoTrayAccessException {
		erlog = ErrorLog.getInstance();
		
		if(!SystemTray.isSupported()) { // The System Tray is not supported
			throw new NoTrayAccessException("No System Support");
		}
		
		try {
				trayIcon = new TrayIcon(createImage("media/alert.gif", "tray icon"));
		} catch (FileNotFoundException e) {
			erlog.log(This_Class, e);
		}
	}

	/**
	 * This launches and builds the Application
	 * @throws AWTException
	 * @throws InterruptedException
	 */
	public void launch() throws AWTException, InterruptedException {

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
		
		addPlugins();
		
		popup.add(exitItem);
		trayIcon.setPopupMenu(popup);
		tray.add(trayIcon);
	}

	private void addPlugins() {
		AddPlugin p = AddPlugin.newAP();
		
		while(!p.empty()) {
			popup.add(p.pop());
		}
	}

	/**
	 * Manages creating the Tray Icon
	 * @param path - File path for the Tray Icon
	 * @param description - Describes the icon being built
	 * @return Image file of the Tray Icon
	 */
	private Image createImage(String path, String description) throws FileNotFoundException {
		java.net.URL imageURL = Nut.class.getResource(path);

		if (imageURL == null)
			throw new FileNotFoundException();

		return (new ImageIcon(imageURL, description)).getImage();
	}

	/**
	 * Launches a new Thread to run the program.
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			launch();
		} catch (AWTException | InterruptedException e) {
			erlog.log(This_Class, e);
		}
	}
}
