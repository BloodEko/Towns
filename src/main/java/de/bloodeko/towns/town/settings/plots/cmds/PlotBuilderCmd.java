package de.bloodeko.towns.town.settings.plots.cmds;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import de.bloodeko.towns.town.settings.plots.PlotData;
import de.bloodeko.towns.util.Messages;

public class PlotBuilderCmd extends PlotBaseCmd {

    @Override
    public void execute(Player player, String[] args) {
        String name = getArg(0, args);
        PlotData plot = getPlotAsRenter(player);
        
        if (hasArg(1, args)) {
            OfflinePlayer target = getOfflineTarget(name);
            plot.region.getMembers().removePlayer(target.getUniqueId());
            Messages.say(player, "settings.plot.buildercmd.removedPlayer", target.getName());
        }
        else {
            Player target = getTarget(name);
            plot.region.getMembers().addPlayer(target.getUniqueId());
            Messages.say(player, "settings.plot.buildercmd.addedPlayer", target.getName());
        }
    }
}
