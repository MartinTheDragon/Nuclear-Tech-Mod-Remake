@file:Suppress("unused")

package at.martinthedragon.nucleartech

import at.martinthedragon.nucleartech.item.NTechBlockItems
import at.martinthedragon.nucleartech.item.NTechItems
import net.minecraft.tags.ItemTags
import net.minecraft.tags.TagKey
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Item
import net.minecraftforge.common.Tags
import java.util.function.Supplier
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract
import net.minecraft.world.item.Items as VI

private typealias BI = NTechBlockItems
private typealias I = NTechItems

private typealias VG = VanillaGroup
private typealias MG = ModGroup

// NOTE: These don't accurately describe what the corresponding item is! Materials are used to describe relations between items of the same type.
// That's why, for example, fluorite is added as an ingot and not a dust. To accurately know *what* an item is, use Item#is checks.

object Materials {
    private val allMaterials = mutableListOf<MaterialGroup>()

    fun getAllMaterials(): List<MaterialGroup> = allMaterials.toList()
    fun getVanillaMaterials() = getAllMaterials().filterIsInstance<VanillaGroup>()
    fun getModMaterials() = getAllMaterials().filterIsInstance<ModGroup>()

    val coal = add(VG(ore = VI.COAL_ORE, deepOre = VI.DEEPSLATE_COAL_ORE, block = VI.COPPER_BLOCK, ingot = VI.COAL, crystals = I.coalCrystals, powder = I.coalPowder))
    val iron = add(VG(ore = VI.IRON_ORE, deepOre = VI.DEEPSLATE_IRON_ORE, block = VI.IRON_BLOCK, raw = VI.RAW_IRON, ingot = VI.IRON_INGOT, nugget = VI.IRON_NUGGET, crystals = I.ironCrystals, powder = I.ironPowder, plate = I.ironPlate))
    val copper = add(VG(ore = VI.COPPER_ORE, deepOre = VI.DEEPSLATE_COPPER_ORE, block = VI.COPPER_BLOCK, raw = VI.RAW_COPPER, ingot = VI.COPPER_INGOT, crystals = I.copperCrystals, powder = I.copperPowder, plate = I.copperPlate, wire = I.copperWire))
    val gold = add(VG(ore = VI.GOLD_ORE, deepOre = VI.DEEPSLATE_GOLD_ORE, block = VI.GOLD_BLOCK, raw = VI.RAW_GOLD, ingot = VI.GOLD_INGOT, nugget = VI.GOLD_NUGGET, crystals = I.goldCrystals, powder = I.goldPowder, plate = I.goldPlate, wire = I.goldWire))
    val redstone = add(VG(ore = VI.REDSTONE_ORE, deepOre = VI.DEEPSLATE_REDSTONE_ORE, block = VI.REDSTONE_BLOCK, ingot = VI.REDSTONE, crystals = I.redstoneCrystals, ingotIsPowder = true))
    val emerald = add(VG(ore = VI.EMERALD_ORE, deepOre = VI.DEEPSLATE_EMERALD_ORE, block = VI.EMERALD_BLOCK, ingot = VI.EMERALD, powder = I.emeraldPowder))
    val lazuli = add(VG(ore = VI.LAPIS_ORE, deepOre = VI.DEEPSLATE_LAPIS_ORE, block = VI.LAPIS_BLOCK, ingot = VI.LAPIS_LAZULI, crystals = I.lapisCrystals, powder = I.lapisLazuliPowder))
    val diamond = add(VG(ore = VI.DIAMOND_ORE, deepOre = VI.DEEPSLATE_DIAMOND_ORE, block = VI.DIAMOND_BLOCK, ingot = VI.DIAMOND, crystals = I.diamondCrystals, powder = I.diamondPowder))

    val actinium227 = add(MG(ingot = I.actiniumIngot, nugget = I.actinium227Nugget, powder = I.actiniumPowder))
    val advancedAlloy = add(MG(block = BI.advancedAlloyBlock, ingot = I.advancedAlloyIngot, powder = I.advancedAlloyPowder, plate = I.advancedAlloyPlate, wire = I.superConductor))
    val aluminium = add(MG(ore = BI.aluminiumOre, deepOre = BI.deepslateAluminiumOre, block = BI.aluminiumBlock, raw = I.rawAluminium, ingot = I.aluminiumIngot, crystals = I.aluminiumCrystals, powder = I.aluminiumPowder, plate = I.aluminiumPlate, wire = I.aluminiumWire))
    val americium241 = add(MG(ingot = I.americium241Ingot, nugget = I.americium241Nugget))
    val americium242 = add(MG(ingot = I.americium242Ingot, nugget = I.americium242Nugget))
    val americiumFuel = add(MG(ingot = I.americiumFuelIngot, nugget = I.americiumFuelNugget))
    val arsenic = add(MG(ingot = I.arsenicIngot, nugget = I.arsenicNugget))
    val asbestos = add(MG(ore = BI.asbestosOre, deepOre = BI.deepslateAsbestosOre, block = BI.asbestosBlock, ingot = I.asbestosSheet, powder = I.asbestosPowder))
    val australium = add(MG(ore = BI.australianOre, deepOre = BI.deepslateAustralianOre, block = BI.australiumBlock, raw = I.rawAustralium, ingot = I.australiumIngot, nugget = I.australiumNugget, powder = I.australiumPowder))
    val bakelite = add(MG(ingot = I.bakeliteIngot))
    val beryllium = add(MG(ore = BI.berylliumOre, deepOre = BI.deepslateBerylliumOre, block = BI.berylliumBlock, raw = I.rawBeryllium, ingot = I.berylliumIngot, nugget = I.berylliumNugget, crystals = I.berylliumCrystals, powder = I.berylliumPowder))
    val bismuth = add(MG(ingot = I.bismuthIngot, nugget = I.bismuthNugget))
    val boron = add(MG(ingot = I.boronIngot))
    val cobalt = add(MG(ore = BI.cobaltOre, deepOre = BI.deepslateCobaltOre, block = BI.cobaltBlock, raw = I.rawCobalt, ingot = I.cobaltIngot, nugget = I.cobaltNugget, crystals = I.cobaltCrystals, powder = I.cobaltPowder))
    val cobalt60 = add(MG(ingot = I.cobalt60Ingot, nugget = I.cobalt60Nugget))
    val combineSteel = add(MG(block = BI.combineSteelBlock, ingot = I.combineSteelIngot, powder = I.combineSteelPowder, plate = I.combineSteelPlate))
    val daffergon = add(MG(ore = BI.dellite, block = BI.daffergonBlock, ingot = I.daffergonIngot, nugget = I.daffergonNugget, powder = I.daffergonPowder))
    val desh = add(MG(block = BI.deshReinforcedBlock, ingot = I.deshIngot, nugget = I.deshNugget, powder = I.deshPowder))
    val dineutronium = add(MG(ingot = I.dineutroniumIngot, nugget = I.dineutroniumNugget, powder = I.dineutroniumPowder))
    val electronium = add(MG(ingot = I.electroniumIngot))
    val euphemium = add(MG(block = BI.euphemiumBlock, ingot = I.euphemiumIngot, nugget = I.euphemiumNugget, powder = I.euphemiumPowder))
    val ferricSchrabidate = add(MG(ingot = I.ferricSchrabidateIngot))
    val fiberglass = add(MG(block = BI.fiberglassRoll, ingot = I.fiberglassSheet))
    val fluorite = add(MG(ore = BI.fluoriteOre, deepOre = BI.deepslateFluoriteOre, block = BI.fluoriteBlock, ingot = I.fluorite, crystals = I.fluoriteCrystals, ingotIsPowder = true))
    val ghiorsium336 = add(MG(ingot = I.ghiorsium336Ingot, nugget = I.ghiorsium336Nugget))
    val gold198 = add(MG(ingot = I.gold198Ingot, nugget = I.gold198Nugget))
    val graphite = add(MG(ingot = I.graphiteIngot))
    val hesFuel = add(MG(ingot = I.highEnrichedSchrabidiumFuelIngot, nugget = I.highEnrichedSchrabidiumFuelNugget))
    val highSpeedSteel = add(MG(ingot = I.highSpeedSteelIngot, powder = I.highSpeedSteelPowder))
    val insulator = add(MG(block = BI.insulatorRoll, ingot = I.insulator))
    val lanthanum = add(MG(ingot = I.lanthanumIngot, powder = I.lanthanumPowder))
    val lead = add(MG(ore = BI.leadOre, deepOre = BI.deepslateLeadOre, block = BI.leadBlock, raw = I.rawLead, ingot = I.leadIngot, nugget = I.leadNugget, crystals = I.leadCrystals, powder = I.leadPowder, plate = I.leadPlate))
    val lead209 = add(MG(ingot = I.lead209Ingot, nugget = I.lead209Nugget))
    val lesFuel = add(MG(ingot = I.lowEnrichedSchrabidiumFuelIngot, nugget = I.lowEnrichedSchrabidiumFuelNugget))
    val lignite = add(MG(ore = BI.ligniteOre, ingot = I.lignite, powder = I.lignitePowder))
    val lithium = add(MG(block = BI.lithiumBlock, raw = I.rawLithium, ingot = I.lithiumCube, crystals = I.lithiumCrystals, powder = I.lithiumPowder))
    val magnetizedTungsten = add(MG(block = BI.magnetizedTungstenBlock, ingot = I.magnetizedTungstenIngot, powder = I.magnetizedTungstenPowder, wire = I.highTemperatureSuperConductor))
    val moxFuel = add(MG(block = BI.moxFuelBlock, ingot = I.moxFuelIngot, nugget = I.moxFuelNugget))
    val naturalPlutonium = add(MG(ore = BI.netherPlutoniumOre, block = BI.plutoniumBlock, raw = I.rawPlutonium, ingot = I.plutoniumIngot, nugget = I.plutoniumNugget, crystals = I.plutoniumCrystals, powder = I.plutoniumPowder))
    val naturalUranium = add(MG(ore = BI.uraniumOre, deepOre = BI.deepslateUraniumOre, block = BI.uraniumBlock, raw = I.rawUranium, ingot = I.uraniumIngot, nugget = I.uraniumNugget, crystals = I.uraniumCrystals, powder = I.uraniumPowder))
    val neptunium = add(MG(block = BI.neptuniumBlock, ingot = I.neptuniumIngot, nugget = I.neptuniumNugget, powder = I.neptuniumPowder))
    val neptuniumFuel = add(MG(ingot = I.neptuniumFuelIngot, nugget = I.neptuniumFuelNugget))
    val niobium = add(MG(ingot = I.niobiumIngot, powder = I.niobiumPowder))
    val niter = add(MG(ore = BI.niterOre, deepOre = BI.deepslateNiterOre, block = BI.niterBlock, ingot = I.niter, crystals = I.niterCrystals, ingotIsPowder = true))
    val nuclearWaste = add(MG(block = BI.nuclearWasteBlock, ingot = I.nuclearWaste))
    val osmiridium = add(MG(ingot = I.osmiridiumIngot, nugget = I.osmiridiumNugget, crystals = I.osmiridiumCrystals))
    val plutonium238 = add(MG(block = BI.pu238Block, ingot = I.pu238Ingot, nugget = I.pu238Nugget))
    val plutonium239 = add(MG(block = BI.pu239Block, ingot = I.pu239Ingot, nugget = I.pu239Nugget))
    val plutonium240 = add(MG(block = BI.pu240Block, ingot = I.pu240Ingot, nugget = I.pu240Nugget))
    val plutonium241 = add(MG(ingot = I.pu241Ingot, nugget = I.pu241Nugget))
    val plutoniumFuel = add(MG(block = BI.plutoniumFuelBlock, ingot = I.plutoniumFuelIngot, nugget = I.plutoniumFuelNugget))
    val polonium = add(MG(ingot = I.polonium210Ingot, nugget = I.poloniumNugget, powder = I.poloniumPowder))
    val polymer = add(MG(ingot = I.polymerIngot, powder = I.polymerPowder))
    val radium226 = add(MG(ingot = I.radium226Ingot, nugget = I.radium226Nugget))
    val reactorGradeAmericium = add(MG(ingot = I.reactorGradeAmericiumIngot, nugget = I.reactorGradeAmericiumNugget))
    val reactorGradePlutonium = add(MG(ingot = I.reactorGradePlutoniumIngot, nugget = I.reactorGradePlutoniumNugget))
    val redCopper = add(MG(block = BI.redCopperBlock, ingot = I.redCopperIngot, powder = I.redCopperPowder, wire = I.redCopperWire))
    val redPhosphorus = add(MG(ore = BI.netherPhosphorusOre, block = BI.redPhosphorusBlock, ingot = I.redPhosphorus, crystals = I.redPhosphorusCrystals, ingotIsPowder = true))
    val reiium = add(MG(ore = BI.reiite, block = BI.reiiumBlock, ingot = I.reiiumIngot, nugget = I.reiiumNugget, powder = I.reiiumPowder))
    val rubber = add(MG(ingot = I.rubberIngot))
    val saturnite = add(MG(ingot = I.saturniteIngot, plate = I.saturnitePlate))
    val schrabidium = add(MG(ore = BI.schrabidiumOre, deepOre = BI.deepslateSchrabidiumOre, block = BI.schrabidiumBlock, raw = I.rawSchrabidium, ingot = I.schrabidiumIngot, nugget = I.schrabidiumNugget, crystals = I.schrabidiumCrystals, powder = I.schrabidiumPowder, plate = I.schrabidiumPlate, wire = I.schrabidiumWire))
    val schrabidiumFuel = add(MG(block = BI.schrabidiumFuelBlock, ingot = I.schrabidiumFuelIngot, nugget = I.schrabidiumFuelNugget))
    val schraranium = add(MG(ingot = I.schraraniumIngot, crystals = I.schraraniumCrystals))
    val solinium = add(MG(block = BI.soliniumBlock, ingot = I.soliniumIngot, nugget = I.soliniumNugget))
    val starmetal = add(MG(ore = BI.starmetalOre, block = BI.starmetalBlock, raw = I.rawStarmetal, ingot = I.starmetalIngot, crystals = I.starmetalCrystals))
    val steel = add(MG(block = BI.steelBlock, ingot = I.steelIngot, powder = I.steelPowder, plate = I.steelPlate))
    val strontium90 = add(MG(ingot = I.strontium90Ingot, nugget = I.strontium90Nugget))
    val sulfur = add(MG(ore = BI.sulfurOre, deepOre = BI.deepslateSulfurOre, block = BI.sulfurBlock, ingot = I.sulfur, crystals = I.sulfurCrystals, ingotIsPowder = true))
    val tantalium = add(MG(ingot = I.tantaliumIngot, nugget = I.tantaliumNugget))
    val technetium99 = add(MG(ingot = I.technetium99Ingot, nugget = I.technetium99Nugget))
    val technetiumSteel = add(MG(ingot = I.technetiumSteelIngot))
    val thorium = add(MG(ore = BI.thoriumOre, deepOre = BI.deepslateThoriumOre, block = BI.thoriumBlock, raw = I.rawThorium, ingot = I.th232Ingot, nugget = I.th232Nugget, crystals = I.thoriumCrystals, powder = I.thoriumPowder))
    val thoriumFuel = add(MG(block = BI.thoriumFuelBlock, ingot = I.thoriumFuelIngot, nugget = I.thoriumFuelNugget))
    val titanium = add(MG(ore = BI.titaniumOre, deepOre = BI.deepslateTitaniumOre, block = BI.titaniumBlock, raw = I.rawTitanium, ingot = I.titaniumIngot, crystals = I.titaniumCrystals, powder = I.titaniumPowder, plate = I.titaniumPlate))
    val trinitite = add(MG(ore = BI.trinititeOre, block = BI.trinititeBlock, ingot = I.trinitite))
    val tungsten = add(MG(ore = BI.tungstenOre, deepOre = BI.deepslateTungstenOre, block = BI.tungstenBlock, raw = I.rawTungsten, ingot = I.tungstenIngot, crystals = I.tungstenCrystals, powder = I.tungstenPowder, wire = I.tungstenWire))
    val unobtainium = add(MG(ore = BI.brightblendeOre, block = BI.unobtainiumBlock, ingot = I.unobtainiumIngot, nugget = I.unobtainiumNugget, powder = I.unobtainiumPowder))
    val uranium233 = add(MG(block = BI.u233Block, ingot = I.u233Ingot, nugget = I.u233Nugget))
    val uranium235 = add(MG(block = BI.u235Block, ingot = I.u235Ingot, nugget = I.u235Nugget))
    val uranium238 = add(MG(block = BI.u238Block, ingot = I.u238Ingot, nugget = I.u238Nugget))
    val uraniumFuel = add(MG(block = BI.uraniumFuelBlock, ingot = I.uraniumFuelIngot, nugget = I.uraniumFuelNugget))
    val verticium = add(MG(ore = BI.dollarGreenMineral, block = BI.verticiumBlock, ingot = I.verticiumIngot, nugget = I.verticiumNugget, powder = I.verticiumPowder))
    val weidanium = add(MG(ore = BI.weidite, block = BI.weidaniumBlock, ingot = I.weidaniumIngot, nugget = I.weidaniumNugget, powder = I.weidaniumPowder))
    val whitePhosphorus = add(MG(block = BI.whitePhosphorusBlock, ingot = I.whitePhosphorusIngot))
    val yellowcake = add(MG(block = BI.yellowcakeBlock, ingot = I.yellowcake))

    private fun add(group: MaterialGroup): MaterialGroup {
        allMaterials += group
        return group
    }
}

private typealias NT = NTechTags.Items
private typealias FT = Tags.Items
private typealias MT = ItemTags

private typealias Mat = Materials
private typealias TG = TagGroup

object TagGroups {
    private val vanillaTagGroups = mutableListOf<TagGroup>()
    private val modTagGroups = mutableListOf<TagGroup>()

    fun getAllTagGroups(): List<TagGroup> = getVanillaTagGroups() + getModTagGroups()
    fun getVanillaTagGroups(): List<TagGroup> = vanillaTagGroups.toList()
    fun getModTagGroups(): List<TagGroup> = modTagGroups.toList()

    val coal = vanilla(TG(Mat.coal, ore = FT.ORES_COAL, block = FT.STORAGE_BLOCKS_COAL, crystals = NT.CRYSTALS_COAL, powder = NT.DUSTS_COAL))
    val iron = vanilla(TG(Mat.iron, ore = FT.ORES_IRON, block = FT.STORAGE_BLOCKS_IRON, raw = FT.RAW_MATERIALS_IRON, ingot = FT.INGOTS_IRON, nugget = FT.NUGGETS_IRON, crystals = NT.CRYSTALS_IRON, powder = NT.DUSTS_IRON, plate = NT.PLATES_IRON))
    val copper = vanilla(TG(Mat.copper, ore = FT.ORES_COPPER, block = FT.STORAGE_BLOCKS_COPPER, raw = FT.RAW_MATERIALS_COPPER, ingot = FT.INGOTS_COPPER, crystals = NT.CRYSTALS_COPPER, powder = NT.DUSTS_COPPER, plate = NT.PLATES_COPPER, wire = NT.WIRES_COPPER))
    val gold = vanilla(TG(Mat.gold, ore = FT.ORES_GOLD, block = FT.STORAGE_BLOCKS_GOLD, raw = FT.RAW_MATERIALS_GOLD, ingot = FT.INGOTS_GOLD, nugget = FT.NUGGETS_GOLD, crystals = NT.CRYSTALS_GOLD, powder = NT.DUSTS_GOLD, plate = NT.PLATES_GOLD, wire = NT.WIRES_GOLD))
    val redstone = vanilla(TG(Mat.redstone, ore = FT.ORES_REDSTONE, block = FT.STORAGE_BLOCKS_REDSTONE, crystals = NT.CRYSTALS_REDSTONE, ingot = FT.DUSTS_REDSTONE))
    val emerald = vanilla(TG(Mat.emerald, ore = FT.ORES_EMERALD, block = FT.STORAGE_BLOCKS_EMERALD, ingot = FT.GEMS_EMERALD, powder = NT.DUSTS_EMERALD))
    val lazuli = vanilla(TG(Mat.lazuli, ore = FT.ORES_LAPIS, block = FT.STORAGE_BLOCKS_LAPIS, ingot = FT.GEMS_LAPIS, powder = NT.DUSTS_LAPIS))
    val diamond = vanilla(TG(Mat.diamond, ore = FT.ORES_DIAMOND, block = FT.STORAGE_BLOCKS_DIAMOND, ingot = FT.GEMS_DIAMOND, crystals = NT.CRYSTALS_DIAMOND, powder = NT.DUSTS_DIAMOND))

    val actinium227 = mod(TG(Mat.actinium227, ingot = NT.INGOTS_ACTINIUM, powder = NT.DUSTS_ACTINIUM))
    val advancedAlloy = mod(TG(Mat.advancedAlloy))
    val aluminium = mod(TG(Mat.aluminium, ore = NT.ORES_ALUMINIUM, block = NT.STORAGE_BLOCKS_ALUMINIUM, raw = NT.RAW_MATERIALS_ALUMINIUM, ingot = NT.INGOTS_ALUMINIUM, crystals = NT.CRYSTALS_ALUMINIUM, plate = NT.PLATES_ALUMINIUM, powder = NT.DUSTS_ALUMINIUM, wire = NT.WIRES_ALUMINIUM))
    val asbestos = mod(TG(Mat.asbestos, ore = NT.ORES_ASBESTOS, block = NT.STORAGE_BLOCKS_ASBESTOS, ingot = NT.INGOTS_ASBESTOS, powder = NT.DUSTS_ASBESTOS))
    val australium = mod(TG(Mat.australium, ore = NT.ORES_AUSTRALIUM, block = NT.STORAGE_BLOCKS_AUSTRALIUM, raw = NT.RAW_MATERIALS_AUSTRALIUM, ingot = NT.INGOTS_AUSTRALIUM, nugget = NT.NUGGETS_AUSTRALIUM, powder = NT.DUSTS_AUSTRALIUM))
    val beryllium = mod(TG(Mat.beryllium, ore = NT.ORES_BERYLLIUM, block = NT.STORAGE_BLOCKS_BERYLLIUM, raw = NT.RAW_MATERIALS_BERYLLIUM, ingot = NT.INGOTS_BERYLLIUM, nugget = NT.NUGGETS_BERYLLIUM, crystals = NT.CRYSTALS_BERYLLIUM, powder = NT.DUSTS_BERYLLIUM))
    val bismuth = mod(TG(Mat.bismuth, ingot = NT.INGOTS_BISMUTH, nugget = NT.NUGGETS_BISMUTH))
    val cobalt = mod(TG(Mat.cobalt, ore = NT.ORES_COBALT, block = NT.STORAGE_BLOCKS_COBALT, raw = NT.RAW_MATERIALS_COBALT, ingot = NT.INGOTS_COBALT, powder = NT.DUSTS_COBALT))
    val combineSteel = mod(TG(Mat.combineSteel, block = NT.STORAGE_BLOCKS_COMBINE_STEEL, ingot = NT.INGOTS_COMBINE_STEEL, powder = NT.DUSTS_COMBINE_STEEL, plate = NT.PLATES_COMBINE_STEEL))
    val daffergon = mod(TG(Mat.daffergon, ore = NT.ORES_DAFFERGON, block = NT.STORAGE_BLOCKS_DAFFERGON, ingot = NT.INGOTS_DAFFERGON, nugget = NT.NUGGETS_DAFFERGON, powder = NT.DUSTS_DAFFERGON))
    val desh = mod(TG(Mat.desh, block = NT.STORAGE_BLOCKS_DESH, ingot = NT.INGOTS_DESH, nugget = NT.NUGGETS_DESH, powder = NT.DUSTS_DESH))
    val dineutronium = mod(TG(Mat.dineutronium, ingot = NT.INGOTS_DINEUTRONIUM, nugget = NT.NUGGETS_DINEUTRONIUM, powder = NT.DUSTS_DINEUTRONIUM))
    val electronium = mod(TG(Mat.electronium, ingot = NT.INGOTS_ELECTRONIUM))
    val euphemium = mod(TG(Mat.euphemium, block = NT.STORAGE_BLOCKS_EUPHEMIUM, ingot = NT.INGOTS_EUPHEMIUM, nugget = NT.NUGGETS_EUPHEMIUM, powder = NT.DUSTS_EUPHEMIUM))
    val ferricSchrabidate = mod(TG(Mat.ferricSchrabidate, ingot = NT.INGOTS_SCHRABIDATE))
    val fiberglass = mod(TG(Mat.fiberglass, block = NT.STORAGE_BLOCKS_FIBERGLASS, ingot = NT.INGOTS_FIBERGLASS))
    val fluorite = mod(TG(Mat.fluorite, ore = NT.ORES_FLUORITE, block = NT.STORAGE_BLOCKS_FLUORITE, ingot = NT.DUSTS_FLUORITE, crystals = NT.CRYSTALS_FLUORITE))
    val highSpeedSteel = mod(TG(Mat.highSpeedSteel, ingot = NT.INGOTS_HIGH_SPEED_STEEL))
    val insulator = mod(TG(Mat.insulator, block = NT.STORAGE_BLOCKS_INSULATOR, ingot = NT.PLATES_INSULATOR))
    val lanthanum = mod(TG(Mat.lanthanum, ingot = NT.INGOTS_LANTHANUM, powder = NT.DUSTS_LANTHANUM))
    val lead = mod(TG(Mat.lead, ore = NT.ORES_LEAD, block = NT.STORAGE_BLOCKS_LEAD, raw = NT.RAW_MATERIALS_LEAD, ingot = NT.INGOTS_LEAD, nugget = NT.NUGGETS_LEAD, crystals = NT.CRYSTALS_LEAD, powder = NT.DUSTS_LEAD, plate = NT.PLATES_LEAD))
    val lignite = mod(TG(Mat.lignite, ore = NT.ORES_LIGNITE, powder = NT.DUSTS_LIGNITE))
    val lithium = mod(TG(Mat.lithium, block = NT.STORAGE_BLOCKS_LITHIUM, raw = NT.RAW_MATERIALS_LITHIUM, ingot = NT.INGOTS_LITHIUM, crystals = NT.CRYSTALS_LITHIUM, powder = NT.DUSTS_LITHIUM))
    val magnetizedTungsten = mod(TG(Mat.magnetizedTungsten, block = NT.STORAGE_BLOCKS_MAGNETIZED_TUNGSTEN, ingot = NT.INGOTS_MAGNETIZED_TUNGSTEN, powder = NT.DUSTS_MAGNETIZED_TUNGSTEN, wire = NT.WIRES_MAGNETIZED_TUNGSTEN))
    val moxFuel = mod(TG(Mat.moxFuel, block = NT.STORAGE_BLOCKS_MOX, ingot = NT.INGOTS_MOX, nugget = NT.NUGGETS_MOX))
    val naturalPlutonium = mod(TG(Mat.naturalPlutonium, ore = NT.ORES_PLUTONIUM, block = NT.STORAGE_BLOCKS_PLUTONIUM, raw = NT.RAW_MATERIALS_PLUTONIUM, ingot = NT.INGOTS_PLUTONIUM, nugget = NT.NUGGETS_PLUTONIUM, crystals = NT.CRYSTALS_PLUTONIUM, powder = NT.DUSTS_PLUTONIUM))
    val naturalUranium = mod(TG(Mat.naturalUranium, ore = NT.ORES_URANIUM, block = NT.STORAGE_BLOCKS_URANIUM, raw = NT.RAW_MATERIALS_URANIUM, ingot = NT.INGOTS_URANIUM, nugget = NT.NUGGETS_URANIUM, crystals = NT.CRYSTALS_URANIUM, powder = NT.DUSTS_URANIUM))
    val neptunium = mod(TG(Mat.neptunium, block = NT.STORAGE_BLOCKS_NEPTUNIUM, ingot = NT.INGOTS_NEPTUNIUM, nugget = NT.NUGGETS_NEPTUNIUM, powder = NT.DUSTS_NEPTUNIUM))
    val niter = mod(TG(Mat.niter, ore = NT.ORES_NITER, block = NT.STORAGE_BLOCKS_NITER, ingot = NT.DUSTS_NITER, crystals = NT.CRYSTALS_NITER))
    val nuclearWaste = mod(TG(Mat.nuclearWaste, block = NT.STORAGE_BLOCKS_NUCLEAR_WASTE))
    val plutoniumFuel = mod(TG(Mat.plutoniumFuel, block = NT.STORAGE_BLOCKS_PLUTONIUM_FUEL, ingot = NT.INGOTS_PLUTONIUM_FUEL, nugget = NT.NUGGETS_PLUTONIUM_FUEL))
    val polonium = mod(TG(Mat.polonium, ingot = NT.INGOTS_POLONIUM, nugget = NT.NUGGETS_POLONIUM, powder = NT.DUSTS_POLONIUM))
    val polymer = mod(TG(Mat.polymer, ingot = NT.INGOTS_TEFLON, powder = NT.DUSTS_TEFLON))
    val redCopper = mod(TG(Mat.redCopper, block = NT.STORAGE_BLOCKS_RED_COPPER, ingot = NT.INGOTS_RED_COPPER, powder = NT.DUSTS_RED_COPPER, wire = NT.WIRES_RED_COPPER))
    val redPhosphorus = mod(TG(Mat.redPhosphorus, ore = NT.ORES_PHOSPHORUS, crystals = NT.CRYSTALS_PHOSPHORUS))
    val reiium = mod(TG(Mat.reiium, ore = NT.ORES_REIIUM, block = NT.STORAGE_BLOCKS_REIIUM, ingot = NT.INGOTS_REIIUM, nugget = NT.NUGGETS_REIIUM, powder = NT.DUSTS_REIIUM))
    val saturnite = mod(TG(Mat.saturnite, ingot = NT.INGOTS_SATURNITE, plate = NT.PLATES_SATURNITE))
    val schrabidium = mod(TG(Mat.schrabidium, ore = NT.ORES_SCHRABIDIUM, block = NT.STORAGE_BLOCKS_SCHRABIDIUM, raw = NT.RAW_MATERIALS_SCHRABIDIUM, ingot = NT.INGOTS_SCHRABIDIUM, nugget = NT.NUGGETS_SCHRABIDIUM, crystals = NT.CRYSTALS_SCHRABIDIUM, powder = NT.DUSTS_SCHRABIDIUM, plate = NT.PLATES_SCHRABIDIUM, wire = NT.WIRES_SCHRABIDIUM))
    val schrabidiumFuel = mod(TG(Mat.schrabidiumFuel, block = NT.STORAGE_BLOCKS_SCHRABIDIUM_FUEL, ingot = NT.INGOTS_SCHRABIDIUM_FUEL, nugget = NT.NUGGETS_SCHRABIDIUM_FUEL))
    val schraranium = mod(TG(Mat.schraranium, ingot = NT.INGOTS_SCHRARANIUM, crystals = NT.CRYSTALS_SCHRARANIUM))
    val solinium = mod(TG(Mat.solinium, block = NT.STORAGE_BLOCKS_SOLINIUM, ingot = NT.INGOTS_SOLINIUM, nugget = NT.NUGGETS_SOLINIUM))
    val starmetal = mod(TG(Mat.starmetal, ore = NT.ORES_STARMETAL, block = NT.STORAGE_BLOCKS_STARMETAL, raw = NT.RAW_MATERIALS_STARMETAL, ingot = NT.INGOTS_STARMETAL, crystals = NT.CRYSTALS_STARMETAL))
    val steel = mod(TG(Mat.steel, block = NT.STORAGE_BLOCKS_STEEL, ingot = NT.INGOTS_STEEL, powder = NT.DUSTS_STEEL, plate = NT.PLATES_STEEL))
    val sulfur = mod(TG(Mat.sulfur, ore = NT.ORES_SULFUR, block = NT.STORAGE_BLOCKS_SULFUR, ingot = NT.DUSTS_SULFUR, crystals = NT.CRYSTALS_SULFUR))
    val thorium = mod(TG(Mat.thorium, ore = NT.ORES_THORIUM, block = NT.STORAGE_BLOCKS_THORIUM, raw = NT.RAW_MATERIALS_THORIUM, ingot = NT.INGOTS_THORIUM, nugget = NT.NUGGETS_THORIUM, crystals = NT.CRYSTALS_THORIUM, powder = NT.DUSTS_THORIUM))
    val thoriumFuel = mod(TG(Mat.thoriumFuel, block = NT.STORAGE_BLOCKS_THORIUM_FUEL, ingot = NT.INGOTS_THORIUM_FUEL, nugget = NT.NUGGETS_THORIUM_FUEL))
    val titanium = mod(TG(Mat.titanium, ore = NT.ORES_TITANIUM, block = NT.STORAGE_BLOCKS_TITANIUM, raw = NT.RAW_MATERIALS_TITANIUM, ingot = NT.INGOTS_TITANIUM, crystals = NT.CRYSTALS_TITANIUM, powder = NT.DUSTS_TITANIUM, plate = NT.PLATES_TITANIUM))
    val trinitite = mod(TG(Mat.trinitite, ore = NT.ORES_TRINITITE, block = NT.STORAGE_BLOCKS_TRINITITE))
    val tungsten = mod(TG(Mat.tungsten, ore = NT.ORES_TUNGSTEN, block = NT.STORAGE_BLOCKS_TUNGSTEN, raw = NT.RAW_MATERIALS_TUNGSTEN, ingot = NT.INGOTS_TUNGSTEN, crystals = NT.CRYSTALS_TUNGSTEN, powder = NT.DUSTS_TUNGSTEN, wire = NT.WIRES_TUNGSTEN))
    val unobtainium = mod(TG(Mat.unobtainium, ore = NT.ORES_UNOBTAINIUM, block = NT.STORAGE_BLOCKS_UNOBTAINIUM, ingot = NT.INGOTS_UNOBTAINIUM, nugget = NT.NUGGETS_UNOBTAINIUM, powder = NT.DUSTS_UNOBTAINIUM))
    val uraniumFuel = mod(TG(Mat.uraniumFuel, block = NT.STORAGE_BLOCKS_URANIUM_FUEL, ingot = NT.INGOTS_URANIUM_FUEL, nugget = NT.NUGGETS_URANIUM_FUEL))
    val verticium = mod(TG(Mat.verticium, ore = NT.ORES_VERTICIUM, block = NT.STORAGE_BLOCKS_VERTICIUM, ingot = NT.INGOTS_VERTICIUM, nugget = NT.NUGGETS_VERTICIUM, powder = NT.DUSTS_VERTICIUM))
    val weidanium = mod(TG(Mat.weidanium, ore = NT.ORES_WEIDANIUM, block = NT.STORAGE_BLOCKS_WEIDANIUM, ingot = NT.INGOTS_WEIDANIUM, nugget = NT.NUGGETS_WEIDANIUM, powder = NT.DUSTS_WEIDANIUM))
    val whitePhosphorus = mod(TG(Mat.whitePhosphorus, ingot = NT.INGOTS_PHOSPHORUS))
    val yellowcake = mod(TG(Mat.yellowcake, block = NT.STORAGE_BLOCKS_YELLOWCAKE, ingot = NT.YELLOWCAKE_URANIUM))

    private fun vanilla(group: TagGroup): TagGroup {
        vanillaTagGroups += group
        return group
    }

    private fun mod(group: TagGroup): TagGroup {
        modTagGroups += group
        return group
    }
}

interface MaterialGroup {
    val ingotIsPowder: Boolean

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
    val crystals: Supplier<out Item>? = null,
    val powder: Supplier<out Item>? = null,
    val plate: Supplier<out Item>? = null,
    val wire: Supplier<out Item>? = null,
    override val ingotIsPowder: Boolean = false,
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
    val ore: Supplier<out BlockItem>? = null,
    val deepOre: Supplier<out BlockItem>? = null,
    val block: Supplier<out BlockItem>? = null,
    val raw: Supplier<out Item>? = null,
    val ingot: Supplier<out Item>? = null,
    val nugget: Supplier<out Item>? = null,
    val crystals: Supplier<out Item>? = null,
    val powder: Supplier<out Item>? = null,
    val plate: Supplier<out Item>? = null,
    val wire: Supplier<out Item>? = null,
    override val ingotIsPowder: Boolean = false,
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

private typealias ItemTag = TagKey<Item>

data class TagGroup(
    val materialGroup: MaterialGroup,
    val ore: ItemTag? = null,
    val block: ItemTag? = null,
    val raw: ItemTag? = null,
    val ingot: ItemTag? = null,
    val nugget: ItemTag? = null,
    val crystals: ItemTag? = null,
    val powder: ItemTag? = null,
    val plate: ItemTag? = null,
    val wire: ItemTag? = null,
) {
    fun oreTagAndItem() = ore to materialGroup.ore()
    fun blockTagAndItem() = block to materialGroup.block()
    fun rawTagAndItem() = raw to materialGroup.raw()
    fun ingotTagAndItem() = ingot to materialGroup.ingot()
    fun nuggetTagAndItem() = nugget to materialGroup.nugget()
    fun crystalsTagAndItem() = crystals to materialGroup.crystals()
    fun powderTagAndItem() = powder to materialGroup.powder()
    fun plateTagAndItem() = plate to materialGroup.plate()
    fun wireTagAndItem() = wire to materialGroup.wire()
}
