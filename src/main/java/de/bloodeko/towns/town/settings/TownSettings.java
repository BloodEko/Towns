package de.bloodeko.towns.town.settings;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.sk89q.worldguard.protection.flags.Flags;

import static de.bloodeko.towns.util.Serialization.asInt;
import static de.bloodeko.towns.util.Serialization.asRoot;
import static de.bloodeko.towns.util.Serialization.asString;

import de.bloodeko.towns.town.area.TownArea.ChunkRegion;
import de.bloodeko.towns.util.Messages;
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
    
    public int getStage() {
        return stage;
    }
    
    public void setStage(int stage) {
        if (stage < 0) {
            throw new ModifyException("town.townsettings.negativeStage");
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

    public boolean hasSetting(TownSetting setting) {
        return flags.containsKey(setting);
    }

    public void addExtension(TownSetting setting) {
        if (flags.containsKey(setting)) {
            throw new ModifyException("town.townsettings.alreadyBought");
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
            throw new ModifyException("town.townsettings.notBought");
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
        //region.setFlag(Flags.GREET_MESSAGE, "&b~ Stadt " + name);
        //region.setFlag(Flags.FAREWELL_MESSAGE, "&2~ Wildnis &4(PvP)");
        region.setFlag(Flags.GREET_MESSAGE, Messages.get("town.townsettings.enterRegion", name));
        region.setFlag(Flags.FAREWELL_MESSAGE, Messages.get("town.townsettings.leaveRegion"));
        
        for (Entry<TownSetting, Object> entry : flags.entrySet()) {
            if (entry.getKey().getFlag() != null && entry.getValue() != null) {
                region.getFlags().put(entry.getKey().getFlag(), entry.getValue());
            }
        }
    }

    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("stage", stage);
        Map<String, Object> settings = new HashMap<>();
        for (Entry<TownSetting, Object> entry : flags.entrySet()) {
            settings.put(entry.getKey().getId(), entry.getKey().serialize(entry.getValue()));
        }
        map.put("settings", settings);
        return map;
    }
    
    public static TownSettings deserialize(Map<String, Object> root, ChunkRegion region, SettingsRegistry registry) {
        Map<TownSetting, Object> map = new HashMap<>();
        for (Entry<String, Object> entry : asRoot(root.get("settings")).entrySet()) {
            TownSetting setting = registry.fromId(entry.getKey());
            Object value = setting.deserialize(entry.getValue());
            map.put(setting, value);
        }
        TownSettings settings = new TownSettings(region, asString(root.get("name")),
          asInt(root.get("stage")), map);
        settings.updateFlags();
        return settings;
    }
}
