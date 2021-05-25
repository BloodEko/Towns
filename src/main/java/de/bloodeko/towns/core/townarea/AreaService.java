package de.bloodeko.towns.core.townarea;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.sk89q.worldguard.protection.managers.RegionManager;

import de.bloodeko.towns.util.Chunk;

/**
 * Is a part of ChunkService.
 * Groups chunks into areas.
 */
public class AreaService {
    private final Map<Integer, ChunkArea> areas;
    private final RegionManager manager;
    
    AreaService(Map<Integer, ChunkArea> areas, RegionManager manager) {
        this.areas = areas;
        this.manager = manager;
    }
    
    /**
     * Removes the chunk from the old town. Adds the chunk to 
     * the new town. Creates a new area, if none is found.
     * Re-Adds both WorldGuard regions.
     */
    void set(Integer oldTown, int newTown, Chunk chunk) {
        removeArea(oldTown, chunk);
        addArea(newTown, chunk);
    }
    
    /**
     * Adds the chunk to the area, creates a new area
     * if none is found. Re-Adds the WorldGuad region.
     */
    void addArea(int town, Chunk chunk) {
        ChunkArea area = areas.get(town);
        if (area == null) {
            area = createChunkArea(town, chunk);
            areas.put(town, area);
        }
        area.add(manager, chunk);
    }
    
    private ChunkArea createChunkArea(int town, Chunk chunk) {
        HashSet<Chunk> chunks = new HashSet<>();
        chunks.add(chunk);
        ChunkSides sides = new ChunkSides(chunk);
        TownRegion view = new TownRegion(town);
        view.setBoundingBox(sides);
        return new ChunkArea(chunks, sides, view);
    }
    
    /**
     * Removes the chunk from the area, if is not null.
     * Re-Adds the WorldGuard region.
     */
    void removeArea(Integer town, Chunk chunk) {
        if (town != null) {
            ChunkArea area = areas.get(town);
            if (area != null) {
                area.remove(manager, chunk);
            }
        }
    }

    /**
     * Removes the mapping for this area.
     * The WorldGuard region still remains.
     */
    void remove(Integer id) {
        areas.remove(id);
    }

    /**
     * Returns the area for that town, or null.
     */
    ChunkArea get(Integer town) {
        return areas.get(town);
    }

    /**
     * Returns a view of they areas keyset.
     */
    Set<Integer> getAreasView() {
        return Collections.unmodifiableSet(areas.keySet());
    }
}
