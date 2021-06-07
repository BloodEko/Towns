package de.bloodeko.towns.core.townplots.ui;

import java.util.UUID;

import org.bukkit.entity.Player;

import de.bloodeko.towns.core.townplots.PlotData;
import de.bloodeko.towns.core.towns.Town;
import de.bloodeko.towns.util.Messages;

public class PlotExpropriateCmd extends PlotBaseCmd {

    @Override
    public void execute(Player player, String[] args) {
        PlotData plot = getPlotAsGovernor(player);
        Town town = getTown(player);
        
        if (plot.renter == null) {
            Messages.say(player, "settings.plot.expropriatecmd.hasNoRenter");
            return;
        }
        if (getMinDebt(plot) > plot.debt) {
            Messages.say(player, "settings.plot.expropriatecmd.notEnoughDebt");
            return;
        }
        
        UUID renter = plot.renter;
        plot.debt = 0;
        plot.rentable = true;
        plot.renter = null;
        plot.region.getMembers().clear();
        for (UUID uuid : getTownAsPlayer(player).getPeople().getGovernors()) {
            plot.region.getMembers().addPlayer(uuid);
        }
        town.removedPlayer(renter);
        Messages.say(player, "settings.plot.expropriatecmd.expropriated");
    }
    
    /**
     * Returns the minimum depth required for a
     * plot to be expropriated.
     */
    static int getMinDebt(PlotData plot) {
        return plot.rent * 5;
    }
}
