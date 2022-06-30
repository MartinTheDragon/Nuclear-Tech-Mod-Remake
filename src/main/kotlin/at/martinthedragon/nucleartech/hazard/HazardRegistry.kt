package at.martinthedragon.nucleartech.hazard

import at.martinthedragon.nucleartech.*
import at.martinthedragon.nucleartech.hazard.type.*
import net.minecraft.world.item.Item
import net.minecraft.world.item.Items
import net.minecraftforge.common.Tags
import java.util.function.Supplier

// TODO API
object HazardRegistry {
    /*
    Radiation value constants
    Item radiation, block radiation, etc. values are derived from these
    Sorted by order in the periodic table
    An 'F' at the end means the value is for Fuel
    */

    const val Co60 = 30F // Cobalt
    const val Tc99 = 2.75 // Technetium
    const val I131 = 150F // Iodine
    const val Xe135 = 1250F // Xenon
    const val Cs137 = 20F // Caesium
    const val Au198 = 500F // Gold
    const val At209 = 2000F // Astatine
    const val Po210 = 75F // Polonium
    const val Ra226 = 7.5F // Radon
    const val Th232 = .1F // Thorium
    const val ThF = 1.75F
    const val U = .35F // Uranium
    const val U233 = 5F
    const val U235 = 1F
    const val U238 = .25F
    const val UF = .5F
    const val Np237 = 2.5F // Neptunium
    const val NpF = 1.5F
    const val Pu = 7.5F // Plutonium
    const val Pu238 = 10F
    const val Pu239 = 5F
    const val Pu240 = 7.5F
    const val Pu241 = 25F
    const val PuF = 4.25F
    const val Am241 = 8.5F // Americium
    const val Am242 = 9.5F
    const val AmF = 4.75F

    // Other

    const val MOX = 2.5F
    const val Trinitite = .1F
    const val Waste = 15F

    // Fictional

    const val Sa326 = 15F // Schrabidium
    const val Sa327 = 17.5F // Solinium
    const val SaF = 5.85F
    const val Sr = Sa326 * .1F // Schraranium

    private const val ingot = 1F
    private const val nugget = .1F
    private const val gem = 1F
    private const val plate = 1F
    private const val powderMultiplier = 3F
    private const val powder = ingot * powderMultiplier
    private const val powderTiny = nugget * powderMultiplier
    private const val ore = ingot
    private const val raw = ingot
    private const val block = 10F
    private const val crystal = block
    private const val wire = ingot

    private val RADIATION = RadiationHazard()
    private val DIGAMMA = DigammaHazard()
    private val HEAT = HeatHazard()
    private val BLINDING = BlindingHazard()
    private val ASBESTOS = AsbestosHazard()
    private val COAL = CoalHazard()
    private val HYDROREACTIVE = HydroreactiveHazard()
    private val EXPLOSIVE = ExplosiveHazard()

    private val DEFAULT_HEAT_DATA = HazardData(HEAT)
    private val DEFAULT_BLINDING_DATA = HazardData(BLINDING)
    private val DEFAULT_ASBESTOS_DATA = HazardData(ASBESTOS)
    private val DEFAULT_COAL_DATA = HazardData(COAL)
    private val DEFAULT_HYDROREACTIVE_DATA = HazardData(HYDROREACTIVE)
    private val DEFAULT_EXPLOSIVE_DATA = HazardData(EXPLOSIVE)

    fun registerItems() {
        with(HazardSystem) {
            register(Tags.Items.GUNPOWDER, DEFAULT_EXPLOSIVE_DATA)
            register(Items.TNT, HazardData(EXPLOSIVE, 4F))
            register(Items.PUMPKIN_PIE, DEFAULT_EXPLOSIVE_DATA)

            register(NuclearTags.Items.DUSTS_CORDITE, HazardData(EXPLOSIVE, 2F))
            register(NuclearTags.Items.DUSTS_BALLISTITE, DEFAULT_EXPLOSIVE_DATA)

            register(NuclearTags.Items.DUSTS_COAL, HazardData(COAL, powder))
            register(NuclearTags.Items.DUSTS_LIGNITE, HazardData(COAL, powder))

            register(TagGroups.naturalUranium, HazardData(RADIATION, U))
            register(Materials.uranium233, HazardData(RADIATION, U233))
            register(Materials.uranium235, HazardData(RADIATION, U235))
            register(Materials.uranium238, HazardData(RADIATION, U238))
            register(TagGroups.uraniumFuel, HazardData(RADIATION, UF))
            register(TagGroups.thorium, HazardData(RADIATION, Th232))
            register(TagGroups.naturalPlutonium, HazardData(RADIATION, Pu))
            register(Materials.plutonium238, HazardData(RADIATION, Pu238))
            register(Materials.plutonium239, HazardData(RADIATION, Pu239))
            register(Materials.plutonium240, HazardData(RADIATION, Pu240))
            register(TagGroups.plutoniumFuel, HazardData(RADIATION, PuF))
            register(TagGroups.neptunium, HazardData(RADIATION, Np237))
            register(TagGroups.polonium, HazardData(RADIATION, Po210).addEntry(HEAT))
            register(TagGroups.schrabidium, HazardData(RADIATION, Sa326).addEntry(BLINDING))
            register(TagGroups.solinium, HazardData(RADIATION, Sa327).addEntry(BLINDING))
            register(TagGroups.schrabidiumFuel, HazardData(RADIATION, SaF).addEntry(BLINDING))
            register(TagGroups.schraranium, HazardData(RADIATION, Sr).addEntry(BLINDING))

            register(ModBlockItems.slakedSellafite, HazardData(RADIATION, .5F))
            register(ModBlockItems.sellafite, HazardData(RADIATION, 1F))
            register(ModBlockItems.hotSellafite, HazardData(RADIATION, 2.5F))
            register(ModBlockItems.boilingSellafite, HazardData(RADIATION, 4F))
            register(ModBlockItems.blazingSellafite, HazardData(RADIATION, 5F))
            register(ModBlockItems.infernalSellafite, HazardData(RADIATION, 7.5F))
            register(ModBlockItems.sellafiteCorium, HazardData(RADIATION, 10F))
        }
    }

    private fun register(supplier: Supplier<out Item>, data: HazardData) = HazardSystem.register(supplier.get(), data)

    private fun register(tagGroup: TagGroup, data: HazardData) = with(HazardSystem) {
        tagGroup.ore?.let { register(it, modifyRadiationEntry(data, ore)) } ?: run {
            tagGroup.materialGroup.ore()?.let { register(it, modifyRadiationEntry(data, ore)) }
            tagGroup.materialGroup.deepOre()?.let { register(it, modifyRadiationEntry(data, ore)) }
        }
        tagGroup.block?.let { register(it, modifyRadiationEntry(data, block)) } ?: tagGroup.materialGroup.block()?.let { register(it, modifyRadiationEntry(data, block)) }
        tagGroup.raw?.let { register(it, modifyRadiationEntry(data, raw)) } ?: tagGroup.materialGroup.raw()?.let { register(it, modifyRadiationEntry(data, raw)) }
        tagGroup.ingot?.let { register(it, modifyRadiationEntry(data, ingot)) } ?: tagGroup.materialGroup.ingot()?.let { register(it, modifyRadiationEntry(data, ingot)) }
        tagGroup.nugget?.let { register(it, modifyRadiationEntry(data, nugget)) } ?: tagGroup.materialGroup.nugget()?.let { register(it, modifyRadiationEntry(data, nugget)) }
        tagGroup.crystals?.let { register(it, modifyRadiationEntry(data, crystal)) } ?: tagGroup.materialGroup.crystals()?.let { register(it, modifyRadiationEntry(data, crystal)) }
        tagGroup.powder?.let { register(it, modifyRadiationEntry(data, powder)) } ?: tagGroup.materialGroup.powder()?.let { register(it, modifyRadiationEntry(data, powder)) }
        tagGroup.plate?.let { register(it, modifyRadiationEntry(data, plate)) } ?: tagGroup.materialGroup.plate()?.let { register(it, modifyRadiationEntry(data, plate)) }
        tagGroup.wire?.let { register(it, modifyRadiationEntry(data, wire)) } ?: tagGroup.materialGroup.wire()?.let { register(it, modifyRadiationEntry(data, wire)) }
        return@with
    }

    private fun register(materialGroup: MaterialGroup, data: HazardData) = with(HazardSystem) {
        materialGroup.ore()?.let { register(it, modifyRadiationEntry(data, ore)) }
        materialGroup.deepOre()?.let { register(it, modifyRadiationEntry(data, ore)) }
        materialGroup.block()?.let { register(it, modifyRadiationEntry(data, block)) }
        materialGroup.raw()?.let { register(it, modifyRadiationEntry(data, raw)) }
        materialGroup.ingot()?.let { register(it, modifyRadiationEntry(data, ingot)) }
        materialGroup.nugget()?.let { register(it, modifyRadiationEntry(data, nugget)) }
        materialGroup.crystals()?.let { register(it, modifyRadiationEntry(data, crystal)) }
        materialGroup.powder()?.let { register(it, modifyRadiationEntry(data, powder)) }
        materialGroup.plate()?.let { register(it, modifyRadiationEntry(data, plate)) }
        materialGroup.wire()?.let { register(it, modifyRadiationEntry(data, wire)) }
        return@with
    }

    private fun modifyRadiationEntry(data: HazardData, multiplier: Float) =
        HazardData().addEntries(data.getEntries().map { if (it.hazard is RadiationHazard) it.copy(level = it.level * multiplier) else it })
}
