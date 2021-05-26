package de.bloodeko.towns.core.townstages.domain;

import java.util.Map;

import org.bukkit.Material;

import de.bloodeko.towns.core.townstages.StageItemsFactory;
import de.bloodeko.towns.util.ModifyException;

/**
 * Represents a single stage entity for a town.
 */
public class Stage {
    public static final int MAX_STAGE = 5;
    
    private int stage;
    private Map<Material, Bundle> items;
    
    public Stage(int stage, Map<Material, Bundle> map) {
        this.stage = stage;
        this.items = map;
    }
    
    public int getStage() {
        return stage;
    }
    
    public Map<Material, Bundle> getItems() {
        return items;
    }
    
    /**
     * Throws an exception if the Stage doesn't match the requirements yet.
     * Rises to the next stage and sets the new items.
     */
    public void riseStage() {
        stage++;
        setItems();
    }

    /**
     * Sets the stage to the specific stage and resets
     * the items in the bundles.
     */
    public void setStage(int stage) {
        this.stage = stage;
        setItems();
    }
    
    private void setItems() {
        items.clear();
        items.putAll(StageItemsFactory.getItems(stage));
    }
    
    /**
     * Throws an Exception with a specific message if
     * the stage can't rise to the next stage.
     */
    public void checkCanRise() {
        if (stage >=  MAX_STAGE) {
            throw new ModifyException("settings.stage.townAlreadyMaxStage");
        }
        for (Bundle bundle : items.values()) {
            if (bundle.getQty() < bundle.getRequired()) {
                throw new ModifyException("settings.stage.notEnoughMaterialYet", 
                  bundle.getQty(), bundle.getRequired(), bundle.getMaterial());
            }
        }
    }
}
