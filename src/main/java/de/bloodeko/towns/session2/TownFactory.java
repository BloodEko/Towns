package de.bloodeko.towns.session2;

import java.util.HashSet;

public class TownFactory {
    
    public static Town newTown(int id, String name) {
        return new Town(0, name, newSettings(), new HashSet<>());
    }
    
    public static TownSettings newSettings() {
        return new TownSettings(true, false);
    }
    
}
