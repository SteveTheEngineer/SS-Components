package me.ste.stevesseries.components.map.cursor;

import org.bukkit.map.MapCursor;

/**
 * See <a href="https://minecraft.gamepedia.com/Map#Map_icons">the minecraft wiki</a> to find your feature names
 */
public enum MapMarkerType {
    /**
     * This type of marker does <strong>NOT</strong> display on item frames
     */
    PLAYER(MapCursor.Type.WHITE_POINTER),

    /**
     * This type of marker <strong>DOES</strong> display on item frames
     */
    FRAME(MapCursor.Type.GREEN_POINTER),

    /**
     * This type of marker does <strong>NOT</strong> display on item frames
     */
    RED_MARKER(MapCursor.Type.RED_MARKER),

    /**
     * This type of marker does <strong>NOT</strong> display on item frames
     */
    BLUE_MARKER(MapCursor.Type.BLUE_POINTER);

    private final MapCursor.Type bukkitType;
    MapMarkerType(MapCursor.Type bukkitType) {
        this.bukkitType = bukkitType;
    }

    public MapCursor.Type getBukkitType() {
        return this.bukkitType;
    }
}