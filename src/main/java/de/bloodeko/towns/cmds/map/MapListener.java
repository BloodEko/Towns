package de.bloodeko.towns.cmds.map;

import java.util.Map;
import java.util.UUID;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

/**
 * Service that maps players to views and handles incoming
 * inventory click events, to are dispatch them to a view.
 * Removes players, when they close their inventory.
 */
public class MapListener implements Listener {
    private Map<UUID, MapView> views;
    
    public MapListener(Map<UUID, MapView> map) {
        views = map;
    }
    
    /**
     * Adds a player, so his inventory clicks are
     * tracked and delegated to the view.
     */
    public void add(UUID uuid, MapView view) {
        views.put(uuid, view);
    }
    
    @EventHandler
    public void onClick(InventoryClickEvent click) {
        MapView view = views.get(click.getWhoClicked().getUniqueId());
        if (view == null) {
            return;
        }
        click.setCancelled(true);
        view.clickSlot(click.getSlot(), click.getClick());
    }
    
    @EventHandler
    public void onClose(InventoryCloseEvent close) {
        views.remove(close.getPlayer().getUniqueId());
    }
}
