package de.bloodeko.towns.town.settings.plots.cmds;

import org.bukkit.entity.Player;

import de.bloodeko.towns.town.ChunkMap;
import de.bloodeko.towns.town.settings.plots.PlotData;
import de.bloodeko.towns.util.Messages;
import de.bloodeko.towns.util.ModifyException;

public class PlotRentoutCmd extends PlotBaseCmd {

    public PlotRentoutCmd(ChunkMap map) {
        super(map);
    }

    @Override
    public void execute(Player player, String[] args) {
        int rent = getIntArg(0, args);
        checkIsPositive(rent);
        
        PlotData plot = getPlotAsGovernor(player);
        if (plot.renter != null) {
            Messages.say(player, "settings.plot.rentoutcmd.alreadyRented");
            return;
        }

        plot.rentable = true;
        plot.rent = rent;
        Messages.say(player, "settings.plot.rentoutcmd.set", rent);
    }
    
    public int getIntArg(int index, String[] args) {
        try {
            return Integer.valueOf(getArg(index, args));
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
