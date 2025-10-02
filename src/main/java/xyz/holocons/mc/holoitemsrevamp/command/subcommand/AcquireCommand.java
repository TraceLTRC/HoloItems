package xyz.holocons.mc.holoitemsrevamp.command.subcommand;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import xyz.holocons.mc.holoitemsrevamp.HoloItemsRevamp;
import xyz.holocons.mc.holoitemsrevamp.command.SubCommand;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class AcquireCommand implements SubCommand {

    private final HoloItemsRevamp plugin;

    public AcquireCommand(HoloItemsRevamp plugin){
        this.plugin = plugin;
    }

    @Override
    public String getName() {
        return "acquire";
    }

    @Override
    public String getDesc() {
        return "Give a HoloItem";
    }

    @Override
    public String getFormat() {
        return "<item> [amount] [player]";
    }

    @Override
    public String getPermission() {
        return "holoitems.acquire";
    }

    @Override
    public List<String> getAutoComplete(String[] args) {
        return switch (args.length) {
            case 1 -> List.copyOf(plugin.getCollectionManager().getAllItems().keySet());
            case 3 -> null;
            default -> List.of();
        };
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (args.length == 0 || !sender.hasPermission(getPermission())) {
            return false;
        }

        var customItem = plugin.getCollectionManager().getAllItems().get(args[0]);
        if (customItem == null) {
            sender.sendMessage(Component.text(args[0] + " is not a valid item!", NamedTextColor.YELLOW)
                .decoration(TextDecoration.ITALIC, true));
            return false;
        }

        int amount;
        if (args.length == 1) {
            // Amount argument was not given
            amount = 1;
        } else {
            try {
                amount = Integer.parseInt(args[1]);
                if (amount <= 0 || amount > 128) {
                    // Limit max amount to 128
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                sender.sendMessage(Component.text(args[1] + " is an invalid amount!",
                        NamedTextColor.YELLOW)
                    .decoration(TextDecoration.ITALIC, true));
                return false;
            }
        }

        Player player;
        if (args.length >= 3) {
            // Player argument was given
            player = Bukkit.getPlayer(args[2]);
        } else {
            // Less than 3 arguments were given. Check if the player to give the item to is the command sender
            player = sender instanceof Player ? (Player) sender : null;
        }
        if (player == null) {
            // Player is offline or doesn't exist
            sender.sendMessage(Component.text("Player not found!", NamedTextColor.YELLOW)
                .decoration(TextDecoration.ITALIC, true));
            return false;
        }

        var itemStack = customItem.buildStack(player);
        itemStack.setAmount(customItem.getStackSizeOrDefault());

        int totalItemStacks = ((amount - 1)/customItem.getStackSizeOrDefault()) + 1;
        int lastItemStackSize = amount - ((totalItemStacks - 1) * customItem.getStackSizeOrDefault());
        // Failsafe incase my math was bad (it was one time lol)
        lastItemStackSize = Math.clamp(0, lastItemStackSize, customItem.getStackSizeOrDefault());
        ItemStack lastItemStack = itemStack.clone();
        lastItemStack.setAmount(lastItemStackSize);

        var itemStacks = new ItemStack[totalItemStacks];
        Arrays.fill(itemStacks, itemStack);
        itemStacks[totalItemStacks - 1] = lastItemStack;

        Map<Integer, ItemStack> leftoverItems = player.getInventory().addItem(itemStacks);
        // If items could not fit in player's inventory, drop them in the world
        leftoverItems.values().forEach(item -> player.getWorld().dropItemNaturally(player.getLocation(), item));

        return true;
    }
}
