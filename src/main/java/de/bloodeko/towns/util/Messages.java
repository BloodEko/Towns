package de.bloodeko.towns.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.entity.Player;

public class Messages {
    private static Messages instance;
    private Map<String, String> map;
    
    /**
     * Enables the messages service and loads
     * messages from the Stream.
     */
    public static void enable(List<String> lines) {
        instance = new Messages();
        instance.map = new HashMap<>();
        Util.parseProperties(instance.map, lines);
    }
    
    /**
     * Returns the instance of the messages service
     * or null.
     */
    public static Messages getInstance() {
        return instance;
    }
    
    /**
     * Sends a translated message to the player.
     */
    public static void say(Player player, String key, Object... args) {
        player.sendMessage(Messages.get(key, args));
    }
    
    /**
     * Returns the translated message or the key.
     */
    public static String get(String key, Object... args) {
        try {
            String message = getInstance().map.get(key);
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
