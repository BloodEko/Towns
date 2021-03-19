package de.bloodeko.towns.cmds.general;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import de.bloodeko.towns.cmds.CmdBase;
import de.bloodeko.towns.town.ChunkMap;
import de.bloodeko.towns.town.Town;
import de.bloodeko.towns.util.Chunk;
import de.bloodeko.towns.util.Messages;
import de.bloodeko.towns.util.ModifyException;
import de.bloodeko.towns.util.Yaw;

// TODO. there is a bug after server restart
//  that no near towns are found. (add sysout of chunkmap/list.)
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
        Messages.say(player, "cmds.claim.claimedChunk");
    }
    
    private void claim(Player player) {
        Town town = getTown(getNearTowns(player));
        town.getArea().expand(getMap(), town, Chunk.fromEntity(player));
    }
    
    private void claim(Player player, String name) {
        Chunk chunk = Chunk.fromEntity(player);
        Town town = getTown(getNearTowns(player), name);
        town.getArea().expand(getMap(), town, chunk);
    }
    
    private Town getTown(List<Town> list, String name) {
        for (Town town : list) {
            if (town.getSettings().getName().equalsIgnoreCase(name)) {
                return town;
            }
        }
        throw new ModifyException("cmds.claim.townNameNotFound");
    }
    
    private Town getTown(List<Town> list) {
        if (list.size() == 0) {
            throw new ModifyException("cmds.claim.noNearTown");
        }
        Town first = list.get(0);
        for (Town town : list) {
            if (town != first) {
                throw new ModifyException("cmds.claim.cmdUsage");
            }
        }
        return first;
    }
    
    private List<Town> getNearTowns(Player player) {
        List<Town> list = new ArrayList<>();
        for (Yaw yaw : Yaw.DIRECTIONS) {
            Chunk chunk = Chunk.fromEntity(player);
            Town town = getMap().getTown(chunk.add(yaw));
            if (town != null && town.getPeople().isGovernor(player.getUniqueId())) {
                list.add(town);
            }
        }
        return list;
    }
    
    @Override
    public List<String> completeTab(String[] args, Player player) {
        if (args.length == 0 || getMap().hasTown(Chunk.fromEntity(player))) {
            return null;
        }
        List<String> list = new ArrayList<>();
        for (Town town : getNearTowns(player)) {
            String name = town.getSettings().getName();
            if (name.startsWith(args[0])) {
                list.add(name);
            }
        }
        return list;
    }
}
