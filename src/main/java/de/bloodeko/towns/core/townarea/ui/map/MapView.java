package de.bloodeko.towns.core.townarea.ui.map;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import de.bloodeko.towns.core.townarea.ChunkService;
import de.bloodeko.towns.core.towns.Town;
import de.bloodeko.towns.util.Chunk;
import de.bloodeko.towns.util.Util;

/**
 * View that renders towns and wilderness. 
 * Taking zoom and rotation into account.
 */
public class MapView {
    private static final int BASE_X_OFF = -4;
    private static final int BASE_Z_OFF = -2;
    private static final int WIDTH = 9;
    private static final int HEIGHT = 5;
    private static final int HEAD_Z_OFF = 1;
    private static final int MIN_ZOOM = 1;
    private static final int MAX_ZOOM = 4;
    
    protected final ChunkService map;
    protected final MapRotation rotation;
    protected final Player player;
    protected final Inventory inv;
    private Chunk center;
    private int zoom;
    
    public MapView(ChunkService map, MapRotation rotation, Player player, int zoom, Chunk center, Inventory inv) {
        this.map = map;
        this.rotation = rotation;
        this.player = player;
        this.zoom = zoom;
        this.center = center;
        this.inv = inv;
    }
    
    /**
     * Renders the display and shows it to the player.
     */
    public void open() {
        renderDisplay();
        player.openInventory(inv);
    }

    /**
     * Maps the slot to a button
     * and performs its action.
     */
    public void clickSlot(int slot, ClickType click) {
        switch(slot) {
            case 0: toCenter(); break;
            case 1: zoom(click); break;
            case 3: moveUp(); break;
            case 4: moveDown(); break;
            case 6: moveRight(); break;
            case 7: moveLeft(); break;
        }
    }

    /**
     * For left clicks, zooms in, for right clicks zooms out.
     * Returns if the minimum or maximum zoom is reached.
     * Renders the display again and updates it.
     */
    public void zoom(ClickType type) {
        if (type == ClickType.LEFT) {
            if (zoom != MIN_ZOOM) {
                zoom--;
                renderDisplay();
            }
        } 
        if (type == ClickType.RIGHT) {
            if (zoom != MAX_ZOOM) {
                zoom++;
                renderDisplay();
            }
        }
    }
    
    /**
     * Moves the view to the center and
     * renders the display again.
     */
    public void toCenter() {
        Chunk chunk = Chunk.fromEntity(player);
        if (!chunk.equals(center)) {
            center = chunk;
            renderDisplay();
        }
    }
    
    /** 
     * Moves the view upwards.
     */
    public void moveUp() {
        center = center.add(zoom * rotation.up.getX(), zoom * rotation.up.getZ());
        renderDisplay();
    }
    
    /** 
     * Moves the view downwards.
     */
    public void moveDown() {
        center = center.add(zoom * rotation.down.getX(), zoom * rotation.down.getZ());
        renderDisplay();
    }

    /** 
     * Moves the view to the right.
     */
    public void moveRight() {
        center = center.add(zoom * rotation.right.getX(), zoom * rotation.right.getZ());
        renderDisplay();
    }

    /** 
     * Moves the to the left.
     */
    public void moveLeft() {
        center = center.add(zoom * rotation.left.getX(), zoom * rotation.left.getZ());
        renderDisplay();
    }
    
    /**
     * Iterates through the defined height and width.
     * Renders each slot by providing the center and
     * the pixel.
     */
    protected void renderDisplay() {
        for (int z = 0; z < HEIGHT; z++) {
            for (int x = 0; x < WIDTH; x++) {
                renderSlot(center, x, z);
            }
        }
    }
    
    /**
     * Renders the given pixel with the information about 
     * the center pixel and sets it to the inventory.
     */
    protected void renderSlot(Chunk center, int x, int z) {
        Chunk chunk = renderChunk(center, x, z);
        ItemStack icon = getIcon(chunk);
        inv.setItem(getSlot(x, z), icon);
    }
    
    /**
     * Gets the icon for this chunk in the world.
     * Uses the ChunkMap to define whether to display
     * a town icon or a wilderness icon.
     */
    protected ItemStack getIcon(Chunk chunk) {
        Town town = map.getTown(chunk);
        if (town != null) {
            return Util.createItem(Material.LIME_STAINED_GLASS_PANE, town.getName());
        }
        return Util.createItem(Material.BROWN_STAINED_GLASS_PANE, chunk.toString());
    }
    
    /**
     * Returns the chunk based on the center with x/z offset,
     * zoom scaling and direction rotation applied.
     */
    private Chunk renderChunk(Chunk center, int x, int z) {
        x = (x + BASE_X_OFF) * zoom;
        z = (z + BASE_Z_OFF) * zoom;
        return rotation.rotateChunk(center, x, z);
    }
    
    /**
     * Returns the inventory slot for this pixel
     * by applying inventory offsets.
     */
    private int getSlot(int x, int z) {
        return ((HEAD_Z_OFF + z) * WIDTH) + x;
    }
}
