package de.bloodeko.towns.core.townplots.ui;

import org.bukkit.entity.Player;

import de.bloodeko.towns.core.townplots.PlotData;
import de.bloodeko.towns.core.towns.Town;
import de.bloodeko.towns.util.Messages;

public class PlotRentCmd extends PlotBaseCmd {

    @Override
    public void execute(Player player, String[] args) {
        PlotData plot = getPlotAsPlayer(player);
        Town town = getTownAsPlayer(player);
        
        if (plot.renter != null) {
            Messages.say(player, "settings.plot.rentcmd.alreadyRented");
            return;
        }
        if (!plot.rentable) {
            Messages.say(player, "settings.plot.rentcmd.notRentable");
            return;
        }
        if (plot.reversedFor != null && !plot.reversedFor.equals(player.getUniqueId())) {
            Messages.say(player, "settings.plot.rentcmd.notReservedFor");
            return;
        }
        
        payRentToTown(player, plot.rent, town);
        plot.rentable = false;
        plot.reversedFor = null;
        plot.renter = player.getUniqueId();
        plot.region.getMembers().clear();
        plot.region.getMembers().addPlayer(player.getUniqueId());
        town.addedPlayer(player.getUniqueId());
        Messages.say(player, "settings.plot.rentcmd.rented");
    }
}
