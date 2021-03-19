package de.bloodeko.towns.town;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import de.bloodeko.towns.town.TownArea.ChunkRegion;
import de.bloodeko.towns.util.ModifyException;


// TODO own object which also has the ProtectedRegion as dependency.
//  town has the object has dependency.
//  town has getter for the object.
//  object provides stuff to info command
//  town gets permit/kick commands
//  town gets found command.
public class TownPeople {
    private UUID owner;
    private Set<UUID> governors;
    private Set<UUID> builders;
    //private Set<UUID> citizens;
    private ChunkRegion region;
    
    public TownPeople(UUID owner, Set<UUID> governors, Set<UUID> builders, Set<UUID> citizens, ChunkRegion region) {
        this.owner = owner;
        this.governors = governors;
        this.builders = builders;
        //this.citizens = citizens;
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
    
    public TownPeopleData getData() {
        return new TownPeopleData(owner, governors, builders, region);
    }
    
    public static class TownPeopleData {
        public UUID owner;
        public Set<UUID> governors;
        public Set<UUID> builders;
        public ChunkRegion region;
        
        public TownPeopleData(UUID owner, Set<UUID> governors, Set<UUID> builders, ChunkRegion region) {
            this.owner = owner;
            this.governors = governors;
            this.builders = builders;
            this.region = region;
        }
    }
}
