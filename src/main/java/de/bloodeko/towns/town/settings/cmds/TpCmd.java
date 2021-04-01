package de.bloodeko.towns.town.settings.cmds;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import de.bloodeko.towns.Services;
import de.bloodeko.towns.cmds.CmdBase;
import de.bloodeko.towns.town.Town;
import de.bloodeko.towns.town.settings.Settings;
import de.bloodeko.towns.util.Messages;

public class TpCmd extends CmdBase {

    @Override
    public void execute(Player player, String[] args) {
        if (args.length == 0) {
            Messages.say(player, "cmds.tp.cmdUsage");
            return;
        }
        Town town = Services.towns().get(args[0]);
        Location loc = (Location) town.getSettings().get(Settings.WARP);
        if (loc == null) {
            Messages.say(player, "cmds.tp.warpNotSet");
        } else {
            player.teleport(loc);
            Messages.say(player, "cmds.tp.teleport", town.getSettings().getName());
        }
    }
    
    @Override
    public List<String> completeTab(String[] args) {
        String name = args.length == 0 ? "" : args[0];
        return Services.towns().getMatches(name);
    }
}
