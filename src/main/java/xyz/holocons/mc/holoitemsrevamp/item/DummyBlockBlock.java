package xyz.holocons.mc.holoitemsrevamp.item;

import com.strangeone101.holoitemsapi.item.BlockAbility;
import com.strangeone101.holoitemsapi.item.CustomItem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Container;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import xyz.holocons.mc.holoitemsrevamp.HoloItemsRevamp;
import xyz.holocons.mc.holoitemsrevamp.Util;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class DummyBlockBlock extends CustomItem implements BlockAbility {

    private final static String name = "dummy_block";
    private final static Material material = Material.DROPPER;
    private final static Component displayName = Component.text("Dummy Block", NamedTextColor.GOLD);
    private final static List<Component> lore = List.of(
        Component.text("A dummy custom block used for testing.", NamedTextColor.AQUA)
            .decoration(TextDecoration.ITALIC, false)
    );

    public DummyBlockBlock(HoloItemsRevamp plugin) {
        super(plugin, name, material, displayName, lore);
        register();
    }

    @Override
    public short getIdentifier() {
        return 0;
    }

    @Override
    public void onBlockPlace(BlockPlaceEvent event, Block block) {
        event.getPlayer().sendMessage(Component.text("You placed a custom block!"));
    }

    @Override
    public void onBlockBreak(BlockBreakEvent event, Block block) {
        event.getPlayer().sendMessage(Component.text("You broke a custom block!"));
        event.setDropItems(false);

        var location = block.getLocation();

        // Drop custom block
        block.getWorld().dropItemNaturally(location, buildStack(null));

        // Drop contents
        if (block.getState() instanceof Container container) {
            Arrays.stream(container.getInventory().getContents()).filter(Objects::nonNull)
                .forEach(itemStack -> container.getWorld().dropItemNaturally(location, itemStack));
        }
    }

    @Override
    public void onBlockDispense(BlockDispenseEvent event, Block block) {
        var itemStack = new ItemStack(Material.PAPER);
        var itemMeta = itemStack.getItemMeta();

        itemMeta.displayName(Component.text("Current Tick", NamedTextColor.GOLD));
        itemMeta.lore(List.of(Component.text(Util.currentTimeTicks())));

        itemStack.setItemMeta(itemMeta);

        event.setItem(itemStack);
    }
}
