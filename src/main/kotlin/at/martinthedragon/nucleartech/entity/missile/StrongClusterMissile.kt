package at.martinthedragon.nucleartech.entity.missile

import at.martinthedragon.nucleartech.entity.EntityTypes
import at.martinthedragon.nucleartech.entity.ClusterFragment
import at.martinthedragon.nucleartech.math.component1
import at.martinthedragon.nucleartech.math.component2
import at.martinthedragon.nucleartech.math.component3
import net.minecraft.core.BlockPos
import net.minecraft.world.entity.EntityType
import net.minecraft.world.level.Explosion
import net.minecraft.world.level.Level

class StrongClusterMissile : AbstractClusterMissile {
    constructor(entityType: EntityType<StrongClusterMissile>, level: Level) : super(entityType, level)
    constructor(level: Level, startPos: BlockPos, targetPos: BlockPos) : super(EntityTypes.missileClusterStrong.get(), level, startPos, targetPos)

    override val renderModel = MODEL_MISSILE_STRONG
    override val renderScale = 1.5F

    override fun onImpact() {
        val (x, y, z) = position()
        level.explode(this, x, y, z, 15F, false, Explosion.BlockInteraction.DESTROY)
        ClusterFragment.spawnClustered(level, position(), 50)
    }
}
