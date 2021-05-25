package de.bloodeko.towns.core.townarea.ui.map;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import de.bloodeko.towns.core.towns.Town;
import de.bloodeko.towns.util.Chunk;
import de.bloodeko.towns.util.cmds.CmdBase;

/**
 * Handles requests to provide a view implementation and
 * adds the player to the click listener service.
 */
public class MapCmd extends CmdBase {
    private MapListener listener;
    
    public MapCmd(MapListener listener) {
        this.listener = listener;
    }
    
    @Override
    public void execute(Player player, String[] args) {
        boolean local = hasTown(player.getLocation());
        MapView view = newView(player, local);
        listener.add(player.getUniqueId(), view);
        view.open();
    }
    
    private boolean hasTown(Location location) {
        return getMap().has(Chunk.fromLocation(location));
    }

    /**
     * Returns a new MapView either to either display 
     * the global map or a single town.
     */
    private MapView newView(Player player, boolean local) {
        if (local) {
            Town town = getTownAsPlayer(player);
            return MapFactory.newTownView(getMap(), player, town);
        }
        return MapFactory.newGlobalView(getMap(), player);
    }
}
