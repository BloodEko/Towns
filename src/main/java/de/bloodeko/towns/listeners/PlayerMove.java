package de.bloodeko.towns.listeners;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

import de.bloodeko.towns.town.ChunkMap;

/**
 * TODO, move messages to town events.
 * OnEnterTown.
 * OnLeaveTown.
 */
public class PlayerMove extends AbstractListener {
    
    public PlayerMove(ChunkMap map) {
        super(map);
    }

    public void process(Player player, Location toLoc) {
        /*
        if (player.getLocation().getChunk() == toLoc.getChunk()) {
            return;
        }
        Town from = getTown(Chunk.fromLocation(player.getLocation()));
        Town to = getTown(Chunk.fromLocation(toLoc));
        
        if (from != to) {
            if (from != null) {
                player.sendMessage("You left " + from.getName());
            }
            if (to != null) {
                player.sendMessage("You entered " + to.getName());
            }
        }
        */
    }
    
    // https://github.com/DenizenScript/Denizen/blob/dev/plugin/src/main/java/com/denizenscript/denizen/events/entity/AreaEnterExitScriptEvent.java
    // check what to do onQuit/onJoin.
    // there needs to be a map, to keep track per block move.
    // combine with events.
    // enterMessage configurable
    // exitMessage configurable
    // send no message, if message is empty.
    // send no message, if messages are equal.
    
    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        process(event.getPlayer(), event.getTo());
    }
    
    /*
    public static class TownEnterEvent extends Event {
        private static final HandlerList handlers = new HandlerList();
        private Location from;
        private Location to;
        private Town fromTown;
        private Town toTown;
        private Player player;
        
        public TownEnterEvent(Location from, Location to, Town fromTown, Town toTown, Player player) {
            this.from = from;
            this.to = to;
            this.fromTown = fromTown;
            this.toTown = toTown;
            this.player = player;
        }
        
        @Override
        public HandlerList getHandlers() {
            return handlers;
        }

        public static HandlerList getHandlerList() {
            return handlers;
        }
    }*/
    
}
