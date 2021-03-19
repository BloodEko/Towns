package de.bloodeko.towns.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

public class YamlSerializer {
    private YamlConfiguration config;

    public YamlSerializer(YamlConfiguration config, Map<String, Object> map) {
        this.config = config;
        serialize(config, map);
    }
    
    public YamlConfiguration getResult() {
        return config;
    }
    
    private void serialize(ConfigurationSection section, Map<?, ?> map) {
        for (Entry<?, ?> entry : map.entrySet()) {
            String key = entry.getKey().toString();
            Object value = entry.getValue();
            
            if ((value instanceof Collection && ((Collection<?>) value).isEmpty()) 
              || (value instanceof Map && ((Map<?, ?>) value).isEmpty())) {
                continue;
            }
            if (value instanceof Map) {
                serialize(section.createSection(key), (Map<?, ?>) value);
            }
            else if (value instanceof Set) {
                List<String> list = new ArrayList<>();
                for (Object obj : (Set<?>) value) {
                    list.add(obj.toString());
                }
                section.set(key, list);
            }
            else {
                section.set(key, value);
            }
        }
    }
}
