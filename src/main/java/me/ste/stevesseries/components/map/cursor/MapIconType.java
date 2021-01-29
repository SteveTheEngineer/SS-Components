package me.ste.stevesseries.components.map.cursor;

import org.bukkit.map.MapCursor;

/**
 * See <a href="https://minecraft.gamepedia.com/Map#Map_icons">the minecraft wiki</a> to find the icon names. Also keep in mind that some of these icons won't display on item frames!
 */
public enum MapIconType {
    PLAYER(MapCursor.Type.WHITE_POINTER),
    FRAME(MapCursor.Type.GREEN_POINTER),
    RED_MARKER(MapCursor.Type.RED_MARKER),
    BLUE_MARKER(MapCursor.Type.BLUE_POINTER),
    TARGET_X(MapCursor.Type.WHITE_CROSS),
    TARGET_POINT(MapCursor.Type.RED_MARKER),
    PLAYER_OFF_MAP(MapCursor.Type.WHITE_CIRCLE),
    PLAYER_OFF_LIMITS(MapCursor.Type.SMALL_WHITE_CIRCLE),
    MANSION(MapCursor.Type.MANSION),
    MONUMENT(MapCursor.Type.TEMPLE),
    RED_X(MapCursor.Type.RED_X);

    private final MapCursor.Type bukkitType;
    MapIconType(MapCursor.Type bukkitType) {
        this.bukkitType = bukkitType;
    }

    public MapCursor.Type getBukkitType() {
        return this.bukkitType;
    }
}