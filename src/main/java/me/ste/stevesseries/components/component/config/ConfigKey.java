package me.ste.stevesseries.components.component.config;

import java.util.Objects;

public class ConfigKey {
    private final String name;
    private final ConfigType type;

    public ConfigKey(String name, ConfigType type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return this.name;
    }

    public ConfigType getType() {
        return this.type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        ConfigKey that = (ConfigKey) o;
        return Objects.equals(this.name, that.name) && this.type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type);
    }
}