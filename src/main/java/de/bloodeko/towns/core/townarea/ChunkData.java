package de.bloodeko.towns.core.townarea;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.sk89q.worldguard.protection.managers.RegionManager;

import de.bloodeko.towns.Services;
import de.bloodeko.towns.util.Chunk;
import de.bloodeko.towns.util.Node;

public class ChunkData {
    
    /**
     * Reads the "chunks" section from a Node.
     * Returns a ChunkSerivce with an AreaService that holds 
     * ChunkAreas with fully updated bounding boxes.
     */
    public static ChunkService read(Node node, RegionManager manager) {
        Map<Chunk, Integer> chunkData = getChunkMap(getChunkList(node));
        Map<Integer, ChunkArea> areaData = getAreasMap(getAreas(chunkData));
        
        AreaService areas = new AreaService(areaData, manager);
        ChunkService service = new ChunkService(chunkData, areas);
        
        return service;
    }
    
    /**
     * Returns a string-list of chunk mappings or an
     * empty list.
     */
    private static List<String> getChunkList(Node node) {
        return node.get("chunks") == null ?
          new ArrayList<>() : node.getStringList("chunks");
    }
    
    /**
     * Reads a string-list chunks in the format "x,z;townID" 
     * and converts it to a flat map.
     */
    private static Map<Chunk, Integer> getChunkMap(List<String> list) {
        Map<Chunk, Integer> map = new HashMap<>();
        for (String str : list) {
            String[] split = str.split(";");
            map.put(Chunk.fromString(split[0]), Integer.parseInt(split[1]));
        }
        return map;
    }
    
    /**
     * Creates town areas from the normalized chunk data.
     */
    private static Map<Integer, Set<Chunk>> getAreas(Map<Chunk, Integer> data) {
        Map<Integer, Set<Chunk>> areas = new HashMap<>();
        for (Entry<Chunk, Integer> entry : data.entrySet()) {
            Set<Chunk> area = areas.get(entry.getValue());
            if (area == null) {
                area = new HashSet<>();
                areas.put(entry.getValue(), area);
            }
            area.add(entry.getKey());
        }
        return areas;
    }
    
    /**
     * Converts the data map to ChunkAreas with updated bounding boxes.
     */
    private static Map<Integer, ChunkArea> getAreasMap(Map<Integer, Set<Chunk>> data) {
        Map<Integer, ChunkArea> areas = new HashMap<>();
        for (Entry<Integer, Set<Chunk>> entry : data.entrySet()) {
            int town = entry.getKey();
            areas.put(town, newArea(entry.getValue(), town));
        }
        return areas;
    }
    
    /**
     * Creates a new registered region with a bounding box that 
     * covers all chunks and a default area for that town.
     */
    private static ChunkArea newArea(Set<Chunk> chunks, int town) {
        ChunkSides sides = new ChunkSides(chunks.iterator().next());
        sides.updateFor(chunks);
        TownRegion view = new TownRegion(town);
        view.setBoundingBox(sides);
        Services.regionmanager().addRegion(view);
        return new ChunkArea(chunks, sides, view);
    }
    
    /**
     * Returns a string-list with the data from the service
     * in a serialized form.
     */
    public static List<String> write(ChunkService service) {
        List<String> list = new ArrayList<>();
        for (Entry<Chunk, Integer> entry : service.getView().entrySet()) {
            list.add(entry.getKey().toString() + ";" + entry.getValue());
        }
        return list;
    }
}
