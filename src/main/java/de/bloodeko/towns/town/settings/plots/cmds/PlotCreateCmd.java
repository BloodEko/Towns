package de.bloodeko.towns.town.settings.plots.cmds;

import org.bukkit.entity.Player;

import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import de.bloodeko.towns.town.ChunkMap;
import de.bloodeko.towns.town.Town;
import de.bloodeko.towns.town.settings.plots.PlotHandler;
import de.bloodeko.towns.util.Chunk;
import de.bloodeko.towns.util.Messages;
import de.bloodeko.towns.util.ModifyException;

public class PlotCreateCmd extends PlotBaseCmd {
    
    public PlotCreateCmd(ChunkMap map) {
        super(map);
    }

    @Override
    public void execute(Player player, String[] args) {
        Region region = getSelection(player);
        BlockVector3 pos1 = region.getMinimumPoint();
        BlockVector3 pos2 = region.getMaximumPoint();
        checkIsComplete(pos1, pos2);
        
        Chunk a = Chunk.fromCoords(pos1.getX(), pos1.getZ());
        Chunk b = Chunk.fromCoords(pos2.getX(), pos2.getZ());
        Town ta = getMap().getTown(a);
        Town tb = getMap().getTown(b);
        
        if (ta == null) {
            throw new ModifyException("settings.plot.createcmd.outsideOfTown");
        }
        if (ta != tb) {
            throw new ModifyException("settings.plot.createcmd.notSameTown");
        }
        if (!ta.getPeople().isGovernor(player.getUniqueId())) {
            throw new ModifyException("town.townpeople.youAreNoGovernor");
        }
        for (ProtectedRegion rg : getRegions(player, pos1, pos2)) {
            if (rg.getId().startsWith("plot_")) {
                throw new ModifyException("settings.plot.createcmd.plotInTheWay");
            }
        }
        for (Chunk chunk : Chunk.getChunksWithin(a, b)) {
            if (ta != getMap().getTown(chunk)) {
                throw new ModifyException("settings.plot.createcmd.notFullyCoversTown");
            }
        }
        PlotHandler handler = getPlotHandler(ta);
        int size = handler.plots.size();
        int max = getMaxPlots(ta);
        if (size == max) {
            throw new ModifyException("settings.plot.createcmd.maxPlots", size, max);
        }
        getPlotHandler(ta).addPlot(ta.getId(), pos1, pos2, getRegionManager());
        Messages.say(player, "settings.plot.createcmd.created");
    }
    
    public int getMaxPlots(Town town) {
        return town.getSettings().getStage() * 2;
    }
}
