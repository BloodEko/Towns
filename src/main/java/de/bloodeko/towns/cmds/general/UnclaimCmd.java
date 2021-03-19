package de.bloodeko.towns.cmds.general;

import org.bukkit.entity.Player;

import de.bloodeko.towns.cmds.CmdBase;
import de.bloodeko.towns.town.ChunkMap;
import de.bloodeko.towns.town.Town;
import de.bloodeko.towns.util.Chunk;
import de.bloodeko.towns.util.Messages;

public class UnclaimCmd extends CmdBase {
    
    public UnclaimCmd(ChunkMap map) {
        super(map);
    }
    
    @Override
    public void execute(Player player, String[] args) {
        Town town = getTown(player);
        town.getArea().contract(getMap(), Chunk.fromEntity(player));
        Messages.say(player, "cmds.unclaim.unclaimed", Chunk.fromEntity(player), town);
    }
}
