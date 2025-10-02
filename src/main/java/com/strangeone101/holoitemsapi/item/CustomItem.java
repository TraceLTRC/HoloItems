package com.strangeone101.holoitemsapi.item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import org.bukkit.Color;
import org.bukkit.Keyed;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.plugin.Plugin;
import org.checkerframework.checker.nullness.qual.NonNull;

import com.strangeone101.holoitemsapi.Keys;
import com.strangeone101.holoitemsapi.Property;
import com.strangeone101.holoitemsapi.enchantment.Enchantable;
import com.strangeone101.holoitemsapi.statistic.StatsWrapper;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.format.NamedTextColor;
import org.jetbrains.annotations.Nullable;
import xyz.holocons.mc.holoitemsrevamp.Util;

/**
 * A class for creating custom items. Be sure to call {@link #register()} after creating it
 * to properly register it
 */
public class CustomItem implements Keyed {

    private NamespacedKey key;

    private int customModelID;
    private Material material;
    private Component displayName;
    private List<Component> lore;
    private int cooldown = 0;
    private Integer stackSize = null;
    private Set<Property<?>> properties = new HashSet<>();
    private Set<StatsWrapper<?>> statGoals;
    private int hex;
    private ItemFlag[] flags;
    private boolean bookLike;

    private Map<String, Function<PersistentDataContainer, Component>> variables = new HashMap<>();

    public CustomItem(Plugin plugin, String key, Material material, Component displayName, List<Component> lore) {
        this.key = new NamespacedKey(plugin, key);
        this.material = material;
        this.displayName = displayName != null ? displayName : Component.empty();
        this.lore = lore != null ? lore : List.of();
    }

    @Override
    public final NamespacedKey getKey() {
        return key;
    }

    /**
     * Gets the internal name of this custom item
     * @return The internal name
     */
    public final String getInternalName() {
        return getKey().getKey();
    }

    /**
     * Create a new ItemStack for use. NOT for updating existing ones; see updateStack
     * @param player The player
     * @return The ItemStack
     */
    public ItemStack buildStack(Player player) {
        ItemStack stack = new ItemStack(getMaterial());
        ItemMeta meta = stack.getItemMeta();

        // It's important to use the functions `getDisplayName()` and `getLore()` below
        // instead of the field names in case an object overrides them
        meta.displayName(replaceVariables(getDisplayName(), meta.getPersistentDataContainer()));

        if (meta instanceof LeatherArmorMeta) {
            ((LeatherArmorMeta) meta).setColor(Color.fromRGB(hex));
        }

        List<Component> loreList = new ArrayList<>();

        for (var line : getLore()) {
            loreList.add(replaceVariables(line, meta.getPersistentDataContainer()));
        }

        meta.lore(loreList);

        if (customModelID != 0) meta.setCustomModelData(customModelID); //Used for resource packs

        if (properties.contains(Keys.COOLDOWN)) {
            Keys.COOLDOWN.set(meta.getPersistentDataContainer(), 0L);
        }

        Keys.ITEM_ID.set(meta.getPersistentDataContainer(), getInternalName());
        Keys.BOOK_LIKE.set(meta.getPersistentDataContainer(), this.getBookLike());

        // If the item shouldn't be stackable, add a random INTEGER to the NBT
        if(this.getStackSize() != null){
            meta.setMaxStackSize(this.getStackSize());
        }

        if (flags != null && flags.length > 0) meta.addItemFlags(flags);

        stack.setItemMeta(meta);

        if (this instanceof Enchantable enchantable) {
            stack = enchantable.applyEnchantment(stack);
        }

        return stack;
    }

    /**
     * Builds an ItemStack that should only be used for showing an item through an inventory or any other methods
     * that does not allow the player to use the item. This will add missing statistics to the lore.
     * @return An ItemStack with extra information.
     */
    public ItemStack buildGuiStack(OfflinePlayer player) {
        var itemStack = buildStack(null);
        var itemMeta = itemStack.getItemMeta();

        var lore = itemMeta.lore();

        var remainingStats = inspectStatGoals(player);
        if (remainingStats.isEmpty()) {
            return itemStack;
        }

        for (var statistic : remainingStats.keySet()) {
            //lore.add(ChatColor.BLUE + statistic.toString() + ChatColor.RESET + " | " + ChatColor.RED + remainingStats.get(statistic));
            lore.add(
                Component.text(statistic.toString(), NamedTextColor.BLUE)
                    .append(Component.text(" | "))
                    .append(Component.text(remainingStats.get(statistic), NamedTextColor.RED))
            );
        }

        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    /**
     * Called by CustomItemManager on every CustomItem.
     * If non-null, the recipe is added to the server.
     * @return A recipe to add.
     */
    protected @Nullable Recipe getRecipe() {
        return null;
    }

    /**
     * Replaces the string provided with variables
     * @param component The component
     * @param dataHolder The data holder
     * @return The replaced string
     */
    public Component replaceVariables(Component component, PersistentDataContainer dataHolder) {
        var result = component;

        for (var entry : variables.entrySet()) {
            result = result.replaceText(
                TextReplacementConfig.builder().matchLiteral(entry.getKey())
                    .replacement(entry.getValue().apply(dataHolder))
                    .build()
            );
        }

        return result;
    }

    public void addVariable(String variable, Function<PersistentDataContainer, Component> function) {
        variables.put(variable, function);
    }

    /**
     * Get the custom display name
     * @return The display name
     */
    public Component getDisplayName() {
        return displayName;
    }

    /**
     * Get the material
     * @return The material
     */
    public Material getMaterial() {
        return material;
    }

    /**
     * Gets the cooldown of the item
     * @return The cooldown in ticks
     */
    public int getCooldown() {
        return cooldown;
    }

    /**
     * Sets the cooldown of the item
     * @param cooldown The cooldown of the item in ticks
     */
    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }

    /**
     * Applies a cooldown to an itemstack by storing the current time
     * @param item The itemStack
     * @return The itemStack with the cooldown.
     */
    public ItemStack applyCooldown(ItemStack item) {
        var currentTick = Util.currentTimeTicks();
        var meta = item.getItemMeta();
        Keys.COOLDOWN.set(meta.getPersistentDataContainer(), currentTick);
        item.setItemMeta(meta);
        return item;
    }

    /**
     * Checks whether an item is on cooldown
     * @param item The item
     * @return True if it's on cooldown. False otherwise.
     */
    public boolean checkCooldown(ItemStack item) {
        var currentTick = Util.currentTimeTicks();
        var meta = item.getItemMeta();
        var previousTick = Keys.COOLDOWN.get(meta.getPersistentDataContainer());
        return currentTick - previousTick < getCooldown();
    }

    /**
     * Gets all the stat goals needed to unlock/use the item.
     * @return A set of StatsWrappers
     */
    public Set<StatsWrapper<?>> getStatGoals() {
        return statGoals;
    }

    /**
     * Sets the stat goals needed to unlock/use the item
     * @param statGoals A set of StatsWrappers
     */
    public void setStatGoals(Set<StatsWrapper<?>> statGoals) {
        this.statGoals = statGoals;
    }

    /**
     * Checks if a player passes all the stat goals
     * @param player The player to check
     * @return True if there is no stat goals, or the player passes all stat goals. False otherwise.
     */
    public boolean checkStatGoals(Player player) {
        if (getStatGoals() == null)
            return true;
        return getStatGoals().stream().allMatch(stat -> stat.checkPlayer(player));
    }

    /**
     * Returns a map that contains what stat goals the player hasn't reached. The map will not contain stat goals
     * that the player has passed.
     * @param player The player to check against.
     * @return A map, with the key being the statistic, and the value being the remaining goal.
     */
    @NonNull
    public Map<Statistic, Integer> inspectStatGoals(OfflinePlayer player) {
        Map<Statistic, Integer> remainingStats = new HashMap<>();
        if (getStatGoals() == null || getStatGoals().isEmpty())
            return remainingStats;

        getStatGoals().stream().filter(stat -> !stat.checkPlayer(player)).forEach(stat -> {
            remainingStats.put(stat.getStatistic(), stat.inspectPlayer(player));
        });
        return remainingStats;
    }

    /**
     * Get the lore
     * @return The lore
     */
    public List<Component> getLore() {
        return lore;
    }

    /**
     * Set the lore
     * @param lore The lore
     * @return Itself
     */
    public CustomItem setLore(List<Component> lore) {
        this.lore = lore;
        return this;
    }

    /**
     * Add a line to the lore
     * @param component The line to add
     * @return Itself
     */
    public CustomItem addLore(Component component) {
        if (this.lore == null) this.lore = new ArrayList<>();

        this.lore.add(component);

        return this;
    }

    public CustomItem setCustomModelID(int id) {
        this.customModelID = id;
        return this;
    }

    public int getCustomModelID() {
        return customModelID;
    }

    /**
     * Register this item to the registry
     */
    public void register() {
        CustomItemManager.register(this);
    }

    /**
     * The stack size of the item, or null if using the default.
     * @return the stack size, or null
     */
    public Integer getStackSize() {
        return stackSize;
    }

    /**
     * The stack size of the item, or the default stack size if it's not defined.
     * @return the stack size
     */
    public int getStackSizeOrDefault() {
        // Note: I did it this way because Registry does it this way (though with getOrThrow).
        // https://jd.papermc.io/paper/1.21.8/org/bukkit/Registry.html#get(org.bukkit.NamespacedKey)
        // I'm not 100% sure if this is the best way, though.
        return stackSize != null ? stackSize : material.getMaxStackSize();
    }

    /**
     * Sets the stack size of the item. Feeding null will reset to the default stack size.
     * @param stackSize The new stack size
     * @return Itself
     */
    public CustomItem setStackSize(Integer stackSize) {
        this.stackSize = stackSize;
        return this;
    }

    /**
     * Get the properties of this item
     * @return The properties
     */
    public Set<Property<?>> getProperties() {
        return properties;
    }

    /**
     * Add a property to the item
     * @param property The property
     * @return Itself
     */
    public CustomItem addProperty(Property<?> property) {
        this.properties.add(property);
        return this;
    }

    @Override
    public String toString() {
        return "CustomItem{" +
                "name='" + getInternalName() + '\'' +
                ", textureID=" + customModelID +
                ", material=" + material +
                ", displayName='" + displayName + "\'\u00A7r'" +
                ", stackSize=" + stackSize +
                ", properties=" + properties +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CustomItem that = (CustomItem) o;

        return getKey().equals(that.getKey());
    }

    @Override
    public int hashCode() {
        return getKey().hashCode();
    }

    /**
     * Set the leather armor color of this item
     * @param hex The color
     * @return Itself
     */
    public CustomItem setLeatherColor(int hex) {
        this.hex = hex;
        return this;
    }

    /**
     * Get the leather armor color of this item
     * @return The color (0 for none)
     */
    public int getLeatherColor() {
        return this.hex;
    }

    /**
     * Get the flags applied to this item
     * @return The flags
     */
    public ItemFlag[] getFlags() {
        return flags;
    }

    /**
     * Set the flags of this item
     * @param flags
     * @return itself
     */
    public CustomItem setFlags(ItemFlag... flags) {
        this.flags = flags;
        return this;
    }

    public boolean getBookLike() {
        return bookLike;
    }

    public void setBookLike(boolean bookLike) {
        this.bookLike = bookLike;
    }
}
