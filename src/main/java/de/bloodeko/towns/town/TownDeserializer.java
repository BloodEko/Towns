package de.bloodeko.towns.town;

public class TownDeserializer {
    
    /*
    public static List<TownData> deserializeTowns(YamlConfiguration config, SettingsRegistry registry) {
        List<TownData> list = new ArrayList<>();
        for (String id : config.getKeys(false)) {
            TownData data = deserializeTown(id, config.getConfigurationSection(id), registry);
            list.add(data);
        }
        return list;
    }
    
    
    public static TownData deserializeTown(String id, ConfigurationSection town, SettingsRegistry registry) {
        TownSettingsData settings = newTownSettingsData(
          town.getConfigurationSection("settings"), registry);
        
        TownAreaData area = newTownAreaData(
          town.getConfigurationSection("area"));
        
        TownPeopleData people = newTownPeopleData(
          town.getConfigurationSection("people"));
        
        return new TownData(Integer.valueOf(id), settings, area, people);
    }
    
    
    private static TownSettingsData newTownSettingsData(ConfigurationSection settings, SettingsRegistry registry) {
        String name = settings.getString("name");
        int stage = settings.getInt("stage");
        
        Map<TownSetting, Object> map = new HashMap<>();
        for (String key : settings.getKeys(false)) {
            if (key.equals("name") || key.equals("stage")) {
                continue;
            }
            TownSetting setting = registry.settings().get(key);
            map.put(setting, setting.deserialize(settings.get(key)));
        }
        
        return new TownSettingsData(name, stage, map);
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
    */
}
