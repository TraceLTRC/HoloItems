package xyz.holocons.mc.holoitemsrevamp.item;

import com.strangeone101.holoitemsapi.enchantment.Enchantable;
import com.strangeone101.holoitemsapi.item.CustomItem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import xyz.holocons.mc.holoitemsrevamp.HoloItemsRevamp;

import java.util.List;

public class MagnetBook extends CustomItem implements Enchantable {

    private static final String name = "magnet";
    private static final Material material = Material.ENCHANTED_BOOK;
    private static final Component displayName = Component.text("Magnet", NamedTextColor.RED);
    private static final List<Component> lore = List.of(
        Component.text("Automatically put mined items to your inventory!", NamedTextColor.DARK_PURPLE)
    );

    public MagnetBook(HoloItemsRevamp plugin) {
        super(plugin, name, material, displayName, lore);
        this.register();
    }

    @Override
    protected Recipe getRecipe() {
        final var recipe = new ShapedRecipe(getKey(), buildStack(null));
        recipe.shape(
                "AAB",
                "CDE",
                "FGD"
        );
        recipe.setIngredient('A', Material.POWERED_RAIL);
        recipe.setIngredient('B', Material.IRON_PICKAXE);
        recipe.setIngredient('C', Material.HOPPER);
        recipe.setIngredient('D', Material.IRON_BLOCK);
        recipe.setIngredient('E', Material.REDSTONE);
        recipe.setIngredient('F', Material.DROPPER);
        recipe.setIngredient('G', Material.COMPARATOR);
        return recipe;
    }

    @Override
    public NamespacedKey getEnchantmentKey() {
        return getKey();
    }

    @Override
    public ItemStack applyEnchantment(ItemStack itemStack) {
        var enchantedStack = itemStack.clone();
        var enchantedMeta = (EnchantmentStorageMeta) enchantedStack.getItemMeta();

        if (enchantedMeta.addStoredEnchant(getEnchantment(), 1, false)) {
            enchantedStack.setItemMeta(enchantedMeta);
            return enchantedStack;
        } else {
            return null;
        }
    }
}
