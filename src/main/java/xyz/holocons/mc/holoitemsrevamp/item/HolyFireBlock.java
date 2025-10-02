package xyz.holocons.mc.holoitemsrevamp.item;

import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;

import com.strangeone101.holoitemsapi.item.CustomItemManager;
import org.bukkit.Material;
import org.bukkit.block.Beacon;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.type.Campfire;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.util.NumberConversions;

import com.strangeone101.holoitemsapi.item.BlockAbility;
import com.strangeone101.holoitemsapi.item.CustomItem;
import com.strangeone101.holoitemsapi.recipe.CustomItemRecipeChoice;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import xyz.holocons.mc.holoitemsrevamp.HoloItemsRevamp;
import xyz.holocons.mc.holoitemsrevamp.Util;
import xyz.holocons.mc.holoitemsrevamp.util.BlockStateExpiringSet;
import xyz.holocons.mc.holoitemsrevamp.util.ExpiringSet;

public class HolyFireBlock extends CustomItem implements BlockAbility {

    private static final String name = "holy_fire";
    private static final Material material = Material.SOUL_CAMPFIRE;
    private static final Component displayName = Component.text("Holy Fire", NamedTextColor.DARK_PURPLE);
    private static final List<Component> lore = List.of(
            Component.text("Prevent natural mob spawning within 100 blocks when lit", NamedTextColor.AQUA)
                    .decoration(TextDecoration.ITALIC, false));

    private static final BlockStateExpiringSet holyFireMarker = new BlockStateExpiringSet(
            new HolyFireExpirationPolicy());

    public HolyFireBlock(HoloItemsRevamp plugin) {
        super(plugin, name, material, displayName, lore);
        register();
    }

    @Override
    protected Recipe getRecipe() {
        final var recipeKey = getKey();
        final var outStack = buildStack(null);
        final var recipe = new ShapedRecipe(recipeKey, outStack);

        recipe.shape(
                " E ",
                "EQE",
                "GGG"
        );
        recipe.setIngredient('E', Material.END_ROD);
        recipe.setIngredient('G', Material.RAW_GOLD_BLOCK);
        recipe.setIngredient('Q', new CustomItemRecipeChoice("saint_quartz"));

        return recipe;
    }

    @Override
    public void onBlockBreak(BlockBreakEvent event, BlockState blockState) {
        holyFireMarker.remove(blockState);
    }

    @Override
    public void onBlockIgnite(BlockIgniteEvent event, BlockState blockState) {
        holyFireMarker.add(blockState);
    }

    @Override
    public void onBlockPlace(BlockPlaceEvent event, BlockState blockState) {
        isActive(blockState);
    }

    @Override
    public void onCreatureSpawn(CreatureSpawnEvent event, BlockState blockState) {
        if (event.isCancelled() || event.getSpawnReason() != CreatureSpawnEvent.SpawnReason.NATURAL) {
            return;
        }

        final var campfireLocation = blockState.getLocation();
        final var spawnLocation = event.getLocation();
        final var distanceSquared = campfireLocation.distanceSquared(spawnLocation);
        final var rangeSquared = getRangeSquared(blockState);
        if (distanceSquared > rangeSquared || !isActive(blockState)) {
            return;
        }

        event.setCancelled(true);
    }

    @Override
    public void onPlayerInteract(PlayerInteractEvent event, BlockState blockState) {
        if (blockState.getBlockData() instanceof Campfire campfire && !campfire.isLit()) {
            holyFireMarker.remove(blockState);
        }
    }

    /**
     * Checks whether this HolyFire is active.
     * Also updates the block's lit state.
     *
     * @implNote Since this is only called when a Natural-Spawn is attempted, if you
     *           spawn-proof the affected area before activating this, the campfire
     *           won't extinguish.
     */
    private static boolean isActive(final BlockState blockState) {
        if (!(blockState.getBlockData() instanceof Campfire campfire)) {
            return false;
        }

        final var isActive = holyFireMarker.test(blockState);
        if (!isActive && campfire.isLit()) {
            campfire.setLit(false);
            blockState.setBlockData(campfire);
            blockState.update();
        }
        return isActive;
    }

    private static double getRangeSquared(final BlockState blockState) {
        final var boostLevel = getBoostLevel(blockState);
        return NumberConversions.square(80 + 12 * boostLevel);
    }

    /**
     * Gets the "boost level" based on the block below.
     *
     * @param blockState Presumed to be a valid and active HolyFire
     */
    private static int getBoostLevel(final BlockState blockState) {
        final var blockBelow = blockState.getBlock().getRelative(BlockFace.DOWN);
        return blockBelow.getState() instanceof Beacon beacon ? beacon.getTier() + 1 : 0;
    }

    private static class HolyFireExpirationPolicy implements ExpiringSet.ExpirationPolicy<BlockState> {

        @Override
        public long expirationTime(final BlockState blockState) {
            final var boostLevel = getBoostLevel(blockState);
            return now() + Util.toTicks(20 + 8 * boostLevel, ChronoUnit.MINUTES);
        }
    }
}
