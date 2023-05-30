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

class ClusterMissile : AbstractClusterMissile {
    constructor(entityType: EntityType<ClusterMissile>, level: Level) : super(entityType, level)
    constructor(level: Level, startPos: BlockPos, targetPos: BlockPos) : super(EntityTypes.missileCluster.get(), level, startPos, targetPos)

    override fun onImpact() {
        val (x, y, z) = position()
        level.explode(this, x, y, z, 5F, false, Explosion.BlockInteraction.DESTROY)
        ClusterFragment.spawnClustered(level, position(), 25)
    }
}
