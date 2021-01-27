package me.ste.stevesseries.components.component.configuration.element.builtin.number;

import me.ste.stevesseries.base.ItemStackBuilder;
import me.ste.stevesseries.components.Components;
import me.ste.stevesseries.components.component.configuration.element.ConfigurationElement;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class NumberConfigurationElement<T extends Number> implements ConfigurationElement {
    private final static Components PLUGIN = Components.getPlugin(Components.class);
    private final String name;
    private final T stepSmall;
    private final T stepLarge;
    private final Supplier<T> valueSupplier;
    private final Consumer<T> valueConsumer;

    public NumberConfigurationElement(String name, T stepSmall, T stepLarge, Supplier<T> valueSupplier, Consumer<T> valueConsumer) {
        this.name = name;
        this.stepSmall = stepSmall;
        this.stepLarge = stepLarge;
        this.valueSupplier = valueSupplier;
        this.valueConsumer = valueConsumer;
    }

    @Override
    public ItemStack getItem(Player player) {
        T value = this.valueSupplier.get();
        return new ItemStackBuilder(value instanceof Double || value instanceof Float ? Material.LIGHT_BLUE_CONCRETE : Material.BLUE_CONCRETE).displayName(NumberConfigurationElement.PLUGIN.getMessage("number-element-value", this.name, value.toString())).lore(NumberConfigurationElement.PLUGIN.getMessage("number-element-type", value.getClass().getSimpleName()), "", NumberConfigurationElement.PLUGIN.getMessage("number-element-add", this.stepSmall.toString()), NumberConfigurationElement.PLUGIN.getMessage("number-element-subtract", this.stepSmall.toString()), "", NumberConfigurationElement.PLUGIN.getMessage("number-element-add-shift", this.stepLarge.toString()), NumberConfigurationElement.PLUGIN.getMessage("number-element-subtract-shift", this.stepLarge.toString())).build();
    }

    @Override
    public void onClick(Player player, ClickType clickType) {
        T mod;
        if(clickType.isShiftClick()) {
            mod = this.stepLarge;
        } else {
            mod = this.stepSmall;
        }
        if(clickType.isLeftClick()) {
            this.valueConsumer.accept(this.addNumbers(this.valueSupplier.get(), mod));
        } else if(clickType.isRightClick()) {
            this.valueConsumer.accept(this.subtractNumbers(this.valueSupplier.get(), mod));
        }
    }

    public abstract T addNumbers(T a, T b);
    public abstract T subtractNumbers(T a, T b);
}