package de.bloodeko.towns.cmds.core;

import org.bukkit.entity.Player;

import de.bloodeko.towns.cmds.CmdBase;
import de.bloodeko.towns.town.ChunkMap;

public class TestCmd extends CmdBase {

    public TestCmd(ChunkMap map) {
        super(map);
    }

    @Override
    public void execute(Player player, String[] args) {
        player.sendMessage("has abc: " + player.hasPermission("abc"));
        player.sendMessage("has *: " + player.hasPermission("*"));
    }
}
