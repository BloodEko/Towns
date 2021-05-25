package de.bloodeko.towns.core.townpeople;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import de.bloodeko.towns.core.townarea.TownRegion;
import de.bloodeko.towns.util.ModifyException;

/**
 * Entity that holds defined groups of people 
 * within the town.
 */
public class TownPeople {
    private UUID owner;
    private Set<UUID> governors;
    private Set<UUID> builders;
    private TownRegion region;
    
    public TownPeople(UUID owner, Set<UUID> governors, Set<UUID> builders, TownRegion region) {
        this.owner = owner;
        this.governors = governors;
        this.builders = builders;
        this.region = region;
    }
    
    public TownPeople(UUID owner, TownRegion region) {
        this(owner, new HashSet<>(), new HashSet<>(), region);
        governors.add(owner);
    }
    
    /**
     * Returns true of the owner equals the id.
     */
    public boolean isOwner(UUID uuid) {
        return Objects.equals(owner, uuid);
    }
    
    /**
     * Returns the owner for this town or null.
     */
    public UUID getOwner() {
        return owner;
    }
    
    /**
     * Sets the UUID as owner, governor and region member.
     */
    public void setOwner(UUID player) {
        region.getMembers().addPlayer(player);
        builders.remove(player);
        governors.add(player);
        owner = player;
    }
    
    /**
     * Returns a copy of the governors.
     */
    public Set<UUID> getGovernors() {
        return new HashSet<>(governors);
    }
    
    /**
     * Returns true if the UUID is the owner or an governor.
     */
    public boolean isGovernor(UUID uuid) {
        return isOwner(uuid) || governors.contains(uuid);
    }
    
    /**
     * Adds the target as governor and region member.
     * Throws an exception if from is no governor or 
     * the target is already a governor.
     */
    public void addGovenor(UUID from, UUID target) {
        if (!isGovernor(from)) {
            throw new ModifyException("town.townpeople.youAreNoGovernor");
        }
        if (isGovernor(target)) {
            throw new ModifyException("town.townpeople.targetIsAlreadyGovernor");
        }
        region.getMembers().addPlayer(target);
        governors.add(target);
    }
    
    /**
     * Removes the target as governor and region member.
     * Throws an exception if from tries to remove itself,
     * or from is no owner, or the target is no governor.
     */
    public void removeGovenor(UUID from, UUID target) {
        if (Objects.equals(from, target)) {
            throw new ModifyException("town.townpeople.cantRemoveYourself");
        }
        if (!isOwner(from)) {
            throw new ModifyException("town.townpeople.youAreNotOwner");
        }
        if (!isGovernor(target)) {
            throw new ModifyException("town.townpeople.targetIsNoGovernor");
        }
        region.getMembers().removePlayer(target);
        governors.remove(target);
    }
    
    /**
     * Returns true if the UUID is a governor or builder.
     */
    public boolean isBuilder(UUID uuid) {
        return isGovernor(uuid) || builders.contains(uuid);
    }
    
    /**
     * Returns a copy of the builders.
     */
    public Set<UUID> getBuilders() {
        return new HashSet<>(builders);
    }
    
    /**
     * Adds the target as builder and region member.
     * Throws an exception if from is no governor or
     * the target is already a builder.
     */
    public void addBuilder(UUID from, UUID target) {
        if (!isGovernor(from)) {
            throw new ModifyException("town.townpeople.youAreNoGovernor");
        }
        if (isBuilder(target)) {
            throw new ModifyException("town.townpeople.targetIsAlreadyBuilder");
        }
        region.getMembers().addPlayer(target);
        builders.add(target);
    }
    
    /**
     * Removes the target as builder and region member.
     * Throws an exception if from is no governor, or the 
     * target is no builder, or the target is a governor.
     */
    public void removeBuilder(UUID from, UUID target) {
        if (!isGovernor(from)) {
            throw new ModifyException("town.townpeople.youAreNoGovernor");
        }
        if (!isBuilder(target) || isGovernor(target)) {
            throw new ModifyException("town.townpeople.targetIsNoBuilder");
        }
        region.getMembers().removePlayer(target);
        builders.remove(target);
    }
}
