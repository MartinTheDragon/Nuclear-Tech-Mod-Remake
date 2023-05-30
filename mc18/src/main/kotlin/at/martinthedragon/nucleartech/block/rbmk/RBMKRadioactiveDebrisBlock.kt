package at.martinthedragon.nucleartech.block.rbmk

import at.martinthedragon.nucleartech.capability.Capabilities
import at.martinthedragon.nucleartech.hazard.EntityContaminationEffects
import at.martinthedragon.nucleartech.math.toBlockPos
import at.martinthedragon.nucleartech.math.toVec3Middle
import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.damagesource.DamageSource
import net.minecraft.world.entity.EntitySelector
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.AABB
import java.util.*

class RBMKRadioactiveDebrisBlock(properties: Properties) : RBMKBurningDebrisBlock(properties) {
    override fun tick(state: BlockState, level: ServerLevel, pos: BlockPos, random: Random) {
        @Suppress("DEPRECATION")
        super.tick(state, level, pos, random)
        radiate(level, pos)
    }

    override fun getNextTickSchedule(random: Random) = 20 + random.nextInt(20)

    private fun radiate(level: ServerLevel, pos: BlockPos) {
        val rads = 1_000_000F
        val range = 100.0
        val origin = pos.toVec3Middle()

        val entities = level.getEntitiesOfClass(LivingEntity::class.java, AABB(pos).inflate(range), EntitySelector.LIVING_ENTITY_STILL_ALIVE)
        for (entity in entities) {
            val capability = Capabilities.getContamination(entity) ?: continue
            val vector = entity.eyePosition.subtract(origin)
            val distance = vector.length()
            val normalVector = vector.normalize()

            var resistance = 0F
            for (i in 1 until distance.toInt()) {
                val nextPos = origin.add(normalVector.scale(i.toDouble())).toBlockPos()
                resistance += level.getBlockState(nextPos).getExplosionResistance(level, nextPos, null)
            }

            val effectiveRads = rads / resistance.coerceAtLeast(1F) / (distance * distance).toFloat().coerceAtLeast(1F)
            EntityContaminationEffects.contaminate(entity, capability, EntityContaminationEffects.HazardType.Radiation, EntityContaminationEffects.ContaminationType.Creative, effectiveRads)

            if (distance < 5) entity.hurt(DamageSource.IN_FIRE, 100F)

            // TODO marshmallow
        }
    }
}
