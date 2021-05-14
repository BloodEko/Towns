package de.bloodeko.towns.util.cmds;

import static de.bloodeko.towns.util.Util.filterList;
import static de.bloodeko.towns.util.Util.strip0;

import java.util.List;
import java.util.Map;

import org.bukkit.entity.Player;

/**
 * Dispatches registered sub-commands.
 * Also handles their tab completions.
 */
public class CmdHandler {
    private Map<String, CmdBase> cmds;
    
    public CmdHandler(Map<String, CmdBase> cmds) {
        this.cmds = cmds;
    }
    
    public void register(String key, CmdBase cmd) {
        this.cmds.put(key, cmd);
    }
    
    /**
     * Dispatches the first argument to a sub-command
     * and executes it, with the first argument cut off.
     */
    public boolean onCommand(Player player, String[] args) {
        if (args.length == 0) {
            return false;
        }
        CmdBase fmCmd = cmds.get(args[0]);
        if (fmCmd == null) {
            return false;
        }
        fmCmd.execute(player, strip0(args));
        return true;
    }
    
    /**
     * Dispatches tab-completion to a sub-command with the first 
     * argument cut off. and returns the resulting filtered List.
     */
    public List<String> onTabComplete(String[] args, Player player) {
        if (args.length == 1) {
            return filterList(cmds.keySet(), args[0]);
        }
        CmdBase fmCmd = cmds.get(args[0]);
        if (fmCmd == null) {
            return null;
        }
        List<String> simple = fmCmd.completeTab(strip0(args));
        if (simple == null) {
            return fmCmd.completeTab(strip0(args), player);
        }
        return simple;
    }
}
