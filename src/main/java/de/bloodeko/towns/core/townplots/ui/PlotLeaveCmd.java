package de.bloodeko.towns.core.townplots.ui;

import java.util.UUID;

import org.bukkit.entity.Player;

import de.bloodeko.towns.core.townplots.PlotData;
import de.bloodeko.towns.core.towns.Town;
import de.bloodeko.towns.util.Messages;

public class PlotLeaveCmd extends PlotBaseCmd {
    private int maxDepthRatio = 100;
    
    @Override
    public void execute(Player player, String[] args) {
        PlotData plot = getPlotAsPlayer(player);
        Town town = getTownAsPlayer(player);
        
        if (!player.getUniqueId().equals(plot.renter)) {
            Messages.say(player, "settings.plot.leavecmd.notYourPlot");
            return;
        }
        
        if (plot.debt > 0) {
            if (plot.debt < (plot.rent * maxDepthRatio)) {
                payRentToTown(player, plot.debt, town);
            }
            plot.debt = 0;
        }
        plot.rentable = true;
        plot.renter = null;
        plot.region.getMembers().clear();
        for (UUID uuid : town.getPeople().getGovernors()) {
            plot.region.getMembers().addPlayer(uuid);
        }
        town.removedPlayer(player.getUniqueId());
        Messages.say(player, "settings.plot.leavecmd.leave");
    }
}
