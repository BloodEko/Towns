package de.bloodeko.towns;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import de.bloodeko.towns.cmds.CmdFactory;
import de.bloodeko.towns.town.ChunkMap;
import de.bloodeko.towns.town.Town;
import de.bloodeko.towns.town.TownFactory;
import de.bloodeko.towns.town.TownRegistry;
import de.bloodeko.towns.town.settings.Settings;
import de.bloodeko.towns.town.settings.SettingsRegistry;
import de.bloodeko.towns.town.settings.plots.RentService;
import de.bloodeko.towns.town.settings.plots.cmds.PlotCmd;
import de.bloodeko.towns.town.settings.plots.cmds.PlotPayrentCmd;
import de.bloodeko.towns.util.BukkitFactory;
import de.bloodeko.towns.util.Messages;
import de.bloodeko.towns.util.ModifyException;
import de.bloodeko.towns.util.Node;
import de.bloodeko.towns.util.Util;
import de.bloodeko.towns.util.YamlDeserializer;
import de.bloodeko.towns.util.YamlSerializer;
import net.milkbowl.vault.economy.Economy;

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
    private Economy economy;
    private boolean shutdown;

    public ChunkMap getChunkMap() {
        return chunkmap;
    }
    
    public TownRegistry getTownRegistry() {
        return registry;
    }

    public SettingsRegistry getSettingsRegistry() {
        return settings;
    }
    
    public Economy getEconomy() {
        if (economy == null) {
            throw new ModifyException("Economy is null!");
        }
        return economy;
    }
    
    @Override
    public void onEnable() {
        try {
            checkError();
            loadMessages();
            loadChunkMap();
            loadSettings();
            loadNames();
            loadEconomy();
            loadCommands();
            loadListeners();
            loadSerializer();
            loadTowns();
        }
        catch(Exception ex) {
            shutdown = true;
            ex.printStackTrace();
            Bukkit.shutdown();
        }
    }
    
    private void checkError() {
        if (getErrorFile().exists()) {
            throw new IllegalStateException("Cannot start due to shutdown error.");
        }
    }
    
    private void loadMessages() {
        Messages.enable(Util.readLines(getResource("messages.properties")));
    }
    
    private void loadChunkMap() {
        chunkmap = BukkitFactory.newChunkHandler(this);
    }
    
    private void loadSettings() {
        settings = Settings.newRegistry();
    }
    
    private void loadNames() throws Exception {
        File file = new File(getDataFolder() + "/registry.yml");
        YamlConfiguration config = new YamlConfiguration();
        config.load(file);
        int id = config.getInt("id", 0);
        registry = TownFactory.newRegistry(chunkmap, id);
    }
    
    private void loadEconomy() {
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return;
        }
        economy = rsp.getProvider();
    }
    
    private void loadCommands() {
        CmdFactory.init(this);
    }
    
    private void loadListeners() {
        RentService service = new RentService(TownFactory.getWorldManager(), registry, economy);
        Bukkit.getPluginManager().registerEvents(service, this);
        
        PlotCmd cmd = (PlotCmd) Bukkit.getPluginCommand("plot").getExecutor();
        cmd.register("payrent", new PlotPayrentCmd(chunkmap, service));
    }
    
    private void loadSerializer() {
        YamlSerializer.class.getName();
    }
    
    
    private void loadTowns() throws Exception {
        File file = new File(getDataFolder() + "/towns.yml");
        YamlConfiguration config = new YamlConfiguration();
        config.load(file);
        
        Node towns = new YamlDeserializer(config).getRoot();
        TownFactory.loadTowns(towns, chunkmap, registry, settings,
          TownFactory.getWorldManager());
    }
    
    
    @Override
    public void onDisable() {
        if (shutdown) {
            return;
        }
        try {
            saveRegistry();
            saveTowns();
        } catch (Exception ex) {
            createErrorFile(ex);
        }
    }
    
    
    private void createErrorFile(Exception ex) {
        ex.printStackTrace();
        try {
            File file = getErrorFile();
            file.createNewFile();
            ex.printStackTrace(new PrintWriter(file));
        } catch (IOException e) {
            ex.printStackTrace();
        }
    }
    
    
    private File getErrorFile() {
        return new File(getDataFolder() + "/error.dat");
    }
    
    
    public void saveRegistry() throws Exception {
        YamlConfiguration config = new YamlConfiguration();
        config.set("id", registry.getId());
        config.save(getDataFolder() + "/registry.yml");
    }
    
    
    public void saveTowns() throws IOException {
        Node towns = new Node();
        for (Town town : registry.getTowns()) {
            towns.set("" + town.getId(), town.serialize());
        }
        YamlSerializer serializer = new YamlSerializer(new YamlConfiguration(), towns);
        YamlConfiguration config = serializer.getResult();
        config.save(getDataFolder() + "/towns.yml");
    }
}
