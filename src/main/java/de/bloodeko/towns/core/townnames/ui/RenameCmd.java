package de.bloodeko.towns.core.townnames.ui;

import org.bukkit.entity.Player;

import de.bloodeko.towns.Services;
import de.bloodeko.towns.core.towns.legacy.Town;
import de.bloodeko.towns.core.towns.legacy.TownRegistry;
import de.bloodeko.towns.core.townsettings.legacy.Settings;
import de.bloodeko.towns.util.Messages;
import de.bloodeko.towns.util.cmds.CmdBase;

public class RenameCmd extends CmdBase {

    @Override
    public void execute(Player player, String[] args) {
        if (args.length == 0) {
            Messages.say(player, "cmds.rename.cmdUsage");
            return;
        }
        rename(getTown(player), Services.towns(), args[0]);
        Messages.say(player, "cmds.rename.renamed", args[0]);
    }
    
    public void rename(Town town, TownRegistry registry, String name) {
        registry.rename(town, name);
        Settings.NAME.set(town.getSettings().getFlags(), name);
        town.getSettings().updateFlags();
    }
    
}
