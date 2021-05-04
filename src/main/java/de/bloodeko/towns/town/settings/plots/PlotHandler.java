package de.bloodeko.towns.town.settings.plots;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import de.bloodeko.towns.util.Node;
import de.bloodeko.towns.util.Node.Pair;

public class PlotHandler {
    public Map<Integer, PlotData> plots;
    public int nextId;
    
    public PlotHandler(Map<Integer, PlotData> plots, int nextId) {
        this.plots = plots;
        this.nextId = nextId;
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

    public Node serialize() {
        Node node = new Node();
        node.set("nextId", nextId);
        
        Node plots = node.newNode("plots");
        for (PlotData plot : this.plots.values()) {
            plots.set(String.valueOf(plot.id), plot.serialize());
        }
        return node;
    }
    
    public static PlotHandler deserialize(Node plotSetting) {
        Map<Integer, PlotData> map = new HashMap<>();
        Node plots = plotSetting.getNode("plots");
        
        for (Pair pair : plots.entries()) {
            int id = Integer.valueOf(pair.key);
            Node plot = plots.getNode(pair.key);
            map.put(id, PlotData.deserialize(plot, id));
        }
        return new PlotHandler(map, plotSetting.getInt("nextId"));
    }
}
