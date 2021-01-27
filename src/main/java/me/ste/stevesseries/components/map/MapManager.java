package me.ste.stevesseries.components.map;

import me.ste.stevesseries.components.map.render.CustomMapRenderer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.ItemFrame;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapView;

import java.util.HashSet;
import java.util.Set;

/**
 * The manager is responsible for managing custom maps
 */
public class MapManager {
    public static final Set<MapData> ASSIGNED_MAPS = new HashSet<>();

    private MapManager() {}

    /**
     * Assign a custom map to a map view
     * @param view map view to assign to
     * @param map custom map to assign
     * @return map data
     */
    public static MapData assign(MapView view, CustomMap map) {
        MapData data = new MapData(view, map);

        view.getRenderers().clear();
        view.addRenderer(new CustomMapRenderer(data));

        MapManager.ASSIGNED_MAPS.add(data);

        return data;
    }

    /**
     * Same as {@link MapManager#assign(int, CustomMap)}, but uses the map ID instead of the map view
     * @param id map ID
     * @param map custom map to assign
     * @return map data, or null if the ID is invalid
     */
    public static MapData assign(int id, CustomMap map) {
        MapView view = Bukkit.getMap(id);
        if(view != null) {
            return MapManager.assign(view, map);
        } else {
            return null;
        }
    }

    /**
     * Remove the assignment of a custom map
     * @param data custom map data
     */
    public static void unassign(MapData data) {
        data.getView().getRenderers().clear();
        MapManager.ASSIGNED_MAPS.remove(data);
    }

    /**
     * Get the map data of a filled map
     * @param item filled map item
     * @return map data, or null if the item doesn't have a custom map assigned
     */
    public static MapData getMapData(ItemStack item) {
        if(item != null && item.getType() == Material.FILLED_MAP && item.hasItemMeta()) {
            MapMeta meta = (MapMeta) item.getItemMeta();
            assert meta != null;
            if(meta.getMapView() != null) {
                for (MapData data : MapManager.ASSIGNED_MAPS) {
                    if (data.getView().getId() == meta.getMapView().getId()) {
                        return data;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Same as {@link MapManager#getMapData(ItemStack)}, but gets the item from the provided item frame
     * @param frame item frame
     * @return map data, or null either if item frame is null or the item doesn't have a custom map assigned
     */
    public static MapData getMapData(ItemFrame frame) {
        return frame != null ? MapManager.getMapData(frame.getItem()) : null;
    }
}