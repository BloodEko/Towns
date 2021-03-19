package de.bloodeko.towns.town;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import de.bloodeko.towns.util.Chunk;
import de.bloodeko.towns.util.ModifyException;
import de.bloodeko.towns.util.Util;

public class TownRegistry {
    private Map<String, Town> byName;
    private Map<String, Town> byId;
    private ChunkMap map;
    private int id;
    
    public TownRegistry(ChunkMap map, int nextId) {
        this.byName = new HashMap<>();
        this.byId = new HashMap<>();
        this.map = map;
        this.id = nextId;
    }
    
    public int getId() {
        return id;
    }
    
    /**
     * Creates a town as player with included safety checks, that might 
     * throw an exception. Registers the town to known services.
     */
    public void foundTown(Chunk chunk, String name, UUID owner) {
        validateCreation(chunk, name, owner);
        Town town = TownFactory.newTown(map, id++, name, chunk, owner);
        TownFactory.registerTown(town, map, this, TownFactory.getWorldManager());
    }
    
    /**
     * Validates that a town with these settings an be created.
     * Otherwise throws an exception.
     */
    public void validateCreation(Chunk chunk, String name, UUID owner) {
        if (map.hasTown(chunk)) {
            throw new ModifyException("town.townregistry.chunkAlreadyTaken");
        }
        if (owner == null) {
            throw new ModifyException("Can't create a town without owner.");
        }
        validateName(name);
    }
    
    /**
     * Sets this town to the registry. Its name will be
     * taken into account for tab-completions and similar.
     */
    public void add(Town town) {
        byName.put(town.getSettings().getName(), town);
        byId.put(town.getId() + "", town);
    }

    /**
     * Removes the mapping for this name.
     */
    public void remove(String name) {
        byName.remove(name);
    }
    
    /**
     * Removes the towns mapping and adds a mapping with the new name.
     * Throws an exception if the name is already taken.
     */
    public void rename(Town town, String to) {
        if (!town.getSettings().getName().equalsIgnoreCase(to)) {
            if (containsName(to)) {
                throw new ModifyException("town.townregistry.nameAlreadyTaken");
            }
        }
        byName.remove(town.getSettings().getName());
        byName.put(to, town);
    }
    
    /**
     * Finds the town that matches this name. 
     * Otherwise throws an exception.
     */
    public Town get(String name) {
        Town town = find(name);
        if (town == null) {
            throw new ModifyException("town.townregistry.nameNotFound");
        }
        return town;
    }
    
    /**
     * Gets the the town with this id. 
     * Otherwise throws an exception.
     */
    public Town getId(String id) {
        Town town = byId.get(id);
        if (town == null) {
            throw new ModifyException("town.townregistry.idNotFound");
        }
        return town;
    }
    
    /**
     * Returns a town that matches this name or null.
     */
    public Town find(String name) {
        name = name.toLowerCase();
        for (Entry<String, Town> entry : byName.entrySet()) {
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
     * Return all the registered towns.
     */
    public Collection<Town> getTowns() {
        return byName.values();
    }
    
    /**
     * Returns towns that start with this name.
     */
    public List<String> getMatches(String name) {
        name = name.toLowerCase();
        List<String> list = new ArrayList<>();
        for (String town : byName.keySet()) {
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
            throw new ModifyException("town.townregistry.invalidNameSize");
        }
        if (!Util.isValidName(name)) {
            throw new ModifyException("town.townregistry.invalidNameChars");
        }
        if (containsName(name)) {
            throw new ModifyException("town.townregistry.nameAlreadyTaken");
        }
    }
}
