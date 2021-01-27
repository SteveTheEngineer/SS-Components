package me.ste.stevesseries.components.component.configuration.element.builtin.number;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class LongConfigurationElement extends NumberConfigurationElement<Long> {
    public LongConfigurationElement(String name, long stepSmall, long stepLarge, Supplier<Long> valueSupplier, Consumer<Long> valueConsumer) {
        super(name, stepSmall, stepLarge, valueSupplier, valueConsumer);
    }

    @Override
    public Long addNumbers(Long a, Long b) {
        if(a > 0) {
            return a + Math.min(Long.MAX_VALUE - a, b);
        } else {
            return a + b;
        }
    }

    @Override
    public Long subtractNumbers(Long a, Long b) {
        if(a < 0) {
            return a - Math.min(-(Long.MIN_VALUE - a), b);
        } else {
            return a - b;
        }
    }
}