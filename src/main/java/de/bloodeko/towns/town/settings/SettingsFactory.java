package de.bloodeko.towns.town.settings;

import de.bloodeko.towns.town.Town;
import de.bloodeko.towns.town.settings.SettingsRegistry.RegisteredSetting;
import de.bloodeko.towns.town.settings.plots.PlotHandler;
import de.bloodeko.towns.town.settings.plots.cmds.PlotCreateCmd;
import de.bloodeko.towns.util.Messages;

public class SettingsFactory {
    
    public static SettingsRegistry newSettingsRegistry() {
        SettingsRegistry registry = new SettingsRegistry();
        
        RegisteredSetting dmgAnimals = new RegisteredSetting(Settings.DAMAGE_ANIMALS, 1, 3000, Messages.get("settings.damageAnimals"), false);
        RegisteredSetting pvp = new RegisteredSetting(Settings.PVP, 1, 1000, Messages.get("settings.pvp"), false);
        RegisteredSetting warp = new RegisteredSetting(Settings.WARP, 2, 4000, Messages.get("settings.warp"), false);
        
        RegisteredSetting plots = new RegisteredSetting(Settings.PLOTS, 2, 5000, Messages.get("settings.plot"), false);
        plots.addDisplayHandler(new PlotDisplayHandler());
        
        registry.register(dmgAnimals);
        registry.register(pvp);
        registry.register(warp);
        registry.register(plots);
        
        return registry;
    }
    
    public interface DisplayHandler {
        public String display(Town town, Object obj);
    }
    
    public static class PlotDisplayHandler implements DisplayHandler {
        
        public String display(Town town, Object plotHandler) {
            PlotHandler handler = (PlotHandler) plotHandler;
            return handler.plots.size() + "/" + PlotCreateCmd.getMaxPlots(town);
        }
    }
}
