package de.bloodeko.towns.util;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

public class YamlDeserializer {
    private Map<Object, Object> map;

    public YamlDeserializer(YamlConfiguration config) {
        this.map = new HashMap<>();
        deserialize(map, config);
    }
    
    public Map<Object, Object> getResult() {
        return map;
    }
    
    private void deserialize(Map<Object, Object> root, ConfigurationSection section) {
        for (String name : section.getKeys(false)) {
            if (section.isConfigurationSection(name)) {
                Map<Object, Object> map = new HashMap<>();
                root.put(name, map);
                deserialize(map, section.getConfigurationSection(name));
            } 
            else {
                root.put(name, section.get(name));
            }
        }
    }
}
