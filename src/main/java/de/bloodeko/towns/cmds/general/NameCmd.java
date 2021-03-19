package de.bloodeko.towns.cmds.general;

import org.bukkit.entity.Player;

import de.bloodeko.towns.cmds.CmdBase;
import de.bloodeko.towns.town.ChunkMap;
import de.bloodeko.towns.town.TownRegistry;

public class NameCmd extends CmdBase {
    private TownRegistry registry;

    public NameCmd(ChunkMap map, TownRegistry registry) {
        super(map);
        this.registry = registry;
    }

    @Override
    public void execute(Player player, String[] args) {
        if (args.length == 0) {
            player.sendMessage("Use /town name <newname> to set a new name.");
            return;
        }
        getTown(player).rename(registry, args[0]);
        player.sendMessage("Renamed your town to: " + args[0]);
    }
    
}
