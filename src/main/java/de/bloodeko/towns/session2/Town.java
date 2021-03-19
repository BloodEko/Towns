package de.bloodeko.towns.session2;

import java.util.Set;

import org.bukkit.entity.Player;

import de.bloodeko.towns.session1.Chunk;
import de.bloodeko.towns.session1.ChunkMap;

/**
 * TownMembers, TownSerializer, TownArea,
 * TownExpandContractRules.
 *
 *  private TownArea newArea;
 *  private TownMembers members;
 *  private UUID owner;
 *  private Set<UUID> residents;
 *  private Set<UUID> builders;
 */
public class Town {
    private int id;
    private String name;
    private TownSettings settings;
    private Set<Chunk> area;
    
    
    public Town(int id, String name, TownSettings settings, Set<Chunk> area) {
        this.id = id;
        this.name = name;
        this.settings = settings;
        this.area = area;
    }
    
    public int getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public int getSize() {
        return area.size();
    }
    
    public void expand(ChunkMap map, Chunk chunk) {
        map.setTown(chunk, this);
        area.add(chunk);
    }
    
    public void contract(ChunkMap map, Chunk chunk) {
        map.removeTown(chunk);
        area.remove(chunk);
    }

    public boolean isAllowedToClaim(Player claimer) {
        return settings.canClaim();
    }

    public boolean isAllowedToBuild(Player player) {
        return settings.canBuild();
    }
    
    public TownSettings getSettings() {
        return settings;
    }
    
    @Override
    public String toString() {
        return name;
    }
}
