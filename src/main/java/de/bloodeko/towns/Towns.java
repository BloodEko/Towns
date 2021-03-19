package de.bloodeko.towns;

import org.bukkit.plugin.java.JavaPlugin;

import de.bloodeko.towns.protect.AProtectFactory;
import de.bloodeko.towns.session1.BukkitFactory;
import de.bloodeko.towns.session1.ChunkMap;
import de.bloodeko.towns.session1.MapCmd.MapClickHandler;
import de.bloodeko.towns.session1.TownCommandListener;

/**
 * TownCommand (handler system with tab completion)
 * ChunkHandler (variable in Towns)
 * ChunkDisplay (vriable in Towns)
 * /stadt map (uses chunkdisplay. with zoom etc.)
 * 
 * one package per feature.
 * > chunkmap. towns. claiming. map. info. commands. util.
 * > one package per command. features that are used by multiple commands, get an own package.
 * 
 * FoundCMD
 * Movement/LiquidFlow from/to, for enter/leave/move.
 */
public class Towns extends JavaPlugin {
    
    private ChunkMap chunkHandler;
    private TownCommandListener cmdHandler;
    private MapClickHandler clickHandler;
    
    @Override
    public void onEnable() {
        registerEvents();
        chunkHandler = BukkitFactory.newChunkHandler(this);
        cmdHandler = BukkitFactory.newCmdListener(this);
        registerCommand();
        AProtectFactory.register(this);
    }
    
    private void registerCommand() {
        getCommand("town").setExecutor(cmdHandler);
        getCommand("town").setTabCompleter(cmdHandler);
    }
    
    private void registerEvents() {
        clickHandler = BukkitFactory.newMapClickHandler();
        getServer().getPluginManager().registerEvents(clickHandler, this);
    }

    public ChunkMap getChunkMap() {
        return chunkHandler;
    }

    public MapClickHandler getMapClickHandler() {
        return clickHandler;
    }
}
