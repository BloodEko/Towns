package de.bloodeko.towns;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import de.bloodeko.towns.cmds.CmdFactory;
import de.bloodeko.towns.cmds.settings.SettingsRegistry;
import de.bloodeko.towns.listeners.ListenerFactory;
import de.bloodeko.towns.town.ChunkMap;
import de.bloodeko.towns.town.Town;
import de.bloodeko.towns.town.TownFactory;
import de.bloodeko.towns.town.TownRegistry;
import de.bloodeko.towns.util.BukkitFactory;
import de.bloodeko.towns.util.Messages;
import de.bloodeko.towns.util.Util;
import de.bloodeko.towns.util.YamlDeserializer;
import de.bloodeko.towns.util.YamlSerializer;

/**
 * TownCommand (handler system with tab completion)
 * ChunkHandler (variable in Towns)
 * ChunkDisplay (vriable in Towns)
 * /stadt map (uses chunkdisplay. with zoom etc.)
 * 
 * one package per feature.
 * > chunkmap. towns. claiming. map. info. commands. util.
 * > one package per command. features that are used by multiple commands, get an own package.
 * 
 * +FoundCMD, +AnimalProtect, +PVP, +Serialiable Settings objects.
 * Plots, PlotManager. WorldMap, ChunkMap, ChunkToTownWrapper.
 * Config. +Messages/Claimingvariables. Location/Chunk/Region hooks. (for unclaim)
 * StufenAufstiege. Chatsystem. PlayerCache. file. per line UUID:name. onJoin/Quit is set. 
 * onEnable/Disable to/from file. Where to save nextId when no indent?
 * 
 * town.nodifymod notifies for townupgrades on join.
 * town.mod allows cmds.
 * town.admin allows cmds.
 */
public class Towns extends JavaPlugin {
    private ChunkMap chunkmap;
    private TownRegistry registry;
    private SettingsRegistry settings;
    
    @Override
    public void onEnable() {
        Messages.enable(Util.readLines(getResource("messages.properties")));
        
        chunkmap = BukkitFactory.newChunkHandler(this);
        settings = CmdFactory.newSettingsRegistry();
        registry = loadRegistry();
        CmdFactory.init(this);
        ListenerFactory.init(this);
        loadTowns();
        
        //load class.
        YamlSerializer.class.getName();
    }
    
    @Override
    public void onDisable() {
        saveRegistry();
        saveTowns();
    }

    public ChunkMap getChunkMap() {
        return chunkmap;
    }
    
    public TownRegistry getTownRegistry() {
        return registry;
    }
    
    public TownRegistry loadRegistry() {
        File file = new File(getDataFolder() + "/registry.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        int id = config.getInt("id", 0);
        return TownFactory.newRegistry(chunkmap, id);
    }
    
    public void saveRegistry() {
        try {
            YamlConfiguration config = new YamlConfiguration();
            config.set("id", registry.getId());
            config.save(getDataFolder() + "/registry.yml");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void loadTowns() {
        File file = new File(getDataFolder() + "/towns.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        Map<Object, Object> result = new YamlDeserializer(config).getResult();
        TownFactory.loadTowns(result, chunkmap, settings, registry,
          TownFactory.getWorldManager());
    }
    
    public void saveTowns() {
        try {
            Map<String, Object> towns = new HashMap<>();
            for (Town town : registry.getTowns()) {
                towns.put("" + town.getId(), town.serialize());
            }
            YamlSerializer serializer = new YamlSerializer(new YamlConfiguration(), towns);
            YamlConfiguration config = serializer.getResult();
            config.save(getDataFolder() + "/towns.yml");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
