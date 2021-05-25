package de.bloodeko.towns.core.townarea;

import java.util.Arrays;

import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;

import de.bloodeko.towns.Services;
import de.bloodeko.towns.util.Chunk;
import de.bloodeko.towns.util.Messages;

/**
 * Represents a view of an town area for WorldGuard, to allow 
 * WorldGuard to act on the shape of a chunk based town area.
 */
public class TownRegion extends ProtectedCuboidRegion {
    private final Integer town;

    TownRegion(Integer town) {
        super("town_" + town, true, BlockVector3.ZERO, BlockVector3.ZERO);
        this.town = town;
    }
    
    @Override
    public boolean contains(BlockVector3 pt) {
        ChunkArea area = Services.chunkservice().getArea(town);
        if (area == null) {
            return false;
        }
        return area.contains(Chunk.fromCoords(pt.getBlockX(), pt.getBlockZ()));
    }
    
    /**
     * Sets the minimum and maximum point for the bounding box.
     * The region needs to be re-added to take effect.
     */
    void setBoundingBox(ChunkSides sides) {
        BlockVector3 min = Chunk.getMinPos(sides.minX(), sides.minZ());
        BlockVector3 max = Chunk.getMaxPos(sides.maxX(), sides.maxZ());
        setMinMaxPoints(Arrays.asList(min, max));
    }

    /**
     * Sets entry and exit messages for the region.
     */
    @SuppressWarnings("deprecation")
    public void setFlagNames(String name) {
        getFlags().put(Flags.GREET_MESSAGE, Messages.get("town.townsettings.enterRegion", name));
        getFlags().put(Flags.FAREWELL_MESSAGE, Messages.get("town.townsettings.leaveRegion"));
    }
}
