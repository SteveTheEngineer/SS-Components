package me.ste.stevesseries.components.component.config;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

import java.util.Map;

/**
 * A component that can be configured through the component management GUI
 */
public interface ConfigurableComponent {
    /**
     * Get the currently set configuration
     * @return config key -> value map
     */
    Map<ConfigKey, Object> getCurrentConfig();

    /**
     * Change a specific configuration key
     * @param key configuration key to change
     * @param value value to set
     */
    void configure(ConfigKey key, Object value);

    /**
     * Perform custom action of the specified custom type config key
     * @param player player who changed the value
     * @param clickType click type
     * @param key the config key
     */
    default void customAction(Player player, ClickType clickType, ConfigKey key) {}
}