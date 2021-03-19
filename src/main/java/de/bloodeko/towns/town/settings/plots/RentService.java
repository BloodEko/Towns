package de.bloodeko.towns.town.settings.plots;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.sk89q.worldguard.protection.managers.RegionManager;

import de.bloodeko.towns.town.Town;
import de.bloodeko.towns.town.TownRegisterEvent;
import de.bloodeko.towns.town.TownRegistry;
import de.bloodeko.towns.town.TownUnregisterEvent;
import de.bloodeko.towns.town.settings.Settings;

public class RentService implements Listener {
    private RegionManager manager;
    private TownRegistry towns;
    
    public RentService(RegionManager manager, TownRegistry towns) {
        this.manager = manager;
        this.towns = towns;
    }
    
    @EventHandler
    public void onTownLoad(TownRegisterEvent event) {
        manager.getClass(); //todo register regions.
        if (hasPlots(event.town)) {
            System.out.println("Loading town with plots:" + event.town.getSettings().getName());
        }
    }
    
    @EventHandler
    public void onTownUnload(TownUnregisterEvent event) {
        if (hasPlots(event.town)) {
            System.out.println("Unloading town with plots:" + event.town.getSettings().getName());
        }
    }
    
    private boolean hasPlots(Town town) {
        return town.getSettings().hasSetting(Settings.PLOTS);
    }
    
    public void payRents() {
        for (Town town : towns.getTowns()) {
            if (hasPlots(town)) {
                PlotTownHandler plots = (PlotTownHandler) town.getSettings().readSetting(Settings.PLOTS);
                for (PlotData plot : plots.plots.values()) {
                    
                    System.out.println("Checking to collect rent for " + plot.id + " in " + town.getSettings().getName());
                    System.out.println("Renter: " + plot.renter);
                    System.out.println("Rent: " + plot.rent);
                    System.out.println("");
                }
            }
        }
    }
}
