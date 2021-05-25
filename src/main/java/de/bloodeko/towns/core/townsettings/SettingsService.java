package de.bloodeko.towns.core.townsettings;

import java.util.Collections;
import java.util.Map;

import de.bloodeko.towns.core.townsettings.legacy.SettingsRegistry;

public class SettingsService {
    private Map<Integer, TownSettings> data;
    private SettingsRegistry registry;
    
    public SettingsService(Map<Integer, TownSettings> data, SettingsRegistry registry) {
        this.data = data;
        this.registry = registry;
    }
    
    /**
     * Returns the registry for available settings.
     */
    public SettingsRegistry registry() {
        return registry;
    }
    
    /**
     * Returns the settings for that town, or null.
     */
    public TownSettings getSettings(int id) {
        return data.get(id);
    }
    
    /**
     * Sets the settings for that town.
     */
    public void put(int id, TownSettings settings) {
        data.put(id, settings);
    }
    
    /**
     * Removes the mapping for that town.
     */
    public void remove(int id) {
        data.remove(id);
    }
    
    /**
     * Returns a view of the settings data.
     */
    public Map<Integer, TownSettings> getView() {
        return Collections.unmodifiableMap(data);
    }
}
