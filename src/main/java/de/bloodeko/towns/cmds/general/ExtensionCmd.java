package de.bloodeko.towns.cmds.general;

import java.util.List;

import org.bukkit.entity.Player;

import de.bloodeko.towns.cmds.CmdBase;
import de.bloodeko.towns.town.ChunkMap;
import de.bloodeko.towns.town.Town;
import de.bloodeko.towns.town.settings.SettingsRegistry;
import de.bloodeko.towns.town.settings.SettingsRegistry.RegisteredSetting;
import de.bloodeko.towns.util.Chunk;
import de.bloodeko.towns.util.Messages;
import de.bloodeko.towns.util.Util;

public class ExtensionCmd extends CmdBase {
    private SettingsRegistry registry;
    
    public ExtensionCmd(ChunkMap map, SettingsRegistry registry) {
        super(map);
        this.registry = registry;
    }

    @Override
    public void execute(Player player, String[] args) {
        RegisteredSetting setting = registry.fromDisplay(getArg(0, args));
        if (setting == null) {
            Messages.say(player, "cmds.extension.notFound");
            return;
        }
        getTown(player).getSettings().addExtension(setting.value);
        Messages.say(player, "cmds.extension.bought", setting.display);
    }
    
    @Override
    public List<String> completeTab(String[] args, Player player) {
        Town town = getMap().getTown(Chunk.fromEntity(player));
        if (town == null || !town.getPeople().isGovernor(player.getUniqueId())) {
            return null;
        }
        return Util.filterLowerList(registry.getPossibleNames(getTown(player)), args[0]);
    }
}
