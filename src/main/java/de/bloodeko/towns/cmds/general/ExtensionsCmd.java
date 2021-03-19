package de.bloodeko.towns.cmds.general;

import java.util.List;

import org.bukkit.entity.Player;

import de.bloodeko.towns.cmds.CmdBase;
import de.bloodeko.towns.cmds.settings.SettingsRegistry;
import de.bloodeko.towns.town.ChunkMap;
import de.bloodeko.towns.util.Util;

public class ExtensionsCmd extends CmdBase {
    private SettingsRegistry registry;
    
    public ExtensionsCmd(ChunkMap map, SettingsRegistry registry) {
        super(map);
        this.registry = registry;
    }

    @Override
    public void execute(Player player, String[] args) {
        List<String> list = registry.getPossibleNames(getTown(player));
        player.sendMessage("Possible extensions to buy: " + Util.join(list, ", "));
    }
}
