package me.ste.stevesseries.components.map.text;

/**
 * The measured width and height of some text
 */
public class TextMeasurement {
    private final int width;
    private final int height;

    public TextMeasurement(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }
}