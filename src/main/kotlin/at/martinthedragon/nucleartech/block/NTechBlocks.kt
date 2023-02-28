package at.martinthedragon.nucleartech.block

import at.martinthedragon.nucleartech.RegistriesAndLifecycle.BLOCKS
import at.martinthedragon.nucleartech.block.multi.MultiBlockPart
import at.martinthedragon.nucleartech.block.multi.MultiBlockPort
import at.martinthedragon.nucleartech.block.rbmk.*
import at.martinthedragon.nucleartech.hazard.HazardRegistry
import at.martinthedragon.nucleartech.registerK
import net.minecraft.core.BlockPos
import net.minecraft.util.valueproviders.UniformInt
import net.minecraft.world.item.DyeColor
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.block.*
import net.minecraft.world.level.block.state.BlockBehaviour.Properties
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.material.Material.*
import net.minecraft.world.level.material.MaterialColor
import net.minecraftforge.registries.RegistryObject
import kotlin.random.Random

object NTechBlocks {
    val uraniumOre = BLOCKS.registerK("uranium_ore") { OreBlock(Properties.of(STONE).strength(5F, 10F).requiresCorrectToolForDrops()) }
    val scorchedUraniumOre = BLOCKS.registerK("scorched_uranium_ore") { OreBlock(Properties.of(STONE).strength(5F, 10F).requiresCorrectToolForDrops()) }
    val thoriumOre = BLOCKS.registerK("thorium_ore") { OreBlock(Properties.of(STONE).strength(5F, 10F).requiresCorrectToolForDrops()) }
    val titaniumOre = BLOCKS.registerK("titanium_ore") { OreBlock(Properties.of(STONE).strength(5F, 10F).requiresCorrectToolForDrops()) }
    val sulfurOre = BLOCKS.registerK("sulfur_ore") { OreBlock(Properties.of(STONE).strength(3F, 6F).requiresCorrectToolForDrops(), UniformInt.of(1, 2)) }
    val niterOre = BLOCKS.registerK("niter_ore") { OreBlock(Properties.of(STONE).strength(3F, 6F).requiresCorrectToolForDrops(), UniformInt.of(1, 2)) }
    val tungstenOre = BLOCKS.registerK("tungsten_ore") { OreBlock(Properties.of(STONE).strength(6F, 10F).requiresCorrectToolForDrops()) }
    val aluminiumOre = BLOCKS.registerK("aluminium_ore") { OreBlock(Properties.of(STONE).strength(4F, 8F).requiresCorrectToolForDrops()) }
    val fluoriteOre = BLOCKS.registerK("fluorite_ore") { OreBlock(Properties.of(STONE).strength(3F, 6F).requiresCorrectToolForDrops(), UniformInt.of(2, 3)) }
    val berylliumOre = BLOCKS.registerK("beryllium_ore") { OreBlock(Properties.of(STONE).strength(5F, 15F).requiresCorrectToolForDrops()) }
    val leadOre = BLOCKS.registerK("lead_ore") { OreBlock(Properties.of(STONE).strength(5F, 10F).requiresCorrectToolForDrops()) }
    val oilDeposit = BLOCKS.registerK("oil_deposit") { Block(Properties.of(STONE).strength(1F, 2F).requiresCorrectToolForDrops()) }
    val emptyOilDeposit = BLOCKS.registerK("empty_oil_deposit") { Block(Properties.of(STONE).strength(1F, 1F).requiresCorrectToolForDrops()) }
    val oilSand = BLOCKS.registerK("oil_sand") { Block(Properties.of(SAND).strength(1F).sound(SoundType.SAND)) }
    val ligniteOre = BLOCKS.registerK("lignite_ore") { OreBlock(Properties.of(STONE).strength(3F).requiresCorrectToolForDrops(), UniformInt.of(0, 1)) }
    val asbestosOre = BLOCKS.registerK("asbestos_ore") { OreBlock(Properties.of(STONE).strength(3F).requiresCorrectToolForDrops()) }
    val schrabidiumOre = BLOCKS.registerK("schrabidium_ore") { Block(Properties.of(STONE).strength(20F, 50F).lightLevel { 3 }.hasPostProcess { _, _, _ -> true }.emissiveRendering { _, _, _ -> true }) }
    val australianOre = BLOCKS.registerK("australian_ore") { OreBlock(Properties.of(STONE).strength(6F).requiresCorrectToolForDrops()) }
    val weidite = BLOCKS.registerK("weidite") { OreBlock(Properties.of(STONE).strength(6F).requiresCorrectToolForDrops()) }
    val reiite = BLOCKS.registerK("reiite") { OreBlock(Properties.of(STONE).strength(6F).requiresCorrectToolForDrops()) }
    val brightblendeOre = BLOCKS.registerK("brightblende_ore") { OreBlock(Properties.of(STONE).strength(6F).requiresCorrectToolForDrops()) }
    val dellite = BLOCKS.registerK("dellite") { OreBlock(Properties.of(STONE).strength(6F).requiresCorrectToolForDrops()) }
    val dollarGreenMineral = BLOCKS.registerK("dollar_green_mineral") { OreBlock(Properties.of(STONE).strength(6F).requiresCorrectToolForDrops()) }
    val rareEarthOre = BLOCKS.registerK("rare_earth_ore") { OreBlock(Properties.of(STONE).strength(4F, 3F).requiresCorrectToolForDrops(), UniformInt.of(3, 6)) }
    val cobaltOre = BLOCKS.registerK("cobalt_ore") { OreBlock(Properties.of(STONE).strength(3F).requiresCorrectToolForDrops()) }
    val deepslateUraniumOre = BLOCKS.registerK("deepslate_uranium_ore") { OreBlock(Properties.copy(uraniumOre.get()).color(MaterialColor.DEEPSLATE).strength(7.5F, 10F).sound(SoundType.DEEPSLATE)) }
    val scorchedDeepslateUraniumOre = BLOCKS.registerK("scorched_deepslate_uranium_ore") { OreBlock(Properties.copy(scorchedUraniumOre.get()).color(MaterialColor.DEEPSLATE).strength(7.5F, 10F).sound(SoundType.DEEPSLATE)) }
    val deepslateThoriumOre = BLOCKS.registerK("deepslate_thorium_ore") { OreBlock(Properties.copy(thoriumOre.get()).color(MaterialColor.DEEPSLATE).strength(7.5F, 10F).sound(SoundType.DEEPSLATE)) }
    val deepslateTitaniumOre = BLOCKS.registerK("deepslate_titanium_ore") { OreBlock(Properties.copy(titaniumOre.get()).color(MaterialColor.DEEPSLATE).strength(7.5F, 10F).sound(SoundType.DEEPSLATE)) }
    val deepslateSulfurOre = BLOCKS.registerK("deepslate_sulfur_ore") { OreBlock(Properties.copy(sulfurOre.get()).color(MaterialColor.DEEPSLATE).strength(4.5F, 6F).sound(SoundType.DEEPSLATE), UniformInt.of(1, 2)) }
    val deepslateNiterOre = BLOCKS.registerK("deepslate_niter_ore") { OreBlock(Properties.copy(niterOre.get()).color(MaterialColor.DEEPSLATE).strength(4.5F, 6F).sound(SoundType.DEEPSLATE), UniformInt.of(1, 2)) }
    val deepslateTungstenOre = BLOCKS.registerK("deepslate_tungsten_ore") { OreBlock(Properties.copy(tungstenOre.get()).color(MaterialColor.DEEPSLATE).strength(6F, 10F).sound(SoundType.DEEPSLATE)) }
    val deepslateAluminiumOre = BLOCKS.registerK("deepslate_aluminium_ore") { OreBlock(Properties.copy(aluminiumOre.get()).color(MaterialColor.DEEPSLATE).strength(6F, 8F).sound(SoundType.DEEPSLATE)) }
    val deepslateFluoriteOre = BLOCKS.registerK("deepslate_fluorite_ore") { OreBlock(Properties.copy(fluoriteOre.get()).color(MaterialColor.DEEPSLATE).strength(4.5F, 6F).sound(SoundType.DEEPSLATE), UniformInt.of(2, 3)) }
    val deepslateBerylliumOre = BLOCKS.registerK("deepslate_beryllium_ore") { OreBlock(Properties.copy(berylliumOre.get()).color(MaterialColor.DEEPSLATE).strength(7.5F, 15F).sound(SoundType.DEEPSLATE)) }
    val deepslateLeadOre = BLOCKS.registerK("deepslate_lead_ore") { OreBlock(Properties.copy(leadOre.get()).color(MaterialColor.DEEPSLATE).strength(7.5F, 10F).sound(SoundType.DEEPSLATE)) }
    val deepslateOilDeposit = BLOCKS.registerK("deepslate_oil_deposit") { Block(Properties.copy(oilDeposit.get()).color(MaterialColor.DEEPSLATE).strength(2F).sound(SoundType.DEEPSLATE)) }
    val emptyDeepslateOilDeposit = BLOCKS.registerK("empty_deepslate_oil_deposit") { Block(Properties.copy(emptyOilDeposit.get()).color(MaterialColor.DEEPSLATE).strength(2F, 1F).sound(SoundType.DEEPSLATE)) }
    val deepslateAsbestosOre = BLOCKS.registerK("deepslate_asbestos_ore") { OreBlock(Properties.copy(asbestosOre.get()).color(MaterialColor.DEEPSLATE).strength(4.5F, 3F).sound(SoundType.DEEPSLATE)) }
    val deepslateSchrabidiumOre = BLOCKS.registerK("deepslate_schrabidium_ore") { Block(Properties.copy(schrabidiumOre.get()).color(MaterialColor.DEEPSLATE).strength(30F, 50F).sound(SoundType.DEEPSLATE)) }
    val deepslateAustralianOre = BLOCKS.registerK("deepslate_australian_ore") { OreBlock(Properties.copy(australianOre.get()).color(MaterialColor.DEEPSLATE).strength(9F, 6F).sound(SoundType.DEEPSLATE)) }
    val deepslateRareEarthOre = BLOCKS.registerK("deepslate_rare_earth_ore") { OreBlock(Properties.copy(rareEarthOre.get()).color(MaterialColor.DEEPSLATE).strength(6F, 3F).sound(SoundType.DEEPSLATE), UniformInt.of(3, 6)) }
    val deepslateCobaltOre = BLOCKS.registerK("deepslate_cobalt_ore") { OreBlock(Properties.copy(cobaltOre.get()).color(MaterialColor.DEEPSLATE).strength(4.5F, 3F).sound(SoundType.DEEPSLATE)) }
    val netherUraniumOre = BLOCKS.registerK("nether_uranium_ore") { Block(Properties.of(STONE).strength(3f).requiresCorrectToolForDrops()) }
    val scorchedNetherUraniumOre = BLOCKS.registerK("scorched_nether_uranium_ore") { Block(Properties.of(STONE).strength(3F).requiresCorrectToolForDrops()) }
    val netherPlutoniumOre = BLOCKS.registerK("nether_plutonium_ore") { Block(Properties.of(STONE).strength(3f).requiresCorrectToolForDrops()) }
    val netherTungstenOre = BLOCKS.registerK("nether_tungsten_ore") { Block(Properties.of(STONE).strength(3f, 4f).requiresCorrectToolForDrops()) }
    val netherSulfurOre: RegistryObject<Block> = BLOCKS.registerK("nether_sulfur_ore") { object : Block(Properties.of(STONE).strength(2f, 3f).requiresCorrectToolForDrops()) {
        override fun getExpDrop(state: BlockState?, world: LevelReader, pos: BlockPos?, fortune: Int, silktouch: Int) = if (silktouch != 0) 0 else Random.nextInt(1, 4)
    }}
    val netherPhosphorusOre: RegistryObject<Block> = BLOCKS.registerK("nether_phosphorus_ore") { object : Block(Properties.of(STONE).strength(2f, 3f).requiresCorrectToolForDrops()) {
        override fun getExpDrop(state: BlockState?, world: LevelReader, pos: BlockPos?, fortune: Int, silktouch: Int) = if (silktouch != 0) 0 else Random.nextInt(2, 5)
    }}
    val netherSchrabidiumOre = BLOCKS.registerK("nether_schrabidium_ore") { Block(Properties.of(STONE).strength(20f, 50f).requiresCorrectToolForDrops().lightLevel { 7 }.hasPostProcess { _, _, _ -> true }.emissiveRendering { _, _, _ -> true }) }
    val meteorUraniumOre = BLOCKS.registerK("meteor_uranium_ore") { Block(Properties.of(STONE).strength(5F, 10F).requiresCorrectToolForDrops()) }
    val meteorThoriumOre = BLOCKS.registerK("meteor_thorium_ore") { Block(Properties.of(STONE).strength(5F, 10F).requiresCorrectToolForDrops()) }
    val meteorTitaniumOre = BLOCKS.registerK("meteor_titanium_ore") { Block(Properties.of(STONE).strength(5F, 10F).requiresCorrectToolForDrops()) }
    val meteorSulfurOre: RegistryObject<Block> = BLOCKS.registerK("meteor_sulfur_ore") { object : Block(Properties.of(STONE).strength(5F, 10F).requiresCorrectToolForDrops()) {
        override fun getExpDrop(state: BlockState?, world: LevelReader, pos: BlockPos?, fortune: Int, silktouch: Int) = if (silktouch != 0) 0 else Random.nextInt(5, 9)
    }}
    val meteorCopperOre = BLOCKS.registerK("meteor_copper_ore") { Block(Properties.of(STONE).strength(5F, 10F).requiresCorrectToolForDrops()) }
    val meteorTungstenOre = BLOCKS.registerK("meteor_tungsten_ore") { Block(Properties.of(STONE).strength(5F, 10F).requiresCorrectToolForDrops()) }
    val meteorAluminiumOre = BLOCKS.registerK("meteor_aluminium_ore") { Block(Properties.of(STONE).strength(5F, 10F).requiresCorrectToolForDrops()) }
    val meteorLeadOre = BLOCKS.registerK("meteor_lead_ore") { Block(Properties.of(STONE).strength(5F, 10F).requiresCorrectToolForDrops()) }
    val meteorLithiumOre = BLOCKS.registerK("meteor_lithium_ore") { Block(Properties.of(STONE).strength(5F, 10F).requiresCorrectToolForDrops()) }
    val starmetalOre = BLOCKS.registerK("starmetal_ore") { Block(Properties.of(STONE).strength(10F, 100F).requiresCorrectToolForDrops()) }
    val trixite = BLOCKS.registerK("trixite") { Block(Properties.of(STONE).strength(4f, 9f).requiresCorrectToolForDrops()) }
    val basaltSulfurOre = BLOCKS.registerK("basalt_sulfur_ore") { RotatedPillarBlock(Properties.of(STONE, DyeColor.BLACK).requiresCorrectToolForDrops().strength(1.25F, 4.2F).sound(SoundType.BASALT)) }
    val basaltFluoriteOre = BLOCKS.registerK("basalt_fluorite_ore") { RotatedPillarBlock(Properties.of(STONE, DyeColor.BLACK).requiresCorrectToolForDrops().strength(1.25F, 4.2F).sound(SoundType.BASALT)) }
    val basaltAsbestosOre = BLOCKS.registerK("basalt_asbestos_ore") { RotatedPillarBlock(Properties.of(STONE, DyeColor.BLACK).requiresCorrectToolForDrops().strength(1.25F, 4.2F).sound(SoundType.BASALT)) } // TODO asbestos particles
    val basaltVolcanicGemOre = BLOCKS.registerK("basalt_volcanic_gem_ore") { VolcanicGemOreBlock(Properties.of(STONE, DyeColor.ORANGE).requiresCorrectToolForDrops().strength(2F, 5F).sound(SoundType.BASALT)) }

    val uraniumBlock = BLOCKS.registerK("uranium_block") { HazardBlock(Properties.of(METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)).radiation(HazardRegistry.U) }
    val u233Block = BLOCKS.registerK("u233_block") { HazardBlock(Properties.of(METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)).radiation(HazardRegistry.U233) }
    val u235Block = BLOCKS.registerK("u235_block") { HazardBlock(Properties.of(METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)).radiation(HazardRegistry.U235) }
    val u238Block = BLOCKS.registerK("u238_block") { HazardBlock(Properties.of(METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)).radiation(HazardRegistry.U238) }
    val uraniumFuelBlock = BLOCKS.registerK("uranium_fuel_block") { HazardBlock(Properties.of(METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)).radiation(HazardRegistry.UF) }
    val neptuniumBlock = BLOCKS.registerK("neptunium_block") { HazardBlock(Properties.of(METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)).radiation(HazardRegistry.Np237) }
    val moxFuelBlock = BLOCKS.registerK("mox_fuel_block") { HazardBlock(Properties.of(METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)).radiation(HazardRegistry.MOX) }
    val plutoniumBlock = BLOCKS.registerK("plutonium_block") { HazardBlock(Properties.of(METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)).radiation(HazardRegistry.Pu) }
    val pu238Block = BLOCKS.registerK("pu238_block") { HazardBlock(Properties.of(METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)).radiation(HazardRegistry.Pu238) }
    val pu239Block = BLOCKS.registerK("pu239_block") { HazardBlock(Properties.of(METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)).radiation(HazardRegistry.Pu239) }
    val pu240Block = BLOCKS.registerK("pu240_block") { HazardBlock(Properties.of(METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)).radiation(HazardRegistry.Pu240) }
    val plutoniumFuelBlock = BLOCKS.registerK("plutonium_fuel_block") { HazardBlock(Properties.of(METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)).radiation(HazardRegistry.PuF) }
    val thoriumBlock = BLOCKS.registerK("thorium_block") { HazardBlock(Properties.of(METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)).radiation(HazardRegistry.Th232) }
    val thoriumFuelBlock = BLOCKS.registerK("thorium_fuel_block") { HazardBlock(Properties.of(METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)).radiation(HazardRegistry.ThF) }
    val titaniumBlock = BLOCKS.registerK("titanium_block") { Block(Properties.of(METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val sulfurBlock = BLOCKS.registerK("sulfur_block") { Block(Properties.of(METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val niterBlock = BLOCKS.registerK("niter_block") { Block(Properties.of(METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val redCopperBlock = BLOCKS.registerK("red_copper_block") { Block(Properties.of(METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val advancedAlloyBlock = BLOCKS.registerK("advanced_alloy_block") { Block(Properties.of(METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val tungstenBlock = BLOCKS.registerK("tungsten_block") { Block(Properties.of(METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val aluminiumBlock = BLOCKS.registerK("aluminium_block") { Block(Properties.of(METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val fluoriteBlock = BLOCKS.registerK("fluorite_block") { Block(Properties.of(METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val berylliumBlock = BLOCKS.registerK("beryllium_block") { Block(Properties.of(METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val cobaltBlock = BLOCKS.registerK("cobalt_block") { Block(Properties.of(METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val steelBlock = BLOCKS.registerK("steel_block") { Block(Properties.of(METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val leadBlock = BLOCKS.registerK("lead_block") { Block(Properties.of(METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val lithiumBlock = BLOCKS.registerK("lithium_block") { Block(Properties.of(METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val whitePhosphorusBlock = BLOCKS.registerK("white_phosphorus_block") { Block(Properties.of(METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val redPhosphorusBlock = BLOCKS.registerK("red_phosphorus_block") { Block(Properties.of(METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val yellowcakeBlock = BLOCKS.registerK("yellowcake_block") { Block(Properties.of(CLAY).strength(6f).sound(SoundType.SAND)) }
    val scrapBlock = BLOCKS.registerK("scrap_block") { Block(Properties.of(CLAY).strength(1f).sound(SoundType.GRAVEL)) }
    val electricalScrapBlock = BLOCKS.registerK("electrical_scrap_block") { Block(Properties.of(METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val insulatorRoll = BLOCKS.registerK("insulator_roll") { RotatedPillarBlock(Properties.of(METAL).strength(6f).sound(SoundType.METAL)) }
    val fiberglassRoll = BLOCKS.registerK("fiberglass_roll") { RotatedPillarBlock(Properties.of(METAL).strength(6f).sound(SoundType.METAL)) }
    val asbestosBlock = BLOCKS.registerK("asbestos_block") { Block(Properties.of(METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val trinititeBlock = BLOCKS.registerK("trinitite_block") { HazardBlock(Properties.of(METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)).radiation(HazardRegistry.Trinitite) }
    val nuclearWasteBlock = BLOCKS.registerK("nuclear_waste_block") { HazardBlock(Properties.of(METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)).radiation(HazardRegistry.Waste) }
    val schrabidiumBlock = BLOCKS.registerK("schrabidium_block") { HazardBlock(Properties.of(METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)).radiation(HazardRegistry.Sa326) }
    val soliniumBlock = BLOCKS.registerK("solinium_block") { HazardBlock(Properties.of(METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)).radiation(HazardRegistry.Sa327) }
    val schrabidiumFuelBlock = BLOCKS.registerK("schrabidium_fuel_block") { HazardBlock(Properties.of(METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)).radiation(HazardRegistry.SaF) }
    val euphemiumBlock = BLOCKS.registerK("euphemium_block") { Block(Properties.of(METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val schrabidiumCluster = BLOCKS.registerK("schrabidium_cluster") { Block(Properties.of(METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val euphemiumEtchedSchrabidiumCluster = BLOCKS.registerK("euphemium_etched_schrabidium_cluster") { Block(Properties.of(METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val magnetizedTungstenBlock = BLOCKS.registerK("magnetized_tungsten_block") { Block(Properties.of(METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val combineSteelBlock = BLOCKS.registerK("combine_steel_block") { Block(Properties.of(METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val deshReinforcedBlock = BLOCKS.registerK("desh_reinforced_block") { Block(Properties.of(METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val starmetalBlock = BLOCKS.registerK("starmetal_block") { Block(Properties.of(METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val australiumBlock = BLOCKS.registerK("australium_block") { Block(Properties.of(METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val weidaniumBlock = BLOCKS.registerK("weidanium_block") { Block(Properties.of(METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val reiiumBlock = BLOCKS.registerK("reiium_block") { Block(Properties.of(METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val unobtainiumBlock = BLOCKS.registerK("unobtainium_block") { Block(Properties.of(METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val daffergonBlock = BLOCKS.registerK("daffergon_block") { Block(Properties.of(METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val verticiumBlock = BLOCKS.registerK("verticium_block") { Block(Properties.of(METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val titaniumDecoBlock = BLOCKS.registerK("titanium_deco_block") { Block(Properties.of(METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val redCopperDecoBlock = BLOCKS.registerK("red_copper_deco_block") { Block(Properties.of(METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val tungstenDecoBlock = BLOCKS.registerK("tungsten_deco_block") { Block(Properties.of(METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val aluminiumDecoBlock = BLOCKS.registerK("aluminium_deco_block") { Block(Properties.of(METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val steelDecoBlock = BLOCKS.registerK("steel_deco_block") { Block(Properties.of(METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val leadDecoBlock = BLOCKS.registerK("lead_deco_block") { Block(Properties.of(METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val berylliumDecoBlock = BLOCKS.registerK("beryllium_deco_block") { Block(Properties.of(METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val asbestosRoof = BLOCKS.registerK("asbestos_roof") { Block(Properties.of(METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val hazmatBlock = BLOCKS.registerK("hazmat_block") { Block(Properties.of(WOOL).strength(6f).sound(SoundType.WOOL)) }

    val meteorite = BLOCKS.registerK("meteorite") { Block(Properties.of(STONE).strength(15F, 900F).sound(SoundType.STONE)) }
    val meteoriteCobblestone = BLOCKS.registerK("meteorite_cobblestone") { Block(Properties.of(STONE).strength(15F, 900F).sound(SoundType.STONE)) }
    val brokenMeteorite = BLOCKS.registerK("broken_meteorite") { Block(Properties.of(STONE).strength(15F, 900F).sound(SoundType.STONE)) }
    val hotMeteoriteCobblestone = BLOCKS.registerK("hot_meteorite_cobblestone") { MoltenMeteorBlock(Properties.of(STONE).strength(15F, 900F).randomTicks().lightLevel { 7 }.hasPostProcess { _, _, _ -> true }.sound(SoundType.STONE)) }
    val meteoriteTreasure = BLOCKS.registerK("meteorite_treasure") { Block(Properties.of(STONE).strength(15F, 900F).sound(SoundType.STONE)) }

    val steelBeam = BLOCKS.registerK("steel_beam") { SteelBeamBlock(Properties.of(METAL).strength(5F, 15F).sound(SoundType.METAL)) }
    val steelScaffold = BLOCKS.registerK("steel_scaffold") { SteelScaffoldBlock(Properties.of(METAL).strength(5F, 15F).sound(SoundType.METAL)) }
    val steelGrate = BLOCKS.registerK("steel_grate") { SteelGrate(Properties.of(METAL).strength(5F, 15F).sound(SoundType.METAL).noOcclusion()) }

    val glowingMushroom = BLOCKS.registerK("glowing_mushroom") { GlowingMushroomBlock(Properties.of(PLANT, MaterialColor.COLOR_GREEN).noCollission().randomTicks().instabreak().lightLevel { 7 }.hasPostProcess { _, _, _ -> true }.sound(SoundType.GRASS)) }
    val glowingMushroomBlock = BLOCKS.registerK("glowing_mushroom_block") { HugeMushroomBlock(Properties.of(WOOD, MaterialColor.COLOR_GREEN).strength(.2F).lightLevel { 15 }.hasPostProcess { _, _, _ -> true }.sound(SoundType.WOOD)) }
    val glowingMushroomStem = BLOCKS.registerK("glowing_mushroom_stem") { HugeMushroomBlock(Properties.of(WOOD, MaterialColor.COLOR_GREEN).strength(.2F).lightLevel { 12 }.hasPostProcess { _, _, _ -> true }.sound(SoundType.WOOD)) }
    val deadGrass = BLOCKS.registerK("dead_grass") { DeadGrassBlock(Properties.of(GRASS, MaterialColor.COLOR_BROWN).strength(.6F).randomTicks().sound(SoundType.GRASS)) }
    val glowingMycelium = BLOCKS.registerK("glowing_mycelium") { GlowingMyceliumBlock(Properties.of(GRASS, MaterialColor.COLOR_LIGHT_GREEN).strength(.6F).randomTicks().lightLevel { 15 }.hasPostProcess { _, _, _ -> true }.sound(SoundType.GRASS)) }
    val trinitite = BLOCKS.registerK("trinitite_ore") { TrinititeBlock(Properties.of(SAND, MaterialColor.COLOR_GREEN).strength(.5F, 2.5F).sound(SoundType.SAND)) }
    val redTrinitite = BLOCKS.registerK("red_trinitite_ore") { TrinititeBlock(Properties.of(SAND, MaterialColor.COLOR_GREEN).strength(.5F, 2.5F).sound(SoundType.SAND)) }
    val charredLog = BLOCKS.registerK("charred_log") { RotatedPillarBlock(Properties.of(WOOD, MaterialColor.COLOR_BLACK).strength(5F, 2.5F).sound(SoundType.WOOD)) }
    val charredPlanks = BLOCKS.registerK("charred_planks") { Block(Properties.of(WOOD, MaterialColor.COLOR_BLACK).strength(.5F, 2.5F).sound(SoundType.WOOD)) }

    val slakedSellafite = BLOCKS.registerK("slaked_sellafite") { Block(Properties.of(STONE, MaterialColor.COLOR_GRAY).strength(5F).requiresCorrectToolForDrops().sound(SoundType.STONE)) }
    val sellafite = BLOCKS.registerK("sellafite") { HazardBlock(Properties.of(STONE, MaterialColor.COLOR_GREEN).strength(5F).requiresCorrectToolForDrops().sound(SoundType.STONE)).radiation(.05F) }
    val hotSellafite = BLOCKS.registerK("hot_sellafite") { HazardBlock(Properties.of(STONE, MaterialColor.COLOR_GREEN).strength(5F).requiresCorrectToolForDrops().sound(SoundType.STONE)).radiation(.1F) }
    val boilingSellafite = BLOCKS.registerK("boiling_sellafite") { HazardBlock(Properties.of(STONE, MaterialColor.COLOR_GREEN).strength(5F).requiresCorrectToolForDrops().sound(SoundType.STONE)).radiation(.25F) }
    val blazingSellafite = BLOCKS.registerK("blazing_sellafite") { HazardBlock(Properties.of(STONE, MaterialColor.COLOR_LIGHT_GREEN).strength(5F).requiresCorrectToolForDrops().sound(SoundType.STONE)).radiation(.4F) }
    val infernalSellafite = BLOCKS.registerK("infernal_sellafite") { HazardBlock(Properties.of(STONE, MaterialColor.COLOR_LIGHT_GREEN).strength(5F).requiresCorrectToolForDrops().sound(SoundType.STONE)).radiation(.6F) }
    val sellafiteCorium = BLOCKS.registerK("sellafite_corium") { HazardBlock(Properties.of(STONE, MaterialColor.COLOR_LIGHT_GREEN).strength(10F).requiresCorrectToolForDrops().sound(SoundType.STONE)).radiation(1F) }

    val corium = BLOCKS.registerK("corium") { HazardBlock(Properties.of(METAL, MaterialColor.COLOR_BLACK).strength(100F, 6000F).requiresCorrectToolForDrops().sound(SoundType.STONE)).radiation(10F) }
    val corebblestone = BLOCKS.registerK("corebblestone") { HazardBlock(Properties.of(METAL, MaterialColor.COLOR_BLACK).strength(100F, 6000F).requiresCorrectToolForDrops().sound(SoundType.STONE)).radiation(8F) } // TODO emit radon

    // Machines

    val siren = BLOCKS.registerK("siren") { SirenBlock(Properties.of(METAL).strength(5f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val safe = BLOCKS.registerK("safe") { SafeBlock(Properties.of(METAL).strength(25f, 1200f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }

    val steamPressBase = BLOCKS.registerK("steam_press_base") { SteamPressBaseBlock(Properties.of(METAL).strength(5f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val steamPressFrame = BLOCKS.registerK("steam_press_frame") { SteamPressFrameBlock(Properties.of(METAL).strength(5f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val steamPressTop = BLOCKS.registerK("steam_press_top") { SteamPressTopBlock(Properties.of(METAL).strength(5f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }

    val blastFurnace = BLOCKS.registerK("blast_furnace") { BlastFurnaceBlock(Properties.of(METAL).strength(5F).requiresCorrectToolForDrops().lightLevel(this::getLightLevelLit13).sound(SoundType.METAL)) }
    val combustionGenerator = BLOCKS.registerK("combustion_generator") { CombustionGeneratorBlock(Properties.of(METAL).strength(5F).requiresCorrectToolForDrops().lightLevel(this::getLightLevelLit13).sound(SoundType.METAL)) }
    val electricFurnace = BLOCKS.registerK("electric_furnace") { ElectricFurnaceBlock(Properties.of(METAL).strength(5F).requiresCorrectToolForDrops().lightLevel(this::getLightLevelLit13).sound(SoundType.METAL)) }
    val shredder = BLOCKS.registerK("shredder") { ShredderBlock(Properties.of(METAL).strength(5F).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val assembler = BLOCKS.registerK("assembler") { AssemblerBlock(Properties.of(METAL).strength(5F).requiresCorrectToolForDrops().sound(SoundType.METAL).noOcclusion()) }
    val chemPlant = BLOCKS.registerK("chem_plant") { ChemPlantBlock(Properties.of(METAL).strength(5F).requiresCorrectToolForDrops().sound(SoundType.METAL).noOcclusion()) }
    val oilDerrick = BLOCKS.registerK("oil_derrick") { OilDerrickBlock(Properties.of(METAL).strength(5F).requiresCorrectToolForDrops().sound(SoundType.METAL).noOcclusion()) }
    val pumpjack = BLOCKS.registerK("pumpjack") { PumpjackBlock(Properties.of(METAL).strength(5F).requiresCorrectToolForDrops().sound(SoundType.METAL).noOcclusion()) }
    val centrifuge = BLOCKS.registerK("centrifuge") { CentrifugeBlock(Properties.of(METAL).strength(5F).requiresCorrectToolForDrops().sound(SoundType.METAL).noOcclusion()) }

    val ironAnvil = BLOCKS.registerK("iron_anvil") { AnvilBlock(1, Properties.of(METAL).strength(5F, 100F).requiresCorrectToolForDrops().sound(SoundType.ANVIL)) }
    val leadAnvil = BLOCKS.registerK("lead_anvil") { AnvilBlock(1, Properties.of(METAL).strength(5F, 100F).requiresCorrectToolForDrops().sound(SoundType.ANVIL)) }
    val steelAnvil = BLOCKS.registerK("steel_anvil") { AnvilBlock(2, Properties.of(METAL).strength(5F, 100F).requiresCorrectToolForDrops().sound(SoundType.ANVIL)) }
    val meteoriteAnvil = BLOCKS.registerK("meteorite_anvil") { AnvilBlock(3, Properties.of(METAL).strength(5F, 100F).requiresCorrectToolForDrops().sound(SoundType.ANVIL)) }
    val starmetalAnvil = BLOCKS.registerK("starmetal_anvil") { AnvilBlock(3, Properties.of(METAL).strength(5F, 100F).requiresCorrectToolForDrops().sound(SoundType.ANVIL)) }
    val ferrouraniumAnvil = BLOCKS.registerK("ferrouranium_anvil") { AnvilBlock(4, Properties.of(METAL).strength(5F, 100F).requiresCorrectToolForDrops().sound(SoundType.ANVIL)) }
    val bismuthAnvil = BLOCKS.registerK("bismuth_anvil") { AnvilBlock(5, Properties.of(METAL).strength(5F, 100F).requiresCorrectToolForDrops().sound(SoundType.ANVIL)) }
    val schrabidateAnvil = BLOCKS.registerK("schrabidate_anvil") { AnvilBlock(6, Properties.of(METAL).strength(5F, 100F).requiresCorrectToolForDrops().sound(SoundType.ANVIL)) }
    val dineutroniumAnvil = BLOCKS.registerK("dineutronium_anvil") { AnvilBlock(7, Properties.of(METAL).strength(5F, 100F).requiresCorrectToolForDrops().sound(SoundType.ANVIL)) }
    val murkyAnvil = BLOCKS.registerK("murky_anvil") { AnvilBlock(1916169, Properties.of(METAL).strength(5F, 100F).requiresCorrectToolForDrops().sound(SoundType.ANVIL)) }

    val rbmkColumn = BLOCKS.registerK("rbmk_column") { RBMKColumnBlock(Properties.of(METAL).strength(5F, 100F).requiresCorrectToolForDrops().sound(SoundType.METAL).noOcclusion()) }
    val rbmkRod = BLOCKS.registerK("rbmk_rod") { RBMKRodBlock(rbmkColumn, Properties.of(METAL).strength(5F, 100F).requiresCorrectToolForDrops().sound(SoundType.METAL).noOcclusion()) }
    val rbmkModeratedRod = BLOCKS.registerK("rbmk_moderated_rod") { RBMKModeratedRodBlock(rbmkColumn, Properties.of(METAL).strength(5F, 100F).requiresCorrectToolForDrops().sound(SoundType.METAL).noOcclusion()) }
    val rbmkReaSimRod = BLOCKS.registerK("rbmk_reasim_rod") { RBMKReaSimRodBlock(rbmkColumn, Properties.of(METAL).strength(5F, 100F).requiresCorrectToolForDrops().sound(SoundType.METAL).noOcclusion()) }
    val rbmkModeratedReaSimRod = BLOCKS.registerK("rbmk_moderated_reasim_rod") { RBMKModeratedReaSimRodBlock(rbmkColumn, Properties.of(METAL).strength(5F, 100F).requiresCorrectToolForDrops().sound(SoundType.METAL).noOcclusion()) }
    val rbmkReflector = BLOCKS.registerK("rbmk_reflector") { RBMKReflectorBlock(rbmkColumn, Properties.of(METAL).strength(5F, 100F).requiresCorrectToolForDrops().sound(SoundType.METAL).noOcclusion()) }
    val rbmkModerator = BLOCKS.registerK("rbmk_moderator") { RBMKModeratorBlock(rbmkColumn, Properties.of(METAL).strength(5F, 100F).requiresCorrectToolForDrops().sound(SoundType.METAL).noOcclusion()) }
    val rbmkAbsorber = BLOCKS.registerK("rbmk_absorber") { RBMKAbsorberBlock(rbmkColumn, Properties.of(METAL).strength(5F, 100F).requiresCorrectToolForDrops().sound(SoundType.METAL).noOcclusion()) }
    val rbmkBoilerColumn = BLOCKS.registerK("rbmk_boiler_column") { RBMKBoilerColumnBlock(Properties.of(METAL).strength(5F, 100F).requiresCorrectToolForDrops().sound(SoundType.METAL).noOcclusion()) }
    val rbmkBoiler = BLOCKS.registerK("rbmk_boiler") { RBMKBoilerBlock(rbmkBoilerColumn, Properties.of(METAL).strength(5F, 100F).requiresCorrectToolForDrops().sound(SoundType.METAL).noOcclusion()) }
    val rbmkBlank = BLOCKS.registerK("rbmk_blank") { RBMKBlankBlock(rbmkColumn, Properties.of(METAL).strength(5F, 100F).requiresCorrectToolForDrops().sound(SoundType.METAL).noOcclusion()) }
    val rbmkManualControlRod = BLOCKS.registerK("rbmk_manual_control_rod") { RBMKManualControlBlock(rbmkColumn, Properties.of(METAL).strength(5F, 100F).requiresCorrectToolForDrops().sound(SoundType.METAL).noOcclusion()) }
    val rbmkModeratedControlRod = BLOCKS.registerK("rbmk_moderated_control_rod") { RBMKModeratedControlBlock(rbmkColumn, Properties.of(METAL).strength(5F, 100F).requiresCorrectToolForDrops().sound(SoundType.METAL).noOcclusion()) }
    val rbmkAutoControlRod = BLOCKS.registerK("rbmk_auto_control_rod") { RBMKAutoControlBlock(rbmkColumn, Properties.of(METAL).strength(5F, 100F).requiresCorrectToolForDrops().sound(SoundType.METAL).noOcclusion()) }
    val rbmkSteamConnector = BLOCKS.registerK("rbmk_steam_connector") { RBMKSteamConnectorBlock(Properties.of(METAL).strength(5F, 100F).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val rbmkInlet = BLOCKS.registerK("rbmk_inlet") { RBMKInletBlock(Properties.of(METAL).strength(5F, 100F).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val rbmkOutlet = BLOCKS.registerK("rbmk_outlet") { RBMKOutletBlock(Properties.of(METAL).strength(5F, 100F).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val rbmkConsole = BLOCKS.registerK("rbmk_console") { RBMKConsoleBlock(Properties.of(METAL).strength(5F, 100F).requiresCorrectToolForDrops().sound(SoundType.METAL).noOcclusion()) }
    val rbmkDebris = BLOCKS.registerK("rbmk_debris") { RBMKDebrisBlock(Properties.of(METAL).strength(4F, 50F).requiresCorrectToolForDrops().sound(SoundType.METAL).noOcclusion()) }
    val rbmkBurningDebris = BLOCKS.registerK("rbmk_burning_debris") { RBMKBurningDebrisBlock(Properties.of(METAL).strength(4F, 50F).requiresCorrectToolForDrops().sound(SoundType.METAL).noOcclusion()) }
    val rbmkRadioactiveDebris = BLOCKS.registerK("rbmk_radioactive_debris") { RBMKRadioactiveDebrisBlock(Properties.of(METAL).strength(4F, 50F).requiresCorrectToolForDrops().sound(SoundType.METAL).noOcclusion().lightLevel { 15 }) }

    val coatedCable = BLOCKS.registerK("coated_red_copper_cable") { CoatedCableBlock(Properties.of(METAL).strength(5F, 100F).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val coatedUniversalFluidDuct = BLOCKS.registerK("coated_fluid_duct") { CoatedUniversalFluidPipeBlock(Properties.of(METAL).strength(5F, 100F).requiresCorrectToolForDrops().sound(SoundType.METAL)) }

    // Bombs

    val littleBoy = BLOCKS.registerK("little_boy") { LittleBoyBlock(Properties.of(METAL).strength(5F, 6000F).requiresCorrectToolForDrops().sound(SoundType.METAL).noOcclusion()) }
    val fatMan = BLOCKS.registerK("fat_man") { FatManBlock(Properties.of(METAL).strength(5F, 6000F).requiresCorrectToolForDrops().sound(SoundType.METAL).noOcclusion()) }
    val volcanoCore = BLOCKS.registerK("volcano_core") { VolcanoBlock(Properties.of(STONE).strength(-1F, 10000F).sound(SoundType.STONE).noDrops()) }

    // Missiles

    val launchPad = BLOCKS.registerK("launch_pad") { LaunchPadBlock(Properties.of(METAL).strength(5F, 1000F).requiresCorrectToolForDrops().sound(SoundType.METAL).noOcclusion()) }
    val launchPadPart = BLOCKS.registerK("launch_pad_part", LaunchPadBlock::LaunchPadPartBlock)

    // Other

    val genericMultiBlockPart = BLOCKS.registerK("generic_multi_block_part") { MultiBlockPart() }
    val genericMultiBlockPort = BLOCKS.registerK("generic_multi_block_port") { MultiBlockPort() }
    val oilPipe = BLOCKS.registerK("oil_pipe") { Block(Properties.of(METAL).strength(5F, 10F).sound(SoundType.METAL)) }

    // Decoration
    val decoRbmkBlock = BLOCKS.registerK("deco_rbmk") { Block(Properties.of(METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val decoRbmkSmoothBlock = BLOCKS.registerK("deco_rbmk_smooth") { Block(Properties.of(METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }

    private fun getLightLevelLit13(state: BlockState) = if (state.getValue(BlockStateProperties.LIT)) 13 else 0
}
