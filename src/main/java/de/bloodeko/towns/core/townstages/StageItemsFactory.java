package de.bloodeko.towns.core.townstages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;

import de.bloodeko.towns.core.townstages.domain.Bundle;

/**
 * Returns templates of bundle for specific stages.
 */
public class StageItemsFactory {
    
    /**
     * Returns a new mapping of bundles for 
     * a specific stage.
     */
    public static Map<Material, Bundle> getItems(int stage) {
        switch (stage) {
            case 1: return listToMap(getBundle1());
            case 2: return listToMap(getBundle2());
            case 3: return listToMap(getBundle3());
            default: return listToMap(getBundle4());
        }
    }
    
    private static Map<Material, Bundle> listToMap(List<Bundle> list) {
        Map<Material, Bundle> map = new HashMap<>();
        for (Bundle bundle : list) {
            map.put(bundle.getMaterial(), bundle);
        }
        return map;
    }
    
    private static List<Bundle> getBundle1() {
        List<Bundle> list = new ArrayList<>();
        list.add(Bundle.newEmptyOne(Material.COBBLESTONE, 150));
        list.add(Bundle.newEmptyOne(Material.OAK_WOOD, 100));
        return list;
    }        

    private static List<Bundle> getBundle2() {
        List<Bundle> list = new ArrayList<>();
        list.add(Bundle.newEmptyOne(Material.STONE, 240));
        list.add(Bundle.newEmptyOne(Material.OAK_WOOD, 130));
        list.add(Bundle.newEmptyOne(Material.ARROW, 20));
        return list;
    }

    private static List<Bundle> getBundle3() {
        List<Bundle> list = new ArrayList<>();
        list.add(Bundle.newEmptyOne(Material.SADDLE, 2));
        list.add(Bundle.newEmptyOne(Material.OAK_FENCE, 120));
        list.add(Bundle.newEmptyOne(Material.WHEAT, 100));
        return list;
    }

    private static List<Bundle> getBundle4() {
        List<Bundle> list = new ArrayList<>();
        list.add(Bundle.newEmptyOne(Material.DIAMOND, 100));
        return list;
    }
}