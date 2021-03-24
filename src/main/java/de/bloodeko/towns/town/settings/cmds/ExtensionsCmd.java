package de.bloodeko.towns.town.settings.cmds;

import java.util.List;

import org.bukkit.entity.Player;

import de.bloodeko.towns.cmds.CmdBase;
import de.bloodeko.towns.town.ChunkMap;
import de.bloodeko.towns.town.settings.SettingsRegistry;
import de.bloodeko.towns.util.Messages;
import de.bloodeko.towns.util.Util;

public class ExtensionsCmd extends CmdBase {
    private SettingsRegistry registry;
    
    public ExtensionsCmd(ChunkMap map, SettingsRegistry registry) {
        super(map);
        this.registry = registry;
    }

    @Override
    public void execute(Player player, String[] args) {
        List<String> list = registry.getMachesNames(getTown(player));
        Messages.say(player, "cmds.extensions.list", Util.join(list, ", "));
    }
}
