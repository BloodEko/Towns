package de.bloodeko.towns.cmds.map;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import de.bloodeko.towns.town.ChunkMap;
import de.bloodeko.towns.town.Town;
import de.bloodeko.towns.util.Chunk;
import de.bloodeko.towns.util.Messages;
import de.bloodeko.towns.util.Util;

/**
 * Renders and shows maps to players.
 */
public class MapView {
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
    
    public MapView(ChunkMap map, MapRotation rotation, Player player, int zoom, Chunk center, Inventory inv) {
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
        new Integer(10).hashCode();
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
        ItemStack icon = getIcon(chunk);
        inv.setItem(getSlot(x, z), icon);
    }
    
    public ItemStack getIcon(Chunk chunk) {
        Town town = map.getTown(chunk);
        if (town != null) {
            return Util.createItem(Material.LIME_STAINED_GLASS_PANE, town.getSettings().getName());
        }
        return Util.createItem(Material.BROWN_STAINED_GLASS_PANE, chunk.toString());
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
