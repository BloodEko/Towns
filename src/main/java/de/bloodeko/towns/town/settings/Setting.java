package de.bloodeko.towns.town.settings;

import static de.bloodeko.towns.util.Util.trimDouble;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.StateFlag.State;

public abstract class Setting {
    private Flag<?> flag;
    private String id;
    private Object defaultValue;
    
    public Setting(Flag<?> flag, String id, Object defaultValue) {
        this.flag = flag;
        this.id = id;
        this.defaultValue = defaultValue;
    }
    
    /**
     * Returns the internal name used for
     * identification in serialization.
     */
    public String getId() {
        return id;
    }
    
    /**
     * Gets the wg flag for this setting or null.
     */
    public Flag<?> getFlag() {
        return flag;
    }
    
    /**
     * Gets the value which will be used on first initialization
     * of the setting.
     */
    public Object getDefault() {
        return defaultValue;
    }
    
    public abstract Object serialize(Object obj);
    public abstract Object deserialize(Object obj);
    
    /**
     * Converts a State to a String.
     * The result will be "true" or "false".
     */
    public String serializeBoolean(Object obj) {
        return obj == State.ALLOW ? "true" : "false";
    }
    
    /**
     * Converts String back to a State.
     * Reads for the format "true" or "false".
     */
    public State deserializeBoolean(Object obj) {
        return "true".equals(obj) ? State.ALLOW : State.DENY;
    }
    
    /**
     * Converts from a Location to a String.
     * The format will be: 0,0,0,yaw,pitch,world
     */
    public String serializeLocation(Location loc) {
        return trimDouble(loc.getX()) + "," + trimDouble(loc.getY()) + "," 
          + trimDouble(loc.getZ()) + "," + trimDouble(loc.getYaw())  + "," 
          + trimDouble(loc.getPitch()) + "," + loc.getWorld().getName();
    }
    
    /**
     * Converts a String back to a Location.
     */
    public Location deserializeLocation(String loc) {
        String[] args = loc.split(",");
        return new Location(Bukkit.getWorld(args[5]), Double.valueOf(args[0]),
          Double.valueOf(args[1]), Double.valueOf(args[2]),
          Float.valueOf(args[3]), Float.valueOf(args[4]));
    }
}
