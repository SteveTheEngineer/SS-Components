package me.ste.stevesseries.components.map.builtin;

import me.ste.stevesseries.components.map.MapCharacterSprite;
import me.ste.stevesseries.components.map.MapFont;

/**
 * The default map font
 * @author SteveTheEngineer
 */
public class DefaultFont extends MapFont {
    private static DefaultFont instance;

    public DefaultFont() {
        super(1, new MapCharacterSprite(5, 8,
                "X X X",
                " X X ",
                "X X X",
                " X X ",
                "X X X",
                " X X ",
                "X X X",
                "     "
        ));
        this.setCharacterSprite('A', new MapCharacterSprite(5, 8,
                " XXX ",
                "X   X",
                "XXXXX",
                "X   X",
                "X   X",
                "X   X",
                "X   X",
                "     "
        ));
        this.setCharacterSprite('B', new MapCharacterSprite(5, 8,
                "XXXX ",
                "X   X",
                "X   X",
                "XXXX ",
                "X   X",
                "X   X",
                "XXXX ",
                "     "
        ));
        this.setCharacterSprite('C', new MapCharacterSprite(5, 8,
                "XXXXX",
                "X    ",
                "X    ",
                "X    ",
                "X    ",
                "X    ",
                "XXXXX",
                "     "
        ));
        this.setCharacterSprite('D', new MapCharacterSprite(5, 8,
                "XXXX ",
                "X   X",
                "X   X",
                "X   X",
                "X   X",
                "X   X",
                "XXXX ",
                "     "
        ));
        this.setCharacterSprite('E', new MapCharacterSprite(5, 8,
                "XXXXX",
                "X    ",
                "X    ",
                "XXXXX",
                "X    ",
                "X    ",
                "XXXXX",
                "     "
        ));
        this.setCharacterSprite('F', new MapCharacterSprite(5, 8,
                "XXXXX",
                "X    ",
                "X    ",
                "XXXXX",
                "X    ",
                "X    ",
                "X    ",
                "     "
        ));
        this.setCharacterSprite('G', new MapCharacterSprite(5, 8,
                "XXXXX",
                "X    ",
                "X    ",
                "X    ",
                "X   X",
                "X   X",
                "XXXXX",
                "     "
        ));
        this.setCharacterSprite('H', new MapCharacterSprite(5, 8,
                "X   X",
                "X   X",
                "X   X",
                "XXXXX",
                "X   X",
                "X   X",
                "X   X",
                "     "
        ));
        this.setCharacterSprite('I', new MapCharacterSprite(5, 8,
                "XXXXX",
                "  X  ",
                "  X  ",
                "  X  ",
                "  X  ",
                "  X  ",
                "XXXXX",
                "     "
        ));this.setCharacterSprite('J', new MapCharacterSprite(5, 8,
                "   XX",
                "    X",
                "    X",
                "    X",
                "    X",
                "X   X",
                " XXX ",
                "     "
        ));
        this.setCharacterSprite('K', new MapCharacterSprite(5, 8,
                "X   X",
                "X   X",
                "X   X",
                "XXXX ",
                "X   X",
                "X   X",
                "X   X",
                "     "
        ));
        this.setCharacterSprite('L', new MapCharacterSprite(5, 8,
                "X    ",
                "X    ",
                "X    ",
                "X    ",
                "X    ",
                "X    ",
                "XXXXX",
                "     "
        ));
        this.setCharacterSprite('M', new MapCharacterSprite(5, 8,
                "X   X",
                "XX XX",
                "X X X",
                "X   X",
                "X   X",
                "X   X",
                "X   X",
                "     "
        ));
        this.setCharacterSprite('N', new MapCharacterSprite(5, 8,
                "X   X",
                "X   X",
                "XX  X",
                "X X X",
                "X  XX",
                "X   X",
                "X   X",
                "     "
        ));
        this.setCharacterSprite('O', new MapCharacterSprite(5, 8,
                "XXXXX",
                "X   X",
                "X   X",
                "X   X",
                "X   X",
                "X   X",
                "XXXXX",
                "     "
        ));
        this.setCharacterSprite('P', new MapCharacterSprite(5, 8,
                "XXXXX",
                "X   X",
                "X   X",
                "XXXXX",
                "X    ",
                "X    ",
                "X    ",
                "     "
        ));
        this.setCharacterSprite('Q', new MapCharacterSprite(5, 8,
                "XXXXX",
                "X   X",
                "X   X",
                "X   X",
                "X   X",
                "X  X ",
                "XXX X",
                "     "
        ));
        this.setCharacterSprite('R', new MapCharacterSprite(5, 8,
                "XXXX ",
                "X   X",
                "XXXX ",
                "X   X",
                "X   X",
                "X   X",
                "X   X",
                "     "
        ));
        this.setCharacterSprite('S', new MapCharacterSprite(5, 8,
                "XXXXX",
                "X    ",
                "X    ",
                "XXXXX",
                "    X",
                "    X",
                "XXXXX",
                "     "
        ));
        this.setCharacterSprite('T', new MapCharacterSprite(5, 8,
                "XXXXX",
                "  X  ",
                "  X  ",
                "  X  ",
                "  X  ",
                "  X  ",
                "  X  ",
                "     "
        ));
        this.setCharacterSprite('U', new MapCharacterSprite(5, 8,
                "X   X",
                "X   X",
                "X   X",
                "X   X",
                "X   X",
                "X   X",
                "XXXXX",
                "     "
        ));
        this.setCharacterSprite('V', new MapCharacterSprite(5, 8,
                "X   X",
                "X   X",
                "X   X",
                "X   X",
                "X   X",
                " X X ",
                "  X  ",
                "     "
        ));
        this.setCharacterSprite('W', new MapCharacterSprite(5, 8,
                "X   X",
                "X   X",
                "X   X",
                "X   X",
                "X X X",
                "X X X",
                "XX XX",
                "     "
        ));
        this.setCharacterSprite('X', new MapCharacterSprite(5, 8,
                "X   X",
                "X   X",
                " X X ",
                "  X  ",
                " X X ",
                "X   X",
                "X   X",
                "     "
        ));
        this.setCharacterSprite('Y', new MapCharacterSprite(5, 8,
                "X   X",
                " X X ",
                "  X  ",
                "  X  ",
                "  X  ",
                "  X  ",
                "  X  ",
                "     "
        ));
        this.setCharacterSprite('Z', new MapCharacterSprite(5, 8,
                "XXXXX",
                "    X",
                "   X ",
                "  X  ",
                " X   ",
                "X    ",
                "XXXXX",
                "     "
        ));
        this.setCharacterSprite('a', new MapCharacterSprite(5, 8,
                "     ",
                "     ",
                " XXX ",
                "    X",
                " XXXX",
                "X   X",
                " XXXX",
                "     "
        ));
        this.setCharacterSprite('b', new MapCharacterSprite(5, 8,
                "     ",
                "X    ",
                "X    ",
                "XXXXX",
                "X   X",
                "X   X",
                "XXXXX",
                "     "
        ));
        this.setCharacterSprite('c', new MapCharacterSprite(4, 8,
                "    ",
                "    ",
                "XXXX",
                "X   ",
                "X   ",
                "X   ",
                "XXXX",
                "    "
        ));
        this.setCharacterSprite('d', new MapCharacterSprite(5, 8,
                "     ",
                "    X",
                "    X",
                "XXXXX",
                "X   X",
                "X   X",
                "XXXXX",
                "     "
        ));
        this.setCharacterSprite('e', new MapCharacterSprite(5, 8,
                "     ",
                "     ",
                " XXX ",
                "X   X",
                "XXXX ",
                "X    ",
                " XXXX",
                "     "
        ));
        this.setCharacterSprite('f', new MapCharacterSprite(3, 8,
                "   ",
                "  X",
                " X ",
                "XXX",
                " X ",
                " X ",
                " X ",
                "   "
        ));
        this.setCharacterSprite('g', new MapCharacterSprite(5, 8,
                "     ",
                "     ",
                " XXXX",
                "X   X",
                " XXXX",
                "    X",
                " XXX ",
                "     "
        ));
        this.setCharacterSprite('h', new MapCharacterSprite(5, 8,
                "     ",
                "X    ",
                "X    ",
                "XXXXX",
                "X   X",
                "X   X",
                "X   X",
                "     "
        ));
        this.setCharacterSprite('i', new MapCharacterSprite(1, 8,
                " ",
                "X",
                " ",
                "X",
                "X",
                "X",
                "X",
                " "
        ));
        this.setCharacterSprite('j', new MapCharacterSprite(2, 8,
                "  ",
                " X",
                "  ",
                "XX",
                " X",
                " X",
                "X ",
                "  "
        ));
        this.setCharacterSprite('k', new MapCharacterSprite(4, 8,
                "    ",
                "    ",
                "X  X",
                "X  X",
                "XXX ",
                "X  X",
                "X  X",
                "    "
        ));
        this.setCharacterSprite('l', new MapCharacterSprite(1, 8,
                " ",
                "X",
                "X",
                "X",
                "X",
                "X",
                "X",
                " "
        ));
        this.setCharacterSprite('m', new MapCharacterSprite(5, 8,
                "     ",
                "     ",
                "XX X ",
                "X X X",
                "X X X",
                "X   X",
                "X   X",
                "     "
        ));
        this.setCharacterSprite('n', new MapCharacterSprite(5, 8,
                "     ",
                "     ",
                "XXXX ",
                "X   X",
                "X   X",
                "X   X",
                "X   X",
                "     "
        ));
        this.setCharacterSprite('o', new MapCharacterSprite(4, 8,
                "    ",
                "    ",
                "XXXX",
                "X  X",
                "X  X",
                "X  X",
                "XXXX",
                "    "
        ));
        this.setCharacterSprite('p', new MapCharacterSprite(4, 8,
                "    ",
                "    ",
                "XXXX",
                "X  X",
                "XXXX",
                "X   ",
                "X   ",
                "    "
        ));
        this.setCharacterSprite('q', new MapCharacterSprite(3, 8,
                "   ",
                "   ",
                " XX",
                "X X",
                "XXX",
                "  X",
                "  X",
                "   "
        ));
        this.setCharacterSprite('r', new MapCharacterSprite(3, 8,
                "   ",
                "   ",
                "XX ",
                "X X",
                "X  ",
                "X  ",
                "X  ",
                "   "
        ));
        this.setCharacterSprite('s', new MapCharacterSprite(3, 8,
                "   ",
                "   ",
                "XXX",
                "X  ",
                "XXX",
                "  X",
                "XXX",
                "   "
        ));
        this.setCharacterSprite('t', new MapCharacterSprite(3, 8,
                "   ",
                "   ",
                " X ",
                "XXX",
                " X ",
                " X ",
                " X ",
                "   "
        ));
        this.setCharacterSprite('u', new MapCharacterSprite(5, 8,
                "     ",
                "     ",
                "X   X",
                "X   X",
                "X   X",
                "X   X",
                "XXXXX",
                "     "
        ));
        this.setCharacterSprite('v', new MapCharacterSprite(5, 8,
                "     ",
                "     ",
                "X   X",
                "X   X",
                " X X ",
                " X X ",
                "  X  ",
                "     "
        ));
        this.setCharacterSprite('w', new MapCharacterSprite(5, 8,
                "     ",
                "     ",
                "X   X",
                "X   X",
                "X   X",
                "X X X",
                "XX XX",
                "     ",
                "     "
        ));
        this.setCharacterSprite('x', new MapCharacterSprite(5, 8,
                "     ",
                "     ",
                "X   X",
                " X X ",
                "  X  ",
                " X X ",
                "X   X",
                "     "
        ));
        this.setCharacterSprite('y', new MapCharacterSprite(5, 8,
                "     ",
                "     ",
                "X   X",
                "X   X",
                "XXXXX",
                "    X",
                "XXXX ",
                "     "
        ));
        this.setCharacterSprite('z', new MapCharacterSprite(5, 8,
                "     ",
                "     ",
                "XXXXX",
                "   X ",
                "  X  ",
                " X   ",
                "XXXXX",
                "     "
        ));

        this.setCharacterSprite('!', new MapCharacterSprite(1, 8,
                "X",
                "X",
                "X",
                "X",
                "X",
                " ",
                "X",
                " "
        ));
        this.setCharacterSprite('/', new MapCharacterSprite(5, 8,
                "    X",
                "   X ",
                "   X ",
                "  X  ",
                " X   ",
                " X   ",
                "X    ",
                "     "
        ));
        this.setCharacterSprite(' ', new MapCharacterSprite(2, 8,
                "  ",
                "  ",
                "  ",
                "  ",
                "  ",
                "  ",
                "  ",
                "  "
        ));
        this.setCharacterSprite(',', new MapCharacterSprite(1, 8,
                " ",
                " ",
                " ",
                " ",
                " ",
                " ",
                "X",
                "X"
        ));
        this.setCharacterSprite('.', new MapCharacterSprite(1, 8,
                " ",
                " ",
                " ",
                " ",
                " ",
                " ",
                "X",
                " "
        ));
        this.setCharacterSprite('?', new MapCharacterSprite(3, 8,
                "XXX",
                "  X",
                "  X",
                " XX",
                " X ",
                " X ",
                "   ",
                " X "
        ));
        this.setCharacterSprite('+', new MapCharacterSprite(5, 8,
                "     ",
                "  X  ",
                "  X  ",
                "XXXXX",
                "  X  ",
                "  X  ",
                "     ",
                "     "
        ));
        this.setCharacterSprite('-', new MapCharacterSprite(5, 8,
                "     ",
                "     ",
                "     ",
                "XXXXX",
                "     ",
                "     ",
                "     ",
                "     "
        ));
        this.setCharacterSprite(':', new MapCharacterSprite(1, 8,
                " ",
                "X",
                " ",
                " ",
                " ",
                " ",
                "X",
                " "
        ));
        this.setCharacterSprite(';', new MapCharacterSprite(1, 8,
                " ",
                "X",
                " ",
                " ",
                " ",
                " ",
                "X",
                "X"
        ));

        this.setCharacterSprite('1', new MapCharacterSprite(3, 8,
                " X ",
                "XX ",
                " X ",
                " X ",
                " X ",
                " X ",
                "XXX",
                "   "
        ));
        this.setCharacterSprite('2', new MapCharacterSprite(5, 8,
                "XXXXX",
                "    X",
                "    X",
                "    X",
                " XXX ",
                "X    ",
                "XXXXX",
                "     "
        ));
        this.setCharacterSprite('3', new MapCharacterSprite(5, 8,
                "XXXXX",
                "    X",
                "    X",
                "XXXXX",
                "    X",
                "    X",
                "XXXXX",
                "     "
        ));
        this.setCharacterSprite('4', new MapCharacterSprite(5, 8,
                "X   X",
                "X   X",
                "X   X",
                "XXXXX",
                "    X",
                "    X",
                "    X",
                "     "
        ));
        this.setCharacterSprite('5', new MapCharacterSprite(5, 8,
                "XXXXX",
                "X    ",
                "XXXX ",
                "    X",
                "    X",
                "    X",
                "XXXX ",
                "     "
        ));
        this.setCharacterSprite('6', new MapCharacterSprite(5, 8,
                " XXXX",
                "X    ",
                "X    ",
                "XXXX ",
                "X   X",
                "X   X",
                "XXXXX",
                "     "
        ));
        this.setCharacterSprite('7', new MapCharacterSprite(5, 8,
                "XXXXX",
                "    X",
                "   X ",
                "   X ",
                "  X  ",
                "  X  ",
                " X   ",
                "     "
        ));
        this.setCharacterSprite('8', new MapCharacterSprite(5, 8,
                " XXX ",
                "X   X",
                "X   X",
                " XXX ",
                "X   X",
                "X   X",
                " XXX ",
                "     "
        ));
        this.setCharacterSprite('9', new MapCharacterSprite(5, 8,
                "XXXXX",
                "X   X",
                "X   X",
                " XXXX",
                "    X",
                "    X",
                "XXXX ",
                "     "
        ));
        this.setCharacterSprite('0', new MapCharacterSprite(5, 8,
                " XXX ",
                "X   X",
                "X  XX",
                "X X X",
                "XX  X",
                "X   X",
                " XXX ",
                "     "
        ));
        this.setCharacterSprite('_', new MapCharacterSprite(5, 8,
                "     ",
                "     ",
                "     ",
                "     ",
                "     ",
                "     ",
                "     ",
                "XXXXX"
        ));
        this.setCharacterSprite('˅', new MapCharacterSprite(5, 8,
                "     ",
                "     ",
                "     ",
                "     ",
                "X   X",
                " X X ",
                "  X  ",
                "     "
        ));
        this.setCharacterSprite('˄', new MapCharacterSprite(5, 8,
                "  X  ",
                " X X ",
                "X   X",
                "     ",
                "     ",
                "     ",
                "     ",
                "     "
        ));
        this.setCharacterSprite('<', new MapCharacterSprite(3, 8,
                "   ",
                "  X",
                " X ",
                "X  ",
                " X ",
                "  X",
                "   ",
                "   "
        ));
        this.setCharacterSprite('>', new MapCharacterSprite(3, 8,
                "   ",
                "X  ",
                " X ",
                "  X",
                " X ",
                "X  ",
                "   ",
                "   "
        ));
        this.setCharacterSprite('|', new MapCharacterSprite(1, 8,
                "X",
                "X",
                "X",
                "X",
                "X",
                "X",
                "X",
                " "
        ));
    }

    public static DefaultFont getInstance() {
        if(DefaultFont.instance == null) {
            DefaultFont.instance = new DefaultFont();
        }
        return DefaultFont.instance;
    }
}