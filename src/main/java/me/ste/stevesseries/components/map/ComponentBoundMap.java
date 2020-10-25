package me.ste.stevesseries.components.map;

import me.ste.stevesseries.components.component.Component;

/**
 * A map with a component bound to it
 * @param <T> the component type
 */
public abstract class ComponentBoundMap<T extends Component> extends Map {
    protected final T component;

    public ComponentBoundMap(T component, boolean contextual) {
        super(component.getLocation(), component.getFace().getOppositeFace(), contextual);
        this.component = component;
    }

    public ComponentBoundMap(T component) {
        this(component, false);
    }
}