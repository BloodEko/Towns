package de.bloodeko.towns.util.chat;

import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 * The name will be used for display, prefixes the player with 
 * the prefixes and prefixes the message with the suffix.
 */
public class Chat {
    private String name;
    private String prefix;
    private String suffix;
    
    public Chat(String name, String prefix, String suffix) {
        this.name = name;
        this.prefix = prefix;
        this.suffix = suffix;
    }
    
    public String getName() {
        return name;
    }
    
    public boolean allows(Player player) {
        return true;
    }
    
    public void handle(AsyncPlayerChatEvent event) {
        event.setFormat(prefix + event.getFormat());
        event.setMessage(suffix + event.getMessage());
    }
}
