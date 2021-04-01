package de.bloodeko.towns.town.settings.plots.cmds;

import java.util.UUID;

import org.bukkit.entity.Player;

import de.bloodeko.towns.town.settings.plots.PlotData;
import de.bloodeko.towns.util.Messages;

public class PlotExpropriateCmd extends PlotBaseCmd {

    @Override
    public void execute(Player player, String[] args) {
        PlotData plot = getPlotAsGovernor(player);
        if (plot.renter == null) {
            Messages.say(player, "settings.plot.expropriatecmd.hasNoRenter");
            return;
        }
        if (getMinDebt(plot) < plot.debt) {
            Messages.say(player, "settings.plot.expropriatecmd.notEnoughDebt");
            return;
        }
        
        plot.debt = 0;
        plot.rentable = true;
        plot.renter = null;
        plot.region.getMembers().clear();
        for (UUID uuid : getTownAsPlayer(player).getPeople().getGovernors()) {
            plot.region.getMembers().addPlayer(uuid);
        }
        
        Messages.say(player, "settings.plot.expropriatecmd.expropriated");
    }
    
    private int getMinDebt(PlotData plot) {
        return plot.rent * 5;
    }
}
