package ain.tolva.nut.backend.media;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import ain.tolva.nut.*;

/**
 * @author river
 */

/** // TODO restructure note
 * Manages creating the Tray Icon
 * @param path - File path for the Tray Icon
 * @param description - Describes the icon being built
 * @return Image file of the Tray Icon
 */
public class TrayIconLocal{

    String path, description;
    Image trayIcon;

    public TrayIconLocal(String p, String d) {
        path = p;
        description = d;
        try {
            createImage();
        } catch (FileNotFoundException e) {
            // TODO log exception
            System.out.println("Caught");
        }

    }

    private void createImage() throws FileNotFoundException {
        java.net.URL imageURL = Nut.class.getResource(path);

        if (imageURL == null)
            throw new FileNotFoundException();

        this.trayIcon = (new ImageIcon(imageURL, description)).getImage().getScaledInstance(20,20,Image.SCALE_SMOOTH);
    } // TODO add auto scaling, make system independent

    public Image getImage() {
        return this.trayIcon;
    }
}
