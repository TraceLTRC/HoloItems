package xyz.holocons.mc.holoitemsrevamp.item;

import com.strangeone101.holoitemsapi.enchantment.CustomEnchantment;
import com.strangeone101.holoitemsapi.enchantment.EnchantManager;
import com.strangeone101.holoitemsapi.enchantment.Enchantable;
import com.strangeone101.holoitemsapi.item.CustomItem;
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

public class SSKSwordBook extends CustomItem implements Enchantable {

    private static final String name = "ssk_sword";
    private static final Material material = Material.ENCHANTED_BOOK;
    private static final Component displayName = Component.text("SSK Sword", NamedTextColor.RED);
    private static final List<Component> lore = List.of(
        Component.text("Heal-on-hit when at full health!", NamedTextColor.DARK_PURPLE)
    );

    private final EnchantManager enchantManager;

    public SSKSwordBook(HoloItemsRevamp plugin) {
        super(plugin, name, material, displayName, lore);
        this.enchantManager = plugin.getEnchantManager();
        this.register();
    }

    @Override
    protected Recipe getRecipe() {
        // Recipe 0 from OldHoloItems
        ShapedRecipe recipe = new ShapedRecipe(getKey(), buildStack(null));
        recipe.shape("*&#","*&#","*/#");
        recipe.setIngredient('*', Material.BLAZE_POWDER);
        recipe.setIngredient('&', Material.GHAST_TEAR);
        recipe.setIngredient('#', Material.SUGAR);
        recipe.setIngredient('/', Material.NETHER_STAR);
        return recipe;
    }

    @Override
    public Enchantment getEnchantment() {
        return CustomEnchantment.getByKey(getKey());
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
