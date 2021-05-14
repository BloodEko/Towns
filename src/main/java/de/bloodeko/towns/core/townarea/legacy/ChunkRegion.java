package de.bloodeko.towns.core.townarea.legacy;

import java.util.Arrays;
import java.util.Set;

import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldguard.commands.task.RegionAdder;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;

import de.bloodeko.towns.util.Chunk;
import de.bloodeko.towns.util.ModifyException;

/**
 * WorldGuard entity which represents a CuboidRegion and
 * under that maps to chunks. Can resize and update itself.
 */
public class ChunkRegion extends ProtectedCuboidRegion {
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
        return area.contains(Chunk.fromCoords(pt.getBlockX(), pt.getBlockZ()));
    }
    
    /**
     * Resizes the bounding box of the chunk shape.
     * Queries the RegionManager to apply the update.
     */
    public void resize(BlockVector3 min, BlockVector3 max) {
        setMinMaxPoints(Arrays.asList(min, max));
        RegionAdder adder = new RegionAdder(manager, this);
        try {
            adder.call();
        } catch (Exception ex) {
            throw new ModifyException("Could not resize region.", ex);
        }
    }
}
