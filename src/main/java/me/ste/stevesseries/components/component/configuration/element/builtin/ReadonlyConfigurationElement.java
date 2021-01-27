package me.ste.stevesseries.components.component.configuration.element.builtin;

import me.ste.stevesseries.base.ItemStackBuilder;
import me.ste.stevesseries.components.Components;
import me.ste.stevesseries.components.component.configuration.element.ConfigurationElement;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.function.Supplier;

public class ReadonlyConfigurationElement implements ConfigurationElement {
    private final static Components PLUGIN = Components.getPlugin(Components.class);

    private final String name;
    private final Supplier<String> valueSupplier;

    public ReadonlyConfigurationElement(String name, Supplier<String> valueSupplier) {
        this.name = name;
        this.valueSupplier = valueSupplier;
    }

    @Override
    public ItemStack getItem(Player player) {
        return new ItemStackBuilder(Material.LIGHT_GRAY_CONCRETE).displayName(ReadonlyConfigurationElement.PLUGIN.getMessage("readonly-element-value", this.name, this.valueSupplier.get())).lore(ReadonlyConfigurationElement.PLUGIN.getMessage("readonly-element-type")).build();
    }

    @Override
    public void onClick(Player player, ClickType clickType) {}
}