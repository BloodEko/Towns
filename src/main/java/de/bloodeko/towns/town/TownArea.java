package de.bloodeko.towns.town;

import java.util.Set;

import de.bloodeko.towns.util.Chunk;
import de.bloodeko.towns.util.ModifyException;

public class TownArea {
    private Set<Chunk> area;
    private int minX;
    private int maxX;
    private int minZ;
    private int maxZ;
    
    public TownArea(Set<Chunk> area, int minX, int maxX, int minZ, int maxZ) {
        this.area = area;
        this.minX = minX;
        this.maxX = maxX;
        this.minZ = minZ;
        this.maxZ = maxZ;
    }
    
    public int getMinX() { return minX; }
    public int getMaxX() { return maxX; }
    public int getMinZ() { return minZ; }
    public int getMaxZ() { return maxZ; }
    
    public int getSize() {
        return area.size();
    }
    
    public void expand(ChunkMap map, Town town, Chunk chunk) {
        if (map.query(chunk) != null) {
            throw new ModifyException("Chunk is already taken!");
        }
        boolean update = addShape(chunk, town.getName());
        if (update) {
            updateCuboid(town.getName());
        }
        area.add(chunk);
        map.setTown(chunk, town);
    }
    
    private boolean addShape(Chunk chunk, String name) {
        boolean update = false;
        if (chunk.x < minX) {
            minX = chunk.x;
            update = true;
        }
        if (chunk.x > maxX) {
            maxX = chunk.x;
            update = true;
        }
        if (chunk.z < minZ) {
            minZ = chunk.z;
            update = true;
        }
        if (chunk.z > maxZ) {
            maxZ = chunk.z;
            update = true;
        }
        return update;
    }
    
    private void updateShape(String name) {
        int a = minX, b = maxX;
        int c = minZ, d = maxZ;
        
        Chunk base = area.iterator().next();
        minX = base.x; maxX = base.x;
        minZ = base.z; maxZ = base.z;
        
        for (Chunk chunk : area) {
            addShape(chunk, name);
        }
        if (a != minX || b != maxX || c != minZ || d != maxZ) {
            updateCuboid(name);
        }
    }
    
    private void updateCuboid(String name) {
        System.out.println("Changing " + name + " to:");
        System.out.println("A:" + Chunk.getMin(minX) + "," + Chunk.getMin(minZ)
                        + " B:" + Chunk.getMax(maxX) + "," + Chunk.getMax(maxZ));
    }
    
    public void contract(ChunkMap map, Chunk chunk, String name) {
        if (!area.contains(chunk)) {
            throw new ModifyException("The town doesn't contain the chunk");
        }
        area.remove(chunk);
        updateShape(name);
        map.removeTown(chunk);
    }
}
