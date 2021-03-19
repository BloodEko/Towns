package de.bloodeko.towns.protect;

import org.bukkit.entity.Hanging;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.hanging.HangingBreakEvent;
import org.bukkit.event.hanging.HangingBreakEvent.RemoveCause;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import de.bloodeko.towns.session1.ChunkMap;
import de.bloodeko.towns.session2.Town;

/**
 * 
 */
public class BreakHanging extends AbstractListener {
    
    public BreakHanging(ChunkMap map) {
        super(map);
    }
    
    @EventHandler
    public void onHangingBreak(HangingBreakEvent event) {
        if (event.getCause() == RemoveCause.EXPLOSION) {
            Town town = getTown(event.getEntity());
            if (town == null) {
                return;
            }
            event.setCancelled(true);
        }
    }
    
    @EventHandler
    public void onExplosion(HangingBreakByEntityEvent event) {
        Town town = getTown(event.getEntity());
        if (town == null) {
            return;
        }
        if (event.getRemover() instanceof Player) {
            Player player = (Player) event.getRemover();

            if (!town.isAllowedToBuild(player)) {
                player.sendMessage("You cannot break hangings here!");
                event.setCancelled(true);
            }
            return;
        }
        event.setCancelled(true);
    }
    
    @EventHandler
    public void onPlace(HangingPlaceEvent event) {
        Town town = getTown(event.getEntity());
        if (town == null) {
            return;
        }
        if (!town.isAllowedToBuild(event.getPlayer())) {
            event.getPlayer().sendMessage("You cannot place hangings here!");
            event.setCancelled(true);
        }
        return;
    }
    
    @EventHandler
    public void onLeftClick(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Hanging)) {
            return;
        }
        Town town = getTown(event.getEntity());
        if (town == null) {
            return;
        }
        if (event.getDamager() instanceof Player) {
            Player player = (Player) event.getDamager();

            if (!town.isAllowedToBuild(player)) {
                player.sendMessage("You cannot break hangings here!");
                event.setCancelled(true);
            }
            return;
        }
        event.setCancelled(true);
    }
    
    @EventHandler
    public void onRightClick(PlayerInteractEntityEvent event) {
        if (!(event.getRightClicked() instanceof Hanging)) {
            return;
        }
        Town town = getTown(event.getRightClicked());
        if (town == null) {
            return;
        }
        if (!town.isAllowedToBuild(event.getPlayer())) {
            event.getPlayer().sendMessage("You cannot click hanings here!");
            event.setCancelled(true);
        }
        return;
    }
}
