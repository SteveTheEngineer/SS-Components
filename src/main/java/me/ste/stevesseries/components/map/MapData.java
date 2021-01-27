package me.ste.stevesseries.components.map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.map.MapCursor;
import org.bukkit.map.MapView;

import java.util.HashSet;
import java.util.Set;

public class MapData {
    private final MapView view;
    private final CustomMap map;

    public MapData(MapView view, CustomMap map) {
        this.view = view;
        this.map = map;
    }

    public MapView getView() {
        return this.view;
    }

    public CustomMap getMap() {
        return this.map;
    }

    /**
     *  Re-render the whole map. Must be called whenever something has changed and needs updating on the map
     */
    public void rerender() {
        for(Player player : Bukkit.getOnlinePlayers()) {
            player.sendMap(this.view);
        }
    }
}