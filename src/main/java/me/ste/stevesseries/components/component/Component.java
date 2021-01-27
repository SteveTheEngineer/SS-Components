package me.ste.stevesseries.components.component;

import com.google.gson.JsonObject;
import org.bukkit.entity.ItemFrame;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.BrewingStandFuelEvent;
import org.bukkit.event.inventory.FurnaceBurnEvent;
import org.bukkit.event.inventory.FurnaceExtractEvent;
import org.bukkit.event.inventory.FurnaceSmeltEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.vehicle.VehicleBlockCollisionEvent;
import org.bukkit.event.world.PortalCreateEvent;
import org.bukkit.event.world.StructureGrowEvent;

/**
 * <strong>All components MUST have a ComponentLocation constructor!</strong>
 */
public abstract class Component {
    private final ComponentLocation location;

    public Component(ComponentLocation location) {
        this.location = location;
    }

    /**
     * Get the location of the component
     * @return location of the component
     */
    public final ComponentLocation getLocation() {
        return this.location;
    }

    /**
     * Called when the component is added into the world. If your component represents a block, you'll usually want to remove the item frame. This method should be used for initially preparing the area around component
     * @param frame item frame that was used to create the component
     */
    public abstract void onInitialization(ItemFrame frame);

    /**
     * Called when the component is removed from the world. This method should be used for reverting the changes that were made by the component on initialization
     */
    public abstract void onDeletion();

    /**
     * Write the component to JSON. Must be used for persisting the component state between server restarts. <strong>This method might get called at ANY time! Use this method ONLY for saving to disk. If you need to handle the server shutdown, override the {@link Component#onUnload()} or {@link Component#onDestruction()} method depending on what you actually need!</strong>
     * @param object json object to write to
     */
    public abstract void writeToJson(JsonObject object);

    /**
     * Read the component from JSON. Must be used for persisting the component state between server restarts. <strong>This method might get called at ANY time! Use this method ONLY for saving to disk. If you need to handle the server start, override the {@link Component#onLoad()} or {@link Component#onCreation()} method depending on what you actually need!</strong>
     * @param object json object to read from
     */
    public abstract void readFromJson(JsonObject object);

    /**
     * Called <em>after</em> the component is either loaded or added into the world. This method should be used for starting tasks, spawning non-persistent entities, etc.
     */
    public void onCreation() {}

    /**
     * Called <em>before</em> the component is either unloaded or deleted from the world. This method should be used for stopping tasks, removing non-persistent entities, etc.
     */
    public void onDestruction() {}

    /**
     * Called when the component is being loaded when the server is starting. There are no known uses for this method
     */
    public void onLoad() {}

    /**
     * Called when the component is being unloaded when the server is shutting down. There are no known uses for this method
     */
    public void onUnload() {}

    /**
     * Called when the event called affects the block at the component location
     * @param event bukkit event
     */
    public void onPlayerInteract(PlayerInteractEvent event) {}

    /**
     * Called when the event called affects the block at the component location
     * @param event bukkit event
     */
    public void onBlockBreak(BlockBreakEvent event) {}

    /**
     * Called when the event called affects the block at the component location
     * @param event bukkit event
     */
    public void onBlockBurn(BlockBurnEvent event) {}

    /**
     * Called when the event called affects the block at the component location
     * @param event bukkit event
     */
    public void onBlockCanBuild(BlockCanBuildEvent event) {}

    /**
     * Called when the event called affects the block at the component location
     * @param event bukkit event
     */
    public void onBlockCook(BlockCookEvent event) {}

    /**
     * Called when the event called affects the block at the component location
     * @param event bukkit event
     */
    public void onBlockDamage(BlockDamageEvent event) {}

    /**
     * Called when the event called affects the block at the component location
     * @param event bukkit event
     */
    public void onBlockDispenseArmor(BlockDispenseArmorEvent event) {}

    /**
     * Called when the event called affects the block at the component location
     * @param event bukkit event
     */
    public void onBlockDispense(BlockDispenseEvent event) {}

    /**
     * Called when the event called affects the block at the component location
     * @param event bukkit event
     */
    public void onBlockDropItem(BlockDropItemEvent event) {}

    /**
     * Called when the event called affects the block at the component location
     * @param event bukkit event
     */
    public void onBlockExp(BlockExpEvent event) {}

    /**
     * Called when the event called affects the block at the component location
     * @param event bukkit event
     */
    public void onBlockExplode(BlockExplodeEvent event) {}

    /**
     * Called when the event called affects the block at the component location
     * @param event bukkit event
     */
    public void onBlockFade(BlockFadeEvent event) {}

    /**
     * Called when the event called affects the block at the component location
     * @param event bukkit event
     */
    public void onBlockFertilize(BlockFertilizeEvent event) {}

    /**
     * Called when the event called affects the block at the component location
     * @param event bukkit event
     */
    public void onBlockForm(BlockFormEvent event) {}

    /**
     * Called when the event called affects the block at the component location
     * @param event bukkit event
     */
    public void onBlockFromTo(BlockFromToEvent event) {}

    /**
     * Called when the event called affects the block at the component location
     * @param event bukkit event
     */
    public void onBlockGrow(BlockGrowEvent event) {}

    /**
     * Called when the event called affects the block at the component location
     * @param event bukkit event
     */
    public void onBlockIgnite(BlockIgniteEvent event) {}

    /**
     * Called when the event called affects the block at the component location
     * @param event bukkit event
     */
    public void onBlockMultiPlace(BlockMultiPlaceEvent event) {}

    /**
     * Called when the event called affects the block at the component location
     * @param event bukkit event
     */
    public void onBlockPhysics(BlockPhysicsEvent event) {}

    /**
     * Called when the event called affects the block at the component location
     * @param event bukkit event
     */
    public void onBlockPistonExtend(BlockPistonExtendEvent event) {}

    /**
     * Called when the event called affects the block at the component location
     * @param event bukkit event
     */
    public void onBlockPistonRetract(BlockPistonRetractEvent event) {}

    /**
     * Called when the event called affects the block at the component location
     * @param event bukkit event
     */
    public void onBlockPlace(BlockPlaceEvent event) {}

    /**
     * Called when the event called affects the block at the component location
     * @param event bukkit event
     */
    public void onBlockRedstone(BlockRedstoneEvent event) {}

    /**
     * Called when the event called affects the block at the component location
     * @param event bukkit event
     */
    public void onBlockShearEntity(BlockShearEntityEvent event) {}

    /**
     * Called when the event called affects the block at the component location
     * @param event bukkit event
     */
    public void onBlockSpread(BlockSpreadEvent event) {}

    /**
     * Called when the event called affects the block at the component location
     * @param event bukkit event
     */
    public void onCauldronLevelChange(CauldronLevelChangeEvent event) {}

    /**
     * Called when the event called affects the block at the component location
     * @param event bukkit event
     */
    public void onEntityBlockForm(EntityBlockFormEvent event) {}

    /**
     * Called when the event called affects the block at the component location
     * @param event bukkit event
     */
    public void onFluidLevelChange(FluidLevelChangeEvent event) {}

    /**
     * Called when the event called affects the block at the component location
     * @param event bukkit event
     */
    public void onLeavesDecay(LeavesDecayEvent event) {}

    /**
     * Called when the event called affects the block at the component location
     * @param event bukkit event
     */
    public void onMoistureChange(MoistureChangeEvent event) {}

    /**
     * Called when the event called affects the block at the component location
     * @param event bukkit event
     */
    public void onNotePlay(NotePlayEvent event) {}

    /**
     * Called when the event called affects the block at the component location
     * @param event bukkit event
     */
    public void onSignChange(SignChangeEvent event) {}

    /**
     * Called when the event called affects the block at the component location
     * @param event bukkit event
     */
    public void onSpongeAbsorb(SpongeAbsorbEvent event) {}

    /**
     * Called when the event called affects the block at the component location
     * @param event bukkit event
     */
    public void onEntityBreakDoor(EntityBreakDoorEvent event) {}

    /**
     * Called when the event called affects the block at the component location
     * @param event bukkit event
     */
    public void onEntityChangeBlock(EntityChangeBlockEvent event) {}

    /**
     * Called when the event called affects the block at the component location
     * @param event bukkit event
     */
    public void onEntityCombustByBlock(EntityCombustByBlockEvent event) {}

    /**
     * Called when the event called affects the block at the component location
     * @param event bukkit event
     */
    public void onEntityDamageByBlock(EntityDamageByBlockEvent event) {}

    /**
     * Called when the event called affects the block at the component location
     * @param event bukkit event
     */
    public void onEntityEnterBlock(EntityEnterBlockEvent event) {}

    /**
     * Called when the event called affects the block at the component location
     * @param event bukkit event
     */
    public void onEntityExplode(EntityExplodeEvent event) {}

    /**
     * Called when the event called affects the block at the component location
     * @param event bukkit event
     */
    public void onEntityInteract(EntityInteractEvent event) {}

    /**
     * Called when the event called affects the block at the component location
     * @param event bukkit event
     */
    public void onBrewingStandFuel(BrewingStandFuelEvent event) {}

    /**
     * Called when the event called affects the block at the component location
     * @param event bukkit event
     */
    public void onFurnaceBurn(FurnaceBurnEvent event) {}

    /**
     * Called when the event called affects the block at the component location
     * @param event bukkit event
     */
    public void onFurnaceExtract(FurnaceExtractEvent event) {}

    /**
     * Called when the event called affects the block at the component location
     * @param event bukkit event
     */
    public void onFurnaceSmelt(FurnaceSmeltEvent event) {}

    /**
     * Called when the event called affects the block at the component location
     * @param event bukkit event
     */
    public void onPlayerBedEnter(PlayerBedEnterEvent event) {}

    /**
     * Called when the event called affects the block at the component location
     * @param event bukkit event
     */
    public void onPlayerBedLeave(PlayerBedLeaveEvent event) {}

    /**
     * Called when the event called affects the block at the component location
     * @param event bukkit event
     */
    public void onPlayerBucketEmpty(PlayerBucketEmptyEvent event) {}

    /**
     * Called when the event called affects the block at the component location
     * @param event bukkit event
     */
    public void onPlayerBucketFill(PlayerBucketFillEvent event) {}

    /**
     * Called when the event called affects the block at the component location
     * @param event bukkit event
     */
    public void onPlayerHarvestBlock(PlayerHarvestBlockEvent event) {}

    /**
     * Called when the event called affects the block at the component location
     * @param event bukkit event
     */
    public void onVehicleBlockCollision(VehicleBlockCollisionEvent event) {}

    /**
     * Called when the event called affects the block at the component location
     * @param event bukkit event
     */
    public void onPortalCreate(PortalCreateEvent event) {}

    /**
     * Called when the event called affects the block at the component location
     * @param event bukkit event
     */
    public void onStructureGrow(StructureGrowEvent event) {}
}