package com.strangeone101.holoitemsapi.enchantment;

import org.bukkit.Keyed;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;

public interface EnchantmentAbility extends Keyed {

    default void onBlockBreak(BlockBreakEvent event, ItemStack itemStack) {
    }

    default void onBlockPlace(BlockPlaceEvent event, ItemStack itemStack) {
    }

    default void onPlayerDeath(PlayerDeathEvent event, ItemStack itemStack) {
    }

    default void onProjectileLaunch(ProjectileLaunchEvent event, ItemStack itemStack) {
    }

    default void onPlayerInteract(PlayerInteractEvent event, ItemStack itemStack) {
    }    

    default void onPlayerToggleSneak(PlayerToggleSneakEvent event, ItemStack itemStack) {
    }

    default void onPlayerExpChange(PlayerExpChangeEvent event, ItemStack itemStack) {
    }
}
