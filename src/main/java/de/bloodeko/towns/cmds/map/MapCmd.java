package de.bloodeko.towns.cmds.map;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import de.bloodeko.towns.cmds.CmdBase;
import de.bloodeko.towns.town.ChunkMap;
import de.bloodeko.towns.town.Town;
import de.bloodeko.towns.util.Chunk;

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
        MapView view = newView(getMap(), player, local);
        listener.add(player.getUniqueId(), view);
        view.open();
    }
    
    private boolean hasTown(Location location) {
        return getMap().hasTown(Chunk.fromLocation(location));
    }

    /**
     * Returns a new MapView either to either display 
     * the global map or a single town.
     */
    private MapView newView(ChunkMap map, Player player, boolean local) {
        if (local) {
            Town town = getTownAsPlayer(player);
            return MapFactory.newTownView(map, player, town);
        }
        return MapFactory.newGlobalView(map, player);
    }
}
