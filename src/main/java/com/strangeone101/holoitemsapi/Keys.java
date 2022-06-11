package com.strangeone101.holoitemsapi;

import org.bukkit.NamespacedKey;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.UUID;

public class Keys {

    private static HashMap<String, NamespacedKey> keyMap;

    public static Property<UUID> OWNER;
    public static Property<String> OWNER_NAME;
    public static Property<Long> COOLDOWN;
    public static Property<Boolean> UNSTACKABLE;
    public static Property<Integer> RENAMABLE;
    public static Property<String> ITEM_ID;
    public static Property<Integer> DURABILITY;

    public static void fillKeys(HashMap<String, NamespacedKey> keys) {
        if (keyMap != null) throw new RuntimeException("Keys are already supplied!");
        keyMap = keys;

        OWNER = new Properties.Owner(getKey("owner"));
        OWNER_NAME = new Properties.OwnerName(getKey("owner_name"));
        COOLDOWN = new Properties.Cooldown(getKey("cooldown"));
        UNSTACKABLE = new Properties.Unstackable(getKey("unstackable"));
        RENAMABLE = new Properties.Renamable(getKey("renamable"));
        ITEM_ID = new Properties.ItemId(getKey("item_id"));
        DURABILITY = new Properties.Durability(getKey("durability"));
    }

    public static @Nullable NamespacedKey getKey(String key) {
        return keyMap.get(key);
    }

    public static void addKey(String id, NamespacedKey key) {
        if (keyMap == null) {
            keyMap = new HashMap<>();
        }

        if (keyMap.containsKey(id) || keyMap.containsValue(key))
            return;

        keyMap.put(id, key);
    }
}