package xyz.holocons.mc.holoitemsrevamp.item;

import com.strangeone101.holoitemsapi.enchantment.Enchantable;
import com.strangeone101.holoitemsapi.item.CustomItem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import xyz.holocons.mc.holoitemsrevamp.HoloItemsRevamp;

import java.util.List;

public class TideRiderItem extends CustomItem implements Enchantable {

    private static final String name = "tide_rider";
    private static final Material material = Material.TRIDENT;
    private static final Component displayName = Component.text("Tide Rider", NamedTextColor.BLUE);
    private static final List<Component> lore = List.of(
        Component.text("Surf the waves")
    );

    public TideRiderItem(HoloItemsRevamp plugin) {
        super(plugin, name, material, displayName, lore);
        this.register();
    }

    @Override
    protected Recipe getRecipe() {
        final var recipe = new ShapedRecipe(getKey(), buildStack(null));
        recipe.shape(
                "ATA",
                "AG ",
                " A "
        );
        recipe.setIngredient('A', Material.PRISMARINE_BRICKS);
        recipe.setIngredient('T', Material.TRIDENT);
        recipe.setIngredient('G', Material.ENCHANTED_GOLDEN_APPLE);
        return recipe;
    }

    @Override
    public NamespacedKey getEnchantmentKey() {
        return getKey();
    }

    @Override
    public ItemStack applyEnchantment(ItemStack itemStack) {
        var enchantedStack = itemStack.clone();
        var enchantedMeta = enchantedStack.hasItemMeta() ? enchantedStack.getItemMeta() : Bukkit.getItemFactory().getItemMeta(enchantedStack.getType());

        if (enchantedMeta.addEnchant(getEnchantment(), 1, false)) {
            enchantedStack.setItemMeta(enchantedMeta);
            return enchantedStack;
        } else {
            return null;
        }
    }
}
