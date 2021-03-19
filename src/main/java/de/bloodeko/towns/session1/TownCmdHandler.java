package de.bloodeko.towns.session1;

import static de.bloodeko.towns.session1.Util.filterList;
import static de.bloodeko.towns.session1.Util.strip0;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.bukkit.entity.Player;

/**
 * Dispatches registered sub-commands.
 * Also handles their tab completions.
 */
public class TownCmdHandler {
    private Map<String, TownCmdBase> cmds;
    
    public TownCmdHandler(Map<String, TownCmdBase> cmds) {
        this.cmds = cmds;
    }
    
    /**
     * Dispatches the first argument to a sub-command
     * and executes it, with the first argument cut off.
     */
    public boolean onCommand(Player player, String[] args) {
        if (args.length == 0) {
            return false;
        }
        TownCmdBase fmCmd = cmds.get(args[0]);
        if (fmCmd == null) {
            return false;
        }
        fmCmd.execute(player, strip0(args));
        return true;
    }
    
    /**
     * Dispatches tab-completion to a sub-command 
     * and returns the resulting filtered List.
     */
    public List<String> onTabComplete(String[] args) {
        if (args.length == 1) {
            return filterList(cmds.keySet(), args[0]);
        }
        TownCmdBase fmCmd = cmds.get(args[0]);
        if (fmCmd == null) {
            return null;
        }
        return fmCmd.completeTab(strip0(args));
    }
    
    public Collection<TownCmdBase> getCmds() {
        return cmds.values();
    }
}
