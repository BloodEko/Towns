package de.bloodeko.towns.core.townpeople;

import java.util.Collections;
import java.util.Map;

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
}
