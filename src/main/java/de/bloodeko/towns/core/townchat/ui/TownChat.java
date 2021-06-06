package de.bloodeko.towns.core.townchat.ui;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import de.bloodeko.towns.Services;
import de.bloodeko.towns.util.Messages;
import de.bloodeko.towns.util.chat.Chat;

/**
 * Represents the town chat channel. Handles incoming events
 * and dispatches players to their selected chat channel.
 */
public class TownChat extends Chat {

    public TownChat(String name, String prefix, String suffix) {
        super(name, prefix, suffix);
    }
    
    @Override
    public void handle(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        event.getRecipients().clear();
        event.getRecipients().add(player);
        
        Integer id = Services.townchat().getSelected(player.getUniqueId());
        if (id == null) {
            Messages.say(event.getPlayer(), "chats.town.noChatSelected");
            event.setCancelled(true);
            return;
        }
        List<Player> list = Services.townchat().getOnlinePlayers(id);
        event.getRecipients().addAll(list);
        
        String town = Services.nameservice().getName(id);
        event.setFormat(Messages.get("chats.town.format", town, player.getName()));
        event.setMessage(Messages.get("chats.town.suffix") + event.getMessage());
    }
}
