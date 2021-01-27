package me.ste.stevesseries.components.component.configuration.element.builtin;

import me.ste.stevesseries.base.ItemStackBuilder;
import me.ste.stevesseries.components.Components;
import me.ste.stevesseries.components.SelectedLocationsManager;
import me.ste.stevesseries.components.component.configuration.element.ConfigurationElement;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Consumer;

import java.util.function.Supplier;

public class BlockConfigurationElement implements ConfigurationElement {
    private final static Components PLUGIN = Components.getPlugin(Components.class);

    private final String name;
    private final Supplier<Location> locationSupplier;
    private final Consumer<Location> locationConsumer;

    public BlockConfigurationElement(String name, Supplier<Location> locationSupplier, Consumer<Location> locationConsumer) {
        this.name = name;
        this.locationSupplier = locationSupplier;
        this.locationConsumer = locationConsumer;
    }

    @Override
    public ItemStack getItem(Player player) {
        Location location = this.locationSupplier.get();
        return new ItemStackBuilder(Material.YELLOW_CONCRETE).displayName(BlockConfigurationElement.PLUGIN.getMessage("block-element-value", this.name, location != null ? BlockConfigurationElement.PLUGIN.getMessage("block-element-location", location.getWorld().getName(), location.getBlockX(), location.getBlockY(), location.getBlockZ()) : BlockConfigurationElement.PLUGIN.getMessage("block-element-none"))).lore(BlockConfigurationElement.PLUGIN.getMessage("block-element-type"), "", SelectedLocationsManager.getBlock(player) != null ? BlockConfigurationElement.PLUGIN.getMessage("block-element-bind") : BlockConfigurationElement.PLUGIN.getMessage("block-element-no-bind"), BlockConfigurationElement.PLUGIN.getMessage("block-element-unbind")).build();
    }

    @Override
    public void onClick(Player player, ClickType clickType) {
        if(clickType.isLeftClick()) {
            Location selected = SelectedLocationsManager.getBlock(player);
            if(selected != null) {
                this.locationConsumer.accept(selected);
            }
        } else if(clickType.isShiftClick() && clickType.isRightClick()) {
            this.locationConsumer.accept(null);
        }
    }
}