package de.bloodeko.towns.util;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

public class YamlDeserializer {
    private Node root;

    public YamlDeserializer(YamlConfiguration config) {
        root = new Node();
        deserialize(root, config);
    }
    
    public Node getRoot() {
        return root;
    }
    
    private void deserialize(Node root, ConfigurationSection section) {
        for (String name : section.getKeys(false)) {
            if (section.isConfigurationSection(name)) {
                Node node = new Node();
                root.set(name, node);
                deserialize(node, section.getConfigurationSection(name));
            } 
            else {
                root.set(name, section.get(name));
            }
        }
    }
}
