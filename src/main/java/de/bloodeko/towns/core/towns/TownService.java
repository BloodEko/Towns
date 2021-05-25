package de.bloodeko.towns.core.towns;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

public class TownService {
    private final Map<Integer, Town> data;
    private int nextId;
    
    public TownService(Map<Integer, Town> map, int nextId) {
        this.data = map;
        this.nextId = nextId;
    }
    
    /**
     * Returns a new created town and adds it to the service.
     */
    public Town createTown() {
        Town town = new Town(nextId++);
        data.put(town.getId(), town);
        return town;
    }
    
    /**
     * Removes a town from the service.
     */
    public void remove(Integer id) {
        data.remove(id);
    }
    
    /**
     * Returns a town for that id, or null.
     */
    public Town get(Integer id) {
        return data.get(id);
    }
    
    /**
     * Returns a view of the towns.
     */
    public Set<Integer> keySet() {
        return Collections.unmodifiableSet(data.keySet());
    }
    
    /**
     * Returns a view of the next id.
     */
    public int getNextId() {
        return nextId;
    }
}
