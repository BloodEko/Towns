package de.bloodeko.towns.core.townplots;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

public class PlotHandler {
    public Map<Integer, PlotData> plots;
    public int nextId;
    
    public PlotHandler(Map<Integer, PlotData> plots, int nextId) {
        this.plots = plots;
        this.nextId = nextId;
    }
    
    public PlotHandler() {
        this.plots = new HashMap<>();
        this.nextId = 1;
    }

    public PlotData getPlot(int id) {
        return plots.get(id);
    }
    
    public Collection<PlotData> getPlots() {
        return plots.values();
    }

    public PlotData addPlot(int id, BlockVector3 pos1, BlockVector3 pos2, RegionManager manager) {
        ProtectedRegion region = new ProtectedCuboidRegion("plot_" + id + "_" + nextId, true, pos1, pos2);
        region.setPriority(1);
        manager.addRegion(region);
        PlotData plot = PlotData.from(nextId, region);
        plots.put(nextId++, plot);
        return plot;
    }

    public void removePlot(int id, RegionManager manager) {
        PlotData removed = plots.remove(id);
        if (removed != null) {
            manager.removeRegion(removed.region.getId());
        }
    }
}
