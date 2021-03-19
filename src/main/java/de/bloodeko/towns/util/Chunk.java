package de.bloodeko.towns.util;

import org.bukkit.Location;
import org.bukkit.entity.Entity;

import com.sk89q.worldedit.math.BlockVector3;

/**
 * i<<4 works like i*16 (positive and negative)
 * i>>4 works like i/16 (positive but with a wrong offset for negatives)
 */
public class Chunk {
    public final int x;
    public final int z;
    
    private Chunk(int x, int z) {
        this.x = x;
        this.z = z;
    }
    
    public Chunk add(int x, int z) {
        return new Chunk(this.x + x, this.z + z);
    }
    
    public Chunk add(Yaw yaw) {
        return new Chunk(x + yaw.getX(), z + yaw.getZ());
    }
    
    /**
     * Gets the chunks nearby in a square range.
     */
    public Chunk[] getNear(int range) {
        return getChunksWithin(x - range, z - range, x + range + 1, z + range + 1);
    }
    
    /**
     * Returns the Chunks within the area.
     */
    public Chunk[] getChunksWithin(Chunk a, Chunk b) {
        return getChunksWithin(Math.min(a.x, b.x), Math.min(a.x, b.x),
          Math.min(a.z, b.z), Math.max(a.z, b.z));
    }
    
    /**
     * Returns the chunks in the area by looping over the startPos include and 
     * the endPos exclusive. The startPos needs to be smaller than the endPos.
     */
    private static Chunk[] getChunksWithin(int startX, int startZ, int endX, int endZ) {
        int a = endX - startX;
        int b = endZ - startZ;
        Chunk[] chunks = new Chunk[a*b];
        int i = 0;
        for (int x = startX; x < endX; x++) {
            for (int z = startZ; z < endZ; z++) {
                chunks[i++] = Chunk.of(x, z);
            }
        }
        return chunks;
    }
    
    
    public static Chunk of(int x, int z) {
        return new Chunk(x, z);
    }
    
    public static Chunk fromCoords(int x, int z) {
        return new Chunk(x >> 4, z >> 4);
    }
    
    public static Chunk fromLocation(Location loc) {
        return fromCoords(loc.getBlockX(), loc.getBlockZ());
    }
    
    public static Chunk fromEntity(Entity entiy) {
        Location loc = entiy.getLocation();
        return fromCoords(loc.getBlockX(), loc.getBlockZ());
    }
    
    public static Chunk fromString(String name) {
        String[] coords = name.split(",");
        int x = Integer.parseInt(coords[0]);
        int z = Integer.parseInt(coords[1]);
        return new Chunk(x, z);
    }
    
    /**
     * Returns the smallest position for the chunk coordinates.
     */
    public static BlockVector3 getMinPos(int chX, int chZ) {
        return BlockVector3.at(getMin(chX), 0, getMin(chZ));
    }
    
    /**
     * Returns the highest position for the chunk coordinates.
     */
    public static BlockVector3 getMaxPos(int chX, int chZ) {
        return BlockVector3.at(getMax(chX), 256, getMax(chZ));
    }
    
    /**
     * Returns the lowest coordinate for this chunk coordinate.
     */
    public static int getMin(int i) {
        return i << 4;
    }
    
    /**
     * Returns the highest coordinate for this chunk coordinate.
     */
    public static int getMax(int i) {
        return getMin(i+1) -1;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Chunk) {
            Chunk chunk = (Chunk) obj;
            return x == chunk.x && z == chunk.z;
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return x * 31 + z;
    }
    
    @Override
    public String toString() {
        return x + "," + z;
    }
}
