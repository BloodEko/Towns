package de.bloodeko.towns.core.townsettings.legacy.cmds;

import java.util.List;

import org.bukkit.entity.Player;

import de.bloodeko.towns.Services;
import de.bloodeko.towns.util.Messages;
import de.bloodeko.towns.util.Util;
import de.bloodeko.towns.util.cmds.CmdBase;

public class ExtensionsCmd extends CmdBase {
    
    @Override
    public void execute(Player player, String[] args) {
        List<String> list = Services.settingsservice().registry().getMachesNames(getTown(player));
        Messages.say(player, "cmds.extensions.list", Util.join(list, ", "));
    }
}
