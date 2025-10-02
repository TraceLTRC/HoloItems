package xyz.holocons.mc.holoitemsrevamp.enchantment;

import org.bukkit.NamespacedKey;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import com.strangeone101.holoitemsapi.enchantment.EnchantmentAbility;

import xyz.holocons.mc.holoitemsrevamp.HoloItemsRevamp;
import xyz.holocons.mc.holoitemsrevamp.integration.Integrations;

public class Memento implements EnchantmentAbility {

    private final HoloItemsRevamp plugin;

    public Memento(HoloItemsRevamp plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onPlayerDeath(PlayerDeathEvent event, ItemStack itemStack) {
        final var location = event.getPlayer().getLocation();

        // Don't do anything if keepInv is already on for this event
        if (event.getKeepInventory() || !Integrations.WORLDGUARD.canUseEnchantment(location, Memento.class)) {
            return;
        }

        // Set keepInv and keepExp on for this event
        event.setKeepInventory(true);
        event.setKeepLevel(true);

        // Clear corpse items to prevent dupes
        event.setShouldDropExperience(false);
        event.getDrops().clear();

        // Remove a Memento
        itemStack.subtract();
    }

    @Override
    public void onBlockPlace(BlockPlaceEvent event, ItemStack itemStack) {
        event.setCancelled(true);
    }
}
