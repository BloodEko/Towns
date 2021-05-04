package de.bloodeko.towns.chat;

import java.util.HashMap;
import java.util.Map;

import de.bloodeko.towns.Towns;
import de.bloodeko.towns.util.Messages;

/**
 * Builds up the Module.
 */
public class ChatFactory {
    
    public static void load(Towns plugin) {
        Map<Character, Chat> map = new HashMap<>();
        map.put('.', newLocalChat());
        map.put('+', newTeamChat());
        map.put('$', newChat("trade"));
        map.put('!', newChat("global"));
        map.put('?', newChat("help"));
        
        ChatListener listener = new ChatListener(map, map.get('!'));
        plugin.getServer().getPluginManager().registerEvents(listener, plugin);
    }
    
    public static Chat newLocalChat() {
        String name = Messages.get("chats.local.name");
        String prefix = Messages.get("chats.local.prefix");
        String suffix = Messages.get("chats.local.suffix");
        return new LocalChat(name, prefix, suffix, 120);
    }
    
    public static Chat newTeamChat() {
        String name = Messages.get("chats.team.name");
        String prefix = Messages.get("chats.team.prefix");
        String suffix = Messages.get("chats.team.suffix");
        return new TeamChat(name, prefix, suffix, "team.chat");
    }
    
    public static Chat newChat(String id) {
        String name = Messages.get("chats." + id + ".name");
        String prefix = Messages.get("chats." + id + ".prefix");
        String suffix = Messages.get("chats." + id + ".suffix");
        return new Chat(name, prefix, suffix);
    }
}
