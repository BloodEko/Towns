package de.bloodeko.towns.protect;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.event.Listener;

import de.bloodeko.towns.session1.Chunk;
import de.bloodeko.towns.session1.ChunkMap;
import de.bloodeko.towns.session2.Town;

/**
 * Change Priorities in aspect of WorldGuard and GS.
 * 
 * LiquidFlow, CropsTrample, Movement, FireSpread, InventoryOpen, DamageEntity.
 * BreakHanging, DamageEntities.
 *
 */
public abstract class AbstractListener implements Listener {
    public ChunkMap map;
    
    public AbstractListener(ChunkMap map) {
        this.map = map;
    }
    
    public ChunkMap getMap() {
        return map;
    }
    
    public Town getTown(Chunk chunk) {
        return map.getTown(chunk);
    }
    
    public Town getTown(Entity entity) {
        return map.getTown(Chunk.fromLocation(entity.getLocation()));
    }
    
    public Town getTown(Location loc) {
        return map.getTown(Chunk.fromLocation(loc));
    }
}
