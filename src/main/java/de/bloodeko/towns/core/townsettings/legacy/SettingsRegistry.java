package de.bloodeko.towns.core.townsettings.legacy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.bloodeko.towns.core.towns.legacy.Town;

public class SettingsRegistry {
    private Map<String, AdvancedSetting> byInternal;
    private Map<String, AdvancedSetting> byDisplay;
    
    public SettingsRegistry() {
        byDisplay = new HashMap<>();
        byInternal = new HashMap<>();
    }

    public void register(AdvancedSetting setting) {
        byInternal.put(setting.settingKey.getId(), setting);
        byDisplay.put(setting.names.getName(), setting);
    }

    public AdvancedSetting fromDisplay(String key) {
        return byDisplay.get(key);
    }
    
    public AdvancedSetting fromId(String key) {
        return byInternal.get(key);
    }
    
    /**
     * Returns values which can be bought for this town.
     */
    public List<AdvancedSetting> getMatches(Town town) {
        List<AdvancedSetting> list = new ArrayList<>();
        for (AdvancedSetting setting : byDisplay.values()) {
            if (setting.matches(town) && !town.getSettings().has(setting.settingKey)) {
                list.add(setting);
            }
        }
        return list;
    }
    
    /**
     * Returns the names of values which can be bought for 
     * this town.
     */
    public List<String> getMachesNames(Town town) {
        List<String> list = new ArrayList<>();
        for (AdvancedSetting setting : getMatches(town)) {
            list.add(setting.names.getName());
        }
        return list;
    }
}
