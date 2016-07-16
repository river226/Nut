package ain.tolva.nut.plugin;

import java.awt.*;

/**
 * Created by river
 */
public abstract class NutPluginAbstract  implements NutPlugin{

    @Override
    public abstract MenuItem getMenuItem();

    @Override
    public String getName() {
        return "NutPlugin";
    }

    @Override
    public float getVersion() {
        return version;
    }
}
