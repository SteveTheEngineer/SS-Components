package me.ste.stevesseries.components.component;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.ste.stevesseries.base.GenericUtil;
import me.ste.stevesseries.components.Components;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.logging.Level;

public class ComponentManager {
    private static ComponentManager instance;
    private final Components plugin = Components.getPlugin(Components.class);
    private final FileConfiguration configRoot = plugin.getConfig();
    private final Map<Class, ComponentFactory> componentFactories = new LinkedHashMap<>();
    private final Map<Class, String> componentFriendlyNames = new HashMap<>();
    private final Map<UUID, ComponentLocation> selectedComponents = new HashMap<>();

    private final List<Component> components = new LinkedList<>();

    private ComponentManager() {}

    /**
     * Get the {@link ComponentManager} instance
     * @return the instance
     */
    public static ComponentManager getInstance() {
        if(ComponentManager.instance == null) {
            ComponentManager.instance = new ComponentManager();
        }
        return ComponentManager.instance;
    }

    /**
     * Save the components to disk
     */
    public void save() {
        File componentsFile = this.plugin.getDataFolder().toPath().resolve("config.yml").toFile();
        if(!componentsFile.isFile()) {
            try {
                if(!componentsFile.createNewFile()) {
                    return;
                }
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
        try {
            this.configRoot.load(componentsFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }

        List<String> componentss = new LinkedList<>();
        for(Component component : this.components) {
            JsonObject saved = new JsonObject();
            saved.addProperty("class", component.getClass().getName());
            saved.add("location", GenericUtil.locationToJson(component.getLocation()));
            saved.addProperty("face", component.getFace().name());
            try {
                JsonObject data = new JsonObject();
                component.save(data);
                saved.add("data", data);
                componentss.add(saved.toString());
            } catch(Throwable t) {
                this.plugin.getLogger().log(Level.SEVERE, String.format("Unable to save component's data (%s)", component.getClass().getSimpleName()));
                t.printStackTrace();
            }
        }
        this.configRoot.set("components", componentss);

        ConfigurationSection selectedComponents = this.configRoot.getConfigurationSection("selectedComponents");
        if(selectedComponents == null) {
            selectedComponents = this.configRoot.createSection("selectedComponents");
        }

        for(Map.Entry<UUID, ComponentLocation> location : this.selectedComponents.entrySet()) {
            selectedComponents.set(location.getKey().toString(), location.getValue().saveToJson().toString());
        }

        try {
            this.configRoot.save(componentsFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Load the components from disk
     */
    public void load() {
        File componentsFile = this.plugin.getDataFolder().toPath().resolve("config.yml").toFile();
        if(!componentsFile.isFile()) {
            return;
        }
        try {
            this.configRoot.load(componentsFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
        this.components.clear();
        if(this.configRoot.isList("components")) {
            for(String json : this.configRoot.getStringList("components")) {
                JsonObject object = new JsonParser().parse(json).getAsJsonObject();
                try {
                    Class clazz = Class.forName(object.get("class").getAsString());
                    Location location = GenericUtil.locationFromJson(object.getAsJsonObject("location"));
                    BlockFace face = BlockFace.valueOf(object.get("face").getAsString());
                    Component component = this.componentFactories.get(clazz).create(location, face, null);
                    if(component != null) {
                        try {
                            component.load(object.get("data").getAsJsonObject());
                            components.add(component);
                        } catch(Throwable t) {
                            this.plugin.getLogger().log(Level.SEVERE, String.format("Unable to load component's data (%s)", clazz.getSimpleName()));
                            t.printStackTrace();
                        }
                    } else {
                        this.plugin.getLogger().log(Level.SEVERE, String.format("Unable to load component's data (%s), as the component factory creates a null object", clazz.getSimpleName()));
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        this.selectedComponents.clear();
        if(this.configRoot.isConfigurationSection("selectedComponents")) {
            ConfigurationSection section = this.configRoot.getConfigurationSection("selectedComponents");
            for(String uuidString : section.getKeys(false)) {
                this.selectedComponents.put(UUID.fromString(uuidString), ComponentLocation.loadFromJson(new JsonParser().parse(section.getString(uuidString)).getAsJsonObject()));
            }
        }
    }

    /**
     * @deprecated for internal use only
     */
    @Deprecated
    @SafeVarargs
    public final void register(Class<? extends Component>... componentClasses) {
        for(Class<? extends Component> clazz : componentClasses) {
            try {
                Constructor<? extends Component> constructor = clazz.getConstructor(Location.class, BlockFace.class);
                this.componentFactories.put(clazz, (location, face, player) -> {
                    try {
                        return constructor.newInstance(location, face);
                    } catch (InstantiationException | InvocationTargetException | IllegalAccessException e) {
                        e.printStackTrace();
                        return null;
                    }
                });
                if(clazz.isAnnotationPresent(ComponentFriendlyName.class)) {
                    this.componentFriendlyNames.put(clazz, clazz.getAnnotation(ComponentFriendlyName.class).value());
                }
            } catch (NoSuchMethodException e) {
                this.plugin.getLogger().log(Level.SEVERE, String.format("Could not find a Location, BlockFace constructor for component %s", clazz.getName()));
            }
        }
    }

    /**
     * Create a component at the specified location
     * @param componentClass the class of the component to create
     * @param location the location of the component
     * @param face the facing direction of the component
     * @param itemFrame the item frame used to create the component
     * @param player the player who created the component
     * @return created component
     */
    public Component createComponent(Class<? extends Component> componentClass, Location location, BlockFace face, ItemFrame itemFrame, @Nullable Player player) {
        ComponentFactory factory = this.componentFactories.get(componentClass);
        if(factory == null) {
            return null;
        }

        Component component = factory.create(location, face, player);
        if(component == null) {
            this.plugin.getLogger().log(Level.SEVERE, String.format("Unable to create a component: %s", componentClass.getName()));
            return null;
        }
        component.create(itemFrame);
        this.components.add(component);
        return component;
    }

    /**
     * Get the component at the specified location and facing direction
     * @param location the location
     * @param face the facing direction
     * @return the component
     */
    public Component getComponent(Location location, BlockFace face) {
        for(Component component : this.components) {
            if(component.getLocation().getBlock().getLocation().equals(location.getBlock().getLocation()) && component.getFace().equals(face)) {
                return component;
            }
        }
        return null;
    }

    /**
     * Get the component at the specified location
     * @param location the location
     * @return the component
     */
    public Component getComponent(Location location) {
        for(Component component : this.components) {
            if(component.getLocation().getBlock().getLocation().equals(location.getBlock().getLocation())) {
                return component;
            }
        }
        return null;
    }

    /**
     * Remove the specified component
     * @param component the component
     */
    public void removeComponent(Component component) {
        if (this.components.contains(component)) {
            component.handleRemoval();
            this.components.remove(component);
        }
    }

    /**
     * Get the selected component of the specified player
     * @param player the player
     * @return the component
     */
    public Component getSelectedComponent(Player player) {
        ComponentLocation location = this.selectedComponents.get(player.getUniqueId());
        if(location != null) {
            return location.getComponent();
        }
        return null;
    }

    /**
     * Get the selected component location of the specified player
     * @param player the player
     * @return the component
     */
    public ComponentLocation getSelectedComponentLocation(Player player) {
        return this.selectedComponents.get(player.getUniqueId());
    }

    /**
     * Set selected component of the specified player
     * @param player the player
     * @param location location of the component
     */
    public void setSelectedComponent(Player player, ComponentLocation location) {
        if(location != null) {
            this.selectedComponents.put(player.getUniqueId(), location);
        } else {
            this.selectedComponents.remove(player.getUniqueId());
        }
    }


    /**
     * Set selected component of the specified player
     * @param player the player
     * @param component the component
     */
    public void setSelectedComponent(Player player, Component component) {
        this.setSelectedComponent(player, new ComponentLocation(component.getLocation(), component.getFace()));
    }

    /**
     * @deprecated for internal use only
     */
    @Deprecated
    public Map<Class, ComponentFactory> getComponentFactories() {
        return this.componentFactories;
    }

    /**
     * @deprecated for internal use only
     */
    @Deprecated
    public List<Component> getComponents() {
        return this.components;
    }

    /**
     * Get the friendly name of the specified component class
     * @param clazz component class
     * @return the friendly name
     */
    public String getFriendlyName(Class<? extends Component> clazz) {
        return this.componentFriendlyNames.get(clazz);
    }

    /**
     * Get the display name of the specified component class
     * @param clazz component class
     * @return the friendly name, or it's simple class name if it doesn't have one
     */
    public String getDisplayName(Class<? extends Component> clazz) {
        if(this.componentFriendlyNames.containsKey(clazz)) {
            return this.componentFriendlyNames.get(clazz);
        } else {
            return clazz.getSimpleName();
        }
    }
}