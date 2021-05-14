package de.bloodeko.towns.core.townstages.ui;

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
public class InventoyListener implements Listener {
    private Map<UUID, GUIView> views;
    
    public InventoyListener(Map<UUID, GUIView> map) {
        views = map;
    }
    
    /**
     * Adds a player, so his inventory clicks are
     * tracked and delegated to the view.
     */
    public void add(UUID uuid, GUIView view) {
        views.put(uuid, view);
    }
    
    @EventHandler
    public void onClick(InventoryClickEvent click) {
        GUIView view = views.get(click.getWhoClicked().getUniqueId());
        if (view == null) {
            return;
        }
        click.setCancelled(true);
        view.clickSlot(click);
    }
    
    @EventHandler
    public void onClose(InventoryCloseEvent close) {
        views.remove(close.getPlayer().getUniqueId());
    }
    
    /**
     * Represents an interface for an GUI which 
     * can react to clicks and update itself.
     */
    public interface GUIView {
        public void clickSlot(InventoryClickEvent click);
    }

}
