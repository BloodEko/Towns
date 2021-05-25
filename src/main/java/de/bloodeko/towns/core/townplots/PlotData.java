package de.bloodeko.towns.core.townplots;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import de.bloodeko.towns.Services;
import de.bloodeko.towns.util.Node;
import de.bloodeko.towns.util.Node.Pair;

public class PlotData {
    public int id;
    public String name; //name to note?
    public ProtectedRegion region;
    public UUID renter;
    public UUID reversedFor;
    public boolean rentable;
    public int rent;
    public int debt;
    
    /**
     * Creates a new Plot and sets its ID and WorldGuardRegion.
     */
    public static PlotData from(int id, ProtectedRegion region) {
        PlotData data = new PlotData();
        data.id = id;
        data.region = region;
        return data;
    }

    /**
     * Creates a PlotService from a Node that has IDs 
     * as keys, where each represents a PlotHandler.
     */
    public static PlotService read(Node node) {
        PlotService service = new PlotService(new HashMap<>());
        for (Pair pair : node.entries()) {
            Integer id = Integer.valueOf(pair.key);
            PlotHandler handler = readHandler((Node) pair.value);
            service.setHandler(id, handler);
        }
        return service;
    }
    
    /**
     * Returns a Node with subnNodes, which are IDs and each
     * represents a PlotHandler.
     */
    public static Node write(PlotService service) {
        Node node = new Node();
        for (Entry<Integer, PlotHandler> entry : service.getView().entrySet()) {
            node.set(entry.getKey().toString(), writeHandler(entry.getValue()));
        }
        return node;
    }
    
    /**
     * Reads from a node that has a "nextId" and "plots" section.
     * Registers the plot region to WorldGuard.
     */
    private static PlotHandler readHandler(Node node) {
        int nextId = node.getInt("nextId");
        Map<Integer, PlotData> data = new HashMap<>();
        for (Pair pair : node.getNode("plots").entries()) {
            Integer id = Integer.valueOf(pair.key);
            PlotData plot = readPlot((Node) pair.value, id);
            Services.regionmanager().addRegion(plot.region);
            data.put(id, plot);
        }
        return new PlotHandler(data, nextId);
    }
    
    /**
     * Returns a node that has a "nextId" and "plots" section.
     */
    private static Node writeHandler(PlotHandler handler) {
        Node node = new Node();
        node.set("nextId", handler.nextId);
        
        Node plots = new Node();
        node.set("plots", plots);
        
        for (Entry<Integer, PlotData> entry: handler.plots.entrySet()) {
            plots.set(entry.getKey().toString(), writePlot(entry.getValue()));
        }
        return node;
    }

    /**
     * Returns a node with many keys, that represent a plot.
     */
    private static Node writePlot(PlotData data) {
        Node node = new Node();
        node.set("name", data.name);
        node.set("region", data.region.getId());
        node.set("p1", data.region.getMinimumPoint());
        node.set("p2", data.region.getMaximumPoint());
        node.set("builders", data.region.getMembers().getUniqueIds());
        node.set("renter", data.renter);
        node.set("reservedFor", data.reversedFor);
        node.set("rent", data.rent);
        node.set("debt", data.debt);
        return node;
    }

    /**
     * Reads from a node with many keys, to create a Plot.
     * Stores the id of the plot in it.
     */
    private static PlotData readPlot(Node plot, int id) {
        PlotData data = new PlotData();
        data.id = id;
        data.name = plot.getString("name");
        
        String name = plot.getString("region");
        BlockVector3 p1 = plot.getVector("p1");
        BlockVector3 p2 = plot.getVector("p2");
        data.region = new ProtectedCuboidRegion(name, true, p1, p2);
        data.region.setPriority(1);
        
        for (UUID uuid : plot.getUUIDSet("builders")) {
            data.region.getMembers().addPlayer(uuid);
        }
        
        data.renter = plot.getUUID("renter");
        data.reversedFor = plot.getUUID("reservedFor");
        data.rent = plot.getInt("rent");
        data.debt = plot.getInt("debt");
        
        return data;
    }
}
