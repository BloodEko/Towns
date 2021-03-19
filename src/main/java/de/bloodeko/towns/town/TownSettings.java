package de.bloodeko.towns.town;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.sk89q.worldguard.protection.flags.Flags;

import de.bloodeko.towns.cmds.settings.TownSetting;
import de.bloodeko.towns.town.TownArea.ChunkRegion;
import de.bloodeko.towns.util.ModifyException;

public class TownSettings {
    private ChunkRegion region;
    private String name;
    private int stage;
    private Map<TownSetting, Object> flags;
    
    public TownSettings(ChunkRegion region, String name, int stage, Map<TownSetting, Object> extensions) {
        this.region = region;
        this.name = name;
        this.stage = stage;
        this.flags = extensions;
    }
    
    public int getState() {
        return stage;
    }
    
    public void setStage(int stage) {
        if (stage < 0) {
            throw new ModifyException("Stage must be positive.");
        }
        this.stage = stage;
    }

    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public Map<TownSetting, Object> getSettings() {
        return flags;
    }
    
    public Set<TownSetting> getBoughtSettings() {
        return flags.keySet();
    }

    public void addExtension(TownSetting setting) {
        if (flags.containsKey(setting)) {
            throw new ModifyException("This setting was already bought.");
        }
        flags.put(setting, setting.getDefault());
        if (setting.getFlag() != null) {
            region.getFlags().put(setting.getFlag(), setting.getDefault());
        }
    }
    
    /**
     * Gets the value for a generic setting.
     */
    public Object readSetting(TownSetting setting) {
        return flags.get(setting);
    }
    
    /**
     * Sets a genetic setting and updates the wg region.
     * Throws an exception otherwise.
     */
    public void writeSetting(TownSetting setting, Object value) {
        if (!flags.containsKey(setting)) {
            throw new ModifyException("The town doesn't have this setting.");
        }
        flags.put(setting, value);
        if (setting.getFlag() == null) {
            return;
        }
        if (value == null) {
            region.getFlags().remove(setting.getFlag());
        } else {
            region.getFlags().put(setting.getFlag(), value);
        }
    }

    /**
     * Writes the extensions values to the region values.
     */
    @SuppressWarnings("deprecation")
    public void updateFlags() {
        region.setFlag(Flags.GREET_MESSAGE, "&b~ Stadt " + name);
        region.setFlag(Flags.FAREWELL_MESSAGE, "&2~ Wildnis &4(PvP)");
        
        for (Entry<TownSetting, Object> entry : flags.entrySet()) {
            if (entry.getKey().getFlag() != null && entry.getValue() != null) {
                region.getFlags().put(entry.getKey().getFlag(), entry.getValue());
            }
        }
    }
    
    
    /**
     * Get Data for serialization.
     */
    public TownSettingsData getData() {
        return new TownSettingsData(name, stage, flags);
    }
    
    public static class TownSettingsData {
        public String name;
        public Map<TownSetting, Object> flags;
        public int stage;
        
        public TownSettingsData(String name, int stage, Map<TownSetting, Object> flags) {
            this.name = name;
            this.stage = stage;
            this.flags = flags;
        }
    }
}
