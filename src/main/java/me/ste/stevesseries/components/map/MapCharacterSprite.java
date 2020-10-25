package me.ste.stevesseries.components.map;

import org.bukkit.map.MapCanvas;

public class MapCharacterSprite {
    private final int width;
    private final int height;
    private final String[] pixels;

    public MapCharacterSprite(int width, int height, String... pixels) {
        this.width = width;
        this.height = height;
        this.pixels = pixels;
    }

    /**
     * Draw the character at the specified position with the specified parameters
     * @param mapCanvas map canvas
     * @param x character x
     * @param y character y
     * @param size font size
     * @param color font color
     */
    public void draw(MapCanvas mapCanvas, int x, int y, int size, byte color) {
        for(int iy = 0; iy < pixels.length; iy++) {
            String row = pixels[iy];
            for(int ix = 0; ix < row.length(); ix++) {
                boolean val = row.toCharArray()[ix] != ' ';
                if(val) {
                    for(int sy = 0; sy < size; sy++) {
                        for(int sx = 0; sx < size; sx++) {
                            mapCanvas.setPixel(x + ix * size + sx, y + iy * size + sy, color);
                        }
                    }
                }
            }
        }
    }

    /**
     * Get the width of the character
     * @return character width
     */
    public final int getWidth() {
        return this.width;
    }

    /**
     * Get the height of the character
     * @return character height
     */
    public final int getHeight() {
        return this.height;
    }
}