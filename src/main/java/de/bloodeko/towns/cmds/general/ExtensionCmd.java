package de.bloodeko.towns.cmds.general;

import java.util.List;

import org.bukkit.entity.Player;

import de.bloodeko.towns.cmds.CmdBase;
import de.bloodeko.towns.cmds.settings.SettingsRegistry;
import de.bloodeko.towns.cmds.settings.TownSetting;
import de.bloodeko.towns.town.ChunkMap;
import de.bloodeko.towns.town.Town;
import de.bloodeko.towns.util.Chunk;
import de.bloodeko.towns.util.Util;

public class ExtensionCmd extends CmdBase {
    private SettingsRegistry registry;
    
    public ExtensionCmd(ChunkMap map, SettingsRegistry registry) {
        super(map);
        this.registry = registry;
    }

    @Override
    public void execute(Player player, String[] args) {
        TownSetting setting = registry.settings().get(getArg(0, args));
        if (setting == null) {
            player.sendMessage("This setting does not exist.");
            return;
        }
        getTown(player).getSettings().addExtension(setting);
        player.sendMessage("Bought extension " + setting.getName() + " for the town.");
    }
    
    @Override
    public List<String> completeTab(String[] args, Player player) {
        Town town = getMap().query(Chunk.fromEntity(player));
        if (town == null || !town.getPeople().isGovernor(player.getUniqueId())) {
            return null;
        }
        return Util.filterList(registry.getPossibleNames(getTown(player)), args);
    }
}
