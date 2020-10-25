package me.ste.stevesseries.components;

import me.ste.stevesseries.components.component.Component;
import me.ste.stevesseries.components.component.ComponentLocation;
import me.ste.stevesseries.components.component.ComponentManager;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ComponentsCommand implements TabExecutor {
    private final Components plugin = Components.getPlugin(Components.class);

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            if(args.length == 1 && args[0].equals("selectedcomponent")) {
                String componentString = "None";
                Component selected = ComponentManager.getInstance().getSelectedComponent(player);
                if(selected != null) {
                    String name = ComponentManager.getInstance().getFriendlyName(selected.getClass());
                    if(name == null) {
                        name = selected.getClass().getSimpleName();
                    }
                    Location location = selected.getLocation();
                    componentString = String.format("%s | %s @ %d, %d, %d. %s", name, location.getWorld().getName(), location.getBlockX(), location.getBlockY(), location.getBlockZ(), selected.getFace().name());
                }
                sender.spigot().sendMessage(new ComponentBuilder().append("Selected component: ").color(ChatColor.YELLOW).append(componentString).color(ChatColor.GOLD).create());
            } else if(args.length == 1 && args[0].equals("selectedlocation")) {
                String locationString = "None";
                Location selected = this.plugin.getSelectedLocation(player);
                if(selected != null) {
                    locationString = String.format("%s @ %d, %d, %d", selected.getWorld().getName(), selected.getBlockX(), selected.getBlockY(), selected.getBlockZ());
                }
                sender.spigot().sendMessage(new ComponentBuilder().append("Selected location: ").color(ChatColor.YELLOW).append(locationString).color(ChatColor.GOLD).create());
            } else if(args.length == 2 && args[0].equals("selectedcomponent") && args[1].equals("deselect")) {
                ComponentManager.getInstance().setSelectedComponent(player, (ComponentLocation) null);
                sender.spigot().sendMessage(new ComponentBuilder().append("Component has been deselected").color(ChatColor.YELLOW).create());
            } else if(args.length == 2 && args[0].equals("selectedlocation") && args[1].equals("deselect")) {
                this.plugin.setSelectedLocation(player, null);
                sender.spigot().sendMessage(new ComponentBuilder().append("Location has been deselected").color(ChatColor.YELLOW).create());
            } else {
                sender.spigot().sendMessage(new ComponentBuilder().append(String.format("Usage: %s", command.getUsage())).color(ChatColor.RED).create());
            }
        } else {
            sender.spigot().sendMessage(new ComponentBuilder().append("Command sender must be a player").color(ChatColor.RED).create());
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if(args.length == 1) {
            if(args[0].startsWith("selectedc")) {
                return Collections.singletonList("selectedcomponent");
            } else if(args[0].startsWith("selectedl")) {
                return Collections.singletonList("selectedlocation");
            }
            return Arrays.asList("selectedcomponent", "selectedlocaton");
        } else if(args.length == 2) {
            if(args[0].equals("selectedcomponent") || args[0].equals("selectedlocation")) {
                return Collections.singletonList("deselect");
            }
        }
        return Collections.emptyList();
    }
}