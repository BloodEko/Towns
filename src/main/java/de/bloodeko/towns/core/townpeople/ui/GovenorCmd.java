package de.bloodeko.towns.core.townpeople.ui;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import de.bloodeko.towns.core.towns.legacy.Town;
import de.bloodeko.towns.util.Messages;
import de.bloodeko.towns.util.cmds.CmdBase;

public class GovenorCmd extends CmdBase {
    
    @Override
    public void execute(Player player, String[] args) {
        String name = getArg(0, args);
        Town town = getTown(player);
        
        if (hasArg(1, args)) {
            OfflinePlayer target = getOfflineTarget(name);
            town.getPeople().removeGovenor(player.getUniqueId(), target.getUniqueId());
            Messages.say(player, "cmds.governor.removedPlayer", target.getName());
        }
        else {
            Player target = getTarget(name);
            town.getPeople().addGovenor(player.getUniqueId(), target.getUniqueId());
            Messages.say(player, "cmds.governor.addedPlayer", target.getName());
            Messages.say(target, "cmds.governor.gainedRank", town.getSettings().getName());
        }
    }
}
