package de.bloodeko.towns.town;

import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.entity.Player;

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
    private UUID owner;
    private Location warp;
    private TownArea area;
    
    public Town(int id, String name, TownSettings settings, TownArea area, UUID owner) {
        this.id = id;
        this.name = name;
        this.settings = settings;
        this.area = area;
        this.owner = owner;
    }
    
    public int getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public void rename(TownRegistry registry, String name) {
        registry.rename(this, name);
        this.name = name;
    }

    public UUID getOwner() {
        return owner;
    }
    
    public TownArea getArea() {
        return area;
    }

    public boolean isOwner(Player player) {
        return owner == player.getUniqueId();
    }

    public boolean isAllowedToBuild() {
        return settings.canBuild();
    }
    
    public TownSettings getSettings() {
        return settings;
    }
    
    @Override
    public String toString() {
        return name;
    }

    public void setWarp(Location location) {
        this.warp = location;
    }
    
    public Location getWarp() {
        return warp;
    }
}
