package de.bloodeko.towns.cmds.core;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import de.bloodeko.towns.cmds.CmdBase;
import de.bloodeko.towns.town.ChunkMap;
import de.bloodeko.towns.town.Town;
import de.bloodeko.towns.town.TownDeleteEvent;
import de.bloodeko.towns.town.TownRegistry;
import de.bloodeko.towns.util.Messages;
import de.bloodeko.towns.util.ModifyException;

//todo, double check safety.
public class DeleteCmd extends CmdBase {
    
    public DeleteCmd(ChunkMap map, TownRegistry registry) {
        super(map);
    }
    
    @Override
    public void execute(Player player, String[] args) {
        Town town = getTown(player);
        TownDeleteEvent event = new TownDeleteEvent(town);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) {
            throw new ModifyException(event.getResult());
        }
        Messages.say(player, "cmds.delete.deleted", town.getSettings().getName());
    }
}
