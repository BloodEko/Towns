package de.bloodeko.towns.protect;

import org.bukkit.plugin.PluginManager;

import de.bloodeko.towns.Towns;
import de.bloodeko.towns.session1.ChunkMap;

public class AProtectFactory {
    
    public static void register(Towns plugin) {
        PluginManager manager = plugin.getServer().getPluginManager();
        ChunkMap map = plugin.getChunkMap();
        manager.registerEvents(new BlockBreak(map), plugin);
        manager.registerEvents(new BlockInteract(map), plugin);
        manager.registerEvents(new BlockPlace(map), plugin);
        manager.registerEvents(new BreakHanging(map), plugin);
    }
}
