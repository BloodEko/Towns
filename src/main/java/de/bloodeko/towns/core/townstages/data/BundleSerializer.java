package de.bloodeko.towns.core.townstages.data;

import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Material;

import de.bloodeko.towns.core.townstages.domain.Bundle;
import de.bloodeko.towns.util.Node;
import de.bloodeko.towns.util.Node.Pair;

/**
 * Database helper, which serializes and deserializes bundles
 * to an uniform Node format.
 */
public class BundleSerializer {
    
    /**
     * Returns a node which has several Material/Integer mappings
     * representing bundles.
     */
    public static Node serialize(Map<Material, Bundle> bundles) {
        Node node = new Node();
        for (Entry<Material, Bundle> entry : bundles.entrySet()) {
            node.set(entry.getKey().toString(), entry.getValue().getQty());
        }
        return node;
    }

    /**
     * Returns a Map of bundles from a Node which contains
     * Material/Integer mappings representing bundles.
     * Uses the input map as template to modify the given values.
     */
    public static Map<Material, Bundle> deserialize(Node node, Map<Material, Bundle> map) {
        for (Pair pair : node.entries()) {
            Material material = Material.valueOf(pair.key);
            int amount = Integer.parseInt(pair.value.toString());
            
            Bundle bundle = map.get(material);
            if (bundle == null) {
                map.put(material, Bundle.newFilledOne(material, 0, amount));
            } else {
                bundle.setQty(amount);
            }
        }
        return map;
    }
}
