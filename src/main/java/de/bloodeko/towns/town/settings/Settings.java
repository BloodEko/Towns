package de.bloodeko.towns.town.settings;

import java.util.HashMap;

import org.bukkit.Location;

import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.flags.StateFlag.State;

import de.bloodeko.towns.town.settings.plots.PlotHandler;
import de.bloodeko.towns.util.Node;

public class Settings {
    public static final DamageAnimalsSetting DAMAGE_ANIMALS = new DamageAnimalsSetting(Flags.DAMAGE_ANIMALS, "damageAnimals", State.DENY);
    public static final PvpSetting PVP = new PvpSetting(Flags.PVP, "pvp", State.DENY);
    public static final WarpSetting WARP = new WarpSetting(null, "warp", null);
    public static final PlotSetting PLOTS = new PlotSetting(null, "plots", null);
    
    
    public static class PlotSetting extends Setting {

        public PlotSetting(Flag<?> flag, String id, Object defaultValue) {
            super(flag, id, defaultValue);
        }
        
        @Override
        public Object getDefault() {
            return new PlotHandler(new HashMap<>(), 0);
        }

        @Override
        public Object serialize(Object obj) {
            return ((PlotHandler) obj).serialize();
        }

        @Override
        public Object deserialize(Object obj) {
            return PlotHandler.deserialize((Node) obj);
        }
    }
    
    public static class DamageAnimalsSetting extends Setting {

        public DamageAnimalsSetting(Flag<?> flag, String id, Object defaultValue) {
            super(flag, id, defaultValue);
        }

        @Override
        public String serialize(Object obj) {
            return serializeBoolean(obj);
        }

        @Override
        public State deserialize(Object obj) {
            return deserializeBoolean(obj);
        }
    }
    
    public static class PvpSetting extends Setting {

        public PvpSetting(Flag<?> flag, String id, Object defaultValue) {
            super(flag, id, defaultValue);
        }

        @Override
        public String serialize(Object obj) {
            return serializeBoolean(obj);
        }

        @Override
        public State deserialize(Object obj) {
            return deserializeBoolean(obj);
        }
    }
    
    public static class WarpSetting extends Setting {

        public WarpSetting(Flag<?> flag, String id, Object defaultValue) {
            super(flag, id, defaultValue);
        }

        @Override
        public String serialize(Object obj) {
            if (obj == null) return "x";
            return serializeLocation((Location) obj);
        }

        @Override
        public Location deserialize(Object obj) {
            if (obj.equals("x")) return null;
            return deserializeLocation((String) obj);
        }
    }
}
