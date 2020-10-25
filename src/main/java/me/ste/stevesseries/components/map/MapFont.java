package me.ste.stevesseries.components.map;

import org.bukkit.map.MapCanvas;

import java.util.HashMap;

public abstract class MapFont {
    private final int spaceBetweenCharacters;
    private final java.util.Map<Character, MapCharacterSprite> characterSprites;
    private final MapCharacterSprite defaultSprite;

    public MapFont(int spaceBetweenCharacters, MapCharacterSprite defaultSprite) {
        this.spaceBetweenCharacters = spaceBetweenCharacters;
        this.defaultSprite = defaultSprite;
        this.characterSprites = new HashMap<>();
    }

    /**
     * Set the character sprite for the specified character
     * @param c character
     * @param sprite sprite
     */
    protected final void setCharacterSprite(char c, MapCharacterSprite sprite) {
        this.characterSprites.put(c, sprite);
    }

    /**
     * Draw text at the specified position with the specified parameters
     * @param mapCanvas map canvas to draw on
     * @param x position x
     * @param y position y
     * @param color font color
     * @param text the text to draw
     * @param size font size
     */
    public final void drawText(MapCanvas mapCanvas, int x, int y, byte color, String text, int size) {
        int currentWidth = 0;
        for(char c : text.toCharArray()) {
            if(this.characterSprites.containsKey(c)) {
                this.characterSprites.get(c).draw(mapCanvas, x + currentWidth, y, size, color);
                currentWidth += this.characterSprites.get(c).getWidth() * size;
            } else {
                this.defaultSprite.draw(mapCanvas, x + currentWidth, y, size, color);
                currentWidth += this.defaultSprite.getWidth() * size;
            }
            currentWidth += this.spaceBetweenCharacters;
        }
    }

    /**
     * Get the resulting width of the specified text in map pixels
     * @param text the text
     * @param size font size
     * @return width
     */
    public final int getWidth(String text, int size) {
        if(text.length() <= 0) {
            return 0;
        }
        int currentWidth = 0;
        for(char c : text.toCharArray()) {
            if(this.characterSprites.containsKey(c)) {
                currentWidth += this.characterSprites.get(c).getWidth() * size;
            } else {
                currentWidth += this.defaultSprite.getWidth() * size;
            }
            currentWidth += this.spaceBetweenCharacters;
        }
        currentWidth -= this.spaceBetweenCharacters;
        return currentWidth;
    }

    /**
     * Get the resulting height of the specified text in map pixels
     * @param text the text
     * @param size font size
     * @return height
     */
    public final int getHeight(String text, int size) {
        if(text.length() <= 0) {
            return 0;
        }
        int currentHeight = 0;
        for(char c : text.toCharArray()) {
            int height;
            if(this.characterSprites.containsKey(c)) {
                height = this.characterSprites.get(c).getHeight() * size;
            } else {
                height = this.defaultSprite.getWidth() * size;
            }
            if(height >= currentHeight) {
                currentHeight = height;
            }
        }
        return currentHeight;
    }

    /**
     * Get the width of the empty space between characters in pixels
     * @return the width
     */
    public int getSpaceBetweenCharacters() {
        return this.spaceBetweenCharacters;
    }
}