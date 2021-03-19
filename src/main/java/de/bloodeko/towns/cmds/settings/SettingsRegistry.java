package de.bloodeko.towns.cmds.settings;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;

import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.flags.StateFlag.State;

import de.bloodeko.towns.town.Town;

public class SettingsRegistry {
    public static final DamageAnimalsSetting DAMAGE_ANIMALS = new DamageAnimalsSetting(Flags.DAMAGE_ANIMALS, "DamageAnimals", 1, 3000, State.DENY);
    public static final PvpSetting PVP = new PvpSetting(Flags.PVP, "PvP", 1, 1000, State.DENY);
    public static final WarpSetting WARP = new WarpSetting(null, "Warppoint", 2, 4000, null);
    
    private Map<String, TownSetting> settings;
    
    public SettingsRegistry(Map<String, TownSetting> settings) {
        this.settings = settings;
    }

    public void register(TownSetting setting) {
        settings.put(setting.getName(), setting);
    }
    
    public Map<String, TownSetting> settings() {
        return settings;
    }

    public TownSetting get(String key) {
        return settings.get(key);
    }
    
    /**
     * Returns values which can be bought for this town.
     */
    public List<TownSetting> getPossibleSettings(Town town) {
        List<TownSetting> list = new ArrayList<>();
        for (TownSetting setting : settings.values()) {
            if (setting.accepts(town) && !town.getSettings().hasSetting(setting)) {
                list.add(setting);
            }
        }
        return list;
    }
    
    /**
     * Returns the names of values which can be bought for 
     * this town.
     */
    public List<String> getPossibleNames(Town town) {
        List<String> list = new ArrayList<>();
        for (TownSetting setting : getPossibleSettings(town)) {
            list.add(setting.getName());
        }
        return list;
    }
    
    public static class DamageAnimalsSetting extends TownSetting {

        public DamageAnimalsSetting(Flag<?> flag, String name, int minStage, int price, Object defaultValue) {
            super(flag, name, minStage, price, defaultValue);
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

        public PvpSetting(Flag<?> flag, String name, int minStage, int price, Object defaultValue) {
            super(flag, name, minStage, price, defaultValue);
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

        public WarpSetting(Flag<?> flag, String name, int minStage, int price, Object defaultValue) {
            super(flag, name, minStage, price, defaultValue);
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
