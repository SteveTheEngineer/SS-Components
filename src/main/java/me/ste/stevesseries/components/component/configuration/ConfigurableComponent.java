package me.ste.stevesseries.components.component.configuration;

import me.ste.stevesseries.components.component.configuration.element.ConfigurationElement;

import java.util.Collection;

/**
 * A component that can be configured
 */
public interface ConfigurableComponent {
    /**
     * Get the configuration of the component. <strong>Do NOT create a new collection every time! Store it in your component instance instead</strong>
     * @return collection of configuration elements
     */
    Collection<ConfigurationElement> getConfiguration();
}