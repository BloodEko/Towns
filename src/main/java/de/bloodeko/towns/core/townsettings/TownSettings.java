package de.bloodeko.towns.core.townsettings;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import de.bloodeko.towns.core.townarea.TownRegion;
import de.bloodeko.towns.core.townsettings.legacy.Setting;
import de.bloodeko.towns.util.ModifyException;

public class TownSettings {
    private Set<Setting> settings;
    private Map<Object, Object> flags;
    
    public TownSettings(Set<Setting> settings, Map<Object, Object> flags) {
        this.settings = settings;
        this.flags = flags;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public TownSettings(TownRegion region) {
        this.settings = new HashSet<>();
        this.flags = (Map) region.getFlags();
    }

    /**
     * Returns all the setting values present.
     */
    public Set<Setting> settings() {
        return settings;
    }
    
    /**
     * Returns the map of WorldGuard flags
     */
    public Map<Object, Object> getFlags() {
        return flags;
    }
    
    /**
     * Returns the object set for that setting, or null.
     * Uses the setting to read from the flag-map.
     */
    public Object get(Setting setting) {
        return setting.read(flags);
    }

    /**
     * Returns true if a mapping for this setting exists.
     */
    public boolean has(Setting setting) {
        return settings.contains(setting);
    }
    
    /**
     * Throws an exception if this setting is already set.
     * Adds the setting. Initializes the setting to the flag-map.
     */
    public void addSetting(Setting setting, Integer town) {
        if (settings.contains(setting)) {
            throw new ModifyException("town.townsettings.alreadyBought");
        }
        settings.add(setting);
        setting.init(flags, town);
    }
}
