package at.martinthedragon.nucleartech.entities.missiles

import at.martinthedragon.nucleartech.api.explosions.ExplosionLargeParams
import at.martinthedragon.nucleartech.api.explosions.createAndStart
import at.martinthedragon.nucleartech.entities.EntityTypes
import at.martinthedragon.nucleartech.explosions.ExplosionLarge
import at.martinthedragon.nucleartech.ntm
import net.minecraft.core.BlockPos
import net.minecraft.world.entity.EntityType
import net.minecraft.world.level.Level

open class HEMissile : AbstractMissile {
    constructor(entityType: EntityType<HEMissile>, level: Level) : super(entityType, level)
    constructor(level: Level, startPos: BlockPos, targetPos: BlockPos) : super(EntityTypes.missileHE.get(), level, startPos, targetPos)

    override fun onImpact() {
        ExplosionLarge.createAndStart(level, position(), 10F, ExplosionLargeParams(cloud = true, rubble = true, shrapnel = true))
    }
}
