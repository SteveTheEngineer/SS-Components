package me.ste.stevesseries.components.map;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import me.ste.stevesseries.components.Components;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapView;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class MapManager {
    private static MapManager instance;
    private final Components plugin = Components.getPlugin(Components.class);
    private final FileConfiguration configRoot = plugin.getConfig();
    private final java.util.Map<Integer, Map> maps = new HashMap<>();
    private final Multimap<Integer, UUID> mapEntities = ArrayListMultimap.create();
    private final List<UUID> removeOnLoad = new ArrayList<>();

    private MapManager() {}

    /**
     * Get the {@link MapManager} instance
     * @return the instance
     */
    public static MapManager getInstance() {
        if(MapManager.instance == null) {
            MapManager.instance = new MapManager();
        }
        return MapManager.instance;
    }

    /**
     * @deprecated for internal use only
     */
    @Deprecated
    public java.util.Map<Integer, Map> getMaps() {
        return this.maps;
    }

    /**
     * @deprecated for internal use only
     */
    @Deprecated
    public List<UUID> getRemoveOnLoad() {
        return this.removeOnLoad;
    }

    /**
     * Get the map at the specified location
     * @param location the location
     * @param face the facing direction
     * @return the map
     */
    public Map getMap(Location location, BlockFace face) {
        for(Map testMap : maps.values()) {
            if(testMap.getMapLocation().getBlock().getLocation().equals(location.getBlock().getLocation()) && testMap.getFace() == face) {
                return testMap;
            }
        }
        return null;
    }

    /**
     * Set the map on the specified item frame
     * @param itemFrame the item frame
     * @param map the map
     * @return the map view
     */
    public MapView createMap(ItemFrame itemFrame, Map map) {
        MapView view = Bukkit.createMap(itemFrame.getWorld());
        this.mapEntities.put(view.getId(), itemFrame.getUniqueId());
        this.maps.put(view.getId(), map);

        view.getRenderers().forEach(view::removeRenderer);
        view.addRenderer(this.maps.get(view.getId()).getRenderer());

        ItemStack mapItem = new ItemStack(Material.FILLED_MAP);
        MapMeta meta = (MapMeta) mapItem.getItemMeta();

        if(meta != null) {
            meta.setMapView(view);
        }
        mapItem.setItemMeta(meta);

        itemFrame.setItem(mapItem, false);

        return view;
    }

    /**
     * Set the map instance for the specified map id
     * @param id the map id
     * @param map the map instance
     * @return map view
     */
    public MapView replaceMap(int id, Map map) {
        MapView view = Bukkit.getMap(id);
        this.maps.put(view.getId(), map);

        view.getRenderers().forEach(view::removeRenderer);
        view.addRenderer(this.maps.get(view.getId()).getRenderer());

        return view;
    }

    /**
     * Remove map with the specified id
     * @param id the map id
     */
    public void removeMap(int id) {
        MapView view = Bukkit.getMap(id);
        if(view != null) {
            view.getRenderers().forEach(view::removeRenderer);
        }
        List<UUID> entitiesLeft = new ArrayList<>();
        for(UUID uuid : MapManager.getInstance().getMapEntities().removeAll(id)) {
            Entity entity = Bukkit.getEntity(uuid);
            if(entity != null) {
                entity.remove();
            } else {
                entitiesLeft.add(uuid);
            }
        }
        MapManager.getInstance().getMaps().remove(id);
        this.removeOnLoad.addAll(entitiesLeft);
    }

    /**
     * Get all registered item frame entities
     * @return the entities
     */
    public Multimap<Integer, UUID> getMapEntities() {
        return this.mapEntities;
    }

    /**
     * Save the maps to disk
     */
    public void save() {
        File mapsFile = this.plugin.getDataFolder().toPath().resolve("config.yml").toFile();
        if(!mapsFile.isFile()) {
            try {
                if(!mapsFile.createNewFile()) {
                    return;
                }
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
        try {
            this.configRoot.load(mapsFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }

        if(!this.configRoot.isConfigurationSection("mapEntities")) {
            this.configRoot.createSection("mapEntities");
        }
        ConfigurationSection section = this.configRoot.getConfigurationSection("mapEntities");
        for(int id : this.mapEntities.keySet()) {
            Collection<UUID> uuids = this.mapEntities.get(id);
            List<String> uuidStrings = new ArrayList<>();
            for(UUID uuid : uuids) {
                uuidStrings.add(uuid.toString());
            }
            section.set("" + id, uuidStrings);
        }
        List<String> uuidStrings = new ArrayList<>();
        for(UUID uuid : this.removeOnLoad) {
            uuidStrings.add(uuid.toString());
        }
        this.configRoot.set("removeOnLoad", uuidStrings);

        try {
            this.configRoot.save(mapsFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Load the maps from disk
     */
    public void load() {
        File mapsFile = this.plugin.getDataFolder().toPath().resolve("config.yml").toFile();
        if(!mapsFile.isFile()) {
            return;
        }
        try {
            this.configRoot.load(mapsFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
        this.mapEntities.clear();
        this.removeOnLoad.clear();
        if(!this.configRoot.isConfigurationSection("mapEntities")) {
            return;
        }

        ConfigurationSection section = this.configRoot.getConfigurationSection("mapEntities");
        for(String idString : section.getKeys(false)) {
            List<String> uuidStrings = section.getStringList(idString);
            int id = Integer.parseInt(idString);
            List<UUID> uuids = new ArrayList<>();
            for(String uuidString : uuidStrings) {
                uuids.add(UUID.fromString(uuidString));
            }
            this.mapEntities.putAll(id, uuids);
        }
        for(String uuidString : this.configRoot.getStringList("removeOnLoad")) {
            this.removeOnLoad.add(UUID.fromString(uuidString));
        }
    }
}