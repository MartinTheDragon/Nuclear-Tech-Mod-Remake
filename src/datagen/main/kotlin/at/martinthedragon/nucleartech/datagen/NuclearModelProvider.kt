package at.martinthedragon.nucleartech.datagen

import at.martinthedragon.nucleartech.NuclearTech
import net.minecraft.data.DataGenerator
import net.minecraftforge.client.model.generators.BlockModelBuilder
import net.minecraftforge.client.model.generators.ModelProvider
import net.minecraftforge.client.model.generators.loaders.CompositeModelBuilder
import net.minecraftforge.client.model.generators.loaders.OBJLoaderBuilder
import net.minecraftforge.common.data.ExistingFileHelper

class NuclearModelProvider(
    dataGenerator: DataGenerator,
    existingFileHelper: ExistingFileHelper,
) : ModelProvider<BlockModelBuilder>(
    dataGenerator,
    NuclearTech.MODID,
    OTHER_FOLDER,
    ::BlockModelBuilder,
    existingFileHelper
) {
    override fun getName(): String = "Nuclear Tech Mod Generic Models"

    override fun registerModels() {
        getBuilder("assembler")
            .customLoader { modelLoader, existingFileHelper -> CompositeModelBuilder.begin(modelLoader, existingFileHelper) }
            .submodel("body", getBuilder("assembler_body")
                .customLoader { modelLoader: BlockModelBuilder, existingFileHelper -> OBJLoaderBuilder.begin(modelLoader, existingFileHelper) }
                .modelLocation(modLoc("models/other/assembler/assembler_body.obj"))
                .flipV(true).end()
                .texture("texture", modLoc("block/assembler/assembler_body"))
            ).submodel("cog", getBuilder("assembler_cog")
                .customLoader { modelLoader: BlockModelBuilder, existingFileHelper -> OBJLoaderBuilder.begin(modelLoader, existingFileHelper) }
                .modelLocation(modLoc("models/other/assembler/assembler_cog.obj"))
                .flipV(true).end()
                .texture("texture", modLoc("block/assembler/assembler_cog"))
            ).submodel("slider", getBuilder("assembler_slider")
                .customLoader { modelLoader: BlockModelBuilder, existingFileHelper -> OBJLoaderBuilder.begin(modelLoader, existingFileHelper) }
                .modelLocation(modLoc("models/other/assembler/assembler_slider.obj"))
                .flipV(true).end()
                .texture("texture", modLoc("block/assembler/assembler_slider"))
            ).submodel("arm", getBuilder("assembler_arm")
                .customLoader { modelLoader: BlockModelBuilder, existingFileHelper -> OBJLoaderBuilder.begin(modelLoader, existingFileHelper) }
                .modelLocation(modLoc("models/other/assembler/assembler_arm.obj"))
                .flipV(true).end()
                .texture("texture", modLoc("block/assembler/assembler_arm"))
            ).end()
        getBuilder("mushroom_cloud")
            .customLoader { modelLoader: BlockModelBuilder, existingFileHelper -> OBJLoaderBuilder.begin(modelLoader, existingFileHelper) }
            .modelLocation(modLoc("models/other/mushroom_cloud/mush.obj"))
            .flipV(true).detectCullableFaces(false).end()
            .texture("fireball_texture", modLoc("entity/mushroom_cloud/fireball"))
    }

    companion object {
        const val OTHER_FOLDER = "other"
    }
}
