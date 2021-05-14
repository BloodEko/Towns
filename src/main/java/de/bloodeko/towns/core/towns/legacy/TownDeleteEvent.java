package de.bloodeko.towns.core.towns.legacy;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Called when a town is triggered for deletion.
 * Register on high when working with the outcome.
 */
public class TownDeleteEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    
    private Town town;
    private boolean cancelled;
    private String message;
    
    public TownDeleteEvent(Town town) {
        this.town = town;
    }
    
    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }
    
    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    /**
     * Returns the town that is about 
     * to be deleted.
     */
    public Town getTown() {
        return town;
    }
    
    /**
     * Returns true if the town will 
     * not be deleted.
     */
    public boolean isCancelled() {
        return cancelled;
    }
    
    /**
     * Cancels the town deletion with an error message 
     * which will be shown in the UI.
     */
    public void setCancelled(String msg) {
        cancelled = true;
        message = msg;
    }
    
    /**
     * Returns the cancel message or null.
     */
    public String getResult() {
        return message;
    }
}
