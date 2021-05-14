package de.bloodeko.towns.core.townplots.ui;

import org.bukkit.entity.Player;

import de.bloodeko.towns.core.townplots.PlotData;
import de.bloodeko.towns.core.townplots.PlotHandler;
import de.bloodeko.towns.util.Messages;

public class PlotRemoveCmd extends PlotBaseCmd {

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
