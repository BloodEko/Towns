package de.bloodeko.towns.core.townchat.domain;

import java.util.Collections;
import java.util.Set;

/**
 * Represents the chats a player was added to 
 * and is allowed interact with.
 */
public class UserChatEntity {
    private final Set<Integer> chats;
    private Integer selected;
    
    UserChatEntity(Integer selected, Set<Integer> chats) {
        this.selected = selected;
        this.chats = chats;
    }
    
    /**
     * Sets a selected chat for the user, or null.
     */
    void select(Integer id) {
        selected = id;
    }
    
    /**
     * Gets a selected chat for the user, or null.
     */
    Integer getSelected() {
        return selected;
    }
    
    /**
     * Adds the user to chat, so it can select these.
     */
    void add(Integer id) {
        chats.add(id);
    }
    
    /**
     * Removes the player from a chat, so it's no longer selectable.
     * Updates the players selection, if it is affected.
     */
    void remove(Integer id) {
        chats.remove(id);
        if (selected == id) {
            selected = null;
        }
    }
    
    /**
     * Returns a view of chats the player is inside.
     */
    Set<Integer> getChats() {
        return Collections.unmodifiableSet(chats);
    }
}
