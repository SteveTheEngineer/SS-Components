package me.ste.stevesseries.components.component;

import com.google.gson.JsonObject;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

public abstract class Component {
    private final Location location;
    private final BlockFace face;

    /**
     * @param location the location where the component was placed at
     * @param face the direction component is facing
     */
    public Component(Location location, BlockFace face) {
        this.location = location;
        this.face = face;
    }

    /**
     * Get the location where the component was placed
     * @return the location
     */
    public final Location getLocation() {
        return this.location;
    }

    /**
     * Get the direction component is facing
     * @return the direction
     */
    public final BlockFace getFace() {
        return this.face;
    }

    /**
     * Initialize the component at the location of the specified item frame
     * @param itemFrame the item frame
     */
    public abstract void create(ItemFrame itemFrame);

    /**
     * Handle the component removal
     */
    public abstract void handleRemoval();

    /**
     * Handle player breaking block at the component's location
     * @param player player who broke the block
     */
    public void handleBlockBreak(Player player) {}

    /**
     * Handle player placing block at the component's location
     * @param player the player who placed the block
     */
    public void handleBlockPlace(Player player, ItemStack block) {}

    /**
     * Handle player interacting with the block at the component's location
     * @param player the player who interacted with the block
     */
    public boolean handleInteraction(Player player, Action action) {
        return false;
    }

    /**
     * Remove the component
     */
    public final void remove() {
        ComponentManager.getInstance().removeComponent(this);
    }

    /**
     * Save the component to JSON
     * @return
     */
    public void save(JsonObject object) {}
    public void load(JsonObject object) {}
}