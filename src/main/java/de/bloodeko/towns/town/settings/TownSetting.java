package de.bloodeko.towns.town.settings;

import static de.bloodeko.towns.util.Util.trimDouble;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.StateFlag.State;

import de.bloodeko.towns.town.Town;

public abstract class TownSetting {
    private Flag<?> flag;
    private String id;
    private String display;
    private int minStage;
    private int price;
    private Object defaultValue;
    
    public TownSetting(Flag<?> flag, String id, String display, int minStage, int price, Object defaultValue) {
        this.flag = flag;
        this.id = id;
        this.display = display;
        this.minStage = minStage;
        this.price = price;
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
     * Returns the display name which is also
     * used as identifier in the UI.
     */
    public String getDisplay() {
        return display;
    }
    
    /**
     * Gets the wg flag for this setting or null.
     */
    public Flag<?> getFlag() {
        return flag;
    }
    
    /**
     * Returns true if the setting can be bought for this town.
     * Per default only checks on the stage.
     */
    public boolean accepts(Town town) {
        return matchesStage(town.getSettings().getStage());
    }
    
    /**
     * Returns true if the town has this stage at least.
     */
    public boolean matchesStage(int stage) {
        return stage >= minStage;
    }
    
    /**
     * Gets the price to buy this setting.
     */
    public int getPrice() {
        return price;
    }
    
    /**
     * Gets the value which will be used on first initialization
     * of the setting.
     */
    public Object getDefault() {
        return defaultValue;
    }
    
    /**
     * Formats the value be used for an display. Writes "null" for no value.
     * Otherwise uses the default serialization implementation.
     */
    public String display(Object obj) {
        if (obj == null) return "null";
        return serialize(obj).toString();
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
