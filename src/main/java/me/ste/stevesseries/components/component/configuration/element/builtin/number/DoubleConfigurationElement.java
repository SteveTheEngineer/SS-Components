package me.ste.stevesseries.components.component.configuration.element.builtin.number;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class DoubleConfigurationElement extends NumberConfigurationElement<Double> {
    public DoubleConfigurationElement(String name, double stepSmall, double stepLarge, Supplier<Double> valueSupplier, Consumer<Double> valueConsumer) {
        super(name, stepSmall, stepLarge, valueSupplier, valueConsumer);
    }

    @Override
    public Double addNumbers(Double a, Double b) {
        if(a > 0) {
            return a + Math.min(Double.MAX_VALUE - a, b);
        } else {
            return a + b;
        }
    }

    @Override
    public Double subtractNumbers(Double a, Double b) {
        if(a < 0) {
            return a - Math.min(-(Double.MIN_VALUE - a), b);
        } else {
            return a - b;
        }
    }
}