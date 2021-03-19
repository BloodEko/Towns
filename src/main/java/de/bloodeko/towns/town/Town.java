package de.bloodeko.towns.town;

import de.bloodeko.towns.town.TownArea.TownAreaData;
import de.bloodeko.towns.town.TownPeople.TownPeopleData;
import de.bloodeko.towns.town.TownSettings.TownSettingsData;

/**
 * TownMembers, TownSerializer, TownArea,
 * TownExpandContractRules
 */
public class Town {
    private int id;
    private TownSettings settings;
    private TownArea area;
    private TownPeople people;
    
    public Town(int id, TownSettings settings, TownArea area, TownPeople people) {
        this.id = id;
        this.settings = settings;
        this.area = area;
        this.people = people;
    }
    
    public int getId() {
        return id;
    }
    
    public TownSettings getSettings() {
        return settings;
    }
    
    public TownArea getArea() {
        return area;
    }

    public TownPeople getPeople() {
        return people;
    }
    
    public TownData getData() {
        return new TownData(id, settings.getData(), area.getData(), people.getData());
    }
    
    /**
     * Data for serialization.
     */
    public static class TownData {
        public int id;
        public TownSettingsData settings;
        public TownAreaData area;
        public TownPeopleData people;
        
        public TownData(int id, TownSettingsData settings, TownAreaData area, TownPeopleData people) {
            this.id = id;
            this.settings = settings;
            this.area = area;
            this.people = people;
        }
    }
}
