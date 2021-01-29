package me.ste.stevesseries.components.map.render;

import me.ste.stevesseries.components.map.MapData;
import me.ste.stevesseries.components.map.cursor.MapIconDirection;
import me.ste.stevesseries.components.map.cursor.MapIconType;
import me.ste.stevesseries.components.map.text.CustomMapFont;
import me.ste.stevesseries.components.map.text.TextAnchor;
import me.ste.stevesseries.components.map.text.TextMeasurement;
import org.bukkit.DyeColor;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapCursor;
import org.bukkit.map.MapCursorCollection;

public class CustomMapCanvas {
    private final MapCanvas canvas;
    private final MapData data;

    public CustomMapCanvas(MapData data, MapCanvas canvas) {
        this.data = data;
        this.canvas = canvas;
    }

    public MapCanvas getHandle() {
        return this.canvas;
    }

    /**
     * Draw text at the specified position
     * @param text text to draw
     * @param font font to use
     * @param color font color
     * @param verticalAnchor vertical origin of the text
     * @param horizontalAnchor horizontal origin of the text
     * @param x x position in a 128x128 space
     * @param y y position in a 128x128 space
     * @param scale text scale
     */
    public void drawText(String text, CustomMapFont font, MapColor color, TextAnchor verticalAnchor, TextAnchor horizontalAnchor, int x, int y, int scale) {
        int offsetX = 0;
        TextMeasurement measurement = font.measureText(text, scale);
        if(horizontalAnchor == TextAnchor.MIDDLE) {
            offsetX = -(measurement.getWidth() / 2);
        } else if(horizontalAnchor == TextAnchor.END) {
            offsetX = -measurement.getWidth();
        }
        int offsetY = 0;
        if(verticalAnchor == TextAnchor.MIDDLE) {
            offsetY = -(measurement.getHeight() / 2);
        } else if(verticalAnchor == TextAnchor.END) {
            offsetY = -measurement.getHeight();
        }
        font.draw(this, text, color, x + offsetX, y + offsetY, scale);
    }

    /**
     * Set the pixel at the specified position
     * @param x x position in a 128x128 space
     * @param y y position in a 128x128 space
     * @param color pixel color
     */
    public void setPixel(int x, int y, MapColor color) {
        this.canvas.setPixel(x, y, color.toBukkit());
    }

    /**
     * Fill the pixels in the specified area
     * @param x x position in a 128x128 space
     * @param y y position in a 128x128 space
     * @param w area width
     * @param h area height
     * @param color pixel color
     */
    public void fillPixels(int x, int y, int w, int h, MapColor color) {
        for(int cx = x; cx < x + w; cx++) {
            for(int cy = y; cy < y + h; cy++) {
                this.setPixel(cx, cy, color);
            }
        }
    }

    /**
     * Fill the entire canvas with the specified color
     * @param color pixel color
     */
    public void clearPixels(MapColor color) {
        this.fillPixels(0, 0, 128, 128, color);
    }

    /**
     * Clear all the icons (including banners) on the map
     */
    public void clearIcons() {
        this.canvas.setCursors(new MapCursorCollection());
    }

    /**
     * Draw a banner on the canvas. <strong>Note that this method works in a 256x256 space rather than 128x128</strong>
     * @param x x position in a <strong>a 256x256</strong> space
     * @param y y position in a <strong>a 256x256</strong> space
     * @param color banner color. See <a href="https://minecraft.gamepedia.com/Map#Map_icons">the minecraft wiki</a> for all banner variations. The corresponding {@link DyeColor} will be simply the string after "banner_"
     * @param title title shown under the banner. null for none
     * @param direction banner direction
     * @deprecated <strong>use with caution! The banners will stay on the map if the server is reloaded using the /reload command</strong>
     */
    @Deprecated
    public void drawBannerIcon(int x, int y, DyeColor color, MapIconDirection direction, String title) {
        this.canvas.getCursors().addCursor(new MapCursor((byte) (Byte.MIN_VALUE + x), (byte) (Byte.MIN_VALUE + y), (byte) direction.ordinal(), MapCursor.Type.valueOf("BANNER_" + color.name()), true, title));
    }

    /**
     * Draw an icon on the canvas. <strong>Note that this method works in a 256x256 space rather than 128x128</strong>
     * @param x x position in <strong>a 256x256</strong> space
     * @param y y position in <strong>a 256x256</strong> space
     * @param type icon type
     * @param direction icon direction
     * @param title title shown under the banner. null for none
     * @deprecated <strong>use with caution! The icons will stay on the map if the server is reloaded using the /reload command</strong>
     */
    @Deprecated
    public void drawIcon(int x, int y, MapIconType type, MapIconDirection direction, String title) {
        this.canvas.getCursors().addCursor(new MapCursor((byte) (Byte.MIN_VALUE + x), (byte) (Byte.MIN_VALUE + y), (byte) direction.ordinal(), type.getBukkitType(), true, title));
    }

    /**
     * {@link CustomMapCanvas#clearIcons()} and {@link CustomMapCanvas#clearPixels(MapColor)} at the same time
     * @param color pixel color for {@link CustomMapCanvas#clearPixels(MapColor)}
     */
    public void clearAll(MapColor color) {
        this.clearPixels(color);
        this.clearIcons();
    }
}