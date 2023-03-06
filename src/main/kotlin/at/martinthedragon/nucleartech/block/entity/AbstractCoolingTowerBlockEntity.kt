package at.martinthedragon.nucleartech.block.entity

import at.martinthedragon.nucleartech.api.block.entities.TickingBlockEntity
import net.minecraft.core.BlockPos
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState

abstract class AbstractCoolingTowerBlockEntity(type: BlockEntityType<out AbstractCoolingTowerBlockEntity>, pos: BlockPos, state: BlockState, inputCapacity: Int, outputCapacity: Int) : AbstractCondenserBlockEntity(type, pos, state, inputCapacity, outputCapacity),
    TickingBlockEntity, IODelegatedBlockEntity
{
    protected abstract val particleTact: Int

    protected abstract fun spawnParticles(level: Level, pos: BlockPos)

    override fun clientTick(level: Level, pos: BlockPos, state: BlockState) {
        if (conversionTimeout > 0 && level.gameTime % particleTact == 0L) {
            spawnParticles(level, pos)
        }
    }
}
