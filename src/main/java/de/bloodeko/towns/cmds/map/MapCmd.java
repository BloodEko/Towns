package de.bloodeko.towns.cmds.map;

import org.bukkit.entity.Player;

import de.bloodeko.towns.cmds.CmdBase;
import de.bloodeko.towns.town.ChunkMap;

/**
 * Handles the command. /town map
 * Undiscovered towns? Renderdistance? Playerstorage?
 */
public class MapCmd extends CmdBase {
    private MapListener clickHandler;
    
    public MapCmd(ChunkMap map, MapListener clickHandler) {
        super(map);
        this.clickHandler = clickHandler;
    }
    
    @Override
    public void execute(Player player, String[] args) {
        MapView view = MapFactory.newChunkMapView(getMap(), player);
        clickHandler.add(player.getUniqueId(), view);
        view.open();
    }
}
