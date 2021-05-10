package de.bloodeko.towns.town.settings.stage.ui;

import org.bukkit.entity.Player;

import de.bloodeko.towns.cmds.CmdBase;
import de.bloodeko.towns.town.Town;
import de.bloodeko.towns.util.Messages;

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
            town.getSettings().getStage().setStage(
              Integer.parseInt(value));
            
            Messages.say(player, "cmds.stage.setStage", 
              town.getSettings().getName(), value);
        } 
        catch (NumberFormatException ex) {
            Messages.say(player, "cmds.stage.invalidNumber");
        }
    }
}
