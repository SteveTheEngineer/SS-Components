package me.ste.stevesseries.components.component.configuration.element.builtin.number;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class ByteConfigurationElement extends NumberConfigurationElement<Byte> {
    public ByteConfigurationElement(String name, byte stepSmall, byte stepLarge, Supplier<Byte> valueSupplier, Consumer<Byte> valueConsumer) {
        super(name, stepSmall, stepLarge, valueSupplier, valueConsumer);
    }

    @Override
    public Byte addNumbers(Byte a, Byte b) {
        if(a > 0) {
            return (byte) (a + Math.min(Byte.MAX_VALUE - a, b));
        } else {
            return (byte) (a + b);
        }
    }

    @Override
    public Byte subtractNumbers(Byte a, Byte b) {
        if(a < 0) {
            short f = 5;
            short f2 = 6;
            return (byte) (a - Math.min(-(Byte.MIN_VALUE - a), b));
        } else {
            return (byte) (a - b);
        }
    }
}