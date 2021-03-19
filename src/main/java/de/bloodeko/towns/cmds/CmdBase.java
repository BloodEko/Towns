package de.bloodeko.towns.cmds;

import java.util.List;

import org.bukkit.entity.Player;

import de.bloodeko.towns.town.ChunkMap;
import de.bloodeko.towns.town.Town;
import de.bloodeko.towns.util.Chunk;
import de.bloodeko.towns.util.ModifyException;

/**
 * Base for commands within the plugin.
 * Gives access to used dependencies.
 */
public abstract class CmdBase {
    private ChunkMap map;
    
    public CmdBase(ChunkMap map) {
        this.map = map;
    }
    
    public abstract void execute(Player player, String[] args);

    public List<String> completeTab(String[] args) {
        return null;
    }
    
    public ChunkMap getMap() {
        return map;
    }
    
    /**
     * Gets the town at the players current location. Throws an exception 
     * if none is found, or the player can't modify there.
     */
    public Town getTown(Player player) {
        Town town = getTownAsPlayer(player);
        if (town.getOwner() != player.getUniqueId()) {
            throw new ModifyException("You have no right to modify that town.");
        }
        return town;
    }
    
    public Town getTownAsPlayer(Player player) {
        Town town = map.query(Chunk.fromEntity(player));
        if (town == null) {
            throw new ModifyException("There is no town at your location.");
        }
        return town;
    }
}
