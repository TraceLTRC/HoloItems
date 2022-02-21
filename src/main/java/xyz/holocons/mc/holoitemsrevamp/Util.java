package xyz.holocons.mc.holoitemsrevamp;

import com.destroystokyo.paper.profile.ProfileProperty;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class Util {

    private static final ThreadLocalRandom RANDOM = ThreadLocalRandom.current();
    private static final UUID SKULL_OWNER = new UUID(0, 0);

    private static long epochTick = -1;
    private static int previousCurrentTick = Integer.MAX_VALUE;

    private static final String[] NUMERALS = { "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X" };

    /**
     * Convenience method to call the deprecated {@code UnsafeValues#nextEntityId()}.
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
     * {@code ThreadLocalRandom} instead.
     * @return A pseudo randomly generated UUID
     */
    public static UUID randomUUID() {
        return new UUID(RANDOM.nextLong(), RANDOM.nextLong());
    }

    /**
     * Returns a player head with the base64 texture. Mostly used for GUI.
     * @param base64 A base 64 string that contains ONLY the texture
     * @return The ItemStack player head
     */
    public static ItemStack playerHeadFromBase64(String base64) {
        final var item = new ItemStack(Material.PLAYER_HEAD);
        final var meta = (SkullMeta) item.getItemMeta();
        final var profile = Bukkit.createProfile(SKULL_OWNER);
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
     * @return The current time represented in terms of game ticks, assuming 20 TPS
     */
    public static long currentTimeTicks() {
        final var currentTick = Bukkit.getCurrentTick();
        if (currentTick < Util.previousCurrentTick) {
            Util.epochTick = System.currentTimeMillis() / 1000 * 20;
        }
        Util.previousCurrentTick = currentTick;
        return Util.epochTick + currentTick;
    }

    /**
     * Returns the ore rarity of a tool material.
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
     * Returns the roman numeral equivalent of a number. This function only works for numbers 1 through 10.
     * Mainly used for enchantments.
     * @param number A number from 1 through 10
     * @return A string with the equivalent roman numerals, or null if it is outside the avaliable range.
     */
    public static String toRoman(int number) {
        return (number > 0 && number <= NUMERALS.length) ? NUMERALS[number] : null;
    }
}
