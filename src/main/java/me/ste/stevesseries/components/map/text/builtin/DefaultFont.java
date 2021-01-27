package me.ste.stevesseries.components.map.text.builtin;

import me.ste.stevesseries.components.map.text.CustomMapFont;

public class DefaultFont extends CustomMapFont {
    public static final DefaultFont INSTANCE = new DefaultFont();
    
    private DefaultFont() {
        super(new String[] {
                "X X X",
                " X X ",
                "X X X",
                " X X ",
                "X X X",
                " X X ",
                "X X X",
                "     "
        }, 1);

        this.addCharacter('A', new String[] {
                " XXX ",
                "X   X",
                "XXXXX",
                "X   X",
                "X   X",
                "X   X",
                "X   X",
                "     "
        });
        this.addCharacter('B', new String[] {
                "XXXX ",
                "X   X",
                "X   X",
                "XXXX ",
                "X   X",
                "X   X",
                "XXXX ",
                "     "
        });
        this.addCharacter('C', new String[] {
                "XXXXX",
                "X    ",
                "X    ",
                "X    ",
                "X    ",
                "X    ",
                "XXXXX",
                "     "
        });
        this.addCharacter('D', new String[] {
                "XXXX ",
                "X   X",
                "X   X",
                "X   X",
                "X   X",
                "X   X",
                "XXXX ",
                "     "
        });
        this.addCharacter('E', new String[] {
                "XXXXX",
                "X    ",
                "X    ",
                "XXXXX",
                "X    ",
                "X    ",
                "XXXXX",
                "     "
        });
        this.addCharacter('F', new String[] {
                "XXXXX",
                "X    ",
                "X    ",
                "XXXXX",
                "X    ",
                "X    ",
                "X    ",
                "     "
        });
        this.addCharacter('G', new String[] {
                "XXXXX",
                "X    ",
                "X    ",
                "X    ",
                "X   X",
                "X   X",
                "XXXXX",
                "     "
        });
        this.addCharacter('H', new String[] {
                "X   X",
                "X   X",
                "X   X",
                "XXXXX",
                "X   X",
                "X   X",
                "X   X",
                "     "
        });
        this.addCharacter('I', new String[] {
                "XXXXX",
                "  X  ",
                "  X  ",
                "  X  ",
                "  X  ",
                "  X  ",
                "XXXXX",
                "     "
        });this.addCharacter('J', new String[] {
                "   XX",
                "    X",
                "    X",
                "    X",
                "    X",
                "X   X",
                " XXX ",
                "     "
        });
        this.addCharacter('K', new String[] {
                "X   X",
                "X   X",
                "X   X",
                "XXXX ",
                "X   X",
                "X   X",
                "X   X",
                "     "
        });
        this.addCharacter('L', new String[] {
                "X    ",
                "X    ",
                "X    ",
                "X    ",
                "X    ",
                "X    ",
                "XXXXX",
                "     "
        });
        this.addCharacter('M', new String[] {
                "X   X",
                "XX XX",
                "X X X",
                "X   X",
                "X   X",
                "X   X",
                "X   X",
                "     "
        });
        this.addCharacter('N', new String[] {
                "X   X",
                "X   X",
                "XX  X",
                "X X X",
                "X  XX",
                "X   X",
                "X   X",
                "     "
        });
        this.addCharacter('O', new String[] {
                "XXXXX",
                "X   X",
                "X   X",
                "X   X",
                "X   X",
                "X   X",
                "XXXXX",
                "     "
        });
        this.addCharacter('P', new String[] {
                "XXXXX",
                "X   X",
                "X   X",
                "XXXXX",
                "X    ",
                "X    ",
                "X    ",
                "     "
        });
        this.addCharacter('Q', new String[] {
                "XXXXX",
                "X   X",
                "X   X",
                "X   X",
                "X   X",
                "X  X ",
                "XXX X",
                "     "
        });
        this.addCharacter('R', new String[] {
                "XXXX ",
                "X   X",
                "XXXX ",
                "X   X",
                "X   X",
                "X   X",
                "X   X",
                "     "
        });
        this.addCharacter('S', new String[] {
                "XXXXX",
                "X    ",
                "X    ",
                "XXXXX",
                "    X",
                "    X",
                "XXXXX",
                "     "
        });
        this.addCharacter('T', new String[] {
                "XXXXX",
                "  X  ",
                "  X  ",
                "  X  ",
                "  X  ",
                "  X  ",
                "  X  ",
                "     "
        });
        this.addCharacter('U', new String[] {
                "X   X",
                "X   X",
                "X   X",
                "X   X",
                "X   X",
                "X   X",
                "XXXXX",
                "     "
        });
        this.addCharacter('V', new String[] {
                "X   X",
                "X   X",
                "X   X",
                "X   X",
                "X   X",
                " X X ",
                "  X  ",
                "     "
        });
        this.addCharacter('W', new String[] {
                "X   X",
                "X   X",
                "X   X",
                "X   X",
                "X X X",
                "X X X",
                "XX XX",
                "     "
        });
        this.addCharacter('X', new String[] {
                "X   X",
                "X   X",
                " X X ",
                "  X  ",
                " X X ",
                "X   X",
                "X   X",
                "     "
        });
        this.addCharacter('Y', new String[] {
                "X   X",
                " X X ",
                "  X  ",
                "  X  ",
                "  X  ",
                "  X  ",
                "  X  ",
                "     "
        });
        this.addCharacter('Z', new String[] {
                "XXXXX",
                "    X",
                "   X ",
                "  X  ",
                " X   ",
                "X    ",
                "XXXXX",
                "     "
        });
        this.addCharacter('a', new String[] {
                "     ",
                "     ",
                " XXX ",
                "    X",
                " XXXX",
                "X   X",
                " XXXX",
                "     "
        });
        this.addCharacter('b', new String[] {
                "     ",
                "X    ",
                "X    ",
                "XXXXX",
                "X   X",
                "X   X",
                "XXXXX",
                "     "
        });
        this.addCharacter('c', new String[] {
                "    ",
                "    ",
                "XXXX",
                "X   ",
                "X   ",
                "X   ",
                "XXXX",
                "    "
        });
        this.addCharacter('d', new String[] {
                "     ",
                "    X",
                "    X",
                "XXXXX",
                "X   X",
                "X   X",
                "XXXXX",
                "     "
        });
        this.addCharacter('e', new String[] {
                "     ",
                "     ",
                " XXX ",
                "X   X",
                "XXXX ",
                "X    ",
                " XXXX",
                "     "
        });
        this.addCharacter('f', new String[] {
                "   ",
                "  X",
                " X ",
                "XXX",
                " X ",
                " X ",
                " X ",
                "   "
        });
        this.addCharacter('g', new String[] {
                "     ",
                "     ",
                " XXXX",
                "X   X",
                " XXXX",
                "    X",
                " XXX ",
                "     "
        });
        this.addCharacter('h', new String[] {
                "     ",
                "X    ",
                "X    ",
                "XXXXX",
                "X   X",
                "X   X",
                "X   X",
                "     "
        });
        this.addCharacter('i', new String[] {
                " ",
                "X",
                " ",
                "X",
                "X",
                "X",
                "X",
                " "
        });
        this.addCharacter('j', new String[] {
                "  ",
                " X",
                "  ",
                "XX",
                " X",
                " X",
                "X ",
                "  "
        });
        this.addCharacter('k', new String[] {
                "    ",
                "    ",
                "X  X",
                "X  X",
                "XXX ",
                "X  X",
                "X  X",
                "    "
        });
        this.addCharacter('l', new String[] {
                " ",
                "X",
                "X",
                "X",
                "X",
                "X",
                "X",
                " "
        });
        this.addCharacter('m', new String[] {
                "     ",
                "     ",
                "XX X ",
                "X X X",
                "X X X",
                "X   X",
                "X   X",
                "     "
        });
        this.addCharacter('n', new String[] {
                "     ",
                "     ",
                "XXXX ",
                "X   X",
                "X   X",
                "X   X",
                "X   X",
                "     "
        });
        this.addCharacter('o', new String[] {
                "    ",
                "    ",
                "XXXX",
                "X  X",
                "X  X",
                "X  X",
                "XXXX",
                "    "
        });
        this.addCharacter('p', new String[] {
                "    ",
                "    ",
                "XXXX",
                "X  X",
                "XXXX",
                "X   ",
                "X   ",
                "    "
        });
        this.addCharacter('q', new String[] {
                "   ",
                "   ",
                " XX",
                "X X",
                "XXX",
                "  X",
                "  X",
                "   "
        });
        this.addCharacter('r', new String[] {
                "   ",
                "   ",
                "XX ",
                "X X",
                "X  ",
                "X  ",
                "X  ",
                "   "
        });
        this.addCharacter('s', new String[] {
                "   ",
                "   ",
                "XXX",
                "X  ",
                "XXX",
                "  X",
                "XXX",
                "   "
        });
        this.addCharacter('t', new String[] {
                "   ",
                "   ",
                " X ",
                "XXX",
                " X ",
                " X ",
                " X ",
                "   "
        });
        this.addCharacter('u', new String[] {
                "     ",
                "     ",
                "X   X",
                "X   X",
                "X   X",
                "X   X",
                "XXXXX",
                "     "
        });
        this.addCharacter('v', new String[] {
                "     ",
                "     ",
                "X   X",
                "X   X",
                " X X ",
                " X X ",
                "  X  ",
                "     "
        });
        this.addCharacter('w', new String[] {
                "     ",
                "     ",
                "X   X",
                "X   X",
                "X   X",
                "X X X",
                "XX XX",
                "     ",
                "     "
        });
        this.addCharacter('x', new String[] {
                "     ",
                "     ",
                "X   X",
                " X X ",
                "  X  ",
                " X X ",
                "X   X",
                "     "
        });
        this.addCharacter('y', new String[] {
                "     ",
                "     ",
                "X   X",
                "X   X",
                "XXXXX",
                "    X",
                "XXXX ",
                "     "
        });
        this.addCharacter('z', new String[] {
                "     ",
                "     ",
                "XXXXX",
                "   X ",
                "  X  ",
                " X   ",
                "XXXXX",
                "     "
        });

        this.addCharacter('!', new String[] {
                "X",
                "X",
                "X",
                "X",
                "X",
                " ",
                "X",
                " "
        });
        this.addCharacter('/', new String[] {
                "    X",
                "   X ",
                "   X ",
                "  X  ",
                " X   ",
                " X   ",
                "X    ",
                "     "
        });
        this.addCharacter(' ', new String[] {
                "  ",
                "  ",
                "  ",
                "  ",
                "  ",
                "  ",
                "  ",
                "  "
        });
        this.addCharacter(',', new String[] {
                " ",
                " ",
                " ",
                " ",
                " ",
                " ",
                "X",
                "X"
        });
        this.addCharacter('.', new String[] {
                " ",
                " ",
                " ",
                " ",
                " ",
                " ",
                "X",
                " "
        });
        this.addCharacter('?', new String[] {
                "XXX",
                "  X",
                "  X",
                " XX",
                " X ",
                " X ",
                "   ",
                " X "
        });
        this.addCharacter('+', new String[] {
                "     ",
                "  X  ",
                "  X  ",
                "XXXXX",
                "  X  ",
                "  X  ",
                "     ",
                "     "
        });
        this.addCharacter('-', new String[] {
                "     ",
                "     ",
                "     ",
                "XXXXX",
                "     ",
                "     ",
                "     ",
                "     "
        });
        this.addCharacter(':', new String[] {
                " ",
                "X",
                " ",
                " ",
                " ",
                " ",
                "X",
                " "
        });
        this.addCharacter(';', new String[] {
                " ",
                "X",
                " ",
                " ",
                " ",
                " ",
                "X",
                "X"
        });

        this.addCharacter('1', new String[] {
                " X ",
                "XX ",
                " X ",
                " X ",
                " X ",
                " X ",
                "XXX",
                "   "
        });
        this.addCharacter('2', new String[] {
                "XXXXX",
                "    X",
                "    X",
                "    X",
                " XXX ",
                "X    ",
                "XXXXX",
                "     "
        });
        this.addCharacter('3', new String[] {
                "XXXXX",
                "    X",
                "    X",
                "XXXXX",
                "    X",
                "    X",
                "XXXXX",
                "     "
        });
        this.addCharacter('4', new String[] {
                "X   X",
                "X   X",
                "X   X",
                "XXXXX",
                "    X",
                "    X",
                "    X",
                "     "
        });
        this.addCharacter('5', new String[] {
                "XXXXX",
                "X    ",
                "XXXX ",
                "    X",
                "    X",
                "    X",
                "XXXX ",
                "     "
        });
        this.addCharacter('6', new String[] {
                " XXXX",
                "X    ",
                "X    ",
                "XXXX ",
                "X   X",
                "X   X",
                "XXXXX",
                "     "
        });
        this.addCharacter('7', new String[] {
                "XXXXX",
                "    X",
                "   X ",
                "   X ",
                "  X  ",
                "  X  ",
                " X   ",
                "     "
        });
        this.addCharacter('8', new String[] {
                " XXX ",
                "X   X",
                "X   X",
                " XXX ",
                "X   X",
                "X   X",
                " XXX ",
                "     "
        });
        this.addCharacter('9', new String[] {
                "XXXXX",
                "X   X",
                "X   X",
                " XXXX",
                "    X",
                "    X",
                "XXXX ",
                "     "
        });
        this.addCharacter('0', new String[] {
                " XXX ",
                "X   X",
                "X  XX",
                "X X X",
                "XX  X",
                "X   X",
                " XXX ",
                "     "
        });
        this.addCharacter('_', new String[] {
                "     ",
                "     ",
                "     ",
                "     ",
                "     ",
                "     ",
                "     ",
                "XXXXX"
        });
        this.addCharacter('˅', new String[] {
                "     ",
                "     ",
                "     ",
                "     ",
                "X   X",
                " X X ",
                "  X  ",
                "     "
        });
        this.addCharacter('˄', new String[] {
                "  X  ",
                " X X ",
                "X   X",
                "     ",
                "     ",
                "     ",
                "     ",
                "     "
        });
        this.addCharacter('<', new String[] {
                "   ",
                "  X",
                " X ",
                "X  ",
                " X ",
                "  X",
                "   ",
                "   "
        });
        this.addCharacter('>', new String[] {
                "   ",
                "X  ",
                " X ",
                "  X",
                " X ",
                "X  ",
                "   ",
                "   "
        });
        this.addCharacter('|', new String[] {
                "X",
                "X",
                "X",
                "X",
                "X",
                "X",
                "X",
                " "
        });
    }
}