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
                .modelLocation(modLoc("models/other/assembler/body.obj"))
                .flipV(true).end()
                .texture("texture", modLoc("other/assembler/body"))
            ).submodel("cog", getBuilder("assembler_cog")
                .customLoader { modelLoader: BlockModelBuilder, existingFileHelper -> OBJLoaderBuilder.begin(modelLoader, existingFileHelper) }
                .modelLocation(modLoc("models/other/assembler/cog.obj"))
                .flipV(true).end()
                .texture("texture", modLoc("other/assembler/cog"))
            ).submodel("slider", getBuilder("assembler_slider")
                .customLoader { modelLoader: BlockModelBuilder, existingFileHelper -> OBJLoaderBuilder.begin(modelLoader, existingFileHelper) }
                .modelLocation(modLoc("models/other/assembler/slider.obj"))
                .flipV(true).end()
                .texture("texture", modLoc("other/assembler/slider"))
            ).submodel("arm", getBuilder("assembler_arm")
                .customLoader { modelLoader: BlockModelBuilder, existingFileHelper -> OBJLoaderBuilder.begin(modelLoader, existingFileHelper) }
                .modelLocation(modLoc("models/other/assembler/arm.obj"))
                .flipV(true).end()
                .texture("texture", modLoc("other/assembler/arm"))
            ).end()
        getBuilder("chem_plant")
            .customLoader { modelLoader, existingFileHelper -> CompositeModelBuilder.begin(modelLoader, existingFileHelper) }
            .submodel("body", getBuilder("chem_plant_body")
                .customLoader { modelLoader, existingFileHelper -> OBJLoaderBuilder.begin(modelLoader, existingFileHelper) }
                .modelLocation(modLoc("models/other/chem_plant/body.obj"))
                .flipV(true).detectCullableFaces(false).end()
                .texture("texture", modLoc("other/chem_plant/body"))
            ).submodel("spinner", getBuilder("chem_plant_spinner")
                .customLoader { modeLoader, existingFileHelper -> OBJLoaderBuilder.begin(modeLoader, existingFileHelper) }
                .modelLocation(modLoc("models/other/chem_plant/spinner.obj"))
                .flipV(true).detectCullableFaces(false).end()
                .texture("texture", modLoc("other/chem_plant/spinner"))
            ).submodel("piston", getBuilder("chem_plant_piston")
                .customLoader { modeLoader, existingFileHelper -> OBJLoaderBuilder.begin(modeLoader, existingFileHelper) }
                .modelLocation(modLoc("models/other/chem_plant/piston.obj"))
                .flipV(true).detectCullableFaces(false).end()
                .texture("texture", modLoc("other/chem_plant/piston"))
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
