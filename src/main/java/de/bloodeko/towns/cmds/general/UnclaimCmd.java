package de.bloodeko.towns.cmds.general;

import org.bukkit.entity.Player;

import de.bloodeko.towns.cmds.CmdBase;
import de.bloodeko.towns.town.ChunkMap;
import de.bloodeko.towns.town.Town;
import de.bloodeko.towns.util.Chunk;

public class UnclaimCmd extends CmdBase {
    
    public UnclaimCmd(ChunkMap map) {
        super(map);
    }
    
    @Override
    public void execute(Player player, String[] args) {
        Chunk chunk = Chunk.fromEntity(player);
        unclaim(chunk, player);
    }
    
    public void unclaim(Chunk chunk, Player player) {
        Town town = getMap().query(chunk);
        if (town == null) {
            player.sendMessage("This chunk is not claimed.");
            return;
        }
        if (!town.isOwner(player)) {
            player.sendMessage("You are not allowed to unclaim here.");
            return;
        }
        town.getArea().contract(getMap(), chunk, town.getName());
        player.sendMessage("Unclaimed " + chunk + " for " + town);
    }
}
