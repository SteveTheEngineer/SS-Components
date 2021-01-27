package me.ste.stevesseries.components.map.text;

import me.ste.stevesseries.components.map.render.CustomMapCanvas;
import me.ste.stevesseries.components.map.render.MapColor;

import java.util.HashMap;
import java.util.Map;

public class CustomMapFont {
    private final Map<Character, String[]> characterMap = new HashMap<>();
    private final String[] undefinedCharacter;
    private final int characterSpacing;

    /**
     * @param undefinedCharacter the pixel map used for characters that are not registered
     * @param characterSpacing space between characters, in pixels
     */
    public CustomMapFont(String[] undefinedCharacter, int characterSpacing) {
        this.undefinedCharacter = undefinedCharacter;
        this.characterSpacing = characterSpacing;
    }

    /**
     * Measure the provided text with the given scale
     * @param text text to measure
     * @param scale font size
     * @return text measurement
     */
    public TextMeasurement measureText(String text, int scale) {
        int width = 0;
        int height = 0;

        for(char character : text.toCharArray()) {
            String[] map = this.characterMap.get(character);
            if(map == null) {
                map = this.undefinedCharacter;
            }

            int charHeight = map.length;
            int charWidth = 0;
            for(String line : map) {
                charWidth = Math.max(charWidth, line.length());
            }

            width += charWidth * scale + this.characterSpacing;
            height = Math.max(height, charHeight * scale);
        }

        if(width >= this.characterSpacing) {
            width -= this.characterSpacing;
        }

        return new TextMeasurement(width, height);
    }

    /**
     * Draw text on the provided canvas
     * @param canvas canvas to draw on
     * @param text text to draw
     * @param color font color
     * @param x top left position X
     * @param y top left position Y
     * @param scale font size
     */
    public void draw(CustomMapCanvas canvas, String text, MapColor color, int x, int y, int scale) {
        int offsetX = x;

        for(char character : text.toCharArray()) {
            String[] map = this.characterMap.get(character);
            if(map == null) {
                map = this.undefinedCharacter;
            }

            int charHeight = map.length;
            int charWidth = 0;
            int i = 0;
            for(String line : map) {
                int i2 = 0;
                for(char pixel : line.toCharArray()) {
                    if(pixel != ' ') {
                        canvas.fillPixels(offsetX + i2 * scale, y + i * scale, scale, scale, color);
                    }
                    i2++;
                }
                charWidth = Math.max(charWidth, line.length());
                i++;
            }

            offsetX += charWidth * scale + this.characterSpacing;
        }
    }

    /**
     * Register a character
     * @param character character to map
     * @param pixelMap pixel map
     */
    protected final void addCharacter(char character, String[] pixelMap) {
        this.characterMap.put(character, pixelMap);
    }
}