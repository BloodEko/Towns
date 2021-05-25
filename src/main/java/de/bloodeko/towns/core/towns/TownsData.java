package de.bloodeko.towns.core.towns;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.bloodeko.towns.util.Node;

public class TownsData {
    
    /**
     * Returns a TownService from a node that 
     * has a "towns" and "nextId" section.
     */
    public static TownService read(Node node) {
        Map<Integer, Town> map = new HashMap<>();
        if (node.get("towns") != null) {
            for (String str : node.getStringList("towns")) {
                Integer id = Integer.valueOf(str);
                map.put(id, new Town(id));    
            }
        }
        int nextId = node.getInt("nextId");
        if (nextId == 0) {
            nextId++;
        }
        return new TownService(map, nextId);
    }
    
    /**
     * Returns a Node that has a "towns" and "nextId" section.
     */
    public static Node write(TownService service) {
        Node node = new Node();
        List<String> list = new ArrayList<>();
        for (Integer id : service.keySet()) {
            list.add(id.toString());
        }
        node.set("towns", list);
        node.set("nextId", service.getNextId());
        return node;
    }
    
}
