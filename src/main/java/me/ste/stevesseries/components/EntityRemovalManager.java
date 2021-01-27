package me.ste.stevesseries.components;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * The manager is responsible for removing entities in a smart way
 */
public class EntityRemovalManager {
    public static final Set<UUID> REMOVE_ON_LOAD = new HashSet<>();

    private EntityRemovalManager() {}

    /**
     * Remove the entity by it's unique id, or save it for later removal on load if it can't be found
     * @param entityId entity unique id
     */
    public static void remove(UUID entityId) {
        Entity entity = Bukkit.getEntity(entityId);
        if(entity != null) {
            entity.remove();
        } else {
            EntityRemovalManager.REMOVE_ON_LOAD.add(entityId);
        }
    }

    /**
     * Remove the entity, or save it for later removal on load if it can't be found
     * @param entity entity to remove
     */
    public static void remove(Entity entity) {
        EntityRemovalManager.remove(entity.getUniqueId());
    }

    /**
     * Get whether the entity is marked for later removal
     * @param entityId entity unique id
     * @return whether the entity is marked for later removal
     */
    public static boolean isMarkedForLaterRemoval(UUID entityId) {
        return EntityRemovalManager.REMOVE_ON_LOAD.contains(entityId);
    }

    /**
     * Get whether the entity is marked for later removal
     * @param entity entity to check
     * @return whether the entity is marked for later removal
     */
    public static boolean isMarkedForLaterRemoval(Entity entity) {
        return EntityRemovalManager.isMarkedForLaterRemoval(entity.getUniqueId());
    }
}