package at.martinthedragon.nucleartech.entity.missile

import at.martinthedragon.nucleartech.api.explosion.ExplosionLargeParams
import at.martinthedragon.nucleartech.entity.EntityTypes
import at.martinthedragon.nucleartech.explosion.ExplosionLarge
import net.minecraft.core.BlockPos
import net.minecraft.world.entity.EntityType
import net.minecraft.world.level.Level

class IncendiaryMissile : AbstractMissile {
    constructor(entityType: EntityType<IncendiaryMissile>, level: Level) : super(entityType, level)
    constructor(level: Level, startPos: BlockPos, targetPos: BlockPos) : super(EntityTypes.missileIncendiary.get(), level, startPos, targetPos)

    override fun onImpact() {
        ExplosionLarge.createAndStart(level, position(), 10F, ExplosionLargeParams(fire = true, cloud = true, rubble = true, shrapnel = true))
    }
}
