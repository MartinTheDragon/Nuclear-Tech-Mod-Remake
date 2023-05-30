package at.martinthedragon.nucleartech.entity.missile

import at.martinthedragon.nucleartech.api.explosion.ExplosionLargeParams
import at.martinthedragon.nucleartech.entity.EntityTypes
import at.martinthedragon.nucleartech.explosion.ExplosionLarge
import at.martinthedragon.nucleartech.math.burnSpherical
import at.martinthedragon.nucleartech.math.burnSphericalFlammable
import net.minecraft.core.BlockPos
import net.minecraft.world.entity.EntityType
import net.minecraft.world.level.Level

class InfernoMissile : AbstractMissile {
    constructor(entityType: EntityType<InfernoMissile>, level: Level) : super(entityType, level)
    constructor(level: Level, startPos: BlockPos, targetPos: BlockPos) : super(EntityTypes.missileInferno.get(), level, startPos, targetPos)

    override val renderModel = MODEL_MISSILE_HUGE
    override val renderScale = 2F

    override fun onImpact() {
        ExplosionLarge.createAndStart(level, position(), 35F, ExplosionLargeParams(fire = true, cloud = true, rubble = true, shrapnel = true))
        burnSpherical(level, blockPosition(), 10)
        burnSphericalFlammable(level, blockPosition(), 20)
    }
}
