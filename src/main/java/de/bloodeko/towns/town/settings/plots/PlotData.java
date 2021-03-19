package de.bloodeko.towns.town.settings.plots;

import java.util.UUID;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;

public class PlotData {
    public int id;
    public String name;
    public ProtectedRegion region;
    public UUID renter;
    public UUID reversedFor;
    public boolean rentable;
    public int rent;
    public int debt;
    
    public PlotData(int id, ProtectedRegion region) {
        this.id = id;
        this.region = region;
    }

    public Object serialize() {
        return id;
    }
}
