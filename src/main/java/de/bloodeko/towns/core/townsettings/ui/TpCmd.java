package de.bloodeko.towns.core.townsettings.ui;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import de.bloodeko.towns.Services;
import de.bloodeko.towns.core.towns.Town;
import de.bloodeko.towns.core.townsettings.legacy.Settings;
import de.bloodeko.towns.util.Messages;
import de.bloodeko.towns.util.ModifyException;
import de.bloodeko.towns.util.cmds.CmdBase;

/**
 * Allows player to teleport to a town, if the warp 
 * is bought and set.
 */
public class TpCmd extends CmdBase {

    @Override
    public void execute(Player player, String[] args) {
        if (args.length == 0) {
            Messages.say(player, "cmds.tp.cmdUsage");
            return;
        }
        Integer id = Services.nameservice().getId(args[0]);
        if (id == null) {
            throw new ModifyException("town.townregistry.nameNotFound");
        }
        Town town = Services.townservice().get(id);
        Object loc = town.getSettings().get(Settings.WARP);
        if (loc instanceof Location) {
            player.teleport((Location) loc);
            Messages.say(player, "cmds.tp.teleport", town.getName());
        } else {
            Messages.say(player, "cmds.tp.warpNotSet");
        }
    }
    
    @Override
    public List<String> completeTab(String[] args) {
        String name = args.length == 0 ? "" : args[0];
        return Services.nameservice().startingWith(name);
    }
}
