package me.ste.stevesseries.components.map.render;

import org.bukkit.Color;
import org.jetbrains.annotations.Nullable;

/**
 * See <a href="https://minecraft.gamepedia.com/Map_item_format#Base_colors">the minecraft wiki</a> to find your color names. Use {@link MapColor#withLightness(Lightness)} to set the lightness of the color afterwards
 */
public class MapColor {
    public static final MapColor NONE = new MapColor(0, null);
    public static final MapColor GRASS = new MapColor(1, Color.fromRGB(127, 178, 56));
    public static final MapColor SAND = new MapColor(2, Color.fromRGB(247, 233, 163));
    public static final MapColor WOOL = new MapColor(3, Color.fromRGB(199, 199, 199));
    public static final MapColor FIRE = new MapColor(4, Color.fromRGB(255, 0, 0));
    public static final MapColor ICE = new MapColor(5, Color.fromRGB(160, 160, 255));
    public static final MapColor METAL = new MapColor(6, Color.fromRGB(167, 167, 167));
    public static final MapColor PLANT = new MapColor(7, Color.fromRGB(0, 124, 0));
    public static final MapColor SNOW = new MapColor(8, Color.fromRGB(255, 255, 255));
    public static final MapColor CLAY = new MapColor(9, Color.fromRGB(164, 168, 184));
    public static final MapColor DIRT = new MapColor(10, Color.fromRGB(151, 109, 77));
    public static final MapColor STONE = new MapColor(11, Color.fromRGB(112, 112, 112));
    public static final MapColor WATER = new MapColor(12, Color.fromRGB(64, 64, 255));
    public static final MapColor WOOD = new MapColor(13, Color.fromRGB(143, 119, 72));
    public static final MapColor QUARTZ = new MapColor(14, Color.fromRGB(255, 252, 245));
    public static final MapColor COLOR_ORANGE = new MapColor(15, Color.fromRGB(216, 127, 51));
    public static final MapColor COLOR_MAGENTA = new MapColor(16, Color.fromRGB(178, 76, 216));
    public static final MapColor COLOR_LIGHT_BLUE = new MapColor(17, Color.fromRGB(102, 153, 216));
    public static final MapColor COLOR_YELLOW = new MapColor(18, Color.fromRGB(229, 229, 51));
    public static final MapColor COLOR_LIGHT_GREEN = new MapColor(19, Color.fromRGB(127, 204, 25));
    public static final MapColor COLOR_PINK = new MapColor(20, Color.fromRGB(242, 127, 165));
    public static final MapColor COLOR_GRAY = new MapColor(21, Color.fromRGB(76, 76, 76));
    public static final MapColor COLOR_LIGHT_GRAY = new MapColor(22, Color.fromRGB(153, 153, 153));
    public static final MapColor COLOR_CYAN = new MapColor(23, Color.fromRGB(76, 127, 153));
    public static final MapColor COLOR_PURPLE = new MapColor(24, Color.fromRGB(127, 63, 178));
    public static final MapColor COLOR_BLUE = new MapColor(25, Color.fromRGB(51, 76, 178));
    public static final MapColor COLOR_BROWN = new MapColor(26, Color.fromRGB(102, 76, 51));
    public static final MapColor COLOR_GREEN = new MapColor(27, Color.fromRGB(102, 127, 51));
    public static final MapColor COLOR_RED = new MapColor(28, Color.fromRGB(153, 51, 51));
    public static final MapColor COLOR_BLACK = new MapColor(29, Color.fromRGB(25, 25, 25));
    public static final MapColor GOLD = new MapColor(30, Color.fromRGB(250, 238, 77));
    public static final MapColor DIAMOND = new MapColor(31, Color.fromRGB(92, 219, 213));
    public static final MapColor LAPIS = new MapColor(32, Color.fromRGB(74, 128, 255));
    public static final MapColor EMERALD = new MapColor(33, Color.fromRGB(0, 217, 58));
    public static final MapColor PODZOL = new MapColor(34, Color.fromRGB(129, 86, 49));
    public static final MapColor NETHER = new MapColor(35, Color.fromRGB(112, 2, 0));
    public static final MapColor TERRACOTTA_WHITE = new MapColor(36, Color.fromRGB(209, 177, 161));
    public static final MapColor TERRACOTTA_ORANGE = new MapColor(37, Color.fromRGB(159, 82, 36));
    public static final MapColor TERRACOTTA_MAGENTA = new MapColor(38, Color.fromRGB(149, 87, 108));
    public static final MapColor TERRACOTTA_LIGHT_BLUE = new MapColor(39, Color.fromRGB(112, 108, 138));
    public static final MapColor TERRACOTTA_YELLOW = new MapColor(40, Color.fromRGB(186, 133, 36));
    public static final MapColor TERRACOTTA_LIGHT_GREEN = new MapColor(41, Color.fromRGB(103, 117, 53));
    public static final MapColor TERRACOTTA_PINK = new MapColor(42, Color.fromRGB(160, 77, 78));
    public static final MapColor TERRACOTTA_GRAY = new MapColor(43, Color.fromRGB(57, 41, 35));
    public static final MapColor TERRACOTTA_LIGHT_GRAY = new MapColor(44, Color.fromRGB(135, 107, 98));
    public static final MapColor TERRACOTTA_CYAN = new MapColor(45, Color.fromRGB(87, 92, 92));
    public static final MapColor TERRACOTTA_PURPLE = new MapColor(46, Color.fromRGB(122, 73, 88));
    public static final MapColor TERRACOTTA_BLUE = new MapColor(47, Color.fromRGB(76, 62, 92));
    public static final MapColor TERRACOTTA_BROWN = new MapColor(48, Color.fromRGB(76, 50, 35));
    public static final MapColor TERRACOTTA_GREEN = new MapColor(49, Color.fromRGB(76, 82, 42));
    public static final MapColor TERRACOTTA_RED = new MapColor(50, Color.fromRGB(142, 60, 46));
    public static final MapColor TERRACOTTA_BLACK = new MapColor(51, Color.fromRGB(37, 22, 16));
    public static final MapColor CRIMSON_NYLIUM = new MapColor(52, Color.fromRGB(189, 48, 49));
    public static final MapColor CRIMSON_STEM = new MapColor(53, Color.fromRGB(148, 63, 97));
    public static final MapColor CRIMSON_HYPHAE = new MapColor(54, Color.fromRGB(92, 25, 29));
    public static final MapColor WARPED_NYLIUM = new MapColor(55, Color.fromRGB(22, 126, 134));
    public static final MapColor WARPED_STEM = new MapColor(56, Color.fromRGB(58, 142, 140));
    public static final MapColor WARPED_HYPHAE = new MapColor(57, Color.fromRGB(86, 44, 62));
    public static final MapColor WARPED_WART_BLOCK = new MapColor(58, Color.fromRGB(20, 180, 133));

    public enum Lightness {
        /**
         * Base Color ID*4 + 3
         */
        DARKEST(3, 135),

        /**
         * Base Color ID*4 + 0
         */
        DARKER(0, 180),

        /**
         * Base Color ID*4 + 1
         */
        LIGHTER(1, 220),

        /**
         * Base Color ID*4 + 2
         */
        LIGHTEST(2, 255);

        private final int subId;
        private final int rgbMultiplier;
        Lightness(int subId, int rgbMultiplier) {
            this.subId = subId;
            this.rgbMultiplier = rgbMultiplier;
        }
    }

    private final int id;
    private final Lightness lightness;
    private final Color color;

    private MapColor(int id, Color color, Lightness lightness) {
        this.id = id;
        this.color = color;
        this.lightness = lightness;
    }

    private MapColor(int id, Color color) {
        this(id, color, Lightness.LIGHTEST);
    }

    public MapColor withLightness(MapColor.Lightness lightness) {
        if(this.lightness == lightness) {
            return this;
        }
        return new MapColor(this.id, this.color, this.lightness);
    }

    public byte toBukkit() {
        return (byte) (this.id * 4 + this.lightness.subId);
    }

    public Lightness getLightness() {
        return this.lightness;
    }

    @Nullable
    public Color getColor() {
        return this.color != null ? Color.fromRGB((int) Math.floor((double) this.color.getRed() * (double) this.lightness.rgbMultiplier / 255D), (int) Math.floor((double) this.color.getGreen() * (double) this.lightness.rgbMultiplier / 255D), (int) Math.floor((double) this.color.getBlue() * (double) this.lightness.rgbMultiplier / 255D)) : null;
    }
}