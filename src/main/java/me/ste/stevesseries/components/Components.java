package me.ste.stevesseries.components;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import me.ste.stevesseries.base.GenericUtil;
import me.ste.stevesseries.components.component.Component;
import me.ste.stevesseries.components.component.ComponentListener;
import me.ste.stevesseries.components.component.ComponentManager;
import me.ste.stevesseries.components.map.MapManager;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.logging.Level;

public final class Components extends JavaPlugin {
    private final Map<UUID, Location> selectedLocations = new HashMap<>();

    @Override
    public void onEnable() {
        Objects.requireNonNull(this.getCommand("components")).setExecutor(new ComponentsCommand());
        this.getServer().getPluginManager().registerEvents(new ComponentListener(), this);

        for(Plugin plugin : this.getServer().getPluginManager().getPlugins()) {
            InputStream fileStream = plugin.getResource("components.json");
            if(fileStream != null) {
                try {
                    JsonObject object = new JsonParser().parse(new InputStreamReader(fileStream, StandardCharsets.UTF_8)).getAsJsonObject();
                    if(object.has("components")) {
                        JsonObject components = object.get("components").getAsJsonObject();
                        for(Map.Entry<String, JsonElement> pkg : components.entrySet()) {
                            String prefix = "";
                            if(!pkg.getKey().equals(".")) {
                                prefix = String.format("%s.", pkg.getKey());
                            }
                            for(JsonElement element : pkg.getValue().getAsJsonArray()) {
                                Class<?> clazz = Class.forName(String.format("%s%s", prefix, element.getAsString()));
                                if(Component.class.isAssignableFrom(clazz)) {
                                    Class<? extends Component> componentClass = clazz.asSubclass(Component.class);
                                    ComponentManager.getInstance().register(componentClass);
                                    if(ComponentManager.getInstance().getComponentFactories().containsKey(componentClass)) {
                                        this.getLogger().log(Level.INFO, String.format("Registered component %s of %s", ComponentManager.getInstance().getDisplayName(componentClass), plugin.getDescription().getFullName()));
                                    }
                                } else {
                                    this.getLogger().log(Level.SEVERE, String.format("Class %s does not extend Component (%s)", clazz.getName(), plugin.getDescription().getFullName()));
                                }
                            }
                        }
                    }
                } catch (JsonParseException | IllegalStateException e) {
                    this.getLogger().log(Level.SEVERE, String.format("Unable to parse components.json of plugin %s", plugin.getDescription().getFullName()), e);
                } catch (ClassNotFoundException e) {
                    this.getLogger().log(Level.SEVERE, String.format("Unable to find component class (%s)", plugin.getDescription().getFullName()), e);
                }
            }
        }

        if(!this.getDataFolder().isDirectory()) {
            if(!this.getDataFolder().mkdirs()) {
                this.getLogger().log(Level.WARNING, "Cannot create new data folder");
            }
        }

        this.load();
        MapManager.getInstance().load();
        ComponentManager.getInstance().load();
    }

    @Override
    public void onDisable() {
        ComponentManager.getInstance().save();
        MapManager.getInstance().save();
        this.save();
    }

    /**
     * Get the selected location of the specified player
     * @param player the player
     * @return selected location
     */
    public Location getSelectedLocation(OfflinePlayer player) {
        return this.selectedLocations.get(player.getUniqueId());
    }

    /**
     * Set the selected location of the specified player
     * @param player the player
     * @param location selected location
     */
    public void setSelectedLocation(OfflinePlayer player, Location location) {
        if(location != null) {
            this.selectedLocations.put(player.getUniqueId(), location);
        } else {
            this.selectedLocations.remove(player.getUniqueId());
        }
    }

    /**
     * Save the selected locations to disk
     */
    public void save() {
        File configFile = this.getDataFolder().toPath().resolve("config.yml").toFile();
        if(!configFile.isFile()) {
            try {
                if(!configFile.createNewFile()) {
                    return;
                }
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
        try {
            this.getConfig().load(configFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }

        ConfigurationSection selectedLocations = this.getConfig().getConfigurationSection("selectedLocations");
        if(selectedLocations == null) {
            selectedLocations = this.getConfig().createSection("selectedLocations");
        }

        for(Map.Entry<UUID, Location> location : this.selectedLocations.entrySet()) {
            selectedLocations.set(location.getKey().toString(), GenericUtil.locationToJson(location.getValue()).toString());
        }

        try {
            this.getConfig().save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Load the selected locations from disk
     */
    public void load() {
        File configFile = this.getDataFolder().toPath().resolve("config.yml").toFile();
        if(!configFile.isFile()) {
            return;
        }
        try {
            this.getConfig().load(configFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
        this.selectedLocations.clear();
        if(this.getConfig().isConfigurationSection("selectedLocations")) {
            ConfigurationSection section = this.getConfig().getConfigurationSection("selectedLocations");
            assert section != null;
            for(String uuidString : section.getKeys(false)) {
                this.selectedLocations.put(UUID.fromString(uuidString), GenericUtil.locationFromJson(new JsonParser().parse(Objects.requireNonNull(section.getString(uuidString))).getAsJsonObject()));
            }
        }
    }
}