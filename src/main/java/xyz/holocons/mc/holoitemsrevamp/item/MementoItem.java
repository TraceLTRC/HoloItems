package xyz.holocons.mc.holoitemsrevamp.item;

import xyz.holocons.mc.holoitemsrevamp.HoloItemsRevamp;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

import com.strangeone101.holoitemsapi.enchantment.CustomEnchantment;
import com.strangeone101.holoitemsapi.enchantment.EnchantManager;
import com.strangeone101.holoitemsapi.enchantment.Enchantable;
import com.strangeone101.holoitemsapi.item.CustomItem;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import java.util.List;

public class MementoItem extends CustomItem implements Enchantable {

    private static final String name = "memento";
    private static final Material material = Material.ENDER_CHEST;
    private static final Component displayName = Component.text("Memento", NamedTextColor.DARK_PURPLE);
    private static final List<Component> lore = List.of(
        Component.text("Keep your items on death. Consumable.", NamedTextColor.DARK_PURPLE)
    );

    private final EnchantManager enchantManager;

    public MementoItem(HoloItemsRevamp plugin) {
        super(plugin, name, material, displayName, lore);
        this.enchantManager = plugin.getEnchantManager();
        this.register();
    }

    @Override
    protected Recipe getRecipe() {
        final var recipe = new ShapedRecipe(getKey(), buildStack(null));
        recipe.shape(
            "AAA",
            "ABA",
            "AAA"
        );
        recipe.setIngredient('A', Material.ENDER_CHEST);
        recipe.setIngredient('B', Material.END_CRYSTAL);
        return recipe;
    }

    @Override
    public Enchantment getEnchantment() {
        return CustomEnchantment.getByKey(getKey());
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
