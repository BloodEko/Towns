package de.bloodeko.towns.core.townpeople.ui;

import java.util.Objects;
import java.util.UUID;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import de.bloodeko.towns.core.townpeople.TownPeople;
import de.bloodeko.towns.core.towns.Town;
import de.bloodeko.towns.util.Messages;
import de.bloodeko.towns.util.ModifyException;
import de.bloodeko.towns.util.cmds.CmdBase;

/**
 * Allows the town owner to transfer ownership.
 */
public class OwnerCmd extends CmdBase {

    @Override
    public void execute(Player player, String[] args) {
        Town town = getTown(player);
        TownPeople people = town.getPeople();
        String name = getArg(0, args);
        OfflinePlayer target = getOfflineTarget(name);
        
        canPerform(people, player.getUniqueId(), target.getUniqueId());
        town.getPeople().setOwner(target.getUniqueId());
        Messages.say(player, "cmds.owner.setOwner", target.getName());
    }
    
    /**
     * Throws an exception if the player is no owner, 
     * or the target itself.
     */
    private void canPerform(TownPeople people, UUID player, UUID target) {
        if (!people.isOwner(player)) {
            throw new ModifyException("cmds.owner.youAreNotOwner");
        }
        if (Objects.equals(player, target)) {
            throw new ModifyException("cmds.owner.youAreAlreadyOwner");
        }
    }
}
