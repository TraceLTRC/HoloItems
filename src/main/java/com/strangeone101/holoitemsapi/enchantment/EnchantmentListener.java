package com.strangeone101.holoitemsapi.enchantment;

import java.util.function.Consumer;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.ThrowableProjectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import xyz.holocons.mc.holoitemsrevamp.HoloItemsRevamp;

public class EnchantmentListener implements Listener {

    private HoloItemsRevamp plugin;
    private EnchantManager manager;

    public EnchantmentListener(HoloItemsRevamp plugin) {
        this.plugin = plugin;
        this.manager = this.plugin.getEnchantManager();
    }

    private void forEachEnchantment(final ItemStack itemStack,
            final Consumer<? super EnchantmentAbility> action) {
        itemStack.getEnchantments().keySet().forEach(enchantment -> {
            NamespacedKey enchKey = enchantment.getKey();
            var ench = manager.getByKey(enchKey);
            if(ench != null) {
                action.accept(ench);
            }
//            if (enchantment instanceof EnchantmentAbility ability) {
//                action.accept(ability);
//            }
        });
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {
        final var itemStack = event.getPlayer().getInventory().getItemInMainHand();
        forEachEnchantment(itemStack, ability -> ability.onBlockBreak(event, itemStack));
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockPlace(BlockPlaceEvent event) {
        final var itemStack = event.getItemInHand();
        forEachEnchantment(itemStack, ability -> ability.onBlockPlace(event, itemStack));
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerDeath(PlayerDeathEvent event) {
        final var storageContents = event.getPlayer().getInventory().getStorageContents();
        for (final var itemStack : storageContents) {
            if (itemStack == null) {
                continue;
            }
            forEachEnchantment(itemStack, ability -> ability.onPlayerDeath(event, itemStack));
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onProjectileLaunch(ProjectileLaunchEvent event) {
        if (!(event.getEntity() instanceof ThrowableProjectile throwableProjectile)) {
            return;
        }
        final var itemStack = throwableProjectile.getItem();
        forEachEnchantment(itemStack, ability -> ability.onProjectileLaunch(event, itemStack));
    }

    @EventHandler(ignoreCancelled = false)
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.isBlockInHand() || !event.getAction().isRightClick() || !event.hasItem()) {
            return;
        }
        final var itemStack = switch (event.getHand()) {
            case HAND -> event.getPlayer().getInventory().getItemInMainHand();
            case OFF_HAND -> event.getPlayer().getInventory().getItemInOffHand();
            default -> new ItemStack(Material.AIR);
        };
        forEachEnchantment(itemStack, ability -> ability.onPlayerInteract(event, itemStack));
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerToggleSneak(PlayerToggleSneakEvent event) {
        final var armorContents = event.getPlayer().getInventory().getArmorContents();
        for (final var itemStack : armorContents) {
            if (itemStack == null) {
                continue;
            }
            forEachEnchantment(itemStack, ability -> ability.onPlayerToggleSneak(event, itemStack));
        }
    }
}
