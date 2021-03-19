package de.bloodeko.towns.town.settings.plots.cmds;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import de.bloodeko.towns.town.ChunkMap;
import de.bloodeko.towns.town.settings.plots.PlotData;
import de.bloodeko.towns.util.Messages;

public class PlotInfoCmd extends PlotBaseCmd {
    
    public PlotInfoCmd(ChunkMap map) {
        super(map);
    }

    @Override
    public void execute(Player player, String[] args) {
        PlotData plot = getPlotAsPlayer(player);
        Messages.say(player, "settings.plot.infocmd.header", plot.id);
        if (plot.name != null) {
            Messages.say(player, "settings.plot.infocmd.name", plot.name);
        }
        if (plot.renter != null) {
            Messages.say(player, "settings.plot.infocmd.renter", Bukkit.getPlayer(plot.renter).getName());
        }
        Messages.say(player, "settings.plot.infocmd.positions", plot.region.getMinimumPoint(), plot.region.getMaximumPoint());
        Messages.say(player, "settings.plot.infocmd.members", plot.region.getMembers().getUniqueIds());
        Messages.say(player, "settings.plot.infocmd.rentable", plot.rentable);
        Messages.say(player, "settings.plot.infocmd.rent", plot.rent);
        Messages.say(player, "settings.plot.infocmd.reservedFor", plot.reversedFor);
        Messages.say(player, "settings.plot.infocmd.footer");
    }
}
