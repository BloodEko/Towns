package de.bloodeko.towns.cmds;

import java.util.Arrays;
import java.util.List;

import org.bukkit.entity.Player;

import de.bloodeko.towns.town.ChunkMap;
import de.bloodeko.towns.town.Town;
import de.bloodeko.towns.town.TownSettings;
import de.bloodeko.towns.util.Util;

public abstract class BooleanSetting extends CmdBase {
    private String name;
    
    public BooleanSetting(ChunkMap map, String name) {
        super(map);
        this.name = name;
    }
    
    @Override
    public void execute(Player player, String[] args) {
        if (args.length == 0) {
            sendError(player);
            return;
        }
        Town town = getTown(player);
        if (args[0].equals("true")) {
            player.sendMessage(name + " is now enabled.");
            setValue(town.getSettings(), true);
            return;
        }
        if (args[0].equals("false")) {
            player.sendMessage(name + " is now disabled.");
            setValue(town.getSettings(), false);
            return;
        }
        sendError(player);
    }
    
    public abstract void setValue(TownSettings settings, boolean value);
    public abstract boolean readValue(TownSettings settings);
    
    private void sendError(Player player) {
        player.sendMessage("Specify a valid boolean.");
    }
    
    @Override
    public List<String> completeTab(String[] args) {
        List<String> list = Arrays.asList("true", "false");
        return Util.filterList(list, args);
    }
    
    public String getName() {
        return name;
    }
}
