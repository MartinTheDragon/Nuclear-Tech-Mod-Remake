package at.martinthedragon.nucleartech.entity

import at.martinthedragon.nucleartech.RegistriesAndLifecycle.ENTITIES
import at.martinthedragon.nucleartech.entity.missile.*
import at.martinthedragon.nucleartech.registerK
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.MobCategory

object EntityTypes {
    val clusterFragment = ENTITIES.registerK("cluster_fragment") { EntityType.Builder.of(::ClusterFragment, MobCategory.MISC).sized(.5F, .5F).updateInterval(20).clientTrackingRange(10).build("cluster_fragment") }
    val falloutRain = ENTITIES.registerK("fallout_rain") { EntityType.Builder.of(::FalloutRain, MobCategory.MISC).fireImmune().build("fallout_rain") }
    val meteor = ENTITIES.registerK("meteor") { EntityType.Builder.of(::Meteor, MobCategory.MISC).sized(4F, 4F).fireImmune().clientTrackingRange(100).build("meteor") }
    val missileBunkerBuster = ENTITIES.registerK("bunker_buster_missile") { EntityType.Builder.of(::BunkerBusterMissile, MobCategory.MISC).fireImmune().sized(1.5F, 1.5F).clientTrackingRange(100).build("bunker_buster_missile") }
    val missileBunkerBusterStrong = ENTITIES.registerK("strong_bunker_buster_missile") { EntityType.Builder.of(::StrongBunkerBusterMissile, MobCategory.MISC).fireImmune().sized(1.5F, 1.5F).clientTrackingRange(100).build("strong_bunker_buster_missile") }
    val missileBurst = ENTITIES.registerK("burst_missile") { EntityType.Builder.of(::BurstMissile, MobCategory.MISC).fireImmune().sized(1.5F, 1.5F).clientTrackingRange(100).build("burst_missile") }
    val missileCluster = ENTITIES.registerK("cluster_missile") { EntityType.Builder.of(::ClusterMissile, MobCategory.MISC).fireImmune().sized(1.5F, 1.5F).clientTrackingRange(100).build("cluster_missile") }
    val missileClusterStrong = ENTITIES.registerK("strong_cluster_missile") { EntityType.Builder.of(::StrongClusterMissile, MobCategory.MISC).fireImmune().sized(1.5F, 1.5F).clientTrackingRange(100).build("strong_cluster_missile") }
    val missileDrill = ENTITIES.registerK("drill_missile") { EntityType.Builder.of(::DrillMissile, MobCategory.MISC).fireImmune().sized(1.5F, 1.5F).clientTrackingRange(100).build("drill_missile") }
    val missileHE = ENTITIES.registerK("he_missile") { EntityType.Builder.of(::HEMissile, MobCategory.MISC).fireImmune().sized(1.5F, 1.5F).clientTrackingRange(100).build("he_missile") }
    val missileHEStrong = ENTITIES.registerK("strong_missile") { EntityType.Builder.of(::StrongHEMissile, MobCategory.MISC).fireImmune().sized(1.5F, 1.5F).clientTrackingRange(100).build("strong_missile") }
    val missileIncendiary = ENTITIES.registerK("incendiary_missile") { EntityType.Builder.of(::IncendiaryMissile, MobCategory.MISC).fireImmune().sized(1.5F, 1.5F).clientTrackingRange(100).build("incendiary_missile") }
    val missileIncendiaryStrong = ENTITIES.registerK("strong_incendiary_missile") { EntityType.Builder.of(::StrongIncendiaryMissile, MobCategory.MISC).fireImmune().sized(1.5F, 1.5F).clientTrackingRange(100).build("strong_incendiary_missile") }
    val missileInferno = ENTITIES.registerK("inferno_missile") { EntityType.Builder.of(::InfernoMissile, MobCategory.MISC).fireImmune().sized(1.5F, 1.5F).clientTrackingRange(100).build("inferno_missile") }
    val missileNuclear = ENTITIES.registerK("nuclear_missile") { EntityType.Builder.of(::NuclearMissile, MobCategory.MISC).fireImmune().sized(1.5F, 1.5F).clientTrackingRange(100).build("nuclear_missile") }
    val missileRain = ENTITIES.registerK("rain_missile") { EntityType.Builder.of(::RainMissile, MobCategory.MISC).fireImmune().sized(1.5F, 1.5F).clientTrackingRange(100).build("rain_missile") }
    val missileTectonic = ENTITIES.registerK("tectonic_missile") { EntityType.Builder.of(::TectonicMissile, MobCategory.MISC).fireImmune().sized(1.5F, 1.5F).clientTrackingRange(100).build("tectonic_missile") }
    val mushroomCloud = ENTITIES.registerK("mushroom_cloud") { EntityType.Builder.of(::MushroomCloud, MobCategory.MISC).fireImmune().sized(20F, 40F).clientTrackingRange(64).build("mushroom_cloud") }
    val nuclearCreeper = ENTITIES.registerK("nuclear_creeper") { EntityType.Builder.of(::NuclearCreeper, MobCategory.MONSTER).sized(.6F, 1.7F).clientTrackingRange(8).build("nuclear_creeper") }
    val nuclearExplosion = ENTITIES.registerK("nuclear_explosion") { EntityType.Builder.of(::NukeExplosion, MobCategory.MISC).fireImmune().build("nuclear_explosion") }
    val oilSpill = ENTITIES.registerK("oil_spill") { EntityType.Builder.of(::OilSpill, MobCategory.MISC).build("oil_spill") }
    val rbmkDebris = ENTITIES.registerK("rbmk_debris") { EntityType.Builder.of(::RBMKDebris, MobCategory.MISC).sized(1F, 1F).updateInterval(20).build("rbmk_debris") }
    val shrapnel = ENTITIES.registerK("shrapnel") { EntityType.Builder.of(::Shrapnel, MobCategory.MISC).sized(.5F, .5F).updateInterval(20).clientTrackingRange(10).build("shrapnel") }
    val volcanicShrapnel = ENTITIES.registerK("volcanic_shrapnel") { EntityType.Builder.of(::VolcanicShrapnel, MobCategory.MISC).sized(.5F, .5F).updateInterval(20).clientTrackingRange(16).build("volcanic_shrapnel") }
    val wasteItemEntity = ENTITIES.registerK("waste_item") { EntityType.Builder.of(::WasteItemEntity, MobCategory.MISC).sized(.25F, .25F).clientTrackingRange(6).updateInterval(20).build("waste_item") }
}
