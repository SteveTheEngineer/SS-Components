package me.ste.stevesseries.components.component;

import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

@FunctionalInterface
public interface ComponentFactory {
    /**
     * Create the component at the specified location
     * @param location the location
     * @param face component direction
     * @param player the player who placed the component
     * @return the component
     */
    Component create(Location location, BlockFace face, @Nullable Player player);
}