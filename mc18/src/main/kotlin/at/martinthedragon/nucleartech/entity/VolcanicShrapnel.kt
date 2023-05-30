package at.martinthedragon.nucleartech.entity

import at.martinthedragon.nucleartech.block.entity.VolcanoBlockEntity
import at.martinthedragon.nucleartech.explosion.ExplosionVNT
import at.martinthedragon.nucleartech.fluid.NTechFluids
import at.martinthedragon.nucleartech.math.toVec3Middle
import net.minecraft.world.entity.EntityType
import net.minecraft.world.level.Level
import net.minecraft.world.phys.BlockHitResult

class VolcanicShrapnel(entityType: EntityType<out VolcanicShrapnel>, level: Level) : Shrapnel(entityType, level) {
    constructor(level: Level) : this(EntityTypes.volcanicShrapnel.get(), level)

    override fun onHitBlock(hitResult: BlockHitResult) {
        super.onHitBlock(hitResult)

        if (deltaMovement.y < -0.2) {
            val above = hitResult.blockPos.above()
            if (level.getBlockState(above).material.isReplaceable)
                level.setBlockAndUpdate(above, NTechFluids.volcanicLava.block.get().defaultBlockState())

            // TODO set monoxide
        } else if (deltaMovement.y > 0.0) {
            ExplosionVNT.createStandard(level, hitResult.blockPos.toVec3Middle(), 7F).apply(VolcanoBlockEntity.volcanoExplosionAttributes).explode()
        }
    }
}
