package xyz.holocons.mc.holoitemsrevamp.enchantment;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Item;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import com.strangeone101.holoitemsapi.enchantment.EnchantmentAbility;

import xyz.holocons.mc.holoitemsrevamp.HoloItemsRevamp;
import xyz.holocons.mc.holoitemsrevamp.integration.Integrations;

public class Magnet implements EnchantmentAbility {

    private final HoloItemsRevamp plugin;

    public Magnet(HoloItemsRevamp plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onBlockBreak(BlockBreakEvent event, ItemStack itemStack) {
        final var location = event.getBlock().getLocation().toCenterLocation();
        final var player = event.getPlayer();

        if (!Integrations.WORLDGUARD.canUseEnchantment(location, Magnet.class)) {
            return;
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                final var items = location.getNearbyEntitiesByType(Item.class, 1.5, Item::canPlayerPickup);
                final var itemStacks = items.stream().map(Item::getItemStack).toArray(ItemStack[]::new);
                final var excess = player.getInventory().addItem(itemStacks);
                items.forEach(player::playPickupItemAnimation);
                items.forEach(Item::remove);
                excess.values()
                        .forEach(itemStack -> player.getWorld().dropItemNaturally(player.getLocation(), itemStack));
            }
        }.runTask(plugin);
    }
}
