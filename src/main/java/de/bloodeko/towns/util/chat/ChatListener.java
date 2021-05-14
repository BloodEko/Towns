package de.bloodeko.towns.util.chat;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import de.bloodeko.towns.util.Messages;

/**
 * Service that handles chat messages and entity that holds 
 * data about existing chats and user settings.
 */
public class ChatListener implements Listener {
    private Map<UUID, Chat> users = new HashMap<>();
    private Map<Character, Chat> chats;
    private Chat global;

    public ChatListener(Map<Character, Chat> chats, Chat global) {
        this.chats = chats;
        this.global = global;
    }
    
    /**
     * Determines which chat to act on. If the player isn't allowed to it,
     * returns and uses the global chat. Switches the chat if specified.
     * Blocks messages without content. Sets the content to the event and 
     * lets the specified chat handle it.
     */
    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        ChatParser parse = new ChatParser(chats, event.getMessage());
        Chat chat = getUserChat(parse.getChat(), event.getPlayer().getUniqueId());
        
        if (!chat.allows(event.getPlayer())) {
            global.handle(event);
            return;
        }
        if (parse.switchTo()) {
            users.put(event.getPlayer().getUniqueId(), chat);
            Messages.say(event.getPlayer(), "chats.switched", chat.getName());
        }
        if (parse.getContent().length() == 0) {
            event.setCancelled(true);
            return;
        }
        
        event.setMessage(parse.getContent());
        chat.handle(event);
    }
    
    /**
     * If the player specified a chat returns it.
     * If the player cached a chat returns it.
     * Otherwise returns the global chat.
     */
    private Chat getUserChat(Chat chat, UUID uuid) {
        if (chat == null) {
            chat = users.get(uuid);
        }
        if (chat == null) {
            chat = global;
        }
        return chat;
    }
}
