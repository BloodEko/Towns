package de.bloodeko.towns.town.settings.plots.cmds;

import org.bukkit.entity.Player;

import de.bloodeko.towns.town.ChunkMap;
import de.bloodeko.towns.town.settings.plots.PlotData;
import de.bloodeko.towns.town.settings.plots.PlotHandler;
import de.bloodeko.towns.util.Messages;

public class PlotRemoveCmd extends PlotBaseCmd {

    public PlotRemoveCmd(ChunkMap map) {
        super(map);
    }

    @Override
    public void execute(Player player, String[] args) {
        PlotHandler handler = getPlotHandler(getTown(player));
        PlotData plot = getPlotAsGovernor(player);
        
        if (plot.renter != null) {
            Messages.say(player, "settings.plot.removecmd.plotHasRenter", plot.id);
            return;
        }
        
        handler.removePlot(plot.id, getRegionManager());
        Messages.say(player, "settings.plot.removecmd.remove", plot.id);
    }
}
