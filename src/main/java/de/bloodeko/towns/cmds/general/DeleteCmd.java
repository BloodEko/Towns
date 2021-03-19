package de.bloodeko.towns.cmds.general;

import org.bukkit.entity.Player;

import de.bloodeko.towns.cmds.CmdBase;
import de.bloodeko.towns.town.ChunkMap;
import de.bloodeko.towns.town.Town;
import de.bloodeko.towns.town.TownFactory;
import de.bloodeko.towns.town.TownRegistry;
import de.bloodeko.towns.util.Messages;

//todo, double check safety.
//todo, prevent, if it has plots.
public class DeleteCmd extends CmdBase {
    private TownRegistry registry;
    
    public DeleteCmd(ChunkMap map, TownRegistry registry) {
        super(map);
        this.registry = registry;
    }

    @Override
    public void execute(Player player, String[] args) {
        Town town = getTown(player);
        TownFactory.unregisterTown(town, getMap(), registry, TownFactory.getWorldManager());
        Messages.say(player, "cmds.delete.deleted", town.getSettings().getName());
    }
}
