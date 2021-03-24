package de.bloodeko.towns.town.people;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.google.common.base.Objects;

import de.bloodeko.towns.town.Town;
import de.bloodeko.towns.town.area.ChunkRegion;
import de.bloodeko.towns.util.ModifyException;
import de.bloodeko.towns.util.Node;

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
    
    public boolean isOwner(UUID uuid) {
        return owner != null && owner.equals(uuid);
    }

    public UUID getOwner() {
        return owner;
    }
    
    public void setOwner(UUID to) {
        region.getMembers().addPlayer(to);
        governors.add(to);
        owner = to;
    }
    
    public void setOwner(UUID from, UUID to) {
        if (!isOwner(from)) {
            throw new ModifyException("town.townpeople.youAreNotOwner");
        }
        setOwner(to);
    }
    
    public Set<UUID> getGovernors() {
        return new HashSet<>(governors);
    }
    public boolean isGovernor(UUID uuid) {
        return isOwner(uuid) || governors.contains(uuid);
    }
    
    public void addGovenor(UUID from, UUID to, Town town) {
        if (!isGovernor(from)) {
            throw new ModifyException("town.townpeople.youAreNoGovernor");
        }
        if (isGovernor(to)) {
            throw new ModifyException("town.townpeople.targetIsAlreadyGovernor");
        }
        Bukkit.getPluginManager().callEvent(new AddGovernorEvent(to, town));
        region.getMembers().addPlayer(to);
        governors.add(to);
    }
    
    public void removeGovenor(UUID from, UUID to, Town town) {
        if (Objects.equal(from, to)) {
            throw new ModifyException("town.townpeople.cantRemoveYourself");
        }
        if (!isOwner(from)) {
            throw new ModifyException("town.townpeople.youAreNotOwner");
        }
        if (!isGovernor(to)) {
            throw new ModifyException("town.townpeople.targetIsNoGovernor");
        }
        Bukkit.getPluginManager().callEvent(new RemoveGovernorEvent(to, town));
        region.getMembers().removePlayer(to);
        governors.remove(to);
    }
    
    public boolean isBuilder(UUID uuid) {
        return isGovernor(uuid) || builders.contains(uuid);
    }
    
    public Set<UUID> getBuilders() {
        return new HashSet<>(builders);
    }
    
    public void addBuilder(UUID from, UUID to) {
        if (!isGovernor(from)) {
            throw new ModifyException("town.townpeople.youAreNoGovernor");
        }
        if (isGovernor(to)) {
            throw new ModifyException("town.townpeople.targetIsAlreadyGovernor");
        }
        if (isBuilder(to)) {
            throw new ModifyException("town.townpeople.targetIsAlreadyBuilder");
        }
        region.getMembers().addPlayer(to);
        builders.add(to);
    }
    
    public void removeBuilder(UUID from, UUID to) {
        if (!isGovernor(from)) {
            throw new ModifyException("town.townpeople.youAreNoGovernor");
        }
        if (!isBuilder(to) || isGovernor(to)) {
            throw new ModifyException("town.townpeople.targetIsNoBuilder");
        }
        region.getMembers().removePlayer(to);
        builders.remove(to);
    }
    
    public Node serialize() {
        Node node = new Node();
        node.set("owner", owner);
        node.set("governors", governors);
        node.set("builders", builders);
        return node;
    }
    
    public static TownPeople deserialize(Node people, ChunkRegion region) {
        Set<UUID> governors = people.getUUIDSet("governors");
        Set<UUID> builders = people.getUUIDSet("builders");
        for (UUID uuid : governors) {
            region.getMembers().addPlayer(uuid);
        }
        for (UUID uuid : builders) {
            region.getMembers().addPlayer(uuid);
        }
        return new TownPeople(people.getUUID("owner"), governors, builders, region);
    }
    
    /*
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("owner", owner.toString());
        map.put("governors", governors);
        map.put("builders", builders);
        return map;
    }
    
    public static TownPeople deserialize(Map<String, Object> root, ChunkRegion region) {
        Set<UUID> governors = asUUIDSet(root.get("governors"));
        Set<UUID> builders = asUUIDSet(root.get("builders"));
        for (UUID uuid : governors) {
            region.getMembers().addPlayer(uuid);
        }
        for (UUID uuid : builders) {
            region.getMembers().addPlayer(uuid);
        }
        return new TownPeople(asUUID(root.get("owner")), governors, builders, region);
    }*/
    
    public static class AddGovernorEvent extends Event {
        private static HandlerList handlers = new HandlerList();
        public final UUID uuid;
        public final Town town;
        
        public AddGovernorEvent(UUID uuid, Town town) {
            this.uuid = uuid;
            this.town = town;
        }

        @Override
        public HandlerList getHandlers() {
            return handlers;
        }
    }
    
    public static class RemoveGovernorEvent extends Event {
        private static HandlerList handlers = new HandlerList();
        public final UUID uuid;
        public final Town town;
        
        public RemoveGovernorEvent(UUID uuid, Town town) {
            this.uuid = uuid;
            this.town = town;
        }

        @Override
        public HandlerList getHandlers() {
            return handlers;
        }
    }
}
