package xyz.holocons.mc.holoitemsrevamp;

import com.strangeone101.holoitemsapi.Keys;
import com.strangeone101.holoitemsapi.enchantment.AnvilListener;
import com.strangeone101.holoitemsapi.enchantment.EnchantManager;
import com.strangeone101.holoitemsapi.enchantment.EnchantmentListener;
import com.strangeone101.holoitemsapi.item.BlockListener;
import com.strangeone101.holoitemsapi.item.CustomItemManager;
import com.strangeone101.holoitemsapi.recipe.CraftListener;
import com.strangeone101.holoitemsapi.recipe.RecipeManager;
import com.strangeone101.holoitemsapi.tracking.CustomBlockStorage;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.plugin.java.JavaPlugin;

import xyz.holocons.mc.holoitemsrevamp.collection.CollectionManager;
import xyz.holocons.mc.holoitemsrevamp.command.MainCommand;
import xyz.holocons.mc.holoitemsrevamp.integration.Integrations;

public final class HoloItemsRevamp extends JavaPlugin {

    private CollectionManager collectionManager;
    private CustomBlockStorage trackingManager;
    private RecipeManager recipeManager;
    private EnchantManager enchantManager;

    @Override
    public void onLoad() {
        Keys.fillKeys(this);

        this.enchantManager = new EnchantManager(this);
        this.collectionManager = new CollectionManager(this);
        this.trackingManager = new CustomBlockStorage(this);
        this.recipeManager = new RecipeManager(this);

        Integrations.onLoad();
    }

    // This is for the (numerous) Lifecycle functions, which are all marked Experimental in 1.21.1, but
    // are no longer marked as such in later versions.
    @SuppressWarnings("UnstableApiUsage")
    @Override
    public void onEnable() {
        Integrations.onEnable();

        CustomItemManager.lock(getRecipeManager());
        trackingManager.loadTrackedBlocks();

        getServer().getPluginManager().registerEvents(new EnchantmentListener(this), this);
        getServer().getPluginManager().registerEvents(new AnvilListener(this), this);
        getServer().getPluginManager().registerEvents(new CraftListener(this), this);
        getServer().getPluginManager().registerEvents(new BlockListener(this), this);

//        getCommand("holoitems").setExecutor(new MainCommand(this));
        var lifecycleManager = this.getLifecycleManager();
        var mainCommand = new MainCommand(this);
        lifecycleManager.registerEventHandler(LifecycleEvents.COMMANDS, commands -> {
            var registrar = commands.registrar();
            registrar.register("holoitems", mainCommand);
        });

        getLogger().info("HoloItems-Revamped [ON]");
    }

    @Override
    public void onDisable() {
        trackingManager.saveTrackedBlocks();
    }

    public CollectionManager getCollectionManager() {
        return collectionManager;
    }

    public CustomBlockStorage getTrackingManager() {
        return trackingManager;
    }

    public RecipeManager getRecipeManager() {
        return recipeManager;
    }

    public EnchantManager getEnchantManager() {
        return enchantManager;
    }
}
