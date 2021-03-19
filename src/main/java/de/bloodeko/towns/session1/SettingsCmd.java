package de.bloodeko.towns.session1;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.entity.Player;

import de.bloodeko.towns.session2.Town;
import de.bloodeko.towns.session2.TownSettings;

/**
 * 
 */
public class SettingsCmd extends TownCmdBase {
    private TownCmdHandler handler;
    
    public SettingsCmd(ChunkMap map, TownCmdHandler handler) {
        super(map);
        this.handler = handler;
    }
    
    @Override
    public void execute(Player player, String[] args) {
        if (args.length == 0) {
            displaySettings(getTown(player), player);
            return;
        }
        handler.onCommand(player, args);
    }

    @Override
    public List<String> completeTab(String[] args) {
        return handler.onTabComplete(args);
    }
    
    /**
     * 
     */
    public void displaySettings(Town town, Player player) {
        if (town == null) {
            player.sendMessage("There is no town at your location.");
            return;
        }
        player.sendMessage("---- Settings[" + town.getName() + "] ----");
        for (TownCmdBase base : handler.getCmds()) {
            BooleanSetting cmd = (BooleanSetting) base;
            player.sendMessage(cmd.getName() + ": " + cmd.readValue(town.getSettings()));
        }
    }
    
    /**
     * 
     */
    public static class SettingsCmdFactory {
        
        public static SettingsCmd newCommand(ChunkMap map) {
            return new SettingsCmd(map, newCommandHandler(map));
        }
        
        public static TownCmdHandler newCommandHandler(ChunkMap map) {
            Map<String, TownCmdBase> cmds = new HashMap<>();
            TownCmdHandler handler = new TownCmdHandler(cmds);
            cmds.put("claiming", new ClaimingCmd(map, "Claiming"));
            cmds.put("building", new BuildingCmd(map, "Building"));
            return handler;
        }
    }
    
    /**
     *
     */
    public static abstract class BooleanSetting extends TownCmdBase {
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
            if (town == null) {
                player.sendMessage("There is no town at your location.");
                return;
            }
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
            return Arrays.asList("true", "false");
        }
        
        public String getName() {
            return name;
        }
    }
    
    /**
     * 
     */
    public static class ClaimingCmd extends BooleanSetting {
        
        public ClaimingCmd(ChunkMap map, String name) {
            super(map, name);
        }
        
        public void setValue(TownSettings settings, boolean value) {
            settings.setClaiming(value);
        }
        public boolean readValue(TownSettings settings) {
            return settings.canClaim();
        }
    }
    
    /**
     * 
     */
    public static class BuildingCmd extends BooleanSetting {
        
        public BuildingCmd(ChunkMap map, String name) {
            super(map, name);
        }
        
        public void setValue(TownSettings settings, boolean value) {
            settings.setBuilding(value);
        }
        public boolean readValue(TownSettings settings) {
            return settings.canBuild();
        }
    }
    
}
