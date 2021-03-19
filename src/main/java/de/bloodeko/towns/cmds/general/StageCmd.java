package de.bloodeko.towns.cmds.general;

import org.bukkit.entity.Player;

import de.bloodeko.towns.cmds.CmdBase;
import de.bloodeko.towns.town.ChunkMap;
import de.bloodeko.towns.town.Town;
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
            town.getSettings().setStage(Integer.parseInt(stage));
            String name = town.getSettings().getName();
            Messages.say(player, "cmds.stage.setStage", name, stage);
        } 
        catch (NumberFormatException ex) {
            Messages.say(player, "cmds.stage.invalidNumber");
        }
    }
}
