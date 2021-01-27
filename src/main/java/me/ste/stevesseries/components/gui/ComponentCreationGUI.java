package me.ste.stevesseries.components.gui;

import me.ste.stevesseries.base.ItemStackBuilder;
import me.ste.stevesseries.components.Components;
import me.ste.stevesseries.components.component.ComponentManager;
import me.ste.stevesseries.components.component.RegisteredComponentData;
import me.ste.stevesseries.inventoryguilibrary.inventory.GridInventory;
import me.ste.stevesseries.inventoryguilibrary.widget.Widget;
import me.ste.stevesseries.inventoryguilibrary.widget.builtin.ButtonWidget;
import me.ste.stevesseries.inventoryguilibrary.widget.builtin.PaginationWidget;
import me.ste.stevesseries.inventoryguilibrary.widget.builtin.PaginationWidgetBarWidget;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.ItemFrame;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ComponentCreationGUI extends Widget {
    private static final ItemStack BAR_BACKGROUND = new ItemStackBuilder(Material.GRAY_STAINED_GLASS_PANE).displayName("").build();

    private final Components plugin;
    private ItemFrame frame;

    public ComponentCreationGUI(Components plugin, ItemFrame frame) {
        super(0, 0, 9, 3);
        this.plugin = plugin;
        this.frame = frame;

        PaginationWidget pagination = new PaginationWidget(0, 0, this.getWidth(), this.getHeight() - 1, null);
        List<Widget> componentWidgets = new ArrayList<>();
        ComponentCreationGUI self = this;
        for(RegisteredComponentData data : ComponentManager.REGISTERED_COMPONENTS.values()) {
            ItemStack stack = new ItemStackBuilder(data.getDisplayMaterial()).displayName(data.getDisplayName()).lore("", this.plugin.getMessage("component-plugin", data.getPlugin().getDescription().getName()), this.plugin.getMessage("component-id", data.getIdentifier().toString()), this.plugin.getMessage("component-class", data.getComponentClass().getName()), "", this.plugin.getMessage("click-to-create")).build();
            componentWidgets.add(new ButtonWidget(0, 0, 1, 1) {
                @Override
                protected ItemStack getItem() {
                    return stack;
                }

                @Override
                protected void click(ClickType type) {
                    if (self.frame != null && !self.frame.isDead()) {
                        ComponentManager.create(data.getIdentifier(), self.frame);
                        this.getPlayer().sendMessage(self.plugin.getMessage("component-created"));
                    } else {
                        this.getPlayer().sendMessage(self.plugin.getMessage("item-frame-missing"));
                    }
                    self.frame = null;
                    this.getPlayer().closeInventory();
                }
            });
        }
        pagination.setItems(componentWidgets);
        this.addChild(pagination);
        this.addChild(new PaginationWidgetBarWidget(0, this.getHeight() - 1, this.getWidth(), 1, ComponentCreationGUI.BAR_BACKGROUND, pagination) {
            @Override
            protected ItemStack getNextPageItem(int page, int pages) {
                return new ItemStackBuilder(Material.BLUE_STAINED_GLASS_PANE).displayName(self.plugin.getMessage("next-page", page, pages)).lore("", self.plugin.getMessage("click-next-page")).build();
            }

            @Override
            protected ItemStack getPreviousPageItem(int page, int pages) {
                return new ItemStackBuilder(Material.BLUE_STAINED_GLASS_PANE).displayName(self.plugin.getMessage("previous-page", page, pages)).lore("", self.plugin.getMessage("click-previous-page")).build();
            }
        });
    }

    @Override
    public void render(GridInventory gridInventory) {}

    @Override
    public Inventory createInventory() {
        return Bukkit.createInventory(null, this.getWidth() * this.getHeight(), this.plugin.getMessage("component-creation"));
    }

    @Override
    public void onDestruction(GridInventory grid) {
        if(this.frame != null) {
            this.frame.remove();
        }
    }
}