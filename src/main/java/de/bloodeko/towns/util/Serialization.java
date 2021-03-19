package de.bloodeko.towns.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class Serialization {
    
    /**
     * Returns the input String as int or 0.
     */
    public static int asInt(Object obj) {
        if (obj == null) return 0;
        return Integer.parseInt(obj.toString());
    }
    
    /**
     * Returns the input as String or null.
     */
    public static String asString(Object obj) {
        if (obj == null) return null;
        return obj.toString();
    }
    
    /**
     * Returns the input String as UUID
     * or null.
     */
    public static UUID asUUID(Object obj) {
        if (obj == null) return null;
        return UUID.fromString((String) obj);
    }
    
    /**
     * Returns the input String-List as
     * UUID-Set or an empty Set.
     */
    @SuppressWarnings("unchecked")
    public static Set<UUID> asUUIDSet(Object obj) {
        Set<UUID> set = new HashSet<>();
        if (obj == null) {
            return set;
        }
        for (String uuid : (List<String>) obj) {
            set.add(UUID.fromString(uuid));
        }
        return set;
    }
    
    /**
     * Returns the input Map as String-to-Object-Map.
     * Or an empty HashMap.
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> asRoot(Object obj) {
        if (obj == null) return new HashMap<>();
        return (Map<String, Object>) obj;
    }

    /**
     * Returns the input Map as Object-to-Object-Map.
     * Or an empty HashMap.
     */
    @SuppressWarnings("unchecked")
    public static Map<Object, Object> asMap(Object obj) {
        if (obj == null) return new HashMap<>();
        return (Map<Object, Object>) obj;
    }
    
    
}
