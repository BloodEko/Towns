package de.bloodeko.towns.town.settings.plots.cmds;

import java.util.HashSet;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.math.BlockVector3;

import de.bloodeko.towns.cmds.core.InfoCmd;
import de.bloodeko.towns.town.settings.plots.PlotData;
import de.bloodeko.towns.util.Messages;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.HoverEvent.Action;
import net.md_5.bungee.api.chat.TextComponent;

public class PlotInfoCmd extends PlotBaseCmd {

    @Override
    public void execute(Player player, String[] args) {
        PlotData plot = getPlotAsPlayer(player);
        PlotDisplay display = new PlotDisplay(player, plot);
        display.show();
    }
    
    private static class PlotDisplay {
        private Player player;
        private PlotData plot;
        
        public PlotDisplay(Player player, PlotData plot) {
            this.player = player;
            this.plot = plot;
        }

        public void show() {
            header();
            bounds();
            rentstate();
            renter();
            reserved();
            members();
            rent();
            debt();
        }
        
        private void header() {
            Messages.say(player, "settings.plot.infocmd.header");
            if (plot.name == null) {
                Messages.say(player, "settings.plot.infocmd.id", plot.id);
            } else {
                Messages.say(player, "settings.plot.infocmd.note", plot.id, plot.name);
            }
        }
        
        private void bounds() {
            BlockVector3 min = plot.region.getMinimumPoint();
            BlockVector3 max = plot.region.getMaximumPoint();
            String message = Messages.get("settings.plot.infocmd.size", getBounds(min, max));
            TextComponent comp = createHover(message, getHover(min, max), getTp(min));
            player.spigot().sendMessage(comp);
        }
        
        private void rentstate() {
            if (plot.renter != null) {
                Messages.say(player, "settings.plot.infocmd.stateRented");
            } else if (plot.rentable) {
                Messages.say(player, "settings.plot.infocmd.stateFree");
            } else {
                Messages.say(player, "settings.plot.infocmd.stateUnrentable");
            }
        }
        
        private void renter() {
            if (plot.renter != null) {
                String name = InfoCmd.getOnlineName(Bukkit.getOfflinePlayer(plot.renter));
                Messages.say(player, "settings.plot.infocmd.renter", name);
            }
        }
        
        private void members() {
            HashSet<UUID> members = new HashSet<>(plot.region.getMembers().getUniqueIds());
            if (plot.renter != null) {
                members.remove(plot.renter);
            }
            if (members.size() == 0) {
                return;
            }
            if (plot.rentable && !members.contains(player.getUniqueId())) {
                return;
            }
            Messages.say(player, "settings.plot.infocmd.members",
              InfoCmd.prefixed(members));
        }
        
        private void rent() {
            if (plot.rentable || plot.renter != null) {
                Messages.say(player, "settings.plot.infocmd.rent", plot.rent);
            }
        }
        
        private void debt() {
            if (plot.debt != 0) {
                Messages.say(player, "settings.plot.infocmd.debt", plot.debt);
            }
        }
        
        private void reserved() {
            if (plot.reversedFor != null) {
                String name = InfoCmd.getOnlineName(Bukkit.getOfflinePlayer(plot.reversedFor));
                Messages.say(player, "settings.plot.infocmd.reservation", name);
            }
        }
    }
    
    public static TextComponent createHover(String message, String hover, String cmd) {
        TextComponent text = new TextComponent(message);
        text.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, cmd));
        text.setHoverEvent(new HoverEvent(Action.SHOW_TEXT, 
          new ComponentBuilder(hover).create()));
        return text;
    }
    
    public static String getHover(BlockVector3 min, BlockVector3 max) {
        return Messages.get("settings.plot.infocmd.hover", min, max);
    }
    
    public static String getBounds(BlockVector3 min, BlockVector3 max) {
        int x = max.getX() - min.getX();
        int y = max.getY() - min.getY();
        int z = max.getZ() - min.getZ();
        return Messages.get("settings.plot.infocmd.bounds", x, y ,z);
    }
    
    public static String getTp(BlockVector3 pos) {
        return Messages.get("settings.plot.infocmd.tpCmd", 
          pos.getX(), pos.getY() + 1, pos.getZ());
    }
}
