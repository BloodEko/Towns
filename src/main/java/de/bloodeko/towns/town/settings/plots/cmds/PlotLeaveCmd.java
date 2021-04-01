package de.bloodeko.towns.town.settings.plots.cmds;

import java.util.UUID;

import org.bukkit.entity.Player;

import de.bloodeko.towns.Services;
import de.bloodeko.towns.town.settings.plots.PlotData;
import de.bloodeko.towns.util.Messages;

public class PlotLeaveCmd extends PlotBaseCmd {
    private int maxDepthRatio = 100;
    
    @Override
    public void execute(Player player, String[] args) {
        PlotData plot = getPlotAsPlayer(player);
        
        if (!player.getUniqueId().equals(plot.renter)) {
            Messages.say(player, "settings.plot.leavecmd.notYourPlot");
            return;
        }
        
        if (plot.debt > 0) {
            if (plot.debt < (plot.rent * maxDepthRatio)) {
                payRentToTown(Services.economy(), player, plot.debt, getTownAsPlayer(player));
            }
            plot.debt = 0;
        }
        plot.rentable = true;
        plot.renter = null;
        plot.region.getMembers().clear();
        for (UUID uuid : getTownAsPlayer(player).getPeople().getGovernors()) {
            plot.region.getMembers().addPlayer(uuid);
        }
        
        Messages.say(player, "settings.plot.leavecmd.leave");
    }
}
