package at.martinthedragon.nucleartech.datagen

import at.martinthedragon.nucleartech.NuclearTech
import at.martinthedragon.nucleartech.block.AnvilBlock
import at.martinthedragon.nucleartech.block.NTechBlocks
import at.martinthedragon.nucleartech.extensions.appendToPath
import at.martinthedragon.nucleartech.fluid.NTechFluids
import at.martinthedragon.nucleartech.ntm
import net.minecraft.core.Direction
import net.minecraft.data.DataGenerator
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.HorizontalDirectionalBlock
import net.minecraft.world.level.block.PipeBlock
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraftforge.client.model.generators.BlockModelBuilder
import net.minecraftforge.client.model.generators.BlockStateProvider
import net.minecraftforge.client.model.generators.ConfiguredModel
import net.minecraftforge.client.model.generators.loaders.OBJLoaderBuilder
import net.minecraftforge.common.data.ExistingFileHelper

class NuclearBlockStateProvider(
    dataGenerator: DataGenerator,
    existingFileHelper: ExistingFileHelper
) : BlockStateProvider(dataGenerator, NuclearTech.MODID, existingFileHelper) {
    override fun getName(): String = "Nuclear Tech Mod Block States and Models"

    override fun registerStatesAndModels() {
        simpleBlock(NTechBlocks.uraniumOre.get())
        simpleBlock(NTechBlocks.scorchedUraniumOre.get())
        simpleBlock(NTechBlocks.thoriumOre.get())
        simpleBlock(NTechBlocks.titaniumOre.get())
        simpleBlock(NTechBlocks.sulfurOre.get())
        simpleBlock(NTechBlocks.niterOre.get())
        simpleBlock(NTechBlocks.tungstenOre.get())
        simpleBlock(NTechBlocks.aluminiumOre.get())
        simpleBlock(NTechBlocks.fluoriteOre.get())
        simpleBlock(NTechBlocks.berylliumOre.get())
        simpleBlock(NTechBlocks.leadOre.get())
        simpleBlock(NTechBlocks.oilDeposit.get())
        simpleBlock(NTechBlocks.emptyOilDeposit.get())
        simpleBlock(NTechBlocks.oilSand.get())
        simpleBlock(NTechBlocks.ligniteOre.get())
        simpleBlock(NTechBlocks.asbestosOre.get())
        simpleBlock(NTechBlocks.schrabidiumOre.get())
        simpleBlock(NTechBlocks.australianOre.get())
        simpleBlock(NTechBlocks.weidite.get())
        simpleBlock(NTechBlocks.reiite.get())
        simpleBlock(NTechBlocks.brightblendeOre.get())
        simpleBlock(NTechBlocks.dellite.get())
        simpleBlock(NTechBlocks.dollarGreenMineral.get())
        simpleBlock(NTechBlocks.rareEarthOre.get())
        simpleBlock(NTechBlocks.cobaltOre.get())
        simpleBlock(NTechBlocks.deepslateUraniumOre.get())
        simpleBlock(NTechBlocks.scorchedDeepslateUraniumOre.get())
        simpleBlock(NTechBlocks.deepslateThoriumOre.get())
        simpleBlock(NTechBlocks.deepslateTitaniumOre.get())
        simpleBlock(NTechBlocks.deepslateSulfurOre.get())
        simpleBlock(NTechBlocks.deepslateNiterOre.get())
        simpleBlock(NTechBlocks.deepslateTungstenOre.get())
        simpleBlock(NTechBlocks.deepslateAluminiumOre.get())
        simpleBlock(NTechBlocks.deepslateFluoriteOre.get())
        simpleBlock(NTechBlocks.deepslateBerylliumOre.get())
        simpleBlock(NTechBlocks.deepslateLeadOre.get())
        simpleBlock(NTechBlocks.deepslateOilDeposit.get())
        simpleBlock(NTechBlocks.emptyDeepslateOilDeposit.get())
        simpleBlock(NTechBlocks.deepslateAsbestosOre.get())
        simpleBlock(NTechBlocks.deepslateSchrabidiumOre.get())
        simpleBlock(NTechBlocks.deepslateAustralianOre.get())
        simpleBlock(NTechBlocks.deepslateRareEarthOre.get())
        simpleBlock(NTechBlocks.deepslateCobaltOre.get())
        simpleBlock(NTechBlocks.netherUraniumOre.get())
        simpleBlock(NTechBlocks.scorchedNetherUraniumOre.get())
        simpleBlock(NTechBlocks.netherPlutoniumOre.get())
        simpleBlock(NTechBlocks.netherTungstenOre.get())
        simpleBlock(NTechBlocks.netherSulfurOre.get())
        simpleBlock(NTechBlocks.netherPhosphorusOre.get())
        simpleBlock(NTechBlocks.netherSchrabidiumOre.get())
        simpleBlock(NTechBlocks.meteorUraniumOre.get())
        simpleBlock(NTechBlocks.meteorThoriumOre.get())
        simpleBlock(NTechBlocks.meteorTitaniumOre.get())
        simpleBlock(NTechBlocks.meteorSulfurOre.get())
        simpleBlock(NTechBlocks.meteorCopperOre.get())
        simpleBlock(NTechBlocks.meteorTungstenOre.get())
        simpleBlock(NTechBlocks.meteorAluminiumOre.get())
        simpleBlock(NTechBlocks.meteorLeadOre.get())
        simpleBlock(NTechBlocks.meteorLithiumOre.get())
        simpleBlock(NTechBlocks.starmetalOre.get())
        simpleBlock(NTechBlocks.trixite.get())
        axisBlock(NTechBlocks.basaltSulfurOre.get())
        axisBlock(NTechBlocks.basaltFluoriteOre.get())
        axisBlock(NTechBlocks.basaltAsbestosOre.get())
        axisBlock(NTechBlocks.basaltVolcanicGemOre.get())
        simpleBlock(NTechBlocks.uraniumBlock.get())
        simpleBlock(NTechBlocks.u233Block.get())
        simpleBlock(NTechBlocks.u235Block.get())
        simpleBlock(NTechBlocks.u238Block.get())
        simpleBlock(NTechBlocks.uraniumFuelBlock.get())
        simpleBlock(NTechBlocks.neptuniumBlock.get())
        simpleBlock(NTechBlocks.moxFuelBlock.get())
        simpleBlock(NTechBlocks.plutoniumBlock.get())
        simpleBlock(NTechBlocks.pu238Block.get())
        simpleBlock(NTechBlocks.pu239Block.get())
        simpleBlock(NTechBlocks.pu240Block.get())
        simpleBlock(NTechBlocks.plutoniumFuelBlock.get())
        simpleBlock(NTechBlocks.thoriumBlock.get())
        simpleBlock(NTechBlocks.thoriumFuelBlock.get())
        simpleBlock(NTechBlocks.titaniumBlock.get())
        simpleBlock(NTechBlocks.sulfurBlock.get())
        simpleBlock(NTechBlocks.niterBlock.get())
        simpleBlock(NTechBlocks.redCopperBlock.get())
        simpleBlock(NTechBlocks.advancedAlloyBlock.get())
        simpleBlock(NTechBlocks.tungstenBlock.get())
        simpleBlock(NTechBlocks.aluminiumBlock.get())
        simpleBlock(NTechBlocks.fluoriteBlock.get())
        simpleBlock(NTechBlocks.berylliumBlock.get())
        simpleBlock(NTechBlocks.cobaltBlock.get())
        simpleBlock(NTechBlocks.steelBlock.get())
        simpleBlock(NTechBlocks.leadBlock.get())
        simpleBlock(NTechBlocks.lithiumBlock.get())
        simpleBlock(NTechBlocks.whitePhosphorusBlock.get())
        simpleBlock(NTechBlocks.redPhosphorusBlock.get())
        simpleBlock(NTechBlocks.yellowcakeBlock.get())
        simpleBlock(NTechBlocks.scrapBlock.get())
        simpleBlock(NTechBlocks.electricalScrapBlock.get())
        axisBlock(NTechBlocks.insulatorRoll.get())
        axisBlock(NTechBlocks.fiberglassRoll.get())
        simpleBlock(NTechBlocks.asbestosBlock.get())
        simpleBlock(NTechBlocks.trinititeBlock.get())
        simpleBlock(NTechBlocks.nuclearWasteBlock.get())
        simpleBlock(NTechBlocks.schrabidiumBlock.get())
        simpleBlock(NTechBlocks.soliniumBlock.get())
        simpleBlock(NTechBlocks.schrabidiumFuelBlock.get())
        simpleBlock(NTechBlocks.euphemiumBlock.get())
        simpleBlock(NTechBlocks.schrabidiumCluster.get(), models().cubeColumn("schrabidium_cluster", extend(blockTexture(NTechBlocks.schrabidiumCluster.get()), "_side"), extend(blockTexture(NTechBlocks.schrabidiumCluster.get()), "_end")))
        simpleBlock(NTechBlocks.euphemiumEtchedSchrabidiumCluster.get(), models().cubeColumn("euphemium_etched_schrabidium_cluster", extend(blockTexture(NTechBlocks.euphemiumEtchedSchrabidiumCluster.get()), "_side"), extend(blockTexture(NTechBlocks.euphemiumEtchedSchrabidiumCluster.get()), "_end")))
        simpleBlock(NTechBlocks.magnetizedTungstenBlock.get())
        simpleBlock(NTechBlocks.combineSteelBlock.get())
        simpleBlock(NTechBlocks.deshReinforcedBlock.get())
        simpleBlock(NTechBlocks.starmetalBlock.get())
        simpleBlock(NTechBlocks.australiumBlock.get())
        simpleBlock(NTechBlocks.weidaniumBlock.get())
        simpleBlock(NTechBlocks.reiiumBlock.get())
        simpleBlock(NTechBlocks.unobtainiumBlock.get())
        simpleBlock(NTechBlocks.daffergonBlock.get())
        simpleBlock(NTechBlocks.verticiumBlock.get())
        simpleBlock(NTechBlocks.titaniumDecoBlock.get())
        simpleBlock(NTechBlocks.redCopperDecoBlock.get())
        simpleBlock(NTechBlocks.tungstenDecoBlock.get())
        simpleBlock(NTechBlocks.aluminiumDecoBlock.get())
        simpleBlock(NTechBlocks.steelDecoBlock.get())
        simpleBlock(NTechBlocks.leadDecoBlock.get())
        simpleBlock(NTechBlocks.berylliumDecoBlock.get())
        simpleBlock(NTechBlocks.asbestosRoof.get())
        simpleBlock(NTechBlocks.hazmatBlock.get())
        simpleBlock(NTechBlocks.meteorite.get())
        simpleBlock(NTechBlocks.meteoriteCobblestone.get())
        simpleBlock(NTechBlocks.brokenMeteorite.get())
        simpleBlock(NTechBlocks.hotMeteoriteCobblestone.get())
        simpleBlock(NTechBlocks.meteoriteTreasure.get())

        simpleBlock(NTechBlocks.decoRbmkBlock.get())
        simpleBlock(NTechBlocks.decoRbmkSmoothBlock.get())

        models().withExistingParent("steel_beam", "block/block").element().from(7F, 0F, 7F).to(9F, 16F, 9F).allFaces { _, builder -> builder.uvs(0F, 0F, 2F, 16F) }.face(Direction.UP).uvs(0F, 0F, 2F, 2F).cullface(Direction.UP).end().face(Direction.DOWN).uvs(0F, 0F, 2F, 2F).cullface(Direction.DOWN).end().textureAll("#all").end().texture("all", blockTexture(NTechBlocks.steelBeam.get())).texture("particle", blockTexture(NTechBlocks.steelBeam.get()))
            .also { axisBlock(NTechBlocks.steelBeam.get(), it, it) }
        run {
            val scaffoldTexture = blockTexture(NTechBlocks.steelDecoBlock.get())
            val scaffoldModel = models().withExistingParent("steel_scaffold", "block/block").customLoader { parent, existingFileHelper -> OBJLoaderBuilder.begin(parent, existingFileHelper) }.modelLocation(ntm("models/block/steel_scaffold.obj")).flipV(true).end().texture("texture", scaffoldTexture).texture("particle", scaffoldTexture)
            getVariantBuilder(NTechBlocks.steelScaffold.get())
                .partialState().with(BlockStateProperties.HORIZONTAL_AXIS, Direction.Axis.X).modelForState().modelFile(scaffoldModel).addModel()
                .partialState().with(BlockStateProperties.HORIZONTAL_AXIS, Direction.Axis.Z).modelForState().modelFile(scaffoldModel).rotationY(90).addModel()
        }
        run {
            val grateTop = blockTexture(NTechBlocks.steelGrate.get())
            val grateSide = extend(grateTop, "_side")
            fun grateModel(level: Int) = models().withExistingParent("steel_grate${level - 1}", "block/block").element().from(0F, (level - 1) * 2F, 0F).to(16F, level * 2F, 16F)
                .allFaces { side, builder -> builder.uvs(0F, 0F, 16F, 2F).texture("#side").cullface(side) }
                .face(Direction.UP).uvs(0F, 0F, 16F, 16F).texture("#up").end()
                .face(Direction.DOWN).uvs(0F, 0F, 16F, 16F).texture("#up").end()
                .end().texture("side", grateSide).texture("up", grateTop).texture("particle", grateTop)

            getVariantBuilder(NTechBlocks.steelGrate.get()).forAllStatesExcept({
                val level = it.getValue(BlockStateProperties.LEVEL_FLOWING)

                ConfiguredModel.builder()
                    .modelFile(grateModel(level))
                    .build()
            }, BlockStateProperties.WATERLOGGED)
        }
        simpleBlock(NTechBlocks.glowingMushroom.get(), models().cross("glowing_mushroom", blockTexture(NTechBlocks.glowingMushroom.get())))
        val singleFaceTemplateModelFile = models().getExistingFile(mcLoc("block/template_single_face"))
        val glowingMushroomModelFile = models().getBuilder("glowing_mushroom_block").parent(singleFaceTemplateModelFile).texture("texture", blockTexture(NTechBlocks.glowingMushroomBlock.get()))
        val glowingMushroomInsideModelFile = models().getBuilder("glowing_mushroom_block_inside").parent(singleFaceTemplateModelFile).texture("texture", extend(blockTexture(NTechBlocks.glowingMushroomBlock.get()), "_inside"))
        val glowingMushroomStemModelFile = models().getBuilder("glowing_mushroom_stem").parent(singleFaceTemplateModelFile).texture("texture", blockTexture(NTechBlocks.glowingMushroomStem.get()))
        getMultipartBuilder(NTechBlocks.glowingMushroomBlock.get())
            .part().modelFile(glowingMushroomModelFile).addModel().condition(PipeBlock.NORTH, true).end()
            .part().modelFile(glowingMushroomModelFile).rotationY(90).uvLock(true).addModel().condition(PipeBlock.EAST, true).end()
            .part().modelFile(glowingMushroomModelFile).rotationY(180).uvLock(true).addModel().condition(PipeBlock.SOUTH, true).end()
            .part().modelFile(glowingMushroomModelFile).rotationY(270).uvLock(true).addModel().condition(PipeBlock.WEST, true).end()
            .part().modelFile(glowingMushroomModelFile).rotationX(270).uvLock(true).addModel().condition(PipeBlock.UP, true).end()
            .part().modelFile(glowingMushroomModelFile).rotationX(90).uvLock(true).addModel().condition(PipeBlock.DOWN, true).end()
            .part().modelFile(glowingMushroomInsideModelFile).addModel().condition(PipeBlock.NORTH, false).end()
            .part().modelFile(glowingMushroomInsideModelFile).rotationY(90).uvLock(false).addModel().condition(PipeBlock.EAST, false).end()
            .part().modelFile(glowingMushroomInsideModelFile).rotationY(180).uvLock(false).addModel().condition(PipeBlock.SOUTH, false).end()
            .part().modelFile(glowingMushroomInsideModelFile).rotationY(270).uvLock(false).addModel().condition(PipeBlock.WEST, false).end()
            .part().modelFile(glowingMushroomInsideModelFile).rotationX(270).uvLock(false).addModel().condition(PipeBlock.UP, false).end()
            .part().modelFile(glowingMushroomInsideModelFile).rotationX(90).uvLock(false).addModel().condition(PipeBlock.DOWN, false).end()
        getMultipartBuilder(NTechBlocks.glowingMushroomStem.get())
            .part().modelFile(glowingMushroomStemModelFile).addModel().condition(PipeBlock.NORTH, true).end()
            .part().modelFile(glowingMushroomStemModelFile).rotationY(90).uvLock(true).addModel().condition(PipeBlock.EAST, true).end()
            .part().modelFile(glowingMushroomStemModelFile).rotationY(180).uvLock(true).addModel().condition(PipeBlock.SOUTH, true).end()
            .part().modelFile(glowingMushroomStemModelFile).rotationY(270).uvLock(true).addModel().condition(PipeBlock.WEST, true).end()
            .part().modelFile(glowingMushroomStemModelFile).rotationX(270).uvLock(true).addModel().condition(PipeBlock.UP, true).end()
            .part().modelFile(glowingMushroomStemModelFile).rotationX(90).uvLock(true).addModel().condition(PipeBlock.DOWN, true).end()
            .part().modelFile(glowingMushroomInsideModelFile).addModel().condition(PipeBlock.NORTH, false).end()
            .part().modelFile(glowingMushroomInsideModelFile).rotationY(90).uvLock(false).addModel().condition(PipeBlock.EAST, false).end()
            .part().modelFile(glowingMushroomInsideModelFile).rotationY(180).uvLock(false).addModel().condition(PipeBlock.SOUTH, false).end()
            .part().modelFile(glowingMushroomInsideModelFile).rotationY(270).uvLock(false).addModel().condition(PipeBlock.WEST, false).end()
            .part().modelFile(glowingMushroomInsideModelFile).rotationX(270).uvLock(false).addModel().condition(PipeBlock.UP, false).end()
            .part().modelFile(glowingMushroomInsideModelFile).rotationX(90).uvLock(false).addModel().condition(PipeBlock.DOWN, false).end()
        simpleBlock(NTechBlocks.deadGrass.get(), models().cubeBottomTop("dead_grass", extend(blockTexture(NTechBlocks.deadGrass.get()), "_side"), blockTexture(Blocks.DIRT), extend(blockTexture(NTechBlocks.deadGrass.get()), "_top")))
        simpleBlock(NTechBlocks.glowingMycelium.get(), models().cubeBottomTop("glowing_mycelium", extend(blockTexture(NTechBlocks.glowingMycelium.get()), "_side"), blockTexture(Blocks.DIRT), extend(blockTexture(NTechBlocks.glowingMycelium.get()), "_top")))
        simpleBlock(NTechBlocks.trinitite.get())
        simpleBlock(NTechBlocks.redTrinitite.get())
        logBlock(NTechBlocks.charredLog.get())
        simpleBlock(NTechBlocks.charredPlanks.get())
        simpleBlock(NTechBlocks.slakedSellafite.get())
        simpleBlock(NTechBlocks.sellafite.get())
        simpleBlock(NTechBlocks.hotSellafite.get())
        simpleBlock(NTechBlocks.boilingSellafite.get())
        simpleBlock(NTechBlocks.blazingSellafite.get())
        simpleBlock(NTechBlocks.infernalSellafite.get())
        simpleBlock(NTechBlocks.sellafiteCorium.get())
        simpleBlock(NTechBlocks.corium.get())
        simpleBlock(NTechBlocks.corebblestone.get())
        simpleBlock(NTechBlocks.siren.get(), models().cubeColumn("siren", extend(blockTexture(NTechBlocks.siren.get()), "_side"), blockTexture(NTechBlocks.steelBlock.get())))
        horizontalBlock(NTechBlocks.safe.get(), extend(blockTexture(NTechBlocks.safe.get()), "_side"), extend(blockTexture(NTechBlocks.safe.get()), "_front"), extend(blockTexture(NTechBlocks.safe.get()), "_side"))
        anvil(NTechBlocks.ironAnvil.get())
        anvil(NTechBlocks.leadAnvil.get())
        anvil(NTechBlocks.steelAnvil.get())
        anvil(NTechBlocks.meteoriteAnvil.get())
        anvil(NTechBlocks.starmetalAnvil.get())
        anvil(NTechBlocks.ferrouraniumAnvil.get())
        anvil(NTechBlocks.bismuthAnvil.get())
        anvil(NTechBlocks.schrabidateAnvil.get())
        anvil(NTechBlocks.dineutroniumAnvil.get())
        anvil(NTechBlocks.murkyAnvil.get())
        simpleBlock(NTechBlocks.coatedCable.get())
        simpleBlock(NTechBlocks.coatedUniversalFluidDuct.get(), models().withExistingParent(name(NTechBlocks.coatedUniversalFluidDuct.get()), "block/block").element().cube("#texture").end().element().cube("#overlay").allFaces { _, builder -> builder.tintindex(0) }.end().texture("texture", blockTexture(NTechBlocks.coatedUniversalFluidDuct.get())).texture("particle", blockTexture(NTechBlocks.coatedUniversalFluidDuct.get())).texture("overlay", extend(blockTexture(NTechBlocks.coatedUniversalFluidDuct.get()), "_overlay")))
        simpleBlock(NTechBlocks.steamPressBase.get(), models().getExistingFile(NTechBlocks.steamPressBase.id))
        simpleBlock(NTechBlocks.steamPressFrame.get(), models().getExistingFile(NTechBlocks.steamPressFrame.id))
        simpleBlock(NTechBlocks.steamPressTop.get(), models().getExistingFile(NTechBlocks.steamPressTop.id))
        litHorizontalBlock(NTechBlocks.blastFurnace.get(), top = extend(blockTexture(NTechBlocks.blastFurnace.get()), "_top"), topLit = extend(blockTexture(NTechBlocks.blastFurnace.get()), "_top_on"))
        litHorizontalBlock(NTechBlocks.combustionGenerator.get())
        litHorizontalBlock(NTechBlocks.electricFurnace.get(), bottom = extend(blockTexture(NTechBlocks.electricFurnace.get()), "_bottom"), top = extend(blockTexture(NTechBlocks.electricFurnace.get()), "_top"))
        cubeAllSides(NTechBlocks.shredder.get(), north = extend(blockTexture(NTechBlocks.shredder.get()), "_front"), south = extend(blockTexture(NTechBlocks.shredder.get()), "_front"), east = extend(blockTexture(NTechBlocks.shredder.get()), "_side"), west = extend(blockTexture(NTechBlocks.shredder.get()), "_side"))
        horizontalBlock(NTechBlocks.assembler.get(), models().getBuilder("assembler").texture("particle", "other/assembler/particles"))
        horizontalBlock(NTechBlocks.chemPlant.get(), models().getBuilder("chem_plant").texture("particle", "other/chem_plant/particles"))
        simpleBlock(NTechBlocks.turbine.get(), models().cubeColumn("turbine", extend(blockTexture(NTechBlocks.turbine.get()), "_side"), extend(blockTexture(NTechBlocks.turbine.get()), "_end")))
        simpleBlock(NTechBlocks.smallCoolingTower.get(), models().getBuilder("small_cooling_tower").texture("particle", "other/small_cooling_tower/particles"))
        simpleBlock(NTechBlocks.largeCoolingTower.get(), models().getBuilder("large_cooling_tower").texture("particle", "other/large_cooling_tower/particles"))
        simpleBlock(NTechBlocks.oilDerrick.get(), models().getBuilder("oil_derrick").texture("particle", "other/oil_derrick/particles"))
        simpleBlock(NTechBlocks.pumpjack.get(), models().getBuilder("pumpjack").texture("particle", "other/pumpjack/particles"))
        simpleBlock(NTechBlocks.centrifuge.get(), models().getBuilder("centrifuge").texture("particle", "other/centrifuge/particles"))
        simpleBlock(NTechBlocks.rbmkColumn.get(), models().getBuilder("rbmk_column"))
        simpleBlock(NTechBlocks.rbmkRod.get(), models().getBuilder("rbmk_rod").texture("particle", "other/rbmk/rods"))
        simpleBlock(NTechBlocks.rbmkModeratedRod.get(), models().getBuilder("rbmk_moderated_rod").texture("particle", "other/rbmk/moderated_rods"))
        simpleBlock(NTechBlocks.rbmkReaSimRod.get(), models().getBuilder("rbmk_reasim_rod").texture("particle", "other/rbmk/reasim_rods"))
        simpleBlock(NTechBlocks.rbmkModeratedReaSimRod.get(), models().getBuilder("rbmk_moderated_reasim_rod").texture("particle", "other/rbmk/moderated_reasim_rods"))
        simpleBlock(NTechBlocks.rbmkReflector.get(), models().getBuilder("rbmk_reflector").texture("particle", "other/rbmk/reflector"))
        simpleBlock(NTechBlocks.rbmkModerator.get(), models().getBuilder("rbmk_moderator").texture("particle", "other/rbmk/moderator"))
        simpleBlock(NTechBlocks.rbmkAbsorber.get(), models().getBuilder("rbmk_absorber").texture("particle", "other/rbmk/absorber"))
        simpleBlock(NTechBlocks.rbmkBoilerColumn.get(), models().getBuilder("rbmk_boiler_column"))
        simpleBlock(NTechBlocks.rbmkBoiler.get(), models().getBuilder("rbmk_boiler").texture("particle", "other/rbmk/boiler"))
        simpleBlock(NTechBlocks.rbmkBlank.get(), models().getBuilder("rbmk_blank").texture("particle", "other/rbmk/blank"))
        simpleBlock(NTechBlocks.rbmkManualControlRod.get(), models().getBuilder("rbmk_manual_control_rod").texture("particle", "other/rbmk/manual_control"))
        simpleBlock(NTechBlocks.rbmkModeratedControlRod.get(), models().getBuilder("rbmk_moderated_control_rod").texture("particle", "other/rbmk/moderated_control"))
        simpleBlock(NTechBlocks.rbmkAutoControlRod.get(), models().getBuilder("rbmk_auto_control_rod").texture("particle", "other/rbmk/auto_control"))
        simpleBlock(NTechBlocks.rbmkSteamConnector.get())
        simpleBlock(NTechBlocks.rbmkInlet.get())
        simpleBlock(NTechBlocks.rbmkOutlet.get())
        simpleBlock(NTechBlocks.rbmkConsole.get(), models().getBuilder("rbmk_console").texture("particle", "other/rbmk/console_particles"))
        simpleBlock(NTechBlocks.rbmkDebris.get(), models().getBuilder("rbmk_debris")
            .parent(blockTransformsModel)
            .customLoader { modelLoader: BlockModelBuilder, existingFileHelper -> OBJLoaderBuilder.begin(modelLoader, existingFileHelper) }
            .modelLocation(modLoc("models/other/rbmk/debris.obj"))
            .flipV(true).detectCullableFaces(false).end()
            .texture("texture", modLoc("other/rbmk/debris"))
            .texture("particle", modLoc("other/rbmk/debris"))
        )
        simpleBlock(NTechBlocks.rbmkBurningDebris.get(), models().getBuilder("rbmk_burning_debris")
            .parent(blockTransformsModel)
            .customLoader { modelLoader: BlockModelBuilder, existingFileHelper -> OBJLoaderBuilder.begin(modelLoader, existingFileHelper) }
            .modelLocation(modLoc("models/other/rbmk/debris.obj"))
            .flipV(true).detectCullableFaces(false).end()
            .texture("texture", modLoc("other/rbmk/burning_debris"))
            .texture("particle", modLoc("other/rbmk/burning_debris"))
        )
        simpleBlock(NTechBlocks.rbmkRadioactiveDebris.get(), models().getBuilder("rbmk_radioactive_debris")
            .parent(blockTransformsModel)
            .customLoader { modelLoader: BlockModelBuilder, existingFileHelper -> OBJLoaderBuilder.begin(modelLoader, existingFileHelper) }
            .modelLocation(modLoc("models/other/rbmk/debris.obj"))
            .flipV(true).detectCullableFaces(false).end()
            .texture("texture", modLoc("other/rbmk/radioactive_debris"))
            .texture("particle", modLoc("other/rbmk/radioactive_debris"))
        )
        horizontalBlock(NTechBlocks.littleBoy.get(), models().getBuilder("little_boy").texture("particle", "other/little_boy/little_boy_particles"))
        horizontalBlock(NTechBlocks.fatMan.get(), models().getBuilder("fat_man").texture("particle", "other/fat_man/fat_man_particles"))
        simpleBlock(NTechBlocks.volcanoCore.get())
        simpleBlock(NTechBlocks.launchPad.get(), models().getBuilder("launch_pad").texture("particle", "other/launch_pad/launch_pad_particles"))
        simpleBlock(NTechBlocks.launchPadPart.get(), models().getBuilder("launch_pad").texture("particle", "other/launch_pad/launch_pad_particles"))

        simpleBlock(NTechBlocks.genericMultiBlockPart.get(), models().getBuilder("generic_multi_block_part"))
        simpleBlock(NTechBlocks.genericMultiBlockPort.get(), models().getBuilder("generic_multi_block_port"))
        simpleBlock(NTechBlocks.oilPipe.get())

        for ((source, _, _, block) in NTechFluids.getFluidsList()) {
            simpleBlock(block.get(), models().getBuilder(block.id.path).texture("particle", source.get().attributes.stillTexture))
        }

        copiedBlockItem(NTechBlocks.uraniumOre.get())
        copiedBlockItem(NTechBlocks.scorchedUraniumOre.get())
        copiedBlockItem(NTechBlocks.thoriumOre.get())
        copiedBlockItem(NTechBlocks.titaniumOre.get())
        copiedBlockItem(NTechBlocks.sulfurOre.get())
        copiedBlockItem(NTechBlocks.niterOre.get())
        copiedBlockItem(NTechBlocks.tungstenOre.get())
        copiedBlockItem(NTechBlocks.aluminiumOre.get())
        copiedBlockItem(NTechBlocks.fluoriteOre.get())
        copiedBlockItem(NTechBlocks.berylliumOre.get())
        copiedBlockItem(NTechBlocks.leadOre.get())
        copiedBlockItem(NTechBlocks.oilDeposit.get())
        copiedBlockItem(NTechBlocks.emptyOilDeposit.get())
        copiedBlockItem(NTechBlocks.oilSand.get())
        copiedBlockItem(NTechBlocks.ligniteOre.get())
        copiedBlockItem(NTechBlocks.asbestosOre.get())
        copiedBlockItem(NTechBlocks.schrabidiumOre.get())
        copiedBlockItem(NTechBlocks.australianOre.get())
        copiedBlockItem(NTechBlocks.weidite.get())
        copiedBlockItem(NTechBlocks.reiite.get())
        copiedBlockItem(NTechBlocks.brightblendeOre.get())
        copiedBlockItem(NTechBlocks.dellite.get())
        copiedBlockItem(NTechBlocks.dollarGreenMineral.get())
        copiedBlockItem(NTechBlocks.rareEarthOre.get())
        copiedBlockItem(NTechBlocks.cobaltOre.get())
        copiedBlockItem(NTechBlocks.deepslateUraniumOre.get())
        copiedBlockItem(NTechBlocks.scorchedDeepslateUraniumOre.get())
        copiedBlockItem(NTechBlocks.deepslateThoriumOre.get())
        copiedBlockItem(NTechBlocks.deepslateTitaniumOre.get())
        copiedBlockItem(NTechBlocks.deepslateSulfurOre.get())
        copiedBlockItem(NTechBlocks.deepslateNiterOre.get())
        copiedBlockItem(NTechBlocks.deepslateTungstenOre.get())
        copiedBlockItem(NTechBlocks.deepslateAluminiumOre.get())
        copiedBlockItem(NTechBlocks.deepslateFluoriteOre.get())
        copiedBlockItem(NTechBlocks.deepslateBerylliumOre.get())
        copiedBlockItem(NTechBlocks.deepslateLeadOre.get())
        copiedBlockItem(NTechBlocks.deepslateOilDeposit.get())
        copiedBlockItem(NTechBlocks.emptyDeepslateOilDeposit.get())
        copiedBlockItem(NTechBlocks.deepslateAsbestosOre.get())
        copiedBlockItem(NTechBlocks.deepslateSchrabidiumOre.get())
        copiedBlockItem(NTechBlocks.deepslateAustralianOre.get())
        copiedBlockItem(NTechBlocks.deepslateRareEarthOre.get())
        copiedBlockItem(NTechBlocks.deepslateCobaltOre.get())
        copiedBlockItem(NTechBlocks.netherUraniumOre.get())
        copiedBlockItem(NTechBlocks.scorchedNetherUraniumOre.get())
        copiedBlockItem(NTechBlocks.netherPlutoniumOre.get())
        copiedBlockItem(NTechBlocks.netherTungstenOre.get())
        copiedBlockItem(NTechBlocks.netherSulfurOre.get())
        copiedBlockItem(NTechBlocks.netherPhosphorusOre.get())
        copiedBlockItem(NTechBlocks.netherSchrabidiumOre.get())
        copiedBlockItem(NTechBlocks.meteorUraniumOre.get())
        copiedBlockItem(NTechBlocks.meteorThoriumOre.get())
        copiedBlockItem(NTechBlocks.meteorTitaniumOre.get())
        copiedBlockItem(NTechBlocks.meteorSulfurOre.get())
        copiedBlockItem(NTechBlocks.meteorCopperOre.get())
        copiedBlockItem(NTechBlocks.meteorTungstenOre.get())
        copiedBlockItem(NTechBlocks.meteorAluminiumOre.get())
        copiedBlockItem(NTechBlocks.meteorLeadOre.get())
        copiedBlockItem(NTechBlocks.meteorLithiumOre.get())
        copiedBlockItem(NTechBlocks.starmetalOre.get())
        copiedBlockItem(NTechBlocks.trixite.get())
        copiedBlockItem(NTechBlocks.basaltSulfurOre.get())
        copiedBlockItem(NTechBlocks.basaltFluoriteOre.get())
        copiedBlockItem(NTechBlocks.basaltAsbestosOre.get())
        copiedBlockItem(NTechBlocks.basaltVolcanicGemOre.get())
        copiedBlockItem(NTechBlocks.uraniumBlock.get())
        copiedBlockItem(NTechBlocks.u233Block.get())
        copiedBlockItem(NTechBlocks.u235Block.get())
        copiedBlockItem(NTechBlocks.u238Block.get())
        copiedBlockItem(NTechBlocks.uraniumFuelBlock.get())
        copiedBlockItem(NTechBlocks.neptuniumBlock.get())
        copiedBlockItem(NTechBlocks.moxFuelBlock.get())
        copiedBlockItem(NTechBlocks.plutoniumBlock.get())
        copiedBlockItem(NTechBlocks.pu238Block.get())
        copiedBlockItem(NTechBlocks.pu239Block.get())
        copiedBlockItem(NTechBlocks.pu240Block.get())
        copiedBlockItem(NTechBlocks.plutoniumFuelBlock.get())
        copiedBlockItem(NTechBlocks.thoriumBlock.get())
        copiedBlockItem(NTechBlocks.thoriumFuelBlock.get())
        copiedBlockItem(NTechBlocks.titaniumBlock.get())
        copiedBlockItem(NTechBlocks.sulfurBlock.get())
        copiedBlockItem(NTechBlocks.niterBlock.get())
        copiedBlockItem(NTechBlocks.redCopperBlock.get())
        copiedBlockItem(NTechBlocks.advancedAlloyBlock.get())
        copiedBlockItem(NTechBlocks.tungstenBlock.get())
        copiedBlockItem(NTechBlocks.aluminiumBlock.get())
        copiedBlockItem(NTechBlocks.fluoriteBlock.get())
        copiedBlockItem(NTechBlocks.berylliumBlock.get())
        copiedBlockItem(NTechBlocks.cobaltBlock.get())
        copiedBlockItem(NTechBlocks.steelBlock.get())
        copiedBlockItem(NTechBlocks.leadBlock.get())
        copiedBlockItem(NTechBlocks.lithiumBlock.get())
        copiedBlockItem(NTechBlocks.whitePhosphorusBlock.get())
        copiedBlockItem(NTechBlocks.redPhosphorusBlock.get())
        copiedBlockItem(NTechBlocks.yellowcakeBlock.get())
        copiedBlockItem(NTechBlocks.scrapBlock.get())
        copiedBlockItem(NTechBlocks.electricalScrapBlock.get())
        copiedBlockItem(NTechBlocks.insulatorRoll.get())
        copiedBlockItem(NTechBlocks.fiberglassRoll.get())
        copiedBlockItem(NTechBlocks.asbestosBlock.get())
        copiedBlockItem(NTechBlocks.trinititeBlock.get())
        copiedBlockItem(NTechBlocks.nuclearWasteBlock.get())
        copiedBlockItem(NTechBlocks.schrabidiumBlock.get())
        copiedBlockItem(NTechBlocks.soliniumBlock.get())
        copiedBlockItem(NTechBlocks.schrabidiumFuelBlock.get())
        copiedBlockItem(NTechBlocks.euphemiumBlock.get())
        copiedBlockItem(NTechBlocks.schrabidiumCluster.get())
        copiedBlockItem(NTechBlocks.euphemiumEtchedSchrabidiumCluster.get())
        copiedBlockItem(NTechBlocks.magnetizedTungstenBlock.get())
        copiedBlockItem(NTechBlocks.combineSteelBlock.get())
        copiedBlockItem(NTechBlocks.deshReinforcedBlock.get())
        copiedBlockItem(NTechBlocks.starmetalBlock.get())
        copiedBlockItem(NTechBlocks.australiumBlock.get())
        copiedBlockItem(NTechBlocks.weidaniumBlock.get())
        copiedBlockItem(NTechBlocks.reiiumBlock.get())
        copiedBlockItem(NTechBlocks.unobtainiumBlock.get())
        copiedBlockItem(NTechBlocks.daffergonBlock.get())
        copiedBlockItem(NTechBlocks.verticiumBlock.get())
        copiedBlockItem(NTechBlocks.titaniumDecoBlock.get())
        copiedBlockItem(NTechBlocks.redCopperDecoBlock.get())
        copiedBlockItem(NTechBlocks.tungstenDecoBlock.get())
        copiedBlockItem(NTechBlocks.aluminiumDecoBlock.get())
        copiedBlockItem(NTechBlocks.steelDecoBlock.get())
        copiedBlockItem(NTechBlocks.leadDecoBlock.get())
        copiedBlockItem(NTechBlocks.berylliumDecoBlock.get())
        copiedBlockItem(NTechBlocks.asbestosRoof.get())
        copiedBlockItem(NTechBlocks.hazmatBlock.get())
        copiedBlockItem(NTechBlocks.meteorite.get())
        copiedBlockItem(NTechBlocks.meteoriteCobblestone.get())
        copiedBlockItem(NTechBlocks.brokenMeteorite.get())
        copiedBlockItem(NTechBlocks.hotMeteoriteCobblestone.get())
        copiedBlockItem(NTechBlocks.meteoriteTreasure.get())
        copiedBlockItem(NTechBlocks.decoRbmkBlock.get())
        copiedBlockItem(NTechBlocks.decoRbmkSmoothBlock.get())
        copiedBlockItem(NTechBlocks.steelBeam.get())
        copiedBlockItem(NTechBlocks.steelScaffold.get())
        simpleBlockItem(NTechBlocks.steelGrate.get(), models().getExistingFile(NTechBlocks.steelGrate.id.appendToPath("4")))
        simpleItem(NTechBlocks.glowingMushroom.get())
        copiedBlockItem(NTechBlocks.deadGrass.get())
        copiedBlockItem(NTechBlocks.glowingMycelium.get())
        copiedBlockItem(NTechBlocks.trinitite.get())
        copiedBlockItem(NTechBlocks.redTrinitite.get())
        copiedBlockItem(NTechBlocks.charredLog.get())
        copiedBlockItem(NTechBlocks.charredPlanks.get())
        copiedBlockItem(NTechBlocks.slakedSellafite.get())
        copiedBlockItem(NTechBlocks.sellafite.get())
        copiedBlockItem(NTechBlocks.hotSellafite.get())
        copiedBlockItem(NTechBlocks.boilingSellafite.get())
        copiedBlockItem(NTechBlocks.blazingSellafite.get())
        copiedBlockItem(NTechBlocks.infernalSellafite.get())
        copiedBlockItem(NTechBlocks.sellafiteCorium.get())
        copiedBlockItem(NTechBlocks.corium.get())
        copiedBlockItem(NTechBlocks.corebblestone.get())
        copiedBlockItem(NTechBlocks.siren.get())
        copiedBlockItem(NTechBlocks.safe.get())
        copiedBlockItem(NTechBlocks.ironAnvil.get())
        copiedBlockItem(NTechBlocks.leadAnvil.get())
        copiedBlockItem(NTechBlocks.steelAnvil.get())
        copiedBlockItem(NTechBlocks.meteoriteAnvil.get())
        copiedBlockItem(NTechBlocks.starmetalAnvil.get())
        copiedBlockItem(NTechBlocks.ferrouraniumAnvil.get())
        copiedBlockItem(NTechBlocks.bismuthAnvil.get())
        copiedBlockItem(NTechBlocks.schrabidateAnvil.get())
        copiedBlockItem(NTechBlocks.dineutroniumAnvil.get())
        copiedBlockItem(NTechBlocks.murkyAnvil.get())
        copiedBlockItem(NTechBlocks.rbmkSteamConnector.get())
        copiedBlockItem(NTechBlocks.rbmkInlet.get())
        copiedBlockItem(NTechBlocks.rbmkOutlet.get())
        copiedBlockItem(NTechBlocks.rbmkDebris.get())
        copiedBlockItem(NTechBlocks.rbmkBurningDebris.get())
        copiedBlockItem(NTechBlocks.rbmkRadioactiveDebris.get())
        copiedBlockItem(NTechBlocks.coatedCable.get())
        copiedBlockItem(NTechBlocks.coatedUniversalFluidDuct.get())
        copiedBlockItem(NTechBlocks.blastFurnace.get())
        copiedBlockItem(NTechBlocks.combustionGenerator.get())
        copiedBlockItem(NTechBlocks.electricFurnace.get())
        copiedBlockItem(NTechBlocks.shredder.get())
        copiedBlockItem(NTechBlocks.turbine.get())
        copiedBlockItem(NTechBlocks.volcanoCore.get())
    }

    private val blockTransformsModel = models().getExistingFile(mcLoc("block/block"))
    private val generatedItem = models().getExistingFile(mcLoc("item/generated"))

    private fun simpleItem(block: Block) {
        itemModels().getBuilder(block.registryName!!.path)
            .parent(generatedItem)
            .texture("layer0", blockTexture(block))
    }

    private fun name(block: Block) = block.registryName!!.path

    private fun extend(rl: ResourceLocation, suffix: String): ResourceLocation =
        ResourceLocation(rl.namespace, rl.path + suffix)

    private fun copiedBlockItem(block: Block) {
        simpleBlockItem(block, models().getExistingFile(block.registryName))
    }

    private fun cubeAllSides(
        block: Block,
        down: ResourceLocation = extend(blockTexture(block), "_down"),
        up: ResourceLocation = extend(blockTexture(block), "_up"),
        north: ResourceLocation = extend(blockTexture(block), "_north"),
        south: ResourceLocation = extend(blockTexture(block), "_south"),
        east: ResourceLocation = extend(blockTexture(block), "_east"),
        west: ResourceLocation = extend(blockTexture(block), "_west")
    ) {
        simpleBlock(block, models().cube(name(block), down, up, north, south, east, west).texture("particle", north))
    }

    private fun litHorizontalBlock(
        block: Block,
        side: ResourceLocation = extend(blockTexture(block), "_side"),
        sideLit: ResourceLocation = side,
        front: ResourceLocation = extend(blockTexture(block), "_front"),
        frontLit: ResourceLocation = extend(blockTexture(block), "_front_on"),
        bottom: ResourceLocation = side,
        bottomLit: ResourceLocation = bottom,
        top: ResourceLocation = side,
        topLit: ResourceLocation = top
    ) {
        getVariantBuilder(block)
            .forAllStates {
                if (it.getValue(BlockStateProperties.LIT))
                    ConfiguredModel.builder()
                        .modelFile(models().orientableWithBottom(name(block) + "_on", sideLit, frontLit, bottomLit, topLit))
                        .rotationY((it.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot() + 180).toInt() % 360)
                        .build()
                else
                    ConfiguredModel.builder()
                        .modelFile(models().orientableWithBottom(name(block), side, front, bottom, top))
                        .rotationY((it.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot() + 180).toInt() % 360)
                        .build()
            }
    }

    private fun anvil(anvil: AnvilBlock) {
        getVariantBuilder(anvil).forAllStatesExcept({
            ConfiguredModel.builder()
                .modelFile(models().getBuilder(anvil.registryName!!.path)
                    .customLoader { modelLoader, existingFileHelper -> OBJLoaderBuilder.begin(modelLoader, existingFileHelper) }
                    .modelLocation(modLoc("models/block/anvil/anvil.obj"))
                    .flipV(true).detectCullableFaces(false).end()
                    .texture("anvil_texture", blockTexture(anvil))
                    .texture("particle", blockTexture(anvil))
                    .parent(blockTransformsModel))
                .rotationY(((it.getValue(HorizontalDirectionalBlock.FACING).toYRot() + 180) % 360).toInt())
                .build()
        }, BlockStateProperties.WATERLOGGED)

    }
}