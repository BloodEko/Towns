package de.bloodeko.towns.core.townnames;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.bloodeko.towns.util.ModifyException;
import de.bloodeko.towns.util.Util;

public class NameService {
    private final Map<String, Integer> lookup;
    private final Map<Integer, String> data;
    
    public NameService() {
        this.lookup = new HashMap<>();
        this.data = new HashMap<>();
    }

    /**
     * Removes mappings associated with this name.
     * Then creates new mappings for this name.
     */
    public void rename(int id, String name) {
        String oldName = getName(id);
        if (oldName != null) {
            lookup.remove(oldName.toLowerCase());
        }
        lookup.put(name.toLowerCase(), id);
        data.put(id, name);
    }

    /**
     * Ensures this name is a valid length, contains valid characters 
     * and is available. Otherwise throws an exception.
     */
    public void verify(String name) {
        name = name.toLowerCase();
        if (name.length() < 3 || name.length() > 20) {
            throw new ModifyException("town.townregistry.invalidNameSize");
        }
        if (!Util.isValidName(name)) {
            throw new ModifyException("town.townregistry.invalidNameChars");
        }
        if (lookup.containsKey(name)) {
            throw new ModifyException("town.townregistry.nameAlreadyTaken");
        }
    }
    
    /**
     * Removes mappings associated for that town.
     */
    public void remove(Integer id) {
        lookup.values().removeIf(val -> val == id);
        data.remove(id);
    }
    
    /**
     * Returns any names that start with the input
     * string by ignoring casing.
     */
    public List<String> startingWith(String str) {
        str = str.toLowerCase();
        List<String> list = new ArrayList<>();
        for (String name : data.values()) {
            if (name.toLowerCase().startsWith(str)) {
                list.add(name);
            }
        }
        return list;
    }
    
    /**
     * Returns the name that equals the input string
     * by ignoring casing, or null.
     */
    public String getName(String str) {
        Integer id = getId(str);
        return getName(id);
    }
    
    /**
     * Returns the name by id, or null.
     */
    public String getName(Integer id) {
        return data.get(id);
    }
    
    /**
     * Returns the town by name, or null.
     */
    public Integer getId(String name) {
        return lookup.get(name.toLowerCase());
    }
    
    /**
     * Returns a view of the present data.
     */
    public Map<Integer, String> getView() {
        return Collections.unmodifiableMap(data);
    }
}
