package at.martinthedragon.nucleartech.api.block.entities

import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityTicker
import net.minecraft.world.level.block.entity.BlockEntityType

/**
 * A combination of [TickingServerBlockEntity] and [TickingClientBlockEntity].
 *
 * @see createSidedTickerChecked
 */
public interface TickingBlockEntity : TickingServerBlockEntity, TickingClientBlockEntity

public fun <T, X> createSidedTickerChecked(isClient: Boolean, type: BlockEntityType<T>, expectedType: BlockEntityType<X>): BlockEntityTicker<T>?
    where T : BlockEntity,
          X : BlockEntity,
          X : TickingClientBlockEntity,
          X : TickingServerBlockEntity =
    if (type == expectedType)
        if (isClient) createClientTickerChecked(type, expectedType) as BlockEntityTicker<T>
        else createServerTickerChecked(type, expectedType) as BlockEntityTicker<T>
    else null
