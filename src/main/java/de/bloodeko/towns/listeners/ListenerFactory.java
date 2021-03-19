package de.bloodeko.towns.listeners;

import org.bukkit.plugin.PluginManager;

import de.bloodeko.towns.Towns;
import de.bloodeko.towns.town.ChunkMap;

public class ListenerFactory {
    
    public static void init(Towns plugin) {
        PluginManager manager = plugin.getServer().getPluginManager();
        ChunkMap map = plugin.getChunkMap();
        manager.registerEvents(new BlockBreak(map), plugin);
        manager.registerEvents(new BlockInteract(map), plugin);
        manager.registerEvents(new BlockPlace(map), plugin);
        manager.registerEvents(new BreakHanging(map), plugin);
        //manager.registerEvents(new PlayerMove(map), plugin);
    }
}
