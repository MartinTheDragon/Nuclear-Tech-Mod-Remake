package at.martinthedragon.nucleartech.entity.missile

import at.martinthedragon.nucleartech.api.explosion.ExplosionLargeParams
import at.martinthedragon.nucleartech.block.NTechBlocks
import at.martinthedragon.nucleartech.block.VolcanoBlock
import at.martinthedragon.nucleartech.entity.EntityTypes
import at.martinthedragon.nucleartech.explosion.ExplosionLarge
import at.martinthedragon.nucleartech.fluid.NTechFluids
import net.minecraft.core.BlockPos
import net.minecraft.world.entity.EntityType
import net.minecraft.world.level.Level

class TectonicMissile : AbstractMissile {
    constructor(entityType: EntityType<TectonicMissile>, level: Level) : super(entityType, level)
    constructor(level: Level, startPos: BlockPos, targetPos: BlockPos) : super(EntityTypes.missileTectonic.get(), level, startPos, targetPos)

    override val renderModel = MODEL_MISSILE_NUCLEAR
    override val renderScale = 1.5F

    override fun onImpact() {
        ExplosionLarge.createAndStart(level, position(), 10F, ExplosionLargeParams(fire = true, cloud = true, rubble = true))

        for (x in -1..1) for (y in -1..1) for (z in -1..1)
            level.setBlockAndUpdate(blockPosition().offset(x, y, z), NTechFluids.volcanicLava.block.get().defaultBlockState())

        level.setBlockAndUpdate(blockPosition(), NTechBlocks.volcanoCore.get().defaultBlockState().let {
            if (random.nextInt(5) == 0) {
                it.setValue(VolcanoBlock.SMOLDERING, true)
            } else {
                it.setValue(VolcanoBlock.GROWS, random.nextInt(10) == 0).setValue(VolcanoBlock.EXTINGUISHES, random.nextInt(10) < 9)
            }
        })
    }
}
