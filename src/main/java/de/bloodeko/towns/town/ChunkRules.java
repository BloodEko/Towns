package de.bloodeko.towns.town;

import de.bloodeko.towns.util.Chunk;
import de.bloodeko.towns.util.ModifyException;

/**
 * Defines the rules in which expansion of towns is allowed 
 * to ensure a solid shape.
 * 
 * TODO: check if cuboid shape is defined on town create, because there is no message in the console.
 *  Then test with a worldguard region which can be resized. Listeners can be dropped by that.
 * 1. 1:2 ratio
 * 2. 60% of sides² is claimed.
 * 3. for a town <100, 2 chunkfaces are required.
 *    when it increases a side, only 1 chunkface.
 * 4. a range is calculated by the size.
 *    70% of chunks in range must be from the town.
 */
public class ChunkRules {
    private final double AREA_PERCENTAGE = 0.55;
    private final int NEAR_RANGE = 2;
    private final int MIN_COUNT_NEAR = 6;
    private final int SKIP_CHECKS = 12;
    private final int SIDES_RATIO = 2;
    
    /**
     * Checks whether the expansion results in a valid town shape.
     * Throws an exception else. Skips the check for a size of 0.
     */
    public void checkExpand(ChunkMap map, Town town, TownArea area, Chunk chunk) {
        if (area.getSize() == 0) {
            return;
        }
        UpdatedSides side = UpdatedSides.of(area, chunk);
        checkSidesRatio(side, area);
        if (area.getSize() > SKIP_CHECKS) {
            checkPercentage(side, area);
            checkNear(map, town, chunk);
        }
    }
    
    /**
     * Ensures with the new claimed chunk taken into account,
     * the sides of the town would remain in an 1:2 ratio.
     */
    private void checkSidesRatio(UpdatedSides side, TownArea area) {
        int lengthX = side.maxX - side.minX + 1;
        int lengthZ = side.maxX - side.minZ + 1;
        double minSide = Math.min(lengthX, lengthZ);
        double maxSide = Math.max(lengthX, lengthZ);
        if (maxSide / minSide > SIDES_RATIO) {
            throw new ModifyException("Sides must be in a " + SIDES_RATIO + ":1 ratio.");
        }
    }
    
    /**
     * Ensures that X percent of sides² without the new chunk taken into account 
     * are claimed. Only checks for this, when the sides UpdatedSide increased. 
     * If sides are blocked by other towns, giving up claims is the only way.
     */
    private void checkPercentage(UpdatedSides side, TownArea townArea) {
        if (!side.changed) {
            return;
        }
        double areaCur = townArea.getSize();
        double areaMax = townArea.getMaxSize();
        double percentage = areaCur/areaMax;
        if (percentage < AREA_PERCENTAGE) {
            throw new ModifyException("Not enough area filled. " + areaCur + "/" + areaMax 
              + " are only " + (percentage*100) + "/" + AREA_PERCENTAGE + "%.");
        }
    }
    
    /**
     * Ensures that in a range of X for the newly claimed 
     * chunk are at least X chunks of the town.
     */
    private void checkNear(ChunkMap map, Town town, Chunk chunk) {
        int count = 0;
        for (Chunk ch : getNearChunks(chunk, NEAR_RANGE)) {
            if (map.query(ch) == town) count++;
        }
        if (count < MIN_COUNT_NEAR) {
            throw new ModifyException("Not enough chunks near. " + count + "/" + MIN_COUNT_NEAR);
        }
    }
    
    /**
     * Gets the chunks around in a square range.
     */
    private Chunk[] getNearChunks(Chunk chunk, int range) {
        Chunk[] chunks = new Chunk[range * range];
        int startX = chunk.x - range;
        int startZ = chunk.z - range;
        int endX = chunk.x + range + 1;
        int endZ = chunk.z + range + 1;
        int i = 0;
        for (int x = startX; x < endX; x++) {
            for (int z = startZ; z < endZ; z++) {
                chunks[i++] = Chunk.of(x, z);
            }
        }
        return chunks;
    }
    
    /*
    private void checkChunkFaces(UpdatedSides side, ChunkMap map, Town town, Chunk chunk) {
        if (town.getArea().getSize() > 100) {
            return;
        }
        int count = 0;
        for (Chunk face : getChunkFaces(chunk)) {
            if (map.query(face) == town) {
                count++;
            }
        }
        if (count < 2) {
            if (!side.changed) {
                throw new ModifyException("If you don't expand, 2 chunkfaces are required.");
            }
        }
    }
    
    private Chunk[] getChunkFaces(Chunk chunk) {
        Chunk[] faces = new Chunk[4];
        faces[0] = chunk.add(Yaw.NORTH);
        faces[1] = chunk.add(Yaw.SOUTH);
        faces[2] = chunk.add(Yaw.EAST);
        faces[3] = chunk.add(Yaw.WEST);
        return faces;
    }
    */
    
    public static class UpdatedSides {
        public int minX;
        public int maxX;
        public int minZ;
        public int maxZ;
        public boolean changed;
        
        public UpdatedSides(int minX, int maxX, int minZ, int maxZ) {
            this.minX = minX;
            this.maxX = maxX;
            this.minZ = minZ;
            this.maxZ = maxZ;
        }
        
        private void update(Chunk chunk) {
            if (chunk.x < minX) {
                minX = chunk.x;
                changed = true;
            }
            if (chunk.x > maxX) {
                maxX = chunk.x;
                changed = true;
            }
            if (chunk.z < minZ) {
                minZ = chunk.z;
                changed = true;
            }
            if (chunk.z > maxZ) {
                maxZ = chunk.z;
                changed = true;
            }
        }

        public static UpdatedSides of(int minX, int maxX, int minZ, int maxZ, Chunk chunk) {
            UpdatedSides sides = new UpdatedSides(minX, maxX, minZ, maxZ);
            sides.update(chunk);
            return sides;
        }

        public static UpdatedSides of(TownArea area, Chunk chunk) {
            return UpdatedSides.of(area.getMinX(), area.getMaxX(), area.getMinZ(), area.getMaxZ(), chunk);
        }
    }
    
}
