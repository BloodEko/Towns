package de.bloodeko.towns.town.settings.plots;

import java.util.Collection;
import java.util.Map;

import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

public class PlotTownHandler {
    public Map<Integer, PlotData> plots;
    public int nextId;
    
    public PlotTownHandler(Map<Integer, PlotData> plots, int nextId) {
        this.plots = plots;
        this.nextId = nextId;
    }
    
    public PlotData getPlot(int id) {
        return plots.get(id);
    }
    
    public Collection<PlotData> getPlots() {
        return plots.values();
    }

    public void addPlot(int id, BlockVector3 pos1, BlockVector3 pos2, RegionManager manager) {
        ProtectedRegion region = new ProtectedCuboidRegion("plot_" + id + "_" + nextId, true, pos1, pos2);
        manager.addRegion(region);
        plots.put(nextId, new PlotData(nextId++, region));
    }

    public void removePlot(int id, RegionManager manager) {
        PlotData removed = plots.remove(id);
        if (removed != null) {
            PlotData data = plots.remove(id);
            manager.removeRegion(data.region.getId());
        }
    }

    public Map<String, Object> serialize() {
        return null;
    }
    
    public static PlotTownHandler deserialize(Map<String, Object> root) {
        return null;
    }
}
