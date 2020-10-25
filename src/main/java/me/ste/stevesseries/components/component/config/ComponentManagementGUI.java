package me.ste.stevesseries.components.component.config;

import me.ste.stevesseries.base.ChatInputManager;
import me.ste.stevesseries.base.ItemStackBuilder;
import me.ste.stevesseries.components.Components;
import me.ste.stevesseries.components.component.Component;
import me.ste.stevesseries.components.component.ComponentLocation;
import me.ste.stevesseries.components.component.ComponentManager;
import me.ste.stevesseries.inventoryguilibrary.GUI;
import me.ste.stevesseries.inventoryguilibrary.InventoryGUILibrary;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class ComponentManagementGUI extends GUI {
    private static final ItemStack REMOVE_COMPONENT_STACK = new ItemStackBuilder(Material.RED_STAINED_GLASS_PANE).displayName("&cREMOVE COMPONENT").build();
    private static final ItemStack SELECT_COMPONENT_STACK = new ItemStackBuilder(Material.ORANGE_STAINED_GLASS_PANE).displayName("Select component").build();
    private static final ItemStack CANNOT_BE_CONFIGURED_STACK = new ItemStackBuilder(Material.BLACK_STAINED_GLASS_PANE).displayName("This component cannot be configured").build();

    private static final List<String> CONFIG_GENERIC_LORE = Arrays.asList("", "&eClick to change");
    private static final List<String> CONFIG_COMPONENT_LORE = Arrays.asList("", "&eLeft click to bind selected component", "&eRight click to unbind");
    private static final List<String> CONFIG_LOCATION_LORE = Arrays.asList("", "&eLeft click to bind selected location", "&eRight click to unbind");
    private static final List<String> CONFIG_INTEGER_LORE = Arrays.asList("", "&eLeft click to add 1", "&eRight click to remove 1", "", "&eHold shift to make added/removed value 10");
    private static final List<String> CONFIG_DOUBLE_LORE = Arrays.asList("", "&eLeft click to add 0.1", "&eRight click to remove 0.1", "", "&eHold shift to make added/removed value 1");

    private final Components plugin = Components.getPlugin(Components.class);

    private final Component component;
    private int page;
    private int pages;

    public ComponentManagementGUI(Player player, Component component) {
        super(player, 9 * 3, ComponentManager.getInstance().getDisplayName(component.getClass()));
        this.component = component;
        this.pages = 1;
    }

    @Override
    public void updateInventory(Inventory inventory) {
        if(this.component instanceof ConfigurableComponent) {
            ConfigurableComponent configurableComponent = (ConfigurableComponent) this.component;
            this.pages = (int) Math.max(1, Math.ceil(configurableComponent.getCurrentConfig().entrySet().size() / 18D));
        }

        GUI.fillItems(inventory, 0, 2, 9, 1, new ItemStackBuilder(Material.GRAY_STAINED_GLASS_PANE).displayName("").build());
        inventory.setItem(GUI.getGridPositionIndex(3, 2), ComponentManagementGUI.REMOVE_COMPONENT_STACK);
        inventory.setItem(GUI.getGridPositionIndex(5, 2), ComponentManagementGUI.SELECT_COMPONENT_STACK);
        inventory.setItem(GUI.getGridPositionIndex(0, 2), new ItemStackBuilder(Material.BLUE_STAINED_GLASS_PANE).displayName(String.format("Previous page &7(%d/%d)", this.page + 1, this.pages)).build());
        inventory.setItem(GUI.getGridPositionIndex(8, 2), new ItemStackBuilder(Material.BLUE_STAINED_GLASS_PANE).displayName(String.format("Next page &7(%d/%d)", this.page + 1, this.pages)).build());

        if(this.component instanceof ConfigurableComponent) {
            ConfigurableComponent configurableComponent = (ConfigurableComponent) this.component;
            int i = 0;
            for(Map.Entry<ConfigKey, Object> config : new ArrayList<>(configurableComponent.getCurrentConfig().entrySet()).subList(this.page * 18, Math.min(configurableComponent.getCurrentConfig().entrySet().size(), (this.page + 1) * 18))) {
                Material itemMaterial = Material.WHITE_CONCRETE;
                String itemName = config.getKey().getName();
                ConfigType type = config.getKey().getType();
                List<String> lore = ComponentManagementGUI.CONFIG_GENERIC_LORE;
                if(type == ConfigType.BOOLEAN) {
                    boolean value = (boolean) config.getValue();
                    itemName += String.format(": %s", value ? "&a+" : "&c-");
                    itemMaterial = value ? Material.LIME_CONCRETE : Material.RED_CONCRETE;
                } else if(type == ConfigType.INTEGER) {
                    int value = (int) config.getValue();
                    itemName += String.format(": %d", value);
                    itemMaterial = Material.BLUE_CONCRETE;
                    lore = ComponentManagementGUI.CONFIG_INTEGER_LORE;
                } else if(type == ConfigType.DOUBLE) {
                    double value = (double) config.getValue();
                    itemName += String.format(": %s", value);
                    itemMaterial = Material.LIGHT_BLUE_CONCRETE;
                    lore = ComponentManagementGUI.CONFIG_DOUBLE_LORE;
                } else if(type == ConfigType.STRING) {
                    String value = (String) config.getValue();
                    itemName += String.format(": &l\"&r%s&l\"", value);
                    itemMaterial = Material.ORANGE_CONCRETE;
                } else if(type == ConfigType.COMPONENT) {
                    ComponentLocation value = (ComponentLocation) config.getValue();
                    Component component = value != null ? ComponentManager.getInstance().getComponent(value.getLocation(), value.getFace()) : null;
                    String name = "&cUnknown&r";
                    if(component != null) {
                        name = ComponentManager.getInstance().getFriendlyName(component.getClass());
                        if(name == null) {
                            name = component.getClass().getSimpleName();
                        }
                    }
                    itemName += value != null ? String.format(": %s | %s @ %d, %d, %d. %s", name, Objects.requireNonNull(value.getLocation().getWorld()).getName(), value.getLocation().getBlockX(), value.getLocation().getBlockY(), value.getLocation().getBlockZ(), value.getFace().name()) : ": None";
                    itemMaterial = Material.CYAN_CONCRETE;
                    lore = ComponentManagementGUI.CONFIG_COMPONENT_LORE;
                    if (ComponentManager.getInstance().getSelectedComponent(this.getPlayer()) == null) {
                        lore = new ArrayList<>(lore);
                        lore.set(1, "&e&mLeft click to bind selected component");
                    }
                } else if(type == ConfigType.LOCATION) {
                    Location value = (Location) config.getValue();
                    itemName += value != null ? String.format(": %s @ %d, %d, %d", Objects.requireNonNull(value.getWorld()).getName(), value.getBlockX(), value.getBlockY(), value.getBlockZ()) : ": None";
                    itemMaterial = Material.YELLOW_CONCRETE;
                    lore = ComponentManagementGUI.CONFIG_LOCATION_LORE;
                    if (ComponentManager.getInstance().getSelectedComponent(this.getPlayer()) == null) {
                        lore = new ArrayList<>(lore);
                        lore.set(1, "&e&mLeft click to bind selected location");
                    }
                } else if(type == ConfigType.CUSTOM) {
                    if(config.getValue() != null) {
                        itemName += String.format(": %s",  (String) config.getValue());
                    }
                    itemMaterial = Material.LIGHT_GRAY_CONCRETE;
                } else if(type == ConfigType.READONLY) {
                    String value = (String) config.getValue();
                    itemName += String.format(": %s", value);
                    itemMaterial = Material.PURPLE_CONCRETE;
                }
                inventory.setItem(i, new ItemStackBuilder(itemMaterial).displayName(itemName).lore(lore).build());
                i++;
            }
        } else {
            GUI.fillItems(inventory, 0, 0, 9, 2, ComponentManagementGUI.CANNOT_BE_CONFIGURED_STACK);
        }
    }

    @Override
    public void handleClick(ItemStack stack, ClickType clickType, int slot, Inventory inventory) {
        super.handleClick(stack, clickType, slot, inventory);

        if(clickType == ClickType.DOUBLE_CLICK) {
            return;
        }

        if(slot == GUI.getGridPositionIndex(3, 2)) {
            this.component.remove();
            this.getPlayer().sendMessage(ChatColor.RED + "Component has been removed");
            this.getPlayer().closeInventory();
        } else if (slot == GUI.getGridPositionIndex(5, 2)) {
            ComponentManager.getInstance().setSelectedComponent(this.getPlayer(), this.component);
            this.getPlayer().sendMessage(ChatColor.GOLD + "Component has been selected");
            this.getPlayer().closeInventory();
        } else if(slot == GUI.getGridPositionIndex(8, 2)) {
            if(this.page < this.pages - 1) {
                this.page++;
                this.refresh();
            }
        } else if(slot == GUI.getGridPositionIndex(0, 2)) {
            if(this.page > 0) {
                this.page--;
                this.refresh();
            }
        } else if(this.component instanceof ConfigurableComponent && slot >= 0 && slot <= GUI.getGridPositionIndex(8, 1)) {
            ConfigurableComponent configurableComponent = (ConfigurableComponent) this.component;
            List<Map.Entry<ConfigKey, Object>> entries = new ArrayList<>(configurableComponent.getCurrentConfig().entrySet());
            int index = this.page * 18 + slot;
            if(entries.size() >= index) {
                Map.Entry<ConfigKey, Object> config = entries.get(index);
                if(config != null) {
                    ConfigType type = config.getKey().getType();
                    if(type == ConfigType.BOOLEAN) {
                        configurableComponent.configure(config.getKey(), !(boolean) config.getValue());
                    } else if(type == ConfigType.INTEGER) {
                        int newVal = (int) config.getValue();
                        int modVal = clickType.isShiftClick() ? 10 : 1;
                        if(clickType.isLeftClick()) {
                            newVal += modVal;
                        } else if(clickType.isRightClick()) {
                            newVal -= modVal;
                        }
                        configurableComponent.configure(config.getKey(), newVal);
                    } else if(type == ConfigType.DOUBLE) {
                        double newVal = (double) config.getValue();
                        double modVal = clickType.isShiftClick() ? 1 : 0.1;
                        if(clickType.isLeftClick()) {
                            newVal += modVal;
                        } else if(clickType.isRightClick()) {
                            newVal -= modVal;
                        }
                        configurableComponent.configure(config.getKey(), newVal);
                    } else if(type == ConfigType.COMPONENT) {
                        if(clickType.isLeftClick()) {
                            Component selected = ComponentManager.getInstance().getSelectedComponent(this.getPlayer());
                            if(selected != null) {
                                configurableComponent.configure(config.getKey(), new ComponentLocation(selected.getLocation(), selected.getFace()));
                            }
                        } else if(clickType.isRightClick()) {
                            configurableComponent.configure(config.getKey(), null);
                        }
                    } else if(type == ConfigType.LOCATION) {
                        if(clickType.isLeftClick()) {
                            Location selected = this.plugin.getSelectedLocation(this.getPlayer());
                            if(selected != null) {
                                configurableComponent.configure(config.getKey(), selected);
                            }
                        } else if(clickType.isRightClick()) {
                            configurableComponent.configure(config.getKey(), null);
                        }
                    } else if(type == ConfigType.CUSTOM) {
                        configurableComponent.customAction(this.getPlayer(), clickType, config.getKey());
                    } else if(type == ConfigType.STRING) {
                        this.getPlayer().sendMessage(ChatColor.YELLOW + "Type the new string value:");
                        ChatInputManager.input(this.getPlayer()).thenAccept(value -> {
                            configurableComponent.configure(config.getKey(), value);
                            this.getPlayer().sendMessage(ChatColor.YELLOW + "The value has been set");
                            InventoryGUILibrary.getInstance().openGUI(this.getPlayer(), this);
                        });
                        this.getPlayer().closeInventory();
                    }
                    this.refresh();
                }
            }
        }
    }
}