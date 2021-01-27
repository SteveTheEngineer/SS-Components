package me.ste.stevesseries.components.component.configuration.element.builtin;

import me.ste.stevesseries.base.ChatInputManager;
import me.ste.stevesseries.base.ItemStackBuilder;
import me.ste.stevesseries.components.Components;
import me.ste.stevesseries.components.component.configuration.element.ConfigurationElement;
import me.ste.stevesseries.inventoryguilibrary.GUIManager;
import me.ste.stevesseries.inventoryguilibrary.widget.Widget;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Consumer;

import java.util.function.Supplier;

public class StringConfigurationElement implements ConfigurationElement {
    private final static Components PLUGIN = Components.getPlugin(Components.class);

    private final String name;
    private final Supplier<String> stringSupplier;
    private final Consumer<String> stringConsumer;

    public StringConfigurationElement(String name, Supplier<String> stringSupplier, Consumer<String> stringConsumer) {
        this.name = name;
        this.stringSupplier = stringSupplier;
        this.stringConsumer = stringConsumer;
    }

    @Override
    public ItemStack getItem(Player player) {
        return new ItemStackBuilder(Material.YELLOW_CONCRETE).displayName(StringConfigurationElement.PLUGIN.getMessage("string-element-value", this.name, this.stringSupplier.get())).lore(StringConfigurationElement.PLUGIN.getMessage("string-element-type"), "", StringConfigurationElement.PLUGIN.getMessage("string-element-click")).build();
    }

    @Override
    public void onClick(Player player, ClickType clickType) {
        Widget gui = GUIManager.getGui(player);
        if(gui != null) {
            player.closeInventory();
        }
        player.sendMessage(StringConfigurationElement.PLUGIN.getMessage("type-new-text"));
        ChatInputManager.input(player).thenAccept(text -> {
            player.sendMessage(StringConfigurationElement.PLUGIN.getMessage("new-text-set"));
            this.stringConsumer.accept(text);
            if(gui != null) {
                GUIManager.open(player, gui);
            }
        });
    }
}