package at.martinthedragon.nucleartech.entities

import at.martinthedragon.nucleartech.RegistriesAndLifecycle.ENTITIES
import net.minecraft.entity.EntityClassification
import net.minecraft.entity.EntityType
import net.minecraftforge.fml.RegistryObject

object EntityTypes {
    val nukeExplosionEntity: RegistryObject<EntityType<NukeExplosionEntity>> = ENTITIES.register("nuclear_explosion") { EntityType.Builder.of(::NukeExplosionEntity, EntityClassification.MISC).fireImmune().build("nuke_explosion_entity") }
    val nukeCloudEntity: RegistryObject<EntityType<NukeCloudEntity>> = ENTITIES.register("mushroom_cloud") { EntityType.Builder.of(::NukeCloudEntity, EntityClassification.MISC).fireImmune().sized(20F, 40F).clientTrackingRange(64).build("nuke_cloud_entity") }
    val falloutRainEntity: RegistryObject<EntityType<FalloutRainEntity>> = ENTITIES.register("fallout_rain") { EntityType.Builder.of(::FalloutRainEntity, EntityClassification.MISC).fireImmune().build("fallout_rain_entity") }
    val nuclearCreeperEntity: RegistryObject<EntityType<NuclearCreeperEntity>> = ENTITIES.register("nuclear_creeper") { EntityType.Builder.of(::NuclearCreeperEntity, EntityClassification.MONSTER).sized(.6F, 1.7F).clientTrackingRange(8).build("nuclear_creeper") }
}
