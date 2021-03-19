package de.bloodeko.towns.town.settings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.bloodeko.towns.town.Town;

public class SettingsRegistry {
    private Map<String, TownSetting> byInternal;
    private Map<String, TownSetting> byDisplay;
    
    public SettingsRegistry() {
        this.byDisplay = new HashMap<>();
        this.byInternal = new HashMap<>();
    }

    public void register(TownSetting setting) {
        byInternal.put(setting.getId(), setting);
        byDisplay.put(setting.getDisplay(), setting);
    }

    public TownSetting fromDisplay(String key) {
        return byDisplay.get(key);
    }
    
    public TownSetting fromId(String key) {
        return byInternal.get(key);
    }
    
    /**
     * Returns values which can be bought for this town.
     */
    public List<TownSetting> getPossibleSettings(Town town) {
        List<TownSetting> list = new ArrayList<>();
        for (TownSetting setting : byDisplay.values()) {
            if (setting.accepts(town) && !town.getSettings().hasSetting(setting)) {
                list.add(setting);
            }
        }
        return list;
    }
    
    /**
     * Returns the names of values which can be bought for 
     * this town.
     */
    public List<String> getPossibleNames(Town town) {
        List<String> list = new ArrayList<>();
        for (TownSetting setting : getPossibleSettings(town)) {
            list.add(setting.getDisplay());
        }
        return list;
    }
}
