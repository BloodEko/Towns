package de.bloodeko.towns.core.towns.ui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import de.bloodeko.towns.core.towns.legacy.Town;
import de.bloodeko.towns.core.towns.legacy.TownDeleteEvent;
import de.bloodeko.towns.util.Messages;
import de.bloodeko.towns.util.ModifyException;
import de.bloodeko.towns.util.cmds.CmdBase;

//todo, double check safety.
public class DeleteCmd extends CmdBase {
    
    @Override
    public void execute(Player player, String[] args) {
        Town town = getTown(player);
        TownDeleteEvent event = new TownDeleteEvent(town);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) {
            throw new ModifyException(event.getResult());
        }
        Messages.say(player, "cmds.delete.deleted", town.getSettings().getName());
    }
}
