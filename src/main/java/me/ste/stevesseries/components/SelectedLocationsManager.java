package me.ste.stevesseries.components;

import me.ste.stevesseries.components.component.ComponentLocation;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * The manager is responsible for storing selected locations of players
 */
public class SelectedLocationsManager {
    public static final Map<UUID, Location> SELECTED_BLOCKS = new HashMap<>();
    public static final Map<UUID, ComponentLocation> SELECTED_COMPONENTS = new HashMap<>();

    private SelectedLocationsManager() {}

    /**
     * Set the currently selected block location of the player
     * @param player target player
     * @param location selected block location
     */
    public static void setBlock(OfflinePlayer player, Location location) {
        if(location != null) {
            SelectedLocationsManager.SELECTED_BLOCKS.put(player.getUniqueId(), location);
        } else {
            SelectedLocationsManager.SELECTED_BLOCKS.remove(player.getUniqueId());
        }
    }

    /**
     * Get the currently selected block location of the player
     * @param player target player
     * @return selected block location
     */
    public static Location getBlock(OfflinePlayer player) {
        return SelectedLocationsManager.SELECTED_BLOCKS.get(player.getUniqueId());
    }

    /**
     * Set the currently selected component location of the player
     * @param player target player
     * @param location selected component location
     */
    public static void setComponent(OfflinePlayer player, ComponentLocation location) {
        if(location != null) {
            SelectedLocationsManager.SELECTED_COMPONENTS.put(player.getUniqueId(), location);
        } else {
            SelectedLocationsManager.SELECTED_COMPONENTS.remove(player.getUniqueId());
        }
    }

    /**
     * Get the currently selected component location of the player
     * @param player target player
     * @return selected component location
     */
    public static ComponentLocation getComponent(OfflinePlayer player) {
        return SelectedLocationsManager.SELECTED_COMPONENTS.get(player.getUniqueId());
    }
}