package com.strangeone101.holoitemsapi.enchantment;

import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

/**
 * An interface to allow custom items to be used for enchanting
 */
public interface Enchantable {

    /**
     * Returns the key used by the custom enchantment.
     * @return The NamespacedKey
     */
    public NamespacedKey getEnchantmentKey();

    /**
     * Returns the enchantment the custom item uses.
     * @return The enchantment.
     */
    public default Enchantment getEnchantment() {
        return RegistryAccess.registryAccess()
                .getRegistry(RegistryKey.ENCHANTMENT)
                .get(getEnchantmentKey());
    }

    /**
     * Applies the enchantment to the itemstack.
     * @param itemStack The item stack to apply the enchantment to.
     * @return A clone of the item stack with the enchantment applied.
     */
    public ItemStack applyEnchantment(ItemStack itemStack);

}
