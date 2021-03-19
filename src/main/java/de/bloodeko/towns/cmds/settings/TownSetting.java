package de.bloodeko.towns.cmds.settings;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.StateFlag.State;

import de.bloodeko.towns.town.Town;

public abstract class TownSetting {
    private Flag<?> flag;
    private String name;
    private int minStage;
    private int price;
    private Object defaultValue;
    
    public TownSetting(Flag<?> flag, String name, int minStage, int price, Object defaultValue) {
        this.flag = flag;
        this.name = name;
        this.minStage = minStage;
        this.price = price;
        this.defaultValue = defaultValue;
    }
    
    public String getName() {
        return name;
    }
    
    public Flag<?> getFlag() {
        return flag;
    }
    
    public boolean accepts(Town town) {
        return matchesStage(town.getSettings().getState());
    }
    
    public boolean matchesStage(int stage) {
        return stage >= minStage;
    }
    
    public int getPrice() {
        return price;
    }
    
    public Object getDefault() {
        return defaultValue;
    }
    
    //abstract or not? future settings?
    public abstract String display(Object obj);
    public abstract Object serialize(Object obj);
    public abstract Object deserialize(Object obj);
    
    public String serializeBoolean(Object obj) {
        return obj == State.ALLOW ? "true" : "false";
    }
    
    public State deserializeBoolean(Object obj) {
        return "true".equals(obj) ? State.ALLOW : State.DENY;
    }
    
    // 0,0,0,yaw,pitch,world
    public String serializeLocation(Location loc) {
        return loc.getX() + "," + loc.getY() + "," + loc.getZ() + "," + loc.getYaw() 
          + "," + loc.getPitch() + "," + loc.getWorld().getName();
    }
    
    public Location deserializeLocation(String loc) {
        String[] args = loc.split(",");
        
        return new Location(Bukkit.getWorld(args[5]), Double.valueOf(args[0]), 
          Double.valueOf(args[1]), Double.valueOf(args[2]),
          Float.valueOf(args[3]), Float.valueOf(args[4]));
    }
}
