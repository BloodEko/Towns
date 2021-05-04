package de.bloodeko.towns.cmds.core;

import org.bukkit.entity.Player;

import de.bloodeko.towns.cmds.CmdBase;
import de.bloodeko.towns.town.Town;
import de.bloodeko.towns.util.Chunk;
import de.bloodeko.towns.util.Messages;

public class UnclaimCmd extends CmdBase {
    
    @Override
    public void execute(Player player, String[] args) {
        Town town = getTown(player);
        town.getArea().contract(getMap(), Chunk.fromEntity(player));
        String name = town.getSettings().getName();
        Messages.say(player, "cmds.unclaim.unclaimed", Chunk.fromEntity(player), name);
    }
}
