package de.bloodeko.towns.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;

import de.bloodeko.towns.town.ChunkMap;
import de.bloodeko.towns.town.Town;

public class BlockPlace extends AbstractListener {
    
    public BlockPlace(ChunkMap map) {
        super(map);
    }
    
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Town town = getTown(event.getBlock().getLocation());
        if (town == null) {
            return;
        }
        if (!town.isAllowedToBuild()) {
            event.getPlayer().sendMessage("You cannot place blocks here!");
            event.setCancelled(true);
            return;
        }
    }
    
}
