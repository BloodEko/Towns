package de.bloodeko.towns.town;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Called when a town is loaded to register it 
 * to various services.
 */
public class TownLoadEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    
    private Town town;
    
    public TownLoadEvent(Town town) {
        this.town = town;
    }
    
    /**
     * Returns the town entity that is about 
     * to get registered.
     */
    public Town getTown() {
        return town;
    }
    
    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }
    
    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
