package de.bloodeko.towns.town;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Called when a town was deleted.
 */
public class TownDeletedEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    
    public final Town town;
    
    public TownDeletedEvent(Town town) {
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