package at.martinthedragon.nucleartech.block.entity

import at.martinthedragon.nucleartech.math.component1
import at.martinthedragon.nucleartech.math.component2
import at.martinthedragon.nucleartech.math.component3
import at.martinthedragon.nucleartech.math.toVec3
import at.martinthedragon.nucleartech.particle.CoolingTowerCloudParticleOptions
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Rotation
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.AABB

class LargeCoolingTowerBlockEntity(pos: BlockPos, state: BlockState) : AbstractCoolingTowerBlockEntity(BlockEntityTypes.largeCoolingTowerBlockEntityType.get(), pos, state, 10_000, 10_000) {
    override val particleTact = 4

    override fun spawnParticles(level: Level, pos: BlockPos) {
        val (x, y, z) = pos.toVec3().add(0.5 + level.random.nextDouble() * 3.0 - 1.5, 1.0, 0.5 + level.random.nextDouble() * 3.0 - 1.5)
        level.addParticle(
            CoolingTowerCloudParticleOptions(1F, 10F, 0.5F, 750 + level.random.nextInt(250)),
            x, y, z,
            0.0, 0.0, 0.0
        )
    }

    override fun getRenderBoundingBox() = AABB(blockPos.offset(-4, 0, -4), blockPos.offset(5, 13, 5))

    override val ioConfigurations = IODelegatedBlockEntity.fromTriples(blockPos, Rotation.NONE,
        Triple(BlockPos(4, 0, 0), Direction.EAST, listOf(IOConfiguration(IOConfiguration.Mode.BOTH, IODelegatedBlockEntity.DEFAULT_FLUID_ACTION))),
        Triple(BlockPos(-4, 0, 0), Direction.WEST, listOf(IOConfiguration(IOConfiguration.Mode.BOTH, IODelegatedBlockEntity.DEFAULT_FLUID_ACTION))),
        Triple(BlockPos(0, 0, 4), Direction.SOUTH, listOf(IOConfiguration(IOConfiguration.Mode.BOTH, IODelegatedBlockEntity.DEFAULT_FLUID_ACTION))),
        Triple(BlockPos(0, 0, -4), Direction.NORTH, listOf(IOConfiguration(IOConfiguration.Mode.BOTH, IODelegatedBlockEntity.DEFAULT_FLUID_ACTION))),
        Triple(BlockPos(4, 0, 3), Direction.EAST, listOf(IOConfiguration(IOConfiguration.Mode.BOTH, IODelegatedBlockEntity.DEFAULT_FLUID_ACTION))),
        Triple(BlockPos(4, 0, -3), Direction.EAST, listOf(IOConfiguration(IOConfiguration.Mode.BOTH, IODelegatedBlockEntity.DEFAULT_FLUID_ACTION))),
        Triple(BlockPos(-4, 0, 3), Direction.WEST, listOf(IOConfiguration(IOConfiguration.Mode.BOTH, IODelegatedBlockEntity.DEFAULT_FLUID_ACTION))),
        Triple(BlockPos(-4, 0, -3), Direction.WEST, listOf(IOConfiguration(IOConfiguration.Mode.BOTH, IODelegatedBlockEntity.DEFAULT_FLUID_ACTION))),
        Triple(BlockPos(3, 0, 4), Direction.SOUTH, listOf(IOConfiguration(IOConfiguration.Mode.BOTH, IODelegatedBlockEntity.DEFAULT_FLUID_ACTION))),
        Triple(BlockPos(-3, 0, 4), Direction.SOUTH, listOf(IOConfiguration(IOConfiguration.Mode.BOTH, IODelegatedBlockEntity.DEFAULT_FLUID_ACTION))),
        Triple(BlockPos(3, 0, -4), Direction.NORTH, listOf(IOConfiguration(IOConfiguration.Mode.BOTH, IODelegatedBlockEntity.DEFAULT_FLUID_ACTION))),
        Triple(BlockPos(-3, 0, -4), Direction.NORTH, listOf(IOConfiguration(IOConfiguration.Mode.BOTH, IODelegatedBlockEntity.DEFAULT_FLUID_ACTION))),
    )
}
