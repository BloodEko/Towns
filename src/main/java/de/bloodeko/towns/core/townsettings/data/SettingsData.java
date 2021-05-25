package de.bloodeko.towns.core.townsettings.data;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import de.bloodeko.towns.Services;
import de.bloodeko.towns.core.townarea.TownRegion;
import de.bloodeko.towns.core.townsettings.SettingsService;
import de.bloodeko.towns.core.townsettings.TownSettings;
import de.bloodeko.towns.core.townsettings.legacy.AdvancedSetting;
import de.bloodeko.towns.core.townsettings.legacy.Setting;
import de.bloodeko.towns.core.townsettings.legacy.Settings;
import de.bloodeko.towns.core.townsettings.legacy.SettingsRegistry;
import de.bloodeko.towns.util.Node;
import de.bloodeko.towns.util.Node.Pair;

public class SettingsData {

    /**
     * Returns a SettingsService from a Node, with subsections,
     * where under each key, all the settings are stored.
     * Requires the ChunkService and NameService to be loaded.
     */
    public static SettingsService read(Node node) {
        Map<Integer, TownSettings> data = new HashMap<>();
        SettingsRegistry registry = Settings.newRegistry();
        
        for (Pair pair : node.entries()) {
            Integer id = Integer.valueOf(pair.key);
            TownRegion region = Services.chunkservice().getRegion(id);
            TownSettings settings = readSettings((Node) pair.value, region, registry);
            data.put(id, settings);
        }
        
        for (Integer id : Services.chunkservice().getAreas()) {
            data.putIfAbsent(id, new TownSettings(Services.chunkservice().getRegion(id)));
        }
        
        for (Integer id : Services.chunkservice().getAreas()) {
            String name = Services.nameservice().getName(id);
            Services.chunkservice().getRegion(id).setFlagNames(name);
        }
        
        return new SettingsService(data, registry);
    }
    
    /**
     * Returns a Node with subsections, with the keys as 
     * a town ID and the value a Node with TownSettings data.
     */
    public static Node write(SettingsService service) {
        Node node = new Node();
        for (Entry<Integer, TownSettings> entry : service.getView().entrySet()) {
            node.set(entry.getKey().toString(), writeSettings(entry.getValue()));
        }
        return node;
    }
    
    /**
     * Returns TownSettings from a Node with settings as keys. Uses the
     * registry to write flags to the region for available settings.
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static TownSettings readSettings(Node settings, TownRegion region, SettingsRegistry registry) {
        Map<Object, Object> map = (Map) region.getFlags();
        Set<Setting> set = new HashSet<>();
        
        for (Pair pair : settings.entries()) {
            AdvancedSetting advanced = registry.fromId(pair.key);
            Setting setting = advanced.settingKey;
            set.add(setting);
            setting.deserialize(map, pair.value);
        }
        
        return new TownSettings(set, map);
    }
    
    /**
     * Returns a Node with various keys as settings.
     */
    public static Node writeSettings(TownSettings settings) {
        Node node = new Node();
        for (Setting setting : settings.settings()) {
            node.set(setting.getId(), setting.serialize(settings.getFlags()));
        }
        return node;
    }
}
