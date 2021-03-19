package de.bloodeko.towns.town;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import de.bloodeko.towns.town.TownArea.ChunkRegion;
import de.bloodeko.towns.util.ModifyException;

public class TownPeople {
    private UUID owner;
    private Set<UUID> governors;
    private Set<UUID> builders;
    private ChunkRegion region;
    
    public TownPeople(UUID owner, Set<UUID> governors, Set<UUID> builders, ChunkRegion region) {
        this.owner = owner;
        this.governors = governors;
        this.builders = builders;
        this.region = region;
    }
    
    public Set<UUID> getGovernors() {
        return new HashSet<>(governors);
    }
    
    public Set<UUID> getBuilders() {
        return new HashSet<>(builders);
    }
    
    public boolean isOwner(UUID uuid) {
        return owner != null && owner.equals(uuid);
    }
    
    public boolean isGovernor(UUID uuid) {
        return isOwner(uuid) || governors.contains(uuid);
    }
    
    public boolean isBuilder(UUID uuid) {
        return isGovernor(uuid) || builders.contains(uuid);
    }
    
    public void setOwner(UUID from, UUID to) {
        if (!isOwner(from)) {
            throw new ModifyException("You are no owner of this town!");
        }
        setOwner(to);
    }
    
    public void setOwner(UUID to) {
        region.getOwners().getPlayerDomain().addPlayer(to);
        governors.add(to);
        owner = to;
    }
    
    public void addGovenor(UUID from, UUID to) {
        if (!isGovernor(from)) {
            throw new ModifyException("You are no govenor of this town!");
        }
        if (isGovernor(to)) {
            throw new ModifyException("The target is already govenor of this town.");
        }
        region.getOwners().getPlayerDomain().addPlayer(to);
        governors.add(to);
    }
    
    public void removeGovenor(UUID from, UUID to) {
        if (!isOwner(from)) {
            throw new ModifyException("You are no owner of this town!");
        }
        if (!isGovernor(to)) {
            throw new ModifyException("The target is no govenor of this town.");
        }
        region.getOwners().getPlayerDomain().removePlayer(to);
        governors.remove(to);
    }
    
    public void addBuilder(UUID from, UUID to) {
        if (!isGovernor(from)) {
            throw new ModifyException("You are no govenor of this town!");
        }
        if (isBuilder(to)) {
            throw new ModifyException("The target is already builder.");
        }
        region.getMembers().getPlayerDomain().addPlayer(to);
        builders.add(to);
    }
    
    public void removeBuilder(UUID from, UUID to) {
        if (!isGovernor(from)) {
            throw new ModifyException("You are no govenor of this town!");
        }
        if (!isBuilder(to)) {
            throw new ModifyException("The target is no builder of this town.");
        }
        region.getMembers().getPlayerDomain().removePlayer(to);
        builders.remove(to);
    }
    
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("owner", owner.toString());
        map.put("governors", governors);
        map.put("builders", builders);
        return map;
    }
    
    public static TownPeople deserialize(Map<String, Object> root, ChunkRegion region) {
        UUID owner = getUUID(root.get("uuid"));
        Set<UUID> governors =  getUUIDSet(root.get("governors"));
        Set<UUID> builders =  getUUIDSet(root.get("builders"));
        return new TownPeople(owner, governors, builders, region);
    }
    
    public static UUID getUUID(Object obj) {
        return UUID.fromString(obj.toString());
    }
    
    @SuppressWarnings("unchecked")
    public static Set<UUID> getUUIDSet(Object obj) {
        Set<UUID> set = new HashSet<>();
        for (String uuid : (List<String>) obj) {
            set.add(UUID.fromString(uuid));
        }
        return set;
    }
}
