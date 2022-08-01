package at.martinthedragon.nucleartech

import at.martinthedragon.nucleartech.api.ModPlugin
import at.martinthedragon.nucleartech.api.NuclearTechPlugin
import at.martinthedragon.nucleartech.api.explosion.NuclearExplosionMk4Params
import at.martinthedragon.nucleartech.api.explosion.ExplosionAlgorithmRegistration
import at.martinthedragon.nucleartech.api.explosion.ExplosionLargeParams
import at.martinthedragon.nucleartech.api.explosion.ExplosionAlgorithmRegistration.Defaults as DefaultExplosionAlgorithms
import at.martinthedragon.nucleartech.api.hazard.radiation.HazmatRegistry
import at.martinthedragon.nucleartech.entity.NukeExplosion
import at.martinthedragon.nucleartech.explosion.ExplosionLarge
import at.martinthedragon.nucleartech.item.NTechItems
import at.martinthedragon.nucleartech.item.NuclearArmorMaterials
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.ArmorMaterials as VanillaArmorMaterials

@Suppress("unused")
@NuclearTechPlugin
class MainPlugin : ModPlugin {
    override val id = ResourceLocation(NuclearTech.MODID, NuclearTech.MODID)

    override fun registerExplosions(explosions: ExplosionAlgorithmRegistration) {
        explosions.register(DefaultExplosionAlgorithms.MK4, NuclearExplosionMk4Params::class.java, NukeExplosion.Companion)
        explosions.register(DefaultExplosionAlgorithms.EXPLOSION_LARGE, ExplosionLargeParams::class.java, ExplosionLarge)
    }

    override fun registerHazmatValues(hazmat: HazmatRegistry): Unit = with(hazmat) {
        registerMaterial(VanillaArmorMaterials.IRON, .0225F)
        registerMaterial(VanillaArmorMaterials.GOLD, .0225F)
        registerMaterial(VanillaArmorMaterials.NETHERITE, .08F)
        registerMaterial(NuclearArmorMaterials.steel, .045F)
        registerMaterial(NuclearArmorMaterials.titanium, .045F)
        registerMaterial(NuclearArmorMaterials.advancedAlloy, .07F)
        registerArmorRepairItem(NTechItems.cobaltIngot, .125F)
        registerMaterial(NuclearArmorMaterials.hazmat, .6F)
        registerMaterial(NuclearArmorMaterials.advancedHazmat, 1F)
        registerMaterial(NuclearArmorMaterials.reinforcedHazmat, 2F)
        registerMaterial(NuclearArmorMaterials.paAAlloy, 1.7F)
        registerArmorRepairItem(NTechItems.starmetalIngot, 1F)
        registerMaterial(NuclearArmorMaterials.combineSteel, 1.3F)
        registerMaterial(NuclearArmorMaterials.schrabidium, 3F)
    }
}
