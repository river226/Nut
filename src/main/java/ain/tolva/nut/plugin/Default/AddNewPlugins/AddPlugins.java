package ain.tolva.nut.plugin.Default.AddNewPlugins;

import ain.tolva.nut.backend.alerts.AlertType;
import ain.tolva.nut.backend.alerts.Alerter;
import ain.tolva.nut.plugin.NutPluginAbstract;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by river
 */
public class AddPlugins extends NutPluginAbstract {

    private final String THIS_CLASS = "Add Plugins";

    @Override
    public MenuItem getMenuItem() {
        MenuItem ap = new MenuItem("Add Plugin");

        ap.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Alerter a = new Alerter(AlertType.ATTENTION, "This is a test");
                a.run();
            }
        });

        return ap;
    }

    @Override
    public String getName() {
        return THIS_CLASS;
    }
}
