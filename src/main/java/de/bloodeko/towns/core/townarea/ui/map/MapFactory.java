package de.bloodeko.towns.core.townarea.ui.map;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import de.bloodeko.towns.Towns;
import de.bloodeko.towns.core.townarea.ChunkService;
import de.bloodeko.towns.core.towns.Town;
import de.bloodeko.towns.util.Chunk;
import de.bloodeko.towns.util.Messages;
import de.bloodeko.towns.util.Util;
import de.bloodeko.towns.util.Yaw;

public class MapFactory {
    
    /**
     * Returns a new MapView backed with the
     * ChunkMap, player, and default settings.
     */
    public static MapView newGlobalView(ChunkService map, Player player) {
        MapRotation rotation = getMapRotation(player.getLocation().getYaw());
        int zoom = 1;
        Chunk center = Chunk.fromEntity(player);
        Inventory inv = newMapInventory();
        return new MapView(map, rotation, player, zoom, center, inv);
    }

    /**
     * Returns a new local MapView backed with the 
     * ChunkMap, player, and default settings.
     */
    public static TownView newTownView(ChunkService map, Player player, Town town) {
        MapRotation rotation = getMapRotation(player.getLocation().getYaw());
        int zoom = 1;
        Chunk center = Chunk.fromEntity(player);
        Inventory inv = newMapInventory();
        return new TownView(map, rotation, player, zoom, center, inv, town);
    }
    
    /**
     * Returns a new inventory with the TownMap design.
     */
    public static Inventory newMapInventory() {
        String name = Messages.get("cmds.map.gui.header");
        Inventory inv = Bukkit.createInventory(null, 54, name);
        inv.setContents(newMapContent());
        return inv;
    }
    
    /**
     * Returns a new array filled with the layout
     * and buttons for a TownMap.
     */
    public static ItemStack[] newMapContent() {
        ItemStack[] items = new ItemStack[54];
        ItemStack pane = Util.createItem(Material.BLACK_STAINED_GLASS_PANE, "~~~");
        items[0] = Util.createItem(Material.STONE, "Zentrum");
        items[1] = Util.createItem(Material.STONE, "Zoom", "§fLK: nah", "§fRK: weg");
        items[2] = pane;
        items[3] = Util.createItem(Material.STONE, "↑");
        items[4] = Util.createItem(Material.STONE, "↓");
        items[5] = pane;
        items[6] = Util.createItem(Material.STONE, "←");
        items[7] = Util.createItem(Material.STONE, "→");
        items[8] = pane;
        return items;
    }
    
    /**
     * Returns a MapRoation based based on the yaw.
     */
    public static MapRotation getMapRotation(float yaw) {
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
     * Returns a new and registered MapListener service that 
     * listens on inventory clicks.
     */
    public static MapListener newClickHandler(Towns plugin) {
        MapListener handler = new MapListener(new HashMap<>());
        plugin.getServer().getPluginManager().registerEvents(handler, plugin);
        return handler;
    }
}
