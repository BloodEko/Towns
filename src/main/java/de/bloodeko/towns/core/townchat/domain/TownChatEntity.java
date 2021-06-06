package de.bloodeko.towns.core.townchat.domain;

import java.util.Collections;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Represents a thread-safe chat channel from a town, 
 * containing all online/offline players.
 */
public class TownChatEntity {
    private final Set<UUID> players;

    TownChatEntity() {
        this.players = ConcurrentHashMap.newKeySet();
    }
    
    /**
     * Adds a player to this town chat.
     */
    void add(UUID uuid) {
        players.add(uuid);
    }
    
    /**
     * Removes a player from this town chat.
     */
    void remove(UUID uuid) {
        players.remove(uuid);
    }
    
    /**
     * Returns a view of all players in the chat.
     */
    Set<UUID> getView() {
        return Collections.unmodifiableSet(players);
    }
}
