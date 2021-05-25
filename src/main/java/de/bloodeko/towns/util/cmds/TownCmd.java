package de.bloodeko.towns.util.cmds;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import de.bloodeko.towns.util.ModifyException;


public class TownCmd implements CommandExecutor, TabCompleter {
    private CmdHandler handler;
    
    public TownCmd(CmdHandler handler) {
        this.handler = handler;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Command has no console support.");
            return true;
        }
        try {
            return handler.onCommand((Player) sender, args);
        }
        catch(ModifyException ex) {
            sender.sendMessage(ex.getMessage());
            return true;
        }
    }
    
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (sender instanceof Player) {
            return handler.onTabComplete(args, (Player) sender);
        }
        return null;
    }    
}
