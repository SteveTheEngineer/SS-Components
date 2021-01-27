package me.ste.stevesseries.components.component.configuration.element;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

/**
 * A configuration element. Used in {@link me.ste.stevesseries.components.component.configuration.ConfigurableComponent} for component configuration
 */
public interface ConfigurationElement {
    /**
     * Get the item to display as the configuration element
     * @param player player who sees the item
     * @return item stack
     */
    ItemStack getItem(Player player);

    /**
     * Called when a player clicks on the configuration element
     * @param player player who clicked
     * @param clickType click type
     */
    void onClick(Player player, ClickType clickType);
}