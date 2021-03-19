package de.bloodeko.towns.protect;

import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;

import de.bloodeko.towns.session1.ChunkMap;
import de.bloodeko.towns.session2.Town;

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
        if (!town.isAllowedToBuild(event.getPlayer())) {
            event.getPlayer().sendMessage("You cannot place blocks here!");
            event.setCancelled(true);
            return;
        }
    }
    
}
