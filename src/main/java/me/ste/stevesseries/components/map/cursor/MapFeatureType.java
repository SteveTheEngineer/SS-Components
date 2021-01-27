package me.ste.stevesseries.components.map.cursor;

import org.bukkit.map.MapCursor;

/**
 * See <a href="https://minecraft.gamepedia.com/Map#Map_icons">the minecraft wiki</a> to find your feature names
 */
public enum MapFeatureType {
    /**
     * This type of marker <strong>DOES</strong> display on item frames
     */
    TARGET_X(MapCursor.Type.WHITE_CROSS),

    /**
     * This type of marker <strong>DOES</strong> display on item frames
     */
    TARGET_POINT(MapCursor.Type.RED_MARKER),

    /**
     * This type of marker does <strong>NOT</strong> display on item frames
     */
    PLAYER_OFF_MAP(MapCursor.Type.WHITE_CIRCLE),

    /**
     * This type of marker does <strong>NOT</strong> display on item frames
     */
    PLAYER_OFF_LIMITS(MapCursor.Type.SMALL_WHITE_CIRCLE),

    /**
     * This type of marker <strong>DOES</strong> display on item frames
     */
    MANSION(MapCursor.Type.MANSION),

    /**
     * This type of marker <strong>DOES</strong> display on item frames
     */
    MONUMENT(MapCursor.Type.TEMPLE),

    /**
     * This type of marker <strong>DOES</strong> display on item frames
     */
    RED_X(MapCursor.Type.RED_X);

    private final MapCursor.Type bukkitType;
    MapFeatureType(MapCursor.Type bukkitType) {
        this.bukkitType = bukkitType;
    }

    public MapCursor.Type getBukkitType() {
        return this.bukkitType;
    }
}