package de.bloodeko.towns.core.towns.ui;

import org.bukkit.entity.Player;

import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import de.bloodeko.towns.Services;
import de.bloodeko.towns.core.TownFactory;
import de.bloodeko.towns.core.townplots.ui.PlotBaseCmd;
import de.bloodeko.towns.core.towns.Town;
import de.bloodeko.towns.util.Chunk;
import de.bloodeko.towns.util.Messages;
import de.bloodeko.towns.util.ModifyException;
import de.bloodeko.towns.util.cmds.CmdBase;

public class FoundCmd extends CmdBase {
    private static final int price = 1000;
    private static final int range = 7;

    @Override
    public void execute(Player player, String[] args) {
        checkNearTowns(player, range);
        checkMoney(Services.economy(), player, price);
        
        String name = getArg(0, args, "cmds.found.needName");
        TownFactory.createTown(name, Chunk.fromEntity(player), player.getUniqueId());
        
        Services.economy().withdrawPlayer(player, price);
        Messages.say(player, "cmds.found.created", name);
    }
    
    /**
     * Throws an exception if any region prefixed with "town_" is near
     * the player and the player is no governor of that town.
     */
    private void checkNearTowns(Player player, int range) {
        for (ProtectedRegion region : getNearRegions(player, range)) {
            if (region.getId().startsWith("town_")) {
                Town town = getTown(region.getId());
                if (town.getPeople().isGovernor(player.getUniqueId())) {
                    continue;
                }
                throw new ModifyException("cmds.found.townInWay",
                  town.getName(), range);
            }
        }
    }
    
    /**
     * Returns the town for that region ID which is
     * in the format "towns_0".
     */
    private Town getTown(String regionId) {
        Integer id = Integer.valueOf(regionId.split("_")[1]);
        return Services.townservice().get(id);
    }
    
    /**
     * Returns all regions in a square range around the player.
     */
    private ApplicableRegionSet getNearRegions(Player player, int range) {
        Chunk pos = Chunk.fromEntity(player);
        return PlotBaseCmd.getRegions(player, 
          Chunk.getMinPos(pos.x - range , pos.z - range), 
          Chunk.getMaxPos(pos.x + range , pos.z + range));
    }
}
