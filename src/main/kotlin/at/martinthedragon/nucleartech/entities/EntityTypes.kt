package at.martinthedragon.nucleartech.entities

import at.martinthedragon.nucleartech.RegistriesAndLifecycle.ENTITIES
import at.martinthedragon.nucleartech.explosions.NukeExplosionEntity
import net.minecraft.entity.EntityClassification
import net.minecraft.entity.EntityType

object EntityTypes {
    val nukeExplosionEntity = ENTITIES.register("nuke_explosion_entity") {
        EntityType.Builder.of(::NukeExplosionEntity, EntityClassification.MISC).fireImmune().canSpawnFarFromPlayer().build("nuke_explosion_entity")
    }
}
