package de.bloodeko.towns.town;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import de.bloodeko.towns.town.Town.TownData;
import de.bloodeko.towns.town.TownArea.TownAreaData;
import de.bloodeko.towns.town.TownPeople.TownPeopleData;
import de.bloodeko.towns.town.TownSettings.TownSettingsData;
import de.bloodeko.towns.util.Chunk;

public class TownDeserializer {
    
    public static List<TownData> deserializeTowns(YamlConfiguration config) {
        List<TownData> list = new ArrayList<>();
        for (String id : config.getKeys(false)) {
            TownData data = deserializeTown(id, config.getConfigurationSection(id));
            list.add(data);
        }
        return list;
    }
    
    
    public static TownData deserializeTown(String id, ConfigurationSection town) {
        TownSettingsData settings = newTownSettingsData(
          town.getConfigurationSection("settings"));
        
        TownAreaData area = newTownAreaData(
          town.getConfigurationSection("area"));
        
        TownPeopleData people = newTownPeopleData(
          town.getConfigurationSection("people"));
        
        return new TownData(Integer.valueOf(id), settings, area, people);
    }
    
    
    private static TownSettingsData newTownSettingsData(ConfigurationSection settings) {
        String name = settings.getString("name");
        return new TownSettingsData(name, null);
    }
    
    
    private static TownAreaData newTownAreaData(ConfigurationSection area) {
        Set<Chunk> chunks = new HashSet<>();
        for (String chunk : area.getStringList("area")) {
            chunks.add(Chunk.fromString(chunk));
        }
        return new TownAreaData(chunks, null, null, null);
    }
    
    
    private static TownPeopleData newTownPeopleData(ConfigurationSection people) {
        UUID owner = UUID.fromString(people.getString("owner"));
        
        Set<UUID> governors = new HashSet<>();
        for (String uuid : people.getStringList("govenors")) {
            governors.add(UUID.fromString(people.getString(uuid)));
        }
        
        Set<UUID> builders = new HashSet<>();
        for (String uuid : people.getStringList("builders")) {
            builders.add(UUID.fromString(people.getString(uuid)));
        }
        
        return new TownPeopleData(owner, governors, builders, null);
    }
}
