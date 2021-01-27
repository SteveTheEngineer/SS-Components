package me.ste.stevesseries.components.gui;

import me.ste.stevesseries.base.ItemStackBuilder;
import me.ste.stevesseries.components.Components;
import me.ste.stevesseries.components.component.ComponentManager;
import me.ste.stevesseries.components.SelectedLocationsManager;
import me.ste.stevesseries.components.component.Component;
import me.ste.stevesseries.components.component.RegisteredComponentData;
import me.ste.stevesseries.components.component.configuration.ConfigurableComponent;
import me.ste.stevesseries.components.component.configuration.element.ConfigurationElement;
import me.ste.stevesseries.inventoryguilibrary.inventory.GridInventory;
import me.ste.stevesseries.inventoryguilibrary.widget.Widget;
import me.ste.stevesseries.inventoryguilibrary.widget.builtin.ButtonWidget;
import me.ste.stevesseries.inventoryguilibrary.widget.builtin.PaginationWidget;
import me.ste.stevesseries.inventoryguilibrary.widget.builtin.PaginationWidgetBarWidget;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ComponentModificationGUI extends Widget {
    private static final Components PLUGIN = Components.getPlugin(Components.class);

    private static final ItemStack NOT_CONFIGURABLE = new ItemStackBuilder(Material.BLACK_STAINED_GLASS_PANE).displayName(ComponentModificationGUI.PLUGIN.getMessage("component-not-configurable")).build();
    private static final ItemStack BAR_BACKGROUND = new ItemStackBuilder(Material.GRAY_STAINED_GLASS_PANE).displayName("").build();
    private static final ItemStack DELETE_COMPONENT = new ItemStackBuilder(Material.RED_STAINED_GLASS_PANE).displayName(ComponentModificationGUI.PLUGIN.getMessage("delete-component")).lore("", ComponentModificationGUI.PLUGIN.getMessage("click-to-delete")).build();
    private static final ItemStack SELECT_COMPONENT = new ItemStackBuilder(Material.CYAN_STAINED_GLASS_PANE).displayName(ComponentModificationGUI.PLUGIN.getMessage("select-component")).lore("", ComponentModificationGUI.PLUGIN.getMessage("click-to-select")).build();

    private final Component component;
    private final RegisteredComponentData data;
    private final ItemStack componentStack;

    public ComponentModificationGUI(Component component) {
        super(0, 0, 9, 3);
        this.component = component;
        this.data = ComponentManager.getRegisteredComponentData(this.component.getClass());
        this.componentStack = new ItemStackBuilder(this.data.getDisplayMaterial()).displayName(this.data.getDisplayName()).lore("", ComponentModificationGUI.PLUGIN.getMessage("component-plugin", this.data.getPlugin().getDescription().getName()), ComponentModificationGUI.PLUGIN.getMessage("component-id", this.data.getIdentifier().toString()), ComponentModificationGUI.PLUGIN.getMessage("component-class", this.data.getComponentClass().getName())).build();

        ComponentModificationGUI self = this;

        PaginationWidget pagination = new PaginationWidget(0, 0, this.getWidth(), this.getHeight() - 1, !(component instanceof ConfigurableComponent) ? ComponentModificationGUI.NOT_CONFIGURABLE : null);
        if(component instanceof ConfigurableComponent) {
            List<Widget> elements = new ArrayList<>();
            for(ConfigurationElement element : ((ConfigurableComponent) component).getConfiguration()) {
                elements.add(new ButtonWidget(0, 0, 1, 1) {
                    @Override
                    protected ItemStack getItem() {
                        return element.getItem(self.getPlayer());
                    }

                    @Override
                    protected void click(ClickType type) {
                        element.onClick(self.getPlayer(), type);
                    }
                });
            }
            pagination.setItems(elements);
        }
        this.addChild(pagination);

        PaginationWidgetBarWidget w = new PaginationWidgetBarWidget(0, this.getHeight() - 1, this.getWidth(), 1, null, pagination) {
            @Override
            protected ItemStack getNextPageItem(int page, int pages) {
                return new ItemStackBuilder(Material.BLUE_STAINED_GLASS_PANE).displayName(ComponentModificationGUI.PLUGIN.getMessage("next-page", page, pages)).lore("", ComponentModificationGUI.PLUGIN.getMessage("click-next-page")).build();
            }

            @Override
            protected ItemStack getPreviousPageItem(int page, int pages) {
                return new ItemStackBuilder(Material.BLUE_STAINED_GLASS_PANE).displayName(ComponentModificationGUI.PLUGIN.getMessage("previous-page", page, pages)).lore("", ComponentModificationGUI.PLUGIN.getMessage("click-previous-page")).build();
            }
        };
        this.addChild(w);

        this.addChild(new ButtonWidget(2, this.getHeight() - 1, 1, 1) {
            @Override
            protected ItemStack getItem() {
                return ComponentModificationGUI.DELETE_COMPONENT;
            }

            @Override
            protected void click(ClickType type) {
                if(type.isShiftClick() && type.isRightClick()) {
                    ComponentManager.delete(self.component);
                    self.getPlayer().closeInventory();
                    self.getPlayer().sendMessage(ComponentModificationGUI.PLUGIN.getMessage("component-deleted"));
                }
            }
        });
        this.addChild(new ButtonWidget(4, this.getHeight() - 1, 1, 1) {
            @Override
            protected ItemStack getItem() {
                return ComponentModificationGUI.SELECT_COMPONENT;
            }

            @Override
            protected void click(ClickType type) {
                SelectedLocationsManager.setComponent(self.getPlayer(), self.component.getLocation());
                self.getPlayer().closeInventory();
                self.getPlayer().sendMessage(ComponentModificationGUI.PLUGIN.getMessage("component-selected"));
            }
        });
    }

    @Override
    public void render(GridInventory grid) {
        grid.fill(1, this.getRealY() + this.getHeight() - 1, this.getWidth() - 2, 1, ComponentModificationGUI.BAR_BACKGROUND);

        grid.set(6, this.getRealY() + this.getHeight() - 1, this.componentStack);
    }

    @Override
    public Inventory createInventory() {
        return Bukkit.createInventory(null, this.getWidth() * this.getHeight(), this.data.getDisplayName());
    }
}