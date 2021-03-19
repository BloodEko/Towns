package de.bloodeko.towns.town.settings.plots;

import java.util.HashMap;
import java.util.Map;

import de.bloodeko.towns.town.Town;
import de.bloodeko.towns.town.settings.NameProvider;
import de.bloodeko.towns.town.settings.Setting;
import de.bloodeko.towns.town.settings.Settings;
import de.bloodeko.towns.town.settings.plots.cmds.PlotCreateCmd;
import de.bloodeko.towns.util.Messages;
import de.bloodeko.towns.util.Node;

public class PlotSetting extends Setting {

    @Override
    public String getId() {
        return "plots";
    }

    @Override
    public Object read(Map<Object, Object> map) {
        return map.get(Settings.PLOTS);
    }

    @Override
    public void set(Map<Object, Object> map, Object obj) {
        map.put(Settings.PLOTS, obj);
    }

    @Override
    public void init(Map<Object, Object> map) {
        map.put(Settings.PLOTS, new PlotHandler(new HashMap<>(), 0));
    }

    @Override
    public Object serialize(Map<Object, Object> map) {
        return ((PlotHandler) read(map)).serialize();
    }

    @Override
    public void deserialize(Map<Object, Object> map, Object obj) {
        set(map, PlotHandler.deserialize((Node) obj));
    }
    
    
    public static class PlotDisplay implements NameProvider {
        
        public String display(Town town) {
            PlotHandler handler = (PlotHandler) Settings.PLOTS.read(town.getSettings().getFlags());
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
