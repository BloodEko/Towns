package de.bloodeko.towns.town;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import de.bloodeko.towns.util.Chunk;
import de.bloodeko.towns.util.ModifyException;
import de.bloodeko.towns.util.Util;

/**
 * 
 */
public class TownRegistry {
    private Map<String, Town> towns;
    private ChunkMap map;
    private int nextId;
    
    public TownRegistry(Map<String, Town> towns, ChunkMap map, int nextId) {
        this.towns = towns;
        this.map = map;
        this.nextId = nextId;
    }
    
    /**
     * Founds a town at this location and adds it to the service. Ensures the name and 
     * location is valid. Throws an exception otherwise.
     */
    public Town createTown(Chunk chunk, String name, UUID owner) {
        if (map.hasTown(chunk)) {
            throw new ModifyException("There is already a town at this location.");
        }
        if (owner == null) {
            throw new ModifyException("Can't create a town without owner.");
        }
        validateName(name);
        Town town = TownFactory.newTown(nextId++, name, chunk, owner);
        town.getArea().expand(map, town, chunk);
        add(town);
        return town;
    }
    
    /**
     * Returns the id, the next will have.
     */
    public int getNextId() {
        return nextId;
    }
    
    /**
     * Adds a town with this name.
     */
    public void add(Town town) {
        towns.put(town.getName(), town);
    }
    
    /**
     * Removes the towns mapping and adds a mapping with the new name.
     * Throws an exception if the name is already taken.
     */
    public void rename(Town town, String to) {
        if (!town.getName().equalsIgnoreCase(to)) {
            if (containsName(to)) {
                throw new ModifyException("There is already a town with this name.");
            }
        }
        towns.remove(town.getName());
        towns.put(to, town);
    }
    
    /**
     * Finds the town that matches this name. Otherwise throws an exception.
     */
    public Town get(String name) {
        Town town = find(name);
        if (town == null) {
            throw new ModifyException("No town was found with this name.");
        }
        return town;
    }
    
    /**
     * Returns a town that matches this name or null.
     */
    public Town find(String name) {
        name = name.toLowerCase();
        for (Entry<String, Town> entry : towns.entrySet()) {
            if (entry.getKey().toLowerCase().equals(name)) {
                return entry.getValue();
            }
        }
        return null;
    }
    
    /**
     * Returns true if the town-name is already taken.
     */
    public boolean containsName(String name) {
        return find(name) != null;
    }
    
    /**
     * Returns towns that start with this name.
     */
    public List<String> getTabCompletion(String name) {
        name = name.toLowerCase();
        List<String> list = new ArrayList<>();
        for (String town : towns.keySet()) {
            if (town.toLowerCase().startsWith(name)) {
                list.add(town);
            }
        }
        return list;
    }
    
    /**
     * Ensures this name is a valid length, contains valid characters 
     * and is available. Otherwise throws an exception.
     */
    public void validateName(String name) {
        name = name.toLowerCase();
        if (name.length() < 3 || name.length() > 20) {
            throw new ModifyException("The name is too short/long.");
        }
        if (!Util.isValidName(name)) {
            throw new ModifyException("The name contains invalid characters.");
        }
        if (containsName(name)) {
            throw new ModifyException("There is already a town with this name.");
        }
    }
}
