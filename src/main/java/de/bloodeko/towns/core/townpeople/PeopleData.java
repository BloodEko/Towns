package de.bloodeko.towns.core.townpeople;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import de.bloodeko.towns.Services;
import de.bloodeko.towns.core.townarea.TownRegion;
import de.bloodeko.towns.util.Node;
import de.bloodeko.towns.util.Node.Pair;

public class PeopleData {

    /**
     * Returns a PeopleService from a Node, with subsections,
     * where each key is an ID and the value a TownPeople Node.
     * Requires the ChunkService to be already loaded.
     */
    public static PeopleService read(Node node) {
        Map<Integer, TownPeople> data = new HashMap<>();
        for (Pair pair : node.entries()) {
            Integer id = Integer.valueOf(pair.key);
            TownRegion region = Services.chunkservice().getRegion(id);
            TownPeople people = deserialize((Node) pair.value, region);
            data.put(id, people);
        }
        
        return new PeopleService(data);
    }
    
    /**
     * Returns a Node with subsections, with the keys as 
     * a town ID and the value a Node with TownPoeple data.
     */
    public static Node write(PeopleService service) {
        Node node = new Node();
        for (Entry<Integer, TownPeople> entry : service.getView().entrySet()) {
            node.set(entry.getKey().toString(), serialize(entry.getValue()));
        }
        return node;
    }
    
    /**
     * Returns a node that has an "owner", "governors" and "builders" section.
     */
    public static Node serialize(TownPeople people) {
        Node node = new Node();
        node.set("owner", people.getOwner());
        node.set("governors", people.getGovernors());
        node.set("builders", people.getBuilders());
        return node;
    }
    
    /**
     * Creates people from a Node with a "owner", "governors" and "builders"
     * section. The UUIDs will be added as members to the WorldGuard region.
     */
    public static TownPeople deserialize(Node people, TownRegion region) {
        Set<UUID> governors = people.getUUIDSet("governors");
        Set<UUID> builders = people.getUUIDSet("builders");
        for (UUID uuid : governors) {
            region.getMembers().addPlayer(uuid);
        }
        for (UUID uuid : builders) {
            region.getMembers().addPlayer(uuid);
        }
        return new TownPeople(people.getUUID("owner"), governors, builders, region);
    }
}
