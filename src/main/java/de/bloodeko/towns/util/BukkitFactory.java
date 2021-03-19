package de.bloodeko.towns.util;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import de.bloodeko.towns.Towns;
import de.bloodeko.towns.cmds.general.MapCmd.ChunkMapView;
import de.bloodeko.towns.cmds.general.MapCmd.MapRotation;
import de.bloodeko.towns.town.ChunkMap;

public class BukkitFactory {
    
    public static ChunkMap newChunkHandler(Towns plugin) {
        return new ChunkMap(new HashMap<>());
    }
    
    public static ChunkMapView newChunkMapView(ChunkMap map, Player player) {
        Inventory inv = Items.createMapInv();
        return new ChunkMapView(map, newMapRotation(player.getLocation().getYaw()),
            player, 1, Chunk.fromEntity(player), inv);
    }
    
    public static MapRotation newMapRotation(float yaw) {
        Yaw direc = Yaw.getDirection(yaw);
        switch (direc) {
            case NORTH: return MapRotation.NORTH;
            case SOUTH: return MapRotation.SOUTH;
            case WEST: return MapRotation.WEST;
            case EAST: return MapRotation.EAST;
        }
        throw new RuntimeException();
    }
    
    /**
     * Item Utilities.
     */
    public static class Items {

        public static ItemStack createItem(Material mat, String name) {
            ItemStack stack = new ItemStack(mat);
            ItemMeta meta = stack.getItemMeta();
            meta.setDisplayName(name);
            stack.setItemMeta(meta);
            return stack;
        }
        
        @SuppressWarnings("deprecation")
        public static ItemStack createSkull(String owner, String name) {
            ItemStack stack = createItem(Material.PLAYER_HEAD, name);
            SkullMeta meta = (SkullMeta) stack.getItemMeta();
            meta.setOwner(owner);
            stack.setItemMeta(meta);
            return stack;
        }
        
        public static Inventory createMapInv() {
            Inventory inv = Bukkit.createInventory(null, 54, "Townmap");
            inv.setContents(Items.createMapContent());
            return inv;
        }
        
        public static ItemStack[] createMapContent() {
            ItemStack[] items = new ItemStack[54];
            ItemStack pane = Items.createItem(Material.BLACK_STAINED_GLASS_PANE, "~~~");
            items[0] = Items.createItem(Material.STONE, "center");
            items[1] = Items.createItem(Material.STONE, "zoom");
            items[2] = pane;
            items[3] = Items.createItem(Material.STONE, "↑");
            items[4] = Items.createItem(Material.STONE, "↓");
            items[5] = pane;
            items[6] = Items.createItem(Material.STONE, "←");
            items[7] = Items.createItem(Material.STONE, "→");
            items[8] = pane;
            return items;
        }
    }
    
}
