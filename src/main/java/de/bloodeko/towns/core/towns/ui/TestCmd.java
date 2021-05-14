package de.bloodeko.towns.core.towns.ui;

import org.bukkit.entity.Player;

import de.bloodeko.towns.util.cmds.CmdBase;

public class TestCmd extends CmdBase {
    
    @Override
    public void execute(Player player, String[] args) {
        player.sendMessage("has abc: " + player.hasPermission("abc"));
        player.sendMessage("has *: " + player.hasPermission("*"));
    }
}
