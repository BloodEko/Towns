package de.bloodeko.towns.town.settings;

import de.bloodeko.towns.town.settings.SettingsRegistry.RegisteredSetting;
import de.bloodeko.towns.util.Messages;

public class SettingsFactory {
    
    public static SettingsRegistry newSettingsRegistry() {
        SettingsRegistry registry = new SettingsRegistry();
        registry.register(new RegisteredSetting(Settings.DAMAGE_ANIMALS, 1, 3000, Messages.get("settings.damageAnimals"), false));
        registry.register(new RegisteredSetting(Settings.PVP, 1, 1000, Messages.get("settings.pvp"), false));
        registry.register(new RegisteredSetting(Settings.WARP, 2, 4000, Messages.get("settings.warp"), false));
        registry.register(new RegisteredSetting(Settings.PLOTS, 2, 5000, Messages.get("settings.plot"), false));
        return registry;
    }
}
