package at.martinthedragon.nucleartech.api.block.entities

import net.minecraft.core.BlockPos
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityTicker
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState

public interface TickingClientBlockEntity {
    public fun clientTick(level: Level, pos: BlockPos, state: BlockState)
}

@Suppress("UNCHECKED_CAST")
public fun <T, X> createClientTickerChecked(type: BlockEntityType<T>, expectedType: BlockEntityType<X>): BlockEntityTicker<T>?
    where T : BlockEntity,
          X : BlockEntity,
          X : TickingClientBlockEntity =
    if (type == expectedType) createClientTicker<X>() as BlockEntityTicker<T> else null

public fun <T> createClientTicker(): BlockEntityTicker<T>
    where T : BlockEntity,
          T : TickingClientBlockEntity =
    BlockEntityTicker<T> { level, pos, state, blockEntity ->
        blockEntity.clientTick(level, pos, state)
    }
