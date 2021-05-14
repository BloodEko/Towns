package de.bloodeko.towns.core.townstages.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.bloodeko.towns.core.townstages.domain.Bundle;
import de.bloodeko.towns.core.townstages.ui.InventoyListener.GUIView;
import de.bloodeko.towns.util.Messages;
import de.bloodeko.towns.util.ModifyException;

/**
 * Represents the UI for an inventory that displays 
 * stage items and reacts to interaction.
 */
public class StageView implements GUIView {
    private final int HEADER_SIZE = 9;
    private Map<Material, Bundle> bundles;
    
    public StageView(Map<Material, Bundle> bundles) {
        this.bundles = bundles;
    }
    
    /**
     * Renders the inventory and opens it for the player.
     */
    public void open(Player player, Inventory inv) {
        renderItems(inv);
        player.openInventory(inv);
    }

    /**
     * Reacts to the click, updates the internal data
     * and eventually re-renders the inventory.
     */
    @Override
    public void clickSlot(InventoryClickEvent click) {
        if (click.getSlot() < HEADER_SIZE || click.getCurrentItem() == null) {
            return;
        }
        Bundle bundle = bundles.get(click.getCurrentItem().getType());
        if (bundle == null) {
            return;
        }
        switch(click.getClick()) {
            case LEFT:
                deposit(click.getWhoClicked(), bundle, 1);
                break;
            case SHIFT_LEFT: 
                deposit(click.getWhoClicked(), bundle, 64);
                break;
            case RIGHT:
                withdraw(click.getWhoClicked(), bundle, 1);
                break;
            case SHIFT_RIGHT:
                withdraw(click.getWhoClicked(), bundle, 64);
                break;
            default:
                break;
        }
        renderItems(click.getInventory());
    }
    
    /**
     * Calculates how many items to take. Tries to take the qty.
     * Reduces the qty to the maximum. Reduces the qty to available.
     */
    private int countTake(int maximum, int qty, int available) {
        if (qty > maximum) {
            qty = maximum;
        }
        if (qty > available) {
            qty = available;
        }
        return qty;
    }
    
    /**
     * Deposits items in the bundle and takes them from the player inventory.
     * If possible, otherwise sends an error message.
     */
    private void deposit(HumanEntity player, Bundle bundle, int qty) {
        Material material = bundle.getMaterial();
        Inventory inv = player.getInventory();
        
        int maximum = bundle.getRequired() - bundle.getQty();
        int available = countItems(inv, material);
        int take = countTake(maximum, qty, available);
        
        if (maximum <= 0) {
            String message = Messages.get("cmds.stage.gui.bundleIsFull");
            player.sendMessage(message);
            return;
        }
        if (available == 0) {
            String message = Messages.get("cmds.stage.gui.notEnoughItems", qty, material);
            player.sendMessage(message);
            return;
        }
        
        bundle.add(take);
        takeItems(inv, material, take);
    }
    
    /**
     * Withdraws items from the bundle and gives them to the player inventory.
     * If possible, otherwise sends an error message.
     */
    private void withdraw(HumanEntity player, Bundle bundle, int qty) {
        Material material = bundle.getMaterial();
        Inventory inv = player.getInventory();
        
        int maximum = countFreeSpace(inv, new ItemStack(material));
        int available = bundle.getQty();
        int take = countTake(maximum, qty, available);
        
        if (maximum == 0) {
            player.sendMessage(Messages.get("cmds.stage.gui.youHaveNoSpace"));
            return;
        }
        if (available == 0) {
            player.sendMessage(Messages.get("cmds.stage.gui.bundleIsEmpty"));
            return;
        }
        
        bundle.sub(take);
        giveStack(player.getInventory(), material, take);
    }

    /**
     * Returns how many similar items the inventory 
     * can take of this.
     */
    private int countFreeSpace(Inventory inv, ItemStack val) {
        int count = 0;
        for (int i = 0; i < 36; i++) {
            ItemStack item = inv.getItem(i);
            
            if (item == null) {
                count += val.getMaxStackSize();
            } 
            else if (item.isSimilar(val)) {
                count += val.getMaxStackSize() - item.getAmount();
            }
        }
        return count;
    }
    
    /**
     * Returns the amount of items found in the inventory.
     */
    private int countItems(Inventory inv, Material material) {
        int count = 0;
        for (ItemStack stack : inv.getContents()) {
            if (stack != null && stack.getType() == material) {
                count += stack.getAmount();
            }
        }
        return count;
    }
    
    /**
     * Gives a single stack to a player inventory.
     * Any excessive items get deleted.
     */
    private void giveStack(Inventory inv, Material material, int qty) {
        ItemStack stack = new ItemStack(material);
        stack.setAmount(qty);
        inv.addItem(stack);
    }
    
    /**
     * Takes as much items as possible up to the quantity 
     * from the inventory that match the given material.
     */
    private void takeItems(Inventory inv, Material material, int qty) {
        ItemStack[] content = inv.getContents();
        for (int i = 0; i < content.length; i++) {
            ItemStack stack = content[i];
            if (stack == null || stack.getType() != material) {
                continue;
            }
            int stackAmount = stack.getAmount();
            if (qty >= stackAmount) {
                inv.clear(i);
                qty -= stackAmount;
            } else {
                stack.setAmount(stackAmount - qty);
                qty -= stackAmount - qty;
            }
            
            if (qty <= 0) {
                return;
            }
        }
    }
    
    private void renderItems(Inventory inv) {
        if (bundles.size() >= inv.getSize() - HEADER_SIZE) {
            throw new ModifyException("GUI is too small.");
        }
        int i = 0;
        for (Bundle bundle : bundles.values()) {
            inv.setItem(HEADER_SIZE + i, asItem(bundle));
            i++;
        }
    }
    
    private ItemStack asItem(Bundle bundle) {
        ItemStack stack = new ItemStack(bundle.getMaterial());
        ItemMeta meta = stack.getItemMeta();
        meta.setLore(getLore(bundle));
        stack.setItemMeta(meta);
        return stack;
    }
    
    private List<String> getLore(Bundle bundle) {
        List<String> list = new ArrayList<>();
        list.add(Messages.get("cmds.stage.gui.lore.required", bundle.getRequired()));
        list.add(Messages.get("cmds.stage.gui.lore.given", bundle.getQty()));
        return list;
    }
}
