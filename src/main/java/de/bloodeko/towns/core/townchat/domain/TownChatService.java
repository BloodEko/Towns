package de.bloodeko.towns.core.townchat.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import de.bloodeko.towns.Services;
import de.bloodeko.towns.core.townpeople.TownPeople;
import de.bloodeko.towns.core.townplots.PlotData;
import de.bloodeko.towns.core.townplots.PlotHandler;

public class TownChatService {
    private final Map<UUID, UserChatEntity> userdata;
    private final Map<Integer, TownChatEntity> channels;
    
    public TownChatService() {
        this.userdata = new ConcurrentHashMap<>();
        this.channels = new ConcurrentHashMap<>();
    }
    
    /**
     * Returns the selected town for that player, or null.
     */
    public Integer getSelected(UUID uuid) {
        UserChatEntity data = userdata.get(uuid);
        if (data == null) {
            return null;
        }
        return data.getSelected();
    }
    
    /**
     * Returns the names of all town channels 
     * the player is inside.
     */
    public List<String> getChatNames(UUID uuid) {
        List<String> list = new ArrayList<>();
        UserChatEntity data = userdata.get(uuid);
        if (data != null) {
            for (Integer id : data.getChats()) {
                list.add(Services.nameservice().getName(id));
            }
        }
        return list;
    }
    
    /**
     * Returns a view of the chats the player is inside.
     */
    public Set<Integer> getChats(UUID uuid) {
        UserChatEntity data = userdata.get(uuid);
        if (data == null) {
            return Collections.emptySet();
        }
        return data.getChats();
    }
    
    /**
     * Assumes that user data and the channel exists.
     * Set the players selection to it.
     */
    public void switchChat(UUID uuid, Integer town) {
        userdata.get(uuid).select(town);
    }

    /**
     * Returns all offline players for that town chat, or null.
     * The returned Set is guaranteed to be thread-safe.
     */
    public Set<UUID> getPlayers(Integer town) {
        TownChatEntity channel = channels.get(town);
        if (channel == null) {
            return null;
        }
        return channel.getView();
    }
    
    /**
     * Creates and sets a new TownChat for that town by adding 
     * players using the TownPeople and TownPlots data.
     */
    public void initTown(Integer town) {
        channels.put(town, new TownChatEntity());
        
        PlotHandler handler = Services.plotservice().getPlots(town);
        if (handler != null) {
            for (PlotData plot : handler.getPlots())
                if (plot.renter != null)
                    addPlayer(town, plot.renter);
        }
        
        TownPeople people = Services.peopleservice().getPeople(town);
        addPlayer(town, people.getOwner());
        for (UUID uuid : people.getGovernors()) {
            addPlayer(town, uuid);
        }
        for (UUID uuid : people.getBuilders()) {
            addPlayer(town, uuid);
        }
    }

    /**
     * Loads an empty town into the service, without adding 
     * any players.
     */
    public void loadTown(Integer town) {
        channels.put(town, new TownChatEntity());
    }
    
    /**
     * Assumes a channel for the town exists.
     * Adds the player to the chat channel.
     * Updates the users data, even if absent.
     */
    public void addPlayer(Integer town, UUID uuid) {
        UserChatEntity user = userdata.computeIfAbsent(uuid, key -> {
            HashSet<Integer> set = new HashSet<>(Arrays.asList(town));
            return new UserChatEntity(town, set);
        });
        user.add(town);
        channels.get(town).add(uuid);
    }
    
    /**
     * Removes the player from this town chat.
     * Assumes a channel for the town exists.
     */
    public void removePlayer(Integer town, UUID uuid) {
        UserChatEntity user = userdata.get(uuid);
        if (user != null) {
            user.remove(town);
        }
        channels.get(town).remove(uuid);
    }
    
    /**
     * Assumes that a channel exists for this town.
     * Returns all players currently online in it.
     */
    public List<Player> getOnlinePlayers(Integer town) {
        List<Player> list = new ArrayList<>();
        Set<UUID> chatPlayers = Services.townchat().getPlayers(town);
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (chatPlayers.contains(player.getUniqueId())) {
                list.add(player);
            }
        }
        return list;
    }
    
    /**
     * Deletes the specified town chat and frees resources.
     * Sets the selector for affected players to null.
     */
    public void destroy(Integer town) {
        for (UUID uuid : getPlayers(town)) {
            userdata.get(uuid).remove(town);
        }
        channels.remove(town);
    }
    
    /**
     * Returns a view of all towns which have chat channels.
     */
    public Set<Integer> getTowns() {
        return Collections.unmodifiableSet(channels.keySet());
    }
}
