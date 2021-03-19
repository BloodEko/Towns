package de.bloodeko.towns.town.settings;

import static de.bloodeko.towns.util.Serialization.asInt;
import static de.bloodeko.towns.util.Serialization.asRoot;
import static de.bloodeko.towns.util.Serialization.asString;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.sk89q.worldguard.protection.flags.Flags;

import de.bloodeko.towns.town.area.TownArea.ChunkRegion;
import de.bloodeko.towns.util.Messages;
import de.bloodeko.towns.util.ModifyException;

public class TownSettings {
    private ChunkRegion region;
    private String name;
    private int stage;
    private Map<Setting, Object> extensions;
    
    public TownSettings(ChunkRegion region, String name, int stage, Map<Setting, Object> extensions) {
        this.region = region;
        this.name = name;
        this.stage = stage;
        this.extensions = extensions;
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

    public void addExtension(Setting setting) {
        if (extensions.containsKey(setting)) {
            throw new ModifyException("town.townsettings.alreadyBought");
        }
        extensions.put(setting, setting.getDefault());
        if (setting.getFlag() != null) {
            region.getFlags().put(setting.getFlag(), setting.getDefault());
        }
    }
    
    /**
     * Gets the value for a generic setting.
     */
    public Object readSetting(Setting setting) {
        return extensions.get(setting);
    }

    public boolean hasSetting(Setting setting) {
        return extensions.containsKey(setting);
    }
    
    public Map<Setting, Object> getSettings() {
        return extensions;
    }
    
    /**
     * Sets a genetic setting and updates the wg region.
     * Throws an exception otherwise.
     */
    public void writeSetting(Setting setting, Object value) {
        if (!extensions.containsKey(setting)) {
            throw new ModifyException("town.townsettings.notBought");
        }
        extensions.put(setting, value);
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
        region.setFlag(Flags.GREET_MESSAGE, Messages.get("town.townsettings.enterRegion", name));
        region.setFlag(Flags.FAREWELL_MESSAGE, Messages.get("town.townsettings.leaveRegion"));
        
        for (Entry<Setting, Object> entry : extensions.entrySet()) {
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
        for (Entry<Setting, Object> entry : extensions.entrySet()) {
            settings.put(entry.getKey().getId(), entry.getKey().serialize(entry.getValue()));
        }
        map.put("settings", settings);
        return map;
    }
    
    public static TownSettings deserialize(Map<String, Object> root, ChunkRegion region, SettingsRegistry registry) {
        Map<Setting, Object> map = new HashMap<>();
        for (Entry<String, Object> entry : asRoot(root.get("settings")).entrySet()) {
            Setting setting = registry.fromId(entry.getKey()).value;
            Object value = setting.deserialize(entry.getValue());
            map.put(setting, value);
        }
        TownSettings settings = new TownSettings(region, asString(root.get("name")),
          asInt(root.get("stage")), map);
        settings.updateFlags();
        return settings;
    }
}
