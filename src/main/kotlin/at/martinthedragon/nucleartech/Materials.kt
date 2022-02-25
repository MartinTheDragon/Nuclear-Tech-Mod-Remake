@file:Suppress("unused")

package at.martinthedragon.nucleartech

import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Item
import java.util.function.Supplier
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract
import net.minecraft.world.item.Items as VI

private typealias BI = ModBlockItems
private typealias I = ModItems

private typealias VG = VanillaGroup
private typealias MG = ModGroup

object Materials {
    private val allMaterials = mutableListOf<MaterialGroup>()

    fun getAllMaterials(): List<MaterialGroup> = allMaterials

    val coal = add(VG(ore = VI.COAL_ORE, deepOre = VI.DEEPSLATE_COAL_ORE, block = VI.COPPER_BLOCK, ingot = VI.COAL, powder = I.coalPowder))
    val iron = add(VG(ore = VI.IRON_ORE, deepOre = VI.DEEPSLATE_IRON_ORE, block = VI.IRON_BLOCK, raw = VI.RAW_IRON, ingot = VI.IRON_INGOT, nugget = VI.IRON_NUGGET, crystals = I.ironCrystals, powder = I.ironPowder, plate = I.ironPlate))
    val copper = add(VG(ore = VI.COPPER_ORE, deepOre = VI.DEEPSLATE_COPPER_ORE, block = VI.COPPER_BLOCK, raw = VI.RAW_COPPER, ingot = VI.COPPER_INGOT, crystals = I.copperCrystals, powder = I.copperPowder, plate = I.copperPlate, wire = I.copperWire))
    val gold = add(VG(ore = VI.GOLD_ORE, deepOre = VI.DEEPSLATE_GOLD_ORE, block = VI.GOLD_BLOCK, raw = VI.RAW_GOLD, ingot = VI.GOLD_INGOT, nugget = VI.GOLD_NUGGET, crystals = I.goldCrystals, powder = I.goldPowder, plate = I.goldPlate, wire = I.goldWire))
    val redstone = add(VG(ore = VI.REDSTONE_ORE, deepOre = VI.DEEPSLATE_REDSTONE_ORE, block = VI.REDSTONE_BLOCK, ingot = VI.REDSTONE, crystals = I.redstoneCrystals))
    val emerald = add(VG(ore = VI.EMERALD_ORE, deepOre = VI.DEEPSLATE_EMERALD_ORE, block = VI.EMERALD_BLOCK, ingot = VI.EMERALD, powder = I.emeraldPowder))
    val lazuli = add(VG(ore = VI.LAPIS_ORE, deepOre = VI.DEEPSLATE_LAPIS_ORE, block = VI.LAPIS_BLOCK, ingot = VI.LAPIS_LAZULI, powder = I.lapisLazuliPowder))
    val diamond = add(VG(ore = VI.DIAMOND_ORE, deepOre = VI.DEEPSLATE_DIAMOND_ORE, block = VI.DIAMOND_BLOCK, ingot = VI.DIAMOND, crystals = I.diamondCrystals, powder = I.diamondPowder))

    val naturalUranium = add(MG(ore = BI.uraniumOre, deepOre = BI.deepslateUraniumOre, block = BI.uraniumBlock, raw = I.rawUranium, ingot = I.uraniumIngot, nugget = I.uraniumNugget, crystals = I.uraniumCrystals, powder = I.uraniumPowder))
    val uranium233 = add(MG(block = BI.u233Block, ingot = I.u233Ingot, nugget = I.u233Nugget))
    val uranium235 = add(MG(block = BI.u235Block, ingot = I.u235Ingot, nugget = I.u235Nugget))
    val uranium238 = add(MG(block = BI.u238Block, ingot = I.u238Ingot, nugget = I.u238Nugget))
    val thorium = add(MG(ore = BI.thoriumOre, deepOre = BI.deepslateThoriumOre, block = BI.thoriumBlock, raw = I.rawThorium, ingot = I.th232Ingot, nugget = I.th232Nugget, crystals = I.thoriumCrystals, powder = I.thoriumPowder))
    val naturalPlutonium = add(MG(block = BI.plutoniumBlock, raw = I.rawPlutonium, ingot = I.plutoniumIngot, nugget = I.plutoniumNugget, crystals = I.plutoniumCrystals, powder = I.plutoniumPowder))
    val plutonium238 = add(MG(block = BI.pu238Block, ingot = I.pu238Ingot, nugget = I.pu238Nugget))
    val plutonium239 = add(MG(block = BI.pu239Block, ingot = I.pu239Ingot, nugget = I.pu239Nugget))
    val plutonium240 = add(MG(block = BI.pu240Block, ingot = I.pu240Ingot, nugget = I.pu240Nugget))
    val neptunium = add(MG(block = BI.neptuniumBlock, ingot = I.neptuniumIngot, nugget = I.neptuniumNugget, powder = I.neptuniumPowder))
    val polonium = add(MG(ingot = I.poloniumIngot, nugget = I.poloniumNugget, powder = I.poloniumPowder))
    val titanium = add(MG(ore = BI.titaniumOre, deepOre = BI.deepslateTitaniumOre, block = BI.titaniumBlock, raw = I.rawTitanium, ingot = I.titaniumIngot, crystals = I.titaniumCrystals, powder = I.titaniumPowder, plate = I.titaniumPlate))
    val redCopper = add(MG(block = BI.redCopperBlock, ingot = I.redCopperIngot, powder = I.redCopperPowder, wire = I.redCopperWire))
    val advancedAlloy = add(MG(block = BI.advancedAlloyBlock, ingot = I.advancedAlloyIngot, powder = I.advancedAlloyPowder, plate = I.advancedAlloyPlate, wire = I.superConductor))
    val tungsten = add(MG(ore = BI.tungstenOre, deepOre = BI.deepslateTungstenOre, block = BI.tungstenBlock, raw = I.rawTungsten, ingot = I.tungstenIngot, crystals = I.tungstenCrystals, powder = I.tungstenPowder, wire = I.tungstenWire))
    val magnetizedTungsten = add(MG(block = BI.magnetizedTungstenBlock, ingot = I.magnetizedTungstenIngot, powder = I.magnetizedTungstenPowder, wire = I.highTemperatureSuperConductor))
    val aluminium = add(MG(ore = BI.aluminiumOre, deepOre = BI.deepslateAluminiumOre, block = BI.aluminiumBlock, raw = I.rawAluminium, ingot = I.aluminiumIngot, crystals = I.aluminiumCrystals, powder = I.aluminiumPowder, plate = I.aluminiumPlate, wire = I.aluminiumWire))
    val steel = add(MG(block = BI.steelBlock, ingot = I.steelIngot, powder = I.steelPowder, plate = I.steelPlate))
    val lead = add(MG(ore = BI.leadOre, deepOre = BI.deepslateLeadOre, block = BI.leadBlock, raw = I.rawLead, ingot = I.leadIngot, nugget = I.leadNugget, crystals = I.leadCrystals, powder = I.leadPowder, plate = I.leadPlate))
    val beryllium = add(MG(ore = BI.berylliumOre, deepOre = BI.deepslateBerylliumOre, block = BI.berylliumBlock, raw = I.rawBeryllium, ingot = I.berylliumIngot, nugget = I.berylliumNugget, crystals = I.berylliumCrystals, powder = I.berylliumPowder))
    val cobalt = add(MG(ore = BI.cobaltOre, deepOre = BI.deepslateCobaltOre, block = BI.cobaltBlock, raw = I.rawCobalt, ingot = I.cobaltIngot, powder = I.cobaltPowder))
    val highSpeedSteel = add(MG(ingot = I.highSpeedSteelIngot, powder = I.highSpeedSteelPowder))
    val polymer = add(MG(ingot = I.polymerIngot, powder = I.polymerPowder))
    val schraranium = add(MG(ingot = I.schraraniumIngot, crystals = I.schraraniumCrystals))
    val schrabidium = add(MG(ore = BI.schrabidiumOre, deepOre = BI.deepslateSchrabidiumOre, block = BI.schrabidiumBlock, raw = I.rawSchrabidium, ingot = I.schrabidiumIngot, nugget = I.schrabidiumNugget, crystals = I.schrabidiumCrystals, powder = I.schrabidiumPowder, plate = I.schrabidiumPlate, wire = I.schrabidiumWire))
    val solinium = add(MG(block = BI.soliniumBlock, ingot = I.soliniumIngot, nugget = I.soliniumNugget))
    val combineSteel = add(MG(block = BI.combineSteelBlock, ingot = I.combineSteelIngot, powder = I.combineSteelPowder, plate = I.combineSteelPlate))
    val uraniumFuel = add(MG(block = BI.uraniumFuelBlock, ingot = I.uraniumFuelIngot, nugget = I.uraniumFuelNugget))
    val thoriumFuel = add(MG(block = BI.thoriumFuelBlock, ingot = I.thoriumFuelIngot, nugget = I.thoriumFuelNugget))
    val plutoniumFuel = add(MG(block = BI.plutoniumFuelBlock, ingot = I.plutoniumFuelIngot, nugget = I.plutoniumFuelNugget))
    val moxFuel = add(MG(block = BI.moxFuelBlock, ingot = I.moxFuelIngot, nugget = I.moxFuelNugget))
    val schrabidiumFuel = add(MG(block = BI.schrabidiumFuelBlock, ingot = I.schrabidiumFuelIngot, nugget = I.schrabidiumFuelNugget))
    val hesFuel = add(MG(ingot = I.highEnrichedSchrabidiumFuelIngot, nugget = I.highEnrichedSchrabidiumFuelNugget))
    val lesFuel = add(MG(ingot = I.lowEnrichedSchrabidiumFuelIngot, nugget = I.lowEnrichedSchrabidiumFuelNugget))
    val australium = add(MG(ore = BI.australianOre, deepOre = BI.deepslateAustralianOre, block = BI.australiumBlock, raw = I.rawAustralium, ingot = I.australiumIngot, nugget = I.australiumNugget, powder = I.australiumPowder))
    val weidanium = add(MG(ore = BI.weidite, block = BI.weidaniumBlock, ingot = I.weidaniumIngot, nugget = I.weidaniumNugget, powder = I.weidaniumPowder))
    val reiium = add(MG(ore = BI.reiite, block = BI.reiiumBlock, ingot = I.reiiumIngot, nugget = I.reiiumNugget, powder = I.reiiumPowder))
    val unobtainium = add(MG(ore = BI.brightblendeOre, block = BI.unobtainiumBlock, ingot = I.unobtainiumIngot, nugget = I.unobtainiumNugget, powder = I.unobtainiumPowder))
    val daffergon = add(MG(ore = BI.dellite, block = BI.daffergonBlock, ingot = I.daffergonIngot, nugget = I.daffergonNugget, powder = I.daffergonPowder))
    val verticium = add(MG(ore = BI.dollarGreenMineral, block = BI.verticiumBlock, ingot = I.verticiumIngot, nugget = I.verticiumNugget, powder = I.verticiumPowder))
    val lanthanum = add(MG(ingot = I.lanthanumIngot, powder = I.lanthanumPowder))
    val actinium = add(MG(ingot = I.actiniumIngot, powder = I.actiniumPowder))
    val desh = add(MG(block = BI.deshReinforcedBlock, ingot = I.deshIngot, nugget = I.deshNugget, powder = I.deshPowder))
    val starmetal = add(MG(block = BI.starmetalBlock, raw = I.rawStarmetal, ingot = I.starmetalIngot, crystals = I.starmetalCrystals))
    val saturnite = add(MG(ingot = I.saturniteIngot, plate = I.saturnitePlate))
    val euphemium = add(MG(block = BI.euphemiumBlock, ingot = I.euphemiumIngot, nugget = I.euphemiumNugget, powder = I.euphemiumPowder))
    val dineutronium = add(MG(ingot = I.dineutroniumIngot, nugget = I.dineutroniumNugget, powder = I.dineutroniumPowder))
    val electronium = add(MG(ingot = I.electroniumIngot))
    val lignite = add(MG(ore = BI.ligniteOre, ingot = I.lignite, powder = I.lignitePowder))
    val sulfur = add(MG(ore = BI.sulfurOre, deepOre = BI.deepslateSulfurOre, block = BI.sulfurBlock, ingot = I.sulfur, crystals = I.sulfurCrystals))
    val niter = add(MG(ore = BI.niterOre, deepOre = BI.deepslateNiterOre, block = BI.niterBlock, ingot = I.niter, crystals = I.niterCrystals))
    val fluorite = add(MG(ore = BI.fluoriteOre, deepOre = BI.deepslateFluoriteOre, block = BI.fluoriteBlock, ingot = I.fluorite, crystals = I.fluoriteCrystals))
    val asbestos = add(MG(ore = BI.asbestosOre, deepOre = BI.deepslateAsbestosOre, block = BI.asbestosBlock, raw = I.rawAsbestos, ingot = I.asbestosSheet, powder = I.asbestosPowder))
    val lithium = add(MG(block = BI.lithiumBlock, raw = I.rawLithium, ingot = I.lithiumCube, crystals = I.lithiumCrystals, powder = I.lithiumPowder))

    private fun add(group: MaterialGroup): MaterialGroup {
        allMaterials += group
        return group
    }
}

interface MaterialGroup {
    fun ore(): Item?
    fun deepOre(): Item?
    fun block(): Item?
    fun raw(): Item?
    fun ingot(): Item?
    fun nugget(): Item?
    fun crystals(): Item?
    fun powder(): Item?
    fun plate(): Item?
    fun wire(): Item?
}

data class VanillaGroup(
    val ore: Item,
    val deepOre: Item,
    val block: Item,
    val raw: Item? = null,
    val ingot: Item,
    val nugget: Item? = null,
    val crystals: Supplier<Item>? = null,
    val powder: Supplier<Item>? = null,
    val plate: Supplier<Item>? = null,
    val wire: Supplier<Item>? = null,
) : MaterialGroup {
    override fun ore() = ore
    override fun deepOre() = deepOre
    override fun block() = block
    override fun raw() = raw
    override fun ingot() = ingot
    override fun nugget() = nugget
    override fun crystals() = crystals?.get()
    override fun powder() = powder?.get()
    override fun plate() = plate?.get()
    override fun wire() = wire?.get()

}

data class ModGroup(
    val ore: Supplier<BlockItem>? = null,
    val deepOre: Supplier<BlockItem>? = null,
    val block: Supplier<BlockItem>? = null,
    val raw: Supplier<Item>? = null,
    val ingot: Supplier<Item>? = null,
    val nugget: Supplier<Item>? = null,
    val crystals: Supplier<Item>? = null,
    val powder: Supplier<Item>? = null,
    val plate: Supplier<Item>? = null,
    val wire: Supplier<Item>? = null,
) : MaterialGroup {
    override fun ore() = ore?.get()
    override fun deepOre() = deepOre?.get()
    override fun block() = block?.get()
    override fun raw() = raw?.get()
    override fun ingot() = ingot?.get()
    override fun nugget() = nugget?.get()
    override fun crystals() = crystals?.get()
    override fun powder() = powder?.get()
    override fun plate() = plate?.get()
    override fun wire() = wire?.get()
}

@OptIn(ExperimentalContracts::class)
fun MaterialGroup.isVanilla(): Boolean {
    contract {
        returns(true) implies (this@isVanilla is VanillaGroup)
    }
    return this is VanillaGroup
}
fun MaterialGroup.asVanilla() = this as VanillaGroup
fun MaterialGroup.asVanillaOrNull() = this as? VanillaGroup
