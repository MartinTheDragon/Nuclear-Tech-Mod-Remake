package at.martinthedragon.nucleartech.blocks.entities

import at.martinthedragon.nucleartech.NuclearTech
import at.martinthedragon.nucleartech.SoundEvents
import at.martinthedragon.nucleartech.api.blocks.entities.BombBlockEntity
import at.martinthedragon.nucleartech.api.blocks.entities.TickingServerBlockEntity
import at.martinthedragon.nucleartech.api.items.TargetDesignator
import at.martinthedragon.nucleartech.blocks.multi.MultiBlockPart
import at.martinthedragon.nucleartech.energy.EnergyStorageExposed
import at.martinthedragon.nucleartech.energy.transferEnergy
import at.martinthedragon.nucleartech.items.MissileItem
import at.martinthedragon.nucleartech.math.toVec3Middle
import at.martinthedragon.nucleartech.menus.LaunchPadMenu
import at.martinthedragon.nucleartech.networking.LaunchPadMissileMessage
import at.martinthedragon.nucleartech.networking.NuclearPacketHandler
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.core.NonNullList
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.chat.TranslatableComponent
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket
import net.minecraft.sounds.SoundSource
import net.minecraft.world.ContainerHelper
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.ContainerData
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.AABB
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.util.LazyOptional
import net.minecraftforge.energy.CapabilityEnergy
import net.minecraftforge.items.CapabilityItemHandler
import net.minecraftforge.items.ItemStackHandler
import net.minecraftforge.network.PacketDistributor
import net.minecraftforge.network.PacketDistributor.TargetPoint
import java.util.function.Supplier

class LaunchPadBlockEntity(pos: BlockPos, state: BlockState) : BaseContainerBlockEntity(BlockEntityTypes.launchPadBlockEntityType.get(), pos, state), BombBlockEntity<LaunchPadBlockEntity>, TickingServerBlockEntity {
    var energy: Int
        get() = energyStorage.energyStored
        set(value) { energyStorage.energy = value }

    private val dataAccess = object : ContainerData {
        override fun get(index: Int) = when (index) {
            0 -> energy
            else -> 0
        }

        override fun set(index: Int, value: Int) {
            when (index) {
                0 -> energy = value
            }
        }

        override fun getCount() = 1
    }

    var missileItem: ItemStack = ItemStack.EMPTY

    private val items = NonNullList.withSize(3, ItemStack.EMPTY)
    private val inventory = object : ItemStackHandler(items) {
        override fun onContentsChanged(slot: Int) {
            if (slot == 0) {
                missileItem = getStackInSlot(0)
                updateMissileClient()
            }
            setChanged()
        }

        override fun isItemValid(slot: Int, stack: ItemStack): Boolean = when (slot) {
            0 -> stack.item is MissileItem<*>
            1 -> stack.item is TargetDesignator
            2 -> stack.getCapability(CapabilityEnergy.ENERGY).isPresent
            else -> false
        }
    }
    private val energyStorage = EnergyStorageExposed(MAX_ENERGY, 1000, 0)

    override fun load(tag: CompoundTag) {
        super.load(tag)
        items.clear()
        ContainerHelper.loadAllItems(tag, items)
        energy = tag.getInt("Energy")
        missileItem = items[0]
    }

    override fun saveAdditional(tag: CompoundTag) {
        super.saveAdditional(tag)
        ContainerHelper.saveAllItems(tag, items)
        tag.putInt("Energy", energy)
    }

    override fun getUpdateTag() = CompoundTag().apply {
        val missileTag = CompoundTag()
        items[0].save(missileTag)
        put("MissileItem", missileTag)
    }

    // FIXME doesn't handle when updating data via command
    override fun handleUpdateTag(tag: CompoundTag) {
        missileItem = ItemStack.of(tag.getCompound("MissileItem"))
    }

    private fun updateMissileClient() {
        if (hasLevel() && !level!!.isClientSide) NuclearPacketHandler.INSTANCE.send(
            PacketDistributor.NEAR.with(TargetPoint.p(blockPos.x.toDouble(), blockPos.y.toDouble(), blockPos.z.toDouble(), 512.0, level!!.dimension())),
            LaunchPadMissileMessage(blockPos, missileItem)
        )
    }

    override fun getUpdatePacket(): ClientboundBlockEntityDataPacket = ClientboundBlockEntityDataPacket.create(this)

    override fun clearContent() { items.clear() }
    override fun getContainerSize() = items.size
    override fun isEmpty() = items.all { it.isEmpty } && energy == 0
    override fun getItem(slot: Int): ItemStack = inventory.getStackInSlot(slot)
    override fun removeItem(slot: Int, amount: Int): ItemStack = inventory.extractItem(slot, amount, false)
    override fun removeItemNoUpdate(slot: Int): ItemStack = ContainerHelper.takeItem(items, slot)

    override fun setItem(slot: Int, itemStack: ItemStack) {
        itemStack.count = itemStack.count.coerceAtMost(maxStackSize)
        inventory.setStackInSlot(slot, itemStack)
        setChanged()
    }

    override fun stillValid(player: Player): Boolean = if (level!!.getBlockEntity(worldPosition) != this) false else player.distanceToSqr(worldPosition.toVec3Middle()) <= 64

    override fun createMenu(windowID: Int, playerInventory: Inventory) = LaunchPadMenu(windowID, playerInventory, this, dataAccess)

    override fun getDefaultName() = TranslatableComponent("container.${NuclearTech.MODID}.launch_pad")
    override fun getRenderBoundingBox(): AABB = AABB(blockPos.offset(-3, 0, -3), blockPos.offset(3, 16, 3))

    override val requiresComponentsToDetonate = true
    override fun getRequiredDetonationComponents(): Map<out Supplier<out Item>, Int> = emptyMap()
    override fun isComplete() = !items[0].isEmpty && !items[1].isEmpty
    override fun canDetonate(): Boolean {
        if (!super.canDetonate() || energy < LAUNCH_ENERGY_COST || items[0].item !is MissileItem<*>) return false
        val designator = items[1]
        if (designator.item !is TargetDesignator || !designator.hasTag()) return false
        return true
    }

    override fun detonate(): Boolean {
        val level = level
        if (level == null || level.isClientSide) return false

        val missile = items[0].item as? MissileItem<*> ?: return false
        val designator = items[1].item as? TargetDesignator ?: return false
        val designatorTag = items[1].tag ?: return false
        if (!designator.hasValidTarget(level, designatorTag, blockPos)) return false

        level.playSound(null, worldPosition, SoundEvents.missileTakeoff.get(), SoundSource.BLOCKS, 2F, 1F)
        if (!level.addFreshEntity(missile.missileSupplier(level, blockPos.above(2), designator.getTargetPos(level, designatorTag, blockPos)))) return false

        items[0].shrink(1)
        energy -= LAUNCH_ENERGY_COST
        setChanged()
        updateMissileClient()
        return true
    }

    override fun serverTick(level: Level, pos: BlockPos, state: BlockState) {
        if (items[2].getCapability(CapabilityEnergy.ENERGY).isPresent) {
            transferEnergy(items[2], energyStorage)
            setChanged()
        }
    }

    private val inventoryCapability = LazyOptional.of(this::inventory)
    private val energyCapability = LazyOptional.of(this::energyStorage)

    override fun <T : Any?> getCapability(cap: Capability<T>, side: Direction?): LazyOptional<T> {
        if (!remove) when (cap) {
            CapabilityItemHandler.ITEM_HANDLER_CAPABILITY -> return inventoryCapability.cast()
            CapabilityEnergy.ENERGY -> return energyCapability.cast()
        }
        return super.getCapability(cap, side)
    }

    class LaunchPadPartBlockEntity(pos: BlockPos, state: BlockState) : MultiBlockPart.MultiBlockPartBlockEntity(BlockEntityTypes.launchPadPartBlockEntityType.get(), pos, state) {
        override fun <T : Any?> getCapability(cap: Capability<T>, side: Direction?): LazyOptional<T> {
            if (!remove && hasLevel()) {
                val coreEntity = level!!.getBlockEntity(core)
                if (coreEntity != null) return coreEntity.getCapability(cap, side).cast()
            }
            return super.getCapability(cap)
        }
    }

    companion object {
        const val MAX_ENERGY = 100_000
        const val LAUNCH_ENERGY_COST = 75_000
    }
}
