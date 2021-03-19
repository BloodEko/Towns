package de.bloodeko.towns.cmds.core;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import de.bloodeko.towns.cmds.CmdBase;
import de.bloodeko.towns.town.ChunkMap;
import de.bloodeko.towns.town.Town;
import de.bloodeko.towns.util.Messages;

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
            town.getPeople().removeGovenor(player.getUniqueId(), target.getUniqueId(), town);
            Messages.say(player, "cmds.governor.removedPlayer", target.getName());
        }
        else {
            Player target = getTarget(name);
            town.getPeople().addGovenor(player.getUniqueId(), target.getUniqueId(), town);
            Messages.say(player, "cmds.governor.addedPlayer", target.getName());
            Messages.say(target, "cmds.governor.gainedRank", town.getSettings().getName());
        }
    }
}
