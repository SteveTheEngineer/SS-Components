package me.ste.stevesseries.components.component.configuration.element.builtin;

import me.ste.stevesseries.base.ItemStackBuilder;
import me.ste.stevesseries.components.Components;
import me.ste.stevesseries.components.component.ComponentManager;
import me.ste.stevesseries.components.SelectedLocationsManager;
import me.ste.stevesseries.components.component.Component;
import me.ste.stevesseries.components.component.ComponentLocation;
import me.ste.stevesseries.components.component.configuration.element.ConfigurationElement;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Consumer;

import java.util.function.Supplier;

public class ComponentConfigurationElement implements ConfigurationElement {
    private final static Components PLUGIN = Components.getPlugin(Components.class);

    private final String name;
    private final Supplier<ComponentLocation> componentSupplier;
    private final Consumer<ComponentLocation> componentConsumer;

    public ComponentConfigurationElement(String name, Supplier<ComponentLocation> componentSupplier, Consumer<ComponentLocation> componentConsumer) {
        this.name = name;
        this.componentSupplier = componentSupplier;
        this.componentConsumer = componentConsumer;
    }

    @Override
    public ItemStack getItem(Player player) {
        ComponentLocation component = this.componentSupplier.get();
        Component actualComponent = component != null ? ComponentManager.getComponentAt(component) : null;
        Location location = component != null ? component.getLocation() : null;
        return new ItemStackBuilder(Material.YELLOW_CONCRETE).displayName(ComponentConfigurationElement.PLUGIN.getMessage("component-element-value", this.name, component != null ? ComponentConfigurationElement.PLUGIN.getMessage("component-element-component", actualComponent != null ? ComponentManager.getRegisteredComponentData(actualComponent.getClass()).getDisplayName() : ComponentConfigurationElement.PLUGIN.getMessage("component-element-invalid"), location.getWorld().getName(), location.getBlockX(), location.getBlockY(), location.getBlockZ(), component.getDirection().name()) : ComponentConfigurationElement.PLUGIN.getMessage("component-element-none"))).lore(ComponentConfigurationElement.PLUGIN.getMessage("component-element-type"), "", SelectedLocationsManager.getComponent(player) != null ? ComponentConfigurationElement.PLUGIN.getMessage("component-element-bind") : ComponentConfigurationElement.PLUGIN.getMessage("component-element-no-bind"), ComponentConfigurationElement.PLUGIN.getMessage("component-element-unbind")).build();
    }

    @Override
    public void onClick(Player player, ClickType clickType) {
        if(clickType.isLeftClick()) {
            ComponentLocation selected = SelectedLocationsManager.getComponent(player);
            if(selected != null) {
                this.componentConsumer.accept(selected);
            }
        } else if(clickType.isShiftClick() && clickType.isRightClick()) {
            this.componentConsumer.accept(null);
        }
    }
}