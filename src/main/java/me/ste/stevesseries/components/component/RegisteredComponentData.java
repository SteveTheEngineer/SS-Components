package me.ste.stevesseries.components.component;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.Plugin;

import javax.naming.Name;
import java.lang.reflect.InvocationTargetException;

/**
 * The data of a registered component
 */
public class RegisteredComponentData {
    private final Plugin plugin;
    private final NamespacedKey identifier;
    private final Class<? extends Component> componentClass;
    private final Material displayMaterial;
    private final String displayName;

    public RegisteredComponentData(Plugin plugin, NamespacedKey identifier, Class<? extends Component> componentClass, Material displayMaterial, String displayName) {
        this.plugin = plugin;
        this.identifier = identifier;
        this.componentClass = componentClass;
        this.displayMaterial = displayMaterial;
        this.displayName = displayName;
    }

    /**
     * Get the plugin that owns the component
     * @return plugin that owns the component
     */
    public Plugin getPlugin() {
        return this.plugin;
    }

    /**
     * Get the unique ID of the component
     * @return unique ID
     */
    public NamespacedKey getIdentifier() {
        return this.identifier;
    }

    /**
     * Get the class of the component
     * @return class of the component
     */
    public Class<? extends Component> getComponentClass() {
        return this.componentClass;
    }

    /**
     * Get the display material of the component
     * @return display material
     */
    public Material getDisplayMaterial() {
        return this.displayMaterial;
    }

    /**
     * Get the display name of the component
     * @return display name
     */
    public String getDisplayName() {
        return this.displayName;
    }

    /**
     * Create a new instance of the component class
     * @param location component location
     * @throws IllegalStateException if the registered component doesn't have a ComponentLocation constructor. This shouldn't normally be thrown, as it's checked during the component registration
     * @return component instance
     */
    public Component createInstance(ComponentLocation location) {
        try {
            return this.componentClass.getConstructor(ComponentLocation.class).newInstance(location);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new IllegalStateException("The registered component " + this.identifier + " doesn't have a ComponentLocation constructor");
        }
    }
}