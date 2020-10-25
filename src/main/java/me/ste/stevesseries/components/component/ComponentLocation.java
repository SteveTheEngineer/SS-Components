package me.ste.stevesseries.components.component;

import com.google.gson.JsonObject;
import me.ste.stevesseries.base.GenericUtil;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;

public class ComponentLocation {
    private final Location location;
    private final BlockFace face;

    /**
     * @param location the location of the component
     * @param face the direction the component is facing
     */
    public ComponentLocation(Location location, BlockFace face) {
        this.location = location;
        this.face = face;
    }

    /**
     * Get the component location
     * @return the component location
     */
    public Location getLocation() {
        return this.location;
    }

    /**
     * Get the component facing direction
     * @return the component facing direction
     */
    public BlockFace getFace() {
        return this.face;
    }

    /**
     * Serialize the component location to JSON
     * @return JSON serialized component location
     */
    public JsonObject saveToJson() {
        JsonObject object = new JsonObject();
        object.add("location", GenericUtil.locationToJson(this.location));
        object.addProperty("face", this.face.name());
        return object;
    }

    /**
     * Get the component at the location
     * @return the component, or null if there's no component
     */
    public Component getComponent() {
        return ComponentManager.getInstance().getComponent(this.location, this.face);
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
     * Check if the specified location isn't null and the component at the location exists and the component class matches
     * @param location the component location
     * @param requiredClass expected component class
     * @return true, if the component meets the requirements
     */
    public static boolean isValid(ComponentLocation location, Class<? extends Component> requiredClass) {
        return ComponentLocation.isValid(location) && requiredClass.isAssignableFrom(location.getComponent().getClass());
    }

    /**
     * Check if the specified location isn't null and there's a component at the location
     * @param location the location
     * @return true, if the component exists
     */
    public static boolean isValid(ComponentLocation location) {
        return location != null && location.getComponent() != null;
    }
}