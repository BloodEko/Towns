package de.bloodeko.towns.town;

import static de.bloodeko.towns.util.Serialization.asUUID;
import static de.bloodeko.towns.util.Serialization.asUUIDSet;

import java.util.HashMap;
import java.util.HashSet;
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
            throw new ModifyException("town.townpeople.youAreNotOwner");
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
            throw new ModifyException("town.townpeople.youAreNoGovernor");
        }
        if (isGovernor(to)) {
            throw new ModifyException("town.townpeople.targetIsAlreadyGovernor");
        }
        region.getOwners().getPlayerDomain().addPlayer(to);
        governors.add(to);
    }
    
    public void removeGovenor(UUID from, UUID to) {
        if (!isOwner(from)) {
            throw new ModifyException("town.townpeople.youAreNotOwner");
        }
        if (!isGovernor(to)) {
            throw new ModifyException("town.townpeople.targetIsNoGovernor");
        }
        region.getOwners().getPlayerDomain().removePlayer(to);
        governors.remove(to);
    }
    
    public void addBuilder(UUID from, UUID to) {
        if (!isGovernor(from)) {
            throw new ModifyException("town.townpeople.youAreNoGovernor");
        }
        if (isBuilder(to)) {
            throw new ModifyException("town.townpeople.targetIsAlreadyBuilder");
        }
        region.getMembers().getPlayerDomain().addPlayer(to);
        builders.add(to);
    }
    
    public void removeBuilder(UUID from, UUID to) {
        if (!isGovernor(from)) {
            throw new ModifyException("town.townpeople.youAreNoGovernor");
        }
        if (!isBuilder(to)) {
            throw new ModifyException("town.townpeople.targetIsNoBuilder");
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
        return new TownPeople(asUUID(root.get("owner")), asUUIDSet(root.get("governors")),
          asUUIDSet(root.get("builders")), region);
    }
}
