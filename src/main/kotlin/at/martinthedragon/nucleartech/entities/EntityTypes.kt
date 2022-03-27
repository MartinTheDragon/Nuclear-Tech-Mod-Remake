package at.martinthedragon.nucleartech.entities

import at.martinthedragon.nucleartech.RegistriesAndLifecycle.ENTITIES
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.MobCategory
import net.minecraftforge.registries.RegistryObject

object EntityTypes {
    val nuclearExplosion: RegistryObject<EntityType<NukeExplosion>> = ENTITIES.register("nuclear_explosion") { EntityType.Builder.of(::NukeExplosion, MobCategory.MISC).fireImmune().build("nuke_explosion_entity") }
    val mushroomCloud: RegistryObject<EntityType<MushroomCloud>> = ENTITIES.register("mushroom_cloud") { EntityType.Builder.of(::MushroomCloud, MobCategory.MISC).fireImmune().sized(20F, 40F).clientTrackingRange(64).build("nuke_cloud_entity") }
    val falloutRain: RegistryObject<EntityType<FalloutRain>> = ENTITIES.register("fallout_rain") { EntityType.Builder.of(::FalloutRain, MobCategory.MISC).fireImmune().build("fallout_rain_entity") }
    val nuclearCreeper: RegistryObject<EntityType<NuclearCreeper>> = ENTITIES.register("nuclear_creeper") { EntityType.Builder.of(::NuclearCreeper, MobCategory.MONSTER).sized(.6F, 1.7F).clientTrackingRange(8).build("nuclear_creeper") }
}
