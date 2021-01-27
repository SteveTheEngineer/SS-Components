package me.ste.stevesseries.components.component.builtin;

import com.google.gson.JsonObject;
import me.ste.stevesseries.base.ItemStackBuilder;
import me.ste.stevesseries.components.EntityRemovalManager;
import me.ste.stevesseries.components.component.Component;
import me.ste.stevesseries.components.component.ComponentLocation;
import me.ste.stevesseries.components.map.CustomMap;
import me.ste.stevesseries.components.map.MapData;
import me.ste.stevesseries.components.map.MapManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.ItemFrame;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapView;

import java.util.UUID;

public abstract class MapComponent extends Component {
    private CustomMap map;
    private MapView view;
    private UUID itemFrameUniqueId;
    private MapData data;

    public MapComponent(ComponentLocation location) {
        super(location);
    }

    protected void setMap(CustomMap map) {
        this.map = map;
    }

    @Override
    public void onInitialization(ItemFrame frame) {
        this.view = Bukkit.createMap(frame.getWorld());

        frame.setItem(new ItemStackBuilder(Material.FILLED_MAP).<MapMeta>meta(meta -> meta.setMapView(this.view)).build());
        this.itemFrameUniqueId = frame.getUniqueId();
    }

    @Override
    public void onDeletion() {
        EntityRemovalManager.remove(this.itemFrameUniqueId);
    }

    @Override
    public void onCreation() {
        this.data = MapManager.assign(this.view, this.map);
    }

    @Override
    public void onDestruction() {
        MapManager.unassign(this.data);
    }

    @Override
    public void writeToJson(JsonObject object) {
        JsonObject mapComponent = new JsonObject();

        mapComponent.addProperty("itemFrame", this.itemFrameUniqueId.toString());
        mapComponent.addProperty("id", this.view.getId());

        object.add("_mapComponent", mapComponent);
    }

    @Override
    public void readFromJson(JsonObject object) {
        JsonObject mapComponent = object.getAsJsonObject("_mapComponent");

        this.itemFrameUniqueId = UUID.fromString(mapComponent.get("itemFrame").getAsString());
        this.view = Bukkit.getMap(mapComponent.get("id").getAsInt());
    }
}