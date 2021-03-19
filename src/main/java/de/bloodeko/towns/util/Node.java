package de.bloodeko.towns.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

/**
 * Node with serialized data in a String-to-Object-Map.
 * Holds operations for reading and writing.
 */
public class Node {
    private Map<String, Object> map;
    
    public Node() {
        map = new HashMap<>();
    }
    
    /**
     * Sets the raw object.
     */
    public void set(String key, Object obj) {
        map.put(key, obj);
    }
    
    /**
     * Gets an raw object or null.
     */
    public Object get(String key) {
        return map.get(key);
    }
    
    /**
     * Returns the value as int or 0.
     */
    public int getInt(String key) {
        try {
            return Integer.valueOf(map.get(key).toString());
        } catch (Exception ex) {
            return 0;
        }
    }
    
    /**
     * Returns the value as String or null.
     */
    public String getString(String key) {
        Object obj = map.get(key);
        return obj  == null ? null : obj.toString();
    }
    
    /**
     * Returns an empty Node or the value casted to a Node.
     * Might throw an exception if queried on a wrong key.
     */
    public Node getNode(String key) {
        Object obj = map.get(key);
        return obj == null ? new Node() : (Node) obj;
    }
    
    /**
     * Returns the value casted as an String-List.
     * Might throw an exception.
     */
    @SuppressWarnings("unchecked")
    public List<String> getStringList(String key) {
        return (List<String>) map.get(key);
    }

    /**
     * Returns the value casted as an String-List and converted 
     * to an UUID-Set. Might throw an exception.
     */
    public Set<UUID> getUUIDSet(String key) {
        Set<UUID> set = new HashSet<>();
        List<String> list = getStringList(key);
        if (list == null) {
            return set;
        }
        for (String uuid : list) {
            set.add(UUID.fromString(uuid));
        }
        return set;
    }
    
    /**
     * Returns null or the value casted to an String and converted
     * to an UUID. Might throw an exception.
     */
    public UUID getUUID(String key) {
        Object obj = map.get(key);
        return obj == null ? null : UUID.fromString((String) obj);
    }

    /**
     * Returns true if this node has no entries.
     */
    public boolean isEmpty() {
        return map.isEmpty();
    }
    
    /**
     * Returns the entrySet wrapped in a List for easy
     * String-to-Object reading.
     */
    public List<Pair> entries() {
        List<Pair> list = new ArrayList<>();
        for (Entry<String, Object> entry : map.entrySet()) {
            list.add(new Pair(entry.getKey(), entry.getValue()));
        }
        return list;
    }
    
    /**
     * Adds a empty sub-node to this Node and
     * returns it.
     */
    public Node newNode(String key) {
        Node node = new Node();
        map.put(key, node);
        return node;
    }
    
    public static class Pair {
        public final String key;
        public final Object value;
        
        public Pair(String key, Object value) {
            this.key = key;
            this.value = value;
        }
    }
}
