package de.bloodeko.towns.core.townpeople.ui;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import de.bloodeko.towns.core.towns.legacy.Town;
import de.bloodeko.towns.util.Messages;
import de.bloodeko.towns.util.cmds.CmdBase;

public class BuilderCmd extends CmdBase {
    
    @Override
    public void execute(Player player, String[] args) {
        String name = getArg(0, args);
        Town town = getTown(player);
        
        if (hasArg(1, args)) {
            OfflinePlayer target = getOfflineTarget(name);
            town.getPeople().removeBuilder(player.getUniqueId(), target.getUniqueId());
            Messages.say(player, "cmds.builder.removedPlayer", target.getName());
        }
        else {
            Player target = getTarget(name);
            town.getPeople().addBuilder(player.getUniqueId(), target.getUniqueId());
            Messages.say(player, "cmds.builder.addedPlayer", target.getName());
            Messages.say(target, "cmds.builder.gainedRank", town.getSettings().getStage());
        }
    }
}
