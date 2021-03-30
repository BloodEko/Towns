package de.bloodeko.towns.town.settings.plots;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.sk89q.worldguard.protection.managers.RegionManager;

import de.bloodeko.towns.town.Town;
import de.bloodeko.towns.town.TownDeleteEvent;
import de.bloodeko.towns.town.TownLoadEvent;
import de.bloodeko.towns.town.TownRegistry;
import de.bloodeko.towns.town.settings.Settings;
import net.milkbowl.vault.economy.Economy;

public class RentService implements Listener {
    private RegionManager manager;
    private TownRegistry towns;
    private Economy economy;
    
    public RentService(RegionManager manager, TownRegistry towns, Economy economy) {
        this.manager = manager;
        this.towns = towns;
        this.economy = economy;
    }
    
    @EventHandler
    public void onTownLoad(TownLoadEvent event) {
        for (PlotData plot : getPlots(event.town)) {
            manager.addRegion(plot.region);
        }
    }
    
    @EventHandler
    public void onTownDelete(TownDeleteEvent event) {
        boolean rented = false;
        for (PlotData plot : getPlots(event.getTown())) {
            if (plot.renter != null) {
                rented = true;
                break;
            }
        }
        if (rented) {
            event.setCancelled("settings.plot.rentservice.cantDelete");
            return;
        }
        for (PlotData plot : getPlots(event.getTown())) {
            manager.removeRegion(plot.region.getId());
        }
    }
    
    public Collection<PlotData> getPlots(Town town) {
        if (hasSetting(town)) {
            PlotHandler plots = (PlotHandler) town.getSettings().get(Settings.PLOTS);
            return plots.getPlots();
        }
        return Collections.emptyList();
    }
    
    private boolean hasSetting(Town town) {
        return town.getSettings().has(Settings.PLOTS);
    }
    
    public void payRents() {
        for (Town town : towns.getTowns()) {
            UUID ownerId = town.getPeople().getGovernors().iterator().next();
            OfflinePlayer owner = Bukkit.getPlayer(ownerId);
            
            for (PlotData plot : getPlots(town)) {
                int price = plot.rent;
                if (plot.renter == null || price == 0) {
                    continue;
                }
                OfflinePlayer player = Bukkit.getPlayer(plot.renter);
                double money = economy.getBalance(player);
                
                if (money - price < 0) {
                    plot.debt += price;
                    System.out.println(plot.id + ": Increasing depth " + price + " for " + owner.getName());
                }
                else {
                    economy.withdrawPlayer(player, price);
                    economy.depositPlayer(owner, price);
                    
                    System.out.println(plot.id + ": Taking " + price + " from " + player.getName());
                    System.out.println(plot.id + ": Giving " + price + " to " + owner.getName());
                }
            }
        }
    }
}
