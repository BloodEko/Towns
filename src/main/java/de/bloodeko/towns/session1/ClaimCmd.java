package de.bloodeko.towns.session1;

import java.util.List;

import org.bukkit.entity.Player;

import de.bloodeko.towns.session2.Town;

public class ClaimCmd extends TownCmdBase {
    
    public ClaimCmd(ChunkMap chunks) {
        super(chunks);
    }
    
    @Override
    public void execute(Player player, String[] args) {
        Town town = getMap().getTown(getChunkBehind(player));
        claim(Chunk.fromEntity(player), town, player);
    }
    
    public void claim(Chunk chunk, Town town, Player player) {
        if (getMap().getTown(chunk) != null) {
            player.sendMessage("This chunk is already claimed.");
            return;
        }
        if (town == null) {
            player.sendMessage("No town found behind you.");
            return;
        }
        if (!town.isAllowedToClaim(player)) {
            player.sendMessage("You are not allowed to claim for: " + town.getName());
            return;
        }
        player.sendMessage("Claimed " + chunk + " for " + town);
        town.expand(getMap(), chunk);
    }
    
    private Chunk getChunkBehind(Player player) {
        Yaw direc = Yaw.getReverseDirection(player.getLocation().getYaw());
        return Chunk.fromEntity(player).add(direc);
    }

    @Override
    public List<String> completeTab(String[] args) {
        return null;
    }

    
    /*
    private Chunk findNearTown(Chunk base, Player claimer) {
        for (Yaw yaw : Yaw.DIRECTIONS) {
            Chunk chunk = base.add(yaw);
            Town town = chunks.getTown(chunk);
            if (town != null && town.isAllowedToClaim(claimer)) {
                return chunk;
            }
        }
        return null;
    }
    */
}
