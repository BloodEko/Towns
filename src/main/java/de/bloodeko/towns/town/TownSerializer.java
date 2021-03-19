package de.bloodeko.towns.town;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.bukkit.configuration.file.YamlConfiguration;

import de.bloodeko.towns.cmds.settings.TownSetting;
import de.bloodeko.towns.town.Town.TownData;

public class TownSerializer {
    private YamlConfiguration config;
    private TownData data;
    
    public TownSerializer(YamlConfiguration config, TownData data) {
        this.config = config;
        this.data = data;
    }
    
    public static YamlConfiguration serialize(TownRegistry registry) {
        YamlConfiguration config = new YamlConfiguration();
        for (Town town : registry.getTowns()) {
            serialize(config, town.getData());
        }
        return config;
    }
    
    public static YamlConfiguration serialize(YamlConfiguration config, TownData data) {
        TownSerializer serializer = new TownSerializer(config, data);
        return serializer.perform();
    }
    
    public YamlConfiguration perform() {
        config.set(String.valueOf(data.id), "x");
        writeSettings();
        writeArea();
        writePeople();
        return config;
    }
    
    private void write(String key, Object obj) {
        config.set(data.id + "." + key, obj);
    }
    
    private void writeSettings() {
        write("settings.name", data.settings.name);
        write("settings.stage", data.settings.stage);
        for (Entry<TownSetting, Object> entry : data.settings.flags.entrySet()) {
            TownSetting setting = entry.getKey();
            Object value = entry.getValue();
            write("settings." + setting.getName(), setting.serialize(value));
        }
    }
    
    private void writeArea() {
        write("area.area", toList(data.area.chunks));
    }
    
    private void writePeople() {
        write("people.owner", data.people.owner.toString());
        write("people.governors", toList(data.people.governors));
        write("people.builders", toList(data.people.builders));
    }
    
    private List<String> toList(Set<?> set) {
        List<String> list = new ArrayList<>(set.size());
        for (Object obj : set) {
            list.add(obj.toString());
        }
        return list;
    }
    
}
