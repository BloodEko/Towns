package de.bloodeko.towns.town;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldguard.commands.task.RegionAdder;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;

import de.bloodeko.towns.util.Chunk;
import de.bloodeko.towns.util.ModifyException;

public class TownArea {
    private Set<Chunk> chunks;
    private TownSides sides;
    private ChunkRules rules;
    private ChunkRegion region;
    
    public TownArea(Set<Chunk> chunks, TownSides sides, ChunkRules rules, ChunkRegion region) {
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
        if (map.query(chunk) != null) {
            throw new ModifyException("Chunk is already taken!");
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
            throw new ModifyException("The town doesn't contain the chunk");
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
    
    
    public static class ChunkRegion extends ProtectedCuboidRegion {
        private RegionManager manager;
        private Set<Chunk> area;
        
        public ChunkRegion(RegionManager manager, Set<Chunk> area, String id, boolean transientRegion, 
          BlockVector3 p1, BlockVector3 p2) {
            super(id, transientRegion, p1, p2);
            this.manager = manager;
            this.area = area;
        }
        
        @Override
        public boolean contains(BlockVector3 pt) {
            Chunk chunk = Chunk.fromCoords(pt.getBlockX(), pt.getBlockZ());
            return area.contains(chunk);
        }
        
        public void resize(BlockVector3 min, BlockVector3 max) {
            setMinMaxPoints(Arrays.asList(min, max));
            RegionAdder adder = new RegionAdder(manager, this);
            try {
                adder.call();
            } catch (Exception ex) {
                throw new ModifyException("Could not redefine.", ex);
            }
        }
    }
    
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("chunks", chunks);
        return map;
    }

    @SuppressWarnings("unchecked")
    public static TownArea deserialize(Map<String, Object> root, Set<Chunk> chunks, ChunkRegion region) {
        for (String chunk : (List<String>) root.get("chunks")) {
            chunks.add(Chunk.fromString(chunk));
        }
        return TownFactory.newArea(chunks, region);
    }
    
    
    
    public static class TownSides {
        public int minX;
        public int maxX;
        public int minZ;
        public int maxZ;
        
        public TownSides(int minX, int maxX, int minZ, int maxZ) {
            this.minX = minX;
            this.maxX = maxX;
            this.minZ = minZ;
            this.maxZ = maxZ;
        }
        
        public void update(Chunk chunk) {
            if (chunk.x < minX) {
                minX = chunk.x;
            }
            if (chunk.x > maxX) {
                maxX = chunk.x;
            }
            if (chunk.z < minZ) {
                minZ = chunk.z;
            }
            if (chunk.z > maxZ) {
                maxZ = chunk.z;
            }
        }
        
        public int getSize() {
            int a = maxX - minX + 1;
            int b = maxZ - minZ + 1;
            return a * b;
        }

        public TownSides copy() {
            return new TownSides(minX, maxX, minZ, maxZ);
        }

        public static TownSides fromChunk(Chunk chunk) {
            return new TownSides(chunk.x, chunk.x, chunk.z, chunk.z);
        }
        
        @Override
        public boolean equals(Object obj) {
            if (obj instanceof TownSides) {
                TownSides side = (TownSides) obj;
                return side.minX == minX && side.maxX == maxX 
                    && side.minZ == minZ && side.maxZ == maxZ;
            }
            return false;
        }
        
        @Override
        public String toString() {
            return "[minX:" + minX + " maxX:" + maxX 
              + " minZ:" + minZ + " maxZ:" + maxZ + "]";
        }
    }
}
