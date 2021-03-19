package de.bloodeko.towns.town.settings.plots.cmds;

import org.bukkit.entity.Player;

import de.bloodeko.towns.town.ChunkMap;
import de.bloodeko.towns.town.settings.plots.PlotData;
import de.bloodeko.towns.util.Messages;
import de.bloodeko.towns.util.Util;

public class PlotNameCmd extends PlotBaseCmd {

    public PlotNameCmd(ChunkMap map) {
        super(map);
    }

    @Override
    public void execute(Player player, String[] args) {
        PlotData plot = getPlotAsGovernor(player);
        String name = getArg(0, args);
        
        if (name.equals("!")) {
            plot.name = null;
            Messages.say(player, "settings.plot.namecmd.removed", name);
        } else {
            Util.isValidName(name);
            plot.name = name;
            Messages.say(player, "settings.plot.namecmd.set", name);
        }
    }
}
