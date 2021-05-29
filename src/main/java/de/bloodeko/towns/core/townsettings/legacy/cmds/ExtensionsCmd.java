package de.bloodeko.towns.core.townsettings.legacy.cmds;

import java.util.List;

import org.bukkit.entity.Player;

import de.bloodeko.towns.Services;
import de.bloodeko.towns.core.towns.Town;
import de.bloodeko.towns.util.Messages;
import de.bloodeko.towns.util.Util;
import de.bloodeko.towns.util.cmds.CmdBase;

/**
 * Allows governors to see a list of extensions
 * which are possible to buy for that town.
 */
public class ExtensionsCmd extends CmdBase {
    
    @Override
    public void execute(Player player, String[] args) {
        Town town = getTown(player);
        List<String> list = Services.settingsservice().registry().getMachesNames(town);
        Messages.say(player, "cmds.extensions.list", Util.join(list, ", "));
    }
}
