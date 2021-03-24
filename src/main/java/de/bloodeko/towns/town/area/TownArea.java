package de.bloodeko.towns.town.area;

import java.util.Set;

import com.sk89q.worldedit.math.BlockVector3;

import de.bloodeko.towns.town.ChunkMap;
import de.bloodeko.towns.town.Town;
import de.bloodeko.towns.town.TownFactory;
import de.bloodeko.towns.util.Chunk;
import de.bloodeko.towns.util.ModifyException;
import de.bloodeko.towns.util.Node;

public class TownArea {
    private Set<Chunk> chunks;
    private TownSides sides;
    private ClaimRules rules;
    private ChunkRegion region;
    
    public TownArea(Set<Chunk> chunks, TownSides sides, ClaimRules rules, ChunkRegion region) {
        this.chunks = chunks;
        this.sides = sides;
        this.rules = rules;
        this.region = region;
    }
    
    public TownSides getSides() {
        return sides.copy();
    }
    
    public int getSize() {
        return chunks.size();
    }
    
    public int getMaxSize() {
        return sides.getSize();
    }
    
    public Set<Chunk> getChunks() {
        return chunks;
    }
    
    public ChunkRegion getRegion() {
        return region;
    }
    
    /**
     * Expands the area by this chunk. Throws an exception if the chunk is already taken in the ChunkMap.
     * Might throw an exception when checking TownRules. Updates the TownSides and the WorldGuard cuboid.
     * Adds the chunk to the area. Sets the chunk for the town in the ChunkMap. 
     */
    public void expand(ChunkMap map, Town town, Chunk chunk) {
        if (map.getTown(chunk) != null) {
            throw new ModifyException("town.townarea.alreadyTaken");
        }
        rules.checkExpand(map, town, this, chunk);
        updateShape(chunk);
        chunks.add(chunk);
        map.setTown(chunk, town);
    }
    
    /**
     * Removes this chunk from the area. Throws an exception if the area doesn't contain it.
     * Updates the TownSides and the WorldGuard cuboid. Removes the chunk from the ChunkMap.
     */
    public void contract(ChunkMap map, Chunk chunk) {
        if (!chunks.contains(chunk)) {
            throw new ModifyException("town.townarea.notTaken");
        }
        if (chunks.size() == 1) {
            throw new ModifyException("town.townarea.cantRemoveLast");
        }
        chunks.remove(chunk);
        updateShape();
        map.removeTown(chunk);
    }
    
    /**
     * Modifies the TownShape to match this change.
     * When a change is found, updates the cuboid.
     */
    private void updateShape(Chunk chunk) {
        TownSides oldSides = sides.copy();
        sides.update(chunk);
        if (!oldSides.equals(sides)) {
            updateCuboid();
        }
    }
    
    /**
     * Recalculates TownSides and the ChunkRegion
     * to cover all current chunks.
     */
    public void updateShape() {
        TownSides oldSides = sides;
        sides = TownSides.fromChunk(chunks.iterator().next());
        for (Chunk chunk : chunks) {
            sides.update(chunk);
        }
        if (!oldSides.equals(sides)) {
            updateCuboid();
        }
    }
    
    /**
     * Updated the WorldGuard cuboid, that it contains all chunks.
     * Doing so by calculating the minimum and maximum boundaries.
     */
    private void updateCuboid() {
        BlockVector3 min = Chunk.getMinPos(sides.minX, sides.minZ);
        BlockVector3 max = Chunk.getMaxPos(sides.maxX, sides.maxZ);
        region.resize(min, max);
    }
    
    public Node serialize() {
        Node node = new Node();
        node.set("chunks", chunks);
        return node;
    }
    
    public static TownArea deserialize(Node area, Set<Chunk> chunks, ChunkRegion region) {
        for (String chunk : area.getStringList("chunks")) {
            chunks.add(Chunk.fromString(chunk));
        }
        return TownFactory.newArea(chunks, region);
    }
}
