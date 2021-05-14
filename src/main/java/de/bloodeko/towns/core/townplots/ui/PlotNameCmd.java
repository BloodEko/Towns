package de.bloodeko.towns.core.townplots.ui;

import java.util.Arrays;
import java.util.List;

import org.bukkit.entity.Player;

import de.bloodeko.towns.core.townplots.PlotData;
import de.bloodeko.towns.util.Messages;
import de.bloodeko.towns.util.Util;

public class PlotNameCmd extends PlotBaseCmd {
    private final String off = "!";

    @Override
    public void execute(Player player, String[] args) {
        PlotData plot = getPlotAsGovernor(player);
        String name = getArg(0, args);
        
        if (name.equals(off)) {
            plot.name = null;
            Messages.say(player, "settings.plot.namecmd.removed", name);
        } else {
            Util.isValidName(name);
            plot.name = name;
            Messages.say(player, "settings.plot.namecmd.set", name);
        }
    }
    
    @Override
    public List<String> completeTab(String[] args) {
        return Util.filterList(Arrays.asList(off), args[0]);
    }
}
