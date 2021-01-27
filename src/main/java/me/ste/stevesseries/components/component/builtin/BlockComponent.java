package me.ste.stevesseries.components.component.builtin;

import me.ste.stevesseries.components.Components;
import me.ste.stevesseries.components.component.Component;
import me.ste.stevesseries.components.component.ComponentLocation;
import me.ste.stevesseries.components.gui.ComponentModificationGUI;
import me.ste.stevesseries.inventoryguilibrary.GUIManager;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.ItemFrame;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.BrewingStandFuelEvent;
import org.bukkit.event.inventory.FurnaceBurnEvent;
import org.bukkit.event.inventory.FurnaceExtractEvent;
import org.bukkit.event.inventory.FurnaceSmeltEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.world.PortalCreateEvent;
import org.bukkit.event.world.StructureGrowEvent;

public abstract class BlockComponent extends Component {
    private static final Components PLUGIN = Components.getPlugin(Components.class);

    private final Material blockType;

    public BlockComponent(ComponentLocation location, Material blockType) {
        super(location);
        this.blockType = blockType;
    }

    @Override
    public void onInitialization(ItemFrame frame) {
        frame.remove();
        this.getLocation().getLocation().getBlock().setType(this.blockType);
    }

    @Override
    public void onDeletion() {
        this.getLocation().getLocation().getBlock().setType(Material.AIR);
    }

    @Override
    public void onBlockBreak(BlockBreakEvent event) {
        event.setCancelled(true);
    }

    @Override
    public void onPlayerInteract(PlayerInteractEvent event) {
        event.setCancelled(true);

        if(event.getAction() == Action.LEFT_CLICK_BLOCK && event.getPlayer().getGameMode() == GameMode.CREATIVE) {
            if(event.getPlayer().hasPermission("stevesseries.components.modify")) {
                GUIManager.open(event.getPlayer(), new ComponentModificationGUI(this));
            } else {
                event.getPlayer().sendMessage(BlockComponent.PLUGIN.getMessage("no-permission"));
            }
        }
    }

    @Override
    public void onBlockBurn(BlockBurnEvent event) {
        event.setCancelled(true);
    }

    @Override
    public void onBlockCook(BlockCookEvent event) {
        event.setCancelled(true);
    }

    @Override
    public void onBlockDispense(BlockDispenseEvent event) {
        event.setCancelled(true);
    }

    @Override
    public void onBlockDropItem(BlockDropItemEvent event) {
        event.setCancelled(true);
    }

    @Override
    public void onBlockExplode(BlockExplodeEvent event) {
        event.blockList().removeIf(block -> block.getLocation().equals(this.getLocation().getLocation()));
    }

    @Override
    public void onBlockFade(BlockFadeEvent event) {
        event.setCancelled(true);
    }

    @Override
    public void onBlockFertilize(BlockFertilizeEvent event) {
        event.setCancelled(true);
    }

    @Override
    public void onBlockForm(BlockFormEvent event) {
        event.setCancelled(true);
    }

    @Override
    public void onBlockFromTo(BlockFromToEvent event) {
        event.setCancelled(true);
    }

    @Override
    public void onBlockGrow(BlockGrowEvent event) {
        event.setCancelled(true);
    }

    @Override
    public void onBlockIgnite(BlockIgniteEvent event) {
        event.setCancelled(true);
    }

    @Override
    public void onBlockMultiPlace(BlockMultiPlaceEvent event) {
        event.setCancelled(true);
    }

    @Override
    public void onBlockPhysics(BlockPhysicsEvent event) {
        event.setCancelled(true);
    }

    @Override
    public void onBlockPistonExtend(BlockPistonExtendEvent event) {
        event.setCancelled(true);
    }

    @Override
    public void onBlockPistonRetract(BlockPistonRetractEvent event) {
        event.setCancelled(true);
    }

    @Override
    public void onBlockShearEntity(BlockShearEntityEvent event) {
        event.setCancelled(true);
    }

    @Override
    public void onBlockSpread(BlockSpreadEvent event) {
        event.setCancelled(true);
    }

    @Override
    public void onCauldronLevelChange(CauldronLevelChangeEvent event) {
        event.setCancelled(true);
    }

    @Override
    public void onEntityBlockForm(EntityBlockFormEvent event) {
        event.setCancelled(true);
    }

    @Override
    public void onFluidLevelChange(FluidLevelChangeEvent event) {
        event.setCancelled(true);
    }

    @Override
    public void onLeavesDecay(LeavesDecayEvent event) {
        event.setCancelled(true);
    }

    @Override
    public void onMoistureChange(MoistureChangeEvent event) {
        event.setCancelled(true);
    }

    @Override
    public void onNotePlay(NotePlayEvent event) {
        event.setCancelled(true);
    }

    @Override
    public void onSignChange(SignChangeEvent event) {
        event.setCancelled(true);
    }

    @Override
    public void onSpongeAbsorb(SpongeAbsorbEvent event) {
        event.setCancelled(true);
    }

    @Override
    public void onEntityBreakDoor(EntityBreakDoorEvent event) {
        event.setCancelled(true);
    }

    @Override
    public void onEntityChangeBlock(EntityChangeBlockEvent event) {
        event.setCancelled(true);
    }

    @Override
    public void onEntityEnterBlock(EntityEnterBlockEvent event) {
        event.setCancelled(true);
    }

    @Override
    public void onEntityExplode(EntityExplodeEvent event) {
        event.blockList().removeIf(block -> block.getLocation().equals(this.getLocation().getLocation()));
    }

    @Override
    public void onEntityInteract(EntityInteractEvent event) {
        event.setCancelled(true);
    }

    @Override
    public void onBrewingStandFuel(BrewingStandFuelEvent event) {
        event.setCancelled(true);
    }

    @Override
    public void onFurnaceBurn(FurnaceBurnEvent event) {
        event.setCancelled(true);
    }

    @Override
    public void onFurnaceSmelt(FurnaceSmeltEvent event) {
        event.setCancelled(true);
    }

    @Override
    public void onPlayerBedEnter(PlayerBedEnterEvent event) {
        event.setCancelled(true);
    }

    @Override
    public void onPlayerBucketEmpty(PlayerBucketEmptyEvent event) {
        event.setCancelled(true);
    }

    @Override
    public void onPlayerBucketFill(PlayerBucketFillEvent event) {
        event.setCancelled(true);
    }

    @Override
    public void onPortalCreate(PortalCreateEvent event) {
        event.setCancelled(true);
    }

    @Override
    public void onStructureGrow(StructureGrowEvent event) {
        event.setCancelled(true);
    }
}