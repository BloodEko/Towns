package de.bloodeko.towns.protect;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;

import de.bloodeko.towns.session1.ChunkMap;

public class BlockInteract extends AbstractListener {
    
    public BlockInteract(ChunkMap map) {
        super(map);
    }
    
    @EventHandler
    public void onBlockInteract(PlayerInteractEvent event) {
        /*
        System.out.println("");
        System.out.println("hasBlock: " + event.hasBlock());
        System.out.println("hasItem: " + event.hasItem());
        System.out.println("isBlockInHand: " + event.isBlockInHand());
        System.out.println("getAction: " + event.getAction());
        System.out.println("getClickedBlock: " + event.getClickedBlock());
        System.out.println("getHand: " + event.getHand());
        System.out.println("getItem: " + event.getItem());
        System.out.println("getMaterial: " + event.getMaterial());
        System.out.println("getPlayer: " + event.getPlayer());
        System.out.println("useInteractedBlock: " + event.useInteractedBlock());
        System.out.println("useItemInHand: " + event.useItemInHand());
        */
    }
}
