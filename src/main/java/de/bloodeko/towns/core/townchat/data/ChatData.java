package de.bloodeko.towns.core.townchat.data;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import de.bloodeko.towns.Services;
import de.bloodeko.towns.core.townchat.domain.TownChatService;
import de.bloodeko.towns.util.Node;
import de.bloodeko.towns.util.Node.Pair;

public class ChatData {
    
    /**
     * Returns a TownChatService from a Node, with subsections,
     * where each key is an ID with a "towns" and "selectors" Node.
     */
    public static TownChatService read(Node node) {
        TownChatService service = new TownChatService();
        loadTownChats(node.getNode("towns"), service);
        for (Pair pair : node.getNode("selectors").entries()) {
            UUID uuid = UUID.fromString(pair.key);
            Integer town = Integer.valueOf(pair.value.toString());
            service.addPlayer(town, uuid);
        }
        return service;
    }
    
    /**
     * Initializes chats to a TownChatService from a "towns" node,
     * where each section is a town ID with an value of "x" for no
     * members set, or the members as String-List.
     */
    @SuppressWarnings("unchecked")
    private static void loadTownChats(Node towns, TownChatService service) {
        for (Pair pair : towns.entries()) {
            Integer town = Integer.parseInt(pair.key);
            service.loadTown(town);
            if (pair.value.equals("x")) {
                continue;
            }
            for (String uuid : (List<String>) pair.value) {
                service.addPlayer(town, UUID.fromString(uuid));
            }
        }
    }

    /**
     * Returns a Node which has a "towns" section, where 
     * key is an ID with an UUID-Set as value and a 
     * "selectors" section, with UUID:ID mappings.
     */
    public static Node write(TownChatService townchatService) {
        Node node = new Node();
        Node towns = node.newNode("towns");
        for (Integer id : Services.townchat().getTowns()) {
            Set<UUID> members = Services.townchat().getPlayers(id);
            towns.set(id.toString(), members.isEmpty() ? "x" : members);
        }

        Node selectors = node.newNode("selectors");
        for (Integer id : Services.townchat().getTowns()) {
            for (UUID uuid : Services.townchat().getPlayers(id)) {
                selectors.set(uuid.toString(), id);
            }
        }
        return node;
    }

}
