package de.bloodeko.towns.cmds.map;

import java.util.Arrays;
import java.util.List;

import org.bukkit.entity.Player;

import de.bloodeko.towns.cmds.CmdBase;
import de.bloodeko.towns.town.ChunkMap;
import de.bloodeko.towns.town.Town;
import de.bloodeko.towns.util.Messages;
import de.bloodeko.towns.util.Util;

/**
 * Handles requests to provide a view implementation and
 * adds the player to the click listener service.
 */
public class MapCmd extends CmdBase {
    private final String localname = Messages.get("cmds.map.local");
    private MapListener listener;
    
    public MapCmd(MapListener listener) {
        this.listener = listener;
    }
    
    @Override
    public void execute(Player player, String[] args) {
        boolean local = hasArg(0, args) && getArg(0, args).equals(localname);
        MapView view = newView(getMap(), player, local);
        listener.add(player.getUniqueId(), view);
        view.open();
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
    
    @Override
    public List<String> completeTab(String[] args) {
        return Util.filterList(Arrays.asList(localname), args[0]);
    }
}
