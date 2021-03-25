package de.bloodeko.towns.cmds.map;

import org.bukkit.entity.Player;

import de.bloodeko.towns.cmds.CmdBase;
import de.bloodeko.towns.town.ChunkMap;

/**
 * Handles requests to provide a view implementation and
 * adds the player to the click listener service.
 */
public class MapCmd extends CmdBase {
    private MapListener listener;
    
    public MapCmd(ChunkMap map, MapListener listener) {
        super(map);
        this.listener = listener;
    }
    
    @Override
    public void execute(Player player, String[] args) {
        MapView view = MapFactory.newGlobalView(getMap(), player);
        listener.add(player.getUniqueId(), view);
        view.open();
    }
}
