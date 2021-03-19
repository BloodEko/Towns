package de.bloodeko.towns.cmds.general;

import java.util.Map;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import de.bloodeko.towns.cmds.CmdBase;
import de.bloodeko.towns.town.ChunkMap;
import de.bloodeko.towns.util.BukkitFactory;
import de.bloodeko.towns.util.BukkitFactory.Items;
import de.bloodeko.towns.util.Chunk;
import de.bloodeko.towns.util.Messages;
import de.bloodeko.towns.util.Yaw;

/**
 * Handles the command. /town map
 * Undiscovered towns? Renderdistance? Playerstorage?
 */
public class MapCmd extends CmdBase {
    private MapClickHandler clickHandler;
    
    public MapCmd(ChunkMap map, MapClickHandler clickHandler) {
        super(map);
        this.clickHandler = clickHandler;
    }
    
    @Override
    public void execute(Player player, String[] args) {
        ChunkMapView view = BukkitFactory.newChunkMapView(getMap(), player);
        clickHandler.add(player.getUniqueId(), view);
        view.open();
    }
    
    /**
     * Renders and shows maps to players.
     */
    public static class ChunkMapView {
        private static final int ROW_LENGTH = 9;
        private static final int COLUMN_LENGTH = 5;
        private static final int HEAD_Z_OFF = 1;
        private static final int MIN_ZOOM = 1;
        private static final int MAX_ZOOM = 4;
        
        private ChunkMap map;
        private MapRotation rotation;
        private Player player;
        private int zoom;
        private Chunk center;
        private Inventory inv;
        
        public ChunkMapView(ChunkMap map, MapRotation rotation, Player player, int zoom, Chunk center, Inventory inv) {
            this.map = map;
            this.rotation = rotation;
            this.player = player;
            this.zoom = zoom;
            this.center = center;
            this.inv = inv;
        }

        public void zoom(ClickType type) {
            if (type == ClickType.LEFT) {
                if (zoom != MIN_ZOOM) {
                    zoom--;
                    Messages.say(player, "cmds.map.setZoom", zoom);
                    render();
                }
            } 
            if (type == ClickType.RIGHT) {
                if (zoom != MAX_ZOOM) {
                    zoom++;
                    Messages.say(player, "cmds.map.setZoom", zoom);
                    render();
                }
            }
        }
        
        public void open() {
            render();
            player.openInventory(inv);
            Messages.say(player, "cmds.map.openView", rotation.up);
        }
        
        private void render() {
            for (int z = 0; z < COLUMN_LENGTH; z++) {
                for (int x = 0; x < ROW_LENGTH; x++) {
                    renderSlot(center, x, z);
                }
            }
        }
        
        private void renderSlot(Chunk base, int x, int z) {
            Chunk chunk = renderChunk(base, x, z);
            ItemStack icon = map.getIcon(chunk);
            if (icon == null ) {
                icon = Items.createItem(Material.BROWN_STAINED_GLASS_PANE, chunk.toString());
            }
            inv.setItem(getSlot(x, z), icon);
        }
        
        private Chunk renderChunk(Chunk base, int x, int z) {
            return rotation.renderChunk(base, zoom, x, z);
        }
        
        private int getSlot(int x, int z) {
            return ((HEAD_Z_OFF + z) * ROW_LENGTH) + x;
        }
        
        public void toCenter() {
            Chunk chunk = Chunk.fromEntity(player);
            if (!chunk.equals(center)) {
                center = chunk;
                render();
            }
        }
        
        public void moveUp() {
            center = center.add(zoom * rotation.up.getX(), zoom * rotation.up.getZ());
            render();
        }
        
        public void moveDown() {
            center = center.add(zoom * rotation.down.getX(), zoom * rotation.down.getZ());
            render();
        }
        
        public void moveRight() {
            center = center.add(zoom * rotation.right.getX(), zoom * rotation.right.getZ());
            render();
        }
        
        public void moveLeft() {
            center = center.add(zoom * rotation.left.getX(), zoom * rotation.left.getZ());
            render();
        }
    }
    
    /**
     * Maps each slot to a specific rotation.
     */
    public static class MapRotation {
        private static final Yaw TO_NORTH = Yaw.NORTH;
        private static final Yaw TO_SOUTH = Yaw.SOUTH;
        private static final Yaw TO_WEST = Yaw.WEST;
        private static final Yaw TO_EAST = Yaw.EAST;
        
        private static final int BASE_X_OFF = -4;
        private static final int BASE_Z_OFF = -2;
        
        public static final Rotation2D ROTATE_0 = (chunk, x, z) -> chunk.add(x, z);
        public static final Rotation2D ROTATE_90 = (chunk, x, z) -> chunk.add(-z, x);
        public static final Rotation2D ROTATE_180 = (chunk, x, z) -> chunk.add(-x, -z);
        public static final Rotation2D ROTATE_270 = (chunk, x, z) -> chunk.add(z, -x);
        
        public static final MapRotation NORTH = new MapRotation(TO_NORTH, TO_SOUTH, TO_WEST, TO_EAST, ROTATE_0);
        public static final MapRotation SOUTH = new MapRotation(TO_SOUTH, TO_NORTH, TO_EAST, TO_WEST, ROTATE_180);
        public static final MapRotation WEST = new MapRotation(TO_WEST, TO_EAST, TO_SOUTH, TO_NORTH, ROTATE_270);
        public static final MapRotation EAST = new MapRotation(TO_EAST, TO_WEST, TO_NORTH, TO_SOUTH, ROTATE_90);
        
        public final Yaw up;
        public final Yaw down;
        public final Yaw right;
        public final Yaw left;
        public final Rotation2D blockRot;
        
        public MapRotation(Yaw up, Yaw down, Yaw right, Yaw left, Rotation2D blockRot) {
            this.up = up;
            this.down = down;
            this.right = right;
            this.left = left;
            this.blockRot = blockRot;
        }
        
        public Chunk renderChunk(Chunk chunk, int zoom, int x, int z) {
            x = x * zoom + BASE_X_OFF * zoom;
            z = z * zoom + BASE_Z_OFF * zoom;
            return blockRot.rotate(chunk, x, z);
        }
        
        public static interface Rotation2D {
            public Chunk rotate(Chunk chunk, int x, int z);
        }
    }
    
    /**
     * Handles incoming clicks and maps
     * them to their corresponding view.
     */
    public static class MapClickHandler implements Listener {
        private Map<UUID, ChunkMapView> views;
        
        public MapClickHandler(Map<UUID, ChunkMapView> map) {
            views = map;
        }

        public void add(UUID player, ChunkMapView view) {
            views.put(player, view);
        }
        
        @EventHandler
        public void onClick(InventoryClickEvent click) {
            ChunkMapView view = views.get(click.getWhoClicked().getUniqueId());
            if (view == null) {
                return;
            }
            click.setCancelled(true);
            switch(click.getSlot()) {
                case 0: view.toCenter(); break;
                case 1: view.zoom(click.getClick()); break;
                case 3: view.moveUp(); break;
                case 4: view.moveDown(); break;
                case 6: view.moveRight(); break;
                case 7: view.moveLeft(); break;
            }
        }
        
        @EventHandler
        public void onClose(InventoryCloseEvent close) {
            views.remove(close.getPlayer().getUniqueId());
        }
    }

}
