package de.bloodeko.towns.util.chat;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import de.bloodeko.towns.util.Messages;

/**
 * LocalChat which only sends messages to players
 * that are in range of the message sender.
 */
public class LocalChat extends Chat {
    private int range;
    
    public LocalChat(String name, String prefix, String suffix, int range) {
        super(name, prefix, suffix);
        this.range = range;
    }

    @Override
    public void handle(AsyncPlayerChatEvent event) {
        super.handle(event);
        List<Player> near = getNearPlayers(event.getPlayer().getLocation(), range);
        event.getRecipients().clear();
        event.getRecipients().addAll(near);
        if (near.size() == 1) {
            event.setMessage(event.getMessage() + '\n' 
            + Messages.get("chats.noNearPlayers"));
        }
    }
    
    private List<Player> getNearPlayers(Location loc, int distance) {
        List<Player> list = new ArrayList<>();
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getLocation().distance(loc) < distance) {
                list.add(player);
            }
        }
        return list;
    }
}
