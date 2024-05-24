package xyz.holocons.mc.holoitemsrevamp.integration;

import org.bukkit.plugin.Plugin;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

import xyz.holocons.mc.holoitemsrevamp.HoloItemsRevamp;

public final class Integrations {

    public static final WorldGuardHook WORLDGUARD;

    static {
        final var thisPlugin = HoloItemsRevamp.getPlugin(HoloItemsRevamp.class);
        WORLDGUARD = getPlugin(thisPlugin, "WorldGuard") instanceof WorldGuardPlugin
                ? new WorldGuardHook.Integration()
                : new WorldGuardHook.Stub();
    }

    private Integrations() {
    }

    private static Plugin getPlugin(Plugin thisPlugin, String otherPluginName) {
        final var otherPlugin = thisPlugin.getServer().getPluginManager().getPlugin(otherPluginName);
        if (otherPlugin != null) {
            thisPlugin.getLogger()
                    .info("Found " + otherPlugin.getPluginMeta().getDisplayName());
        }
        return otherPlugin;
    }

    public static void onLoad() {
        WORLDGUARD.onLoad();
    }

    public static void onEnable() {
        WORLDGUARD.onEnable();
    }
}
