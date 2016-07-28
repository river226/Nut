package ain.tolva.nut;

/**
 * @author river226
 */

// Java Imports
import java.awt.AWTException;
import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Stack;

import ain.tolva.nut.backend.alerts.*;
import ain.tolva.nut.backend.exceptions.*;
import ain.tolva.nut.backend.media.TrayIconLocal;
import ain.tolva.nut.backend.plugins.*;
import ain.tolva.nut.plugin.*;


@SuppressWarnings("unused")
public class Nut implements Runnable {

	private static final String THIS_CLASS = "Nut.java";

	private PopupMenu popup = new PopupMenu();
	private SystemTray tray = SystemTray.getSystemTray();
	private Menu displayMenu = new Menu("Display");
	private ErrorLog erlog;
	private ArrayList<NutPlugin> plug;
	private TrayIcon trayIcon;


	/**
	 * @throws NoTrayAccessException, FileNotFoundException
	 */
	public Nut()
			throws NoTrayAccessException, FileNotFoundException {
		erlog = ErrorLog.getInstance();

		if(!SystemTray.isSupported()) { // The System Tray is not supported
			throw new NoTrayAccessException("No System Support");
		}

		trayIcon = new TrayIcon(new TrayIconLocal("media/nut_tmp_logo.png", "Nut").getImage());
	}

	/**
	 * Launches a new Thread to run the program.
	 */
	@Override
	public void run() {
		try {
			launch();
		} catch (AWTException | InterruptedException e) {
			erlog.log(THIS_CLASS, e);
		}
	}

	/**
	 * @throws AWTException
	 * @throws InterruptedException
	 */
	public void launch() throws AWTException, InterruptedException {
		addPlugins(getPlugins());
        popup.addSeparator();
		popup.add(buildAddPluginItem());
		popup.add(buildExitItem());
		trayIcon.setPopupMenu(popup);
		tray.add(trayIcon);
	}

	private MenuItem buildAddPluginItem() {
		MenuItem ap = new MenuItem("Add Plugin");

		try {
			ap.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Alerter alert = new Alerter(AlertType.ATTENTION, "This is a test");
					alert.run();
				}
			});
		} catch (Exception e) {
			erlog.log(THIS_CLASS, e);
		}

		return ap;
	}

	private MenuItem buildExitItem() {
		MenuItem e = new MenuItem("Exit");

		e.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tray.remove(trayIcon);
				System.exit(0);
			}
		});

		return e;
	}

	private Stack<MenuItem> getPlugins() {
		AddPlugin p = AddPlugin.getInstance();
		Stack<MenuItem> plug = new Stack<>();
		while(!p.empty()) {
			MenuItem i = p.pop();
			if(i != null)
				plug.push(i);
		}
		return plug;
	}

	private void addPlugins(Stack<MenuItem> plug)  {
		while(!plug.empty()) {
			popup.add(plug.pop());
		}
	}
}
