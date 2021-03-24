package de.bloodeko.towns.cmds.map;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import de.bloodeko.towns.Towns;
import de.bloodeko.towns.town.ChunkMap;
import de.bloodeko.towns.util.Chunk;
import de.bloodeko.towns.util.Util;
import de.bloodeko.towns.util.Yaw;

public class MapFactory {
    
    public static Inventory createMapInv() {
        Inventory inv = Bukkit.createInventory(null, 54, "Townmap");
        inv.setContents(MapFactory.createMapContent());
        return inv;
    }
    
    public static ItemStack[] createMapContent() {
        ItemStack[] items = new ItemStack[54];
        ItemStack pane = Util.createItem(Material.BLACK_STAINED_GLASS_PANE, "~~~");
        items[0] = Util.createItem(Material.STONE, "center");
        items[1] = Util.createItem(Material.STONE, "zoom");
        items[2] = pane;
        items[3] = Util.createItem(Material.STONE, "↑");
        items[4] = Util.createItem(Material.STONE, "↓");
        items[5] = pane;
        items[6] = Util.createItem(Material.STONE, "←");
        items[7] = Util.createItem(Material.STONE, "→");
        items[8] = pane;
        return items;
    }
    
    public static MapView newChunkMapView(ChunkMap map, Player player) {
        Inventory inv = MapFactory.createMapInv();
        return new MapView(map, newMapRotation(player.getLocation().getYaw()),
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
    
    public static MapListener newClickHandler(Towns plugin) {
        MapListener handler = new MapListener(new HashMap<>());
        plugin.getServer().getPluginManager().registerEvents(handler, plugin);
        return handler;
    }
}
