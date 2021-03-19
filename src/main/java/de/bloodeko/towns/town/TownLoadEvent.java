package de.bloodeko.towns.town;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Called when a Town is founded or loaded on startup.
 */
public class TownLoadEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    
    public final Town town;
    
    public TownLoadEvent(Town town) {
        this.town = town;
    }
    
    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }
    
    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
