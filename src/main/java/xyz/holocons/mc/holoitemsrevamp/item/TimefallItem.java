package xyz.holocons.mc.holoitemsrevamp.item;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.jetbrains.annotations.NotNull;

import com.strangeone101.holoitemsapi.enchantment.EnchantManager;
import com.strangeone101.holoitemsapi.enchantment.Enchantable;
import com.strangeone101.holoitemsapi.item.CustomItem;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import xyz.holocons.mc.holoitemsrevamp.HoloItemsRevamp;

public class TimefallItem extends CustomItem implements Enchantable {

    private final static String name = "timefall";
    private final static Material material = Material.POWDER_SNOW_BUCKET;
    private final static Component displayName = Component.text("Timefall", NamedTextColor.BLUE);

    private final static List<Component> lore = List.of(
        Component.text("Accelerate oxidation")
    );

    private final EnchantManager enchantManager;

    public TimefallItem(HoloItemsRevamp plugin) {
        super(plugin, name, material, displayName, lore);
        this.enchantManager = plugin.getEnchantManager();
        this.setStackable(false);
        this.register();
    }

    @Override
    protected Recipe getRecipe() {
        final var recipe = new ShapedRecipe(getKey(), buildStack(null));
        recipe.shape(
            "ABA",
            "BCB",
            "ABA"
        );
        recipe.setIngredient('A', Material.SNOW);
        recipe.setIngredient('B', Material.BONE_BLOCK);
        recipe.setIngredient('C', Material.BUCKET);
        return recipe;
    }

    @Override
    public @NotNull Enchantment getEnchantment() {
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
