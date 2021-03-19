package de.bloodeko.towns.cmds.general;

import org.bukkit.entity.Player;

import de.bloodeko.towns.cmds.CmdBase;
import de.bloodeko.towns.town.ChunkMap;
import de.bloodeko.towns.town.TownRegistry;
import de.bloodeko.towns.util.Chunk;

public class FoundCmd extends CmdBase {
    private TownRegistry registry;
    
    public FoundCmd(ChunkMap map, TownRegistry registry) {
        super(map);
        this.registry = registry;
    }

    @Override
    public void execute(Player player, String[] args) {
        String name = getArg(0, args, "You need to specify a name.");
        registry.foundTown(Chunk.fromEntity(player), name, player.getUniqueId());
        player.sendMessage("Town " + name + " created.");
    }

}
