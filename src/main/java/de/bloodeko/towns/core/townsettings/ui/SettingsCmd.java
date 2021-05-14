package de.bloodeko.towns.core.townsettings.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.bukkit.entity.Player;

import de.bloodeko.towns.Services;
import de.bloodeko.towns.core.towns.legacy.Town;
import de.bloodeko.towns.core.townsettings.legacy.AdvancedSetting;
import de.bloodeko.towns.core.townsettings.legacy.Setting;
import de.bloodeko.towns.core.townsettings.legacy.SettingsRegistry;
import de.bloodeko.towns.util.Messages;
import de.bloodeko.towns.util.cmds.CmdBase;

public class SettingsCmd extends CmdBase {

    @Override
    public void execute(Player player, String[] args) {
        Town town = getTown(player);
        Messages.say(player, "cmds.info.settingsHeader");
        
        for (AdvancedSetting setting : getSettings(Services.settings(), town)) {
            Messages.say(player, "cmds.info.setting", 
             setting.names.getName(), setting.names.display(town));
        }
    }
    
    /**
     * Returns settings for this town, sorted by higher priority first.
     * Skips hidden settings.
     */
    public static List<AdvancedSetting> getSettings(SettingsRegistry settings, Town town) {
        List<AdvancedSetting> list = new ArrayList<>();
        for (Setting flag : town.getSettings().settings()) {
            AdvancedSetting setting = settings.fromId(flag.getId());
            if (setting == null)
                continue;
            if (setting.names.isHidden()) 
                continue;
            
            list.add(setting);
        }
        Collections.sort(list, Comparator.comparing(SettingsCmd::getPriority).reversed());
        return list;
    }
    
    private static int getPriority(AdvancedSetting setting) {
        return setting.names.getPriority();
    }
}
