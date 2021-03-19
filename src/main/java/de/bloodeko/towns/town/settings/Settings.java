package de.bloodeko.towns.town.settings;

import org.bukkit.Location;

import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.flags.StateFlag.State;

import de.bloodeko.towns.util.Messages;

public class Settings {
    public static final DamageAnimalsSetting DAMAGE_ANIMALS = new DamageAnimalsSetting(
      Flags.DAMAGE_ANIMALS, "damageAnimals", Messages.get("settings.damageAnimals"), 1, 3000, State.DENY);
          
    public static final PvpSetting PVP = new PvpSetting(
      Flags.PVP, "pvp", Messages.get("settings.pvp"), 1, 1000, State.DENY);
          
    public static final WarpSetting WARP = new WarpSetting(
      null, "warp", Messages.get("settings.warp"), 2, 4000, null);
    
    public static class DamageAnimalsSetting extends TownSetting {

        public DamageAnimalsSetting(Flag<?> flag, String id, String name, int minStage, int price, Object defaultValue) {
            super(flag, id, name, minStage, price, defaultValue);
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
    
    public static class PvpSetting extends TownSetting {

        public PvpSetting(Flag<?> flag, String id, String name, int minStage, int price, Object defaultValue) {
            super(flag, id, name, minStage, price, defaultValue);
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
    
    public static class WarpSetting extends TownSetting {

        public WarpSetting(Flag<?> flag, String id, String name, int minStage, int price, Object defaultValue) {
            super(flag, id, name, minStage, price, defaultValue);
        }

        @Override
        public String serialize(Object obj) {
            return serializeLocation((Location) obj);
        }

        @Override
        public Location deserialize(Object obj) {
            return deserializeLocation((String) obj);
        }
    }
}
