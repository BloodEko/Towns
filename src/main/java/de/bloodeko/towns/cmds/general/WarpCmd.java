package de.bloodeko.towns.cmds.general;

import org.bukkit.entity.Player;

import de.bloodeko.towns.cmds.CmdBase;
import de.bloodeko.towns.town.ChunkMap;

public class WarpCmd extends CmdBase {

    public WarpCmd(ChunkMap map) {
        super(map);
    }
    
    @Override
    public void execute(Player player, String[] args) {
        if (args.length == 0) {
            getTown(player).setWarp(player.getLocation());
            player.sendMessage("Set the warppoint to your location.");
        } else {
            getTown(player).setWarp(null);
            player.sendMessage("Disabled the warp for this town.");
        }
    }
    
}
