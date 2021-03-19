package de.bloodeko.towns.town.settings.plots.cmds;

import org.bukkit.entity.Player;

import de.bloodeko.towns.town.ChunkMap;
import de.bloodeko.towns.town.settings.plots.PlotData;
import de.bloodeko.towns.town.settings.plots.PlotTownHandler;
import de.bloodeko.towns.util.Messages;

public class PlotListCmd extends PlotBaseCmd {
    
    public PlotListCmd(ChunkMap map) {
        super(map);
    }

    @Override
    public void execute(Player player, String[] args) {
        PlotTownHandler handler = getPlotHandler(getTown(player));
        
        if (handler.plots.isEmpty()) {
            Messages.say(player, "settings.plot.listcmd.noPlotsDefined");
            return;
        }
        Messages.say(player, "settings.plot.listcmd.header");
        for (PlotData data : handler.getPlots()) {
            String suffix = "";
            if (data.name != null) {
                suffix = Messages.get("settings.plot.listcmd.nameSuffix", data.name);
            }
            
            Messages.say(player, "settings.plot.listcmd.plot", data.id, 
              data.region.getMinimumPoint(), data.region.getMaximumPoint(), 
              suffix);
        }
    }
}
