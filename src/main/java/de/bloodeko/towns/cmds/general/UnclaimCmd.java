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
        Chunk chunk = Chunk.fromEntity(player);
        unclaim(chunk, player);
    }
    
    public void unclaim(Chunk chunk, Player player) {
        Town town = getMap().getTown(chunk);
        if (town == null) {
            Messages.say(player, "cmds.unclaim.notClaimed");
            return;
        }
        if (!town.getPeople().isGovernor(player.getUniqueId())) {
            Messages.say(player, "cmds.unclaim.notAllowed");
            return;
        }
        town.getArea().contract(getMap(), chunk);
        Messages.say(player, "cmds.unclaim.unclaimed", chunk, town);
    }
}
