package de.bloodeko.towns.cmds.general;

import org.bukkit.entity.Player;

import de.bloodeko.towns.cmds.CmdBase;
import de.bloodeko.towns.cmds.settings.Settings;
import de.bloodeko.towns.town.ChunkMap;

public class WarpCmd extends CmdBase {

    public WarpCmd(ChunkMap map) {
        super(map);
    }
    
    @Override
    public void execute(Player player, String[] args) {
        if (args.length == 0) {
            getTown(player).getSettings().updateSetting(Settings.WARP_FLAG, player.getLocation());
            player.sendMessage("Warppoint set to your location.");
        } else {
            getTown(player).getSettings().updateSetting(Settings.WARP_FLAG, null);
            player.sendMessage("Removed the warppoint for this town.");
        }
    }
    
}
