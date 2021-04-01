package de.bloodeko.towns.town.settings.plots.cmds;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import de.bloodeko.towns.cmds.CmdBase;
import de.bloodeko.towns.cmds.CmdHandler;
import de.bloodeko.towns.util.ModifyException;

public class PlotCmd extends CmdBase implements CommandExecutor, TabCompleter {
    private CmdHandler handler;
    
    public PlotCmd(CmdHandler handler) {
        this.handler = handler;
    }
    
    public void register(String name, CmdBase cmd) {
        handler.register(name, cmd);
    }

    @Override
    public void execute(Player player, String[] args) {
        handler.onCommand(player, args);
    }
    
    @Override
    public List<String> completeTab(String[] args, Player player) {
        return handler.onTabComplete(args, player);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (sender instanceof Player) {
            return handler.onTabComplete(args, (Player) sender);
        }
        return null;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            try {
                return handler.onCommand((Player) sender, args);
            }
            catch(ModifyException ex) {
                sender.sendMessage(ex.getMessage());
                return true;
            }
        }
        return true;
    }
}
