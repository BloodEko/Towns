package de.bloodeko.towns.core.towns.legacy;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.sk89q.worldguard.protection.managers.RegionManager;

import de.bloodeko.towns.Services;
import de.bloodeko.towns.util.Chunk;

/**
 * Listens to register the town to core services.
 */
public class TownLoadListener implements Listener {
    private ChunkMap map;
    private TownRegistry names;
    private RegionManager manager;
    
    public TownLoadListener() {
        this.map = Services.chunkMap();
        this.names = Services.towns();
        this.manager = Services.regions();
    }
    
    @EventHandler
    public void onLoadTown(TownLoadEvent event) {
        Town town = event.getTown();
        for (Chunk chunk : town.getArea().getChunks()) {
            map.setTown(chunk, town);
        }
        names.add(town);
        manager.addRegion(town.getArea().getRegion());
    }
}
