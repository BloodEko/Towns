package de.bloodeko.towns.town.settings.plots.cmds;

import org.bukkit.entity.Player;

import de.bloodeko.towns.town.ChunkMap;
import de.bloodeko.towns.town.settings.plots.PlotData;
import de.bloodeko.towns.util.Messages;

public class PlotRentCmd extends PlotBaseCmd {

    public PlotRentCmd(ChunkMap map) {
        super(map);
    }

    @Override
    public void execute(Player player, String[] args) {
        PlotData plot = getPlotAsPlayer(player);
        
        if (plot.renter != null) {
            Messages.say(player, "settings.plot.rentcmd.alreadyRented");
            return;
        }
        if (!plot.rentable) {
            Messages.say(player, "settings.plot.rentcmd.notRentable");
            return;
        }
        if (plot.reversedFor != null && plot.reversedFor != player.getUniqueId()) {
            Messages.say(player, "settings.plot.rentcmd.notReservedFor");
            return;
        }
        
        plot.rentable = false;
        plot.renter = player.getUniqueId();
        plot.region.getMembers().addPlayer(player.getUniqueId());
        
        Messages.say(player, "settings.plot.rentcmd.rented");
    }
}
