package at.martinthedragon.nucleartech.entities.missiles

import at.martinthedragon.nucleartech.api.explosions.NuclearExplosionMk4Params
import at.martinthedragon.nucleartech.api.explosions.createAndStart
import at.martinthedragon.nucleartech.config.NuclearConfig
import at.martinthedragon.nucleartech.entities.EntityTypes
import at.martinthedragon.nucleartech.explosions.Explosions
import net.minecraft.core.BlockPos
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.EntityType
import net.minecraft.world.level.Level

class NuclearMissile : AbstractMissile {
    constructor(entityType: EntityType<NuclearMissile>, level: Level) : super(entityType, level)
    constructor(level: Level, startPos: BlockPos, targetPos: BlockPos) : super(EntityTypes.missileNuclear.get(), level, startPos, targetPos)

    override val renderModel = missileModel("missile_nuclear")
    override val renderScale = 1.5F

    override fun onImpact() {
        Explosions.getBuiltinDefault().createAndStart(level, position(), NuclearConfig.explosions.missileStrength.get().toFloat(), NuclearExplosionMk4Params())
    }
}
