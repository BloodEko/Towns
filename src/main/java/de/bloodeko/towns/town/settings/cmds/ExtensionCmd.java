package de.bloodeko.towns.town.settings.cmds;

import java.util.List;

import org.bukkit.entity.Player;

import de.bloodeko.towns.Services;
import de.bloodeko.towns.cmds.CmdBase;
import de.bloodeko.towns.town.Town;
import de.bloodeko.towns.town.settings.AdvancedSetting;
import de.bloodeko.towns.util.Chunk;
import de.bloodeko.towns.util.Messages;
import de.bloodeko.towns.util.Util;

public class ExtensionCmd extends CmdBase {
    
    @Override
    public void execute(Player player, String[] args) {
        AdvancedSetting setting = Services.settings().fromDisplay(getArg(0, args));
        if (setting == null) {
            Messages.say(player, "cmds.extension.notFound");
            return;
        }
        getTown(player).getSettings().addSetting(setting.settingKey);
        Messages.say(player, "cmds.extension.bought", setting.names.getName());
    }
    
    @Override
    public List<String> completeTab(String[] args, Player player) {
        Town town = getMap().getTown(Chunk.fromEntity(player));
        if (town == null || !town.getPeople().isGovernor(player.getUniqueId())) {
            return null;
        }
        return Util.filterLowerList(Services.settings().getMachesNames(getTown(player)), args[0]);
    }
}
