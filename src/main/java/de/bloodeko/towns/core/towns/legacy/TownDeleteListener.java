package de.bloodeko.towns.core.towns.legacy;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import com.sk89q.worldguard.protection.managers.RegionManager;

import de.bloodeko.towns.Services;
import de.bloodeko.towns.util.Chunk;

/**
 * Listens to remove the town from core services.
 */
public class TownDeleteListener implements Listener {
    private TownRegistry registry;
    private ChunkMap map;
    private RegionManager manager;
    
    public TownDeleteListener() {
        this.registry = Services.towns();
        this.map = Services.chunkMap();
        this.manager = Services.regions();
    }
    
    @EventHandler(priority=EventPriority.HIGH)
    public void onTownDelete(TownDeleteEvent event) {
        if (event.isCancelled()) {
            return;
        }
        Town town = event.getTown();
        for (Chunk chunk : town.getArea().getChunks()) {
            map.removeTown(chunk);
        }
        registry.remove(town.getSettings().getName());
        manager.removeRegion("town_" + town.getId());
    }
}
