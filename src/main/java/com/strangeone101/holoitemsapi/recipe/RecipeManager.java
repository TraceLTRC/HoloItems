package com.strangeone101.holoitemsapi.recipe;

import org.bukkit.Bukkit;
import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.Recipe;
import xyz.holocons.mc.holoitemsrevamp.HoloItemsRevamp;

import java.util.HashMap;
import java.util.Map;

public class RecipeManager {

    private final HoloItemsRevamp plugin;
    private final Map<NamespacedKey, Recipe> recipeMap = new HashMap<>();

    public RecipeManager(HoloItemsRevamp plugin) {
        this.plugin = plugin;
    }

    /**
     * Registers a recipe in this RecipeManager. Doing this also registers the recipe using Bukkit::addRecipe
     */
    public void registerRecipe(Recipe recipe) {
        if(recipe == null) {
            return;
        }
        if(recipe instanceof Keyed keyed) {
            var key = keyed.getKey();
            recipeMap.put(key, recipe);
            Bukkit.addRecipe(recipe);
        }
        else {
            // This is possible for a MerchantRecipe.
            throw new IllegalArgumentException(
                    "Can't register a recipe that doesn't have a NamespacedKey. Recipe:" + recipe);
        }
    }

    /**
     * Gets a recipe that's been registered in this RecipeManager
     */
    public Recipe getRegisteredRecipe(NamespacedKey key) {
        return this.recipeMap.get(key);
    }

    /**
     * Gets a recipe that's been registered in this RecipeManager. This difference between the recipe returned
     * by this and the parameter recipe is that the original recipe may have lost its CustomItemRecipeChoice values.
     */
    public Recipe getRegisteredRecipe(Recipe recipe) {
        if(recipe instanceof Keyed key) {
            return getRegisteredRecipe(key.getKey());
        }
        else {
            return null;
        }
    }
}
