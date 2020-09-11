package at.martinthedragon.ntm

import at.martinthedragon.ntm.RegistriesAndLifecycle.register
import at.martinthedragon.ntm.blocks.*
import net.minecraft.block.AbstractBlock.Properties
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.SoundType
import net.minecraft.block.material.Material.*
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IBlockReader
import net.minecraft.world.IWorldReader
import net.minecraftforge.common.ToolType
import kotlin.random.Random

object ModBlocks { // anyone from the forge team will not like this
    // func_235861_h_() = setRequiresTool()
    val uraniumOre: Block = Block(Properties.create(ROCK).hardnessAndResistance(3f).harvestLevel(2).harvestTool(ToolType.PICKAXE).func_235861_h_()).setRegistryName("uranium_ore").register()
    val thoriumOre: Block = Block(Properties.create(ROCK).hardnessAndResistance(3f).harvestLevel(2).harvestTool(ToolType.PICKAXE).func_235861_h_()).setRegistryName("thorium_ore").register()
    val titaniumOre: Block = Block(Properties.create(ROCK).hardnessAndResistance(3f).harvestLevel(2).harvestTool(ToolType.PICKAXE).func_235861_h_()).setRegistryName("titanium_ore").register()
    val sulfurOre: Block = object : Block(Properties.create(ROCK).hardnessAndResistance(2f).harvestLevel(1).harvestTool(ToolType.PICKAXE).func_235861_h_()) {
        override fun getExpDrop(state: BlockState?, world: IWorldReader?, pos: BlockPos?, fortune: Int, silktouch: Int) = if (silktouch != 0) 0 else Random.nextInt(1, 3)
    }.setRegistryName("sulfur_ore").register()
    val niterOre: Block = object : Block(Properties.create(ROCK).hardnessAndResistance(2f).harvestLevel(1)){
        override fun getExpDrop(state: BlockState?, world: IWorldReader?, pos: BlockPos?, fortune: Int, silktouch: Int) = if (silktouch != 0) 0 else Random.nextInt(2, 4)
    }.setRegistryName("niter_ore").register()
    val copperOre: Block = Block(Properties.create(ROCK).hardnessAndResistance(3f).harvestLevel(1).harvestTool(ToolType.PICKAXE).func_235861_h_()).setRegistryName("copper_ore").register()
    val tungstenOre: Block = Block(Properties.create(ROCK).hardnessAndResistance(3f, 4f).harvestLevel(2).harvestTool(ToolType.PICKAXE).func_235861_h_()).setRegistryName("tungsten_ore").register()
    val aluminiumOre: Block = Block(Properties.create(ROCK).hardnessAndResistance(2f, 3f).harvestLevel(1).harvestTool(ToolType.PICKAXE).func_235861_h_()).setRegistryName("aluminium_ore").register()
    val fluoriteOre: Block = object : Block(Properties.create(ROCK).hardnessAndResistance(2f).harvestLevel(1).harvestTool(ToolType.PICKAXE).func_235861_h_()) {
        override fun getExpDrop(state: BlockState?, world: IWorldReader?, pos: BlockPos?, fortune: Int, silktouch: Int) = if (silktouch != 0) 0 else Random.nextInt(2, 4)
    }.setRegistryName("fluorite_ore").register()
    val berylliumOre: Block = Block(Properties.create(ROCK).hardnessAndResistance(3f).harvestLevel(2).harvestTool(ToolType.PICKAXE).func_235861_h_()).setRegistryName("beryllium_ore").register()
    val leadOre: Block = Block(Properties.create(ROCK).hardnessAndResistance(4f, 6f).harvestLevel(2).harvestTool(ToolType.PICKAXE).func_235861_h_()).setRegistryName("lead_ore").register()
    val oilDeposit: Block = Block(Properties.create(ROCK).hardnessAndResistance(1f, 2f).harvestLevel(0).harvestTool(ToolType.PICKAXE).func_235861_h_()).setRegistryName("oil_deposit").register()
    val emptyOilDeposit: Block = Block(Properties.create(ROCK).hardnessAndResistance(1f, 2f).harvestLevel(0).harvestTool(ToolType.PICKAXE).func_235861_h_()).setRegistryName("empty_oil_deposit").register()
    val oilSand: Block = Block(Properties.create(SAND).hardnessAndResistance(1f).harvestLevel(-1).harvestTool(ToolType.SHOVEL).sound(SoundType.SAND)).setRegistryName("oil_sand").register()
    val ligniteOre: Block = object : Block(Properties.create(ROCK).hardnessAndResistance(3f).harvestLevel(0).harvestTool(ToolType.PICKAXE).func_235861_h_()) {
        override fun getExpDrop(state: BlockState?, world: IWorldReader?, pos: BlockPos?, fortune: Int, silktouch: Int) = if (silktouch != 0) 0 else Random.nextInt(0, 2)
    }.setRegistryName("lignite_ore").register()
    val asbestosOre: Block = Block(Properties.create(ROCK).hardnessAndResistance(3f).harvestLevel(2).harvestTool(ToolType.PICKAXE).func_235861_h_()).setRegistryName("asbestos_ore").register()
    val schrabidiumOre: Block = object : Block(Properties.create(ROCK).hardnessAndResistance(20f, 50f).harvestLevel(4)) {
        override fun getLightValue(state: BlockState?, world: IBlockReader?, pos: BlockPos?) = 7
    }.setRegistryName("schrabidium_ore").register()
    val australianOre: Block = Block(Properties.create(ROCK).hardnessAndResistance(6f).harvestLevel(2).harvestTool(ToolType.PICKAXE).func_235861_h_()).setRegistryName("australian_ore").register()
    val weidite: Block = Block(Properties.create(ROCK).hardnessAndResistance(6f).harvestLevel(2).harvestTool(ToolType.PICKAXE).func_235861_h_()).setRegistryName("weidite").register()
    val reiite: Block = Block(Properties.create(ROCK).hardnessAndResistance(6f).harvestLevel(2).harvestTool(ToolType.PICKAXE).func_235861_h_()).setRegistryName("reiite").register()
    val brightblendeOre: Block = Block(Properties.create(ROCK).hardnessAndResistance(6f).harvestLevel(2).harvestTool(ToolType.PICKAXE).func_235861_h_()).setRegistryName("brightblende_ore").register()
    val dellite: Block = Block(Properties.create(ROCK).hardnessAndResistance(6f).harvestLevel(2).harvestTool(ToolType.PICKAXE).func_235861_h_()).setRegistryName("dellite").register()
    val dollarGreenMineral: Block = Block(Properties.create(ROCK).hardnessAndResistance(6f).harvestLevel(2).harvestTool(ToolType.PICKAXE).func_235861_h_()).setRegistryName("dollar_green_mineral").register()
    val rareEarthOre: Block = object : Block(Properties.create(ROCK).hardnessAndResistance(4f, 3f).harvestLevel(2).harvestTool(ToolType.PICKAXE).func_235861_h_()){
        override fun getExpDrop(state: BlockState?, world: IWorldReader?, pos: BlockPos?, fortune: Int, silktouch: Int) = if (silktouch != 0) 0 else Random.nextInt(3, 7)
    }.setRegistryName("rare_earth_ore").register()
    val netherUraniumOre: Block = Block(Properties.create(ROCK).hardnessAndResistance(3f).harvestLevel(2).harvestTool(ToolType.PICKAXE).func_235861_h_()).setRegistryName("nether_uranium_ore").register()
    val netherPlutoniumOre: Block = Block(Properties.create(ROCK).hardnessAndResistance(3f).harvestLevel(2).harvestTool(ToolType.PICKAXE).func_235861_h_()).setRegistryName("nether_plutonium_ore").register()
    val netherTungstenOre: Block = Block(Properties.create(ROCK).hardnessAndResistance(3f, 4f).harvestLevel(2).harvestTool(ToolType.PICKAXE).func_235861_h_()).setRegistryName("nether_tungsten_ore").register()
    val netherSulfurOre: Block = object : Block(Properties.create(ROCK).hardnessAndResistance(2f, 3f).harvestLevel(1).harvestTool(ToolType.PICKAXE).func_235861_h_()) {
        override fun getExpDrop(state: BlockState?, world: IWorldReader?, pos: BlockPos?, fortune: Int, silktouch: Int) = if (silktouch != 0) 0 else Random.nextInt(1, 4)
    }.setRegistryName("nether_sulfur_ore").register()
    val netherPhosphorusOre: Block = object : Block(Properties.create(ROCK).hardnessAndResistance(2f, 3f).harvestLevel(1).harvestTool(ToolType.PICKAXE).func_235861_h_()) {
        override fun getExpDrop(state: BlockState?, world: IWorldReader?, pos: BlockPos?, fortune: Int, silktouch: Int) = if (silktouch != 0) 0 else Random.nextInt(2, 5)
    }.setRegistryName("nether_phosphorus_ore").register()
    val netherSchrabidiumOre: Block = object : Block(Properties.create(ROCK).hardnessAndResistance(20f, 50f).harvestLevel(4).harvestTool(ToolType.PICKAXE).func_235861_h_()) {
        override fun getLightValue(state: BlockState?, world: IBlockReader?, pos: BlockPos?) = 7
    }.setRegistryName("nether_schrabidium_ore").register()
    val meteorUraniumOre: Block = Block(Properties.create(ROCK).hardnessAndResistance(6f).harvestLevel(3).harvestTool(ToolType.PICKAXE).func_235861_h_()).setRegistryName("meteor_uranium_ore").register()
    val meteorThoriumOre: Block = Block(Properties.create(ROCK).hardnessAndResistance(6f).harvestLevel(3).harvestTool(ToolType.PICKAXE).func_235861_h_()).setRegistryName("meteor_thorium_ore").register()
    val meteorTitaniumOre: Block = Block(Properties.create(ROCK).hardnessAndResistance(6f).harvestLevel(3).harvestTool(ToolType.PICKAXE).func_235861_h_()).setRegistryName("meteor_titanium_ore").register()
    val meteorSulfurOre: Block = object : Block(Properties.create(ROCK).hardnessAndResistance(5f).harvestLevel(3).harvestTool(ToolType.PICKAXE).func_235861_h_()) {
        override fun getExpDrop(state: BlockState?, world: IWorldReader?, pos: BlockPos?, fortune: Int, silktouch: Int) = if (silktouch != 0) 0 else Random.nextInt(5, 9)
    }.setRegistryName("meteor_sulfur_ore").register()
    val meteorCopperOre: Block = Block(Properties.create(ROCK).hardnessAndResistance(6f).harvestLevel(3).harvestTool(ToolType.PICKAXE).func_235861_h_()).setRegistryName("meteor_copper_ore").register()
    val meteorTungstenOre: Block = Block(Properties.create(ROCK).hardnessAndResistance(6f).harvestLevel(3).harvestTool(ToolType.PICKAXE).func_235861_h_()).setRegistryName("meteor_tungsten_ore").register()
    val meteorAluminiumOre: Block = Block(Properties.create(ROCK).hardnessAndResistance(6f).harvestLevel(3).harvestTool(ToolType.PICKAXE).func_235861_h_()).setRegistryName("meteor_aluminium_ore").register()
    val meteorLeadOre: Block = Block(Properties.create(ROCK).hardnessAndResistance(6f).harvestLevel(3).harvestTool(ToolType.PICKAXE).func_235861_h_()).setRegistryName("meteor_lead_ore").register()
    val meteorLithiumOre: Block = Block(Properties.create(ROCK).hardnessAndResistance(6f).harvestLevel(3).harvestTool(ToolType.PICKAXE).func_235861_h_()).setRegistryName("meteor_lithium_ore").register()
    val starmetalOre: Block = Block(Properties.create(ROCK).hardnessAndResistance(6f).harvestLevel(3).harvestTool(ToolType.PICKAXE).func_235861_h_()).setRegistryName("starmetal_ore").register()
    val trixite: Block = Block(Properties.create(ROCK).hardnessAndResistance(4f, 9f).harvestLevel(2).harvestTool(ToolType.PICKAXE).func_235861_h_()).setRegistryName("trixite").register()

    val uraniumBlock: Block = Block(Properties.create(IRON).hardnessAndResistance(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).func_235861_h_().sound(SoundType.METAL)).setRegistryName("uranium_block").register()
    val u233Block: Block = Block(Properties.create(IRON).hardnessAndResistance(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).func_235861_h_().sound(SoundType.METAL)).setRegistryName("u233_block").register()
    val u235Block: Block = Block(Properties.create(IRON).hardnessAndResistance(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).func_235861_h_().sound(SoundType.METAL)).setRegistryName("u235_block").register()
    val u238Block: Block = Block(Properties.create(IRON).hardnessAndResistance(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).func_235861_h_().sound(SoundType.METAL)).setRegistryName("u238_block").register()
    val uraniumFuelBlock: Block = Block(Properties.create(IRON).hardnessAndResistance(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).func_235861_h_().sound(SoundType.METAL)).setRegistryName("uranium_fuel_block").register()
    val neptuniumBlock: Block = Block(Properties.create(IRON).hardnessAndResistance(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).func_235861_h_().sound(SoundType.METAL)).setRegistryName("neptunium_block").register()
    val moxFuelBlock: Block = Block(Properties.create(IRON).hardnessAndResistance(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).func_235861_h_().sound(SoundType.METAL)).setRegistryName("mox_fuel_block").register()
    val plutoniumBlock: Block = Block(Properties.create(IRON).hardnessAndResistance(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).func_235861_h_().sound(SoundType.METAL)).setRegistryName("plutonium_block").register()
    val pu238Block: Block = Block(Properties.create(IRON).hardnessAndResistance(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).func_235861_h_().sound(SoundType.METAL)).setRegistryName("pu238_block").register()
    val pu239Block: Block = Block(Properties.create(IRON).hardnessAndResistance(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).func_235861_h_().sound(SoundType.METAL)).setRegistryName("pu239_block").register()
    val pu240Block: Block = Block(Properties.create(IRON).hardnessAndResistance(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).func_235861_h_().sound(SoundType.METAL)).setRegistryName("pu240_block").register()
    val plutoniumFuelBlock: Block = Block(Properties.create(IRON).hardnessAndResistance(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).func_235861_h_().sound(SoundType.METAL)).setRegistryName("plutonium_fuel_block").register()
    val thoriumBlock: Block = Block(Properties.create(IRON).hardnessAndResistance(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).func_235861_h_().sound(SoundType.METAL)).setRegistryName("thorium_block").register()
    val thoriumFuelBlock: Block = Block(Properties.create(IRON).hardnessAndResistance(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).func_235861_h_().sound(SoundType.METAL)).setRegistryName("thorium_fuel_block").register()
    val titaniumBlock: Block = Block(Properties.create(IRON).hardnessAndResistance(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).func_235861_h_().sound(SoundType.METAL)).setRegistryName("titanium_block").register()
    val sulfurBlock: Block = Block(Properties.create(IRON).hardnessAndResistance(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).func_235861_h_().sound(SoundType.METAL)).setRegistryName("sulfur_block").register()
    val niterBlock: Block = Block(Properties.create(IRON).hardnessAndResistance(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).func_235861_h_().sound(SoundType.METAL)).setRegistryName("niter_block").register()
    val copperBlock: Block = Block(Properties.create(IRON).hardnessAndResistance(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).func_235861_h_().sound(SoundType.METAL)).setRegistryName("copper_block").register()
    val redCopperBlock: Block = Block(Properties.create(IRON).hardnessAndResistance(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).func_235861_h_().sound(SoundType.METAL)).setRegistryName("red_copper_block").register()
    val advancedAlloyBlock: Block = Block(Properties.create(IRON).hardnessAndResistance(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).func_235861_h_().sound(SoundType.METAL)).setRegistryName("advanced_alloy_block").register()
    val tungstenBlock: Block = Block(Properties.create(IRON).hardnessAndResistance(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).func_235861_h_().sound(SoundType.METAL)).setRegistryName("tungsten_block").register()
    val aluminiumBlock: Block = Block(Properties.create(IRON).hardnessAndResistance(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).func_235861_h_().sound(SoundType.METAL)).setRegistryName("aluminium_block").register()
    val fluoriteBlock: Block = Block(Properties.create(IRON).hardnessAndResistance(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).func_235861_h_().sound(SoundType.METAL)).setRegistryName("fluorite_block").register()
    val berylliumBlock: Block = Block(Properties.create(IRON).hardnessAndResistance(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).func_235861_h_().sound(SoundType.METAL)).setRegistryName("beryllium_block").register()
    val cobaltBlock: Block = Block(Properties.create(IRON).hardnessAndResistance(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).func_235861_h_().sound(SoundType.METAL)).setRegistryName("cobalt_block").register()
    val steelBlock: Block = Block(Properties.create(IRON).hardnessAndResistance(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).func_235861_h_().sound(SoundType.METAL)).setRegistryName("steel_block").register()
    val leadBlock: Block = Block(Properties.create(IRON).hardnessAndResistance(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).func_235861_h_().sound(SoundType.METAL)).setRegistryName("lead_block").register()
    val lithiumBlock: Block = Block(Properties.create(IRON).hardnessAndResistance(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).func_235861_h_().sound(SoundType.METAL)).setRegistryName("lithium_block").register()
    val whitePhosphorusBlock: Block = Block(Properties.create(IRON).hardnessAndResistance(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).func_235861_h_().sound(SoundType.METAL)).setRegistryName("white_phosphorus_block").register()
    val redPhosphorusBlock: Block = Block(Properties.create(IRON).hardnessAndResistance(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).func_235861_h_().sound(SoundType.METAL)).setRegistryName("red_phosphorus_block").register()
    val yellowcakeBlock: Block = Block(Properties.create(CLAY).hardnessAndResistance(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).func_235861_h_().sound(SoundType.SAND)).setRegistryName("yellowcake_block").register()
    val scrapBlock: Block = Block(Properties.create(CLAY).hardnessAndResistance(1f).harvestLevel(-1).harvestTool(ToolType.SHOVEL).func_235861_h_().sound(SoundType.GROUND)).setRegistryName("scrap_block").register()
    val electricalScrapBlock: Block = Block(Properties.create(IRON).hardnessAndResistance(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).func_235861_h_().sound(SoundType.METAL)).setRegistryName("electrical_scrap_block").register()
    val insulatorRoll: Block = Block(Properties.create(IRON).hardnessAndResistance(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).func_235861_h_().sound(SoundType.METAL)).setRegistryName("insulator_roll").register()
    val fiberglassRoll: Block = Block(Properties.create(IRON).hardnessAndResistance(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).func_235861_h_().sound(SoundType.METAL)).setRegistryName("fiberglass_roll").register()
    val asbestosBlock: Block = Block(Properties.create(IRON).hardnessAndResistance(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).func_235861_h_().sound(SoundType.METAL)).setRegistryName("asbestos_block").register()
    val trinititeBlock: Block = Block(Properties.create(IRON).hardnessAndResistance(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).func_235861_h_().sound(SoundType.METAL)).setRegistryName("trinitite_block").register()
    val nuclearWasteBlock: Block = Block(Properties.create(IRON).hardnessAndResistance(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).func_235861_h_().sound(SoundType.METAL)).setRegistryName("nuclear_waste_block").register()
    val schrabidiumBlock: Block = Block(Properties.create(IRON).hardnessAndResistance(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).func_235861_h_().sound(SoundType.METAL)).setRegistryName("schrabidium_block").register()
    val soliniumBlock: Block = Block(Properties.create(IRON).hardnessAndResistance(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).func_235861_h_().sound(SoundType.METAL)).setRegistryName("solinium_block").register()
    val schrabidiumFuelBlock: Block = Block(Properties.create(IRON).hardnessAndResistance(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).func_235861_h_().sound(SoundType.METAL)).setRegistryName("schrabidium_fuel_block").register()
    val euphemiumBlock: Block = Block(Properties.create(IRON).hardnessAndResistance(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).func_235861_h_().sound(SoundType.METAL)).setRegistryName("euphemium_block").register()
    val schrabidiumCluster: Block = Block(Properties.create(IRON).hardnessAndResistance(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).func_235861_h_().sound(SoundType.METAL)).setRegistryName("schrabidium_cluster").register()
    val euphemiumEtchedSchrabidiumCluster: Block = Block(Properties.create(IRON).hardnessAndResistance(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).func_235861_h_().sound(SoundType.METAL)).setRegistryName("euphemium_etched_schrabidium_cluster").register()
    val magnetizedTungstenBlock: Block = Block(Properties.create(IRON).hardnessAndResistance(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).func_235861_h_().sound(SoundType.METAL)).setRegistryName("magnetized_tungsten_block").register()
    val combineSteelBlock: Block = Block(Properties.create(IRON).hardnessAndResistance(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).func_235861_h_().sound(SoundType.METAL)).setRegistryName("combine_steel_block").register()
    val deshReinforcedBlock: Block = Block(Properties.create(IRON).hardnessAndResistance(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).func_235861_h_().sound(SoundType.METAL)).setRegistryName("desh_reinforced_block").register()
    val starmetalBlock: Block = Block(Properties.create(IRON).hardnessAndResistance(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).func_235861_h_().sound(SoundType.METAL)).setRegistryName("starmetal_block").register()
    val australiumBlock: Block = Block(Properties.create(IRON).hardnessAndResistance(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).func_235861_h_().sound(SoundType.METAL)).setRegistryName("australiumblock").register()
    val weidaniumBlock: Block = Block(Properties.create(IRON).hardnessAndResistance(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).func_235861_h_().sound(SoundType.METAL)).setRegistryName("weidanium_block").register()
    val reiiumBlock: Block = Block(Properties.create(IRON).hardnessAndResistance(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).func_235861_h_().sound(SoundType.METAL)).setRegistryName("reiium_block").register()
    val unobtainiumBlock: Block = Block(Properties.create(IRON).hardnessAndResistance(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).func_235861_h_().sound(SoundType.METAL)).setRegistryName("unobtainium_block").register()
    val daffergonBlock: Block = Block(Properties.create(IRON).hardnessAndResistance(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).func_235861_h_().sound(SoundType.METAL)).setRegistryName("daffergon_block").register()
    val verticiumBlock: Block = Block(Properties.create(IRON).hardnessAndResistance(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).func_235861_h_().sound(SoundType.METAL)).setRegistryName("verticium_block").register()
    val titaniumDecoBlock: Block = Block(Properties.create(IRON).hardnessAndResistance(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).func_235861_h_().sound(SoundType.METAL)).setRegistryName("titanium_deco_block").register()
    val redCopperDecoBlock: Block = Block(Properties.create(IRON).hardnessAndResistance(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).func_235861_h_().sound(SoundType.METAL)).setRegistryName("red_copper_deco_block").register()
    val tungstenDecoBlock: Block = Block(Properties.create(IRON).hardnessAndResistance(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).func_235861_h_().sound(SoundType.METAL)).setRegistryName("tungsten_deco_block").register()
    val aluminiumDecoBlock: Block = Block(Properties.create(IRON).hardnessAndResistance(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).func_235861_h_().sound(SoundType.METAL)).setRegistryName("aluminium_deco_block").register()
    val steelDecoBlock: Block = Block(Properties.create(IRON).hardnessAndResistance(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).func_235861_h_().sound(SoundType.METAL)).setRegistryName("steel_deco_block").register()
    val leadDecoBlock: Block = Block(Properties.create(IRON).hardnessAndResistance(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).func_235861_h_().sound(SoundType.METAL)).setRegistryName("lead_deco_block").register()
    val berylliumDecoBlock: Block = Block(Properties.create(IRON).hardnessAndResistance(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).func_235861_h_().sound(SoundType.METAL)).setRegistryName("beryllium_deco_block").register()
    val asbestosRoof: Block = Block(Properties.create(IRON).hardnessAndResistance(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).func_235861_h_().sound(SoundType.METAL)).setRegistryName("asbestos_roof").register()
    val hazmatBlock: Block = Block(Properties.create(WOOL).hardnessAndResistance(6f).harvestLevel(1).harvestTool(ToolType.PICKAXE).func_235861_h_().sound(SoundType.CLOTH)).setRegistryName("hazmat_block").register()

    val siren: Block = Siren(Properties.create(IRON).hardnessAndResistance(5f).harvestLevel(1).harvestTool(ToolType.PICKAXE).func_235861_h_().sound(SoundType.METAL)).setRegistryName("siren").register()
    val safe: Block = Safe(Properties.create(IRON).hardnessAndResistance(25f, 1200f).harvestLevel(2).harvestTool(ToolType.PICKAXE).func_235861_h_().sound(SoundType.METAL)).setRegistryName("safe").register()

    // region Steam Press
    val steamPressBase: Block = SteamPressBase(Properties.create(IRON).hardnessAndResistance(5f).harvestLevel(1).harvestTool(ToolType.PICKAXE).func_235861_h_().sound(SoundType.METAL)).setRegistryName("steam_press_base").register()
    val steamPressFrame: Block = SteamPressFrame(Properties.create(IRON).hardnessAndResistance(5f).harvestLevel(1).harvestTool(ToolType.PICKAXE).func_235861_h_().sound(SoundType.METAL)).setRegistryName("steam_press_frame").register()
    val steamPressTop: Block = SteamPressTop(Properties.create(IRON).hardnessAndResistance(5f).harvestLevel(1).harvestTool(ToolType.PICKAXE).func_235861_h_().sound(SoundType.METAL)).setRegistryName("steam_press_top").register()
    // endregion
}
