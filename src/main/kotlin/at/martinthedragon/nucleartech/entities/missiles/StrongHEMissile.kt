package at.martinthedragon.nucleartech.entities.missiles

import at.martinthedragon.nucleartech.api.explosions.ExplosionLargeParams
import at.martinthedragon.nucleartech.api.explosions.createAndStart
import at.martinthedragon.nucleartech.entities.EntityTypes
import at.martinthedragon.nucleartech.explosions.ExplosionLarge
import net.minecraft.core.BlockPos
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.EntityType
import net.minecraft.world.level.Level

class StrongHEMissile : AbstractMissile {
    constructor(entityType: EntityType<StrongHEMissile>, level: Level) : super(entityType, level)
    constructor(level: Level, startPos: BlockPos, targetPos: BlockPos) : super(EntityTypes.missileHEStrong.get(), level, startPos, targetPos)

    override val renderModel = MODEL_MISSILE_STRONG
    override val renderScale = 1.5F

    override fun onImpact() {
        ExplosionLarge.createAndStart(level, position(), 25F, ExplosionLargeParams(cloud = true, rubble = true, shrapnel = true))
    }
}
