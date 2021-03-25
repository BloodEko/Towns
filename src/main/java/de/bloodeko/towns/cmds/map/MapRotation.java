package de.bloodeko.towns.cmds.map;

import de.bloodeko.towns.util.Chunk;
import de.bloodeko.towns.util.Yaw;

/**
 * Value to hold the directions for going up/down/right/left for 
 * a given perspective and a rotation to move 2D vectors.
 */
public class MapRotation {
    public static final Move2D ROTATE_0 = (chunk, x, z) -> chunk.add(x, z);
    public static final Move2D ROTATE_90 = (chunk, x, z) -> chunk.add(-z, x);
    public static final Move2D ROTATE_180 = (chunk, x, z) -> chunk.add(-x, -z);
    public static final Move2D ROTATE_270 = (chunk, x, z) -> chunk.add(z, -x);
    
    public static final MapRotation NORTH = new MapRotation(Yaw.NORTH, Yaw.SOUTH, Yaw.WEST, Yaw.EAST, ROTATE_0);
    public static final MapRotation SOUTH = new MapRotation(Yaw.SOUTH, Yaw.NORTH, Yaw.EAST, Yaw.WEST, ROTATE_180);
    public static final MapRotation WEST = new MapRotation(Yaw.WEST, Yaw.EAST, Yaw.SOUTH, Yaw.NORTH, ROTATE_270);
    public static final MapRotation EAST = new MapRotation(Yaw.EAST, Yaw.WEST, Yaw.NORTH, Yaw.SOUTH, ROTATE_90);
    
    public final Yaw up;
    public final Yaw down;
    public final Yaw right;
    public final Yaw left;
    public final Move2D roation;
    
    public MapRotation(Yaw up, Yaw down, Yaw right, Yaw left, Move2D rotation) {
        this.up = up;
        this.down = down;
        this.right = right;
        this.left = left;
        this.roation = rotation;
    }
    
    /**
     * Calculates the offset chunk, and moves x and z from it.
     * Uses the direction to apply a rotation.
     */
    public Chunk rotateChunk(Chunk chunk, int x, int z) {
        return roation.move(chunk, x, z);
    }
    
    /**
     * Defines an operation to move from a Chunk 
     * based on a 2D vector.
     */
    public static interface Move2D {
        public Chunk move(Chunk chunk, int x, int z);
    }
}
