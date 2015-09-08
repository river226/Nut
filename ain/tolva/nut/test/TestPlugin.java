package ain.tolva.nut.test;

import java.awt.MenuItem;

import ain.tolva.nut.plugin.NutPlugin;

public class TestPlugin implements NutPlugin {

	@Override
	public MenuItem getMenuItem() {
		return new MenuItem("Test");
	}

	@Override
	public String getName() {
		return "Test";
	}

	@Override
	public double getVersion() {
		return version;
	}
	
}
