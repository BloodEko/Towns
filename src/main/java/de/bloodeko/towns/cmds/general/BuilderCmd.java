package de.bloodeko.towns.cmds.general;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import de.bloodeko.towns.cmds.CmdBase;
import de.bloodeko.towns.town.ChunkMap;
import de.bloodeko.towns.town.Town;
import de.bloodeko.towns.util.Messages;

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
            Messages.say(player, "cmds.builder.removedPlayer", target.getName());
        }
        else {
            Player target = getTarget(name);
            town.getPeople().addBuilder(player.getUniqueId(), target.getUniqueId());
            Messages.say(player, "cmds.builder.addedPlayer", target.getName());
            Messages.say(target, "cmds.builder.gainedRank", town.getSettings().getName());
        }
    }
}
