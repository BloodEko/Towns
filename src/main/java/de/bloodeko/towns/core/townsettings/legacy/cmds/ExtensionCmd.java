package de.bloodeko.towns.core.townsettings.legacy.cmds;

import java.util.List;

import org.bukkit.entity.Player;

import de.bloodeko.towns.Services;
import de.bloodeko.towns.core.towns.Town;
import de.bloodeko.towns.core.townsettings.legacy.AdvancedSetting;
import de.bloodeko.towns.util.Chunk;
import de.bloodeko.towns.util.DoubleCheck;
import de.bloodeko.towns.util.DoubleCheck.Combined;
import de.bloodeko.towns.util.Messages;
import de.bloodeko.towns.util.ModifyException;
import de.bloodeko.towns.util.Util;
import de.bloodeko.towns.util.cmds.CmdBase;

/**
 * Allows governors to buy extensions for their towns.
 * Ensures that the town has the requirements to do so.
 */
public class ExtensionCmd extends CmdBase {
    private final DoubleCheck check = new DoubleCheck(10000);
    
    @Override
    public void execute(Player player, String[] args) {
        String arg = getArg(0, args, "cmds.extension.notFound");
        AdvancedSetting setting = Services.settingsservice().registry().fromDisplay(arg);
        if (setting == null) {
            throw new ModifyException("cmds.extension.notFound");
        }
        
        Town town = getTown(player);
        if (!setting.condition.canBuy(town)) {
            throw new ModifyException("cmds.extension.conditionFailed");
        }
        
        Combined key = new Combined(player.getUniqueId(), setting);
        if (!check.passForgetting(key)) {
            Messages.say(player, "cmds.extension.verfiyBuy", setting.names.getName(), 
              setting.prices.getPrice(), Services.economy().currencyNamePlural());
            Messages.say(player, "cmds.base.typeAgain", check.toSeconds());
            return;
        }
        
        town.getSettings().addSetting(setting.settingKey, town.getId());
        Messages.say(player, "cmds.extension.bought", setting.names.getName());
    }
    
    @Override
    public List<String> completeTab(String[] args, Player player) {
        Integer id = getMap().get(Chunk.fromEntity(player));
        Town town = Services.townservice().get(id);
        if (town == null || !town.getPeople().isGovernor(player.getUniqueId())) {
            return null;
        }
        return Util.filterLowerList(Services.settingsservice().registry().getMachesNames(getTown(player)), args[0]);
    }
}
