package xyz.holocons.mc.holoitemsrevamp;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.temporal.TemporalUnit;
import java.util.Base64;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import com.destroystokyo.paper.profile.ProfileProperty;

import io.papermc.paper.util.Tick;
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
     * Convenience method to call the deprecated
     * {@code UnsafeValues#nextEntityId()}.
     *
     * @return The next EntityId available
     */
    @SuppressWarnings("deprecation")
    public static int nextEntityId() {
        return Bukkit.getUnsafe().nextEntityId();
    }

    /**
     * Calling {@code UUID#randomUUID()} uses {@code SecureRandom} to get a
     * cryptographically secure random UUID, but for our use cases, we don't need it
     * to be cryptographically secure. We can generate our UUIDs a little more
     * cheaply using {@code ThreadLocalRandom} instead. Should not be called from
     * any async threads.
     *
     * @return A pseudo randomly generated UUID
     */
    public static UUID randomUUID() {
        return new UUID(RANDOM.nextLong(), RANDOM.nextLong());
    }

    /**
     * Returns a player head with the base64 texture. Mostly used for GUI.
     *
     * @param url A url from textures.minecraft.net that represents the skin texture, as stored on Mojang servers.
     *            <a href="https://minecraft.wiki/w/Mojang_API#Query_player's_skin_and_cape">Read more here.</a>
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
     * Calling {@code System#currentTimeMillis()} performs IO which might be
     * expensive if done several times per game tick. On the other hand,
     * {@code Bukkit#getCurrentTick()} is cheap but returns a relative current time
     * since it begins counting from 0 when the server starts. Instead, we'll use
     * the system time as an epoch and add the current tick to it to efficiently get
     * an absolute current time.
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
     * Returns the roman numeral equivalent of a number. This is only useful for
     * numbers 1 through 10. Mainly used for enchantments.
     *
     * @param number A number from 1 through 10
     * @return A TranslatableComponent, or empty Component if it is outside the
     *         available range.
     */
    public static Component toRoman(int number) {
        return (number > 0 && number <= 10)
                ? Component.translatable("enchantment.level." + Integer.toString(number))
                : Component.empty();
    }

    public static long toTicks(long amount, TemporalUnit unit) {
        return toTicks(Duration.of(amount, unit));
    }

    public static long toTicks(Duration duration) {
        return Tick.tick().fromDuration(duration);
    }
}
