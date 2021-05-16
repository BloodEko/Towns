package de.bloodeko.towns.core.townnames.ui;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import de.bloodeko.towns.Services;
import de.bloodeko.towns.core.towns.legacy.Town;
import de.bloodeko.towns.core.townsettings.legacy.Settings;
import de.bloodeko.towns.util.Messages;
import de.bloodeko.towns.util.cmds.CmdBase;

public class TpCmd extends CmdBase {

    @Override
    public void execute(Player player, String[] args) {
        if (args.length == 0) {
            Messages.say(player, "cmds.tp.cmdUsage");
            return;
        }
        Town town = Services.towns().get(args[0]);
        Object loc = town.getSettings().get(Settings.WARP);
        if (loc instanceof Location) {
            player.teleport((Location) loc);
            Messages.say(player, "cmds.tp.teleport", town.getSettings().getName());
        } else {
            Messages.say(player, "cmds.tp.warpNotSet");
        }
    }
    
    @Override
    public List<String> completeTab(String[] args) {
        String name = args.length == 0 ? "" : args[0];
        return Services.towns().getMatches(name);
    }
}
