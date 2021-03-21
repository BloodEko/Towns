package de.bloodeko.towns.chat;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import de.bloodeko.towns.util.Messages;

/**
 * Handles chat messages with their prefixes
 * and delegates them to sub-handlers.
 */
public class ChatListener implements Listener {
    private Map<Character, Chat> chats;
    private Map<UUID, Chat> users = new HashMap<>();

    public ChatListener(Map<Character, Chat> chats) {
        this.chats = chats;
    }
    
    /**
     * Loads a chat to this listener.
     */
    public void putChat(char ch, Chat chat) {
        chats.put(ch, chat);
    }
    
    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        switchChat(event);
        Chat chat = getChat(event);
        if (chat == null) {
            return;
        }
        if (event.getMessage().length() == 0) {
            event.setCancelled(true);
            return;
        }
        chat.handle(event);
    }
    
    /**
     * Checks for a single chat symbol, to return
     * a chat. Otherwise returns the cached chat
     * or null.
     */
    private Chat getChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        Chat chat = getPrefixChat(event);
        if (chat != null) {
            return chat;
        }
        chat = users.get(player.getUniqueId());
        if (chat == null || !chat.allows(player)) {
            return null;
        }
        return chat;
    }

    /**
     * Checks for a single chat symbols. If found
     * return the chat and trims the message.
     */
    private Chat getPrefixChat(AsyncPlayerChatEvent event) {
        String message = event.getMessage();
        Player player = event.getPlayer();
        if (message.length() == 0) {
            return null;
        }
        Chat chat = chats.get(message.charAt(0));
        if (chat == null || !chat.allows(player)) {
            return null;
        }
        event.setMessage(event.getMessage().substring(1).trim());
        return chat;
    }
    
    /**
     * Checks for a double chat symbols. If found
     * switches the chat and trims the message.
     */
    private void switchChat(AsyncPlayerChatEvent event) {
        String message = event.getMessage();
        Player player = event.getPlayer();
        if (message.length() < 2) {
            return;
        }
        char ch1 = message.charAt(0);
        char ch2 = message.charAt(1);
        if (ch1 != ch2) {
            return;
        }
        Chat chat = chats.get(ch1);
        if (chat == null || !chat.allows(player)) {
            return;
        }
        users.put(player.getUniqueId(), chat);
        event.setMessage(event.getMessage().substring(2).trim());
        Messages.say(event.getPlayer(), "chats.switched", chat.getName());
    }
}
