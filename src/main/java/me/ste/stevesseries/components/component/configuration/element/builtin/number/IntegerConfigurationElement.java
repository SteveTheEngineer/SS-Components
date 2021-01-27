package me.ste.stevesseries.components.component.configuration.element.builtin.number;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class IntegerConfigurationElement extends NumberConfigurationElement<Integer> {
    public IntegerConfigurationElement(String name, int stepSmall, int stepLarge, Supplier<Integer> valueSupplier, Consumer<Integer> valueConsumer) {
        super(name, stepSmall, stepLarge, valueSupplier, valueConsumer);
    }

    @Override
    public Integer addNumbers(Integer a, Integer b) {
        if(a > 0) {
            return a + Math.min(Integer.MAX_VALUE - a, b);
        } else {
            return a + b;
        }
    }

    @Override
    public Integer subtractNumbers(Integer a, Integer b) {
        if(a < 0) {
            return a - Math.min(-(Integer.MIN_VALUE - a), b);
        } else {
            return a - b;
        }
    }
}