package xyz.holocons.mc.holoitemsrevamp.enchantment;

import com.strangeone101.holoitemsapi.enchantment.CustomEnchantment;
import com.strangeone101.holoitemsapi.enchantment.EnchantmentAbility;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import xyz.holocons.mc.holoitemsrevamp.HoloItemsRevamp;

public class VerificationSeal extends CustomEnchantment implements EnchantmentAbility {

    private final HoloItemsRevamp plugin;

    public VerificationSeal(HoloItemsRevamp plugin) {
        super(plugin, "verification_seal");
        this.plugin = plugin;
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
        return itemStack.getType() == Material.FILLED_MAP;
    }

    @Override
    public @NotNull Component displayName(int level) {
        return Component.text()
            .color(NamedTextColor.GRAY)
            .decoration(TextDecoration.ITALIC, false)
            .append(Component.text("Verification Seal"))
            .build();
    }

    @Override
    public int getCostMultiplier() {
        return 12;
    }

    @Override
    public void onInventoryClicked(InventoryClickEvent event, ItemStack itemStack) {
        if(event instanceof CraftItemEvent || event.getInventory().getType() == InventoryType.CARTOGRAPHY) {
            event.setCancelled(true);
        }
        else {
            final var clickedInventory = event.getClickedInventory();
            if(clickedInventory != null && clickedInventory.getType() == InventoryType.CARTOGRAPHY) {
                event.setCancelled(true);
            }
        }
    }
}
