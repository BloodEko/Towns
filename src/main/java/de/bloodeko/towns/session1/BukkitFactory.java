package de.bloodeko.towns.session1;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import de.bloodeko.towns.Towns;
import de.bloodeko.towns.session1.MapCmd.ChunkMapView;
import de.bloodeko.towns.session1.MapCmd.MapClickHandler;
import de.bloodeko.towns.session1.MapCmd.MapRotation;
import de.bloodeko.towns.session1.SettingsCmd.SettingsCmdFactory;
import de.bloodeko.towns.session2.Town;
import de.bloodeko.towns.session2.TownFactory;

public class BukkitFactory {
    
    public static ChunkMap newChunkHandler(Towns plugin) {
        ChunkMap map = new ChunkMap(new HashMap<>());
        
        Town town0 = TownFactory.newTown(0, "Testcity0");
        town0.expand(map, Chunk.of(1, 9));
        town0.expand(map, Chunk.of(1, 8));
        town0.expand(map, Chunk.of(0, 9));
        town0.expand(map, Chunk.of(0, 8));
        
        Town town1 = TownFactory.newTown(1, "Testcity1");
        town1.expand(map, Chunk.of(1, 10));
        
        return map;
    }
    
    public static TownCommandListener newCmdListener(Towns plugin) {
        TownCmdHandler handler = newCmdHandler(plugin);
        return new TownCommandListener(handler);
    }
    
    public static TownCmdHandler newCmdHandler(Towns plugin) {
        Map<String, TownCmdBase> cmds = new HashMap<>();
        TownCmdHandler handler = new TownCmdHandler(cmds);
        ChunkMap map = plugin.getChunkMap();
        cmds.put("map", new MapCmd(map, plugin.getMapClickHandler()));
        cmds.put("claim", new ClaimCmd(map));
        cmds.put("unclaim", new UnclaimCmd(map));
        cmds.put("info", new InfoCmd(map));
        cmds.put("settings", SettingsCmdFactory.newCommand(map));
        return handler;
    }
    
    public static MapClickHandler newMapClickHandler() {
        return new MapClickHandler(new HashMap<>());
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
            items[0] = pane;
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
