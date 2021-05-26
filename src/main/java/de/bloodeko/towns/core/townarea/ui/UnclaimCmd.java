package de.bloodeko.towns.core.townarea.ui;

import org.bukkit.entity.Player;

import de.bloodeko.towns.Services;
import de.bloodeko.towns.core.towns.Town;
import de.bloodeko.towns.util.Chunk;
import de.bloodeko.towns.util.Messages;
import de.bloodeko.towns.util.ModifyException;
import de.bloodeko.towns.util.cmds.CmdBase;

/**
 * Allows owners of a town to decrease its land.
 * Updates the area with the remove chunk.
 */
public class UnclaimCmd extends CmdBase {
    
    @Override
    public void execute(Player player, String[] args) {
        Town town = getTown(player);
        if (!player.getUniqueId().equals(town.getPeople().getOwner())) {
            throw new ModifyException("cmds.base.youreNotOwner");
        }

        Chunk chunk = Chunk.fromEntity(player);
        Services.chunkservice().remove(chunk);
        Messages.say(player, "cmds.unclaim.unclaimed", chunk);
    }
}
