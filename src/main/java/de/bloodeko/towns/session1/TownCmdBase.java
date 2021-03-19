package de.bloodeko.towns.session1;

import java.util.List;

import org.bukkit.entity.Player;

import de.bloodeko.towns.session2.Town;

/**
 * Base for commands within the plugin.
 * Gives access to used dependencies.
 */
public abstract class TownCmdBase {
    private ChunkMap map;
    
    public TownCmdBase(ChunkMap map) {
        this.map = map;
    }
    
    public abstract void execute(Player player, String[] args);
    public abstract List<String> completeTab(String[] args);
    
    public ChunkMap getMap() {
        return map;
    }
    
    public Town getTown(Player player) {
        return map.getTown(Chunk.fromEntity(player));
    }
}
