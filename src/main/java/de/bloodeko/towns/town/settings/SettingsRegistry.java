package de.bloodeko.towns.town.settings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.bloodeko.towns.town.Town;
import de.bloodeko.towns.util.Messages;

public class SettingsRegistry {
    private Map<String, RegisteredSetting> byInternal;
    private Map<String, RegisteredSetting> byDisplay;
    
    public SettingsRegistry() {
        this.byDisplay = new HashMap<>();
        this.byInternal = new HashMap<>();
    }

    public void register(RegisteredSetting setting) {
        byInternal.put(setting.value.getId(), setting);
        byDisplay.put(setting.display, setting);
    }

    public RegisteredSetting fromDisplay(String key) {
        return byDisplay.get(key);
    }
    
    public RegisteredSetting fromId(String key) {
        return byInternal.get(key);
    }
    
    /**
     * Returns values which can be bought for this town.
     */
    public List<RegisteredSetting> getPossibleSettings(Town town) {
        List<RegisteredSetting> list = new ArrayList<>();
        for (RegisteredSetting setting : byDisplay.values()) {
            if (setting.accepts(town) && !town.getSettings().hasSetting(setting.value)) {
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
        for (RegisteredSetting setting : getPossibleSettings(town)) {
            list.add(setting.display);
        }
        return list;
    }
    
    public static class RegisteredSetting {
        public final Setting value;
        public final int minStage;
        public final int price;
        public final String display;
        public final boolean hidden;
        
        public RegisteredSetting(Setting setting, int minStage, int price, String display, boolean hidden) {
            this.value = setting;
            this.minStage = minStage;
            this.price = price;
            this.display = display;
            this.hidden = hidden;
        }
        
        /**
         * Returns true if the setting can be bought for this town.
         * Per default only checks on the stage.
         */
        public boolean accepts(Town town) {
            return matchesStage(town.getSettings().getStage());
        }
        
        /**
         * Returns true if the town has this stage at least.
         */
        public boolean matchesStage(int stage) {
            return stage >= minStage;
        }
        
        /**
         * Formats the value to be used for an display. If the object
         * or the result value is null, displays an empty value.
         */
        public String display(Object obj) {
            String empty = Messages.get("cmds.info.nullValue");
            if (obj == null)  {
                return empty;
            }
            Object result = value.serialize(obj);
            if (result == null) {
                return empty;
            }
            return result.toString();
        }
    }
    
}
