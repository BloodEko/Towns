package de.bloodeko.towns.town.settings.plots.cmds;

import java.util.UUID;

import org.bukkit.entity.Player;

import de.bloodeko.towns.town.ChunkMap;
import de.bloodeko.towns.town.settings.plots.PlotData;
import de.bloodeko.towns.util.Messages;

public class PlotLeaveCmd extends PlotBaseCmd {

    public PlotLeaveCmd(ChunkMap map) {
        super(map);
    }

    @Override
    public void execute(Player player, String[] args) {
        PlotData plot = getPlotAsPlayer(player);
        
        if (!player.getUniqueId().equals(plot.renter)) {
            Messages.say(player, "settings.plot.leavecmd.notYourPlot");
            return;
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
