package me.ste.stevesseries.components.component;

import me.ste.stevesseries.components.Components;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.ItemFrame;
import org.bukkit.plugin.Plugin;

import java.util.*;

/**
 * This manager is responsible to managing the components
 */
public class ComponentManager {
    private static final Components PLUGIN = Components.getPlugin(Components.class);

    public static final Map<NamespacedKey, RegisteredComponentData> REGISTERED_COMPONENTS = new HashMap<>();
    public static final Set<Component> EXISTING_COMPONENTS = new HashSet<>();

    private ComponentManager() {

    }

    /**
     * Register a new component. <strong>The component MUST have a ComponentLocation constructor</strong>
     * @param plugin plugin that owns the component
     * @param id component unique ID
     * @param componentClass component class
     * @param displayName component display name. Defaults to the class name if null
     * @param displayMaterial component display material. Defaults to {@link Material#EMERALD_BLOCK} if null
     * @throws IllegalArgumentException if a component with the same identifier is already registered, or the class doesn't have a ComponentLocation constructor
     * @return registered component data
     */
    public static RegisteredComponentData register(Plugin plugin, String id, Class<? extends Component> componentClass, String displayName, Material displayMaterial) {
        NamespacedKey identifier = new NamespacedKey(plugin, id);

        if(ComponentManager.PLUGIN.isEnabled()) {
            throw new IllegalStateException("Attempted to register component " + identifier + ", but the components plugin was already enabled! Register your components in the onLoad method of your plugin class!");
        }

        if(ComponentManager.REGISTERED_COMPONENTS.containsKey(identifier)) {
            throw new IllegalArgumentException("A component with the same identifier is already registered: " + identifier);
        }
        try {
            componentClass.getDeclaredConstructor(ComponentLocation.class);
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException("The component " + componentClass.getName() + " doesn't have a ComponentLocation constructor");
        }

        RegisteredComponentData data = new RegisteredComponentData(plugin, identifier, componentClass, displayMaterial != null ? displayMaterial : Material.EMERALD_BLOCK, displayName != null ? displayName : componentClass.getSimpleName());
        ComponentManager.REGISTERED_COMPONENTS.put(identifier, data);
        return data;
    }

    /**
     * Same as {@link ComponentManager#register(Plugin, String, Class, String, Material)}, but uses the default values for display name and display material
     * @param plugin plugin that owns the component
     * @param id component unique ID
     * @param componentClass component class
     * @return registered component data
     */
    public static RegisteredComponentData register(Plugin plugin, String id, Class<? extends Component> componentClass) {
        return ComponentManager.register(plugin, id, componentClass, null, null);
    }

    /**
     * Get the component at the provided location
     * @param location component location
     * @return component, or null if there's none
     */
    public static Component getComponentAt(ComponentLocation location) {
        for(Component component : ComponentManager.getComponentsAt(location.getLocation())) {
            if(component.getLocation().getDirection() == location.getDirection()) {
                return component;
            }
        }
        return null;
    }

    /**
     * Get the components at the provided non-precise location
     * @param location non-precise location
     * @return list of components
     */
    public static List<Component> getComponentsAt(Location location) {
        List<Component> components = new ArrayList<>();
        for(Component component : ComponentManager.EXISTING_COMPONENTS) {
            Location location2 = component.getLocation().getLocation();
            if(location.getBlockX() == location2.getBlockX() && location.getBlockY() == location2.getBlockY() && location.getBlockZ() == location2.getBlockZ()) {
                components.add(component);
            }
        }
        return components;
    }

    /**
     * Create a new component in place of the specified item frame
     * @param component component unique ID
     * @param frame item frame. This is required
     * @return new component instance
     */
    public static Component create(NamespacedKey component, ItemFrame frame) {
        ComponentLocation location = new ComponentLocation(frame.getLocation().getBlock().getLocation(), frame.getFacing());
        RegisteredComponentData data = ComponentManager.REGISTERED_COMPONENTS.get(component);
        if(data == null) {
            throw new IllegalArgumentException("The component " + component + " does not exist");
        }
        Component actualComponent = data.createInstance(location);
        actualComponent.onInitialization(frame);
        actualComponent.onCreation();
        ComponentManager.EXISTING_COMPONENTS.add(actualComponent);
        return actualComponent;
    }

    /**
     * Delete the specified component
     * @param component component to delete
     */
    public static void delete(Component component) {
        if(ComponentManager.exists(component)) {
            component.onDestruction();
            component.onDeletion();
            ComponentManager.EXISTING_COMPONENTS.remove(component);
        }
    }

    /**
     * Check whether the specified component exists in the world
     * @param component component to check
     * @return true, if the component exists in the world
     */
    public static boolean exists(Component component) {
        return ComponentManager.EXISTING_COMPONENTS.contains(component);
    }

    /**
     * Get the data of a registered component
     * @param componentId component unique ID
     * @return registered component data
     */
    public static RegisteredComponentData getRegisteredComponentData(NamespacedKey componentId) {
        return ComponentManager.REGISTERED_COMPONENTS.get(componentId);
    }

    /**
     * Get the data of a registered component by it's class
     * @param componentClass component class
     * @return first matched component data
     */
    public static RegisteredComponentData getRegisteredComponentData(Class<? extends Component> componentClass) {
        for(RegisteredComponentData data : ComponentManager.REGISTERED_COMPONENTS.values()) {
            if(data.getComponentClass().equals(componentClass)) {
                return data;
            }
        }
        return null;
    }
}