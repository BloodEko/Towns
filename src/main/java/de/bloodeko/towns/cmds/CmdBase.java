package de.bloodeko.towns.cmds;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import de.bloodeko.towns.Services;
import de.bloodeko.towns.town.ChunkMap;
import de.bloodeko.towns.town.Town;
import de.bloodeko.towns.util.Chunk;
import de.bloodeko.towns.util.ModifyException;
import net.milkbowl.vault.economy.Economy;

/**
 * Base for commands within the plugin.
 * Gives access to used dependencies.
 */
public abstract class CmdBase {
    public abstract void execute(Player player, String[] args);

    public List<String> completeTab(String[] args) {
        return null;
    }

    public List<String> completeTab(String[] args, Player player) {
        return null;
    }
    
    public ChunkMap getMap() {
        return Services.chunkMap();
    }
    
    /**
     * Returns an online player for the the name. Otherwise throws 
     * an exception with a default message.
     */
    public Player getTarget(String name) {
        Player target = Bukkit.getPlayer(name);
        if (target == null || !target.getName().equals(name)) {
            throw new ModifyException("cmds.base.targetNotOnline", name);
        }
        return target;
    }
    
    /**
     * Returns an offline player for the name. Otherwise throws 
     * an exception with a default message.
     */
    @SuppressWarnings("deprecation")
    public OfflinePlayer getOfflineTarget(String name) {
        OfflinePlayer target = Bukkit.getOfflinePlayer(name);
        if (target == null || !target.getName().equals(name)) {
            throw new ModifyException("cmds.base.targetNotFound", name);
        }
        return target;
    }
    
    /**
     * Returns the argument at the index. If it doesn't exist throws an 
     * exception with a default message.
     */
    public String getArg(int index, String[] args) {
        return getArg(index, args, "cmds.base.indexNotFound", index);
    }
    
    /**
     * Returns the argument at the index. If it doesn't exist throws an 
     * exception with the message.
     */
    public String getArg(int index, String[] args, String message, Object... vargs) {
        if (!hasArg(index, args)) {
            throw new ModifyException(message, vargs);
        }
        return args[index];
    }
    
    /**
     * Returns whether the index exits in the array.
     */
    public boolean hasArg(int index, String[] args) {
        return args.length > index;
    }
    
    /**
     * Checks if the player has enough money.
     * Throws an exception otherwise.
     */
    public void checkMoney(Economy economy, Player player, double price) {
        if (economy.getBalance(player) < price) {
            throw new ModifyException("cmds.base.notEnoughMoney", 
              price, economy.currencyNamePlural());
        }
    }
    
    /**
     * Gets the town at the current location with permission checks.
     * Otherwise throws an exception.
     */
    public Town getTown(Player player) {
        Town town = getTownAsPlayer(player);
        if (!town.getPeople().isGovernor(player.getUniqueId())) {
            throw new ModifyException("cmds.base.noEditPermission");
        }
        return town;
    }
    
    /**
     * Gets the town at the current location without permission checks.
     * Throws an exception, if no Town is found.
     */
    public Town getTownAsPlayer(Player player) {
        Town town = getMap().getTown(Chunk.fromEntity(player));
        if (town == null) {
            throw new ModifyException("cmds.base.noTownAtLocation");
        }
        return town;
    }
    
    /**
     * Gets the town at the current location without permission checks.
     * Throws an exception, if no Town is found.
     */
    public Town getTownAsMod(Player player) {
        Town town = getTownAsPlayer(player);
        if (!player.hasPermission("towns.mod")) {
            throw new ModifyException("cmds.base.noModPermission");
        }
        return town;
    }
}
