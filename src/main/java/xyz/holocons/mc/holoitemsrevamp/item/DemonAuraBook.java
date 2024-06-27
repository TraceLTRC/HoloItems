package xyz.holocons.mc.holoitemsrevamp.item;

import com.strangeone101.holoitemsapi.enchantment.EnchantManager;
import com.strangeone101.holoitemsapi.enchantment.Enchantable;
import com.strangeone101.holoitemsapi.item.CustomItem;
import com.strangeone101.holoitemsapi.recipe.RecipeManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import xyz.holocons.mc.holoitemsrevamp.HoloItemsRevamp;

import java.util.List;

public class DemonAuraBook extends CustomItem implements Enchantable {

    private static final String name = "demon_aura";
    private static final Material material = Material.ENCHANTED_BOOK;
    private static final Component displayName = Component.text("Demon Aura", NamedTextColor.RED);
    private static final List<Component> lore = List.of(
        Component.text("Heal from EXP gained!", NamedTextColor.DARK_PURPLE)
    );

    private final EnchantManager enchantManager;

    public DemonAuraBook(HoloItemsRevamp plugin) {
        super(plugin, name, material, displayName, lore);
        this.enchantManager = plugin.getEnchantManager();
        this.register();
    }

    @Override
    protected Recipe getRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(getKey(), buildStack(null));
        recipe.shape(
            "aaa",
            "aba",
            "aaa"
        );
        recipe.setIngredient('a', Material.CANDLE);
        recipe.setIngredient('b', Material.IRON_HORSE_ARMOR);
        return recipe;
    }

    @Override
    public Enchantment getEnchantment() {
        return Enchantment.getByKey(getKey());
    }

    @Override
    public ItemStack applyEnchantment(ItemStack itemStack) {
        var enchantedStack = itemStack.clone();
        var enchantedMeta = (EnchantmentStorageMeta) enchantedStack.getItemMeta();

        if (enchantedMeta.addStoredEnchant(getEnchantment(), 1, false)) {
            enchantedStack.setItemMeta(enchantedMeta);
            enchantManager.removeCustomEnchantmentLore(enchantedStack);
            enchantManager.applyCustomEnchantmentLore(enchantedStack);
            return enchantedStack;
        } else {
            return null;
        }
    }
}
