package at.martinthedragon.nucleartech

import at.martinthedragon.nucleartech.RegistriesAndLifecycle.BLOCKS
import at.martinthedragon.nucleartech.blocks.*
import net.minecraft.core.BlockPos
import net.minecraft.util.valueproviders.UniformInt
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.block.*
import net.minecraft.world.level.block.state.BlockBehaviour.Properties
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.material.Material.*
import net.minecraft.world.level.material.MaterialColor
import net.minecraftforge.registries.RegistryObject
import kotlin.random.Random
import at.martinthedragon.nucleartech.hazards.items.HazardItemEffect.Companion as RadValue

object ModBlocks {
    val uraniumOre: RegistryObject<Block> = BLOCKS.register("uranium_ore") { OreBlock(Properties.of(STONE).strength(5F, 10F).requiresCorrectToolForDrops()) }
    val scorchedUraniumOre: RegistryObject<Block> = BLOCKS.register("scorched_uranium_ore") { OreBlock(Properties.of(STONE).strength(5F, 10F).requiresCorrectToolForDrops()) }
    val thoriumOre: RegistryObject<Block> = BLOCKS.register("thorium_ore") { OreBlock(Properties.of(STONE).strength(5F, 10F).requiresCorrectToolForDrops()) }
    val titaniumOre: RegistryObject<Block> = BLOCKS.register("titanium_ore") { OreBlock(Properties.of(STONE).strength(5F, 10F).requiresCorrectToolForDrops()) }
    val sulfurOre: RegistryObject<Block> = BLOCKS.register("sulfur_ore") { OreBlock(Properties.of(STONE).strength(3F, 6F).requiresCorrectToolForDrops(), UniformInt.of(1, 2)) }
    val niterOre: RegistryObject<Block> = BLOCKS.register("niter_ore") { OreBlock(Properties.of(STONE).strength(3F, 6F).requiresCorrectToolForDrops(), UniformInt.of(1, 2)) }
    val tungstenOre: RegistryObject<Block> = BLOCKS.register("tungsten_ore") { OreBlock(Properties.of(STONE).strength(6F, 10F).requiresCorrectToolForDrops()) }
    val aluminiumOre: RegistryObject<Block> = BLOCKS.register("aluminium_ore") { OreBlock(Properties.of(STONE).strength(4F, 8F).requiresCorrectToolForDrops()) }
    val fluoriteOre: RegistryObject<Block> = BLOCKS.register("fluorite_ore") { OreBlock(Properties.of(STONE).strength(3F, 6F).requiresCorrectToolForDrops(), UniformInt.of(2, 3)) }
    val berylliumOre: RegistryObject<Block> = BLOCKS.register("beryllium_ore") { OreBlock(Properties.of(STONE).strength(5F, 15F).requiresCorrectToolForDrops()) }
    val leadOre: RegistryObject<Block> = BLOCKS.register("lead_ore") { OreBlock(Properties.of(STONE).strength(5F, 10F).requiresCorrectToolForDrops()) }
    val oilDeposit: RegistryObject<Block> = BLOCKS.register("oil_deposit") { Block(Properties.of(STONE).strength(1F, 2F).requiresCorrectToolForDrops()) }
    val emptyOilDeposit: RegistryObject<Block> = BLOCKS.register("empty_oil_deposit") { Block(Properties.of(STONE).strength(1F, 1F).requiresCorrectToolForDrops()) }
    val oilSand: RegistryObject<Block> = BLOCKS.register("oil_sand") { Block(Properties.of(SAND).strength(1F).sound(SoundType.SAND)) }
    val ligniteOre: RegistryObject<Block> = BLOCKS.register("lignite_ore") { OreBlock(Properties.of(STONE).strength(3F).requiresCorrectToolForDrops(), UniformInt.of(0, 1)) }
    val asbestosOre: RegistryObject<Block> = BLOCKS.register("asbestos_ore") { OreBlock(Properties.of(STONE).strength(3F).requiresCorrectToolForDrops()) }
    val schrabidiumOre: RegistryObject<Block> = BLOCKS.register("schrabidium_ore") { Block(Properties.of(STONE).strength(20F, 50F).lightLevel { 3 }.hasPostProcess { _, _, _ -> true }.emissiveRendering { _, _, _ -> true }) }
    val australianOre: RegistryObject<Block> = BLOCKS.register("australian_ore") { OreBlock(Properties.of(STONE).strength(6F).requiresCorrectToolForDrops()) }
    val weidite: RegistryObject<Block> = BLOCKS.register("weidite") { OreBlock(Properties.of(STONE).strength(6F).requiresCorrectToolForDrops()) }
    val reiite: RegistryObject<Block> = BLOCKS.register("reiite") { OreBlock(Properties.of(STONE).strength(6F).requiresCorrectToolForDrops()) }
    val brightblendeOre: RegistryObject<Block> = BLOCKS.register("brightblende_ore") { OreBlock(Properties.of(STONE).strength(6F).requiresCorrectToolForDrops()) }
    val dellite: RegistryObject<Block> = BLOCKS.register("dellite") { OreBlock(Properties.of(STONE).strength(6F).requiresCorrectToolForDrops()) }
    val dollarGreenMineral: RegistryObject<Block> = BLOCKS.register("dollar_green_mineral") { OreBlock(Properties.of(STONE).strength(6F).requiresCorrectToolForDrops()) }
    val rareEarthOre: RegistryObject<Block> = BLOCKS.register("rare_earth_ore") { OreBlock(Properties.of(STONE).strength(4F, 3F).requiresCorrectToolForDrops(), UniformInt.of(3, 6)) }
    val cobaltOre: RegistryObject<Block> = BLOCKS.register("cobalt_ore") { OreBlock(Properties.of(STONE).strength(3F).requiresCorrectToolForDrops()) }
    val deepslateUraniumOre: RegistryObject<Block> = BLOCKS.register("deepslate_uranium_ore") { OreBlock(Properties.copy(uraniumOre.get()).color(MaterialColor.DEEPSLATE).strength(7.5F, 10F).sound(SoundType.DEEPSLATE)) }
    val scorchedDeepslateUraniumOre: RegistryObject<Block> = BLOCKS.register("scorched_deepslate_uranium_ore") { OreBlock(Properties.copy(scorchedUraniumOre.get()).color(MaterialColor.DEEPSLATE).strength(7.5F, 10F).sound(SoundType.DEEPSLATE)) }
    val deepslateThoriumOre: RegistryObject<Block> = BLOCKS.register("deepslate_thorium_ore") { OreBlock(Properties.copy(thoriumOre.get()).color(MaterialColor.DEEPSLATE).strength(7.5F, 10F).sound(SoundType.DEEPSLATE)) }
    val deepslateTitaniumOre: RegistryObject<Block> = BLOCKS.register("deepslate_titanium_ore") { OreBlock(Properties.copy(titaniumOre.get()).color(MaterialColor.DEEPSLATE).strength(7.5F, 10F).sound(SoundType.DEEPSLATE)) }
    val deepslateSulfurOre: RegistryObject<Block> = BLOCKS.register("deepslate_sulfur_ore") { OreBlock(Properties.copy(sulfurOre.get()).color(MaterialColor.DEEPSLATE).strength(4.5F, 6F).sound(SoundType.DEEPSLATE), UniformInt.of(1, 2)) }
    val deepslateNiterOre: RegistryObject<Block> = BLOCKS.register("deepslate_niter_ore") { OreBlock(Properties.copy(niterOre.get()).color(MaterialColor.DEEPSLATE).strength(4.5F, 6F).sound(SoundType.DEEPSLATE), UniformInt.of(1, 2)) }
    val deepslateTungstenOre: RegistryObject<Block> = BLOCKS.register("deepslate_tungsten_ore") { OreBlock(Properties.copy(tungstenOre.get()).color(MaterialColor.DEEPSLATE).strength(6F, 10F).sound(SoundType.DEEPSLATE)) }
    val deepslateAluminiumOre: RegistryObject<Block> = BLOCKS.register("deepslate_aluminium_ore") { OreBlock(Properties.copy(aluminiumOre.get()).color(MaterialColor.DEEPSLATE).strength(6F, 8F).sound(SoundType.DEEPSLATE)) }
    val deepslateFluoriteOre: RegistryObject<Block> = BLOCKS.register("deepslate_fluorite_ore") { OreBlock(Properties.copy(fluoriteOre.get()).color(MaterialColor.DEEPSLATE).strength(4.5F, 6F).sound(SoundType.DEEPSLATE), UniformInt.of(2, 3)) }
    val deepslateBerylliumOre: RegistryObject<Block> = BLOCKS.register("deepslate_beryllium_ore") { OreBlock(Properties.copy(berylliumOre.get()).color(MaterialColor.DEEPSLATE).strength(7.5F, 15F).sound(SoundType.DEEPSLATE)) }
    val deepslateLeadOre: RegistryObject<Block> = BLOCKS.register("deepslate_lead_ore") { OreBlock(Properties.copy(leadOre.get()).color(MaterialColor.DEEPSLATE).strength(7.5F, 10F).sound(SoundType.DEEPSLATE)) }
    val deepslateAsbestosOre: RegistryObject<Block> = BLOCKS.register("deepslate_asbestos_ore") { OreBlock(Properties.copy(asbestosOre.get()).color(MaterialColor.DEEPSLATE).strength(4.5F, 3F).sound(SoundType.DEEPSLATE)) }
    val deepslateSchrabidiumOre: RegistryObject<Block> = BLOCKS.register("deepslate_schrabidium_ore") { Block(Properties.copy(schrabidiumOre.get()).color(MaterialColor.DEEPSLATE).strength(30F, 50F).sound(SoundType.DEEPSLATE)) }
    val deepslateAustralianOre: RegistryObject<Block> = BLOCKS.register("deepslate_australian_ore") { OreBlock(Properties.copy(australianOre.get()).color(MaterialColor.DEEPSLATE).strength(9F, 6F).sound(SoundType.DEEPSLATE)) }
    val deepslateRareEarthOre: RegistryObject<Block> = BLOCKS.register("deepslate_rare_earth_ore") { OreBlock(Properties.copy(rareEarthOre.get()).color(MaterialColor.DEEPSLATE).strength(6F, 3F).sound(SoundType.DEEPSLATE), UniformInt.of(3, 6)) }
    val deepslateCobaltOre: RegistryObject<Block> = BLOCKS.register("deepslate_cobalt_ore") { OreBlock(Properties.copy(cobaltOre.get()).color(MaterialColor.DEEPSLATE).strength(4.5F, 3F).sound(SoundType.DEEPSLATE)) }
    val netherUraniumOre: RegistryObject<Block> = BLOCKS.register("nether_uranium_ore") { Block(Properties.of(STONE).strength(3f).requiresCorrectToolForDrops()) }
    val scorchedNetherUraniumOre: RegistryObject<Block> = BLOCKS.register("scorched_nether_uranium_ore") { Block(Properties.of(STONE).strength(3F).requiresCorrectToolForDrops()) }
    val netherPlutoniumOre: RegistryObject<Block> = BLOCKS.register("nether_plutonium_ore") { Block(Properties.of(STONE).strength(3f).requiresCorrectToolForDrops()) }
    val netherTungstenOre: RegistryObject<Block> = BLOCKS.register("nether_tungsten_ore") { Block(Properties.of(STONE).strength(3f, 4f).requiresCorrectToolForDrops()) }
    val netherSulfurOre: RegistryObject<Block> = BLOCKS.register("nether_sulfur_ore") { object : Block(Properties.of(STONE).strength(2f, 3f).requiresCorrectToolForDrops()) {
        override fun getExpDrop(state: BlockState?, world: LevelReader, pos: BlockPos?, fortune: Int, silktouch: Int) = if (silktouch != 0) 0 else Random.nextInt(1, 4)
    }}
    val netherPhosphorusOre: RegistryObject<Block> = BLOCKS.register("nether_phosphorus_ore") { object : Block(Properties.of(STONE).strength(2f, 3f).requiresCorrectToolForDrops()) {
        override fun getExpDrop(state: BlockState?, world: LevelReader, pos: BlockPos?, fortune: Int, silktouch: Int) = if (silktouch != 0) 0 else Random.nextInt(2, 5)
    }}
    val netherSchrabidiumOre: RegistryObject<Block> = BLOCKS.register("nether_schrabidium_ore") { Block(Properties.of(STONE).strength(20f, 50f).requiresCorrectToolForDrops().lightLevel { 7 }.hasPostProcess { _, _, _ -> true }.emissiveRendering { _, _, _ -> true }) }
    val meteorUraniumOre: RegistryObject<Block> = BLOCKS.register("meteor_uranium_ore") { Block(Properties.of(STONE).strength(6f).requiresCorrectToolForDrops()) }
    val meteorThoriumOre: RegistryObject<Block> = BLOCKS.register("meteor_thorium_ore") { Block(Properties.of(STONE).strength(6f).requiresCorrectToolForDrops()) }
    val meteorTitaniumOre: RegistryObject<Block> = BLOCKS.register("meteor_titanium_ore") { Block(Properties.of(STONE).strength(6f).requiresCorrectToolForDrops()) }
    val meteorSulfurOre: RegistryObject<Block> = BLOCKS.register("meteor_sulfur_ore") { object : Block(Properties.of(STONE).strength(5f).requiresCorrectToolForDrops()) {
        override fun getExpDrop(state: BlockState?, world: LevelReader, pos: BlockPos?, fortune: Int, silktouch: Int) = if (silktouch != 0) 0 else Random.nextInt(5, 9)
    }}
    val meteorCopperOre: RegistryObject<Block> = BLOCKS.register("meteor_copper_ore") { Block(Properties.of(STONE).strength(6f).requiresCorrectToolForDrops()) }
    val meteorTungstenOre: RegistryObject<Block> = BLOCKS.register("meteor_tungsten_ore") { Block(Properties.of(STONE).strength(6f).requiresCorrectToolForDrops()) }
    val meteorAluminiumOre: RegistryObject<Block> = BLOCKS.register("meteor_aluminium_ore") { Block(Properties.of(STONE).strength(6f).requiresCorrectToolForDrops()) }
    val meteorLeadOre: RegistryObject<Block> = BLOCKS.register("meteor_lead_ore") { Block(Properties.of(STONE).strength(6f).requiresCorrectToolForDrops()) }
    val meteorLithiumOre: RegistryObject<Block> = BLOCKS.register("meteor_lithium_ore") { Block(Properties.of(STONE).strength(6f).requiresCorrectToolForDrops()) }
    val starmetalOre: RegistryObject<Block> = BLOCKS.register("starmetal_ore") { Block(Properties.of(STONE).strength(6f).requiresCorrectToolForDrops()) }
    val trixite: RegistryObject<Block> = BLOCKS.register("trixite") { Block(Properties.of(STONE).strength(4f, 9f).requiresCorrectToolForDrops()) }

    val uraniumBlock: RegistryObject<Block> = BLOCKS.register("uranium_block") { HazardBlock(Properties.of(METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)).radiation(RadValue.U) }
    val u233Block: RegistryObject<Block> = BLOCKS.register("u233_block") { HazardBlock(Properties.of(METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)).radiation(RadValue.U233) }
    val u235Block: RegistryObject<Block> = BLOCKS.register("u235_block") { HazardBlock(Properties.of(METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)).radiation(RadValue.U235) }
    val u238Block: RegistryObject<Block> = BLOCKS.register("u238_block") { HazardBlock(Properties.of(METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)).radiation(RadValue.U238) }
    val uraniumFuelBlock: RegistryObject<Block> = BLOCKS.register("uranium_fuel_block") { HazardBlock(Properties.of(METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)).radiation(RadValue.UF) }
    val neptuniumBlock: RegistryObject<Block> = BLOCKS.register("neptunium_block") { HazardBlock(Properties.of(METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)).radiation(RadValue.Np237) }
    val moxFuelBlock: RegistryObject<Block> = BLOCKS.register("mox_fuel_block") { HazardBlock(Properties.of(METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)).radiation(RadValue.MOX) }
    val plutoniumBlock: RegistryObject<Block> = BLOCKS.register("plutonium_block") { HazardBlock(Properties.of(METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)).radiation(RadValue.Pu) }
    val pu238Block: RegistryObject<Block> = BLOCKS.register("pu238_block") { HazardBlock(Properties.of(METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)).radiation(RadValue.Pu238) }
    val pu239Block: RegistryObject<Block> = BLOCKS.register("pu239_block") { HazardBlock(Properties.of(METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)).radiation(RadValue.Pu239) }
    val pu240Block: RegistryObject<Block> = BLOCKS.register("pu240_block") { HazardBlock(Properties.of(METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)).radiation(RadValue.Pu240) }
    val plutoniumFuelBlock: RegistryObject<Block> = BLOCKS.register("plutonium_fuel_block") { HazardBlock(Properties.of(METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)).radiation(RadValue.PuF) }
    val thoriumBlock: RegistryObject<Block> = BLOCKS.register("thorium_block") { HazardBlock(Properties.of(METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)).radiation(RadValue.Th232) }
    val thoriumFuelBlock: RegistryObject<Block> = BLOCKS.register("thorium_fuel_block") { HazardBlock(Properties.of(METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)).radiation(RadValue.ThF) }
    val titaniumBlock: RegistryObject<Block> = BLOCKS.register("titanium_block") { Block(Properties.of(METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val sulfurBlock: RegistryObject<Block> = BLOCKS.register("sulfur_block") { Block(Properties.of(METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val niterBlock: RegistryObject<Block> = BLOCKS.register("niter_block") { Block(Properties.of(METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val copperBlock: RegistryObject<Block> = BLOCKS.register("copper_block") { Block(Properties.of(METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val redCopperBlock: RegistryObject<Block> = BLOCKS.register("red_copper_block") { Block(Properties.of(METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val advancedAlloyBlock: RegistryObject<Block> = BLOCKS.register("advanced_alloy_block") { Block(Properties.of(METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val tungstenBlock: RegistryObject<Block> = BLOCKS.register("tungsten_block") { Block(Properties.of(METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val aluminiumBlock: RegistryObject<Block> = BLOCKS.register("aluminium_block") { Block(Properties.of(METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val fluoriteBlock: RegistryObject<Block> = BLOCKS.register("fluorite_block") { Block(Properties.of(METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val berylliumBlock: RegistryObject<Block> = BLOCKS.register("beryllium_block") { Block(Properties.of(METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val cobaltBlock: RegistryObject<Block> = BLOCKS.register("cobalt_block") { Block(Properties.of(METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val steelBlock: RegistryObject<Block> = BLOCKS.register("steel_block") { Block(Properties.of(METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val leadBlock: RegistryObject<Block> = BLOCKS.register("lead_block") { Block(Properties.of(METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val lithiumBlock: RegistryObject<Block> = BLOCKS.register("lithium_block") { Block(Properties.of(METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val whitePhosphorusBlock: RegistryObject<Block> = BLOCKS.register("white_phosphorus_block") { Block(Properties.of(METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val redPhosphorusBlock: RegistryObject<Block> = BLOCKS.register("red_phosphorus_block") { Block(Properties.of(METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val yellowcakeBlock: RegistryObject<Block> = BLOCKS.register("yellowcake_block") { Block(Properties.of(CLAY).strength(6f).sound(SoundType.SAND)) }
    val scrapBlock: RegistryObject<Block> = BLOCKS.register("scrap_block") { Block(Properties.of(CLAY).strength(1f).sound(SoundType.GRAVEL)) }
    val electricalScrapBlock: RegistryObject<Block> = BLOCKS.register("electrical_scrap_block") { Block(Properties.of(METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val insulatorRoll: RegistryObject<RotatedPillarBlock> = BLOCKS.register("insulator_roll") { RotatedPillarBlock(Properties.of(METAL).strength(6f).sound(SoundType.METAL)) }
    val fiberglassRoll: RegistryObject<RotatedPillarBlock> = BLOCKS.register("fiberglass_roll") { RotatedPillarBlock(Properties.of(METAL).strength(6f).sound(SoundType.METAL)) }
    val asbestosBlock: RegistryObject<Block> = BLOCKS.register("asbestos_block") { Block(Properties.of(METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val trinititeBlock: RegistryObject<Block> = BLOCKS.register("trinitite_block") { HazardBlock(Properties.of(METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)).radiation(RadValue.Trinitite) }
    val nuclearWasteBlock: RegistryObject<Block> = BLOCKS.register("nuclear_waste_block") { HazardBlock(Properties.of(METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)).radiation(RadValue.Waste) }
    val schrabidiumBlock: RegistryObject<Block> = BLOCKS.register("schrabidium_block") { HazardBlock(Properties.of(METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)).radiation(RadValue.Sa326) }
    val soliniumBlock: RegistryObject<Block> = BLOCKS.register("solinium_block") { HazardBlock(Properties.of(METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)).radiation(RadValue.Sa327) }
    val schrabidiumFuelBlock: RegistryObject<Block> = BLOCKS.register("schrabidium_fuel_block") { HazardBlock(Properties.of(METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)).radiation(RadValue.SaF) }
    val euphemiumBlock: RegistryObject<Block> = BLOCKS.register("euphemium_block") { Block(Properties.of(METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val schrabidiumCluster: RegistryObject<Block> = BLOCKS.register("schrabidium_cluster") { Block(Properties.of(METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val euphemiumEtchedSchrabidiumCluster: RegistryObject<Block> = BLOCKS.register("euphemium_etched_schrabidium_cluster") { Block(Properties.of(METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val magnetizedTungstenBlock: RegistryObject<Block> = BLOCKS.register("magnetized_tungsten_block") { Block(Properties.of(METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val combineSteelBlock: RegistryObject<Block> = BLOCKS.register("combine_steel_block") { Block(Properties.of(METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val deshReinforcedBlock: RegistryObject<Block> = BLOCKS.register("desh_reinforced_block") { Block(Properties.of(METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val starmetalBlock: RegistryObject<Block> = BLOCKS.register("starmetal_block") { Block(Properties.of(METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val australiumBlock: RegistryObject<Block> = BLOCKS.register("australium_block") { Block(Properties.of(METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val weidaniumBlock: RegistryObject<Block> = BLOCKS.register("weidanium_block") { Block(Properties.of(METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val reiiumBlock: RegistryObject<Block> = BLOCKS.register("reiium_block") { Block(Properties.of(METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val unobtainiumBlock: RegistryObject<Block> = BLOCKS.register("unobtainium_block") { Block(Properties.of(METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val daffergonBlock: RegistryObject<Block> = BLOCKS.register("daffergon_block") { Block(Properties.of(METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val verticiumBlock: RegistryObject<Block> = BLOCKS.register("verticium_block") { Block(Properties.of(METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val titaniumDecoBlock: RegistryObject<Block> = BLOCKS.register("titanium_deco_block") { Block(Properties.of(METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val redCopperDecoBlock: RegistryObject<Block> = BLOCKS.register("red_copper_deco_block") { Block(Properties.of(METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val tungstenDecoBlock: RegistryObject<Block> = BLOCKS.register("tungsten_deco_block") { Block(Properties.of(METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val aluminiumDecoBlock: RegistryObject<Block> = BLOCKS.register("aluminium_deco_block") { Block(Properties.of(METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val steelDecoBlock: RegistryObject<Block> = BLOCKS.register("steel_deco_block") { Block(Properties.of(METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val leadDecoBlock: RegistryObject<Block> = BLOCKS.register("lead_deco_block") { Block(Properties.of(METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val berylliumDecoBlock: RegistryObject<Block> = BLOCKS.register("beryllium_deco_block") { Block(Properties.of(METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val asbestosRoof: RegistryObject<Block> = BLOCKS.register("asbestos_roof") { Block(Properties.of(METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val hazmatBlock: RegistryObject<Block> = BLOCKS.register("hazmat_block") { Block(Properties.of(WOOL).strength(6f).sound(SoundType.WOOL)) }

    val glowingMushroom: RegistryObject<GlowingMushroom> = BLOCKS.register("glowing_mushroom") { GlowingMushroom(Properties.of(PLANT, MaterialColor.COLOR_GREEN).noCollission().randomTicks().instabreak().lightLevel { 7 }.hasPostProcess { _, _, _, -> true }.sound(SoundType.GRASS)) }
    val glowingMushroomBlock: RegistryObject<HugeMushroomBlock> = BLOCKS.register("glowing_mushroom_block") { HugeMushroomBlock(Properties.of(WOOD, MaterialColor.COLOR_GREEN).strength(.2F).lightLevel { 15 }.hasPostProcess { _, _, _ -> true }.sound(SoundType.WOOD)) }
    val glowingMushroomStem: RegistryObject<HugeMushroomBlock> = BLOCKS.register("glowing_mushroom_stem") { HugeMushroomBlock(Properties.of(WOOD, MaterialColor.COLOR_GREEN).strength(.2F).lightLevel { 12 }.hasPostProcess { _, _, _ -> true }.sound(SoundType.WOOD)) }
    val deadGrass: RegistryObject<DeadGrass> = BLOCKS.register("dead_grass") { DeadGrass(Properties.of(GRASS, MaterialColor.COLOR_BROWN).strength(.6F).randomTicks().sound(SoundType.GRASS)) }
    val glowingMycelium: RegistryObject<GlowingMycelium> = BLOCKS.register("glowing_mycelium") { GlowingMycelium(Properties.of(GRASS, MaterialColor.COLOR_LIGHT_GREEN).strength(.6F).randomTicks().lightLevel { 15 }.hasPostProcess { _, _, _, -> true }.sound(SoundType.GRASS)) }
    val trinitite: RegistryObject<Trinitite> = BLOCKS.register("trinitite_ore") { Trinitite(Properties.of(SAND, MaterialColor.COLOR_GREEN).strength(.5F, 2.5F).sound(SoundType.SAND)) }
    val redTrinitite: RegistryObject<Trinitite> = BLOCKS.register("red_trinitite_ore") { Trinitite(Properties.of(SAND, MaterialColor.COLOR_GREEN).strength(.5F, 2.5F).sound(SoundType.SAND)) }
    val charredLog: RegistryObject<RotatedPillarBlock> = BLOCKS.register("charred_log") { RotatedPillarBlock(Properties.of(WOOD, MaterialColor.COLOR_BLACK).strength(5F, 2.5F).sound(SoundType.WOOD)) }
    val charredPlanks: RegistryObject<Block> = BLOCKS.register("charred_planks") { Block(Properties.of(WOOD, MaterialColor.COLOR_BLACK).strength(.5F, 2.5F).sound(SoundType.WOOD)) }

    val slakedSellafite: RegistryObject<Block> = BLOCKS.register("slaked_sellafite") { Block(Properties.of(STONE, MaterialColor.COLOR_GRAY).strength(5F).requiresCorrectToolForDrops().sound(SoundType.STONE)) }
    val sellafite: RegistryObject<Block> = BLOCKS.register("sellafite") { HazardBlock(Properties.of(STONE, MaterialColor.COLOR_GREEN).strength(5F).requiresCorrectToolForDrops().sound(SoundType.STONE)).radiation(.05F) }
    val hotSellafite: RegistryObject<Block> = BLOCKS.register("hot_sellafite") { HazardBlock(Properties.of(STONE, MaterialColor.COLOR_GREEN).strength(5F).requiresCorrectToolForDrops().sound(SoundType.STONE)).radiation(.1F) }
    val boilingSellafite: RegistryObject<Block> = BLOCKS.register("boiling_sellafite") { HazardBlock(Properties.of(STONE, MaterialColor.COLOR_GREEN).strength(5F).requiresCorrectToolForDrops().sound(SoundType.STONE)).radiation(.25F) }
    val blazingSellafite: RegistryObject<Block> = BLOCKS.register("blazing_sellafite") { HazardBlock(Properties.of(STONE, MaterialColor.COLOR_LIGHT_GREEN).strength(5F).requiresCorrectToolForDrops().sound(SoundType.STONE)).radiation(.4F) }
    val infernalSellafite: RegistryObject<Block> = BLOCKS.register("infernal_sellafite") { HazardBlock(Properties.of(STONE, MaterialColor.COLOR_LIGHT_GREEN).strength(5F).requiresCorrectToolForDrops().sound(SoundType.STONE)).radiation(.6F) }
    val sellafiteCorium: RegistryObject<Block> = BLOCKS.register("sellafite_corium") { HazardBlock(Properties.of(STONE, MaterialColor.COLOR_LIGHT_GREEN).strength(10F).requiresCorrectToolForDrops().sound(SoundType.STONE)).radiation(1F) }

    // Machines

    val siren: RegistryObject<Block> = BLOCKS.register("siren") { Siren(Properties.of(METAL).strength(5f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val safe: RegistryObject<Block> = BLOCKS.register("safe") { Safe(Properties.of(METAL).strength(25f, 1200f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }

    val steamPressBase: RegistryObject<Block> = BLOCKS.register("steam_press_base") { SteamPressBase(Properties.of(METAL).strength(5f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val steamPressFrame: RegistryObject<Block> = BLOCKS.register("steam_press_frame") { SteamPressFrame(Properties.of(METAL).strength(5f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val steamPressTop: RegistryObject<Block> = BLOCKS.register("steam_press_top") { SteamPressTop(Properties.of(METAL).strength(5f).requiresCorrectToolForDrops().sound(SoundType.METAL)) }

    val blastFurnace: RegistryObject<BlastFurnace> = BLOCKS.register("blast_furnace") { BlastFurnace(Properties.of(METAL).strength(5F).requiresCorrectToolForDrops().lightLevel(this::getLightLevelLit13).sound(SoundType.METAL)) }
    val combustionGenerator: RegistryObject<CombustionGenerator> = BLOCKS.register("combustion_generator") { CombustionGenerator(Properties.of(METAL).strength(5F).requiresCorrectToolForDrops().lightLevel(this::getLightLevelLit13).sound(SoundType.METAL)) }
    val electricFurnace: RegistryObject<ElectricFurnace> = BLOCKS.register("electric_furnace") { ElectricFurnace(Properties.of(METAL).strength(5F).requiresCorrectToolForDrops().lightLevel(this::getLightLevelLit13).sound(SoundType.METAL)) }
    val shredder: RegistryObject<Shredder> = BLOCKS.register("shredder") { Shredder(Properties.of(METAL).strength(5F).requiresCorrectToolForDrops().sound(SoundType.METAL)) }

    val ironAnvil: RegistryObject<Anvil> = BLOCKS.register("iron_anvil") { Anvil(1, Properties.of(METAL).strength(5F, 100F).requiresCorrectToolForDrops().sound(SoundType.ANVIL)) }
    val leadAnvil: RegistryObject<Anvil> = BLOCKS.register("lead_anvil") { Anvil(1, Properties.of(METAL).strength(5F, 100F).requiresCorrectToolForDrops().sound(SoundType.ANVIL)) }
    val steelAnvil: RegistryObject<Anvil> = BLOCKS.register("steel_anvil") { Anvil(2, Properties.of(METAL).strength(5F, 100F).requiresCorrectToolForDrops().sound(SoundType.ANVIL)) }
    val meteoriteAnvil: RegistryObject<Anvil> = BLOCKS.register("meteorite_anvil") { Anvil(3, Properties.of(METAL).strength(5F, 100F).requiresCorrectToolForDrops().sound(SoundType.ANVIL)) }
    val starmetalAnvil: RegistryObject<Anvil> = BLOCKS.register("starmetal_anvil") { Anvil(3, Properties.of(METAL).strength(5F, 100F).requiresCorrectToolForDrops().sound(SoundType.ANVIL)) }
    val ferrouraniumAnvil: RegistryObject<Anvil> = BLOCKS.register("ferrouranium_anvil") { Anvil(4, Properties.of(METAL).strength(5F, 100F).requiresCorrectToolForDrops().sound(SoundType.ANVIL)) }
    val bismuthAnvil: RegistryObject<Anvil> = BLOCKS.register("bismuth_anvil") { Anvil(5, Properties.of(METAL).strength(5F, 100F).requiresCorrectToolForDrops().sound(SoundType.ANVIL)) }
    val schrabidateAnvil: RegistryObject<Anvil> = BLOCKS.register("schrabidate_anvil") { Anvil(6, Properties.of(METAL).strength(5F, 100F).requiresCorrectToolForDrops().sound(SoundType.ANVIL)) }
    val dineutroniumAnvil: RegistryObject<Anvil> = BLOCKS.register("dineutronium_anvil") { Anvil(7, Properties.of(METAL).strength(5F, 100F).requiresCorrectToolForDrops().sound(SoundType.ANVIL)) }
    val murkyAnvil: RegistryObject<Anvil> = BLOCKS.register("murky_anvil") { Anvil(1916169, Properties.of(METAL).strength(5F, 100F).requiresCorrectToolForDrops().sound(SoundType.ANVIL)) }

    // Bombs

    val littleBoy: RegistryObject<LittleBoy> = BLOCKS.register("little_boy") { LittleBoy(Properties.of(METAL).strength(5F, 6000F).requiresCorrectToolForDrops().sound(SoundType.METAL).noOcclusion()) }
    val fatMan: RegistryObject<FatMan> = BLOCKS.register("fat_man") { FatMan(Properties.of(METAL).strength(5F, 6000F).requiresCorrectToolForDrops().sound(SoundType.METAL).noOcclusion()) }

    private fun getLightLevelLit13(state: BlockState) = if (state.getValue(BlockStateProperties.LIT)) 13 else 0
}
