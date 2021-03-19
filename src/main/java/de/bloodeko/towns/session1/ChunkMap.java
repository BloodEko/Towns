package de.bloodeko.towns.session1;

import java.util.Map;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import de.bloodeko.towns.session1.BukkitFactory.Items;
import de.bloodeko.towns.session2.Town;

public class ChunkMap {
    private Map<Chunk, Town> map;
    
    public ChunkMap(Map<Chunk, Town> map) {
        this.map = map;
    }
    
    public Town getTown(Chunk chunk) {
        return map.get(chunk);
    }
    
    public boolean hasTown(Chunk chunk) {
        return map.containsKey(chunk);
    }
    
    public void setTown(Chunk chunk, Town town) {
        map.put(chunk, town);
    }
    
    public void removeTown(Chunk chunk) {
        map.remove(chunk);
    }
    
    public ItemStack getIcon(Chunk chunk) {
        Town town = getTown(chunk);
        if (town == null) return null;
        return Items.createItem(Material.LIME_STAINED_GLASS_PANE, town.getName());
    }
}
