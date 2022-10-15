package at.martinthedragon.nucleartech.block.entity

import at.martinthedragon.nucleartech.api.block.entities.SoundLoopBlockEntity
import at.martinthedragon.nucleartech.capability.WorldlyCapabilityProvider
import at.martinthedragon.nucleartech.extensions.contains
import at.martinthedragon.nucleartech.extensions.horizontalRotation
import at.martinthedragon.nucleartech.extensions.inverted
import at.martinthedragon.nucleartech.math.toVec3Middle
import at.martinthedragon.nucleartech.serialization.NBTKeys
import at.martinthedragon.nucleartech.serialization.loadItemsFromTagToList
import at.martinthedragon.nucleartech.serialization.saveItemsToTag
import com.google.common.collect.HashBasedTable
import com.google.common.collect.Table
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.Tag
import net.minecraft.network.chat.Component
import net.minecraft.world.*
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.entity.player.StackedContents
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.StackedContentsCompatible
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.block.Rotation
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.util.LazyOptional
import net.minecraftforge.common.util.NonNullSupplier
import net.minecraftforge.items.CapabilityItemHandler
import net.minecraftforge.items.IItemHandlerModifiable
import net.minecraftforge.items.ItemHandlerHelper
import java.util.*
import kotlin.math.min

abstract class BaseMachineBlockEntity(type: BlockEntityType<*>, pos: BlockPos, state: BlockState) : SyncedBlockEntity(type, pos, state),
    IItemHandlerModifiable, MenuProvider, Nameable, StackedContentsCompatible, Container, ContainerSyncableBlockEntity, SoundLoopBlockEntity, WorldlyCapabilityProvider
{
    // is expected to retain its size on clear
    protected abstract val mainInventory: MutableList<ItemStack>

    override fun getSlots() = mainInventory.size

    override fun getStackInSlot(slot: Int) = mainInventory[slot]

    override fun insertItem(slot: Int, stack: ItemStack, simulate: Boolean): ItemStack {
        if (stack.isEmpty) return ItemStack.EMPTY
        if (!isItemValid(slot, stack)) return stack
        checkSlotIndex(slot)

        var limit = getStackLimit(slot, stack)
        val existing = mainInventory[slot]
        if (!existing.isEmpty) {
            if (!ItemHandlerHelper.canItemStacksStack(stack, existing)) return stack
            limit -= existing.count
        }
        if (limit <= 0) return stack
        val willReachLimit = stack.count > limit

        if (!simulate) {
            if (existing.isEmpty) mainInventory[slot] = if (willReachLimit) ItemHandlerHelper.copyStackWithSize(stack, limit) else stack
            else existing.grow(if (willReachLimit) limit else stack.count)
            inventoryChanged(slot)
        }
        return if (willReachLimit) ItemHandlerHelper.copyStackWithSize(stack, stack.count - limit) else ItemStack.EMPTY
    }

    override fun extractItem(slot: Int, amount: Int, simulate: Boolean): ItemStack {
        if (amount == 0) return ItemStack.EMPTY
        checkSlotIndex(slot)

        val existing = mainInventory[slot]
        if (existing.isEmpty) return ItemStack.EMPTY

        val toExtract = min(amount, existing.maxStackSize)
        return if (existing.count <= toExtract) {
            if (!simulate) {
                mainInventory[slot] = ItemStack.EMPTY
                inventoryChanged(slot)
                existing
            } else existing.copy()
        } else {
            if (!simulate) {
                mainInventory[slot] = ItemHandlerHelper.copyStackWithSize(existing, existing.count - toExtract)
                inventoryChanged(slot)
            }
            ItemHandlerHelper.copyStackWithSize(existing, toExtract)
        }
    }

    override fun setStackInSlot(slot: Int, stack: ItemStack) {
        checkSlotIndex(slot)
        mainInventory[slot] = stack
        inventoryChanged(slot)
    }

    protected open fun inventoryChanged(slot: Int) {
        setChanged()
    }

    protected fun checkSlotIndex(slot: Int) {
        require(slot in mainInventory.indices) { "Slot $slot is outside of slot range [0,${mainInventory.size})" }
    }

    override fun getSlotLimit(slot: Int) = 64
    protected open fun getStackLimit(slot: Int, stack: ItemStack) = min(getSlotLimit(slot), stack.maxStackSize)

    override fun fillStackedContents(stackedContents: StackedContents) {
        for (stack in mainInventory) stackedContents.accountStack(stack)
    }

    override fun clearContent() { mainInventory.clear() }
    override fun getContainerSize() = mainInventory.size
    override fun isEmpty() = mainInventory.all(ItemStack::isEmpty)
    override fun getItem(slot: Int): ItemStack = mainInventory[slot]

    override fun removeItem(slot: Int, amount: Int): ItemStack {
        val item = ContainerHelper.removeItem(mainInventory, slot, amount)
        inventoryChanged(slot)
        return item
    }
    override fun removeItemNoUpdate(slot: Int): ItemStack = ContainerHelper.takeItem(mainInventory, slot)

    override fun setItem(slot: Int, itemStack: ItemStack) {
        mainInventory[slot] = itemStack
        itemStack.count = itemStack.count.coerceAtMost(maxStackSize)
        inventoryChanged(slot)
    }

    override fun stillValid(player: Player) = if (level!!.getBlockEntity(worldPosition) != this) false else player.distanceToSqr(worldPosition.toVec3Middle()) <= 64

    override val hasInventory = true
    override fun getInventory() = this

    protected abstract val defaultName: Component

    @get:[JvmSynthetic JvmName("getCustomName$")]
    var customName: Component? = null

    override fun getCustomName(): Component? = customName

    override fun getName(): Component = if (customName != null) customName!! else defaultName

    override fun getDisplayName() = name

    protected var lockCode: LockCode = LockCode.NO_LOCK

    protected open fun canOpen(player: Player): Boolean {
        return BaseContainerBlockEntity.canUnlock(player, lockCode, displayName)
    }

    override fun createMenu(windowID: Int, inventory: Inventory, player: Player) = if (canOpen(player)) createMenu(windowID, inventory) else null

    protected abstract fun createMenu(windowID: Int, inventory: Inventory): AbstractContainerMenu

    override val soundPos: BlockPos get() = worldPosition
    @Suppress("LeakingThis") // it's fine because it'll only ever tick when the block entity is ticking
    override val soundLoopStateMachine = SoundLoopBlockEntity.SoundStateMachine(this)

    private var horizontalRotation: Rotation? = null

    protected fun getHorizontalBlockRotation(): Rotation {
        if (horizontalRotation == null) horizontalRotation = if (blockState.hasProperty(BlockStateProperties.HORIZONTAL_FACING)) blockState.getValue(BlockStateProperties.HORIZONTAL_FACING).horizontalRotation else Rotation.NONE
        return horizontalRotation!!
    }

    @Suppress("DEPRECATION", "OVERRIDE_DEPRECATION")
    override fun setBlockState(state: BlockState) {
        super.setBlockState(state)
        horizontalRotation = null
    }

    private val capabilityHandlerSuppliers: Table<Capability<*>, Optional<Direction>, NonNullSupplier<*>> = defaultCapabilityTable()
    private val capabilityHandlers: Table<Capability<*>, Optional<Direction>, LazyOptional<*>> = defaultCapabilityTable()

    private val otherCapabilityHandlerSuppliers: MutableMap<BlockPos, Table<Capability<*>, Optional<Direction>, NonNullSupplier<*>>> = mutableMapOf(blockPos to capabilityHandlerSuppliers)
    private val otherCapabilityHandlers: MutableMap<BlockPos, Table<Capability<*>, Optional<Direction>, LazyOptional<*>>> = mutableMapOf(blockPos to capabilityHandlers)

    init {
        registerCapabilityHandler(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, { this })
    }

    protected fun <T : Any> registerCapabilityHandler(capability: Capability<T>, handler: () -> T, side: Direction? = null): LazyOptional<T> {
        val optionalSide = Optional.ofNullable(side)
        val lazyOptionalHandler = LazyOptional.of(handler)
        capabilityHandlerSuppliers.put(capability, optionalSide, handler)
        capabilityHandlers.put(capability, optionalSide, lazyOptionalHandler)?.invalidate()
        return lazyOptionalHandler
    }

    protected fun <T : Any> registerCapabilityHandler(capability: Capability<T>, handler: () -> T, vararg sides: Direction): LazyOptional<T> {
        if (sides.isEmpty()) return registerCapabilityHandler(capability, handler, null)
        val lazyOptionalHandler = LazyOptional.of(handler)
        for (side in sides) {
            val optionalSide = Optional.of(side)
            capabilityHandlerSuppliers.put(capability, optionalSide, handler)
            capabilityHandlers.put(capability, optionalSide, lazyOptionalHandler)
        }
        return lazyOptionalHandler
    }

    protected fun <T : Any> registerCapabilityHandler(capability: Capability<T>, relativePos: BlockPos, handler: () -> T, side: Direction? = null): LazyOptional<T> {
        val optionalSide = Optional.ofNullable(side)
        val lazyOptionalHandler = LazyOptional.of(handler)
        otherCapabilityHandlerSuppliers.getOrPut(relativePos, this::defaultCapabilityTable).put(capability, optionalSide, handler)
        otherCapabilityHandlers.getOrPut(relativePos, this::defaultCapabilityTable).put(capability, optionalSide, lazyOptionalHandler)?.invalidate()
        return lazyOptionalHandler
    }

    protected fun <T : Any> registerCapabilityHandler(capability: Capability<T>, relativePos: BlockPos, handler: () -> T, vararg sides: Direction): LazyOptional<T> {
        if (sides.isEmpty()) return registerCapabilityHandler(capability, relativePos, handler, null)
        val lazyOptionalHandler = LazyOptional.of(handler)
        for (side in sides) {
            val optionalSide = Optional.of(side)
            otherCapabilityHandlerSuppliers.getOrPut(relativePos, this::defaultCapabilityTable).put(capability, optionalSide, handler)
            otherCapabilityHandlers.getOrPut(relativePos, this::defaultCapabilityTable).put(capability, optionalSide, lazyOptionalHandler)?.invalidate()
        }
        return lazyOptionalHandler
    }

    override fun <T : Any> getCapability(cap: Capability<T>, side: Direction?): LazyOptional<T> {
        if (!remove) {
            val capabilityRow = capabilityHandlers.row(cap)
            // if there are no sided handlers always return the unsided one
            if (capabilityRow.size == 1 && capabilityRow.contains(Optional.empty())) return capabilityRow.getValue(Optional.empty()).cast()
            return capabilityRow[Optional.ofNullable(side?.let(getHorizontalBlockRotation().inverted::rotate))]?.cast() ?: super<SyncedBlockEntity>.getCapability(cap, side)
        }
        return super<SyncedBlockEntity>.getCapability(cap, side)
    }

    // to avoid accidental overrides
    final override fun <T : Any> getCapability(cap: Capability<T>) = super<SyncedBlockEntity>.getCapability(cap)

    override fun <T : Any> getCapability(cap: Capability<T>, pos: BlockPos, side: Direction?): LazyOptional<T> {
        if (!remove) {
            if (otherCapabilityHandlers.size == 1) return getCapability(cap, side) // there is only us
            val relativePos = pos.subtract(blockPos).rotate(getHorizontalBlockRotation().inverted)
            val table = otherCapabilityHandlers[relativePos] ?: return LazyOptional.empty()
            val capabilityRow = table.row(cap)
            if (capabilityRow.size == 1 && capabilityRow.contains(Optional.empty())) return capabilityRow.getValue(Optional.empty()).cast()
            return capabilityRow[Optional.ofNullable(side?.let(getHorizontalBlockRotation().inverted::rotate))]?.cast() ?: return LazyOptional.empty()
        }

        return LazyOptional.empty()
    }

    override fun invalidateCaps() {
        super.invalidateCaps()
        for (handler in capabilityHandlers.values()) handler.invalidate()
        for (handler in otherCapabilityHandlers.values.flatMap(Table<*, *, LazyOptional<*>>::values)) handler.invalidate()
    }

    override fun reviveCaps() {
        super.reviveCaps()
        for (cell in capabilityHandlerSuppliers.cellSet())
            capabilityHandlers.put(cell.rowKey, cell.columnKey, LazyOptional.of(cell.value))?.invalidate() // invalidate just to make sure we don't lose any listeners
        for ((pos, table) in otherCapabilityHandlerSuppliers) for (cell in table.cellSet())
            otherCapabilityHandlers.getOrPut(pos, this::defaultCapabilityTable).put(cell.rowKey, cell.columnKey, LazyOptional.of(cell.value))?.invalidate()
    }

    private fun <R, C, V> defaultCapabilityTable() = HashBasedTable.create<R, C, V>(2, 2)

    override fun saveAdditional(tag: CompoundTag) {
        super.saveAdditional(tag)
        lockCode.addToTag(tag)
        if (hasCustomName()) tag.putString(NBTKeys.CUSTOM_NAME, Component.Serializer.toJson(customName!!))
        saveItemsToTag(tag, mainInventory)
    }

    override fun load(tag: CompoundTag) {
        super.load(tag)
        lockCode = LockCode.fromTag(tag)
        if (tag.contains(NBTKeys.CUSTOM_NAME, Tag.TAG_STRING)) customName = Component.Serializer.fromJson(tag.getString(NBTKeys.CUSTOM_NAME))
        mainInventory.clear()
        loadItemsFromTagToList(tag, mainInventory)
    }

    override fun setRemoved() {
        super.setRemoved()
        soundLoopStateMachine.tick()
    }
}
