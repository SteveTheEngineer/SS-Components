package me.ste.stevesseries.components.map.render;

import me.ste.stevesseries.components.map.MapData;
import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapCursorCollection;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.jetbrains.annotations.NotNull;

/**
 * A {@link MapRenderer} that basically passes it's calls to the assigned {@link MapData}
 */
public class CustomMapRenderer extends MapRenderer {
    private final MapData data;

    public CustomMapRenderer(MapData data) {
        super(true);
        this.data = data;
    }

    @Override
    public void initialize(@NotNull MapView map) {
        this.data.getMap().onCreation(this.data);
    }

    @Override
    public void render(@NotNull MapView map, @NotNull MapCanvas canvas, @NotNull Player player) {
        this.data.getMap().render(player, this.data, new CustomMapCanvas(this.data, canvas));
    }
}