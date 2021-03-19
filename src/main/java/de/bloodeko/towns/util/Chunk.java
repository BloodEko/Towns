package de.bloodeko.towns.util;

import org.bukkit.Location;
import org.bukkit.entity.Entity;

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
        return "Chunk[" + x + ", " + z + "]";
    }
}
