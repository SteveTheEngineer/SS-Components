package me.ste.stevesseries.components.listener;

import me.ste.stevesseries.components.EntityRemovalManager;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;

public class EntityRemovalListener implements Listener {
    @EventHandler
    public void onChunkLoad(ChunkLoadEvent event) {
        for(Entity entity : event.getChunk().getEntities()) {
            if(EntityRemovalManager.isMarkedForLaterRemoval(entity)) {
                entity.remove();
                EntityRemovalManager.REMOVE_ON_LOAD.remove(entity.getUniqueId());
            }
        }
    }
}