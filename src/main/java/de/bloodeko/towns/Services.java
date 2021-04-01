package de.bloodeko.towns;

import com.sk89q.worldguard.protection.managers.RegionManager;

import de.bloodeko.towns.town.ChunkMap;
import de.bloodeko.towns.town.TownRegistry;
import de.bloodeko.towns.town.settings.SettingsRegistry;
import net.milkbowl.vault.economy.Economy;

/**
 * Uses the service locator pattern to acts as 
 * a registry for services within the plugin.
 */
public class Services {
    private static Services instance;
    public Towns plugin;
    public ChunkMap chunkmap;
    public TownRegistry registry;
    public SettingsRegistry settings;
    public Economy economy;
    public RegionManager manager;
    
    /**
     * Sets the registry, consider all field to be
     * non-null, to prevent problems at the runtime.
     */
    public static void set(Services locator) {
        instance = locator;
    }
    
    public static Towns plugin() {
        return instance.plugin;
    }
    
    public static ChunkMap chunkMap() {
        return instance.chunkmap;
    }
    
    public static TownRegistry towns() {
        return instance.registry;
    }
    
    public static SettingsRegistry settings() {
        return instance.settings;
    }
    
    public static Economy economy() {
        return instance.economy;
    }
    
    public static RegionManager regions() {
        return instance.manager;
    }
}
