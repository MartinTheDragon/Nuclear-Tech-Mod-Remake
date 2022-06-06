package at.martinthedragon.nucleartech.entities.missiles

import net.minecraft.core.BlockPos
import net.minecraft.world.entity.EntityType
import net.minecraft.world.level.Level

abstract class AbstractClusterMissile : AbstractMissile {
    constructor(entityType: EntityType<out AbstractClusterMissile>, level: Level) : super(entityType, level)
    constructor(entityType: EntityType<out AbstractClusterMissile>, level: Level, startPos: BlockPos, targetPos: BlockPos) : super(entityType, level, startPos, targetPos)

    override fun tick() {
        super.tick()

        if (deltaMovement.y < -1) {
            if (!level.isClientSide) onImpact()
            discard()
        }
    }
}
