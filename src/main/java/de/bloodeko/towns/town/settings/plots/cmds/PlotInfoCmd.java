package de.bloodeko.towns.town.settings.plots.cmds;

import java.util.Set;
import java.util.StringJoiner;
import java.util.UUID;

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
            Messages.say(player, "settings.plot.infocmd.renter", asName(plot.renter));
        }
        Messages.say(player, "settings.plot.infocmd.positions", plot.region.getMinimumPoint(), plot.region.getMaximumPoint());
        Messages.say(player, "settings.plot.infocmd.members", asNames(plot.region.getMembers().getUniqueIds()));
        Messages.say(player, "settings.plot.infocmd.rentable", plot.rentable);
        Messages.say(player, "settings.plot.infocmd.rent", plot.rent);
        Messages.say(player, "settings.plot.infocmd.debt", plot.debt);
        Messages.say(player, "settings.plot.infocmd.reservedFor", asName(plot.reversedFor));
        Messages.say(player, "settings.plot.infocmd.footer");
    }
    
    private String asName(UUID uuid) {
        if (uuid == null) {
            return "--";
        }
        return Bukkit.getOfflinePlayer(uuid).getName();
    }
    
    private String asNames(Set<UUID> uuids) {
        StringJoiner joiner = new StringJoiner(", ");
        for (UUID uuid : uuids) {
            joiner.add(Bukkit.getOfflinePlayer(uuid).getName());
        }
        return joiner.toString();
    }
}
