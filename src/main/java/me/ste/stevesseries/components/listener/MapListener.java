package me.ste.stevesseries.components.listener;

import me.ste.stevesseries.components.map.MapClickType;
import me.ste.stevesseries.components.map.MapData;
import me.ste.stevesseries.components.map.MapManager;
import org.bukkit.*;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.hanging.HangingBreakEvent;
import org.bukkit.event.inventory.InventoryCreativeEvent;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerAnimationType;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class MapListener implements Listener {
    private boolean onInteraction(Player player, MapClickType type) {
        if(player.getGameMode() != GameMode.SPECTATOR) {
            Location eyeLocation = player.getEyeLocation();
            double maxDistance = player.getGameMode() == GameMode.CREATIVE ? 5D : 4D; // The vanilla reach distance for creative mode is 5, and for others is 4

            MapData mapData = null;
            Rotation rotation = null;
            RayTraceResult result = null;
            Location frameLocation = null;
            double resultDistance = Float.MAX_VALUE;

            RayTraceResult blockResult = player.rayTraceBlocks(maxDistance); // Get the closest hit block
            if(blockResult != null && blockResult.getHitBlock() != null) {
                resultDistance = blockResult.getHitPosition().distanceSquared(eyeLocation.toVector()); // Set resultDistance to the hit block location, to prevent interacting with maps through blocks
            }

            RayTraceResult entityResult = player.getWorld().rayTraceEntities(eyeLocation, player.getLocation().getDirection(), maxDistance, entity -> entity.getUniqueId() != player.getUniqueId() && (!(entity instanceof Player) || ((Player) entity).getGameMode() != GameMode.SPECTATOR)); // Get the closest hit entity
            if(entityResult != null && entityResult.getHitEntity() != null) {
                double distance = entityResult.getHitPosition().distanceSquared(eyeLocation.toVector());
                if(distance < resultDistance) {
                    resultDistance = distance;
                    if(entityResult.getHitEntity().getType() == EntityType.ITEM_FRAME) { // If we hit a map item frame, set it as the result item frame. This is important, as if we don't do this here, and the map was an assigned map item frame, it will get ignored in the later loop
                        ItemFrame frame = (ItemFrame) entityResult.getHitEntity();
                        MapData data = MapManager.getMapData(frame);
                        if(data != null && frame.getFacing() == entityResult.getHitBlockFace()) {
                            frameLocation = frame.getLocation();
                            mapData = data;
                            rotation = frame.getRotation();
                            result = entityResult;
                        }
                    }
                }
            }

            for(Entity entity : player.getNearbyEntities(maxDistance, maxDistance, maxDistance)) { // Loop through every entity in the distance of maxDistance
                if(entity.getType() == EntityType.ITEM_FRAME) {
                    ItemFrame frame = (ItemFrame) entity;
                    MapData data = MapManager.getMapData(frame);
                    if(data != null) {
                        BoundingBox box = entity.getBoundingBox(); // Get the base item frame bounding box
                        BlockFace facing = frame.getFacing();

                        // Adjust the base item frame bounding box, so it fits the map item frame. The map item frames fit the whole block unlike the regular item frames
                        if(facing == BlockFace.NORTH || facing == BlockFace.EAST || facing == BlockFace.SOUTH || facing == BlockFace.WEST) {
                            box.expand(0D, 0.125D, 0D); // Expand the bounding box vertically to the size of 1 if the item frame is not facing up or down
                            if(facing == BlockFace.NORTH || facing == BlockFace.SOUTH) {
                                box.expand(0.125D, 0D, 0D); // Expand the bounding box on the X axis to the size of 1 if the item frame is facing north or south
                            } else {
                                box.expand(0D, 0D, 0.125D); // Expand the bounding box on the Z axis to the size of 1 if the item frame is facing east or west
                            }
                        } else {
                            box.expand(0.125D, 0D, 0.125D); // Expand the bounding box horizontally if the item frame is facing up or down
                        }
                        box.expand(facing, 0.00789D); // Expand the bounding box in the frame's facing direction. If you look closely, the map itself is actually a bit offset from the item frame

                        // Ray trace the bounding box from the player eye location
                        RayTraceResult currentResult = box.rayTrace(eyeLocation.toVector(), player.getLocation().getDirection(), maxDistance);
                        if(currentResult != null && currentResult.getHitBlockFace() != null) {
                            double distance = currentResult.getHitPosition().distanceSquared(eyeLocation.toVector());
                            if(distance < resultDistance) { // Make sure this is the closest hit position
                                frameLocation = frame.getLocation();
                                resultDistance = distance;
                                rotation = frame.getRotation();
                                mapData = data;
                                result = currentResult.getHitBlockFace() == facing ? currentResult : null; // Set the result to null if we haven't hit the front side, but still update the distance
                            }
                        }
                    }
                }
            }

            if(result != null) { // Result can be null if no assigned map item frames were hit
                BlockFace facing = result.getHitBlockFace();

                Vector hitPosition = result.getHitPosition();
                Vector position = hitPosition.subtract(frameLocation.getBlock().getLocation().toVector()); // Get the coordinates relative to the block origin

                // Transform 3D coordinates to 2D coordinates relative to the top left corner of the front face of the item frame
                float x = 0F;
                float y = 0F;
                if(facing == BlockFace.NORTH || facing == BlockFace.SOUTH) {
                    x = (float) position.getX();
                    if(facing == BlockFace.NORTH) {
                        x = 1F - x;
                    }
                    y = 1F - (float) position.getY();
                } else if(facing == BlockFace.EAST || facing == BlockFace.WEST) {
                    x = (float) position.getZ();
                    if(facing == BlockFace.EAST) {
                        x = 1F - x;
                    }
                    y = 1F - (float) position.getY();
                } else if(facing == BlockFace.UP || facing == BlockFace.DOWN) {
                    x = (float) position.getX();
                    y = (float) position.getZ();
                    if(facing == BlockFace.DOWN) {
                        y = 1F - y;
                    }
                }

                // If the item frame has a rotation, rotate the coordinates accordingly
                int rotationNumber = rotation.ordinal() % 4;
                if(rotationNumber == 1) {
                    float oldX = x;
                    x = y;
                    y = 1F - oldX;
                } else if(rotationNumber == 2) {
                    x = 1F - x;
                    y = 1F - y;
                } else if(rotationNumber == 3) {
                    float oldX = x;
                    x = 1F - y;
                    y = oldX;
                }

                // Transform proportional coordinates to coordinates in a 128x128 space and call onClick on the hit map
                mapData.getMap().onClick(player, mapData, type, (int) Math.floor(128F * x), (int) Math.floor(128F * y), x, y);

                return true;
            }
        }
        return false;
    }

    private final Set<UUID> ignoreInteractEvent = new HashSet<>();
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();
        if(event.getHand() == EquipmentSlot.HAND) {
            if(!this.ignoreInteractEvent.contains(player.getUniqueId())) {
                if(event.getPlayer().getGameMode() == GameMode.ADVENTURE && (action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK)) {
                    return;
                }
                if(this.onInteraction(player, action == Action.RIGHT_CLICK_BLOCK || action == Action.RIGHT_CLICK_AIR ? MapClickType.RIGHT : MapClickType.LEFT)) {
                    this.ignoreInteractEvent.add(player.getUniqueId());
                    if(action == Action.RIGHT_CLICK_AIR) {
                        player.swingMainHand();
                    }
                    event.setCancelled(true);
                }
            } else {
                this.ignoreInteractEvent.remove(player.getUniqueId());
            }
        }
    }

    @EventHandler
    public void onPlayerAnimation(PlayerAnimationEvent event) {
        if(event.getAnimationType() == PlayerAnimationType.ARM_SWING && event.getPlayer().getTargetBlockExact(4) != null && event.getPlayer().getGameMode() == GameMode.ADVENTURE) {
            if(!this.ignoreInteractEvent.contains(event.getPlayer().getUniqueId())) {
                this.onInteraction(event.getPlayer(), MapClickType.LEFT);
            } else {
                this.ignoreInteractEvent.remove(event.getPlayer().getUniqueId());
            }
        }
    }

    @EventHandler
    public void onEntityInteract(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        if(event.getHand() == EquipmentSlot.HAND && event.getRightClicked().getType() == EntityType.ITEM_FRAME) {
            if(MapManager.getMapData((ItemFrame) event.getRightClicked()) != null) {
                this.ignoreInteractEvent.add(player.getUniqueId());
                event.setCancelled(true);
                this.onInteraction(player, MapClickType.RIGHT);
            }
        }
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if(event.getEntity().getType() == EntityType.ITEM_FRAME && MapManager.getMapData((ItemFrame) event.getEntity()) != null) {
            event.setCancelled(true);
            if(event.getDamager() instanceof Player && ((Player) event.getDamager()).getGameMode() != GameMode.ADVENTURE) {
                this.onInteraction((Player) event.getDamager(), MapClickType.LEFT);
            }
        }
    }

    @EventHandler
    public void onHangingBreak(HangingBreakEvent event) {
        if(event.getEntity().getType() == EntityType.ITEM_FRAME && MapManager.getMapData((ItemFrame) event.getEntity()) != null) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryCreative(InventoryCreativeEvent event) {
        if(MapManager.getMapData(event.getCursor()) != null) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onChunkLoad(ChunkLoadEvent event) {
        for(Entity entity : event.getChunk().getEntities()) {
            if(entity.getType() == EntityType.ITEM_FRAME) {
                ItemFrame frame = (ItemFrame) entity;
                if(MapManager.getMapData(frame) != null) {
                    frame.setRotation(Rotation.NONE);
                }
            }
        }
    }
}