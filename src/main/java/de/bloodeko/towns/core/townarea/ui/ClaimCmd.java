package de.bloodeko.towns.core.townarea.ui;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import de.bloodeko.towns.Services;
import de.bloodeko.towns.core.towns.Town;
import de.bloodeko.towns.util.Chunk;
import de.bloodeko.towns.util.DoubleCheck;
import de.bloodeko.towns.util.Messages;
import de.bloodeko.towns.util.ModifyException;
import de.bloodeko.towns.util.Yaw;
import de.bloodeko.towns.util.cmds.CmdBase;

/**
 * Allows governors of towns to increase their land.
 * Updates the area with the newly claimed chunk.
 */
public class ClaimCmd extends CmdBase {
    private static final int PRICE = 512;
    private static final ClaimRules RULES = new ClaimRules();
    private final DoubleCheck check = new DoubleCheck(60000);
    
    @Override
    public void execute(Player player, String[] args) {
        checkMoney(Services.economy(), player, PRICE);
        getMap().verify(Chunk.fromEntity(player));
        
        if (!check.passExtending(player.getUniqueId())) {
            String currency = Services.economy().currencyNamePlural();
            Messages.say(player, "cmds.claim.verfiyBuy", PRICE, currency);
            Messages.say(player, "cmds.claim.confirmBuy", check.toSeconds());
            return;
        }
        
        if (args.length == 0) {
            Town town = combine(getNearTowns(player));
            claim(town, Chunk.fromEntity(player));
        } else {
            Town town = getFirstMatch(getNearTowns(player), args[0]);
            claim(town, Chunk.fromEntity(player));
        }
        
        Services.economy().withdrawPlayer(player, PRICE);
        Messages.say(player, "cmds.claim.claimedChunk");
    }
    
    /**
     * Claims the chunk for the town. Throws an exception if
     * the ChunkRules for expansion don't match.
     */
    private void claim(Town town, Chunk chunk) {
        RULES.checkExpand(getMap(), town, town.getArea(), chunk);
        Services.chunkservice().set(chunk, town.getId());
    }
    
    /**
     * Returns the town of the list, which matches.
     * Throws an exception if no name matches.
     */
    private Town getFirstMatch(List<Town> list, String name) {
        for (Town town : list) {
            if (town.getName().equalsIgnoreCase(name)) {
                return town;
            }
        }
        throw new ModifyException("cmds.claim.townNameNotNear");
    }
    
    /**
     * Throws an exception if no town is in the list.
     * Throws an exception if different towns are in the list.
     * Returns the town in the list.
     */
    private Town combine(List<Town> list) {
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
    
    /**
     * Returns a list of all 4 surrounding chunks, 
     * where the player is also a governor.
     */
    private List<Town> getNearTowns(Player player) {
        List<Town> list = new ArrayList<>();
        Chunk chunk = Chunk.fromEntity(player);
        for (Yaw yaw : Yaw.DIRECTIONS) {
            Town town = getMap().getTown(chunk.add(yaw));
            if (town != null && town.getPeople().isGovernor(player.getUniqueId())) {
                list.add(town);
            }
        }
        return list;
    }
    
    @Override
    public List<String> completeTab(String[] args, Player player) {
        if (args.length == 0 || getMap().has(Chunk.fromEntity(player))) {
            return null;
        }
        List<String> list = new ArrayList<>();
        for (Town town : getNearTowns(player)) {
            if (town.getName().startsWith(args[0])) {
                list.add(town.getName());
            }
        }
        return list;
    }
}
