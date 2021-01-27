package me.ste.stevesseries.components.listener;

import me.ste.stevesseries.components.SelectedLocationsManager;
import me.ste.stevesseries.components.component.ComponentManager;
import me.ste.stevesseries.components.Components;
import me.ste.stevesseries.components.component.ComponentLocation;
import me.ste.stevesseries.components.gui.ComponentCreationGUI;
import me.ste.stevesseries.inventoryguilibrary.GUIManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class ComponentListener implements Listener {
    private final Components plugin;

    public ComponentListener(Components plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if(event.getHand() == EquipmentSlot.HAND && event.getAction() == Action.LEFT_CLICK_BLOCK) {
            Player player = event.getPlayer();
            EntityEquipment equipment = player.getEquipment();
            if (equipment != null && Components.isValidComponentTool(equipment.getItemInMainHand())) {
                Location location = Objects.requireNonNull(event.getClickedBlock()).getLocation();
                SelectedLocationsManager.setBlock(player, location);
                event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getMessage("block-selected", Objects.requireNonNull(location.getWorld()).getName(), location.getBlockX(), location.getBlockY(), location.getBlockZ())));
                event.setCancelled(true);
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onHangingPlace(HangingPlaceEvent event) {
        Player player = event.getPlayer();
        if(player != null && event.getEntity().getType() == EntityType.ITEM_FRAME) {
            EntityEquipment equipment = player.getEquipment();
            if(equipment != null) {
                ItemStack hand = equipment.getItemInMainHand();
                if(Components.isValidComponentTool(hand)) {
                    if(event.getPlayer().hasPermission("stevesseries.components.modify")) {
                        if(ComponentManager.getComponentAt(new ComponentLocation(event.getEntity().getLocation(), event.getEntity().getFacing())) == null) {
                            GUIManager.open(event.getPlayer(), new ComponentCreationGUI(this.plugin, (ItemFrame) event.getEntity()));
                        } else {
                            event.getPlayer().sendMessage(this.plugin.getMessage("component-already-exists"));
                            event.setCancelled(true);
                        }
                    } else {
                        event.getPlayer().sendMessage(this.plugin.getMessage("no-permission"));
                        event.setCancelled(true);
                    }
                    if(event.getPlayer().getGameMode() != GameMode.CREATIVE) {
                        hand.setAmount(Math.min(hand.getMaxStackSize(), hand.getAmount() + 1));
                        Bukkit.getServer().getScheduler().runTask(this.plugin, () -> event.getPlayer().updateInventory());
                    }
                }
            }
        }
    }
}