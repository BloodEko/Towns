package de.bloodeko.towns;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import de.bloodeko.towns.core.townarea.ChunkData;
import de.bloodeko.towns.core.townnames.NameData;
import de.bloodeko.towns.core.townpeople.PeopleData;
import de.bloodeko.towns.core.townplots.PlotData;
import de.bloodeko.towns.core.townplots.ui.PlotCmd;
import de.bloodeko.towns.core.townplots.ui.PlotPayrentCmd;
import de.bloodeko.towns.core.towns.TownsData;
import de.bloodeko.towns.core.townsettings.data.SettingsData;
import de.bloodeko.towns.core.townstages.data.StageSerializer;
import de.bloodeko.towns.util.Messages;
import de.bloodeko.towns.util.Node;
import de.bloodeko.towns.util.Util;
import de.bloodeko.towns.util.YamlDeserializer;
import de.bloodeko.towns.util.YamlSerializer;
import de.bloodeko.towns.util.chat.ChatFactory;
import de.bloodeko.towns.util.cmds.CmdFactory;
import net.milkbowl.vault.economy.Economy;

/**
 * Loads the services and towns, by an exception, stops the server.
 * Persists the towns on disable, by an exception, creates an 
 * error file, that prevents the server from further startups.
 */
public class Towns extends JavaPlugin {
    private final File townsFile = new File(getDataFolder() + "/towns.yml");
    private final File errorFile = new File(getDataFolder() + "/error.dat");
    
    private boolean shutdown;
    private Services locator;
    
    @Override
    public void onEnable() {
        try {
            checkError();
            loadMessages();
            loadLocator();
            loadCommands();
            loadListeners();
        } 
        catch(Exception ex) {
            shutdown = true;
            ex.printStackTrace();
            Bukkit.shutdown();
        }
    }
    
    private void loadMessages() {
        Messages.enable(Util.readLines(getResource("messages.properties")));
    }

    private void loadLocator() {
        locator = Services.init(this);
        loadServices();
        loadEconomy();
    }
    
    /**
     * Reads the data from all services and sets their locators.
     */
    private void loadServices() {
        Node root = new YamlDeserializer(loadYaml(townsFile)).getRoot();
        
        locator.chunkService = ChunkData.read(      root, Services.regionmanager());
        locator.nameService = NameData.read(        root.getNode("townnames"));
        locator.peopleService = PeopleData.read(    root.getNode("townpeople"));
        locator.plotService = PlotData.read(        root.getNode("townplots"));
        locator.settingsService = SettingsData.read(root.getNode("townsettings"));
        locator.stageService = StageSerializer.read(root.getNode("townstages"));
        locator.townService = TownsData.read(       root.getNode("towns"));
    }
    
    /**
     * Writes the data from all services to the towns file.
     */
    private void saveServices() throws Exception {
        Node root = new Node();
        root.set("chunks",       ChunkData.write(locator.chunkService));
        root.set("townnames",    NameData.write(locator.nameService));
        root.set("townpeople",   PeopleData.write(locator.peopleService));
        root.set("townplots",    PlotData.write(locator.plotService));
        root.set("townsettings", SettingsData.write(locator.settingsService));
        root.set("townstages",   StageSerializer.write(locator.stageService));
        root.set("towns",        TownsData.write(locator.townService));
        
        YamlSerializer serializer = new YamlSerializer(new YamlConfiguration(), root);
        serializer.getResult().save(townsFile);
    }
    
    /**
     * Loads the bridge to the economy module.
     * Throws an exception otherwise.
     */
    private void loadEconomy() {
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            throw new IllegalStateException("Economy not found.");
        }
        locator.economy = rsp.getProvider();
    }
    
    /**
     * Loads the commands module.
     * Requires the Services to be set.
     */
    private void loadCommands() {
        CmdFactory.load();
    }
    
    /**
     * Loads core listeners and other modules.
     */
    private void loadListeners() {
        PlotCmd cmd = (PlotCmd) Bukkit.getPluginCommand("gs").getExecutor();
        cmd.register("mietenzahlung", new PlotPayrentCmd());
        ChatFactory.load(this);
        YamlSerializer.class.getName();
    }
    
    /**
     * Returns the YamlConfiguration for that file.
     * Or a newly created, empty YamlConfiguration.
     */
    private YamlConfiguration loadYaml(File file) {
        try {
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
        if (shutdown) return;
        try {
            saveServices();
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
        Util.writeException(errorFile, ex);
    }
    
    /**
     * Checks if there was an error during the last startup.
     * Throws an exception if this is the case.
     */
    private void checkError() {
        if (errorFile.exists()) {
            throw new IllegalStateException("Towns is set to safetymode.");
        }
    }
}
