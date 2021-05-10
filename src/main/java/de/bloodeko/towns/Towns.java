package de.bloodeko.towns;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import de.bloodeko.towns.chat.ChatFactory;
import de.bloodeko.towns.cmds.CmdFactory;
import de.bloodeko.towns.town.Town;
import de.bloodeko.towns.town.TownDeleteListener;
import de.bloodeko.towns.town.TownFactory;
import de.bloodeko.towns.town.TownLoadListener;
import de.bloodeko.towns.town.settings.Settings;
import de.bloodeko.towns.town.settings.plots.RentService;
import de.bloodeko.towns.town.settings.plots.cmds.PlotCmd;
import de.bloodeko.towns.town.settings.plots.cmds.PlotPayrentCmd;
import de.bloodeko.towns.util.Messages;
import de.bloodeko.towns.util.Node;
import de.bloodeko.towns.util.Util;
import de.bloodeko.towns.util.YamlDeserializer;
import de.bloodeko.towns.util.YamlSerializer;
import net.milkbowl.vault.economy.Economy;

/**
 * Loads the services and towns, by an exception, stops the server.
 * Persists the towns on disable, by an exception, creates an 
 * error file, that prevents the server from further startups.
 */
public class Towns extends JavaPlugin {
    private boolean shutdown;
    private Services locator;
    
    @Override
    public void onEnable() {
        try {
            checkError();
            loadCore();
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
            throw new IllegalStateException("Towns is set to safetymode.");
        }
    }
    
    /**
     * Loads all core services and adds them to 
     * the service locator.
     */
    private void loadCore() throws Exception {
        loadMessages();
        loadService();
        loadChunks();
        loadSettings();
        loadNames();
        loadEconomy();
    }
    
    private void loadMessages() {
        Messages.enable(Util.readLines(getResource("messages.properties")));
    }
    
    private void loadService() {
        Services.set(locator = new Services());
        locator.plugin = this;
    }
    
    private void loadChunks() {
        locator.chunkmap = TownFactory.newChunkMap();
        locator.regions = TownFactory.getWorldManager();
    }
    
    private void loadSettings() {
        locator.settings = Settings.newRegistry();
    }
    
    private void loadNames() {
        int id = loadYaml("registry.yml").getInt("id", 0);
        locator.registry = TownFactory.newRegistry(id);
    }
    
    private void loadEconomy() {
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            throw new IllegalStateException("Economy not found.");
        }
        locator.economy = rsp.getProvider();
    }
    
    /**
     * Loads the commands module.
     */
    private void loadCommands() {
        CmdFactory.load();
    }
    
    /**
     * Loads core listeners and other modules.
     */
    private void loadListeners() {
        Listener onLoad = new TownLoadListener();
        Bukkit.getPluginManager().registerEvents(onLoad, this);
        
        Listener onDelete = new TownDeleteListener();
        Bukkit.getPluginManager().registerEvents(onDelete, this);
        
        RentService service = new RentService();
        Bukkit.getPluginManager().registerEvents(service, this);
        
        PlotCmd cmd = (PlotCmd) Bukkit.getPluginCommand("gs").getExecutor();
        cmd.register("mietenzahlung", new PlotPayrentCmd(service));
        
        ChatFactory.load(this);
    }
    
    /**
     * Loads all towns into the plugin.
     */
    private void loadTowns() {
        Node towns = new YamlDeserializer(loadYaml("towns.yml")).getRoot();
        TownFactory.loadTowns(towns);
    }
    
    private void loadSerializer() {
        YamlSerializer.class.getName();
    }
    
    private YamlConfiguration loadYaml(String path) {
        try {
            File file = new File(getDataFolder() + "/" + path);
            YamlConfiguration config = new YamlConfiguration();
            if (file.exists()) {
                config.load(file);
            }
            return config;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
    
    
    @Override
    public void onDisable() {
        if (shutdown) {
            return;
        }
        try {
            saveNames();
            saveTowns();
        } catch (Exception ex) {
            createErrorFile(ex);
        }
    }
    
    /**
     * Creates an error file for the Exception, which 
     * will prevent further startups until deletion.
     */
    private void createErrorFile(Exception ex) {
        ex.printStackTrace();
        Util.writeException(getErrorFile(), ex);
    }
    
    private File getErrorFile() {
        return new File(getDataFolder() + "/error.dat");
    }
    
    public void saveNames() throws Exception {
        YamlConfiguration config = new YamlConfiguration();
        config.set("id", Services.towns().getId());
        config.save(getDataFolder() + "/registry.yml");
    }
    
    
    public void saveTowns() throws IOException {
        Node towns = new Node();
        for (Town town : Services.towns().getTowns()) {
            towns.set("" + town.getId(), town.serialize());
        }
        YamlSerializer serializer = new YamlSerializer(new YamlConfiguration(), towns);
        YamlConfiguration config = serializer.getResult();
        config.save(getDataFolder() + "/towns.yml");
    }
}
