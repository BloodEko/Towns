package de.bloodeko.towns.core.townarea;

import java.util.Set;

import de.bloodeko.towns.util.Chunk;

/**
 * Covers the minimum and maximum chunks to produce
 * a square from a chunk based selection.
 */
public class ChunkSides {
    private int minX;
    private int minZ;
    private int maxX;
    private int maxZ;
    
    ChunkSides(int minX, int minZ, int maxX, int maxZ) {
        this.minX = minX;
        this.minZ = minZ;
        this.maxX = maxX;
        this.maxZ = maxZ;
    }
    
    ChunkSides(Chunk chunk) {
        this(chunk.x, chunk.z, chunk.x, chunk.z);
    }

    public int minX() { return minX; }
    public int maxX() { return maxX; }
    public int minZ() { return minZ; }
    public int maxZ() { return maxZ; }
    
    /**
     * Updates to include all these chunks.
     * Returns true if the area changed.
     */
    boolean updateFor(Set<Chunk> chunks) {
        ChunkSides sides = new ChunkSides(chunks.iterator().next());
        for (Chunk chunk : chunks) {
            sides.updateFor(chunk);
        }
        minX = sides.minX;
        minZ = sides.minZ;
        maxX = sides.maxX;
        maxZ = sides.maxZ;
        return this.equals(sides);
    }
    
    /**
     * Updates to include this chunk.
     * Returns true if the area changed.
     */
    public boolean updateFor(Chunk chunk) {
        boolean change = false;
        if (chunk.x < minX) {
            minX = chunk.x;
            change = true;
        }
        if (chunk.x > maxX) {
            maxX = chunk.x;
            change = true;
        }
        if (chunk.z < minZ) {
            minZ = chunk.z;
            change = true;
        }
        if (chunk.z > maxZ) {
            maxZ = chunk.z;
            change = true;
        }
        return change;
    }
    
    /**
     * Returns the total square area in chunks, that
     * this sides cover.
     */
    public int getSize() {
        int a = maxX - minX + 1;
        int b = maxZ - minZ + 1;
        return a * b;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ChunkSides) {
            ChunkSides side = (ChunkSides) obj;
            return side.minX == minX && side.maxX == maxX 
                && side.minZ == minZ && side.maxZ == maxZ;
        }
        return false;
    }
    
    @Override
    public String toString() {
        return "[minX:" + minX + " minZ:" + minZ 
          + " maxX:" + maxX + " maxZ:" + maxZ + "]";
    }
}
