package de.bloodeko.towns.core.towns.ui;

import org.bukkit.entity.Player;

import de.bloodeko.towns.core.TownFactory;
import de.bloodeko.towns.core.towns.Town;
import de.bloodeko.towns.util.Messages;
import de.bloodeko.towns.util.cmds.CmdBase;

//todo, double check safety.
public class DeleteCmd extends CmdBase {
    
    @Override
    public void execute(Player player, String[] args) {
        Town town = getTown(player);
        String name = town.getName();
        TownFactory.destroyTown(town.getId());
        Messages.say(player, "cmds.delete.deleted", name);
    }
}
