package de.bloodeko.towns.cmds.map;

import java.util.Map;
import java.util.UUID;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

/**
 * Handles incoming clicks and maps
 * them to their corresponding view.
 */
public class MapListener implements Listener {
    private Map<UUID, MapView> views;
    
    public MapListener(Map<UUID, MapView> map) {
        views = map;
    }

    public void add(UUID player, MapView view) {
        views.put(player, view);
    }
    
    @EventHandler
    public void onClick(InventoryClickEvent click) {
        MapView view = views.get(click.getWhoClicked().getUniqueId());
        if (view == null) {
            return;
        }
        click.setCancelled(true);
        switch(click.getSlot()) {
            case 0: view.toCenter(); break;
            case 1: view.zoom(click.getClick()); break;
            case 3: view.moveUp(); break;
            case 4: view.moveDown(); break;
            case 6: view.moveRight(); break;
            case 7: view.moveLeft(); break;
        }
    }
    
    @EventHandler
    public void onClose(InventoryCloseEvent close) {
        views.remove(close.getPlayer().getUniqueId());
    }
}
