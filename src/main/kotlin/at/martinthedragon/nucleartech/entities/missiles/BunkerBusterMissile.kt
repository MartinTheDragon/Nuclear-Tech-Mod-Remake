package at.martinthedragon.nucleartech.entities.missiles

import at.martinthedragon.nucleartech.api.explosions.ExplosionLargeParams
import at.martinthedragon.nucleartech.api.explosions.createAndStart
import at.martinthedragon.nucleartech.entities.EntityTypes
import at.martinthedragon.nucleartech.explosions.ExplosionLarge
import at.martinthedragon.nucleartech.math.component1
import at.martinthedragon.nucleartech.math.component2
import at.martinthedragon.nucleartech.math.component3
import net.minecraft.core.BlockPos
import net.minecraft.world.entity.EntityType
import net.minecraft.world.level.Explosion
import net.minecraft.world.level.Level

class BunkerBusterMissile : AbstractMissile {
    constructor(entityType: EntityType<BunkerBusterMissile>, level: Level) : super(entityType, level)
    constructor(level: Level, startPos: BlockPos, targetPos: BlockPos) : super(EntityTypes.missileBunkerBuster.get(), level, startPos, targetPos)

    override val renderModel = MODEL_MISSILE_STRONG
    override val renderScale = 1.5F

    override fun onImpact() {
        ExplosionLarge.createAndStart(level, position(), 5F, ExplosionLargeParams(cloud = true, rubble = true, shrapnel = true))
        val (x, y, z) = position()
        for (i in 1 until 15)
            level.explode(this, x, y - i, z, 5F, Explosion.BlockInteraction.BREAK)
    }
}
