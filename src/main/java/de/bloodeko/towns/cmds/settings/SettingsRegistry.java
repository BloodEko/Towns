package de.bloodeko.towns.cmds.settings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;

import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.flags.StateFlag.State;

import de.bloodeko.towns.town.Town;
import de.bloodeko.towns.util.Messages;

public class SettingsRegistry {
    public static final DamageAnimalsSetting DAMAGE_ANIMALS = new DamageAnimalsSetting(
      Flags.DAMAGE_ANIMALS, "damageAnimals", Messages.get("settings.damageAnimals"), 1, 3000, State.DENY);
    
    public static final PvpSetting PVP = new PvpSetting(
      Flags.PVP, "pvp", Messages.get("settings.pvp"), 1, 1000, State.DENY);
    
    public static final WarpSetting WARP = new WarpSetting(
      null, "warp", Messages.get("settings.warp"), 2, 4000, null);
    

    private Map<String, TownSetting> byInternal;
    private Map<String, TownSetting> byDisplay;
    
    public SettingsRegistry() {
        this.byDisplay = new HashMap<>();
        this.byInternal = new HashMap<>();
    }

    public void register(TownSetting setting) {
        byInternal.put(setting.getId(), setting);
        byDisplay.put(setting.getDisplay(), setting);
    }

    public TownSetting fromDisplay(String key) {
        return byDisplay.get(key);
    }
    
    public TownSetting fromId(String key) {
        return byInternal.get(key);
    }
    
    /**
     * Returns values which can be bought for this town.
     */
    public List<TownSetting> getPossibleSettings(Town town) {
        List<TownSetting> list = new ArrayList<>();
        for (TownSetting setting : byDisplay.values()) {
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
            list.add(setting.getDisplay());
        }
        return list;
    }
    
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
