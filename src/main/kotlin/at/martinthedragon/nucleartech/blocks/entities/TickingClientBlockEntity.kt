package at.martinthedragon.nucleartech.blocks.entities

import net.minecraft.core.BlockPos
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityTicker
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState

interface TickingClientBlockEntity {
    fun clientTick(level: Level, pos: BlockPos, state: BlockState)
}

@Suppress("UNCHECKED_CAST")
fun <T, X> createClientTickerChecked(type: BlockEntityType<T>, expectedType: BlockEntityType<X>): BlockEntityTicker<T>?
    where T : BlockEntity,
          X : BlockEntity,
          X : TickingClientBlockEntity =
    if (type == expectedType) createClientTicker<X>() as BlockEntityTicker<T> else null

fun <T> createClientTicker()
    where T : BlockEntity,
          T : TickingClientBlockEntity =
    BlockEntityTicker<T> { level, pos, state, blockEntity ->
        blockEntity.clientTick(level, pos, state)
    }

fun <T, X> createSidedTickerChecked(isClient: Boolean, type: BlockEntityType<T>, expectedType: BlockEntityType<X>): BlockEntityTicker<T>?
    where T : BlockEntity,
          X : BlockEntity,
          X : TickingClientBlockEntity,
          X : TickingServerBlockEntity =
    if (type == expectedType)
        if (isClient) createClientTickerChecked(type, expectedType) as BlockEntityTicker<T>
        else createServerTickerChecked(type, expectedType) as BlockEntityTicker<T>
    else null
