package com.strangeone101.holoitemsapi.enchantment;

import net.kyori.adventure.text.Component;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import xyz.holocons.mc.holoitemsrevamp.HoloItemsRevamp;
import xyz.holocons.mc.holoitemsrevamp.enchantment.*;
import xyz.holocons.mc.holoitemsrevamp.integration.Integrations;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class EnchantManager {

    private final List<String> enchantmentNames;
    private final Map<Map.Entry<Enchantment, Integer>, Component> enchantmentLores;

    public EnchantManager(HoloItemsRevamp plugin) {
        try {
            final var field = Enchantment.class.getDeclaredField("acceptingNew");
            field.setAccessible(true);
            field.set(null, true);
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        final var enchantments = buildCustomEnchantments(plugin);

        enchantments.forEach(CustomEnchantment::registerEnchantment);
        enchantments.forEach(Integrations.WORLDGUARD::registerEnchantment);

        this.enchantmentNames = enchantments.stream().map(CustomEnchantment::name).toList();
        // Key is a pair of Enchantment and level, value is the lore
        this.enchantmentLores = enchantments
                .stream().<Map.Entry<Map.Entry<Enchantment, Integer>, Component>>mapMulti(
                        (customEnchantment, consumer) -> IntStream
                                .rangeClosed(customEnchantment.getStartLevel(), customEnchantment.getMaxLevel())
                                .forEach(level -> {
                                    final var lore = customEnchantment.lore(level);
                                    if (lore != null) {
                                        consumer.accept(Map.entry(Map.entry(customEnchantment, level), lore));
                                    }
                                }))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        Enchantment.stopAcceptingRegistrations();
    }

    public List<String> enchantmentNames() {
        return enchantmentNames;
    }

    /**
     * If the item stack is an enchanted book, it will get the enchantments using
     * {@link EnchantmentStorageMeta#getStoredEnchants()}
     *
     * @param itemStack An ItemStack that has enchantments
     * @return A map of the enchantments
     */
    public static Map<Enchantment, Integer> getEnchantments(ItemStack itemStack) {
        return itemStack.getItemMeta() instanceof EnchantmentStorageMeta enchantmentStorageMeta
                ? enchantmentStorageMeta.getStoredEnchants()
                : itemStack.getEnchantments();
    }

    /**
     * Applies the display names of all custom enchantments present on an itemstack
     * to the itemstack as lore. If any custom enchantment lore is already on the
     * given itemstack, {@code EnchantManager#removeCustomEnchantmentLore} should be
     * done first.
     *
     * @param itemStack An ItemStack that has custom enchantments
     */
    public void applyCustomEnchantmentLore(ItemStack itemStack) {
        final var enchantmentLore = getEnchantments(itemStack).entrySet().stream()
                .map(enchantmentLores::get)
                .filter(Objects::nonNull);

        final var oldLore = itemStack.lore();
        final var newLore = oldLore == null
                ? enchantmentLore.toList()
                : Stream.concat(enchantmentLore, oldLore.stream()).toList();
        itemStack.lore(newLore.isEmpty() ? null : newLore);
    }

    /**
     * Removes all custom enchantment lore from the given itemstack.
     *
     * @param itemStack An ItemStack without custom enchantments
     */
    public void removeCustomEnchantmentLore(ItemStack itemStack) {
        final var oldLore = itemStack.lore();
        if (oldLore != null) {
            final var newLore = oldLore.stream()
                    .filter(loreComponent -> !enchantmentLores.containsValue(loreComponent))
                    .toList();
            itemStack.lore(newLore.isEmpty() ? null : newLore);
        }
    }

    private static Set<CustomEnchantment> buildCustomEnchantments(HoloItemsRevamp plugin) {
        return Set.of(
                new Magnet(plugin),
                new Memento(plugin),
                new TideRider(plugin),
                new Backdash(plugin),
                new DemonAura(plugin),
                new Plow(plugin)
        );
    }
}
