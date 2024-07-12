package xyz.holocons.mc.holoitemsrevamp.item;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.block.ShulkerBox;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.meta.BlockStateMeta;

import com.strangeone101.holoitemsapi.enchantment.CustomEnchantment;
import com.strangeone101.holoitemsapi.enchantment.EnchantManager;
import com.strangeone101.holoitemsapi.enchantment.Enchantable;
import com.strangeone101.holoitemsapi.item.BlockAbility;
import com.strangeone101.holoitemsapi.item.CustomItem;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import xyz.holocons.mc.holoitemsrevamp.HoloItemsRevamp;
import xyz.holocons.mc.holoitemsrevamp.enchantment.Battery;

public class BatteryBlock extends CustomItem implements Enchantable, BlockAbility {

    private static final String name = "battery";
    private static final Material material = Material.SHULKER_BOX;
    private static final Component displayName = Component.text("Battery", NamedTextColor.RED);
    private static final List<Component> lore = List.of(
            Component.text("Holds fuel", NamedTextColor.DARK_PURPLE));

    private final EnchantManager enchantManager;

    public BatteryBlock(HoloItemsRevamp plugin) {
        super(plugin, name, material, displayName, lore);
        this.enchantManager = plugin.getEnchantManager();
        this.register();
    }

    @Override
    protected Recipe getRecipe() {
        // TODO: Add a recipe.
        // Maybe the recipe should use a shulker-box in its recipe since this is
        // a custom-item and NOT an enchantment book.
        return null;
    }

    @Override
    public Enchantment getEnchantment() {
        return CustomEnchantment.getByKey(getKey());
    }

    @Override
    public ItemStack applyEnchantment(ItemStack itemStack) {
        var enchantedStack = itemStack.clone();
        var enchantedMeta = enchantedStack.hasItemMeta() ? enchantedStack.getItemMeta()
                : Bukkit.getItemFactory().getItemMeta(enchantedStack.getType());

        if (enchantedMeta.addEnchant(getEnchantment(), 1, false)) {
            enchantedStack.setItemMeta(enchantedMeta);
            enchantManager.removeCustomEnchantmentLore(enchantedStack);
            enchantManager.applyCustomEnchantmentLore(enchantedStack);
            return enchantedStack;
        } else {
            return null;
        }
    }

    @Override
    public void onBlockDropItem(BlockDropItemEvent event, BlockState blockState) {
        final var contents = getShulkerBoxContents(blockState);
        BlockAbility.super.onBlockDropItem(event, blockState);
        final var items = event.getItems();
        final var iterator = items.listIterator(items.size());
        while (iterator.hasPrevious()) {
            final var item = iterator.previous();
            final var itemStack = item.getItemStack();
            for (final var enchantment : itemStack.getEnchantments().keySet()) {
                if (enchantment instanceof Battery) {
                    setShulkerBoxContents(itemStack, contents);
                    return;
                }
            }
        }
    }

    private static ItemStack[] getShulkerBoxContents(final BlockState blockState) {
        if (blockState instanceof ShulkerBox shulkerBox) {
            return shulkerBox.getInventory().getContents();
        } else {
            return null;
        }
    }

    private static void setShulkerBoxContents(final ItemStack itemStack, final ItemStack[] contents) {
        if (itemStack.getItemMeta() instanceof BlockStateMeta blockStateMeta
                && blockStateMeta.getBlockState() instanceof ShulkerBox shulkerBox) {
            shulkerBox.getInventory().setContents(contents);
            blockStateMeta.setBlockState(shulkerBox);
            itemStack.setItemMeta(blockStateMeta);
        }
    }
}
