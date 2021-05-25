package de.bloodeko.towns.core.townstages.data;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Material;

import de.bloodeko.towns.core.townstages.StageItemsFactory;
import de.bloodeko.towns.core.townstages.domain.Bundle;
import de.bloodeko.towns.core.townstages.domain.Stage;
import de.bloodeko.towns.core.townstages.domain.StageService;
import de.bloodeko.towns.util.Node;
import de.bloodeko.towns.util.Node.Pair;

/**
 * Database helper, which serializes and deserializes stages
 * to an uniform Node format.
 */
public class StageSerializer {

    /**
     * Returns a StageService from a Node, with subsections,
     * where each key is an ID and the value stage data.
     */
    public static StageService read(Node node) {
        Map<Integer, Stage> data = new HashMap<>();
        for (Pair pair : node.entries()) {
            Integer id = Integer.valueOf(pair.key);
            Stage stage = deserialize((Node) pair.value);
            data.put(id, stage);
        }
        return new StageService(data);
    }
    
    /**
     * Returns a Node with subsections, where each key is 
     * an ID and the value stage data.
     */
    public static Node write(StageService service) {
        Node node = new Node();
        for (Entry<Integer, Stage> entry : service.getView().entrySet()) {
            node.set(entry.getKey().toString(), serialize(entry.getValue()));
        }
        return node;
    }

    /**
     * Returns a Stage from a Node with "stage" and "items" keys.
     */
    public static Stage deserialize(Node node) {
        int stage = node.getInt("stage");
        Map<Material, Bundle> items = BundleSerializer.deserialize(
          node.getNode("items"), StageItemsFactory.getItems(stage));
        return new Stage(stage, items);
    }
    
    /**
     * Returns a Node with "stage" and "items" keys.
     */
    public static Node serialize(Stage stage) {
        Node node = new Node();
        node.set("stage", stage.getStage());
        node.set("items", BundleSerializer.serialize(stage.getItems()));
        return node;
    }
}
