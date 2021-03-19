package de.bloodeko.towns.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.entity.Player;

/**
 * Messages service which helps with translation of messages and
 * returns values used for visual purposes.
 */
public class Messages {
    private static Messages instance;
    private final Map<String, String> map;
    
    /**
     * Enables the Messages Service by parsing the
     * given lines as properties.
     */
    public static void enable(List<String> lines) {
        Map<String, String> map = new HashMap<>();
        Util.parseProperties(map, lines);
        instance = new Messages(map);
    }
    
    private Messages(Map<String, String> map) {
        this.map = map;
    }
    
    /**
     * Sends an translated message to the player and fills
     * variables with the given arguments.
     */
    public static void say(Player player, String key, Object... args) {
        player.sendMessage(Messages.get(key, args));
    }
    
    /**
     * Uses the key to find an translated message and fills
     * variables with the given arguments.
     */
    public static String get(String key, Object... args) {
        try {
            String message = instance.map.get(key);
            if (args.length > 0) {
                message = String.format(message, args);
            }
            if (message == null) {
                message = key;
            }
            return message;
        } catch (Exception ex) {
            return key;
        }
    }
}
