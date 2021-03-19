package de.bloodeko.towns.session1;

import org.bukkit.Location;
import org.bukkit.entity.Entity;

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
