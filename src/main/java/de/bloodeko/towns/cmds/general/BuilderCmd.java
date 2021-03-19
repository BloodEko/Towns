package de.bloodeko.towns.cmds.general;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import de.bloodeko.towns.cmds.CmdBase;
import de.bloodeko.towns.town.ChunkMap;
import de.bloodeko.towns.town.Town;

public class BuilderCmd extends CmdBase {

    public BuilderCmd(ChunkMap map) {
        super(map);
    }
    
    @Override
    public void execute(Player player, String[] args) {
        String name = getArg(0, args);
        Town town = getTown(player);
        
        if (hasArg(1, args)) {
            OfflinePlayer target = getOfflineTarget(name);
            town.getPeople().removeBuilder(player.getUniqueId(), target.getUniqueId());
            player.sendMessage(name  + " is no longer builder here.");
        }
        else {
            Player target = getTarget(name);
            town.getPeople().addBuilder(player.getUniqueId(), target.getUniqueId());
            player.sendMessage(name  + " is now builder here.");
            target.sendMessage("You are now builder in " + town.getSettings().getName() + ".");
        }
    }
}
