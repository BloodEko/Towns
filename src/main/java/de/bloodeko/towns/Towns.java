package de.bloodeko.towns;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import de.bloodeko.towns.cmds.CmdFactory;
import de.bloodeko.towns.town.ChunkMap;
import de.bloodeko.towns.town.Town;
import de.bloodeko.towns.town.TownFactory;
import de.bloodeko.towns.town.TownRegistry;
import de.bloodeko.towns.town.settings.SettingsFactory;
import de.bloodeko.towns.town.settings.SettingsRegistry;
import de.bloodeko.towns.town.settings.plots.RentService;
import de.bloodeko.towns.town.settings.plots.cmds.PlotCmd;
import de.bloodeko.towns.town.settings.plots.cmds.PlotPayrentCmd;
import de.bloodeko.towns.util.BukkitFactory;
import de.bloodeko.towns.util.Messages;
import de.bloodeko.towns.util.Node;
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
 * +FoundCMD, 
 * +AnimalProtect, +PVP, +Serialiable Settings objects.
 * +Messages
 * +Chunkrules
 * +registryIdFile
 * -PlayerCache. onEnable/onDisable/onJoin/Quit is set.
 * 
 * ?MoreSettings/+SettingsUpgrade
 * ?Plots, PlotManager. WorldMap, ChunkMap, ChunkToTownWrapper.
 * ?Location/Chunk/Region hooks. (for unclaim)
 * ?StufenAufstiege.
 * ?Chatsystem. 
 * ?Config.
 * ?Permissions town.mod/admin/notifyTownUpgrade
 */
public class Towns extends JavaPlugin {
    private ChunkMap chunkmap;
    private TownRegistry registry;
    private SettingsRegistry settings;

    public ChunkMap getChunkMap() {
        return chunkmap;
    }
    
    public TownRegistry getTownRegistry() {
        return registry;
    }

    public SettingsRegistry getSettingsRegistry() {
        return settings;
    }
    
    @Override
    public void onEnable() {
        loadMessages();
        loadChunkMap();
        loadSettings();
        loadNames();
        loadCommands();
        loadListeners();
        loadSerializer();
        loadTowns();
    }
    
    private void loadMessages() {
        Messages.enable(Util.readLines(getResource("messages.properties")));
    }
    
    private void loadChunkMap() {
        chunkmap = BukkitFactory.newChunkHandler(this);
    }
    
    private void loadSettings() {
        settings = SettingsFactory.newSettingsRegistry();
    }
    
    private void loadNames() {
        File file = new File(getDataFolder() + "/registry.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        int id = config.getInt("id", 0);
        registry = TownFactory.newRegistry(chunkmap, id);
    }
    
    private void loadCommands() {
        CmdFactory.init(this);
    }
    
    private void loadListeners() {
        RentService service = new RentService(TownFactory.getWorldManager(), registry);
        Bukkit.getPluginManager().registerEvents(service, this);
        
        PlotCmd cmd = (PlotCmd) Bukkit.getPluginCommand("plot").getExecutor();
        cmd.register("payrent", new PlotPayrentCmd(chunkmap, service));
    }
    
    private void loadSerializer() {
        YamlSerializer.class.getName();
    }
    
    private void loadTowns() {
        File file = new File(getDataFolder() + "/towns.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        Node towns = new YamlDeserializer(config).getRoot();
        TownFactory.loadTowns(towns, chunkmap, registry, settings,
          TownFactory.getWorldManager());
    }
    
    @Override
    public void onDisable() {
        saveRegistry();
        saveTowns();
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
    
    public void saveTowns() {
        try {
            Node towns = new Node();
            for (Town town : registry.getTowns()) {
                towns.set("" + town.getId(), town.serialize());
            }
            YamlSerializer serializer = new YamlSerializer(new YamlConfiguration(), towns);
            YamlConfiguration config = serializer.getResult();
            config.save(getDataFolder() + "/towns.yml");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
