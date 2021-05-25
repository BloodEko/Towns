package de.bloodeko.towns.core.townnames;

import java.util.Map.Entry;

import de.bloodeko.towns.util.Node;
import de.bloodeko.towns.util.Node.Pair;

public class NameData {
    
    /**
     * Reads the entries from the node, in a NAME:INT format.
     * They will be registered as names to the service.
     */
    public static NameService read(Node node) {
        NameService service = new NameService();
        for (Pair pair : node.entries()) {
            service.rename(Integer.parseInt(pair.value.toString()), pair.key);
        }
        return service;
    }
    
    /**
     * Writes all name mappings to a Node.
     */
    public static Node write(NameService service) {
        Node node = new Node();
        for (Entry<Integer, String> entry : service.getView().entrySet()) {
            node.set(entry.getValue(), entry.getKey());
        }
        return node;
    }
    
}
