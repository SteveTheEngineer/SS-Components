package me.ste.stevesseries.components.listener;

import me.ste.stevesseries.components.component.ComponentManager;
import me.ste.stevesseries.components.component.Component;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.*;
import org.bukkit.event.vehicle.VehicleBlockCollisionEvent;
import org.bukkit.event.weather.LightningStrikeEvent;
import org.bukkit.event.world.PortalCreateEvent;
import org.bukkit.event.world.StructureGrowEvent;

public class ComponentBlockListener implements Listener {
    @EventHandler(ignoreCancelled = true)
    public void onPlayerInteract(PlayerInteractEvent event) {
        Event.Result useInteractedBlock = event.useInteractedBlock();
        Event.Result useItemInHand = event.useItemInHand();
        Block block = event.getClickedBlock();
        if(block != null) {
            for(Component component: ComponentManager.getComponentsAt(block.getLocation())) {
                component.onPlayerInteract(event);
                useInteractedBlock = Event.Result.values()[Math.min(event.useInteractedBlock().ordinal(), useInteractedBlock.ordinal())];
                useItemInHand = Event.Result.values()[Math.min(event.useItemInHand().ordinal(), useItemInHand.ordinal())];
            }
        }
        event.setUseInteractedBlock(useInteractedBlock);
        event.setUseItemInHand(useItemInHand);
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {
        for(Component component : ComponentManager.getComponentsAt(event.getBlock().getLocation())) {
            component.onBlockBreak(event);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockBurn(BlockBurnEvent event) {
        for(Component component : ComponentManager.getComponentsAt(event.getBlock().getLocation())) {
            component.onBlockBurn(event);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockCanBuild(BlockCanBuildEvent event) {
        for(Component component : ComponentManager.getComponentsAt(event.getBlock().getLocation())) {
            component.onBlockCanBuild(event);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockCook(BlockCookEvent event) {
        for(Component component : ComponentManager.getComponentsAt(event.getBlock().getLocation())) {
            component.onBlockCook(event);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockDamage(BlockDamageEvent event) {
        for(Component component : ComponentManager.getComponentsAt(event.getBlock().getLocation())) {
            component.onBlockDamage(event);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockDispenseArmor(BlockDispenseArmorEvent event) {
        for(Component component : ComponentManager.getComponentsAt(event.getBlock().getLocation())) {
            component.onBlockDispenseArmor(event);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockDispense(BlockDispenseEvent event) {
        for(Component component : ComponentManager.getComponentsAt(event.getBlock().getLocation())) {
            component.onBlockDispense(event);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockDropItem(BlockDropItemEvent event) {
        for(Component component : ComponentManager.getComponentsAt(event.getBlock().getLocation())) {
            component.onBlockDropItem(event);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockExp(BlockExpEvent event) {
        for(Component component : ComponentManager.getComponentsAt(event.getBlock().getLocation())) {
            component.onBlockExp(event);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockExplode(BlockExplodeEvent event) {
        for(Component component : ComponentManager.getComponentsAt(event.getBlock().getLocation())) {
            component.onBlockExplode(event);
        }
        for(Block block : event.blockList()) {
            for(Component component : ComponentManager.getComponentsAt(block.getLocation())) {
                component.onBlockExplode(event);
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockFade(BlockFadeEvent event) {
        for(Component component : ComponentManager.getComponentsAt(event.getBlock().getLocation())) {
            component.onBlockFade(event);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockFertilize(BlockFertilizeEvent event) {
        for(Component component : ComponentManager.getComponentsAt(event.getBlock().getLocation())) {
            component.onBlockFertilize(event);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockForm(BlockFormEvent event) {
        for(Component component : ComponentManager.getComponentsAt(event.getBlock().getLocation())) {
            component.onBlockForm(event);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockFromTo(BlockFromToEvent event) {
        for(Component component : ComponentManager.getComponentsAt(event.getBlock().getLocation())) {
            component.onBlockFromTo(event);
        }
        for(Component component : ComponentManager.getComponentsAt(event.getToBlock().getLocation())) {
            component.onBlockFromTo(event);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockGrow(BlockGrowEvent event) {
        for(Component component : ComponentManager.getComponentsAt(event.getBlock().getLocation())) {
            component.onBlockGrow(event);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockIgnite(BlockIgniteEvent event) {
        for(Component component : ComponentManager.getComponentsAt(event.getBlock().getLocation())) {
            component.onBlockIgnite(event);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockMultiPlace(BlockMultiPlaceEvent event) {
        for(Component component : ComponentManager.getComponentsAt(event.getBlock().getLocation())) {
            component.onBlockMultiPlace(event);
        }
        for(BlockState block : event.getReplacedBlockStates()) {
            for(Component component : ComponentManager.getComponentsAt(block.getLocation())) {
                component.onBlockMultiPlace(event);
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockPhysics(BlockPhysicsEvent event) {
        for(Component component : ComponentManager.getComponentsAt(event.getBlock().getLocation())) {
            component.onBlockPhysics(event);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockPistonExtend(BlockPistonExtendEvent event) {
        for(Component component : ComponentManager.getComponentsAt(event.getBlock().getLocation())) {
            component.onBlockPistonExtend(event);
        }
        for(Block block : event.getBlocks()) {
            for(Component component : ComponentManager.getComponentsAt(block.getLocation())) {
                component.onBlockPistonExtend(event);
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockPistonRetract(BlockPistonRetractEvent event) {
        for(Component component : ComponentManager.getComponentsAt(event.getBlock().getLocation())) {
            component.onBlockPistonRetract(event);
        }
        for(Block block : event.getBlocks()) {
            for(Component component : ComponentManager.getComponentsAt(block.getLocation())) {
                component.onBlockPistonRetract(event);
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockPlace(BlockPlaceEvent event) {
        for(Component component : ComponentManager.getComponentsAt(event.getBlock().getLocation())) {
            component.onBlockPlace(event);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockRedstone(BlockRedstoneEvent event) {
        for(Component component : ComponentManager.getComponentsAt(event.getBlock().getLocation())) {
            component.onBlockRedstone(event);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockShearEntity(BlockShearEntityEvent event) {
        for(Component component : ComponentManager.getComponentsAt(event.getBlock().getLocation())) {
            component.onBlockShearEntity(event);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockSpread(BlockSpreadEvent event) {
        for(Component component : ComponentManager.getComponentsAt(event.getBlock().getLocation())) {
            component.onBlockSpread(event);
        }
        for(Component component : ComponentManager.getComponentsAt(event.getSource().getLocation())) {
            component.onBlockSpread(event);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onCauldronLevelChange(CauldronLevelChangeEvent event) {
        for(Component component : ComponentManager.getComponentsAt(event.getBlock().getLocation())) {
            component.onCauldronLevelChange(event);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onEntityBlockForm(EntityBlockFormEvent event) {
        for(Component component : ComponentManager.getComponentsAt(event.getBlock().getLocation())) {
            component.onEntityBlockForm(event);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onFluidLevelChange(FluidLevelChangeEvent event) {
        for(Component component : ComponentManager.getComponentsAt(event.getBlock().getLocation())) {
            component.onFluidLevelChange(event);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onLeavesDecay(LeavesDecayEvent event) {
        for(Component component : ComponentManager.getComponentsAt(event.getBlock().getLocation())) {
            component.onLeavesDecay(event);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onMoistureChange(MoistureChangeEvent event) {
        for(Component component : ComponentManager.getComponentsAt(event.getBlock().getLocation())) {
            component.onMoistureChange(event);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onNotePlay(NotePlayEvent event) {
        for(Component component : ComponentManager.getComponentsAt(event.getBlock().getLocation())) {
            component.onNotePlay(event);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onSignChange(SignChangeEvent event) {
        for(Component component : ComponentManager.getComponentsAt(event.getBlock().getLocation())) {
            component.onSignChange(event);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onSpongeAbsorb(SpongeAbsorbEvent event) {
        for(Component component : ComponentManager.getComponentsAt(event.getBlock().getLocation())) {
            component.onSpongeAbsorb(event);
        }
        for(BlockState block : event.getBlocks()) {
            for(Component component : ComponentManager.getComponentsAt(block.getLocation())) {
                component.onSpongeAbsorb(event);
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onEntityBreakDoor(EntityBreakDoorEvent event) {
        for(Component component : ComponentManager.getComponentsAt(event.getBlock().getLocation())) {
            component.onEntityBreakDoor(event);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onEntityChangeBlock(EntityChangeBlockEvent event) {
        for(Component component : ComponentManager.getComponentsAt(event.getBlock().getLocation())) {
            component.onEntityChangeBlock(event);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onEntityCombustByBlock(EntityCombustByBlockEvent event) {
        if(event.getCombuster() != null) {
            for(Component component : ComponentManager.getComponentsAt(event.getCombuster().getLocation())) {
                component.onEntityCombustByBlock(event);
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onEntityDamageByBlock(EntityDamageByBlockEvent event) {
        if(event.getDamager() != null) {
            for (Component component : ComponentManager.getComponentsAt(event.getDamager().getLocation())) {
                component.onEntityDamageByBlock(event);
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onEntityEnterBlock(EntityEnterBlockEvent event) {
        for(Component component : ComponentManager.getComponentsAt(event.getBlock().getLocation())) {
            component.onEntityEnterBlock(event);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onEntityExplode(EntityExplodeEvent event) {
        for(Block block : event.blockList()) {
            for(Component component : ComponentManager.getComponentsAt(block.getLocation())) {
                component.onEntityExplode(event);
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onEntityInteract(EntityInteractEvent event) {
        for(Component component : ComponentManager.getComponentsAt(event.getBlock().getLocation())) {
            component.onEntityInteract(event);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onBrewingStandFuel(BrewingStandFuelEvent event) {
        for(Component component : ComponentManager.getComponentsAt(event.getBlock().getLocation())) {
            component.onBrewingStandFuel(event);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onFurnaceBurn(FurnaceBurnEvent event) {
        for(Component component : ComponentManager.getComponentsAt(event.getBlock().getLocation())) {
            component.onFurnaceBurn(event);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onFurnaceExtract(FurnaceExtractEvent event) {
        for(Component component : ComponentManager.getComponentsAt(event.getBlock().getLocation())) {
            component.onFurnaceExtract(event);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onFurnaceSmelt(FurnaceSmeltEvent event) {
        for(Component component : ComponentManager.getComponentsAt(event.getBlock().getLocation())) {
            component.onFurnaceSmelt(event);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerBedEnter(PlayerBedEnterEvent event) {
        for(Component component : ComponentManager.getComponentsAt(event.getBed().getLocation())) {
            component.onPlayerBedEnter(event);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerBedLeave(PlayerBedLeaveEvent event) {
        for(Component component : ComponentManager.getComponentsAt(event.getBed().getLocation())) {
            component.onPlayerBedLeave(event);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerBucketEmpty(PlayerBucketEmptyEvent event) {
        for(Component component : ComponentManager.getComponentsAt(event.getBlock().getLocation())) {
            component.onPlayerBucketEmpty(event);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerBucketFill(PlayerBucketFillEvent event) {
        for(Component component : ComponentManager.getComponentsAt(event.getBlock().getLocation())) {
            component.onPlayerBucketFill(event);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerHarvestBlock(PlayerHarvestBlockEvent event) {
        for(Component component : ComponentManager.getComponentsAt(event.getHarvestedBlock().getLocation())) {
            component.onPlayerHarvestBlock(event);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onVehicleBlockCollision(VehicleBlockCollisionEvent event) {
        for(Component component : ComponentManager.getComponentsAt(event.getBlock().getLocation())) {
            component.onVehicleBlockCollision(event);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onPortalCreate(PortalCreateEvent event) {
        for(BlockState block : event.getBlocks()) {
            for(Component component : ComponentManager.getComponentsAt(block.getLocation())) {
                component.onPortalCreate(event);
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onStructureGrow(StructureGrowEvent event) {
        for(Component component : ComponentManager.getComponentsAt(event.getLocation())) {
            component.onStructureGrow(event);
        }
        for(BlockState block : event.getBlocks()) {
            for(Component component : ComponentManager.getComponentsAt(block.getLocation())) {
                component.onStructureGrow(event);
            }
        }
    }
}