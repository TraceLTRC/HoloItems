package com.strangeone101.holoitemsapi.enchantment;
import com.strangeone101.holoitemsapi.Keys;
import io.papermc.paper.registry.RegistryAccess;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.inventory.PrepareGrindstoneEvent;
import org.bukkit.inventory.meta.Repairable;
import org.bukkit.persistence.PersistentDataType;
import xyz.holocons.mc.holoitemsrevamp.HoloItemsRevamp;

import java.util.Map;

public class AnvilListener implements Listener {

    private final HoloItemsRevamp plugin;

    public AnvilListener(HoloItemsRevamp plugin) {
        this.plugin = plugin;
    }

    @EventHandler(ignoreCancelled = true)
    public void onPrepareAnvil(PrepareAnvilEvent event) {
        // Handle Anvil scenarios involving the BOOK_LIKE key.
        var inventory = event.getInventory();
        var firstItem = inventory.getFirstItem();
        var secondItem = inventory.getSecondItem();
        if(firstItem == null || secondItem == null) {
            // BOOK_LIKE doesn't care about these scenarios (only renaming possible)
            return;
        }

        if(Keys.BOOK_LIKE.get(secondItem.getItemMeta().getPersistentDataContainer())) {
            // Second item is book-like. That means its enchantments need to be applied to the result.
            var result = inventory.getResult();
            if(result == null) {
                result = firstItem.clone();
                event.setResult(result);
            }

            boolean addedEnch = false;
            for(Map.Entry<Enchantment, Integer> ench : secondItem.getEnchantments().entrySet()) {
                // Note the >=: I do not care to implement "merging" (ie: two lv3 enchs becomes a lv4 ench)
                if(result.getEnchantmentLevel(ench.getKey()) >= ench.getValue()) {
                    continue;
                }
                try {
                    result.addEnchantment(ench.getKey(), ench.getValue());
                } catch (IllegalArgumentException ignored) {
                    // either the enchantment is null (not possible)
                    // or the enchantment is not applicable, so they weren't applied
                    // in either case, an enchantment wasn't added
                    continue;
                }
                addedEnch = true;
            }

            if(!addedEnch) {
                // Whatever enchantment(s) were on the 2nd item weren't allowed to be on the result.
                // Therefore, block the result from being made.
                event.setResult(null);
            }
            else {
                // If it succeeded, the repair-cost also needs to be set.
                // If it's non-zero, the book_like tag tells us what the repair cost should be!
                // TODO: Remove this todo when your IDE is no longer marking getView and setRepairCost as unstable
                //   (when that happens, it might get deprecated/changed/removed.)
                var anvilView = event.getView();
                if(firstItem instanceof Repairable repairable) {
                    anvilView.setRepairCost(repairable.getRepairCost());
                }
                else {
                    anvilView.setRepairCost(1);
                }
            }
        }

        if(Keys.BOOK_LIKE.get(firstItem.getItemMeta().getPersistentDataContainer())) {
            // First item is book-like.
            // If this is adding enchantments, don't let the result stay book-like.
            // (If this is just a renaming or repairing, it's fine.)
            var result = event.getResult();
            if(result != null) {
                if(!firstItem.getEnchantments().equals(result.getEnchantments())) {
                    Keys.BOOK_LIKE.set(result.getItemMeta().getPersistentDataContainer(), false);
                }
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onPrepareGrindstone(PrepareGrindstoneEvent event) {
        // Make grindstones remove book-like value.
        var result = event.getResult();
        if(result != null) {
            var meta = result.getItemMeta();
            Keys.BOOK_LIKE.set(meta.getPersistentDataContainer(), false);
            result.setItemMeta(meta);
        }
    }
}
