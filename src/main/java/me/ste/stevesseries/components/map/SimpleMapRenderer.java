package me.ste.stevesseries.components.map;

import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

public class SimpleMapRenderer extends MapRenderer {
    private final Map map;

    public SimpleMapRenderer(Map map) {
        super(map.isContextual());
        this.map = map;
    }

    @Override
    public void initialize(MapView map) {
        super.initialize(map);

        this.map.initialize(map);
    }

    @Override
    public void render(MapView map, MapCanvas canvas, Player player) {
        this.map.render(player, map, canvas);
    }

    public Map getMap() {
        return this.map;
    }
}