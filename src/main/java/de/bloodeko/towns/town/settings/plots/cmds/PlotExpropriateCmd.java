package de.bloodeko.towns.town.settings.plots.cmds;

import java.util.UUID;

import org.bukkit.entity.Player;

import de.bloodeko.towns.town.ChunkMap;
import de.bloodeko.towns.town.settings.plots.PlotData;
import de.bloodeko.towns.util.Messages;

public class PlotExpropriateCmd extends PlotBaseCmd {

    public PlotExpropriateCmd(ChunkMap map) {
        super(map);
    }

    @Override
    public void execute(Player player, String[] args) {
        PlotData plot = getPlotAsGovernor(player);
        if (plot.renter == null) {
            Messages.say(player, "settings.plot.expropriatecmd.hasNoRenter");
            return;
        }
        
        plot.rentable = true;
        plot.renter = null;
        plot.region.getMembers().clear();
        for (UUID uuid : getTownAsPlayer(player).getPeople().getGovernors()) {
            plot.region.getMembers().addPlayer(uuid);
        }
        
        Messages.say(player, "settings.plot.expropriatecmd.expropriated");
    }
}
