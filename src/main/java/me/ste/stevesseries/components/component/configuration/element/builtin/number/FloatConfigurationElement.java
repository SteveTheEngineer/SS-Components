package me.ste.stevesseries.components.component.configuration.element.builtin.number;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class FloatConfigurationElement extends NumberConfigurationElement<Float> {
    public FloatConfigurationElement(String name, float stepSmall, float stepLarge, Supplier<Float> valueSupplier, Consumer<Float> valueConsumer) {
        super(name, stepSmall, stepLarge, valueSupplier, valueConsumer);
    }

    @Override
    public Float addNumbers(Float a, Float b) {
        if(a > 0) {
            return a + Math.min(Float.MAX_VALUE - a, b);
        } else {
            return a + b;
        }
    }

    @Override
    public Float subtractNumbers(Float a, Float b) {
        if(a < 0) {
            return a - Math.min(-(Float.MIN_VALUE - a), b);
        } else {
            return a - b;
        }
    }
}