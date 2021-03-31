package de.bloodeko.towns.town;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.sk89q.worldguard.protection.managers.RegionManager;

import de.bloodeko.towns.util.Chunk;

/**
 * Listens to register the town to core services.
 */
public class TownLoadListener implements Listener {
    private ChunkMap map;
    private TownRegistry names;
    private RegionManager manager;
    
    public TownLoadListener(ChunkMap map, TownRegistry names, RegionManager manager) {
        this.map = map;
        this.names = names;
        this.manager = manager;
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
