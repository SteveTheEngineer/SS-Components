package me.ste.stevesseries.components.component.configuration.element.builtin;

import me.ste.stevesseries.base.ItemStackBuilder;
import me.ste.stevesseries.components.Components;
import me.ste.stevesseries.components.component.configuration.element.ConfigurationElement;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.function.Supplier;

public class BooleanConfigurationElement implements ConfigurationElement {
    private final static Components PLUGIN = Components.getPlugin(Components.class);

    private final ItemStack trueItem;
    private final ItemStack falseItem;
    private final Runnable toggleRunnable;
    private final Supplier<Boolean> valueSupplier;

    public BooleanConfigurationElement(String name, Supplier<Boolean> valueSupplier, Runnable toggleRunnable) {
        this.valueSupplier = valueSupplier;
        this.toggleRunnable = toggleRunnable;

        this.trueItem = new ItemStackBuilder(Material.LIME_CONCRETE).displayName(BooleanConfigurationElement.PLUGIN.getMessage("boolean-element-value", name, BooleanConfigurationElement.PLUGIN.getMessage("boolean-element-true"))).lore(BooleanConfigurationElement.PLUGIN.getMessage("boolean-element-type"), "", BooleanConfigurationElement.PLUGIN.getMessage("boolean-element-toggle")).build();
        this.falseItem = new ItemStackBuilder(Material.RED_CONCRETE).displayName(BooleanConfigurationElement.PLUGIN.getMessage("boolean-element-value", name, BooleanConfigurationElement.PLUGIN.getMessage("boolean-element-false"))).lore(BooleanConfigurationElement.PLUGIN.getMessage("boolean-element-type"), "", BooleanConfigurationElement.PLUGIN.getMessage("boolean-element-toggle")).build();
    }

    @Override
    public ItemStack getItem(Player player) {
        return this.valueSupplier.get() ? this.trueItem : this.falseItem;
    }

    @Override
    public void onClick(Player player, ClickType clickType) {
        this.toggleRunnable.run();
    }
}