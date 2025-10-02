package xyz.holocons.mc.holoitemsrevamp.command;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.apache.commons.lang3.NotImplementedException;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.holocons.mc.holoitemsrevamp.HoloItemsRevamp;
import xyz.holocons.mc.holoitemsrevamp.command.subcommand.AcquireCommand;
import xyz.holocons.mc.holoitemsrevamp.command.subcommand.CollectionsCommand;
import xyz.holocons.mc.holoitemsrevamp.command.subcommand.StatsCommand;

import java.util.*;

public class MainCommand implements BasicCommand {
    
    // Maps from the name of a subcommand to that subcommand
    private final Map<String, SubCommand> subCommands = new HashMap<>();
    private final TextComponent helpComponent;

    public MainCommand(HoloItemsRevamp plugin) {
        addSubCommand(new AcquireCommand(plugin));
        addSubCommand(new CollectionsCommand(plugin));
        addSubCommand(new StatsCommand());
        
        // Create text component message for help page
        final var helpComponentBuilder = Component.text()
            .append(Component.text("=====", NamedTextColor.DARK_AQUA))
            .append(Component.text("HoloItems", NamedTextColor.GREEN))
            .append(Component.text("======", NamedTextColor.DARK_AQUA))
            .append(Component.newline());
        for (var subCommand : subCommands.values()) {
            helpComponentBuilder.append(
                Component.text()
                    .append(Component.text("/holoitems ", NamedTextColor.WHITE))
                    .append(Component.text(subCommand.getName(), NamedTextColor.AQUA))
                    .append(Component.newline())
                    .clickEvent(ClickEvent.suggestCommand("/holoitems " + subCommand.getName() + " "))
                    .hoverEvent(HoverEvent.showText(Component.text(subCommand.getDesc())))
            );
        }
        helpComponentBuilder.append(Component.text("===================", NamedTextColor.DARK_AQUA));
        this.helpComponent = helpComponentBuilder.build();
    }

    @Override
    public void execute(CommandSourceStack sourceStack, String[] args) {
        System.out.println(Arrays.toString(args));
        var sender = sourceStack.getSender();
        if(args.length == 0) {
            sender.sendMessage(helpComponent);
            return;
        }
        var subCommand = subCommands.get(args[0]);
        if (subCommand != null) {
            // found subcommand, attempt to execute:
            if (!subCommand.execute(sender, Arrays.copyOfRange(args, 1, args.length))) {
                sender.sendMessage(Component.text("/holoitems " + subCommand.getName() + " " + subCommand.getFormat())
                    .decoration(TextDecoration.BOLD, true)
                    .decoration(TextDecoration.UNDERLINED, true)
                    .color(NamedTextColor.RED));
            }
        }
        else {
            // no found subcommand:
            sender.sendMessage(Component.text("Command not found!", NamedTextColor.RED).decoration(TextDecoration.BOLD, true));
        }
    }

    @Override
    public @NotNull Collection<String> suggest(CommandSourceStack sourceStack, String[] args) {
        if (args.length <= 1) {
            return subCommands.keySet();
        } else {
            var subCommand = subCommands.get(args[0]);
            if(subCommand == null) {
                return List.of();
            }
            else {
                return subCommand.getAutoComplete(Arrays.copyOfRange(args, 1, args.length));
            }
        }
    }

    @Override
    public boolean canUse(CommandSender sender) {
        return true;
    }
    
    private void addSubCommand(SubCommand subCommand) {
        this.subCommands.put(subCommand.getName(), subCommand);
    }
}
