package me.ste.stevesseries.components.component;

import com.google.gson.JsonObject;
import me.ste.stevesseries.components.map.Map;
import me.ste.stevesseries.components.map.MapManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Rotation;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.map.MapView;

import java.util.UUID;

/**
 * A component that uses a map for it's functions
 */
public abstract class MapComponent extends Component {
    private MapView view;
    private Map map;

    public MapComponent(Location location, BlockFace face) {
        super(location, face);
    }

    @Override
    public void create(ItemFrame itemFrame) {
        this.view = MapManager.getInstance().createMap(itemFrame, this.map);
    }

    @Override
    public void handleRemoval() {
        MapManager.getInstance().removeMap(this.view.getId());
    }

    protected final void setMap(Map map) {
        this.map = map;
    }

    /**
     * Get the map of the component
     * @return the map
     */
    public final Map getMap() {
        return this.map;
    }

    protected MapView getView() {
        return this.view;
    }

    /**
     * Refresh the map
     */
    public void refreshMap() {
        for(Player player : Bukkit.getOnlinePlayers()) {
            player.sendMap(this.view);
        }
    }

    @Override
    public void save(JsonObject object) {
        super.save(object);
        object.addProperty("mapId", this.view.getId());
    }

    @Override
    public void load(JsonObject object) {
        super.load(object);
        this.view = Bukkit.getMap(object.get("mapId").getAsInt());
        MapManager.getInstance().replaceMap(this.view.getId(), this.map);
        for(UUID uuid : MapManager.getInstance().getMapEntities().get(this.view.getId())) {
            Entity entity = Bukkit.getEntity(uuid);
            if(entity instanceof ItemFrame) {
                ((ItemFrame) entity).setRotation(Rotation.NONE);
            }
        }
    }
}