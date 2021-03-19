package de.bloodeko.towns.cmds.core;

import org.bukkit.entity.Player;

import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import de.bloodeko.towns.cmds.CmdBase;
import de.bloodeko.towns.town.ChunkMap;
import de.bloodeko.towns.town.Town;
import de.bloodeko.towns.town.TownRegistry;
import de.bloodeko.towns.town.settings.plots.cmds.PlotBaseCmd;
import de.bloodeko.towns.util.Chunk;
import de.bloodeko.towns.util.Messages;
import de.bloodeko.towns.util.ModifyException;
import net.milkbowl.vault.economy.Economy;

public class FoundCmd extends CmdBase {
    private static int price = 1000;
    private static int range = 7;
    
    private TownRegistry towns;
    private Economy economy;
    
    public FoundCmd(ChunkMap map, TownRegistry towns, Economy economy) {
        super(map);
        this.towns = towns;
        this.economy = economy;
    }

    @Override
    public void execute(Player player, String[] args) {
        checkNearTowns(player, range);
        checkMoney(economy, player, price);
        
        String name = getArg(0, args, "cmds.found.needName");
        towns.foundTown(Chunk.fromEntity(player), name, player.getUniqueId());
        economy.withdrawPlayer(player, price);
        Messages.say(player, "cmds.found.created", name);
    }
    
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
    
    private Town getTown(String regionId) {
        String id = regionId.split("_")[1];
        return towns.getId(id);
    }
    
    private ApplicableRegionSet getNearRegions(Player player, int range) {
        Chunk pos = Chunk.fromEntity(player);
        return PlotBaseCmd.getRegions(player, 
          Chunk.getMinPos(pos.x - range , pos.z - range), 
          Chunk.getMaxPos(pos.x + range , pos.z + range));
    }
}
