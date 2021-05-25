package de.bloodeko.towns.core.townstages.ui;

import org.bukkit.entity.Player;

import de.bloodeko.towns.core.towns.Town;
import de.bloodeko.towns.util.Messages;
import de.bloodeko.towns.util.cmds.CmdBase;

/**
 * Sets the raw stage value for the town,
 * performed by a moderator.
 */
public class StageCmd extends CmdBase {

    @Override
    public void execute(Player player, String[] args) {
        Town town = getTownAsMod(player);
        String value = getArg(0, args);
        
        try {
            town.getStage().setStage(Integer.parseInt(value));
            Messages.say(player, "cmds.stage.setStage", town.getName(), value);
        } 
        catch (NumberFormatException ex) {
            Messages.say(player, "cmds.stage.invalidNumber");
        }
    }
}
