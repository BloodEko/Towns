package de.bloodeko.towns.town.settings.plots.cmds;

import org.bukkit.entity.Player;

import de.bloodeko.towns.town.ChunkMap;
import de.bloodeko.towns.town.settings.plots.PlotData;
import de.bloodeko.towns.util.Messages;
import net.milkbowl.vault.economy.Economy;

public class PlotRentCmd extends PlotBaseCmd {
    private Economy economy;
    
    public PlotRentCmd(ChunkMap map, Economy economy) {
        super(map);
        this.economy = economy;
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
        if (plot.reversedFor != null && !plot.reversedFor.equals(player.getUniqueId())) {
            Messages.say(player, "settings.plot.rentcmd.notReservedFor");
            return;
        }
        
        payRentToTown(economy, player, plot.rent, getTownAsPlayer(player));
        plot.rentable = false;
        plot.renter = player.getUniqueId();
        plot.region.getMembers().clear();
        plot.region.getMembers().addPlayer(player.getUniqueId());
        Messages.say(player, "settings.plot.rentcmd.rented");
    }
}
