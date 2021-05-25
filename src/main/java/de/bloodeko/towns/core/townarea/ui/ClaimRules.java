package de.bloodeko.towns.core.townarea.ui;

import de.bloodeko.towns.core.townarea.ChunkArea;
import de.bloodeko.towns.core.townarea.ChunkService;
import de.bloodeko.towns.core.townarea.ChunkSides;
import de.bloodeko.towns.core.towns.Town;
import de.bloodeko.towns.util.Chunk;
import de.bloodeko.towns.util.ModifyException;

public class ClaimRules {
    private final double AREA_PERCENTAGE = 0.55;
    private final int NEAR_RANGE = 2;
    private final int MIN_COUNT_NEAR = 6;
    private final int MIN_CHUNKS = 9;
    private final int SIDES_RATIO = 2;
    
    /**
     * Checks whether the expansion results in a valid town shape.
     * Throws an exception else. Skips the check for a size of 0.
     */
    public void checkExpand(ChunkService map, Town town, ChunkArea area, Chunk chunk) {
        if (area.size() == 0) {
            return;
        }
        ChunkSides newSides = area.getSides();
        newSides.updateFor(chunk);
        checkSideRatio(newSides, area);
        
        if (area.size() > MIN_CHUNKS) {
            checkPercentage(newSides, area);
            checkNear(map, town, chunk);
        }
    }
    
    /**
     * Ensures with the new claimed chunk taken into account,
     * the sides of the town would remain in an 1:2 ratio.
     */
    private void checkSideRatio(ChunkSides newSides, ChunkArea area) {
        int lengthX = newSides.maxX() - newSides.minX() + 1;
        int lengthZ = newSides.maxZ() - newSides.minZ() + 1;
        double minSide = Math.min(lengthX, lengthZ);
        double maxSide = Math.max(lengthX, lengthZ);
        if (maxSide / minSide > SIDES_RATIO) {
            throw new ModifyException("town.chunkrules.wrongRatio", SIDES_RATIO);
        }
    }
    
    /**
     * Ensures that X percent of sides² without the new chunk taken into account 
     * are claimed. Only checks for this, when the sides UpdatedSide changed. 
     * If sides are blocked by other towns, giving up claims is the only way.
     */
    private void checkPercentage(ChunkSides newSides, ChunkArea townArea) {
        if (townArea.getSides().equals(newSides)) {
            return;
        }
        double areaCur = townArea.size();
        double areaMax = townArea.getMaxSize();
        double percentage = areaCur/areaMax;
        if (percentage < AREA_PERCENTAGE) {
            throw new ModifyException("town.chunkrules.notEnoughAreaFilled",
              areaCur, areaMax, (percentage*100), AREA_PERCENTAGE);
        }
    }
    
    /**
     * Ensures that in a range of X for the newly claimed 
     * chunk are at least X chunks of the town.
     */
    private void checkNear(ChunkService map, Town town, Chunk chunk) {
        int count = 0;
        for (Chunk ch : chunk.getNear(NEAR_RANGE)) {
            if (map.getTown(ch) == town) count++;
        }
        if (count < MIN_COUNT_NEAR) {
            throw new ModifyException("town.chunkrules.notEnoughChunksNear", 
              count, MIN_COUNT_NEAR);
        }
    }
}
