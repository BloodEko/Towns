package de.bloodeko.towns.core.townnames.ui;

import org.bukkit.entity.Player;

import de.bloodeko.towns.Services;
import de.bloodeko.towns.core.townarea.TownRegion;
import de.bloodeko.towns.util.Messages;
import de.bloodeko.towns.util.cmds.CmdBase;

public class RenameCmd extends CmdBase {

    @Override
    public void execute(Player player, String[] args) {
        if (args.length == 0) {
            Messages.say(player, "cmds.rename.cmdUsage");
            return;
        }
        Integer town = getTown(player).getId();
        Services.nameservice().verify(args[0]);
        Services.nameservice().rename(town, args[0]);
        
        TownRegion region = Services.chunkservice().getRegion(town);
        region.setFlagNames(args[0]);
        
        Messages.say(player, "cmds.rename.renamed", args[0]);
    }
}
