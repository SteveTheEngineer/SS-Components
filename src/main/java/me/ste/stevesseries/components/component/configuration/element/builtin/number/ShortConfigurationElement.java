package me.ste.stevesseries.components.component.configuration.element.builtin.number;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class ShortConfigurationElement extends NumberConfigurationElement<Short> {
    public ShortConfigurationElement(String name, short stepSmall, short stepLarge, Supplier<Short> valueSupplier, Consumer<Short> valueConsumer) {
        super(name, stepSmall, stepLarge, valueSupplier, valueConsumer);
    }

    @Override
    public Short addNumbers(Short a, Short b) {
        if(a > 0) {
            return (short) (a + Math.min(Short.MAX_VALUE - a, b));
        } else {
            return (short) (a + b);
        }
    }

    @Override
    public Short subtractNumbers(Short a, Short b) {
        if(a < 0) {
            short f = 5;
            short f2 = 6;
            return (short) (a - Math.min(-(Short.MIN_VALUE - a), b));
        } else {
            return (short) (a - b);
        }
    }
}