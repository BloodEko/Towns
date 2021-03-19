package de.bloodeko.towns.session1;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;


public class TownCommandListener implements CommandExecutor, TabCompleter {
    private TownCmdHandler handler;
    
    public TownCommandListener(TownCmdHandler handler) {
        this.handler = handler;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Command has no console support.");
            return true;
        }
        return handler.onCommand((Player) sender, args);
    }
    
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return handler.onTabComplete(args);
    }    
}
