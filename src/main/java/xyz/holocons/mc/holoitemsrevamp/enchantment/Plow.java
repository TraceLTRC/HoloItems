package xyz.holocons.mc.holoitemsrevamp.enchantment;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import com.strangeone101.holoitemsapi.enchantment.EnchantmentAbility;

import xyz.holocons.mc.holoitemsrevamp.HoloItemsRevamp;
import xyz.holocons.mc.holoitemsrevamp.util.EntityExpiringSet;
import xyz.holocons.mc.holoitemsrevamp.util.ExpiringSet;

public class Plow implements EnchantmentAbility {

    private final HoloItemsRevamp plugin;

    private final EntityExpiringSet plowMarker = new EntityExpiringSet(
            new ExpiringSet.ConstantTicksToLiveExpirationPolicy<>(20));

    public Plow(HoloItemsRevamp plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onBlockBreak(BlockBreakEvent event, ItemStack itemStack) {
        final var player = event.getPlayer();
        if (event.getBlock().getType() == Material.SNOW) {
            plowMarker.add(player);
        } else {
            event.setCancelled(plowMarker.test(player));
        }
    }
}
