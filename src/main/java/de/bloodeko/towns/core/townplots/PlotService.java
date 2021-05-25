package de.bloodeko.towns.core.townplots;

import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import de.bloodeko.towns.Services;
import de.bloodeko.towns.core.towns.Town;
import de.bloodeko.towns.util.ModifyException;
import net.milkbowl.vault.economy.Economy;

public class PlotService {
    private Map<Integer, PlotHandler> data;
    
    PlotService(Map<Integer, PlotHandler> data) {
        this.data = data;
    }
    
    /**
     * Sets the PlotHandler for this town.
     */
    public void setHandler(Integer id, PlotHandler handler) {
        data.put(id, handler);
    }
    
    /**
     * Removes the PlotHandler, plots and WorldGuard
     * regions of these plots, for that town.
     */
    public void remove(Integer id) {
        PlotHandler handler = data.remove(id);
        if (handler == null) {
            return;
        }
        for (PlotData plot : handler.getPlots()) {
            Services.regionmanager().removeRegion(plot.region.getId());
        }
    }
    
    /**
     * Throws an exception if the town still contains rented plots.
     */
    public void verfiyDeletion(Integer id) {
        PlotHandler handler = data.get(id);
        if (handler == null) {
            return;
        }
        for (PlotData plot : handler.getPlots()) {
            if (plot.renter != null) {
                throw new ModifyException("settings.plot.rentservice.cantDelete");
            }
        }
    }
    
    /**
     * Returns the Handler for that town, or null.
     */
    public PlotHandler getPlots(Integer id) {
        return data.get(id);
    }
    
    /**
     * Pays the rents for all available towns to their 
     * first matching governor.
     */
    public void payRents() {
        for (Entry<Integer, PlotHandler> entry : getView().entrySet()) {
            Town town = Services.townservice().get(entry.getKey());
            PlotHandler handler = entry.getValue();
            UUID ownerID = town.getPeople().getGovernors().iterator().next();
            OfflinePlayer owner = Bukkit.getOfflinePlayer(ownerID);
            payRent(handler, owner);
        }
    }
    
    /**
     * Pays the rents for a single town.
     */
    private void payRent(PlotHandler handler, OfflinePlayer owner) {
        for (PlotData plot : handler.getPlots()) {
            if (plot.renter == null || plot.rent == 0) {
                continue;
            }
            payRent(plot, owner);
        }
    }
    
    /**
     * Pays the rent for a single plot. Increases the debt 
     * on the plot if the renter has no money left to pay.
     */
    private void payRent(PlotData plot, OfflinePlayer owner) {
        Economy economy = Services.economy();
        OfflinePlayer renter = Bukkit.getOfflinePlayer(plot.renter);
        double renterMoney = economy.getBalance(renter);
        
        if ((renterMoney - plot.rent) < 0) {
            plot.debt += plot.rent;
        } else {
            economy.withdrawPlayer(renter, plot.rent);
            economy.depositPlayer(owner, plot.rent);
        }
    }
    
    /**
     * Returns a view of the plot data.
     */
    public Map<Integer, PlotHandler> getView() {
        return Collections.unmodifiableMap(data);
    }
}
