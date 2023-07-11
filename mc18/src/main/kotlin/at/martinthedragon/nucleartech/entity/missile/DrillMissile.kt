package at.martinthedragon.nucleartech.entity.missile

import at.martinthedragon.nucleartech.api.explosion.ExplosionLargeParams
import at.martinthedragon.nucleartech.entity.EntityTypes
import at.martinthedragon.nucleartech.explosion.ExplosionLarge
import at.martinthedragon.nucleartech.math.component1
import at.martinthedragon.nucleartech.math.component2
import at.martinthedragon.nucleartech.math.component3
import net.minecraft.core.BlockPos
import net.minecraft.world.entity.EntityType
import net.minecraft.world.level.Explosion
import net.minecraft.world.level.Level

class DrillMissile : AbstractMissile {
    constructor(entityType: EntityType<DrillMissile>, level: Level) : super(entityType, level)
    constructor(level: Level, startPos: BlockPos, targetPos: BlockPos) : super(EntityTypes.missileDrill.get(), level, startPos, targetPos)

    override val renderModel = MODEL_MISSILE_HUGE
    override val renderScale = 2F

    override fun onImpact() {
        ExplosionLarge.createAndStart(level, position(), 10F, ExplosionLargeParams(cloud = true, rubble = true, shrapnel = true))
        val (x, y, z) = position()
        for (i in 1 until 20)
            level.explode(this, x, y - i, z, 7.5F, Explosion.BlockInteraction.BREAK)
    }
}
