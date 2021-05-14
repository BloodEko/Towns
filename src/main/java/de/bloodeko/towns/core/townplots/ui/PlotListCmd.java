package de.bloodeko.towns.core.townplots.ui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.math.BlockVector3;

import de.bloodeko.towns.core.townplots.PlotData;
import de.bloodeko.towns.core.townplots.PlotHandler;
import de.bloodeko.towns.core.towns.ui.InfoCmd;
import de.bloodeko.towns.util.Messages;
import net.md_5.bungee.api.chat.TextComponent;

public class PlotListCmd extends PlotBaseCmd {

    @Override
    public void execute(Player player, String[] args) {
        PlotHandler handler = getPlotHandler(getTown(player));
        
        if (handler.plots.isEmpty()) {
            Messages.say(player, "settings.plot.listcmd.noPlotsDefined");
            return;
        }
        Messages.say(player, "settings.plot.listcmd.header");
        for (PlotData data : handler.getPlots()) {
            BlockVector3 min = data.region.getMinimumPoint();
            BlockVector3 max = data.region.getMaximumPoint();
            
            String bounds = PlotInfoCmd.getBounds(min, max);
            String hover = PlotInfoCmd.getHover(min, max);
            String tp = PlotInfoCmd.getTp(min);
            
            String message = Messages.get("settings.plot.listcmd.line", 
              data.id, bounds, getNote(data), getState(data));
            
            TextComponent comp = PlotInfoCmd.createHover(message, hover, tp);
            player.spigot().sendMessage(comp);
        }
    }
    
    private String getNote(PlotData plot) {
        if (plot.name == null) return "";
        return Messages.get("settings.plot.listcmd.note", plot.name);
    }
    
    private String getState(PlotData plot) {
        if (plot.renter == null) {
            return "";
        }
        String name = InfoCmd.getName(Bukkit.getOfflinePlayer(plot.renter));
        if (plot.debt > 0) {
            if (plot.debt >= plot.rent * 5) {
                return Messages.get("settings.plot.listcmd.kickNote", name);
            } else {
                return Messages.get("settings.plot.listcmd.debtNote", name);
            }
        } else {
            return Messages.get("settings.plot.listcmd.rentedNote", name);
        }
    }
}
