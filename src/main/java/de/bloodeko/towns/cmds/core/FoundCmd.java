package de.bloodeko.towns.cmds.core;

import org.bukkit.entity.Player;

import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import de.bloodeko.towns.Services;
import de.bloodeko.towns.cmds.CmdBase;
import de.bloodeko.towns.town.Town;
import de.bloodeko.towns.town.settings.plots.cmds.PlotBaseCmd;
import de.bloodeko.towns.util.Chunk;
import de.bloodeko.towns.util.Messages;
import de.bloodeko.towns.util.ModifyException;

public class FoundCmd extends CmdBase {
    private static int price = 1000;
    private static int range = 7;

    @Override
    public void execute(Player player, String[] args) {
        checkNearTowns(player, range);
        checkMoney(Services.economy(), player, price);
        
        String name = getArg(0, args, "cmds.found.needName");
        Services.towns().createTown(Chunk.fromEntity(player), name, player.getUniqueId());
        
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
                  town.getSettings().getName(), range);
            }
        }
    }
    
    /**
     * Returns the town for that region id.
     */
    private Town getTown(String regionId) {
        String id = regionId.split("_")[1];
        return Services.towns().getId(id);
    }
    
    /**
     * Returns all regions in a square range arround the player.
     */
    private ApplicableRegionSet getNearRegions(Player player, int range) {
        Chunk pos = Chunk.fromEntity(player);
        return PlotBaseCmd.getRegions(player, 
          Chunk.getMinPos(pos.x - range , pos.z - range), 
          Chunk.getMaxPos(pos.x + range , pos.z + range));
    }
}
