package de.bloodeko.towns.cmds.map;

import de.bloodeko.towns.util.Chunk;
import de.bloodeko.towns.util.Yaw;

/**
 * Maps each slot to a specific rotation.
 */
public class MapRotation {
    private static final Yaw TO_NORTH = Yaw.NORTH;
    private static final Yaw TO_SOUTH = Yaw.SOUTH;
    private static final Yaw TO_WEST = Yaw.WEST;
    private static final Yaw TO_EAST = Yaw.EAST;
    
    private static final int BASE_X_OFF = -4;
    private static final int BASE_Z_OFF = -2;
    
    public static final Rotation2D ROTATE_0 = (chunk, x, z) -> chunk.add(x, z);
    public static final Rotation2D ROTATE_90 = (chunk, x, z) -> chunk.add(-z, x);
    public static final Rotation2D ROTATE_180 = (chunk, x, z) -> chunk.add(-x, -z);
    public static final Rotation2D ROTATE_270 = (chunk, x, z) -> chunk.add(z, -x);
    
    public static final MapRotation NORTH = new MapRotation(TO_NORTH, TO_SOUTH, TO_WEST, TO_EAST, ROTATE_0);
    public static final MapRotation SOUTH = new MapRotation(TO_SOUTH, TO_NORTH, TO_EAST, TO_WEST, ROTATE_180);
    public static final MapRotation WEST = new MapRotation(TO_WEST, TO_EAST, TO_SOUTH, TO_NORTH, ROTATE_270);
    public static final MapRotation EAST = new MapRotation(TO_EAST, TO_WEST, TO_NORTH, TO_SOUTH, ROTATE_90);
    
    public final Yaw up;
    public final Yaw down;
    public final Yaw right;
    public final Yaw left;
    public final Rotation2D blockRot;
    
    public MapRotation(Yaw up, Yaw down, Yaw right, Yaw left, Rotation2D blockRot) {
        this.up = up;
        this.down = down;
        this.right = right;
        this.left = left;
        this.blockRot = blockRot;
    }
    
    public Chunk renderChunk(Chunk chunk, int zoom, int x, int z) {
        x = x * zoom + BASE_X_OFF * zoom;
        z = z * zoom + BASE_Z_OFF * zoom;
        return blockRot.rotate(chunk, x, z);
    }
    
    public static interface Rotation2D {
        public Chunk rotate(Chunk chunk, int x, int z);
    }
}
