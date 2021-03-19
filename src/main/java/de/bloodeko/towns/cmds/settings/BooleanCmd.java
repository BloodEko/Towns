package de.bloodeko.towns.cmds.settings;

import java.util.Arrays;
import java.util.List;

import org.bukkit.entity.Player;

import com.sk89q.worldguard.protection.flags.StateFlag.State;

import de.bloodeko.towns.cmds.CmdBase;
import de.bloodeko.towns.town.ChunkMap;
import de.bloodeko.towns.town.Town;
import de.bloodeko.towns.town.TownSettings;
import de.bloodeko.towns.util.Messages;
import de.bloodeko.towns.util.Util;

/**
 * Provides a uniform format to create commands which toggle 
 * boolean settings.
 */
public abstract class BooleanCmd extends CmdBase {
    private static final String ON = Messages.get("booleanCmd.on");
    private static final String OFF = Messages.get("booleanCmd.off");
    private String name;
    
    public BooleanCmd(ChunkMap map, String name) {
        super(map);
        this.name = name;
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
            setValue(town.getSettings(), State.ALLOW);
            return;
        }
        if (args[0].equals(OFF)) {
            Messages.say(player, "booleanCmd.disabled", name);
            setValue(town.getSettings(), State.DENY);
            return;
        }
        sendError(player);
    }
    
    public abstract void setValue(TownSettings settings, State value);
    
    private void sendError(Player player) {
        Messages.say(player, "booleanCmd.invalidInput");
    }
    
    /**
     * Default implementation to tab-complete.
     */
    @Override
    public List<String> completeTab(String[] args) {
        return Util.filterList(Arrays.asList(ON, OFF), args);
    }
}
