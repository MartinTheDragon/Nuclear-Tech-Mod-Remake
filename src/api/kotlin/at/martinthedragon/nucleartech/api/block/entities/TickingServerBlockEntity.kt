package at.martinthedragon.nucleartech.api.block.entities

import net.minecraft.core.BlockPos
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityTicker
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState

public interface TickingServerBlockEntity {
    public fun serverTick(level: Level, pos: BlockPos, state: BlockState)
}

@Suppress("UNCHECKED_CAST")
public fun <T, X> createServerTickerChecked(type: BlockEntityType<T>, expectedType: BlockEntityType<X>): BlockEntityTicker<T>?
    where T : BlockEntity,
          X : BlockEntity,
          X : TickingServerBlockEntity =
    if (type == expectedType) createServerTicker<X>() as BlockEntityTicker<T> else null

public fun <T> createServerTicker(): BlockEntityTicker<T>
    where T : BlockEntity,
          T : TickingServerBlockEntity=
    BlockEntityTicker<T> { level, pos, state, blockEntity ->
        blockEntity.serverTick(level, pos, state)
    }
