package xyz.holocons.mc.holoitemsrevamp.integration;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.strangeone101.holoitemsapi.enchantment.CustomEnchantment;

public interface WorldGuardHook extends Hook {

    default void registerEnchantment(CustomEnchantment enchantment) {
    }

    default boolean canUseEnchantment(Location location, Class<? extends CustomEnchantment> enchantmentCls) {
        return true;
    }

    public class Stub implements WorldGuardHook {
    }

    public class Integration implements WorldGuardHook {

        public static final Map<Class<? extends CustomEnchantment>, Flag<?>> ENCHANTMENT_FLAGS = new HashMap<>();

        private boolean loaded = false;
        private RegionContainer regionContainer = null;

        @Override
        public void onLoad() {
            if (loaded) {
                return;
            }
            WorldGuard.getInstance().getFlagRegistry().registerAll(ENCHANTMENT_FLAGS.values());
            loaded = true;
        }

        @Override
        public void onEnable() {
            this.regionContainer = WorldGuard.getInstance().getPlatform().getRegionContainer();
        }

        @Override
        public void registerEnchantment(CustomEnchantment enchantment) {
            if (loaded) {
                throw new IllegalStateException("New enchantments cannot be registered at this time");
            }
            final var name = "holoitems-" + enchantment.getKey().getKey().replace('_', '-');
            ENCHANTMENT_FLAGS.put(enchantment.getClass(), new StateFlag(name, true));
        }

        @Override
        public boolean canUseEnchantment(Location location, Class<? extends CustomEnchantment> enchantmentCls) {
            final var flag = ENCHANTMENT_FLAGS.get(enchantmentCls);
            final var value = regionContainer.createQuery().queryValue(BukkitAdapter.adapt(location), null, flag);
            return value != StateFlag.State.DENY;
        }
    }
}
