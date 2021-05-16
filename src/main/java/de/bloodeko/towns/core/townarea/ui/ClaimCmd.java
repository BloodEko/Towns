package de.bloodeko.towns.core.townarea.ui;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import de.bloodeko.towns.Services;
import de.bloodeko.towns.core.towns.legacy.Town;
import de.bloodeko.towns.util.Chunk;
import de.bloodeko.towns.util.Messages;
import de.bloodeko.towns.util.ModifyException;
import de.bloodeko.towns.util.Yaw;
import de.bloodeko.towns.util.cmds.CmdBase;

public class ClaimCmd extends CmdBase {
    private static int price = 512;
    
    @Override
    public void execute(Player player, String[] args) {
        checkMoney(Services.economy(), player, price);
        if (getMap().getTown(Chunk.fromEntity(player)) != null) {
            Messages.say(player, "town.townarea.alreadyTaken");
            return;
        }
        
        if (args.length == 0) {
            claim(player);
        } else {
            claim(player, args[0]);
        }
        
        Services.economy().withdrawPlayer(player, price);
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
        throw new ModifyException("cmds.claim.townNameNotNear");
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