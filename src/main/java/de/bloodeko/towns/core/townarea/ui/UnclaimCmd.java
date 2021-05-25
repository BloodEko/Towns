package de.bloodeko.towns.core.townarea.ui;

import org.bukkit.entity.Player;

import de.bloodeko.towns.Services;
import de.bloodeko.towns.core.towns.Town;
import de.bloodeko.towns.util.Chunk;
import de.bloodeko.towns.util.Messages;
import de.bloodeko.towns.util.cmds.CmdBase;

public class UnclaimCmd extends CmdBase {
    
    @Override
    public void execute(Player player, String[] args) {
        Town town = getTown(player);
        Services.chunkservice().remove(Chunk.fromEntity(player));
        String name = town.getName();
        Messages.say(player, "cmds.unclaim.unclaimed", Chunk.fromEntity(player), name);
    }
}
