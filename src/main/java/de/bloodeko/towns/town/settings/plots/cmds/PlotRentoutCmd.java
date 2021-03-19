package de.bloodeko.towns.town.settings.plots.cmds;

import java.util.Arrays;
import java.util.List;

import org.bukkit.entity.Player;

import de.bloodeko.towns.town.ChunkMap;
import de.bloodeko.towns.town.settings.plots.PlotData;
import de.bloodeko.towns.util.Messages;
import de.bloodeko.towns.util.ModifyException;
import de.bloodeko.towns.util.Util;

public class PlotRentoutCmd extends PlotBaseCmd {
    private final String off = "!";

    public PlotRentoutCmd(ChunkMap map) {
        super(map);
    }

    @Override
    public void execute(Player player, String[] args) {
        PlotData plot = getPlotAsGovernor(player);
        if (plot.renter != null) {
            Messages.say(player, "settings.plot.rentoutcmd.alreadyRented");
            return;
        }
        
        String arg0 = getArg(0, args);
        if (arg0.equals(off)) {
            plot.rentable = false;
            Messages.say(player, "settings.plot.rentoutcmd.unset");
        } else {
            int rent = getInt(arg0);
            checkIsPositive(rent);
            plot.rent = rent;
            plot.rentable = true;
            Messages.say(player, "settings.plot.rentoutcmd.set", rent);
        }
    }
    
    @Override
    public List<String> completeTab(String[] args) {
        return Util.filterList(Arrays.asList(off), args[0]);
    }
    
    public int getInt(String arg) {
        try {
            return Integer.valueOf(arg);
        } catch (NumberFormatException ex) {
            throw new ModifyException("settings.plot.rentoutcmd.wrongInt");
        }
    }
    
    public void checkIsPositive(int val) {
        if (val < 0) {
            throw new ModifyException("settings.plot.rentoutcmd.negativeInt");
        }
    }
}
