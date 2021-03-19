package de.bloodeko.towns.cmds.general;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import de.bloodeko.towns.cmds.CmdBase;
import de.bloodeko.towns.town.ChunkMap;
import de.bloodeko.towns.town.Town;
import de.bloodeko.towns.util.Chunk;
import de.bloodeko.towns.util.ModifyException;
import de.bloodeko.towns.util.Yaw;

public class ClaimCmd extends CmdBase {
    
    public ClaimCmd(ChunkMap chunks) {
        super(chunks);
    }
    
    @Override
    public void execute(Player player, String[] args) {
        if (args.length == 0) {
            claim(player);
        } else {
            claim(player, args[0]);
        }
        player.sendMessage("Claimed chunk!");
    }
    
    private void claim(Player player) {
        Chunk chunk = Chunk.fromEntity(player);
        Town town = getTown(getNearTowns(chunk, player));
        town.getArea().expand(getMap(), town, chunk);
    }
    
    private void claim(Player player, String name) {
        Chunk chunk = Chunk.fromEntity(player);
        Town town = getTown(getNearTowns(chunk, player), name);
        town.getArea().expand(getMap(), town, chunk);
    }
    
    private Town getTown(List<Town> list, String name) {
        for (Town town : list) {
            if (town.getName().equalsIgnoreCase(name)) {
                return town;
            }
        }
        throw new ModifyException("No town found with that name.");
    }
    
    private Town getTown(List<Town> list) {
        if (list.size() == 0) {
            throw new ModifyException("No near town found.");
        }
        Town first = list.get(0);
        for (Town town : list) {
            if (town != first) {
                throw new ModifyException("Multiple towns found. /town claim <name>");
            }
        }
        return first;
    }
    
    private List<Town> getNearTowns(Chunk chunk, Player player) {
        List<Town> list = new ArrayList<>();
        for (Yaw yaw : Yaw.DIRECTIONS) {
            Town town = getMap().query(chunk.add(yaw));
            if (town != null && town.isOwner(player)) {
                list.add(town);
            }
        }
        return list;
    }
    
    /*
    Town town = getMap().getTown(getChunkBehind(player));
    claim(Chunk.fromEntity(player), town, player);
        
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
    */

    
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
