package de.bloodeko.towns;

import org.bukkit.Bukkit;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.world.World;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;

import de.bloodeko.towns.core.townarea.ChunkService;
import de.bloodeko.towns.core.townchat.domain.TownChatService;
import de.bloodeko.towns.core.townnames.NameService;
import de.bloodeko.towns.core.townpeople.PeopleService;
import de.bloodeko.towns.core.townplots.PlotService;
import de.bloodeko.towns.core.towns.TownService;
import de.bloodeko.towns.core.townsettings.SettingsService;
import de.bloodeko.towns.core.townstages.domain.StageService;
import net.milkbowl.vault.economy.Economy;

/**
 * Uses the service locator pattern to acts as 
 * a registry for services within the plugin.
 */
public class Services {
    private static Services instance;
    
    /**
     * Sets the registry for the plugin. Consider all fields 
     * to be set non-null, problems at the runtime.
     */
    public static Services init(Towns plugin) {
        instance = new Services();
        instance.plugin = plugin;
        return instance;
    }

    Towns plugin;
    Economy economy;
    
    TownService townService;
    NameService nameService;
    ChunkService chunkService;
    PeopleService peopleService;
    SettingsService settingsService;
    StageService stageService;
    PlotService plotService;
    TownChatService townchatService;
    
    
    public static Towns plugin() {
        return instance.plugin;
    }
    
    public static Economy economy() {
        return instance.economy;
    }
    
    public static TownService townservice() {
        return instance.townService;
    }
    
    public static NameService nameservice() {
        return instance.nameService;
    }
    
    public static ChunkService chunkservice() {
        return instance.chunkService;
    }
    
    public static PeopleService peopleservice() {
        return instance.peopleService;
    }
    
    public static RegionManager regionmanager() {
        World world = BukkitAdapter.adapt(Bukkit.getWorld("world"));
        return WorldGuard.getInstance().getPlatform().getRegionContainer().get(world);
    }

    public static SettingsService settingsservice() {
        return instance.settingsService;
    }
    
    public static StageService stageservice() {
        return instance.stageService;
    }
    
    public static PlotService plotservice() {
        return instance.plotService;
    }

    public static TownChatService townchat() {
        return instance.townchatService;
    }
}
