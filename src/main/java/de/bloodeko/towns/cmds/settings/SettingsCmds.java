package de.bloodeko.towns.cmds.settings;

import org.bukkit.entity.Player;

import com.sk89q.worldguard.protection.flags.StateFlag.State;

import de.bloodeko.towns.cmds.CmdBase;
import de.bloodeko.towns.town.ChunkMap;
import de.bloodeko.towns.town.settings.Settings;
import de.bloodeko.towns.town.settings.TownSettings;
import de.bloodeko.towns.util.Messages;

/**
 * Commands to toggle and define various setitngs
 * in towns.
 */
public class SettingsCmds {
    
    /**
     * Toggles DAMAGE_ANIMALS.
     */
    public static class DamageAnimalsCmd extends BooleanCmd {

        public DamageAnimalsCmd(ChunkMap map, String name) {
            super(map, name);
        }
        
        @Override
        public void setValue(TownSettings settings, State value) {
            settings.writeSetting(Settings.DAMAGE_ANIMALS, value);
        }
    }
    
    /**
     * Toggles PVP.
     */
    public static class PvpCmd extends BooleanCmd {

        public PvpCmd(ChunkMap map, String name) {
            super(map, name);
        }
        
        @Override
        public void setValue(TownSettings settings, State value) {
            settings.writeSetting(Settings.PVP, value);
        }
    }
    
    /**
     * Sets the WARP_POINT.
     */
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
