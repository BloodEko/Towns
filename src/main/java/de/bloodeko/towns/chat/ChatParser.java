package de.bloodeko.towns.chat;

import java.util.Map;

/**
 * Bundles logic for parsing chat messages,
 * so presentation code can act on it.
 */
public class ChatParser {
    private String message;
    private Chat resultChat;
    private boolean switchTo;
    
    /**
     * Creates a result value from the available chats and the message.
     * Prefixed with a chat char, sets the resultChat, prefixed with two 
     * chat chars, sets switching. Trims and strips chat symbols away.
     */
    public ChatParser(Map<Character, Chat> chats, String msg) {
        if (msg.length() == 0) {
            message = "";
            return;
        }
        if (msg.length() == 1) {
            message = msg;
            return;
        }
        message = msg;
        
        char char0 = msg.charAt(0);
        char char1 = msg.charAt(1);
        
        Chat chat = chats.get(char0);
        if (chat == null) {
            return;
        }
        resultChat = chat;
        
        if (char0 == char1) {
            switchTo = true;
            message = msg.substring(2);
        } else {
            message = msg.substring(1);
        }
        message = message.trim();
    }
    
    /**
     * Returns the message without prefixes.
     */
    public String getContent() {
        return message;
    }
    
    /**
     * Returns the specified chat or null.
     */
    public Chat getChat() {
        return resultChat;
    }
    
    /**
     * Returns whether to switch to that chat.
     */
    public boolean switchTo() {
        return switchTo;
    }
}
