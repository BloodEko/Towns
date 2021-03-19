package de.bloodeko.towns.town.settings.plots;

import java.util.UUID;

import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import de.bloodeko.towns.util.Node;

//name to note?
public class PlotData {
    public int id;
    public String name;
    public ProtectedRegion region;
    public UUID renter;
    public UUID reversedFor;
    public boolean rentable;
    public int rent;
    public int debt;
    
    public static PlotData from(int id, ProtectedRegion region) {
        PlotData data = new PlotData();
        data.id = id;
        data.region = region;
        return data;
    }

    public Node serialize() {
        Node node = new Node();
        node.set("name", name);
        node.set("region", region.getId());
        node.set("p1", region.getMinimumPoint());
        node.set("p2", region.getMaximumPoint());
        node.set("builders", region.getMembers().getUniqueIds());
        node.set("renter", renter);
        node.set("reservedFor", reversedFor);
        node.set("rent", rent);
        node.set("debt", debt);
        return node;
    }

    public static PlotData deserialize(Node plot, int id) {
        PlotData data = new PlotData();
        data.id = id;
        data.name = plot.getString("name");
        
        String name = plot.getString("region");
        BlockVector3 p1 = plot.getVector("p1");
        BlockVector3 p2 = plot.getVector("p2");
        data.region = new ProtectedCuboidRegion(name, true, p1, p2);
        
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
