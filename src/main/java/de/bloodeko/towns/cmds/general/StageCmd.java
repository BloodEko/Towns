package de.bloodeko.towns.cmds.general;

import org.bukkit.entity.Player;

import de.bloodeko.towns.cmds.CmdBase;
import de.bloodeko.towns.town.ChunkMap;
import de.bloodeko.towns.town.Town;

public class StageCmd extends CmdBase {

    public StageCmd(ChunkMap map) {
        super(map);
    }

    @Override
    public void execute(Player player, String[] args) {
        Town town = getTown(player);
        String stage = getArg(0, args);
        try {
            town.getSettings().setStage(Integer.parseInt(stage));
            String name = town.getSettings().getName();
            player.sendMessage("Set the stage of " + name + " to " + stage + ".");
        } catch (NumberFormatException ex) {
            player.sendMessage("You must define a valid number.");
        }
    }
}
