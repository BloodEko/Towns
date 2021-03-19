package de.bloodeko.towns.cmds.general;

import org.bukkit.entity.Player;

import de.bloodeko.towns.cmds.CmdBase;
import de.bloodeko.towns.town.ChunkMap;
import de.bloodeko.towns.town.Town;
import de.bloodeko.towns.town.TownRegistry;
import de.bloodeko.towns.util.Messages;

public class RenameCmd extends CmdBase {
    private TownRegistry registry;

    public RenameCmd(ChunkMap map, TownRegistry registry) {
        super(map);
        this.registry = registry;
    }

    @Override
    public void execute(Player player, String[] args) {
        if (args.length == 0) {
            Messages.say(player, "cmds.rename.cmdUsage");
            return;
        }
        rename(getTown(player), registry, args[0]);
        Messages.say(player, "cmds.rename.renamed", args[0]);
    }
    
    public void rename(Town town, TownRegistry registry, String name) {
        registry.rename(town, name);
        town.getSettings().setName(name);
        town.getSettings().updateFlags();
    }
    
}
