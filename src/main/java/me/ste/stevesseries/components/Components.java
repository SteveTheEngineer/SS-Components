package me.ste.stevesseries.components;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.ste.stevesseries.base.GenericUtil;
import me.ste.stevesseries.components.command.ComponentsCommand;
import me.ste.stevesseries.components.component.Component;
import me.ste.stevesseries.components.component.ComponentLocation;
import me.ste.stevesseries.components.component.ComponentManager;
import me.ste.stevesseries.components.component.RegisteredComponentData;
import me.ste.stevesseries.components.listener.ComponentBlockListener;
import me.ste.stevesseries.components.listener.ComponentListener;
import me.ste.stevesseries.components.listener.EntityRemovalListener;
import me.ste.stevesseries.components.listener.MapListener;
import me.ste.stevesseries.components.map.MapData;
import me.ste.stevesseries.components.map.MapManager;
import org.bukkit.*;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.ItemFrame;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.logging.Level;

public final class Components extends JavaPlugin {
    public static NamespacedKey DATA_KEY_COMPONENT_TOOL;

    private final Path dataFolder = this.getDataFolder().toPath();
    private final Path storageFolder = this.dataFolder.resolve("storage");
    private final Path selectedLocationsFile = this.storageFolder.resolve("selected_locations.json");
    private final Path componentsFile = this.storageFolder.resolve("components.json");
    private final Path componentsBackupFile = this.componentsFile.resolveSibling(this.componentsFile.getFileName() + ".backup");
    private final Path forceLoadFile = this.dataFolder.toAbsolutePath().getParent().getParent().resolve("_COMPONENTS_FORCE_LOAD");
    private final Path removedEntitiesFile = this.storageFolder.resolve("removed_entities.json");

    private final Path localeConfigurationFile = this.dataFolder.resolve("locale.yml");
    private final FileConfiguration localeConfiguration = new YamlConfiguration();

    @Override
    public void onLoad() {
        Components.DATA_KEY_COMPONENT_TOOL = new NamespacedKey(this, "component_tool");
    }

    @Override
    public void onEnable() {
        // Check for plugins that use the legacy API
        boolean legacyPlugins = false;
        for(Plugin plugin : Bukkit.getPluginManager().getPlugins()) { // Check for legacy plugins
            if(plugin.getResource("components.json") != null) {
                this.getLogger().severe("The plugin " + plugin.getDescription().getFullName() + " uses the legacy component registration system (components.json)!");
                legacyPlugins = true;
            }
        }

        if(legacyPlugins) {
            this.getLogger().severe("The server has been shutdown to prevent world corruption. Remove the listed plugins to proceed with the server launch");
            Bukkit.shutdown();
            return;
        }

        // Register all event listeners
        this.getServer().getPluginManager().registerEvents(new ComponentListener(this), this);
        this.getServer().getPluginManager().registerEvents(new ComponentBlockListener(), this);
        this.getServer().getPluginManager().registerEvents(new EntityRemovalListener(), this);
        this.getServer().getPluginManager().registerEvents(new MapListener(), this);

        // Start all the required tasks
        this.getServer().getScheduler().runTaskTimer(this, () -> {
            if(this.isEnabled()) {
                this.save();
            }
        }, 20L * 5L, 20L * 60L * 5L); // Run the save task every 5 minutes

        // Set the commands' executors
        this.getCommand("components").setExecutor(new ComponentsCommand(this));

        // Create the plugin data folder if it doesn't exist
        if(!Files.exists(this.dataFolder)) {
            try {
                Files.createDirectory(this.dataFolder);
            } catch (IOException e) {
                this.getLogger().log(Level.SEVERE, "Could not create the plugin data folder", e);
                this.setEnabled(false);
                return;
            }
        }

        // Create the storage folder if it doesn't exist
        if(!Files.exists(this.storageFolder)) {
            try {
                Files.createDirectory(this.storageFolder);
            } catch (IOException e) {
                this.getLogger().log(Level.SEVERE, "Could not create the storage folder", e);
                this.setEnabled(false);
                return;
            }
        }

        // Load the locale configuration
        if(!Files.exists(this.localeConfigurationFile)) {
            try {
                Files.copy(Objects.requireNonNull(this.getResource("locale.yml")), this.localeConfigurationFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            this.localeConfiguration.load(this.localeConfigurationFile.toFile());
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }

        // Load the data from the storage
        this.load();

        // Notify all components about the server load
        for(Component component : ComponentManager.EXISTING_COMPONENTS) {
            component.onLoad();
            component.onCreation();
        }

        // Reset all custom map item frame rotations
        for(World world : Bukkit.getWorlds()) {
            for(ItemFrame frame : world.getEntitiesByClass(ItemFrame.class)) {
                if(MapManager.getMapData(frame) != null) {
                    frame.setRotation(Rotation.NONE);
                }
            }
        }
    }

    @Override
    public void onDisable() {
        // Cancel all the tasks of the plugin
        this.getServer().getScheduler().cancelTasks(this);

        // Notify all components about the server shutdown
        for(Component component : ComponentManager.EXISTING_COMPONENTS) {
            component.onUnload();
            component.onDestruction();
        }

        // Save the data to the storage
        this.save();
    }

    /**
     * Check if the provided item is a component tool. This is separate from {@link ComponentManager}, because the component management is a part of the plugin itself rather than the provided API
     * @param stack item stack to check
     * @return true, if the provided item stack is a valid component tool
     */
    public static boolean isValidComponentTool(ItemStack stack) {
        if(stack != null && stack.getType() == Material.ITEM_FRAME && stack.hasItemMeta()) {
            Byte value = Objects.requireNonNull(stack.getItemMeta()).getPersistentDataContainer().get(Components.DATA_KEY_COMPONENT_TOOL, PersistentDataType.BYTE);
            return value != null && value != 0;
        } else {
            return false;
        }
    }

    public String getMessage(String key, Object... arguments) {
        if(this.localeConfiguration.isString(key)) {
            return ChatColor.translateAlternateColorCodes('&', String.format(Objects.requireNonNull(this.localeConfiguration.getString(key)), arguments));
        } else {
            return key;
        }
    }

    private void save() {
        this.saveSelectedLocations();
        this.saveRemovedEntities();
        this.saveComponents();
    }

    private void saveRemovedEntities() {
        JsonArray entities = new JsonArray();

        for(UUID entityId : EntityRemovalManager.REMOVE_ON_LOAD) {
            entities.add(entityId.toString());
        }

        try {
            Files.write(this.removedEntitiesFile, entities.toString().getBytes());
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private void saveComponents() {
        JsonArray components = new JsonArray();

        boolean errors = false;
        for(Component component : ComponentManager.EXISTING_COMPONENTS) {
            RegisteredComponentData registeredComponentData = ComponentManager.getRegisteredComponentData(component.getClass());
            ComponentLocation location = component.getLocation();
            Location bukkitLocation = location.getLocation();

            JsonObject object = new JsonObject();
            object.addProperty("id", registeredComponentData.getIdentifier().toString());
            object.add("location", component.getLocation().saveToJson());

            JsonObject data = new JsonObject();
            try {
                component.writeToJson(data);
            } catch(Throwable t) {
                this.getLogger().log(Level.SEVERE, String.format("An error occurred while saving the component %s (ID: %s. Class: %s) | %s @ %d, %d, %d. %s to disk. The component was forcefully deleted", registeredComponentData.getDisplayName(), registeredComponentData.getIdentifier().toString(), registeredComponentData.getComponentClass().getName(), bukkitLocation.getWorld().getName(), bukkitLocation.getBlockX(), bukkitLocation.getBlockY(), bukkitLocation.getBlockZ(), location.getDirection().name()), t);
                ComponentManager.EXISTING_COMPONENTS.remove(component);
                errors = true;
                continue;
            }
            object.add("data", data);

            components.add(object);
        }

        try {
            if(Files.exists(this.componentsFile)) {
                if(errors) {
                    Path backupFile = this.componentsBackupFile.resolveSibling(this.componentsBackupFile.getFileName() + "_" + System.currentTimeMillis());
                    this.getLogger().warning("There have been errors while saving the components. A backup of the current file has been created: " + backupFile.getFileName());
                    Files.copy(this.componentsFile, backupFile, StandardCopyOption.REPLACE_EXISTING);
                }
                Files.copy(this.componentsFile, this.componentsBackupFile, StandardCopyOption.REPLACE_EXISTING); // Backup the old file. If the server suddenly shuts down, and the file is not fully written to disk yet, the file will get corrupted. The file will be rewritten only after it has been backed up
            }
            Files.write(this.componentsFile, components.toString().getBytes());
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private void saveSelectedLocations() {
        JsonObject selectedLocations = new JsonObject();

        JsonObject blocks = new JsonObject();
        for(Map.Entry<UUID, Location> entry : SelectedLocationsManager.SELECTED_BLOCKS.entrySet()) {
            blocks.add(entry.getKey().toString(), GenericUtil.locationToJson(entry.getValue()));
        }
        selectedLocations.add("blocks", blocks);

        JsonObject components = new JsonObject();
        for(Map.Entry<UUID, ComponentLocation> entry : SelectedLocationsManager.SELECTED_COMPONENTS.entrySet()) {
            components.add(entry.getKey().toString(), entry.getValue().saveToJson());
        }
        selectedLocations.add("components", components);

        try {
            Files.write(this.selectedLocationsFile, selectedLocations.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void load() {
        this.loadSelectedLocations();
        this.loadComponents();
        this.loadRemovedEntities();
    }

    private void loadRemovedEntities() {
        JsonArray entities;
        if(Files.exists(this.removedEntitiesFile)) {
            try {
                entities = new JsonParser().parse(new String(Files.readAllBytes(this.removedEntitiesFile))).getAsJsonArray();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        } else {
            entities = new JsonArray();
        }

        EntityRemovalManager.REMOVE_ON_LOAD.clear();
        for(JsonElement element : entities) {
            EntityRemovalManager.REMOVE_ON_LOAD.add(UUID.fromString(element.getAsString()));
        }
    }

    private void loadComponents() {
        JsonArray components = null;
        if(Files.exists(this.componentsFile)) {
            try {
                components = new JsonParser().parse(new String(Files.readAllBytes(this.componentsFile))).getAsJsonArray();
            } catch (Exception e) {
                this.getLogger().log(Level.SEVERE, "The components.json file could not be loaded, it was probably corrupt", e);
                if(Files.exists(this.componentsBackupFile)) {
                    this.getLogger().info("The components.json.backup file has been found! Attempting to load...");
                    try {
                        components = new JsonParser().parse(new String(Files.readAllBytes(this.componentsBackupFile))).getAsJsonArray();
                    } catch(Exception e2) {
                        this.getLogger().log(Level.SEVERE, "The components.json.backup file could not be loaded, it was probably corrupt", e2);
                    }
                }
                if(components == null) {
                    this.getLogger().severe("The server has been shutdown to prevent world corruption. If you want to start the server deleting all the components, delete or move the " + this.componentsFile + " file");
                    Bukkit.shutdown();
                    return;
                }
            }
        } else {
            components = new JsonArray();
        }

        ComponentManager.EXISTING_COMPONENTS.clear();
        boolean errors = false;
        for(JsonElement element : components) {
            JsonObject object = element.getAsJsonObject();
            NamespacedKey id = GenericUtil.parseNamespacedKey(object.get("id").getAsString());
            RegisteredComponentData data = ComponentManager.getRegisteredComponentData(id);
            ComponentLocation location = ComponentLocation.loadFromJson(object.getAsJsonObject("location"));
            Location bukkitLocation = location.getLocation();

            if(data == null) {
                this.getLogger().severe(String.format("Attempted to load component ID %s | %s @ %d, %d, %d. %s from disk, but there wasn't a registered component with the matching ID!", id.toString(), bukkitLocation.getWorld().getName(), bukkitLocation.getBlockX(), bukkitLocation.getBlockY(), bukkitLocation.getBlockZ(), location.getDirection().name()));
                errors = true;
                continue;
            }

            Component component;
            try {
                component = data.createInstance(location);
                component.readFromJson(object.getAsJsonObject("data"));
            } catch(Throwable t) {
                this.getLogger().log(Level.SEVERE, String.format("An error occurred while loading the component %s (ID: %s. Class: %s) | %s @ %d, %d, %d. %s from disk. The component wasn't loaded", data.getDisplayName(), data.getIdentifier().toString(), data.getComponentClass().getName(), bukkitLocation.getWorld().getName(), bukkitLocation.getBlockX(), bukkitLocation.getBlockY(), bukkitLocation.getBlockZ(), location.getDirection().name()), t);
                errors = true;
                continue;
            }

            ComponentManager.EXISTING_COMPONENTS.add(component);
        }

        if(!Files.exists(this.forceLoadFile)) {
            if(errors) {
                this.getLogger().severe("The server has been shutdown to prevent world corruption. See the errors to find out which components could not be loaded. If it says that the component is not registered, make sure you have the plugin that provides the component (the plugin name is the namespace of the component id, for example ss-components:test's owning plugin would be ss-components). Make sure you have the latest version of that plugin, the problem might have been fixed in the latest version. If you've recently upgraded the plugin, try downgrading the plugin to the previous version to see if the problem still persists. If downgrading the plugin solves the problem, please contact the plugin author for further investigation. If it says something else, contact the plugin author, they'll be able to solve this issue. If by this time you still have your issue, your last option is to create a \"_COMPONENTS_FORCE_LOAD\" file in the server root (case sensitive), but MAKE SURE YOU KNOW WHAT YOU'RE DOING! This will forcefully delete the components that have failed to load. The world might need manual clean up after this step");
                Bukkit.shutdown();
            }
        } else {
            this.getLogger().info("The file _COMPONENTS_FORCE_LOAD has been found, skipping the errors check. MAKE SURE YOU KNOW WHAT YOU'RE DOING!");
            try {
                Files.delete(this.forceLoadFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadSelectedLocations() {
        JsonObject selectedLocations;
        if(Files.exists(this.selectedLocationsFile)) {
            try {
                selectedLocations = new JsonParser().parse(new String(Files.readAllBytes(this.selectedLocationsFile))).getAsJsonObject();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        } else {
            selectedLocations = new JsonObject();
            selectedLocations.add("blocks", new JsonObject());
            selectedLocations.add("components", new JsonObject());
        }

        SelectedLocationsManager.SELECTED_BLOCKS.clear();
        for(Map.Entry<String, JsonElement> entry : selectedLocations.getAsJsonObject("blocks").entrySet()) {
            SelectedLocationsManager.SELECTED_BLOCKS.put(UUID.fromString(entry.getKey()), GenericUtil.locationFromJson(entry.getValue().getAsJsonObject()));
        }

        SelectedLocationsManager.SELECTED_COMPONENTS.clear();
        for(Map.Entry<String, JsonElement> entry : selectedLocations.getAsJsonObject("components").entrySet()) {
            SelectedLocationsManager.SELECTED_COMPONENTS.put(UUID.fromString(entry.getKey()), ComponentLocation.loadFromJson(entry.getValue().getAsJsonObject()));
        }
    }
}