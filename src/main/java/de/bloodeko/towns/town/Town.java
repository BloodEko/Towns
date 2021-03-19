package de.bloodeko.towns.town;

import java.util.HashSet;
import java.util.Set;

import com.sk89q.worldguard.protection.managers.RegionManager;

import de.bloodeko.towns.town.area.TownArea;
import de.bloodeko.towns.town.area.TownArea.ChunkRegion;
import de.bloodeko.towns.town.people.TownPeople;
import de.bloodeko.towns.town.settings.SettingsRegistry;
import de.bloodeko.towns.town.settings.TownSettings;
import de.bloodeko.towns.util.Chunk;
import de.bloodeko.towns.util.Node;

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
    
    /**
     * Returns a Node that represent a town and its entries.
     */
    public Node serialize() {
        Node node = new Node();
        node.set("settings", settings.serialize());
        node.set("area", area.serialize());
        node.set("people", people.serialize());
        return node;
    }
    
    /**
     * Creates a town from a town-node.
     */
    public static Town deserialize(int id, Node town, SettingsRegistry registry, RegionManager manager) {
        Set<Chunk> chunks = new HashSet<>();
        ChunkRegion region = TownFactory.newChunkRegion(chunks, id, manager);
        
        TownSettings settings = TownSettings.deserialize(town.getNode("settings"), region, registry);
        TownArea area = TownArea.deserialize(town.getNode("area"), chunks, region);
        TownPeople people = TownPeople.deserialize(town.getNode("people"), region);
        
        return new Town(id, settings, area, people);
    }
    
    /*
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("settings", settings.serialize());
        map.put("area", area.serialize());
        map.put("people", people.serialize());
        return map;
    }
    
    public static Town deserialize(Map<String, Object> root, int id, SettingsRegistry registry, RegionManager manager) {
        Set<Chunk> set = new HashSet<>();
        ChunkRegion region = TownFactory.newChunkRegion(set, id, manager);
        
        TownSettings settings = TownSettings.deserialize(asRoot(root.get("settings")), region, registry);
        TownArea area = TownArea.deserialize(asRoot(root.get("area")), set, region);
        TownPeople people = TownPeople.deserialize(asRoot(root.get("people")), region);
        
        return new Town(id, settings, area, people);
    }
    */
}
