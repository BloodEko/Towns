package de.bloodeko.towns.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;

import de.bloodeko.towns.town.ChunkMap;
import de.bloodeko.towns.town.Town;

public class BlockBreak extends AbstractListener {
    
    public BlockBreak(ChunkMap map) {
        super(map);
    }
    
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Town town = getTown(event.getBlock().getLocation());
        if (town == null) {
            return;
        }
        if (!town.isAllowedToBuild()) {
            event.getPlayer().sendMessage("You cannot break blocks here!");
            event.setCancelled(true);
            return;
        }
    }
    
}
