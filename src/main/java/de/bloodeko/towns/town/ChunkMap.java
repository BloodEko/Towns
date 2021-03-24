package de.bloodeko.towns.town;

import java.util.Map;

import de.bloodeko.towns.util.Chunk;

/**
 * Represents a per-world service that maps 
 * chunks to towns and holds that data.
 */
public class ChunkMap {
    private Map<Chunk, Town> map;
    
    public ChunkMap(Map<Chunk, Town> map) {
        this.map = map;
    }
    
    /** Returns the town at this chunk or null. */
    public Town getTown(Chunk chunk) {
        return map.get(chunk);
    }
    
    /** Returns true if the Chunk is taken. */
    public boolean hasTown(Chunk chunk) {
        return map.containsKey(chunk);
    }
    
    /**  Sets a town to this chunk. */
    public void setTown(Chunk chunk, Town town) {
        map.put(chunk, town);
    }
    
    /** Removes a town from this chunk.*/
    public void removeTown(Chunk chunk) {
        map.remove(chunk);
    }
}
