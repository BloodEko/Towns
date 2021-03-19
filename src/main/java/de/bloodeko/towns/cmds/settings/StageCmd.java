package de.bloodeko.towns.cmds.settings;

import org.bukkit.entity.Player;

import de.bloodeko.towns.cmds.CmdBase;
import de.bloodeko.towns.town.ChunkMap;
import de.bloodeko.towns.town.Town;
import de.bloodeko.towns.town.settings.Settings;
import de.bloodeko.towns.util.Messages;

public class StageCmd extends CmdBase {

    public StageCmd(ChunkMap map) {
        super(map);
    }

    @Override
    public void execute(Player player, String[] args) {
        Town town = getTownAsMod(player);
        String stage = getArg(0, args);
        
        try {
            Settings.STAGE.set(town.getSettings().getFlags(), 
              Integer.valueOf(stage));
            
            Messages.say(player, "cmds.stage.setStage", 
              town.getSettings().getName(), stage);
        } 
        catch (NumberFormatException ex) {
            Messages.say(player, "cmds.stage.invalidNumber");
        }
    }
}
