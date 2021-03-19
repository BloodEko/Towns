package de.bloodeko.towns.session1;

import java.util.List;

import org.bukkit.entity.Player;

import de.bloodeko.towns.session2.Town;

public class UnclaimCmd extends TownCmdBase {
    
    public UnclaimCmd(ChunkMap map) {
        super(map);
    }
    
    @Override
    public void execute(Player player, String[] args) {
        Chunk chunk = Chunk.fromEntity(player);
        unclaim(chunk, player);
    }
    
    public void unclaim(Chunk chunk, Player player) {
        Town town = getMap().getTown(chunk);
        if (town == null) {
            player.sendMessage("This chunk is not claimed.");
            return;
        }
        if (!town.isAllowedToClaim(player)) {
            player.sendMessage("You are not allowed to unclaim here.");
            return;
        }
        player.sendMessage("Unclaimed " + chunk + " for " + town);
        town.contract(getMap(), chunk);
    }

    @Override
    public List<String> completeTab(String[] args) {
        return null;
    }

}
