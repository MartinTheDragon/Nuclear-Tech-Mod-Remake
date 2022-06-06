package at.martinthedragon.nucleartech.entities

import at.martinthedragon.nucleartech.ModItems
import at.martinthedragon.nucleartech.api.explosions.ExplosionLargeParams
import at.martinthedragon.nucleartech.api.explosions.createAndStart
import at.martinthedragon.nucleartech.explosions.ExplosionLarge
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.projectile.ThrowableItemProjectile
import net.minecraft.world.level.Level
import net.minecraft.world.phys.HitResult
import net.minecraft.world.phys.Vec3

class ClusterFragment(entityType: EntityType<ClusterFragment>, level: Level) : ThrowableItemProjectile(entityType, level) {
    override fun getDefaultItem() = ModItems.plutoniumCore.get()

    override fun onHit(hitResult: HitResult) {
        super.onHit(hitResult)
        ExplosionLarge.createAndStart(level, position(), 5F, ExplosionLargeParams(cloud = true, rubble = false, shrapnel = true))
        discard()
    }

    override fun getGravity() = 0.0125F

    companion object {
        private val random = java.util.Random()

        fun spawnClustered(level: Level, position: Vec3, count: Int) {
            for (i in 0 until count) {
                var dx = random.nextGaussian()
                val dy = random.nextGaussian()
                var dz = random.nextGaussian()
                if (random.nextBoolean()) dx = -dx
                if (random.nextBoolean()) dz = -dz

                level.addFreshEntity(ClusterFragment(EntityTypes.clusterFragment.get(), level).apply {
                    moveTo(position)
                    setDeltaMovement(dx, dy, dz)
                })
            }
        }
    }
}
