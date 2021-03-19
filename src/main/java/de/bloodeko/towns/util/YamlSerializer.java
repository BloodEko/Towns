package de.bloodeko.towns.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import de.bloodeko.towns.util.Node.Pair;

public class YamlSerializer {
    private YamlConfiguration config;

    public YamlSerializer(YamlConfiguration config, Node towns) {
        this.config = config;
        serialize(config, towns);
    }
    
    public YamlConfiguration getResult() {
        return config;
    }
    
    private void serialize(ConfigurationSection section, Node node) {
        for (Pair pair : node.entries()) {
            String key = pair.key;
            Object value = pair.value;
            
            if ((value instanceof Collection && ((Collection<?>) value).isEmpty()) 
              || (value instanceof Node && ((Node) value).isEmpty())) {
                continue;
            }
            if (value instanceof Node) {
                serialize(section.createSection(key), (Node) value);
            }
            else if (value instanceof Set) {
                List<String> list = new ArrayList<>();
                for (Object obj : (Set<?>) value) {
                    list.add(obj.toString());
                }
                section.set(key, list);
            }
            else if (value instanceof UUID) {
                section.set(key, value.toString());
            }
            else {
                section.set(key, value);
            }
        }
    }
}
