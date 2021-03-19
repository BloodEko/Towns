package de.bloodeko.towns.session1;


public enum Yaw {
    NORTH("North", 180, 0, -1), SOUTH("South", 0, 0, 1),
    WEST("West", 90, -1, 0), EAST("East", 270, 1, 0);
    
    public static final Yaw[] DIRECTIONS = {NORTH, SOUTH, WEST, EAST};
    
    private String name;
    private float yaw;
    private int x;
    private int z;
    
    private Yaw(String name, float yaw, int x, int z) {
        this.name = name;
        this.yaw = yaw;
        this.x = x;
        this.z = z;
    }
    
    /**
     * Returns a value in the range 
     * of 0-360 for this direction.
     */
    public float getYaw() {
        return yaw;
    }
    
    /**
     * Returns the direction on the X-Axis.
     */
    public int getX() {
        return x;
    }
    
    /**
     * Returns the direction on the Z-Axis.
     */
    public int getZ() {
        return z;
    }
    
    @Override
    public String toString() {
        return name;
    }
    
    /**
     * Returns the Direction for any
     * positive, negative or raw values.
     */
    public static Yaw getDirection(float yaw) {
        yaw = (yaw % 360 + 360) % 360; //normalize
        if (yaw >= 315 || yaw < 45) {
            return SOUTH;
        } else if (yaw < 135) {
            return WEST;
        } else if (yaw < 225) {
            return NORTH;
        }
        return EAST;
    }
    
    public static Yaw getReverseDirection(float yaw) {
        return getDirection(yaw + 180);
    }
}