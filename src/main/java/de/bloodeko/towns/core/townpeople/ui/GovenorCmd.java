package de.bloodeko.towns.core.townpeople.ui;

import java.util.Arrays;
import java.util.List;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import de.bloodeko.towns.core.towns.Town;
import de.bloodeko.towns.util.Messages;
import de.bloodeko.towns.util.Util;
import de.bloodeko.towns.util.cmds.CmdBase;

public class GovenorCmd extends CmdBase {
    private final String removeAction = Messages.get("cmds.governor.removeAction");
    private final String addAction = Messages.get("cmds.governor.addAction");
    
    @Override
    public void execute(Player player, String[] args) {
        if (args.length < 2) {
            Messages.say(player, "cmds.governor.wrongSyntax");
            return;
        }
        String action = getArg(0, args);
        String name = getArg(1, args);
        Town town = getTown(player);
        
        if (action.equals(addAction)) {
            Player target = getTarget(name);
            town.getPeople().addGovenor(player.getUniqueId(), target.getUniqueId());
            Messages.say(player, "cmds.governor.addedPlayer", target.getName());
            Messages.say(target, "cmds.governor.gainedRank", town.getName());
        }
        else if (action.equals(removeAction)) {
            OfflinePlayer target = getOfflineTarget(name);
            town.getPeople().removeGovenor(player.getUniqueId(), target.getUniqueId());
            Messages.say(player, "cmds.governor.removedPlayer", target.getName());
        } 
        else {
            Messages.say(player, "cmds.governor.wrongSyntax");
        }
    }
    
    @Override
    public List<String> completeTab(String[] args) {
        if (args.length == 1) {
            return Util.filterList(Arrays.asList(addAction, removeAction), args[0]);
        }
        return null;
    }
}
