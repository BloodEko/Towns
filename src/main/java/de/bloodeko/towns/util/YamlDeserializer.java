package de.bloodeko.towns.util;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

public class YamlDeserializer {
    private Map<Object, Object> map;

    public YamlDeserializer(YamlConfiguration config, Map<Object, Object> map) {
        this.map = map;
        deserialize(map, config);
    }
    
    public Map<Object, Object> getResult() {
        return map;
    }
    
    private void deserialize(Map<Object, Object> map, ConfigurationSection section) {
        for (String name : section.getKeys(false)) {
            if (section.isConfigurationSection(name)) {
                Map<Object, Object> submap = new HashMap<>();
                deserialize(submap, section.getConfigurationSection(name));
            } 
            else {
                map.put(name, section.get(name));
            }
        }
    }
}
