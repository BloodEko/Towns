package de.bloodeko.towns.town.settings.cmds;

import java.util.List;

import org.bukkit.entity.Player;

import de.bloodeko.towns.Services;
import de.bloodeko.towns.cmds.CmdBase;
import de.bloodeko.towns.util.Messages;
import de.bloodeko.towns.util.Util;

public class ExtensionsCmd extends CmdBase {
    
    @Override
    public void execute(Player player, String[] args) {
        List<String> list = Services.settings().getMachesNames(getTown(player));
        Messages.say(player, "cmds.extensions.list", Util.join(list, ", "));
    }
}
