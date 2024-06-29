package xyz.holocons.mc.holoitemsrevamp;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;

import com.destroystokyo.paper.profile.ProfileProperty;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.util.Ticks;

public final class Util {

    private static final ThreadLocalRandom RANDOM = ThreadLocalRandom.current();
    private static final UUID SKULL_OWNER = new UUID(0, 0);

    private static long epochTick = 0;
    private static long previousCurrentTick = Long.MAX_VALUE;

    private Util() {
    }

    /**
     * Convenience method to call the deprecated {@code UnsafeValues#nextEntityId()}.
     *
     * @return The next EntityId available
     */
    @SuppressWarnings("deprecation")
    public static int nextEntityId() {
        return Bukkit.getUnsafe().nextEntityId();
    }

    /**
     * Calling {@code UUID#randomUUID()} uses {@code SecureRandom} to get a cryptographically
     * secure random UUID, but for our use cases, we don't need it to be
     * cryptographically secure. We can generate our UUIDs a little more cheaply using
     * {@code ThreadLocalRandom} instead. Should not be called from any async threads.
     *
     * @return A pseudo randomly generated UUID
     */
    public static UUID randomUUID() {
        return new UUID(RANDOM.nextLong(), RANDOM.nextLong());
    }

    /**
     * Returns a player head with the base64 texture. Mostly used for GUI.
     *
     * @param base64 A base 64 string that contains ONLY the texture
     * @return The ItemStack player head
     */
    public static ItemStack getPlayerHeadFromSkinUrl(String url) {
        final var item = new ItemStack(Material.PLAYER_HEAD);
        final var meta = (SkullMeta) item.getItemMeta();
        final var profile = Bukkit.createProfile(SKULL_OWNER);
        final var json = "{\"textures\":{\"SKIN\":{\"url\":\"" + url + "\"}}}";
        final var base64 = Base64.getUrlEncoder().encodeToString(json.getBytes(StandardCharsets.UTF_8));
        profile.setProperty(new ProfileProperty("textures", base64));
        meta.setPlayerProfile(profile);
        item.setItemMeta(meta);
        return item;
    }

    /**
     * Calling {@code System#currentTimeMillis()} performs IO which might be expensive
     * if done several times per game tick. On the other hand,
     * {@code Bukkit#getCurrentTick()} is cheap but returns a relative current time since
     * it begins counting from 0 when the server starts. Instead, we'll use the system
     * time as an epoch and add the current tick to it to efficiently get an absolute
     * current time.
     *
     * @return The current time represented in terms of game ticks, assuming 20 TPS
     */
    public static long currentTimeTicks() {
        final var currentTick = Integer.toUnsignedLong(Bukkit.getCurrentTick());
        if (currentTick < Util.previousCurrentTick) {
            Util.epochTick = System.currentTimeMillis() / Ticks.SINGLE_TICK_DURATION_MS - currentTick;
        }
        Util.previousCurrentTick = currentTick;
        return Util.epochTick + currentTick;
    }

    /**
     * Returns the ore rarity of a tool material.
     *
     * @param material The material to check
     * @return An ingot material that corresponds to the provided material, or air if there is none.
     */
    @NotNull
    public static Material getOreLevel(Material material) {
        return switch (material) {
            case NETHERITE_AXE, NETHERITE_BOOTS, NETHERITE_HELMET, NETHERITE_HOE, NETHERITE_CHESTPLATE,
                NETHERITE_LEGGINGS, NETHERITE_INGOT, NETHERITE_PICKAXE, NETHERITE_SHOVEL, NETHERITE_SWORD
                -> Material.NETHERITE_INGOT;
            case DIAMOND_CHESTPLATE, DIAMOND_HELMET, DIAMOND_BOOTS, DIAMOND_LEGGINGS, DIAMOND_HORSE_ARMOR, DIAMOND_HOE,
                DIAMOND_AXE, DIAMOND_PICKAXE, DIAMOND_SHOVEL, DIAMOND_SWORD
                -> Material.DIAMOND;
            case GOLDEN_CHESTPLATE, GOLDEN_HELMET, GOLDEN_BOOTS, GOLDEN_LEGGINGS, GOLDEN_HORSE_ARMOR, GOLDEN_HOE,
                GOLDEN_AXE, GOLDEN_PICKAXE, GOLDEN_SHOVEL, GOLDEN_SWORD
                -> Material.GOLD_INGOT;
            case IRON_CHESTPLATE, IRON_HELMET, IRON_BOOTS, IRON_LEGGINGS, IRON_HORSE_ARMOR, IRON_HOE, IRON_AXE,
                IRON_PICKAXE, IRON_SHOVEL, IRON_SWORD
                -> Material.IRON_INGOT;
            case WOODEN_HOE, WOODEN_AXE, WOODEN_PICKAXE, WOODEN_SHOVEL, WOODEN_SWORD
                -> Material.OAK_PLANKS;
            case LEATHER_CHESTPLATE, LEATHER_HELMET, LEATHER_BOOTS, LEATHER_LEGGINGS
                -> Material.LEATHER;
            default -> Material.AIR;
        };
    }

    /**
     * Returns the roman numeral equivalent of a number. This is only useful for numbers 1 through 10.
     * Mainly used for enchantments.
     *
     * @param number A number from 1 through 10
     * @return A TranslatableComponent, or empty Component if it is outside the available range.
     */
    public static Component toRoman(int number) {
        return (number > 0 && number <= 10)
            ? Component.translatable("enchantment.level." + Integer.toString(number)) : Component.empty();
    }

    public static void healEntity(final LivingEntity entity, double amountToHeal) {
        final var maxHealth = entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
        var health = entity.getHealth();

        if(health + amountToHeal > maxHealth) {
            entity.setHealth(maxHealth);
        }
        else {
            entity.setHealth(health + amountToHeal);
        }
    }
}
