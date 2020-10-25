package me.ste.stevesseries.components.map;

import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.jetbrains.annotations.Nullable;

public abstract class Map {
    public static final BlockFace[] ATTACHABLE_FACES = new BlockFace[] {
            BlockFace.NORTH,
            BlockFace.EAST,
            BlockFace.SOUTH,
            BlockFace.WEST,
            BlockFace.UP,
            BlockFace.DOWN
    };

    public static final int WIDTH = 128;
    public static final int HEIGHT = 128;

    private final Location mapLocation;
    private final BlockFace face;
    private final boolean contextual;
    private final MapRenderer renderer;

    /**
     * @param mapLocation the location of the map
     * @param face the map facing direction
     * @param contextual whether is the map rendered uniquely for each player
     */
    public Map(Location mapLocation, BlockFace face, boolean contextual) {
        this.mapLocation = mapLocation;
        this.face = face;
        this.contextual = contextual;
        this.renderer = new SimpleMapRenderer(this);
    }

    /**
     * @param mapLocation the location of the map
     * @param face the map facing direction
     */
    public Map(Location mapLocation, BlockFace face) {
        this(mapLocation, face, false);
    }

    /**
     * Get the location of the map
     * @return the location
     */
    public final Location getMapLocation() {
        return this.mapLocation;
    }

    /**
     * Get the facing direction of the map
     * @return the facing direction
     */
    public final BlockFace getFace() {
        return this.face;
    }

    /**
     * Get whether is the map rendered uniquely for each player
     * @return
     */
    public final boolean isContextual() {
        return this.contextual;
    }

    /**
     * Handle map initialization
     * @param view map view
     */
    public void initialize(MapView view) {}

    /**
     * Handle player interacting with the map
     * @param player the player who interacted
     * @param leftClick true, if the click was a left click or false if the click was a right click
     * @param x clicked x
     * @param y clicked y
     */
    public void handleClick(Player player, boolean leftClick, int x, int y) {}

    /**
     * Render the map
     * @param player the player to render for
     * @param view map view
     * @param canvas the canvas
     */
    public abstract void render(Player player, MapView view, MapCanvas canvas);

    /**
     * Get the map bukkit renderer
     * @return the renderer
     */
    public MapRenderer getRenderer() {
        return this.renderer;
    }

    /**
     * Fill the specified area with the specified color
     * @param canvas the canvas to fill the area on
     * @param x start location x
     * @param y start location y
     * @param w rectangle width
     * @param h rectangle height
     * @param color the color to fill with
     */
    public static void fillRect(MapCanvas canvas, int x, int y, int w, int h, byte color) {
        for(int cx = x; cx < x + w; cx++) {
            for(int cy = y; cy < y + h; cy++) {
                canvas.setPixel(cx, cy, color);
            }
        }
    }
}