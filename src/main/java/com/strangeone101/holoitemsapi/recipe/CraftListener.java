package com.strangeone101.holoitemsapi.recipe;

import com.strangeone101.holoitemsapi.enchantment.EnchantManager;
import com.strangeone101.holoitemsapi.item.CustomItemManager;
import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import io.papermc.paper.registry.TypedKey;
import io.papermc.paper.registry.tag.TagKey;
import io.papermc.paper.registry.tag.Tag;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Crafter;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.CrafterCraftEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.*;
import xyz.holocons.mc.holoitemsrevamp.HoloItemsRevamp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CraftListener implements Listener {

    private final HoloItemsRevamp plugin;
    private final RecipeManager recipeManager;
    private final EnchantManager enchantManager;

    public CraftListener(HoloItemsRevamp plugin) {
        this.plugin = plugin;
        this.recipeManager = this.plugin.getRecipeManager();
        this.enchantManager = this.plugin.getEnchantManager();
    }

    @EventHandler
    public void onCrafterCraft(CrafterCraftEvent event) {
        final var crafter = event.getBlock();
        if(crafter.getState() instanceof Crafter crafterState) {
            var crafterInv = crafterState.getInventory();
            var crafterContents = crafterInv.getStorageContents();
            var recipe = event.getRecipe();
            var validCraft = checkRecipeCustomItems(crafterContents, recipe);
            event.setCancelled(!validCraft);
        }
        else {
            // ...  crafter.getState() wasn't a Crafter state?
            // just gonna cancel the event to be safe
            event.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onCraft(CraftItemEvent event) {
        // Shouldn't even fire if PrepareItemCraftEvent fired properly,
        // but I'm checking again in here just in case.
        var contents = event.getInventory().getStorageContents();
        var recipe = event.getRecipe();
        var valid = checkRecipeCustomItems(Arrays.copyOfRange(contents, 1, 10), recipe);
        event.setCancelled(!valid);
    }

    @EventHandler(ignoreCancelled = true)
    public void onPrepareCraft(PrepareItemCraftEvent event) {
        var contents = event.getInventory().getStorageContents();
        var recipe = event.getRecipe();
        var valid = checkRecipeCustomItems(Arrays.copyOfRange(contents, 1, 10), recipe);
        if(!valid) {
            event.getInventory().setResult(null);
        }
    }

    /**
     * Checks the custom items in a recipe. If there are custom items where they aren't allowed, returns false.
     * Otherwise, returns true.
     * @return Whether the recipe is valid/okay.
     */
    private boolean checkRecipeCustomItems(ItemStack[] contents, Recipe recipe) {
        final List<Integer> customItemIndices = new ArrayList<>();
        for (int i = 0; i < contents.length; i++) {
            var stack = contents[i];
            if(stack == null) {
                continue;
            }

            if(CustomItemManager.isCustomItem(stack)) {
                customItemIndices.add(i);
            }

            // ... Depending on SandPortal's implementation, this might be relevant?
            // Mostly for stopping people from making sandstone blocks with them.
            boolean hasCustomEnchantments = stack
                    .getEnchantments().keySet().stream().anyMatch(enchantManager::isTaggedHoloEnchantment);
            if(hasCustomEnchantments) {
                // There might be a use for this that isn't just "Recipe is automatically invalid"
                // but for now I'm leaving it like this.
                return false;
            }
        }

        if(customItemIndices.isEmpty()) {
            // no custom items in recipe so automatically valid
            // at least, by this check
            return true;
        }

        var registeredRecipe = recipeManager.getRegisteredRecipe(recipe);
        if(registeredRecipe == null) {
            // recipe is not registered but there's custom items
            return false;
        }

        /*
        The code below was made because I assumed when an event returns a Recipe, it returns the original recipe
        THIS IS NOT THE CASE. The RecipeChoices I was getting were exclusively ExactChoice and MaterialChoice, even
        if one of them was originally a CustomItemRecipeChoice.

        RecipeManager was made after the code below was made - but I've left it here, so that we can still use
        MaterialChoice in other CustomItems. Ex: use Material.TINTED_GLASS for lunarlaser without having to explicitly
        state "and NOT the Reading Glasses holoitem"

        As a result, this is tested code, but I didn't get to thoroughly test it. I've only left it here instead of
        replacing it with "return true;" because I think this will be useful in the future, I just don't know when.
         */
        if(registeredRecipe instanceof ShapedRecipe shapedRecipe) {
            // needed so that if a 2x2 recipe is in the bottom-right
            // it gets "moved" to the top-left
            int minRow = 2;
            int minCol = 2;

            for (int i = 0; i < contents.length; i++) {
                var stack = contents[i];
                if(stack.isEmpty()) {
                    int row = i/3;
                    int col = i%3;
                    minRow = Math.min(row, minRow);
                    minCol = Math.min(col, minCol);
                }
            }

            for(Integer customItemIndex : customItemIndices) {
                int row = (customItemIndex/3) - minRow;
                int col = (customItemIndex%3) - minCol;
                Character choiceChar = shapedRecipe.getShape()[row].charAt(col);
                var choice = shapedRecipe.getChoiceMap().get(choiceChar);
                if(!(choice instanceof CustomItemRecipeChoice)) {
                    // this is a non-custom-item slot with a custom item in it
                    return false;
                }
            }
            // all slots passed
            return true;
        }
        else {
            // TODO: Implement ShapelessRecipe checks.
            // Recipe is not an instance of CraftingRecipe
            // uh, maybe it's a furnace recipe (i know emerald leaf or whatever gnaw requires is a furnace recipe)
            // either way, point is it's not implemented
            plugin.getLogger().warning("CraftListener.checkRecipeCustomItems called with unknown recipe type");
            plugin.getLogger().warning("Recipe class: " + recipe.getClass().toGenericString());
            plugin.getLogger().warning("Recipe toString value: " + recipe);
            return false;
        }
    }
}
