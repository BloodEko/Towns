package de.bloodeko.towns.core.townplots;

import java.util.Map;

import de.bloodeko.towns.Services;
import de.bloodeko.towns.core.townplots.ui.PlotCreateCmd;
import de.bloodeko.towns.core.towns.Town;
import de.bloodeko.towns.core.townsettings.legacy.NameProvider;
import de.bloodeko.towns.core.townsettings.legacy.Setting;
import de.bloodeko.towns.core.townsettings.legacy.Settings;
import de.bloodeko.towns.util.Messages;

//can the working behavior of the dummies
//be fully proved in contrast to the old values?
public class PlotSetting extends Setting {

    @Override
    public String getId() {
        return "plots";
    }

    /**
     * Would return the PlotHandler ID.
     */
    @Override
    public Object read(Map<Object, Object> map) {
        throw new IllegalStateException();
    }

    /**
     * Would sets the PlotHandler ID.
     */
    @Override
    public void set(Map<Object, Object> map, Object obj) {
        throw new IllegalStateException();
    }

    /**
     * Initializes a new PlotHandler object.
     */
    @Override
    public void init(Map<Object, Object> map, Integer id) {
        Services.plotservice().setHandler(id, new PlotHandler());
        map.put(Settings.PLOTS, "x");
    }

    /**
     * Would serialize the PlotHandler ID from the map.
     */
    @Override
    public String serialize(Map<Object, Object> map) {
        return "x";
    }

    /**
     * Would deserialize the PlotHandler ID to the map.
     */
    @Override
    public void deserialize(Map<Object, Object> map, Object obj) {
        map.put(Settings.PLOTS, "x");
    }
    
    
    public static class PlotDisplay implements NameProvider {
        
        public String display(Town town) {
            PlotHandler handler = town.getPlots();
            return handler.plots.size() + "/" + PlotCreateCmd.getMaxPlots(town);
        }

        @Override
        public String getName() {
            return Messages.get("settings.plot");
        }
        
        @Override
        public int getPriority() {
            return 2;
        }

        @Override
        public boolean isHidden() {
            return false;
        }
    }
}
