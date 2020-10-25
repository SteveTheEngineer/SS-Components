package me.ste.stevesseries.components.component;

import me.ste.stevesseries.base.ItemStackBuilder;
import me.ste.stevesseries.inventoryguilibrary.GUI;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ComponentCreationGUI extends GUI {
    private final ItemFrame itemFrame;
    private int page;
    private int pages;
    private boolean removeOnClose;
    private boolean created;

    public ComponentCreationGUI(Player player, ItemFrame itemFrame, boolean removeOnClose) {
        super(player, 27, "Component Creation");
        this.itemFrame = itemFrame;
        this.pages = (int) Math.max(1, Math.ceil(ComponentManager.getInstance().getComponentFactories().entrySet().size() / 18D));
        this.removeOnClose = removeOnClose;
    }
    public ComponentCreationGUI(Player player, ItemFrame itemFrame) {
        this(player, itemFrame, false);
    }

    @Override
    public void updateInventory(Inventory inventory) {
        GUI.fillItems(inventory, 0, 2, 9, 1, new ItemStackBuilder(Material.GRAY_STAINED_GLASS_PANE).displayName("").build());
        inventory.setItem(GUI.getGridPositionIndex(0, 2), new ItemStackBuilder(Material.BLUE_STAINED_GLASS_PANE).displayName(String.format("Previous page &7(%d/%d)", this.page + 1, this.pages)).build());
        inventory.setItem(GUI.getGridPositionIndex(8, 2), new ItemStackBuilder(Material.BLUE_STAINED_GLASS_PANE).displayName(String.format("Next page &7(%d/%d)", this.page + 1, this.pages)).build());

        int i = 0;
        for(Map.Entry<Class, ComponentFactory> factory : new ArrayList<>(ComponentManager.getInstance().getComponentFactories().entrySet()).subList(this.page * 18, Math.min(ComponentManager.getInstance().getComponentFactories().entrySet().size(), (this.page + 1) * 18))) {
            String name = ComponentManager.getInstance().getFriendlyName(factory.getKey());
            if(name == null) {
                name = factory.getKey().getSimpleName();
            }
            inventory.setItem(i, new ItemStackBuilder(Material.EMERALD_BLOCK).displayName(name).build());
            i++;
        }
    }

    @Override
    public void handleClick(ItemStack stack, ClickType clickType, int slot, Inventory inventory) {
        super.handleClick(stack, clickType, slot, inventory);

        if(clickType == ClickType.DOUBLE_CLICK) {
            return;
        }

        if(slot == GUI.getGridPositionIndex(8, 2)) {
            if(this.page < this.pages - 1) {
                this.page++;
                this.refresh();
            }
        } else if(slot == GUI.getGridPositionIndex(0, 2)) {
            if(this.page > 0) {
                this.page--;
                this.refresh();
            }
        } else if(slot >= 0 && slot <= GUI.getGridPositionIndex(8, 4)) {
            @SuppressWarnings("deprecation")
            List<Map.Entry<Class, ComponentFactory>> entries = new ArrayList<>(ComponentManager.getInstance().getComponentFactories().entrySet());
            int index = this.page * 18 + slot;
            if(entries.size() >= index) {
                if(!this.itemFrame.isDead()) {
                    this.created = true;
                } else {
                    this.getPlayer().sendMessage(ChatColor.RED + "Item frame has been removed while creating the component");
                }
                this.getPlayer().closeInventory();
            }
        }
    }

    @Override
    public void handleClosing(Inventory inventory) {
        super.handleClosing(inventory);

        if(this.removeOnClose && !this.created && !this.itemFrame.isDead()) {
            this.itemFrame.remove();
        }
    }
}