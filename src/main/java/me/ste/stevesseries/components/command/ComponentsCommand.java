package me.ste.stevesseries.components.command;

import me.ste.stevesseries.components.Components;
import me.ste.stevesseries.components.SelectedLocationsManager;
import me.ste.stevesseries.components.component.Component;
import me.ste.stevesseries.components.component.ComponentLocation;
import me.ste.stevesseries.components.component.ComponentManager;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class ComponentsCommand implements TabExecutor {
    private final Components plugin;

    public ComponentsCommand(Components plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender.hasPermission("stevesseries.components.command")) {
            if(sender instanceof Player) {
                Player player = (Player) sender;
                String mode = args.length >= 1 ? args[0] : null;

                if(mode != null && mode.equals("selectedblock")) {
                    if(args.length < 2 || !args[1].equals("deselect")) {
                        Location selected = SelectedLocationsManager.getBlock(player);
                        sender.sendMessage(this.plugin.getMessage("selected-block", selected != null ? this.plugin.getMessage("selected-block-location", selected.getWorld().getName(), selected.getBlockX(), selected.getBlockY(), selected.getBlockZ()) : this.plugin.getMessage("selected-block-none")));
                    } else {
                        SelectedLocationsManager.setBlock(player, null);
                        sender.sendMessage(this.plugin.getMessage("block-deselected"));
                    }
                } else if(mode != null && mode.equals("selectedcomponent")) {
                    if(args.length < 2 || !args[1].equals("deselect")) {
                        String value;
                        ComponentLocation location = SelectedLocationsManager.getComponent(player);
                        if(location != null) {
                            Location bukkitLocation = location.getLocation();
                            Component component = ComponentManager.getComponentAt(location);
                            value = String.format("%s | %s @ %d, %d, %d. %s", component != null ? ComponentManager.getRegisteredComponentData(component.getClass()).getDisplayName() : this.plugin.getMessage("component-invalid"), bukkitLocation.getWorld().getName(), bukkitLocation.getBlockX(), bukkitLocation.getBlockY(), bukkitLocation.getBlockZ(), location.getDirection().name());
                        } else {
                            value = this.plugin.getMessage("selected-component-none");
                        }
                        sender.sendMessage(this.plugin.getMessage("selected-component", value));
                    } else {
                        SelectedLocationsManager.setComponent(player, null);
                        sender.sendMessage(this.plugin.getMessage("component-deselected"));
                    }
                } else {
                    sender.sendMessage(this.plugin.getMessage("command-usage"));
                }
            } else {
                sender.sendMessage(this.plugin.getMessage("player-only"));
            }
        } else {
            sender.sendMessage(this.plugin.getMessage("no-permission"));
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if(args.length == 1) {
            List<String> suggestions = new ArrayList<>(Arrays.asList("selectedcomponent", "selectedblock"));
            suggestions.removeIf(suggestion -> !suggestion.startsWith(args[0]));
            return suggestions;
        } else if(args.length == 2) {
            if(args[0].equals("selectedcomponent") || args[0].equals("selectedblock")) {
                if("deselect".startsWith(args[1])) {
                    return Collections.singletonList("deselect");
                }
            }
        }
        return Collections.emptyList();
    }
}