package xyz.holocons.mc.holoitemsrevamp.item;

import com.strangeone101.holoitemsapi.enchantment.EnchantManager;
import com.strangeone101.holoitemsapi.item.CustomItem;
import com.strangeone101.holoitemsapi.enchantment.Enchantable;
import com.strangeone101.holoitemsapi.recipe.RecipeManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import xyz.holocons.mc.holoitemsrevamp.HoloItemsRevamp;

import java.util.List;

public class TimeLapseItem extends CustomItem implements Enchantable {

    private final static String name = "time_lapse";
    private final static Material material = Material.CLOCK;
    private final static Component displayName = Component.text("Time Lapse", NamedTextColor.GOLD);
    private final static List<Component> lore = List.of(
        Component.text("Unsmelt Ingots")
    );

    private final EnchantManager enchantManager;

    public TimeLapseItem(HoloItemsRevamp plugin) {
        super(plugin, name, material, displayName, lore);
        this.enchantManager = plugin.getEnchantManager();
        this.setStackable(false);
        this.register();
        this.registerRecipe();
    }

    private void registerRecipe() {
        final var recipe = new ShapedRecipe(getKey(), buildStack(null));
        recipe.shape(
            "ABA",
            "BCB",
            "ABA"
        );
        recipe.setIngredient('A', Material.CLOCK);
        recipe.setIngredient('B', Material.COPPER_BLOCK);
        recipe.setIngredient('C', Material.BLAST_FURNACE);
        RecipeManager.registerRecipe(recipe);
    }

    @Override
    public Enchantment getEnchantment() {
        return Enchantment.getByKey(getKey());
    }

    @Override
    public ItemStack applyEnchantment(ItemStack itemStack) {
        var enchantedStack = itemStack.clone();
        var enchantedMeta = enchantedStack.hasItemMeta() ? enchantedStack.getItemMeta() : Bukkit.getItemFactory().getItemMeta(enchantedStack.getType());

        if (enchantedMeta.addEnchant(getEnchantment(), 1, false)) {
            enchantedStack.setItemMeta(enchantedMeta);
            enchantManager.removeCustomEnchantmentLore(enchantedStack);
            enchantManager.applyCustomEnchantmentLore(enchantedStack);
            return enchantedStack;
        } else {
            return null;
        }
    }
}