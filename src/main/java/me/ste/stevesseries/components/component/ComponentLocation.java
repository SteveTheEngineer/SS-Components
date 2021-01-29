package me.ste.stevesseries.components.component;

import com.google.gson.JsonObject;
import me.ste.stevesseries.base.GenericUtil;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;

import java.util.Objects;

public class ComponentLocation implements Cloneable {
    private final Location location;
    private final BlockFace direction;

    public ComponentLocation(Location location, BlockFace direction) {
        this.location = location.getBlock().getLocation();
        this.direction = direction;
    }

    /**
     * Get the component location
     * @return component location
     */
    public Location getLocation() {
        return this.location;
    }

    /**
     * Get the component facing direction
     * @return component facing direction
     */
    public BlockFace getDirection() {
        return this.direction;
    }

    /**
     * Serialize the component location to JSON
     * @return JSON serialized component location
     */
    public JsonObject saveToJson() {
        JsonObject object = new JsonObject();
        object.add("location", GenericUtil.locationToJson(this.location));
        object.addProperty("face", this.direction.name());
        return object;
    }

    /**
     * Deserialize the component location from JSON
     * @param object JSON serialized component location
     * @return deserialized component location
     */
    public static ComponentLocation loadFromJson(JsonObject object) {
        return new ComponentLocation(GenericUtil.locationFromJson(object.getAsJsonObject("location")), BlockFace.valueOf(object.get("face").getAsString()));
    }

    /**
     * Check if the specified location isn't null and the component at the location exists and the component is an instance of the specified class
     * @param location the component location
     * @param requiredClass expected class
     * @return true, if the component meets the requirements
     */
    public static boolean isValid(ComponentLocation location, Class<?> requiredClass) {
        return location != null && ComponentManager.getComponentAt(location) != null && requiredClass.isInstance(ComponentManager.getComponentAt(location));
    }

    @Override
    public ComponentLocation clone() {
        return new ComponentLocation(this.location.clone(), this.direction);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if(o == null || this.getClass() != o.getClass()) {
            return false;
        }
        ComponentLocation that = (ComponentLocation) o;
        return Objects.equals(this.location, that.location) && this.direction == that.direction;
    }

    @Override
    public int hashCode() {
        return Objects.hash(location, direction);
    }
}