package de.bloodeko.towns.core.townpeople;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

import de.bloodeko.towns.Services;
import de.bloodeko.towns.core.townplots.PlotData;
import de.bloodeko.towns.core.townplots.PlotHandler;

public class PeopleService {
    private Map<Integer, TownPeople> data;

    public PeopleService(Map<Integer, TownPeople> data) {
        this.data = data;
    }
    
    /**
     * Returns the people for that town, or null.
     */
    public TownPeople getPeople(Integer town) {
        return data.get(town);
    }

    /**
     * Sets a new people association.
     */
    public void put(int id, TownPeople people) {
        data.put(id, people);
    }

    /**
     * Removes this people association.
     */
    public void remove(int id) {
        data.remove(id);
    }

    /**
     * Returns a view of the data.
     */
    public Map<Integer, TownPeople> getView() {
        return Collections.unmodifiableMap(data);
    }
    
    /**
     * Triggered when a player is added to an town entity.
     * If the player is no member yet, adds him to the 
     * town chat.
     */
    public void onAdddedMember(Integer town, UUID uuid) {
        if (hasChat(town)) {
            Services.townchat().addPlayer(town, uuid);
        }
    }
    
    /**
     * Triggered when a player was removed from a town entity.
     * If the player is no longer a member, removes him from 
     * the town chat.
     */
    public void onRemovedMember(Integer town, UUID uuid) {
        if (hasChat(town) && !isMember(town, uuid)) {
            Services.townchat().removePlayer(town, uuid);
        }
    }
    
    /**
     * Returns true if a registered chat for the town exists.
     */
    private boolean hasChat(Integer town) {
        return Services.townchat().getTowns().contains(town);
    }
    
    /**
     * Returns true if the UUID is a owner, governor, builder,
     * renter or builder of a rented plot of the town.
     */
    private boolean isMember(Integer town, UUID uuid) {
        TownPeople people = Services.peopleservice().getPeople(town);
        if (people.isBuilder(uuid)) {
            return true;
        }
        PlotHandler handler = Services.plotservice().getPlots(town);
        if (handler == null) {
            return false;
        }
        for (PlotData plot : handler.getPlots()) {
            if (uuid.equals(plot.renter)) {
                return true;
            }
            if (plot.renter != null && plot.region.getMembers().contains(uuid)) {
                return true;
            }
        }
        return false;
    }
}
