import ain.tolva.nut.plugin.NutPlugin;
import ain.tolva.nut.backend.alerts.*;
import ain.tolva.nut.backend.exceptions.*;

import java.awt.event.*;
import java.awt.MenuItem;
import java.awt.Menu;

public class AlertTest implements NutPlugin {

  private final String NAME = "Alert Test";
  private Alerter alert;

  public AlertTest() {}

  public MenuItem getMenuItem () {
        Menu alerttest = new Menu("Alert Test");
        MenuItem error = new MenuItem("Test Error");
        MenuItem issue = new MenuItem("Test Issue");
        MenuItem atten = new MenuItem("Test Attention");

        error.addActionListener(newActionListener(AlertType.ERROR));
        issue.addActionListener(newActionListener(AlertType.ISSUE));
        atten.addActionListener(newActionListener(AlertType.ATTENTION));

        alerttest.add(error);
        alerttest.add(issue);
        alerttest.add(atten);

        return alerttest;
  }

  private ActionListener newActionListener(final AlertType a) {
    return new ActionListener() {
  			public void actionPerformed(ActionEvent e) {
  				Alerter al = new Alerter(a, "test");
          al.run();
  			}
    };
  }

	public String getName() {
        return NAME;
  }

	public float getVersion() {
        return version;
  }
}
