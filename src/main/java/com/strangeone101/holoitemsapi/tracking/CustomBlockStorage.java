package com.strangeone101.holoitemsapi.tracking;

import com.strangeone101.holoitemsapi.item.BlockAbility;

import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

import org.bukkit.block.Block;
import xyz.holocons.mc.holoitemsrevamp.HoloItemsRevamp;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.stream.Stream;

/**
 * Shoutout to Flo0 (a.k.a 7Smiley7 on Spigot) for sharing the block tracking
 * concept which initially guided the design of our own system.
 *
 * @see <a href=
 *      "https://www.spigotmc.org/threads/tracking-blocks-that-were-placed-by-players.500216/">Resource
 *      thread</a>
 */
public class CustomBlockStorage {

    public static final String FILENAME = "blocks.json";

    private final Logger logger;
    private final File dataFolder;
    private final Object2ObjectOpenHashMap<BlockLocation, BlockAbility> trackedBlocks;
    private final LongOpenHashSet touchedChunks;

    public CustomBlockStorage(HoloItemsRevamp plugin) {
        this.logger = plugin.getLogger();
        this.dataFolder = plugin.getDataFolder();
        this.trackedBlocks = new Object2ObjectOpenHashMap<>();
        this.touchedChunks = new LongOpenHashSet();
    }

    public void loadTrackedBlocks() {
        final var file = new File(dataFolder, FILENAME);
        if (!file.exists()) {
            return;
        }

        if (!trackedBlocks.isEmpty()) {
            throw new IllegalStateException("Block tracker already has data!");
        }

        final Object2ObjectOpenHashMap<BlockLocation, BlockAbility> invalidBlocks = new Object2ObjectOpenHashMap<>();

        try (final var reader = new GsonReader(file)) {
            reader.readBlocks(trackedBlocks, invalidBlocks);
        } catch (IOException e) {
            final var invalidBlocksFolder = new File(dataFolder, "invalid-blocks");
            invalidBlocksFolder.mkdir();

            final var invalidBlocksFile = new File(invalidBlocksFolder, Instant.now().toString() + ".json");
            file.renameTo(invalidBlocksFile);
            throw new RuntimeException(e);
        }

        if (!invalidBlocks.isEmpty()) {
            logger.warning("Invalid blocks will be discarded!");

            final var invalidBlocksFolder = new File(dataFolder, "invalid-blocks");
            invalidBlocksFolder.mkdir();

            final var invalidBlocksFile = new File(invalidBlocksFolder, Instant.now().toString() + ".json");

            try (final var writer = new GsonWriter(invalidBlocksFile)) {
                writer.writeBlocks(invalidBlocks);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        trackedBlocks.keySet().stream().mapToLong(BlockLocation::chunkKey).forEach(touchedChunks::add);
    }

    public void saveTrackedBlocks() {
        dataFolder.mkdir();

        final var file = new File(dataFolder, FILENAME);

        try (final var writer = new GsonWriter(file)) {
            writer.writeBlocks(trackedBlocks);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void track(final Block block, final BlockAbility ability) {
        final var trackedBlock = new BlockLocation(block);
        trackedBlocks.put(trackedBlock, ability);
        touchedChunks.add(trackedBlock.chunkKey());
    }

    public BlockAbility untrack(final Block block) {
        return trackedBlocks.remove(new BlockLocation(block));
    }

    public Stream<Map.Entry<BlockLocation, BlockAbility>> getTrackedBlocks(final UUID worldKey, final long chunkKey) {
        return touchedChunks.contains(chunkKey)
                ? trackedBlocks.entrySet().stream().filter(
                        entry -> entry.getKey().chunkKey() == chunkKey && entry.getKey().worldKey().equals(worldKey))
                : Stream.empty();
    }

    public Stream<Map.Entry<BlockLocation, BlockAbility>> getTrackedBlocks(final UUID worldKey) {
        return trackedBlocks.entrySet().stream()
            .filter(entry -> entry.getKey().isLoaded())
            .filter(entry -> entry.getKey().worldKey().equals(worldKey));
    }

    public BlockAbility getBlockAbility(final Block block) {
        return trackedBlocks.get(new BlockLocation(block));
    }

    public boolean contains(final Block block) {
        return trackedBlocks.containsKey(new BlockLocation(block));
    }
}
