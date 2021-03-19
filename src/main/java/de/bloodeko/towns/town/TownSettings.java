package de.bloodeko.towns.town;

import java.util.Map;
import java.util.Map.Entry;

import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.flags.StateFlag.State;

import de.bloodeko.towns.cmds.settings.Settings;
import de.bloodeko.towns.town.TownArea.ChunkRegion;
import de.bloodeko.towns.util.ModifyException;

public class TownSettings {
    private ChunkRegion region;
    private String name;
    private Map<Flag<?>, Object> extensions;
    
    public TownSettings(ChunkRegion region, String name, Map<Flag<?>, Object> extensions) {
        this.region = region;
        this.name = name;
        this.extensions = extensions;
    }

    public String getName() {
        return name;
    }
    
    public Map<Flag<?>, Object> getExtensions() {
        return extensions;
    }
    
    public void buySetting(String name) {
        
    }
    
    /**
     * Gets the value for this extension or null.
     */
    @SuppressWarnings("unchecked")
    public <T extends Flag<V>, V> V getSettingValue(T flag) {
        Object obj = extensions.get(flag);
        if (obj == null) {
            return null;
        }
        return (V) obj;
    }
    
    /**
     * Throws an exception if the town doesn't have the flag.
     * Sets the extension value and updates the wg region.
     */
    public <T extends Flag<V>, V> void updateSetting(T flag, V val) {
        if (!extensions.containsKey(flag)) {
            throw new ModifyException("The town doesn't have this setting.");
        }
        extensions.put(flag, val);
        region.setFlag(flag, val);
    }
    
    public void rename(TownRegistry registry, Town town, String newName) {
        registry.rename(town, newName);
        name = newName;
        updateFlags();
    }

    /**
     * Adds predefined extensions and flags tot he town.
     */
    @SuppressWarnings("deprecation")
    public void updateFlags() {
        region.setFlag(Flags.GREET_MESSAGE, "&b~ Stadt " + name);
        region.setFlag(Flags.FAREWELL_MESSAGE, "&2~ Wildnis &4(PvP)");
        
        extensions.put(Settings.WARP_FLAG, null);
        extensions.put(Flags.PVP, State.DENY);
        extensions.put(Flags.DAMAGE_ANIMALS, State.DENY);
        
        for (Entry<Flag<?>, Object> entry : extensions.entrySet()) {
            if (entry.getValue() != null) {
                region.getFlags().put(entry.getKey(), entry.getValue());
            }
        }
    }
    
    public TownSettingsData getData() {
        return new TownSettingsData(name, extensions);
    }
    
    public static class TownSettingsData {
        public String name;
        public Map<Flag<?>, Object> flags;
        
        public TownSettingsData(String name, Map<Flag<?>, Object> flags) {
            this.name = name;
            this.flags = flags;
        }
    }
}
