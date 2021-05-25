package de.bloodeko.towns.core.townarea;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import de.bloodeko.towns.Services;
import de.bloodeko.towns.core.towns.Town;
import de.bloodeko.towns.util.Chunk;
import de.bloodeko.towns.util.ModifyException;

/**
 * Represents a whole world map with the areas of towns.
 */
public class ChunkService {
    private final Map<Chunk, Integer> data;
    private final AreaService areas;
    
    ChunkService(Map<Chunk, Integer> data, AreaService areas) {
        this.data = data;
        this.areas = areas;
    }

    /**
     * Returns the town for that chunk or null.
     */
    public Town getTown(Chunk chunk) {
        return Services.townservice().get(get(chunk));
    }
    
    /** 
     * Returns the town as ID for that chunk or null.
     */
    public Integer get(Chunk chunk) {
        return data.get(chunk);
    }
    
    /** 
     * Returns true if the chunk has a town. 
     */
    public boolean has(Chunk chunk) {
        return data.containsKey(chunk);
    }

    /**
     * Throws an exception if the chunk is already taken.
     */
    public void verify(Chunk chunk) {
        if (has(chunk)) {
            throw new ModifyException("town.townarea.alreadyTaken");
        }
    }
    
    /**
     * Adds the chunk to the town. This might remove it from
     * another town and causes WorldGuard regions to update.
     */
    public void set(Chunk chunk, int town) {
        areas.set(data.get(chunk), town, chunk);
        data.put(chunk, town);
    }
    
    /**
     * Removes the chunk from any town. Causes the WorldGuard region to update.
     * Throws an exception if the area size is 1.
     */
    public void remove(Chunk chunk) {
        Integer id = data.get(chunk);
        ChunkArea area = areas.get(id);
        
        if (area == null || area.size() == 1) {
            throw new ModifyException("town.townarea.cantRemoveLast");
        }
        areas.removeArea(id, chunk);
        data.remove(chunk);
    }

    /**
     * Removes the whole area for that town.
     */
    public void removeAll(Integer id) {
        ChunkArea area = areas.get(id);
        areas.remove(id);
        
        for (Chunk chunk : area.getChunkView()) {
            data.remove(chunk);
        }
    }
    
    /**
     * Returns the area for that town or null.
     */
    public ChunkArea getArea(Integer town) {
        return areas.get(town);
    }
    
    /**
     * Returns the WorldGuard region for that town or null.
     */
    public TownRegion getRegion(Integer town) {
        ChunkArea area = areas.get(town);
        if (area == null) {
            return null;
        }
        return area.getRegion();
    }
    
    /**
     * Returns a view of the data.
     */
    public Map<Chunk, Integer> getView() {
        return Collections.unmodifiableMap(data);
    }

    /**
     * Returns a view of all areas.
     */
    public Set<Integer> getAreas() {
        return areas.getAreasView();
    }
}
