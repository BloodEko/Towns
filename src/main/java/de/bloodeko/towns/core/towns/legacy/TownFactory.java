package de.bloodeko.towns.core.towns.legacy;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.world.World;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;

import de.bloodeko.towns.Services;
import de.bloodeko.towns.core.townarea.legacy.ChunkRegion;
import de.bloodeko.towns.core.townarea.legacy.ClaimRules;
import de.bloodeko.towns.core.townarea.legacy.TownArea;
import de.bloodeko.towns.core.townarea.legacy.TownSides;
import de.bloodeko.towns.core.townpeople.TownPeople;
import de.bloodeko.towns.core.townsettings.TownSettings;
import de.bloodeko.towns.core.townsettings.legacy.Settings;
import de.bloodeko.towns.core.townstages.StageFactory;
import de.bloodeko.towns.util.Chunk;
import de.bloodeko.towns.util.Node;
import de.bloodeko.towns.util.Node.Pair;

public class TownFactory {
    public static final String WORLD = "world";

    /**
     * Creates a new empty ChunkMap.
     */
    public static ChunkMap newChunkMap() {
        return new ChunkMap(new HashMap<>());
    }
    
    /**
     * Creates a default town, without registering it to any services.
     */
    public static Town newTown(ChunkMap map, int id, String name, Chunk chunk, UUID owner) {
        Set<Chunk> chunks = new HashSet<>();
        chunks.add(chunk);
        
        ChunkRegion region = newChunkRegion(chunks, id, getWorldManager());
        region.getMembers().addPlayer(owner);
        
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
        return new TownPeople(owner, governors, new HashSet<>(), region);
    }
    
    /**
     * Creates a area from the chunks with updated TownSides and a resized
     * ChunkRegion that covers all chunks.
     */
    public static TownArea newArea(Set<Chunk> chunks, ChunkRegion region) {
        TownArea area = new TownArea(chunks, new TownSides(0, 0, 0, 0), 
          new ClaimRules(), region);
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
        World world = BukkitAdapter.adapt(Bukkit.getWorld(WORLD));
        return WorldGuard.getInstance().getPlatform().getRegionContainer().get(world);
    }
    
    /**
     * Creates default settings, which also update the ChunkRegion flags.
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static TownSettings newSettings(ChunkRegion region, String name) {
        TownSettings settings = new TownSettings(new HashSet<>(),
          (Map) region.getFlags(), StageFactory.getStartStage());
        
        Settings.NAME.set(settings.getFlags(), name);
        return settings.updateFlags();
    }

    /**
     * Creates a new Registry.
     */
    public static TownRegistry newRegistry(int id) {
        return new TownRegistry(id);
    }

    /**
     * Creates and registers towns from the towndata provided.
     */
    public static void loadTowns(Node towns) {
        for (Pair pair : towns.entries()) {
            Town town = Town.deserialize(Integer.valueOf(pair.key), (Node) pair.value, 
              Services.settings(), Services.regions());
            Bukkit.getPluginManager().callEvent(new TownLoadEvent(town));
        }
    }
}
