package at.martinthedragon.nucleartech.entities

import at.martinthedragon.nucleartech.RegistriesAndLifecycle.ENTITIES
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.MobCategory
import net.minecraftforge.registries.RegistryObject

object EntityTypes {
    val nukeExplosionEntity: RegistryObject<EntityType<NukeExplosionEntity>> = ENTITIES.register("nuclear_explosion") { EntityType.Builder.of(::NukeExplosionEntity, MobCategory.MISC).fireImmune().build("nuke_explosion_entity") }
    val nukeCloudEntity: RegistryObject<EntityType<MushroomCloudEntity>> = ENTITIES.register("mushroom_cloud") { EntityType.Builder.of(::MushroomCloudEntity, MobCategory.MISC).fireImmune().sized(20F, 40F).clientTrackingRange(64).build("nuke_cloud_entity") }
    val falloutRainEntity: RegistryObject<EntityType<FalloutRainEntity>> = ENTITIES.register("fallout_rain") { EntityType.Builder.of(::FalloutRainEntity, MobCategory.MISC).fireImmune().build("fallout_rain_entity") }
    val nuclearCreeper: RegistryObject<EntityType<NuclearCreeper>> = ENTITIES.register("nuclear_creeper") { EntityType.Builder.of(::NuclearCreeper, MobCategory.MONSTER).sized(.6F, 1.7F).clientTrackingRange(8).build("nuclear_creeper") }
}
