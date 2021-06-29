package at.martinthedragon.nucleartech

import at.martinthedragon.nucleartech.RegistriesAndLifecycle.BLOCKS
import at.martinthedragon.nucleartech.blocks.*
import net.minecraft.block.*
import net.minecraft.block.AbstractBlock.Properties
import net.minecraft.block.material.Material.*
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IBlockReader
import net.minecraft.world.IWorldReader
import net.minecraftforge.common.ToolType
import net.minecraftforge.fml.RegistryObject
import kotlin.random.Random

object ModBlocks {
    val uraniumOre: RegistryObject<Block> = BLOCKS.register("uranium_ore") { Block(Properties.of(STONE).strength(3f).harvestLevel(2).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops()) }
    val thoriumOre: RegistryObject<Block> = BLOCKS.register("thorium_ore") { Block(Properties.of(STONE).strength(3f).harvestLevel(2).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops()) }
    val titaniumOre: RegistryObject<Block> = BLOCKS.register("titanium_ore") { Block(Properties.of(STONE).strength(3f).harvestLevel(2).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops()) }
    val sulfurOre: RegistryObject<Block> = BLOCKS.register("sulfur_ore") { object : Block(Properties.of(STONE).strength(2f).harvestLevel(1).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops()) {
        override fun getExpDrop(state: BlockState?, world: IWorldReader?, pos: BlockPos?, fortune: Int, silktouch: Int) = if (silktouch != 0) 0 else Random.nextInt(1, 3)
    }}
    val niterOre: RegistryObject<Block> = BLOCKS.register("niter_ore") { object : Block(Properties.of(STONE).strength(2f).harvestLevel(1).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops()){
        override fun getExpDrop(state: BlockState?, world: IWorldReader?, pos: BlockPos?, fortune: Int, silktouch: Int) = if (silktouch != 0) 0 else Random.nextInt(2, 4)
    }}
    val copperOre: RegistryObject<Block> = BLOCKS.register("copper_ore") { Block(Properties.of(STONE).strength(3f).harvestLevel(1).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops()) }
    val tungstenOre: RegistryObject<Block> = BLOCKS.register("tungsten_ore") { Block(Properties.of(STONE).strength(3f, 4f).harvestLevel(2).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops()) }
    val aluminiumOre: RegistryObject<Block> = BLOCKS.register("aluminium_ore") { Block(Properties.of(STONE).strength(2f, 3f).harvestLevel(1).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops()) }
    val fluoriteOre: RegistryObject<Block> = BLOCKS.register("fluorite_ore") { object : Block(Properties.of(STONE).strength(2f).harvestLevel(1).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops()) {
        override fun getExpDrop(state: BlockState?, world: IWorldReader?, pos: BlockPos?, fortune: Int, silktouch: Int) = if (silktouch != 0) 0 else Random.nextInt(2, 4)
    }}
    val berylliumOre: RegistryObject<Block> = BLOCKS.register("beryllium_ore") { Block(Properties.of(STONE).strength(3f).harvestLevel(2).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops()) }
    val leadOre: RegistryObject<Block> = BLOCKS.register("lead_ore") { Block(Properties.of(STONE).strength(4f, 6f).harvestLevel(2).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops()) }
    val oilDeposit: RegistryObject<Block> = BLOCKS.register("oil_deposit") { Block(Properties.of(STONE).strength(1f, 2f).harvestLevel(0).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops()) }
    val emptyOilDeposit: RegistryObject<Block> = BLOCKS.register("empty_oil_deposit") { Block(Properties.of(STONE).strength(1f, 2f).harvestLevel(0).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops()) }
    val oilSand: RegistryObject<Block> = BLOCKS.register("oil_sand") { Block(Properties.of(SAND).strength(1f).harvestLevel(-1).harvestTool(ToolType.SHOVEL).sound(SoundType.SAND)) }
    val ligniteOre: RegistryObject<Block> = BLOCKS.register("lignite_ore") { object : Block(Properties.of(STONE).strength(3f).harvestLevel(0).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops()) {
        override fun getExpDrop(state: BlockState?, world: IWorldReader?, pos: BlockPos?, fortune: Int, silktouch: Int) = if (silktouch != 0) 0 else Random.nextInt(0, 2)
    }}
    val asbestosOre: RegistryObject<Block> = BLOCKS.register("asbestos_ore") { Block(Properties.of(STONE).strength(3f).harvestLevel(2).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops()) }
    val schrabidiumOre: RegistryObject<Block> = BLOCKS.register("schrabidium_ore") { object : Block(Properties.of(STONE).strength(20f, 50f).harvestLevel(4).hasPostProcess { _, _, _ -> true }.emissiveRendering { _, _, _ -> true }) {
        override fun getLightValue(state: BlockState?, world: IBlockReader?, pos: BlockPos?) = 7
    }}
    val australianOre: RegistryObject<Block> = BLOCKS.register("australian_ore") { Block(Properties.of(STONE).strength(6f).harvestLevel(2).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops()) }
    val weidite: RegistryObject<Block> = BLOCKS.register("weidite") { Block(Properties.of(STONE).strength(6f).harvestLevel(2).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops()) }
    val reiite: RegistryObject<Block> = BLOCKS.register("reiite") { Block(Properties.of(STONE).strength(6f).harvestLevel(2).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops()) }
    val brightblendeOre: RegistryObject<Block> = BLOCKS.register("brightblende_ore") { Block(Properties.of(STONE).strength(6f).harvestLevel(2).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops()) }
    val dellite: RegistryObject<Block> = BLOCKS.register("dellite") { Block(Properties.of(STONE).strength(6f).harvestLevel(2).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops()) }
    val dollarGreenMineral: RegistryObject<Block> = BLOCKS.register("dollar_green_mineral") { Block(Properties.of(STONE).strength(6f).harvestLevel(2).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops()) }
    val rareEarthOre: RegistryObject<Block> = BLOCKS.register("rare_earth_ore") { object : Block(Properties.of(STONE).strength(4f, 3f).harvestLevel(2).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops()){
        override fun getExpDrop(state: BlockState?, world: IWorldReader?, pos: BlockPos?, fortune: Int, silktouch: Int) = if (silktouch != 0) 0 else Random.nextInt(3, 7)
    }}
    val netherUraniumOre: RegistryObject<Block> = BLOCKS.register("nether_uranium_ore") { Block(Properties.of(STONE).strength(3f).harvestLevel(2).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops()) }
    val netherPlutoniumOre: RegistryObject<Block> = BLOCKS.register("nether_plutonium_ore") { Block(Properties.of(STONE).strength(3f).harvestLevel(2).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops()) }
    val netherTungstenOre: RegistryObject<Block> = BLOCKS.register("nether_tungsten_ore") { Block(Properties.of(STONE).strength(3f, 4f).harvestLevel(2).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops()) }
    val netherSulfurOre: RegistryObject<Block> = BLOCKS.register("nether_sulfur_ore") { object : Block(Properties.of(STONE).strength(2f, 3f).harvestLevel(1).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops()) {
        override fun getExpDrop(state: BlockState?, world: IWorldReader?, pos: BlockPos?, fortune: Int, silktouch: Int) = if (silktouch != 0) 0 else Random.nextInt(1, 4)
    }}
    val netherPhosphorusOre: RegistryObject<Block> = BLOCKS.register("nether_phosphorus_ore") { object : Block(Properties.of(STONE).strength(2f, 3f).harvestLevel(1).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops()) {
        override fun getExpDrop(state: BlockState?, world: IWorldReader?, pos: BlockPos?, fortune: Int, silktouch: Int) = if (silktouch != 0) 0 else Random.nextInt(2, 5)
    }}
    val netherSchrabidiumOre: RegistryObject<Block> = BLOCKS.register("nether_schrabidium_ore") { object : Block(Properties.of(STONE).strength(20f, 50f).harvestLevel(4).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().hasPostProcess { _, _, _ -> true }.emissiveRendering { _, _, _ -> true }) {
        override fun getLightValue(state: BlockState?, world: IBlockReader?, pos: BlockPos?) = 7
    }}
    val meteorUraniumOre: RegistryObject<Block> = BLOCKS.register("meteor_uranium_ore") { Block(Properties.of(STONE).strength(6f).harvestLevel(3).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops()) }
    val meteorThoriumOre: RegistryObject<Block> = BLOCKS.register("meteor_thorium_ore") { Block(Properties.of(STONE).strength(6f).harvestLevel(3).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops()) }
    val meteorTitaniumOre: RegistryObject<Block> = BLOCKS.register("meteor_titanium_ore") { Block(Properties.of(STONE).strength(6f).harvestLevel(3).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops()) }
    val meteorSulfurOre: RegistryObject<Block> = BLOCKS.register("meteor_sulfur_ore") { object : Block(Properties.of(STONE).strength(5f).harvestLevel(3).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops()) {
        override fun getExpDrop(state: BlockState?, world: IWorldReader?, pos: BlockPos?, fortune: Int, silktouch: Int) = if (silktouch != 0) 0 else Random.nextInt(5, 9)
    }}
    val meteorCopperOre: RegistryObject<Block> = BLOCKS.register("meteor_copper_ore") { Block(Properties.of(STONE).strength(6f).harvestLevel(3).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops()) }
    val meteorTungstenOre: RegistryObject<Block> = BLOCKS.register("meteor_tungsten_ore") { Block(Properties.of(STONE).strength(6f).harvestLevel(3).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops()) }
    val meteorAluminiumOre: RegistryObject<Block> = BLOCKS.register("meteor_aluminium_ore") { Block(Properties.of(STONE).strength(6f).harvestLevel(3).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops()) }
    val meteorLeadOre: RegistryObject<Block> = BLOCKS.register("meteor_lead_ore") { Block(Properties.of(STONE).strength(6f).harvestLevel(3).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops()) }
    val meteorLithiumOre: RegistryObject<Block> = BLOCKS.register("meteor_lithium_ore") { Block(Properties.of(STONE).strength(6f).harvestLevel(3).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops()) }
    val starmetalOre: RegistryObject<Block> = BLOCKS.register("starmetal_ore") { Block(Properties.of(STONE).strength(6f).harvestLevel(3).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops()) }
    val trixite: RegistryObject<Block> = BLOCKS.register("trixite") { Block(Properties.of(STONE).strength(4f, 9f).harvestLevel(2).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops()) }

    val uraniumBlock: RegistryObject<Block> = BLOCKS.register("uranium_block") { Block(Properties.of(METAL).strength(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val u233Block: RegistryObject<Block> = BLOCKS.register("u233_block") { Block(Properties.of(METAL).strength(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val u235Block: RegistryObject<Block> = BLOCKS.register("u235_block") { Block(Properties.of(METAL).strength(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val u238Block: RegistryObject<Block> = BLOCKS.register("u238_block") { Block(Properties.of(METAL).strength(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val uraniumFuelBlock: RegistryObject<Block> = BLOCKS.register("uranium_fuel_block") { Block(Properties.of(METAL).strength(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val neptuniumBlock: RegistryObject<Block> = BLOCKS.register("neptunium_block") { Block(Properties.of(METAL).strength(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val moxFuelBlock: RegistryObject<Block> = BLOCKS.register("mox_fuel_block") { Block(Properties.of(METAL).strength(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val plutoniumBlock: RegistryObject<Block> = BLOCKS.register("plutonium_block") { Block(Properties.of(METAL).strength(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val pu238Block: RegistryObject<Block> = BLOCKS.register("pu238_block") { Block(Properties.of(METAL).strength(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val pu239Block: RegistryObject<Block> = BLOCKS.register("pu239_block") { Block(Properties.of(METAL).strength(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val pu240Block: RegistryObject<Block> = BLOCKS.register("pu240_block") { Block(Properties.of(METAL).strength(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val plutoniumFuelBlock: RegistryObject<Block> = BLOCKS.register("plutonium_fuel_block") { Block(Properties.of(METAL).strength(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val thoriumBlock: RegistryObject<Block> = BLOCKS.register("thorium_block") { Block(Properties.of(METAL).strength(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val thoriumFuelBlock: RegistryObject<Block> = BLOCKS.register("thorium_fuel_block") { Block(Properties.of(METAL).strength(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val titaniumBlock: RegistryObject<Block> = BLOCKS.register("titanium_block") { Block(Properties.of(METAL).strength(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val sulfurBlock: RegistryObject<Block> = BLOCKS.register("sulfur_block") { Block(Properties.of(METAL).strength(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val niterBlock: RegistryObject<Block> = BLOCKS.register("niter_block") { Block(Properties.of(METAL).strength(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val copperBlock: RegistryObject<Block> = BLOCKS.register("copper_block") { Block(Properties.of(METAL).strength(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val redCopperBlock: RegistryObject<Block> = BLOCKS.register("red_copper_block") { Block(Properties.of(METAL).strength(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val advancedAlloyBlock: RegistryObject<Block> = BLOCKS.register("advanced_alloy_block") { Block(Properties.of(METAL).strength(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val tungstenBlock: RegistryObject<Block> = BLOCKS.register("tungsten_block") { Block(Properties.of(METAL).strength(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val aluminiumBlock: RegistryObject<Block> = BLOCKS.register("aluminium_block") { Block(Properties.of(METAL).strength(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val fluoriteBlock: RegistryObject<Block> = BLOCKS.register("fluorite_block") { Block(Properties.of(METAL).strength(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val berylliumBlock: RegistryObject<Block> = BLOCKS.register("beryllium_block") { Block(Properties.of(METAL).strength(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val cobaltBlock: RegistryObject<Block> = BLOCKS.register("cobalt_block") { Block(Properties.of(METAL).strength(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val steelBlock: RegistryObject<Block> = BLOCKS.register("steel_block") { Block(Properties.of(METAL).strength(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val leadBlock: RegistryObject<Block> = BLOCKS.register("lead_block") { Block(Properties.of(METAL).strength(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val lithiumBlock: RegistryObject<Block> = BLOCKS.register("lithium_block") { Block(Properties.of(METAL).strength(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val whitePhosphorusBlock: RegistryObject<Block> = BLOCKS.register("white_phosphorus_block") { Block(Properties.of(METAL).strength(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val redPhosphorusBlock: RegistryObject<Block> = BLOCKS.register("red_phosphorus_block") { Block(Properties.of(METAL).strength(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val yellowcakeBlock: RegistryObject<Block> = BLOCKS.register("yellowcake_block") { Block(Properties.of(CLAY).strength(6f).harvestLevel(1).harvestTool(ToolType.SHOVEL).sound(SoundType.SAND)) }
    val scrapBlock: RegistryObject<Block> = BLOCKS.register("scrap_block") { Block(Properties.of(CLAY).strength(1f).harvestLevel(-1).harvestTool(ToolType.SHOVEL).sound(SoundType.GRAVEL)) }
    val electricalScrapBlock: RegistryObject<Block> = BLOCKS.register("electrical_scrap_block") { Block(Properties.of(METAL).strength(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val insulatorRoll: RegistryObject<RotatedPillarBlock> = BLOCKS.register("insulator_roll") { RotatedPillarBlock(Properties.of(METAL).strength(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).sound(SoundType.METAL)) }
    val fiberglassRoll: RegistryObject<RotatedPillarBlock> = BLOCKS.register("fiberglass_roll") { RotatedPillarBlock(Properties.of(METAL).strength(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).sound(SoundType.METAL)) }
    val asbestosBlock: RegistryObject<Block> = BLOCKS.register("asbestos_block") { Block(Properties.of(METAL).strength(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val trinititeBlock: RegistryObject<Block> = BLOCKS.register("trinitite_block") { Block(Properties.of(METAL).strength(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val nuclearWasteBlock: RegistryObject<Block> = BLOCKS.register("nuclear_waste_block") { Block(Properties.of(METAL).strength(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val schrabidiumBlock: RegistryObject<Block> = BLOCKS.register("schrabidium_block") { Block(Properties.of(METAL).strength(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val soliniumBlock: RegistryObject<Block> = BLOCKS.register("solinium_block") { Block(Properties.of(METAL).strength(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val schrabidiumFuelBlock: RegistryObject<Block> = BLOCKS.register("schrabidium_fuel_block") { Block(Properties.of(METAL).strength(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val euphemiumBlock: RegistryObject<Block> = BLOCKS.register("euphemium_block") { Block(Properties.of(METAL).strength(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val schrabidiumCluster: RegistryObject<Block> = BLOCKS.register("schrabidium_cluster") { Block(Properties.of(METAL).strength(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val euphemiumEtchedSchrabidiumCluster: RegistryObject<Block> = BLOCKS.register("euphemium_etched_schrabidium_cluster") { Block(Properties.of(METAL).strength(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val magnetizedTungstenBlock: RegistryObject<Block> = BLOCKS.register("magnetized_tungsten_block") { Block(Properties.of(METAL).strength(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val combineSteelBlock: RegistryObject<Block> = BLOCKS.register("combine_steel_block") { Block(Properties.of(METAL).strength(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val deshReinforcedBlock: RegistryObject<Block> = BLOCKS.register("desh_reinforced_block") { Block(Properties.of(METAL).strength(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val starmetalBlock: RegistryObject<Block> = BLOCKS.register("starmetal_block") { Block(Properties.of(METAL).strength(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val australiumBlock: RegistryObject<Block> = BLOCKS.register("australium_block") { Block(Properties.of(METAL).strength(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val weidaniumBlock: RegistryObject<Block> = BLOCKS.register("weidanium_block") { Block(Properties.of(METAL).strength(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val reiiumBlock: RegistryObject<Block> = BLOCKS.register("reiium_block") { Block(Properties.of(METAL).strength(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val unobtainiumBlock: RegistryObject<Block> = BLOCKS.register("unobtainium_block") { Block(Properties.of(METAL).strength(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val daffergonBlock: RegistryObject<Block> = BLOCKS.register("daffergon_block") { Block(Properties.of(METAL).strength(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val verticiumBlock: RegistryObject<Block> = BLOCKS.register("verticium_block") { Block(Properties.of(METAL).strength(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val titaniumDecoBlock: RegistryObject<Block> = BLOCKS.register("titanium_deco_block") { Block(Properties.of(METAL).strength(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val redCopperDecoBlock: RegistryObject<Block> = BLOCKS.register("red_copper_deco_block") { Block(Properties.of(METAL).strength(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val tungstenDecoBlock: RegistryObject<Block> = BLOCKS.register("tungsten_deco_block") { Block(Properties.of(METAL).strength(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val aluminiumDecoBlock: RegistryObject<Block> = BLOCKS.register("aluminium_deco_block") { Block(Properties.of(METAL).strength(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val steelDecoBlock: RegistryObject<Block> = BLOCKS.register("steel_deco_block") { Block(Properties.of(METAL).strength(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val leadDecoBlock: RegistryObject<Block> = BLOCKS.register("lead_deco_block") { Block(Properties.of(METAL).strength(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val berylliumDecoBlock: RegistryObject<Block> = BLOCKS.register("beryllium_deco_block") { Block(Properties.of(METAL).strength(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val asbestosRoof: RegistryObject<Block> = BLOCKS.register("asbestos_roof") { Block(Properties.of(METAL).strength(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val hazmatBlock: RegistryObject<Block> = BLOCKS.register("hazmat_block") { Block(Properties.of(WOOL).strength(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).sound(SoundType.WOOL)) }

    val siren: RegistryObject<Block> = BLOCKS.register("siren") { Siren(Properties.of(METAL).strength(5f).harvestLevel(1).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val safe: RegistryObject<Block> = BLOCKS.register("safe") { Safe(Properties.of(METAL).strength(25f, 1200f).harvestLevel(2).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.METAL)) }

    // region Steam Press
    val steamPressBase: RegistryObject<Block> = BLOCKS.register("steam_press_base") { SteamPressBase(Properties.of(METAL).strength(5f).harvestLevel(1).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val steamPressFrame: RegistryObject<Block> = BLOCKS.register("steam_press_frame") { SteamPressFrame(Properties.of(METAL).strength(5f).harvestLevel(1).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    val steamPressTop: RegistryObject<Block> = BLOCKS.register("steam_press_top") { SteamPressTop(Properties.of(METAL).strength(5f).harvestLevel(1).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
    // endregion

    val blastFurnace: RegistryObject<BlastFurnace> = BLOCKS.register("blast_furnace") { BlastFurnace(Properties.of(METAL).strength(5F).harvestLevel(1).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().sound(SoundType.METAL)) }
}
