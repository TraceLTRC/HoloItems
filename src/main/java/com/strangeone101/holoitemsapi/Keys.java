package com.strangeone101.holoitemsapi;

import org.bukkit.Bukkit;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class Keys {

    public static Property<Long> COOLDOWN;
    public static Property<String> ITEM_ID;
    /**
     * Used to represent an item that can also be used "like" an enchantment book.
     * The first example of this is Backdash, which is both boots and applicable to other boots.
     */
    public static Property<Boolean> BOOK_LIKE;

    public static void fillKeys(Plugin plugin) {
        COOLDOWN = new Property<Long>(plugin, "cooldown") {

            @Override
            public boolean has(PersistentDataContainer data) {
                return data.has(getKey(), PersistentDataType.LONG);
            }

            @Override
            public Long get(PersistentDataContainer data) {
                return data.getOrDefault(getKey(), PersistentDataType.LONG, 0L);
            }

            @Override
            public void set(PersistentDataContainer data, Long value) {
                data.set(getKey(), PersistentDataType.LONG, value);
            }

            @Override
            public String getPropertyName() {
                return "Cooldown";
            }
        };

        ITEM_ID = new Property<String>(plugin, "item_id") {

            @Override
            public boolean has(PersistentDataContainer data) {
                return data.has(getKey(), PersistentDataType.STRING);
            }

            @Override
            public String get(PersistentDataContainer data) {
                return data.get(getKey(), PersistentDataType.STRING);
            }

            @Override
            public void set(PersistentDataContainer data, String value) {
                data.set(getKey(), PersistentDataType.STRING, value);
            }

            @Override
            public String getPropertyName() {
                return "Item ID";
            }
        };

        BOOK_LIKE = new Property<Boolean>(plugin, "book_like") {
            @Override
            public boolean has(PersistentDataContainer data) {
                return data.has(getKey(), PersistentDataType.BOOLEAN);
            }

            @Override
            public Boolean get(PersistentDataContainer data) {
                return data.getOrDefault(getKey(), PersistentDataType.BOOLEAN, false);
            }

            @Override
            public void set(PersistentDataContainer data, Boolean value) {
                if (value == null || !value) {
                    data.remove(getKey());
                } else {
                    data.set(getKey(), PersistentDataType.BOOLEAN, true);
                }
            }

            @Override
            public String getPropertyName() {
                return "Book-like";
            }
        };
    }
}
