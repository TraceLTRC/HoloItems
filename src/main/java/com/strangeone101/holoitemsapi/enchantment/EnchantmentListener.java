package com.strangeone101.holoitemsapi.enchantment;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.ThrowableProjectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;

public class EnchantmentListener implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {
        final var itemStack = event.getPlayer().getInventory().getItemInMainHand();

        itemStack.getEnchantments().keySet().forEach(enchantment -> {
            if (enchantment instanceof EnchantmentAbility ability) {
                ability.onBlockBreak(event, itemStack);
            }
        });
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockPlace(BlockPlaceEvent event) {
        final var itemStack = event.getItemInHand();

        itemStack.getEnchantments().keySet().forEach(enchantment -> {
            if (enchantment instanceof EnchantmentAbility ability) {
                ability.onBlockPlace(event, itemStack);
            }
        });
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerDeath(PlayerDeathEvent event) {
        final var storageContents = event.getPlayer().getInventory().getStorageContents();

        for (final var itemStack : storageContents) {
            if (itemStack == null) {
                continue;
            }
            itemStack.getEnchantments().keySet().forEach(enchantment -> {
                if (enchantment instanceof EnchantmentAbility ability) {
                    ability.onPlayerDeath(event, itemStack);
                }
            });
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onProjectileLaunch(ProjectileLaunchEvent event) {
        if (!(event.getEntity() instanceof ThrowableProjectile throwableProjectile)) {
            return;
        }
        final var itemStack = throwableProjectile.getItem();

        itemStack.getEnchantments().keySet().forEach(enchantment -> {
            if (enchantment instanceof EnchantmentAbility ability) {
                ability.onProjectileLaunch(event, itemStack);
            }
        });
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

        itemStack.getEnchantments().keySet().forEach(enchantment -> {
            if (enchantment instanceof EnchantmentAbility ability) {
                ability.onPlayerInteract(event, itemStack);
            }
        });
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerToggleSneak(PlayerToggleSneakEvent event) {
        final var armorContents = event.getPlayer().getInventory().getArmorContents();

        for (final var itemStack : armorContents) {
            if (itemStack == null) {
                continue;
            }
            itemStack.getEnchantments().keySet().forEach(enchantment -> {
                if (enchantment instanceof EnchantmentAbility ability) {
                    ability.onPlayerToggleSneak(event, itemStack);
                }
            });
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerAttack(EntityDamageByEntityEvent event) {
        final var attacker = event.getDamager();
        final var target = event.getEntity();

        if(!(attacker instanceof Player attackingPlayer)) {
            return;
        }

        // Now that I think about it, can you not attack enemies with your offhand weapon?
        final var weapon = attackingPlayer.getInventory().getItemInMainHand();
        weapon.getEnchantments().keySet().forEach(enchantment -> {
            if(enchantment instanceof EnchantmentAbility ability) {
                ability.onPlayerAttack(event, weapon);
            }
        });
    }
}
