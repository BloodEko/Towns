package de.bloodeko.towns.core.townarea.legacy;

import de.bloodeko.towns.util.Chunk;

/**
 * Entity that holds the minimum and maximum
 * sides for a chunk shape.
 */
public class TownSides {
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
    
    /**
     * Updates the shape, so that it also 
     * covers the specified chunk.
     */
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
    
    /**
     * Returns the total size in chunks a square 
     * would require to fully match the shape.
     */
    public int getSize() {
        int a = maxX - minX + 1;
        int b = maxZ - minZ + 1;
        return a * b;
    }
    
    public TownSides copy() {
        return new TownSides(minX, maxX, minZ, maxZ);
    }

    /**
     * Creates sides that only contain this chunk.
     */
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
