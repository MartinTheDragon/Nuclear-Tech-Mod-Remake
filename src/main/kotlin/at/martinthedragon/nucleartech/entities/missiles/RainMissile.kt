package at.martinthedragon.nucleartech.entities.missiles

import at.martinthedragon.nucleartech.entities.EntityTypes
import at.martinthedragon.nucleartech.entities.ClusterFragment
import at.martinthedragon.nucleartech.math.component1
import at.martinthedragon.nucleartech.math.component2
import at.martinthedragon.nucleartech.math.component3
import net.minecraft.core.BlockPos
import net.minecraft.world.entity.EntityType
import net.minecraft.world.level.Explosion
import net.minecraft.world.level.Level

class RainMissile : AbstractClusterMissile {
    constructor(entityType: EntityType<RainMissile>, level: Level) : super(entityType, level)
    constructor(level: Level, startPos: BlockPos, targetPos: BlockPos) : super(EntityTypes.missileRain.get(), level, startPos, targetPos)

    override val renderModel = MODEL_MISSILE_HUGE
    override val renderScale = 2F

    override fun onImpact() {
        val (x, y, z) = position()
        level.explode(this, x, y, z, 25F, false, Explosion.BlockInteraction.DESTROY)
        ClusterFragment.spawnClustered(level, position(), 100)
    }
}
