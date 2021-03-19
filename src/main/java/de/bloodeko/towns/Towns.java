package de.bloodeko.towns;

import org.bukkit.plugin.java.JavaPlugin;

import de.bloodeko.towns.cmds.CmdFactory;
import de.bloodeko.towns.listeners.ListenerFactory;
import de.bloodeko.towns.town.ChunkMap;
import de.bloodeko.towns.town.TownFactory;
import de.bloodeko.towns.town.TownRegistry;
import de.bloodeko.towns.util.BukkitFactory;

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
 * 
 * TODO: Tierschutz, PVP.
 */
public class Towns extends JavaPlugin {

    private ChunkMap chunkMap;
    private TownRegistry towns;
    
    @Override
    public void onEnable() {
        chunkMap = BukkitFactory.newChunkHandler(this);
        towns = TownFactory.newRegistry(chunkMap);
        CmdFactory.init(this);
        ListenerFactory.init(this);
    }

    public ChunkMap getChunkMap() {
        return chunkMap;
    }
    
    public TownRegistry getTownRegistry() {
        return towns;
    }
}
