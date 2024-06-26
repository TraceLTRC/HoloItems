package com.strangeone101.holoitemsapi.tracking;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;

public record BlockLocation(UUID worldKey, int x, int y, int z) {

    public BlockLocation(UUID worldKey, long chunkKey, int blockKey) {
        this(
                worldKey,
                (int) chunkKey << 4 | blockKey >> 16 & 0xF,
                blockKey & 0xFFFF,
                (int) (chunkKey >> 32) << 4 | blockKey >> 24 & 0xF);
    }

    public BlockLocation(Block block) {
        this(block.getWorld().getUID(), block.getX(), block.getY(), block.getZ());
    }

    public BlockLocation(BlockState blockState) {
        this(blockState.getWorld().getUID(), blockState.getX(), blockState.getY(), blockState.getZ());
    }

    public World world() {
        return Bukkit.getWorld(worldKey);
    }

    public long chunkKey() {
        return (long) x >> 4 & 0xFFFFFFFFL | ((long) z >> 4 & 0xFFFFFFFFL) << 32;
    }

    public Chunk chunk() {
        return world().getChunkAt(x >> 4, z >> 4);
    }

    public int blockKey() {
        return y & 0xFFFF | (x & 0xF) << 16 | (z & 0xF) << 24;
    }

    public Block block() {
        return world().getBlockAt(x, y, z);
    }

    public BlockState blockState() {
        return world().getBlockState(x, y, z);
    }

    public boolean isLoaded() {
        return world() != null && world().isChunkLoaded(x >> 4, z >> 4);
    }

    @Override
    public int hashCode() {
        return worldKey.hashCode() ^ ((y + z * 31) * 31 + x);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof BlockLocation other
                && this.x == other.x && this.y == other.y && this.z == other.z
                && this.worldKey.equals(other.worldKey);
    }
}
