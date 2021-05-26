package de.bloodeko.towns.core.towns.ui;

import org.bukkit.entity.Player;

import de.bloodeko.towns.core.TownFactory;
import de.bloodeko.towns.core.towns.Town;
import de.bloodeko.towns.util.DoubleCheck;
import de.bloodeko.towns.util.Messages;
import de.bloodeko.towns.util.ModifyException;
import de.bloodeko.towns.util.cmds.CmdBase;

/**
 * Allows owners to delete their town.
 * Removes it from all known services.
 */
public class DeleteCmd extends CmdBase {
    private final DoubleCheck check = new DoubleCheck(10000);
    
    @Override
    public void execute(Player player, String[] args) {
        Town town = getTown(player);
        String name = town.getName();
        
        if (!player.getUniqueId().equals(town.getPeople().getOwner())) {
            throw new ModifyException("cmds.base.youreNotOwner");
        }
        
        if (!check.passForgetting(player.getUniqueId())) {
            Messages.say(player, "cmds.delete.verifiy", name);
            Messages.say(player, "cmds.base.typeAgain", check.toSeconds());
            return;
        }
        
        TownFactory.destroyTown(town.getId());
        Messages.say(player, "cmds.delete.deleted", name);
    }
}
