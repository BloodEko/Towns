package de.bloodeko.towns.town.settings.plots.cmds;

import org.bukkit.entity.Player;

import de.bloodeko.towns.town.ChunkMap;
import de.bloodeko.towns.town.settings.plots.PlotData;
import de.bloodeko.towns.util.Messages;

public class PlotReserveCmd extends PlotBaseCmd {
    private final String off = "!";

    public PlotReserveCmd(ChunkMap map) {
        super(map);
    }

    @Override
    public void execute(Player player, String[] args) {
        PlotData plot = getPlotAsGovernor(player);
        String name = getArg(0, args);
        
        if (name.equals(off)) {
            plot.reversedFor = null;
            Messages.say(player, "settings.plot.reservecmd.unreserved");
        } else {
            Player target = getTarget(name);
            plot.reversedFor = target.getUniqueId();
            Messages.say(player, "settings.plot.reservecmd.reserved", target.getName());
        }
    }
}
