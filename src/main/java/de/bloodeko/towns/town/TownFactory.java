package de.bloodeko.towns.town;

import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

import de.bloodeko.towns.util.Chunk;
import de.bloodeko.towns.util.Players;

public class TownFactory {
    
    public static Town newTown(int id, String name, Chunk chunk, UUID owner) {
        return new Town(id, name, newSettings(), newArea(chunk), owner);
    }
    
    public static TownArea newArea(Chunk base) {
        return new TownArea(new HashSet<>(), base.x, base.x, base.z, base.z);
    }
    
    public static TownSettings newSettings() {
        TownSettings settings = new TownSettings();
        settings.setClaiming(true);
        return settings;
    }

    public static TownRegistry newRegistry(ChunkMap map) {
        TownRegistry registry = new TownRegistry(new HashMap<>(), map, 0);
        
        Town town0 = registry.createTown(Chunk.of(1, 9), "forestcity", Players.get("BloodEko"));
        town0.getArea().expand(map, town0, Chunk.of(1, 8));
        town0.getArea().expand(map, town0, Chunk.of(0, 9));
        town0.getArea().expand(map, town0, Chunk.of(0, 8));

        registry.createTown(Chunk.of(1, 10), "seacity", Players.get("BloodEko"));
        return registry;
    }
    
}
