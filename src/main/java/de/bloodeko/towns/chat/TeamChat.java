package de.bloodeko.towns.chat;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 * TeamChat which only sends messages to players
 * with a specific permission Node.
 */
public class TeamChat extends Chat {
    private String permission;
    
    public TeamChat(String name, String prefix, String suffix, String permission) {
        super(name, prefix, suffix);
        this.permission = permission;
    }
    
    @Override
    public boolean allows(Player player) {
        return player.hasPermission(permission);
    }

    @Override
    public void handle(AsyncPlayerChatEvent event) {
        super.handle(event);
        List<Player> authroized = getPermissionPlayers(permission);
        event.getRecipients().clear();
        event.getRecipients().addAll(authroized);
    }
    
    public List<Player> getPermissionPlayers(String permission) {
        List<Player> list = new ArrayList<>();
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.hasPermission(permission)) {
                list.add(player);
            }
        }
        return list;
    }
}
