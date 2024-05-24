package xyz.holocons.mc.holoitemsrevamp.item;

import com.strangeone101.holoitemsapi.enchantment.CustomEnchantment;
import com.strangeone101.holoitemsapi.enchantment.EnchantManager;
import com.strangeone101.holoitemsapi.item.CustomItem;
import com.strangeone101.holoitemsapi.enchantment.Enchantable;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import xyz.holocons.mc.holoitemsrevamp.HoloItemsRevamp;

import java.util.List;

public class BackdashBoots extends CustomItem implements Enchantable {

    private final static String name = "backdash";
    private final static Material material = Material.LEATHER_BOOTS;
    private final static Component displayName = Component.text("Backdash", NamedTextColor.DARK_GRAY);
    private final static List<Component> lore = List.of(
        Component.text("Crouch to backdash")
    );

    private final EnchantManager enchantManager;

    public BackdashBoots(HoloItemsRevamp plugin) {
        super(plugin, name, material, displayName, lore);
        this.enchantManager = plugin.getEnchantManager();
        this.setStackable(false);
        this.register();
    }

    @Override
    protected Recipe getRecipe() {
        final var recipe = new ShapedRecipe(getKey(), buildStack(null));
        recipe.shape(
            "   ",
            "A A",
            "B B"
        );
        recipe.setIngredient('A', Material.PISTON);
        recipe.setIngredient('B', Material.PHANTOM_MEMBRANE);
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
