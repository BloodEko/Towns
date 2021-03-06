package de.bloodeko.towns.core.townsettings.legacy.cmds;

import java.util.Arrays;
import java.util.List;

import org.bukkit.entity.Player;

import com.sk89q.worldguard.protection.flags.StateFlag.State;

import de.bloodeko.towns.core.towns.Town;
import de.bloodeko.towns.core.townsettings.TownSettings;
import de.bloodeko.towns.util.Messages;
import de.bloodeko.towns.util.Util;
import de.bloodeko.towns.util.cmds.CmdBase;

/**
 * Provides a uniform format to create commands which toggle 
 * boolean settings.
 */
public abstract class BooleanCmd extends CmdBase {
    private static final String ON = Messages.get("booleanCmd.on");
    private static final String OFF = Messages.get("booleanCmd.off");
    
    private State allow;
    private State deny;
    private String name;
    
    public BooleanCmd(String name, State allow, State deny) {
        this.name = name;
        this.allow = allow;
        this.deny = deny;
    }
    
    /**
     * Default boolean implementation to toggle commands.
     */
    @Override
    public void execute(Player player, String[] args) {
        if (args.length == 0) {
            sendError(player);
            return;
        }
        Town town = getTown(player);
        if (args[0].equals(ON)) {
            Messages.say(player, "booleanCmd.enabled", name);
            setValue(town.getSettings(), allow);
            return;
        }
        if (args[0].equals(OFF)) {
            Messages.say(player, "booleanCmd.disabled", name);
            setValue(town.getSettings(), deny);
            return;
        }
        sendError(player);
    }
    
    /**
     * Checks availability and then updates the 
     * town settings/flags to perform the action.
     */
    public abstract void setValue(TownSettings settings, State value);
    
    private void sendError(Player player) {
        Messages.say(player, "booleanCmd.invalidInput");
    }
    
    /**
     * Default implementation to tab-complete.
     */
    @Override
    public List<String> completeTab(String[] args) {
        return Util.filterList(Arrays.asList(ON, OFF), args[0]);
    }
}
