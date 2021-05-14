package de.bloodeko.towns.core.townstages;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import de.bloodeko.towns.Services;
import de.bloodeko.towns.core.townstages.domain.Bundle;
import de.bloodeko.towns.core.townstages.domain.Stage;
import de.bloodeko.towns.core.townstages.ui.InventoyListener;
import de.bloodeko.towns.core.townstages.ui.RiseCmd;
import de.bloodeko.towns.core.townstages.ui.StageView;
import de.bloodeko.towns.core.townstages.ui.StageViewCmd;
import de.bloodeko.towns.util.Messages;
import de.bloodeko.towns.util.Util;
import de.bloodeko.towns.util.cmds.CmdBase;
import de.bloodeko.towns.util.cmds.CmdFactory;

public class StageFactory {
    
    public static void load(Map<String, CmdBase> cmds) {
        CmdFactory.put(cmds, "stageItems", new StageViewCmd(StageFactory.newGuiService()));
        CmdFactory.put(cmds, "rise", new RiseCmd());
    }
    
    private static InventoyListener newGuiService() {
        Plugin plugin = Services.plugin();
        InventoyListener handler = new InventoyListener(new HashMap<>());
        plugin.getServer().getPluginManager().registerEvents(handler, plugin);
        return handler;
    }
    
    /**
     * Creates a new Stage view for the items.
     */
    public static StageView newView(Stage stage) {
        Map<Material, Bundle> map = new HashMap<>();
        for (Bundle bundle : stage.getItems().values()) {
            map.put(bundle.getMaterial(), bundle);
        }
        return new StageView(map);
    }
    
    /**
     * Returns a new inventory with the Stage design.
     * Picks an inventory size to fit all the items.
     */
    public static Inventory newMapInventory() {
        Inventory inv = Bukkit.createInventory(null, 18, 
          Messages.get("cmds.stage.gui.header"));
        
        for (int i = 0; i < 9; i++) {
            ItemStack item = Util.createItem(Material.BLACK_STAINED_GLASS_PANE, "~~~");
            inv.setItem(i, item);
        }
        return inv;
    }

    /**
     * Returns a new first default stage.
     */
    public static Stage getStartStage() {
        return new Stage(1, StageItemsFactory.getItems(1));
    }
}
