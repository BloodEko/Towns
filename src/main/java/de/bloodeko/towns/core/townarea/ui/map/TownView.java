package de.bloodeko.towns.core.townarea.ui.map;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import de.bloodeko.towns.core.townarea.ChunkService;
import de.bloodeko.towns.core.towns.Town;
import de.bloodeko.towns.util.Chunk;
import de.bloodeko.towns.util.Util;

/**
 * Extending the default view, to highlight a single town
 * and puts other towns in the background.
 */
public class TownView extends MapView {
    private Town town;
    
    public TownView(ChunkService map, MapRotation rotation, Player player, int zoom, 
      Chunk center, Inventory inv, Town town) {
        super(map, rotation, player, zoom, center, inv);
        this.town = town;
    }
    
    @Override
    public ItemStack getIcon(Chunk chunk) {
        Town town = map.getTown(chunk);
        if (this.town == town) {
            return Util.createItem(Material.LIME_STAINED_GLASS_PANE, town.getName());
        } else if (town == null) {
            return Util.createItem(Material.BROWN_STAINED_GLASS_PANE, chunk.toString());
        } else {
            return Util.createItem(Material.WHITE_STAINED_GLASS_PANE, town.getName());
        }
    }
}
