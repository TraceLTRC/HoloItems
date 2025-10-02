package xyz.holocons.mc.holoitemsrevamp.enchantment;

import org.bukkit.NamespacedKey;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import com.strangeone101.holoitemsapi.enchantment.EnchantmentAbility;

import xyz.holocons.mc.holoitemsrevamp.HoloItemsRevamp;
import xyz.holocons.mc.holoitemsrevamp.integration.Integrations;

public class Backdash implements EnchantmentAbility {

    private final HoloItemsRevamp plugin;

    public Backdash(HoloItemsRevamp plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onPlayerToggleSneak(PlayerToggleSneakEvent event, ItemStack itemStack) {
        if (!event.isSneaking()
                || !Integrations.WORLDGUARD.canUseEnchantment(event.getPlayer().getLocation(), Backdash.class)) {
            return;
        }
        final var player = event.getPlayer();
        player.setVelocity(player.getLocation().getDirection().setY(0).normalize().multiply(-1));
    }
}
