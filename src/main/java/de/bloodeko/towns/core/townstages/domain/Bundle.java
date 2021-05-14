package de.bloodeko.towns.core.townstages.domain;

import org.bukkit.Material;

/**
 * Represents an item bundle entity. With an item value, 
 * required amount value, and given amount, which is mutable.
 */
public class Bundle {
    private final Material material;
    private final int required;
    private int amount;
    
    private Bundle(Material material, int needed) {
        this.material = material;
        this.required = needed;
    }
    
    public static Bundle newEmptyOne(Material material, int needed) {
        return new Bundle(material, needed);
    }
    
    public static Bundle newFilledOne(Material material, int needed, int given) {
        Bundle bundle = new Bundle(material, needed);
        bundle.amount = given;
        return bundle;
    }
    
    public Material getMaterial() {
        return material;
    }
    
    public int getRequired() {
        return required;
    }
    
    /**
     * Adds the amount of given items
     */
    public void add(int amount) {
        this.amount += amount;
    }
    
    /**
     * Subtracts the amount of given items.
     */
    public void sub(int amount) {
        this.amount -= amount;
    }
    
    /**
     * Gets the amount of given items.
     */
    public int getQty() {
        return amount;
    }
    
    /**
     * Sets the amount of given items.
     */
    public void setQty(int val) {
        amount = val;
    }
}
