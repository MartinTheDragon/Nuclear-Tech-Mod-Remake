package at.martinthedragon.nucleartech.entities.missiles

import at.martinthedragon.nucleartech.api.explosions.ExplosionLargeParams
import at.martinthedragon.nucleartech.api.explosions.createAndStart
import at.martinthedragon.nucleartech.entities.EntityTypes
import at.martinthedragon.nucleartech.explosions.ExplosionLarge
import net.minecraft.core.BlockPos
import net.minecraft.world.entity.EntityType
import net.minecraft.world.level.Level

class BurstMissile : AbstractMissile {
    constructor(entityType: EntityType<BurstMissile>, level: Level) : super(entityType, level)
    constructor(level: Level, startPos: BlockPos, targetPos: BlockPos) : super(EntityTypes.missileBurst.get(), level, startPos, targetPos)

    override val renderModel = MODEL_MISSILE_HUGE
    override val renderScale = 2F

    override fun onImpact() {
        val position = position()
        for (i in 0 until 4) ExplosionLarge.createAndStart(level, position, 50F, ExplosionLargeParams())
        ExplosionLarge.createAndStart(level, position, 50F, ExplosionLargeParams(cloud = true, rubble = true, shrapnel = true))
    }
}
