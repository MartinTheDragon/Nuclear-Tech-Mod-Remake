package at.martinthedragon.nucleartech.hazard

import at.martinthedragon.nucleartech.*
import at.martinthedragon.nucleartech.hazard.type.*
import at.martinthedragon.nucleartech.item.NTechBlockItems
import at.martinthedragon.nucleartech.item.NTechItems
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
    const val Sr90 = 15F // Strontium
    const val Tc99 = 2.75F // Technetium
    const val I131 = 150F // Iodine
    const val Xe135 = 1250F // Xenon
    const val Cs137 = 20F // Caesium
    const val Au198 = 500F // Gold
    const val Pb209 = 10_000F // Lead
    const val At209 = 2000F // Astatine
    const val Po210 = 75F // Polonium
    const val Ra226 = 7.5F // Radon
    const val Ac227 = 30F // Actinium
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
    const val PuRG = 6.25F
    const val Am241 = 8.5F // Americium
    const val Am242 = 9.5F
    const val AmF = 4.75F
    const val AmRG = 9F

    // Other

    const val MOX = 2.5F
    const val Trinitite = .1F
    const val Waste = 15F

    // Fictional

    const val Sa326 = 15F // Schrabidium
    const val Sa327 = 17.5F // Solinium
    const val SaF = 5.85F
    const val Sr = Sa326 * .1F // Schraranium
    const val Sb = Sa326 * .1F // Ferric Schrabidate
    const val Gh336 = 5F

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
            register(NTechItems.propellantLittleBoy, HazardData(EXPLOSIVE, 2F))

            register(NTechTags.Items.DUSTS_CORDITE, HazardData(EXPLOSIVE, 2F))
            register(NTechTags.Items.DUSTS_BALLISTITE, DEFAULT_EXPLOSIVE_DATA)

            register(TagGroups.lithium, DEFAULT_HYDROREACTIVE_DATA)

            register(NTechTags.Items.DUSTS_COAL, HazardData(COAL, powder))
            register(NTechTags.Items.DUSTS_LIGNITE, HazardData(COAL, powder))

            register(TagGroups.asbestos, DEFAULT_ASBESTOS_DATA)

            register(TagGroups.whitePhosphorus, HazardData(HEAT, 5F))
            register(TagGroups.redPhosphorus, HazardData(HEAT, 2F))

            register(Materials.americium241, HazardData(RADIATION, Am241))
            register(Materials.americium242, HazardData(RADIATION, Am242))
            register(Materials.americiumFuel, HazardData(RADIATION, AmF))
            register(Materials.cobalt60, HazardData(RADIATION, Co60).addEntry(HEAT))
            register(TagGroups.ferricSchrabidate, HazardData(RADIATION, Sb).addEntry(BLINDING, 2.5F))
            register(Materials.ghiorsium336, HazardData(RADIATION, Gh336))
            register(Materials.gold198, HazardData(RADIATION, Au198).addEntry(HEAT, 5F))
            register(Materials.lead209, HazardData(RADIATION, Pb209).addEntry(BLINDING, 2.5F).addEntry(HEAT, 7F))
            register(Materials.neptuniumFuel, HazardData(RADIATION, NpF))
            register(Materials.plutonium238, HazardData(RADIATION, Pu238).addEntry(HEAT, 3F))
            register(Materials.plutonium239, HazardData(RADIATION, Pu239))
            register(Materials.plutonium240, HazardData(RADIATION, Pu240))
            register(Materials.plutonium241, HazardData(RADIATION, Pu241))
            register(Materials.radium226, HazardData(RADIATION, Ra226))
            register(Materials.reactorGradeAmericium, HazardData(RADIATION, AmRG))
            register(Materials.reactorGradePlutonium, HazardData(RADIATION, PuRG))
            register(Materials.strontium90, HazardData(RADIATION, Sr90).addEntry(HEAT).addEntry(HYDROREACTIVE))
            register(Materials.technetium99, HazardData(RADIATION, Tc99))
            register(Materials.uranium233, HazardData(RADIATION, U233))
            register(Materials.uranium235, HazardData(RADIATION, U235))
            register(Materials.uranium238, HazardData(RADIATION, U238))
            register(TagGroups.actinium227, HazardData(RADIATION, Ac227))
            register(TagGroups.moxFuel, HazardData(RADIATION, MOX))
            register(TagGroups.naturalPlutonium, HazardData(RADIATION, Pu))
            register(TagGroups.naturalUranium, HazardData(RADIATION, U))
            register(TagGroups.neptunium, HazardData(RADIATION, Np237))
            register(TagGroups.plutoniumFuel, HazardData(RADIATION, PuF))
            register(TagGroups.polonium, HazardData(RADIATION, Po210).addEntry(HEAT, 3F))
            register(TagGroups.schrabidium, HazardData(RADIATION, Sa326).addEntry(BLINDING, 2.5F))
            register(TagGroups.schrabidiumFuel, HazardData(RADIATION, SaF).addEntry(BLINDING, 2.5F))
            register(TagGroups.schraranium, HazardData(RADIATION, Sr).addEntry(BLINDING, 2.5F))
            register(TagGroups.solinium, HazardData(RADIATION, Sa327).addEntry(BLINDING, 2.5F))
            register(TagGroups.thorium, HazardData(RADIATION, Th232))
            register(TagGroups.thoriumFuel, HazardData(RADIATION, ThF))
            register(TagGroups.uraniumFuel, HazardData(RADIATION, UF))
            register(TagGroups.yellowcake, HazardData(RADIATION, 15F))

            register(NTechTags.Items.ORES_URANIUM, HazardData.EMPTY)
            register(NTechTags.Items.ORES_THORIUM, HazardData.EMPTY)
            register(NTechTags.Items.RAW_MATERIALS_URANIUM, HazardData.EMPTY)
            register(NTechTags.Items.RAW_MATERIALS_THORIUM, HazardData.EMPTY)

            register(NTechBlockItems.slakedSellafite, HazardData(RADIATION, .5F))
            register(NTechBlockItems.sellafite, HazardData(RADIATION, 1F))
            register(NTechBlockItems.hotSellafite, HazardData(RADIATION, 2.5F))
            register(NTechBlockItems.boilingSellafite, HazardData(RADIATION, 4F))
            register(NTechBlockItems.blazingSellafite, HazardData(RADIATION, 5F))
            register(NTechBlockItems.infernalSellafite, HazardData(RADIATION, 7.5F))
            register(NTechBlockItems.sellafiteCorium, HazardData(RADIATION, 10F))
        }
    }

    private fun register(supplier: Supplier<out Item>, data: HazardData) = HazardSystem.register(supplier.get(), data)

    private fun register(tagGroup: TagGroup, data: HazardData) = with(HazardSystem) {
        tagGroup.ore?.let { register(it, modifyEntries(data, ore)) } ?: run {
            tagGroup.materialGroup.ore()?.let { register(it, modifyEntries(data, ore)) }
            tagGroup.materialGroup.deepOre()?.let { register(it, modifyEntries(data, ore)) }
        }
        tagGroup.block?.let { register(it, modifyEntries(data, block)) } ?: tagGroup.materialGroup.block()?.let { register(it, modifyEntries(data, block)) }
        tagGroup.raw?.let { register(it, modifyEntries(data, raw)) } ?: tagGroup.materialGroup.raw()?.let { register(it, modifyEntries(data, raw)) }
        tagGroup.ingot?.let { register(it, modifyEntries(data, ingot)) } ?: tagGroup.materialGroup.ingot()?.let { register(it, modifyEntries(data, ingot)) }
        tagGroup.nugget?.let { register(it, modifyEntries(data, nugget)) } ?: tagGroup.materialGroup.nugget()?.let { register(it, modifyEntries(data, nugget)) }
        tagGroup.crystals?.let { register(it, modifyEntries(data, crystal)) } ?: tagGroup.materialGroup.crystals()?.let { register(it, modifyEntries(data, crystal)) }
        tagGroup.powder?.let { register(it, modifyEntries(data, powder)) } ?: tagGroup.materialGroup.powder()?.let { register(it, modifyEntries(data, powder)) }
        tagGroup.plate?.let { register(it, modifyEntries(data, plate)) } ?: tagGroup.materialGroup.plate()?.let { register(it, modifyEntries(data, plate)) }
        tagGroup.wire?.let { register(it, modifyEntries(data, wire)) } ?: tagGroup.materialGroup.wire()?.let { register(it, modifyEntries(data, wire)) }
        return@with
    }

    private fun register(materialGroup: MaterialGroup, data: HazardData) = with(HazardSystem) {
        materialGroup.ore()?.let { register(it, modifyEntries(data, ore)) }
        materialGroup.deepOre()?.let { register(it, modifyEntries(data, ore)) }
        materialGroup.block()?.let { register(it, modifyEntries(data, block)) }
        materialGroup.raw()?.let { register(it, modifyEntries(data, raw)) }
        materialGroup.ingot()?.let { register(it, modifyEntries(data, ingot)) }
        materialGroup.nugget()?.let { register(it, modifyEntries(data, nugget)) }
        materialGroup.crystals()?.let { register(it, modifyEntries(data, crystal)) }
        materialGroup.powder()?.let { register(it, modifyEntries(data, powder)) }
        materialGroup.plate()?.let { register(it, modifyEntries(data, plate)) }
        materialGroup.wire()?.let { register(it, modifyEntries(data, wire)) }
        return@with
    }

    private fun modifyEntries(data: HazardData, multiplier: Float) =
        HazardData().addEntries(data.getEntries().map { it.copy(level = it.level * multiplier) })
}
