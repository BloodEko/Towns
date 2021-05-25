package de.bloodeko.towns.core.townstages.domain;

import java.util.Collections;
import java.util.Map;

public class StageService {
    private final Map<Integer, Stage> map;

    public StageService(Map<Integer, Stage> map) {
        this.map = map;
    }
    
    /**
     * Sets a stage association for this town.
     */
    public void put(int town, Stage stage) {
        map.put(town, stage);
    }
    
    /**
     * Returns the stage for that town, or null.
     */
    public Stage get(int town) {
        return map.get(town);
    }
    
    /**
     * Removes the mapping for that town.
     */
    public void remove(int id) {
        map.remove(id);
    }

    /**
     * Returns a view of the data.
     */
    public Map<Integer, Stage> getView() {
        return Collections.unmodifiableMap(map);
    }
}
