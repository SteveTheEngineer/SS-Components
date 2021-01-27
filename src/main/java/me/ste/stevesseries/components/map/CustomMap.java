package me.ste.stevesseries.components.map;

import me.ste.stevesseries.components.map.render.CustomMapCanvas;
import org.bukkit.entity.Player;

public abstract class CustomMap {
    /**
     * Render the map for the specified player
     * @param player player to render for
     * @param data map data
     * @param canvas map canvas
     */
    public abstract void render(Player player, MapData data, CustomMapCanvas canvas);

    /**
     * Called when the map is interacted by a player being in an item frame
     * @param player player who interacted with the map
     * @param data map data
     * @param type click type
     * @param x x position in a 128x128 space
     * @param y y position in a 128x128 space
     * @param rawX x proportional position, from 0 to 1
     * @param rawY y proportional position, from 0 to 1
     */
    public void onClick(Player player, MapData data, MapClickType type, int x, int y, float rawX, float rawY) {}

    /**
     * Called when the map rendering context is initialized
     * @param data map data
     */
    public void onCreation(MapData data) {}
}