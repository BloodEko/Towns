package de.bloodeko.towns.cmds.settings;

import org.bukkit.entity.Player;

import com.sk89q.worldguard.protection.flags.StateFlag.State;

import de.bloodeko.towns.cmds.CmdBase;
import de.bloodeko.towns.town.ChunkMap;
import de.bloodeko.towns.town.settings.Settings;
import de.bloodeko.towns.town.settings.TownSettings;
import de.bloodeko.towns.util.Messages;

/**
 * Commands to define and toggle settings
 * for towns.
 */
public class SettingsCmds {
    
    public static class AnimalProtectCmd extends BooleanCmd {

        public AnimalProtectCmd(ChunkMap map, String name) {
            super(map, name, State.ALLOW, State.DENY);
        }
        
        @Override 
        public void setValue(TownSettings settings, State value) {
            settings.writeSetting(Settings.DAMAGE_ANIMALS, value);
        }
    }
    
    
    public static class PvpProtectCmd extends BooleanCmd {

        public PvpProtectCmd(ChunkMap map, String name) {
            super(map, name, State.ALLOW, State.DENY);
        }
        
        @Override 
        public void setValue(TownSettings settings, State value) {
            settings.writeSetting(Settings.PVP, value);
        }
    }
    
    
    public static class WarpCmd extends CmdBase {

        public WarpCmd(ChunkMap map) {
            super(map);
        }

        @Override
        public void execute(Player player, String[] args) {
            if (args.length == 0) {
                getTown(player).getSettings().writeSetting(Settings.WARP, player.getLocation());
                Messages.say(player, "settings.warp.set");
            } else {
                getTown(player).getSettings().writeSetting(Settings.WARP, null);
                Messages.say(player, "settings.warp.disabled");
            }
        }
    }
    
}
