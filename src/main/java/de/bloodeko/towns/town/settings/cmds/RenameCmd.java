package de.bloodeko.towns.town.settings.cmds;

import org.bukkit.entity.Player;

import de.bloodeko.towns.Services;
import de.bloodeko.towns.cmds.CmdBase;
import de.bloodeko.towns.town.Town;
import de.bloodeko.towns.town.TownRegistry;
import de.bloodeko.towns.town.settings.Settings;
import de.bloodeko.towns.util.Messages;

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
