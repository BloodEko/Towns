package de.bloodeko.towns.town.settings;

import static de.bloodeko.towns.util.Util.trimDouble;

import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import com.sk89q.worldguard.protection.flags.StateFlag.State;

public abstract class Setting {
    public abstract String getId();
    //public abstract Flag<?> getFlag();
    
    public abstract Object read(Map<Object, Object> map);
    public abstract void set(Map<Object, Object> map, Object obj);
    public abstract void init(Map<Object, Object> map);
    
    public abstract Object serialize(Map<Object, Object> map);
    public abstract void deserialize(Map<Object, Object> map, Object obj);
    
    /**
     * Converts a State to a String.
     * The result will be "true" or "false".
     */
    public String serializeState(Object obj) {
        if (obj instanceof Boolean) {
            return (boolean) obj ? "allow" : "deny";
        }
        return obj == State.ALLOW ? "allow" : "deny";
    }
    
    /**
     * Converts String back to a State.
     * Reads for the format "true" or "false".
     */
    public State deserializeState(Object obj) {
        return "allow".equals(obj) ? State.ALLOW : State.DENY;
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
