package de.bloodeko.towns.town.settings.stage.data;

import java.util.Map;

import org.bukkit.Material;

import de.bloodeko.towns.town.settings.stage.StageItemsFactory;
import de.bloodeko.towns.town.settings.stage.domain.Bundle;
import de.bloodeko.towns.town.settings.stage.domain.Stage;
import de.bloodeko.towns.util.Node;

/**
 * Database helper, which serializes and deserializes stages
 * to an uniform Node format.
 */
public class StageSerializer {
    
    /**
     * Returns a node which has stage and bundle mapping.
     */
    public static Node serialize(Stage stage) {
        Node node = new Node();
        node.set("stage", stage.getStage());
        node.set("items", BundleSerializer.serialize(stage.getItems()));
        return node;
    }

    /**
     * Return a Map with stage and bundle settings from a Node.
     */
    public static Stage deserialize(Node node) {
        int stage = node.getInt("stage");
        Map<Material, Bundle> items = BundleSerializer.deserialize(
          node.getNode("items"), StageItemsFactory.getItems(stage));
        return new Stage(stage, items);
    }
}
