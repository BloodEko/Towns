package de.bloodeko.towns.town.settings.plots;

import java.util.Collection;
import java.util.Collections;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.sk89q.worldguard.protection.managers.RegionManager;

import de.bloodeko.towns.town.Town;
import de.bloodeko.towns.town.TownLoadEvent;
import de.bloodeko.towns.town.TownRegistry;
import de.bloodeko.towns.town.TownDeletedEvent;
import de.bloodeko.towns.town.settings.Settings;

public class RentService implements Listener {
    private RegionManager manager;
    private TownRegistry towns;
    
    public RentService(RegionManager manager, TownRegistry towns) {
        this.manager = manager;
        this.towns = towns;
    }
    
    @EventHandler
    public void onTownLoad(TownLoadEvent event) {
        for (PlotData plot : getPlots(event.town)) {
            manager.addRegion(plot.region);
            System.out.println("Loading wg region for " + plot.id);
            System.out.println("");
        }
    }
    
    @EventHandler
    public void onTownUnload(TownDeletedEvent event) {
        for (PlotData plot : getPlots(event.town)) {
            manager.removeRegion(plot.region.getId());
            System.out.println("Deleting wg rg region for " 
              + plot.id + " " + plot.region.getId());
        }
    }
    
    private boolean hasPlots(Town town) {
        return town.getSettings().hasSetting(Settings.PLOTS);
    }
    
    public Collection<PlotData> getPlots(Town town) {
        if (hasPlots(town)) {
            PlotHandler plots = (PlotHandler) town.getSettings().readSetting(Settings.PLOTS);
            return plots.getPlots();
        }
        return Collections.emptyList();
    }
    
    public void payRents() {
        for (Town town : towns.getTowns()) {
            for (PlotData plot : getPlots(town)) {                    
                System.out.println("Checking to collect rent for " + plot.id 
                  + " in " + town.getSettings().getName());
                System.out.println("Renter: " + plot.renter);
                System.out.println("Rent: " + plot.rent);
                System.out.println("");
            }
        }
    }
}
