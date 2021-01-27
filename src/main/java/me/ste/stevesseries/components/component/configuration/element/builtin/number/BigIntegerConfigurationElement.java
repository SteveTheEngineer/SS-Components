package me.ste.stevesseries.components.component.configuration.element.builtin.number;

import java.math.BigInteger;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class BigIntegerConfigurationElement extends NumberConfigurationElement<BigInteger> {
    public BigIntegerConfigurationElement(String name, BigInteger stepSmall, BigInteger stepLarge, Supplier<BigInteger> valueSupplier, Consumer<BigInteger> valueConsumer) {
        super(name, stepSmall, stepLarge, valueSupplier, valueConsumer);
    }

    @Override
    public BigInteger addNumbers(BigInteger a, BigInteger b) {
        return a.add(b);
    }

    @Override
    public BigInteger subtractNumbers(BigInteger a, BigInteger b) {
        return a.subtract(b);
    }
}