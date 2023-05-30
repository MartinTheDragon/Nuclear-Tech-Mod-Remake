@file:Suppress("ReplacePutWithAssignment")

package at.martinthedragon.nucleartech.block.entity

import at.martinthedragon.nucleartech.capability.WordlyCapabilityProvider
import at.martinthedragon.nucleartech.coremodules.forge.common.capabilities.Capability
import at.martinthedragon.nucleartech.coremodules.forge.common.util.LazyOptional
import at.martinthedragon.nucleartech.coremodules.minecraft.core.BlockPos
import at.martinthedragon.nucleartech.coremodules.minecraft.core.Direction
import at.martinthedragon.nucleartech.coremodules.minecraft.world.level.block.entity.BlockEntityType
import at.martinthedragon.nucleartech.coremodules.minecraft.world.level.block.state.BlockState
import at.martinthedragon.nucleartech.coremodules.minecraft.world.level.block.state.properties.BlockStateProperties
import java.util.*

private typealias CapabilityTable<T> = MutableMap<Capability<*>, MutableMap<Optional<Direction>, T>>

open class CapabilityBlockEntity(type: BlockEntityType<out CapabilityBlockEntity>, blockPos: BlockPos, blockState: BlockState) : SyncedBlockEntity(type, blockPos, blockState),
    WordlyCapabilityProvider, RotatedBlockEntity
{
    override val horizontalRotation = blockState.getOptionalValue(BlockStateProperties.HORIZONTAL_FACING).orElse(Direction.NORTH).horizontalRotation

    private val capabilityHandlerSuppliers: CapabilityTable<() -> Any> = defaultCapabilityTable()
    private val capabilityHandlers: CapabilityTable<LazyOptional<*>> = defaultCapabilityTable()

    private val otherCapabilityHandlerSuppliers: MutableMap<BlockPos, CapabilityTable<() -> Any>> = mutableMapOf(blockPos to capabilityHandlerSuppliers)
    private val otherCapabilityHandlers: MutableMap<BlockPos, CapabilityTable<LazyOptional<*>>> = mutableMapOf(blockPos to capabilityHandlers)

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
            capabilityHandlers.put(capability, optionalSide, lazyOptionalHandler)?.invalidate()
        }
        return lazyOptionalHandler
    }

    protected fun <T : Any> registerCapabilityHandler(capability: Capability<T>, relativePos: BlockPos, handler: () -> T, side: Direction? = null): LazyOptional<T> {
        if (relativePos == BlockPos.ZERO) return registerCapabilityHandler(capability, handler, side)
        val optionalSide = Optional.ofNullable(side)
        val lazyOptionalHandler = LazyOptional.of(handler)
        otherCapabilityHandlerSuppliers.getOrPut(relativePos, this::defaultCapabilityTable).put(capability, optionalSide, handler)
        otherCapabilityHandlers.getOrPut(relativePos, this::defaultCapabilityTable).put(capability, optionalSide, lazyOptionalHandler)?.invalidate()
        return lazyOptionalHandler
    }

    protected fun <T : Any> registerCapabilityHandler(capability: Capability<T>, relativePos: BlockPos, handler: () -> T, vararg sides: Direction): LazyOptional<T> {
        if (relativePos == BlockPos.ZERO) return registerCapabilityHandler(capability, handler, *sides)
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
        if (!isRemoved) {
            val capabilityRow = capabilityHandlers.getValue(cap)
            // if there are no sided handlers always return the unsided one
            if (capabilityRow.size == 1 && capabilityRow.contains(Optional.empty())) return capabilityRow.getValue(Optional.empty()).cast()
            return capabilityRow[Optional.ofNullable(side?.let(horizontalRotation.inverted::rotate))]?.cast() ?: super<SyncedBlockEntity>.getCapability(cap, side)
        }
        return super<SyncedBlockEntity>.getCapability(cap, side)
    }

    override fun <T : Any> getCapability(cap: Capability<T>, pos: BlockPos, side: Direction?): LazyOptional<T> {
        if (!isRemoved) {
            if (otherCapabilityHandlers.size == 1) return getCapability(cap, side) // there is only us
            val relativePos = pos.subtract(blockPos).rotate(horizontalRotation.inverted)
            val table = otherCapabilityHandlers[relativePos] ?: return LazyOptional.empty()
            val capabilityRow = table.getValue(cap)
            if (capabilityRow.size == 1 && capabilityRow.contains(Optional.empty())) return capabilityRow.getValue(Optional.empty()).cast()
            return capabilityRow[Optional.ofNullable(side?.let(horizontalRotation.inverted::rotate))]?.cast() ?: return LazyOptional.empty()
        }

        return LazyOptional.empty()
    }

    override fun invalidateCaps() {
        super.invalidateCaps()
        for (handler in capabilityHandlers.handlers) handler.invalidate()
        for (handler in otherCapabilityHandlers.values.flatMap(CapabilityTable<LazyOptional<*>>::handlers)) handler.invalidate()
    }

    override fun reviveCaps() {
        super.reviveCaps()
        for ((cap, side, handler) in capabilityHandlerSuppliers.cells)
            capabilityHandlers.put(cap, side, LazyOptional.of(handler))?.invalidate() // invalidate just to make sure we don't lose any listeners
        for ((pos, table) in otherCapabilityHandlerSuppliers) for ((cap, side, handler) in table.cells)
            otherCapabilityHandlers.getOrPut(pos, this::defaultCapabilityTable).put(cap, side, LazyOptional.of(handler))?.invalidate()
    }

    private fun <T> defaultCapabilityTable(): CapabilityTable<T> = mutableMapOf<Capability<*>, MutableMap<Optional<Direction>, T>>().withDefault { mutableMapOf() }
}

private fun <T> CapabilityTable<T>.put(cap: Capability<*>, side: Optional<Direction>, handler: T) = getValue(cap).put(side, handler)

private val <T> CapabilityTable<T>.handlers get() = values.flatMap(Map<Optional<Direction>, T>::values)
private val <T> CapabilityTable<T>.cells get() = flatMap { (cap, sideHandlerMap) -> sideHandlerMap.map { (side, handler) -> Triple(cap, side, handler) }}
