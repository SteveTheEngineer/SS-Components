package me.ste.stevesseries.components.component.configuration.element.builtin.number;

import java.math.BigDecimal;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class BigDecimalConfigurationElement extends NumberConfigurationElement<BigDecimal> {
    public BigDecimalConfigurationElement(String name, BigDecimal stepSmall, BigDecimal stepLarge, Supplier<BigDecimal> valueSupplier, Consumer<BigDecimal> valueConsumer) {
        super(name, stepSmall, stepLarge, valueSupplier, valueConsumer);
    }

    @Override
    public BigDecimal addNumbers(BigDecimal a, BigDecimal b) {
        return a.add(b);
    }

    @Override
    public BigDecimal subtractNumbers(BigDecimal a, BigDecimal b) {
        return a.subtract(b);
    }
}