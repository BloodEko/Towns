package de.bloodeko.towns.town.settings;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.Flags;

import de.bloodeko.towns.town.area.ChunkRegion;
import de.bloodeko.towns.util.Messages;
import de.bloodeko.towns.util.ModifyException;
import de.bloodeko.towns.util.Node;
import de.bloodeko.towns.util.Node.Pair;

public class TownSettings {
    private Set<Setting> settings;
    private Map<Object, Object> flags;
    
    public TownSettings(Set<Setting> settings, Map<Object, Object> flags) {
        this.settings = settings;
        this.flags = flags;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static TownSettings fromFlagMap(Set<Setting> settings, Map<Flag<?>, Object> flags) {
        return new TownSettings(settings, (Map<Object, Object>) (Map) flags);
    }
    
    public Set<Setting> settings() {
        return settings;
    }

    public String getName() {
        return flags.get(Settings.NAME).toString();
    }
    
    public int getStage() {
        return (int) flags.get(Settings.STAGE);
    }
    
    public Map<Object, Object> getFlags() {
        return flags;
    }
    
    public Object get(Setting setting) {
        return setting.read(flags);
    }

    public boolean has(Setting setting) {
        return settings.contains(setting);
    }
    
    public void addSetting(Setting setting) {
        if (settings.contains(setting)) {
            throw new ModifyException("town.townsettings.alreadyBought");
        }
        settings.add(setting);
        setting.init(flags);
    }
    
    public void setSetting(Setting setting, Object value) {
        if (!settings.contains(setting)) {
            throw new ModifyException("town.townsettings.notBought");
        }
        setting.set(flags, value);
    }

    /**
     * Writes the extensions values to the region values.
     * @return 
     */
    @SuppressWarnings("deprecation")
    public TownSettings updateFlags() {
        flags.put(Flags.GREET_MESSAGE, Messages.get("town.townsettings.enterRegion", getName()));
        flags.put(Flags.FAREWELL_MESSAGE, Messages.get("town.townsettings.leaveRegion"));
        return this;
    }
    
    /**
     * Creates a Node with keys that represent 
     * the settings of a town.
     */
    public Node serialize() {
        Node node = new Node();
        for (Setting setting : settings) {
            node.set(setting.getId(), setting.serialize(flags));
        }
        node.set(Settings.NAME.getId(), Settings.NAME.serialize(flags));
        node.set(Settings.STAGE.getId(), Settings.STAGE.serialize(flags));
        return node;
    }
    
    /**
     * Creates TownSettings and writes them to a region from a Node 
     * with keys that represent the settings of a town.
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static TownSettings deserialize(Node settings, ChunkRegion region, SettingsRegistry registry) {
        Map<Object, Object> map = (Map) region.getFlags();
        Set<Setting> set = new HashSet<>();
        
        for (Pair pair : settings.entries()) {
            AdvancedSetting advanced = registry.fromId(pair.key);
            Setting setting = advanced.settingKey;
            
            set.add(setting);
            setting.deserialize(map, pair.value);
        }
        return new TownSettings(set, map).updateFlags();
    }

}
