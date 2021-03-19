package de.bloodeko.towns.town;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.world.World;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;

import de.bloodeko.towns.town.Town.TownData;
import de.bloodeko.towns.town.TownArea.ChunkRegion;
import de.bloodeko.towns.town.TownArea.TownSides;
import de.bloodeko.towns.util.Chunk;

public class TownFactory {
    
    /**
     * Creates a default town, without registering it to any services.
     */
    public static Town newTown(ChunkMap map, int id, String name, Chunk chunk, UUID owner) {
        Set<Chunk> chunks = new HashSet<>();
        chunks.add(chunk);
        
        ChunkRegion region = newChunkRegion(chunks, id, getWorldManager());
        TownArea area = newArea(chunks, region);
        TownPeople people = newTownPeople(owner, region);
        TownSettings settings = newSettings(region, name);
        
        return new Town(id, settings, area, people);
    }
    
    /**
     * Creates default TownPeople, with setting the UUID as owner and governor.
     */
    public static TownPeople newTownPeople(UUID owner, ChunkRegion region) {
        Set<UUID> governors = new HashSet<>();
        governors.add(owner);
        return new TownPeople(owner, governors, new HashSet<>(), new HashSet<>(), region);
    }
    
    /**
     * Creates a area from the chunks with updated TownSides and a resized
     * ChunkRegion that covers all chunks.
     */
    public static TownArea newArea(Set<Chunk> chunks, ChunkRegion region) {
        TownArea area = new TownArea(chunks, new TownSides(0, 0, 0, 0), 
          new ChunkRules(), region);
        area.updateShape();
        return area;
    }
    
    /**
     * Creates a ChunkRegion with the chunks and an area of 0,0,0.
     * Feeds the RegionManager, used for later resizing calls.
     */
    public static ChunkRegion newChunkRegion(Set<Chunk> chunks, int id, RegionManager manager) {
        return new ChunkRegion(manager, chunks, "town_" + id, true, 
          BlockVector3.ZERO, BlockVector3.ZERO);
    }
    
    /**
     * Gets the RegionManager for the world named "world".
     */
    public static RegionManager getWorldManager() {
        World world = BukkitAdapter.adapt(Bukkit.getWorld("world"));
        return WorldGuard.getInstance().getPlatform().getRegionContainer().get(world);
    }
    
    /**
     * Creates default settings, which also update the ChunkRegion flags.
     */
    public static TownSettings newSettings(ChunkRegion region, String name) {
        TownSettings settings = new TownSettings(region, name, new HashMap<>());
        settings.updateFlags();
        return settings;
    }

    /**
     * Creates a new Registry.
     */
    public static TownRegistry newRegistry(ChunkMap map, int id) {
        return new TownRegistry(new HashMap<>(), map, id);
    }

    /**
     * Creates and registers towns from the TownData provided.
     */
    public static void loadTowns(List<TownData> towns, ChunkMap map, TownRegistry registry, RegionManager manager) {
        for (TownData data : towns) {
            Town town = newTown(data, manager);
            registerTown(town, map, registry, manager);
            
        }
    }
    
    /**
     * Creates a town from TownData. The RegionManager will be fed to the
     * ChunkRegion and used for later resizing calls.
     */
    public static Town newTown(TownData data, RegionManager manager) {
        Set<Chunk> chunks = data.area.chunks;
        ChunkRegion region = newChunkRegion(chunks, data.id, manager);
        
        TownArea area = newArea(chunks, region);
        TownPeople people = newTownPeople(data.people.owner, region);
        TownSettings settings = newSettings(region, data.settings.name);
        
        return new Town(data.id, settings, area, people);
    }
    
    /**
     * Registers the town to all used services.
     */
    public static void registerTown(Town town, ChunkMap map, TownRegistry registry, RegionManager manager) {
        for (Chunk chunk : town.getArea().getChunks()) {
            map.setTown(chunk, town);
        }
        registry.add(town);
        manager.addRegion(town.getArea().getRegion());
    }
    
    /**
     * Removes the town from all used services.
     */
    public static void unregisterTown(Town town, ChunkMap map, TownRegistry registry, RegionManager manager) {
        for (Chunk chunk : town.getArea().getChunks()) {
            map.removeTown(chunk);
        }
        registry.remove(town.getSettings().getName());
        manager.removeRegion("town_" + town.getId());
    }
}
