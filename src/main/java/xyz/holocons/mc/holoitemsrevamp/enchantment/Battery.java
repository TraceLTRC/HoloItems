package xyz.holocons.mc.holoitemsrevamp.enchantment;

import java.util.function.Predicate;

import org.bukkit.block.ShulkerBox;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.jetbrains.annotations.NotNull;

import com.destroystokyo.paper.MaterialSetTag;
import com.strangeone101.holoitemsapi.enchantment.CustomEnchantment;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import xyz.holocons.mc.holoitemsrevamp.HoloItemsRevamp;

public class Battery extends CustomEnchantment {

    public Battery(HoloItemsRevamp plugin) {
        super(plugin, "battery");
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public boolean conflictsWith(@NotNull Enchantment enchantment) {
        return false;
    }

    @Override
    public boolean canEnchantItem(@NotNull ItemStack itemStack) {
        return MaterialSetTag.SHULKER_BOXES.isTagged(itemStack.getType());
    }

    @Override
    public @NotNull Component displayName(int i) {
        return Component.text()
                .color(NamedTextColor.GRAY)
                .decoration(TextDecoration.ITALIC, false)
                .append(Component.text("Battery"))
                .build();
    }

    @Override
    public int getCostMultiplier() {
        return 39;
    }

    public static boolean expendFuel(final Player player, final int amount, final Predicate<ItemStack> isFuel) {
        for (var index = 0; index < 9; index++) {
            final var hotbarItemStack = player.getInventory().getItem(index);
            if (isBattery(hotbarItemStack) && amount > 0
                    && hotbarItemStack.getItemMeta() instanceof BlockStateMeta blockStateMeta
                    && blockStateMeta.getBlockState() instanceof ShulkerBox shulkerBox) {
                final var fuelList = new ObjectArrayList<ItemStack>();
                var remainingAmount = amount;
                for (final var inventoryItemStack : shulkerBox.getInventory()) {
                    if (inventoryItemStack != null && isFuel.test(inventoryItemStack)) {
                        remainingAmount -= inventoryItemStack.getAmount();
                        if (remainingAmount > 0) {
                            fuelList.add(inventoryItemStack);
                        } else {
                            inventoryItemStack.setAmount(-remainingAmount);
                            fuelList.forEach(fuelItemStack -> fuelItemStack.setAmount(0));
                            blockStateMeta.setBlockState(shulkerBox);
                            hotbarItemStack.setItemMeta(blockStateMeta);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private static boolean isBattery(final ItemStack itemStack) {
        if (itemStack == null) {
            return false;
        }
        for (final var enchantment : itemStack.getEnchantments().keySet()) {
            if (enchantment instanceof Battery) {
                return true;
            }
        }
        return false;
    }
}
