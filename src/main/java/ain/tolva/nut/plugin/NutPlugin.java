package ain.tolva.nut.plugin;

import java.awt.MenuItem;

public interface NutPlugin {

	/**
	 * Defines generation of this interface so that if new Methods are added they can be
	 * accounted for. Future-proofs Plugins.
	 */
	public final float version = 1;

	/**
	 * Build out a pop up menu item.
	 *
	 * Can build a single MenuItem  with action listeners defined
	 * Can be a Menu with MenuItems added to it, with action listeners definedt
	 *
	 * @return Implemented Menu or MenuItem
	 */
	public abstract MenuItem getMenuItem ();

	/**
	 * Retrieves the plugins name for logging.
	 *
	 * @return plugin name
	 */
	public abstract String getName();

	/**
	 * Plugin version allows the plugin app to identify if a plugin is up to date.
	 *
	 * @return a double representing the version of this plugin
	 */
	public abstract float getVersion();


}
