package de.bloodeko.towns.core.townarea;

import java.util.Collections;
import java.util.Set;

import com.sk89q.worldguard.commands.task.RegionAdder;
import com.sk89q.worldguard.protection.managers.RegionManager;

import de.bloodeko.towns.util.Chunk;
import de.bloodeko.towns.util.ModifyException;

/**
 * Represents the whole area of a town including 
 * shape, bounding box and WorldGuard region.
 */
public class ChunkArea {
    private final Set<Chunk> chunks;
    private final ChunkSides sides;
    private final TownRegion view;
    
    ChunkArea(Set<Chunk> chunks, ChunkSides sides, TownRegion view) {
        this.chunks = chunks;
        this.sides = sides;
        this.view = view;
    }
    
    /**
     * Adds the chunk to the area. Updates the shape for
     * the chunk, might re-add the WorldGuard region.
     */
    void add(RegionManager manager, Chunk ch) {
        chunks.add(ch);
        if (sides.updateFor(ch)) {
            resize(manager);
        }
    }
    
    /**
     * Adds the chunk to the area. Updates the shape
     * whole shape, might re-add the WorldGuard region.
     */
    void remove(RegionManager manager, Chunk ch) {
        chunks.remove(ch);
        if (sides.updateFor(chunks)) {
            resize(manager);
        }
    }
    
    private void resize(RegionManager manager) {
        view.setBoundingBox(sides);
        RegionAdder adder = new RegionAdder(manager, view);
        try {
            adder.call();
        } catch (Exception ex) {
            throw new ModifyException("Could not resize region.", ex);
        }
    }

    /**
     * Returns true if the area contains the chunk.
     */
    boolean contains(Chunk chunk) {
        return chunks.contains(chunk);
    }
    
    /**
     * Returns the amount of chunks covered.
     */
    public int size() {
        return chunks.size();
    }

    /**
     * Returns the total square chunks, that the sides cover.
     */
    public int getMaxSize() {
        return sides.getSize();
    }
    
    /**
     * Returns a copy of the current sides.
     */
    public ChunkSides getSides() {
        return new ChunkSides(sides.minX(), sides.minZ(), sides.maxX(), sides.maxZ());
    }
    
    /**
     * Returns a view of the chunks.
     */
    public Set<Chunk> getChunkView() {
        return Collections.unmodifiableSet(chunks);
    }

    /**
     * Returns the WorldGuard region for that area.
     */
    public TownRegion getRegion() {
        return view;
    }
}
