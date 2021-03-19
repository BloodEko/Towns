package de.bloodeko.towns.cmds.general;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import de.bloodeko.towns.cmds.CmdBase;
import de.bloodeko.towns.town.ChunkMap;
import de.bloodeko.towns.town.Town;

public class GovenorCmd extends CmdBase {

    public GovenorCmd(ChunkMap map) {
        super(map);
    }
    
    @Override
    public void execute(Player player, String[] args) {
        String name = getArg(0, args);
        Town town = getTown(player);
        
        if (hasArg(1, args)) {
            OfflinePlayer target = getOfflineTarget(name);
            town.getPeople().removeGovenor(player.getUniqueId(), target.getUniqueId());
            player.sendMessage(name  + " is no longer govenor here.");
        }
        else {
            Player target = getTarget(name);
            town.getPeople().addGovenor(player.getUniqueId(), target.getUniqueId());
            player.sendMessage(name  + " is now govenor here.");
            target.sendMessage("You are now govenor in " + town.getSettings().getName() + ".");
        }
    }
}
