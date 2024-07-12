package xyz.holocons.mc.holoitemsrevamp.enchantment;

import java.util.Map;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import com.strangeone101.holoitemsapi.enchantment.CustomEnchantment;
import com.strangeone101.holoitemsapi.enchantment.EnchantmentAbility;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import xyz.holocons.mc.holoitemsrevamp.HoloItemsRevamp;

public class GemKnife extends CustomEnchantment implements EnchantmentAbility {

    private static final Map<Material, ItemStack> COMPATIBLE_MATERIALS = Map.ofEntries(
            Map.entry(Material.COAL_ORE, new ItemStack(Material.COAL_BLOCK, 64)),
            Map.entry(Material.DEEPSLATE_COAL_ORE, new ItemStack(Material.COAL_BLOCK, 64)),
            Map.entry(Material.IRON_ORE, new ItemStack(Material.IRON_BLOCK, 64)),
            Map.entry(Material.DEEPSLATE_IRON_ORE, new ItemStack(Material.IRON_BLOCK, 64)),
            Map.entry(Material.GOLD_ORE, new ItemStack(Material.GOLD_BLOCK, 64)),
            Map.entry(Material.DEEPSLATE_GOLD_ORE, new ItemStack(Material.GOLD_BLOCK, 64)),
            Map.entry(Material.NETHER_GOLD_ORE, new ItemStack(Material.GOLD_BLOCK, 64)),
            Map.entry(Material.GILDED_BLACKSTONE, new ItemStack(Material.GOLD_BLOCK, 64)),
            Map.entry(Material.REDSTONE_ORE, new ItemStack(Material.REDSTONE_BLOCK, 64)),
            Map.entry(Material.DEEPSLATE_REDSTONE_ORE, new ItemStack(Material.REDSTONE_BLOCK, 64)),
            Map.entry(Material.LAPIS_ORE, new ItemStack(Material.LAPIS_BLOCK, 64)),
            Map.entry(Material.DEEPSLATE_LAPIS_ORE, new ItemStack(Material.LAPIS_BLOCK, 64)),
            Map.entry(Material.NETHER_QUARTZ_ORE, new ItemStack(Material.QUARTZ_BLOCK, 64)),
            Map.entry(Material.GLOWSTONE, new ItemStack(Material.GLOWSTONE, 64)));

    public GemKnife(HoloItemsRevamp plugin) {
        super(plugin, "gem_knife");
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
    public boolean canEnchantItem(@NotNull ItemStack item) {
        return item.getType() == Material.EMERALD;
    }

    @Override
    public @NotNull Component displayName(int level) {
        return Component.text("Gem Knife", NamedTextColor.GRAY)
                .decoration(TextDecoration.ITALIC, false);
    }

    @Override
    public int getCostMultiplier() {
        // TODO: should this be infinity instead?
        return 12;
    }

    @Override
    public void onPlayerInteract(PlayerInteractEvent event, ItemStack itemStack) {
        final var clickedBlock = event.getClickedBlock();
        if (clickedBlock == null) {
            return;
        }
        final var itemStackToDrop = COMPATIBLE_MATERIALS.get(clickedBlock.getType());
        if (itemStackToDrop == null) {
            return;
        }
        if (Battery.expendFuel(event.getPlayer(), 1, GemKnife::isFuel)) {
            final var location = event.getInteractionPoint();
            location.getWorld().dropItemNaturally(location, itemStackToDrop);
        }
    }

    private static boolean isFuel(final ItemStack itemStack) {
        return itemStack.getType() == Material.NETHERITE_INGOT;
    }
}
