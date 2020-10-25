package me.ste.stevesseries.components.component;

import me.ste.stevesseries.components.Components;
import me.ste.stevesseries.components.map.Map;
import me.ste.stevesseries.components.map.MapManager;
import me.ste.stevesseries.inventoryguilibrary.InventoryGUILibrary;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.hanging.HangingBreakEvent;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.event.inventory.InventoryCreativeEvent;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerAnimationType;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.util.RayTraceResult;

import java.util.*;

public class ComponentListener implements Listener {
    private final List<UUID> rightClicked = new ArrayList<>();
    private final Components plugin = Components.getPlugin(Components.class);

    public boolean handleInteract(Block clickedBlock, BlockFace face, Player player, boolean leftClick) {
        Map foundMap = MapManager.getInstance().getMap(clickedBlock.getRelative(face).getLocation(), face.getOppositeFace());
        if(foundMap != null) {
            RayTraceResult result = Objects.requireNonNull(clickedBlock).rayTrace(player.getEyeLocation(), player.getLocation().getDirection(), 4.5, FluidCollisionMode.NEVER);
            if(result == null) {
                return true;
            }
            Location blockOriginRelative = result.getHitPosition().toLocation(clickedBlock.getWorld()).subtract(clickedBlock.getLocation());
            double x = 0;
            double y = 0;
            if(result.getHitBlockFace() == BlockFace.SOUTH) {
                x = blockOriginRelative.getX();
                y = 1 - blockOriginRelative.getY();
            } else if(result.getHitBlockFace() == BlockFace.NORTH) {
                x = 1 - blockOriginRelative.getX();
                y = 1 - blockOriginRelative.getY();
            } else if(result.getHitBlockFace() == BlockFace.WEST) {
                x = blockOriginRelative.getZ();
                y = 1 - blockOriginRelative.getY();
            } else if(result.getHitBlockFace() == BlockFace.EAST) {
                x = 1 - blockOriginRelative.getZ();
                y = 1 - blockOriginRelative.getY();
            } else if(result.getHitBlockFace() == BlockFace.UP) {
                x = blockOriginRelative.getX();
                y = blockOriginRelative.getZ();
            } else if(result.getHitBlockFace() == BlockFace.DOWN) {
                x = 1 - blockOriginRelative.getX();
                y = 1 - blockOriginRelative.getZ();
            }
            int pix128X = (int) Math.ceil(x * 128);
            int pix128Y = (int) Math.ceil(y * 128);

            foundMap.handleClick(player, leftClick, pix128X, pix128Y);
            return true;
        }
        return false;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.useInteractedBlock() != Event.Result.DENY && event.getClickedBlock() != null && event.getHand() == EquipmentSlot.HAND && event.getAction() == Action.LEFT_CLICK_BLOCK && event.getPlayer().getEquipment() != null) {
            ItemStack hand = event.getPlayer().getEquipment().getItemInMainHand();
            if (hand.getType() == Material.ITEM_FRAME && hand.hasItemMeta() && Objects.requireNonNull(hand.getItemMeta()).hasEnchant(Enchantment.ARROW_INFINITE)) {
                Location location = event.getClickedBlock().getLocation();
                this.plugin.setSelectedLocation(event.getPlayer(), location);
                event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', String.format("&eLocation has been selected: &6%s @ %d, %d, %d", Objects.requireNonNull(location.getWorld()).getName(), location.getBlockX(), location.getBlockY(), location.getBlockZ())));
                event.setCancelled(true);
            }
        }
        if (event.useInteractedBlock() != Event.Result.DENY && event.getClickedBlock() != null && event.getHand() == EquipmentSlot.HAND && (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.LEFT_CLICK_BLOCK) && this.handleInteract(event.getClickedBlock(), event.getBlockFace(), event.getPlayer(), event.getAction() == Action.LEFT_CLICK_BLOCK)) {
            event.setCancelled(true);
            if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getPlayer().getGameMode() == GameMode.ADVENTURE && !this.rightClicked.contains(event.getPlayer().getUniqueId())) {
                this.rightClicked.add(event.getPlayer().getUniqueId());
            }
        }
        if (event.useInteractedBlock() != Event.Result.DENY && event.getHand() == EquipmentSlot.HAND && event.getClickedBlock() != null) {
            Component component = ComponentManager.getInstance().getComponent(event.getClickedBlock().getLocation());
            if (component != null && component.handleInteraction(event.getPlayer(), event.getAction())) {
                event.setCancelled(true);
                if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getPlayer().getGameMode() == GameMode.ADVENTURE && !this.rightClicked.contains(event.getPlayer().getUniqueId())) {
                    this.rightClicked.add(event.getPlayer().getUniqueId());
                }
            }
        }
    }

    @EventHandler
    public void onHangingPlace(HangingPlaceEvent event) {
        if(event.getPlayer() != null && event.getPlayer().getEquipment() != null) {
            ItemStack stack = event.getPlayer().getEquipment().getItemInMainHand();
            if(stack.getType() == Material.ITEM_FRAME && event.getEntity() instanceof ItemFrame) {
                if(stack.hasItemMeta() && Objects.requireNonNull(stack.getItemMeta()).hasEnchant(Enchantment.ARROW_INFINITE)) {
                    if(event.getPlayer().getGameMode() == GameMode.CREATIVE) {
                        if(ComponentManager.getInstance().getComponent(event.getEntity().getLocation(), event.getEntity().getAttachedFace().getOppositeFace()) == null) {
                            InventoryGUILibrary.getInstance().openGUI(event.getPlayer(), new ComponentCreationGUI(event.getPlayer(), (ItemFrame) event.getEntity(), true));
                        }
                    } else {
                        event.getPlayer().sendMessage(ChatColor.RED + "Components can only be placed in creative mode");
                        event.setCancelled(true);
                        stack.setAmount(Math.min(stack.getMaxStackSize(), stack.getAmount() + 1));
                        Bukkit.getServer().getScheduler().runTaskLater(this.plugin, () -> event.getPlayer().updateInventory(), 1L);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerAnimation(PlayerAnimationEvent event) {
        if(event.getPlayer().getGameMode() == GameMode.ADVENTURE && event.getAnimationType() == PlayerAnimationType.ARM_SWING) {
            if(!this.rightClicked.contains(event.getPlayer().getUniqueId())) {
                RayTraceResult result = event.getPlayer().rayTraceBlocks(4.5, FluidCollisionMode.NEVER);
                RayTraceResult entityResult = event.getPlayer().getWorld().rayTraceEntities(event.getPlayer().getEyeLocation(), event.getPlayer().getLocation().getDirection(), 3, entity -> entity instanceof ItemFrame);
                if((entityResult == null || entityResult.getHitEntity() == null) && result != null && result.getHitBlock() != null) {
                    Block clickedBlock = result.getHitBlock();
                    BlockFace blockFace = result.getHitBlockFace();
                    if(!this.handleInteract(clickedBlock, blockFace, event.getPlayer(), true)) {
                        Component component = ComponentManager.getInstance().getComponent(clickedBlock.getLocation());
                        if(component != null) {
                            if (component.handleInteraction(event.getPlayer(), Action.LEFT_CLICK_BLOCK)) {
                                event.setCancelled(true);
                            }
                        }
                    }
                }
            } else {
                this.rightClicked.remove(event.getPlayer().getUniqueId());
            }
        }
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if(event.getEntity() instanceof ItemFrame) {
            ItemFrame itemFrame = (ItemFrame) event.getEntity();
            Map foundMap = MapManager.getInstance().getMap(itemFrame.getLocation(), itemFrame.getAttachedFace());
            if(foundMap != null) {
                event.setCancelled(true);
                if(event.getDamager() instanceof Player && itemFrame.getItem().getType() == Material.FILLED_MAP) {
                    this.handleInteract(itemFrame.getLocation().getBlock().getRelative(itemFrame.getAttachedFace()), itemFrame.getAttachedFace().getOppositeFace(), (Player) event.getDamager(), true);
                }
            }
        }
    }

    @EventHandler
    public void onHangingBreak(HangingBreakEvent event) {
        if(event.getEntity() instanceof ItemFrame) {
            ItemFrame itemFrame = (ItemFrame) event.getEntity();
            if(MapManager.getInstance().getMap(itemFrame.getLocation(), itemFrame.getAttachedFace()) != null) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Component component = ComponentManager.getInstance().getComponent(event.getBlock().getLocation());
        if(component != null) {
            event.setCancelled(true);
            component.handleBlockBreak(event.getPlayer());
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Component component = ComponentManager.getInstance().getComponent(event.getBlock().getLocation());
        if(component != null) {
            event.setCancelled(true);
            component.handleBlockPlace(event.getPlayer(), event.getItemInHand());
        }
    }

    @EventHandler
    public void onBlockBurn(BlockBurnEvent event) {
        Component component = ComponentManager.getInstance().getComponent(event.getBlock().getLocation());
        if(component != null) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockExplode(BlockExplodeEvent event) {
        Component component = ComponentManager.getInstance().getComponent(event.getBlock().getLocation());
        if(component != null) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockFade(BlockFadeEvent event) {
        event.setCancelled(ComponentManager.getInstance().getComponent(event.getBlock().getLocation()) != null);
    }

    @EventHandler
    public void onBlockPhysics(BlockPhysicsEvent event) {
        event.setCancelled(ComponentManager.getInstance().getComponent(event.getBlock().getLocation()) != null);
    }

    @EventHandler
    public void onBlockPistonExtend(BlockPistonExtendEvent event) {
        for(Block block : event.getBlocks()) {
            if(ComponentManager.getInstance().getComponent(block.getLocation()) != null) {
                event.setCancelled(true);
                return;
            }
        }
    }

    @EventHandler
    public void onBlockPistonRetract(BlockPistonRetractEvent event) {
        for(Block block : event.getBlocks()) {
            if(ComponentManager.getInstance().getComponent(block.getLocation()) != null) {
                event.setCancelled(true);
                return;
            }
        }
    }

    @EventHandler
    public void onInventoryCreative(InventoryCreativeEvent event) {
        if(event.getCursor().getType() == Material.FILLED_MAP && event.getCursor().hasItemMeta() && MapManager.getInstance().getMapEntities().containsKey(Objects.requireNonNull(((MapMeta) Objects.requireNonNull(event.getCursor().getItemMeta())).getMapView()).getId())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInteractEntity(PlayerInteractEntityEvent event) {
        if(event.getHand() == EquipmentSlot.HAND && event.getRightClicked() instanceof ItemFrame) {
            ItemFrame itemFrame = (ItemFrame) event.getRightClicked();
            if(itemFrame.getItem().getType() == Material.FILLED_MAP) {
                event.setCancelled(this.handleInteract(itemFrame.getLocation().getBlock().getRelative(itemFrame.getAttachedFace()), itemFrame.getAttachedFace().getOppositeFace(), event.getPlayer(), false));
            }
        }
    }

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onChunkLoad(ChunkLoadEvent event) {
        for(Entity entity : event.getChunk().getEntities()){
            if(MapManager.getInstance().getRemoveOnLoad().contains(entity.getUniqueId())) {
                entity.remove();
                MapManager.getInstance().getRemoveOnLoad().remove(entity.getUniqueId());
            }
        }
    }
}