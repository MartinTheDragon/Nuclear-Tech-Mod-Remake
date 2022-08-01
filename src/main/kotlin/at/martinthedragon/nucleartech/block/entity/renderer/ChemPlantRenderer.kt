package at.martinthedragon.nucleartech.block.entity.renderer

import at.martinthedragon.nucleartech.block.entity.ChemPlantBlockEntity
import at.martinthedragon.nucleartech.ntm
import at.martinthedragon.nucleartech.rendering.SpecialModels
import at.martinthedragon.nucleartech.rendering.renderModelAlpha
import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.math.Vector3f
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.block.model.ItemOverrides
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.block.HorizontalDirectionalBlock
import net.minecraftforge.client.ForgeHooksClient
import net.minecraftforge.client.model.ForgeModelBakery
import net.minecraftforge.client.model.SimpleModelState
import net.minecraftforge.client.model.StandaloneModelConfiguration
import net.minecraftforge.client.model.data.EmptyModelData
import net.minecraftforge.client.model.obj.OBJLoader
import net.minecraftforge.client.model.obj.OBJModel
import net.minecraftforge.client.model.renderable.MultipartTransforms
import net.minecraftforge.client.textures.UnitSprite
import net.minecraftforge.fluids.FluidStack
import kotlin.math.PI
import kotlin.math.sin

class ChemPlantRenderer(@Suppress("UNUSED_PARAMETER") context: BlockEntityRendererProvider.Context) : BlockEntityRenderer<ChemPlantBlockEntity> {
    private fun createModelDeclaration(part: String) = ntm("models/other/chem_plant/chem_plant_$part.obj")

    private val bodyModel = createModelDeclaration("body")
    private val spinnerModel = createModelDeclaration("spinner")
    private val pistonModel = createModelDeclaration("piston")
    private val fluidModel = createModelDeclaration("fluid")
    private val fluidCapModel = createModelDeclaration("fluid_cap")

    private val modelRenderer = Minecraft.getInstance().blockRenderer.modelRenderer

    init {
        val modelLoadFunction = { id: ResourceLocation ->
            OBJLoader.INSTANCE
                .loadModel(OBJModel.ModelSettings(id, false, false, true, true, null))
                .bakeRenderable(StandaloneModelConfiguration.create(id, mapOf("#texture" to ResourceLocation(id.namespace, "block/${id.path.removeSuffix(".obj").removePrefix("models/other/")}"))))
        }

        val bakedModelLoadFunction = { id: ResourceLocation ->
            OBJLoader.INSTANCE
                .loadModel(OBJModel.ModelSettings(id, false, false, true, true, null))
                .bake(StandaloneModelConfiguration.INSTANCE, ForgeModelBakery.instance(), UnitSprite.GETTER, SimpleModelState.IDENTITY, ItemOverrides.EMPTY, id)
        }

        SpecialModels.registerModel(bodyModel, modelLoadFunction)
        SpecialModels.registerModel(spinnerModel, modelLoadFunction)
        SpecialModels.registerModel(pistonModel, modelLoadFunction)
        SpecialModels.registerBakedModel(fluidModel, bakedModelLoadFunction)
        SpecialModels.registerBakedModel(fluidCapModel, bakedModelLoadFunction)
    }

    override fun render(chemPlant: ChemPlantBlockEntity, partials: Float, matrix: PoseStack, buffers: MultiBufferSource, light: Int, overlay: Int) {
        val body = SpecialModels.getModel(bodyModel)
        val spinner = SpecialModels.getModel(spinnerModel)
        val piston = SpecialModels.getModel(pistonModel)
        val fluidPart = SpecialModels.getBakedModel(fluidModel)
        val fluidCap = SpecialModels.getBakedModel(fluidCapModel)

        val state = chemPlant.blockState

        matrix.pushPose()
        matrix.translate(.5, .0, .5)
        matrix.mulPose(Vector3f.YN.rotationDegrees(state.getValue(HorizontalDirectionalBlock.FACING).toYRot()))
        matrix.translate(-.5, .0, -.5)
        body.render(matrix, buffers, RenderType::entitySmoothCutout, light, overlay, partials, MultipartTransforms.EMPTY)

        val spinnerRotation = chemPlant.renderTick % 360 + partials
        val fluid1 = chemPlant.inputTank1.fluid
        matrix.pushPose()
        matrix.translate(-.625, .0, .625)
        if (!fluid1.isEmpty && (chemPlant.isProgressing || chemPlant.canProgress)) matrix.mulPose(Vector3f.YP.rotationDegrees(-spinnerRotation))
        else matrix.mulPose(Vector3f.YP.rotationDegrees(-45F))
        spinner.render(matrix, buffers, RenderType::entitySmoothCutout, light, overlay, partials, MultipartTransforms.EMPTY)
        matrix.popPose()

        val fluid2 = chemPlant.inputTank2.fluid
        matrix.pushPose()
        matrix.translate(.625, .0, .625)
        if (!fluid2.isEmpty && (chemPlant.isProgressing || chemPlant.canProgress)) matrix.mulPose(Vector3f.YP.rotationDegrees(-spinnerRotation))
        else matrix.mulPose(Vector3f.YP.rotationDegrees(45F))
        spinner.render(matrix, buffers, RenderType::entitySmoothCutout, light, overlay, partials, MultipartTransforms.EMPTY)
        matrix.popPose()

        val pistonPush = sin((chemPlant.renderTick % 40 + partials) * .05 * PI) * .25 - .25
        matrix.pushPose()
        if (chemPlant.isProgressing || chemPlant.canProgress) matrix.translate(.0, pistonPush, .0)
        else matrix.translate(.0, -.25, .0)
        piston.render(matrix, buffers, RenderType::entitySmoothCutout, light, overlay, partials, MultipartTransforms.EMPTY)
        matrix.popPose()

        if (!fluid1.isEmpty) {
            matrix.pushPose()
            matrix.translate(-.625, .0, .625)
            val renderBuffer = ForgeHooksClient.getBlockMaterial(getTextureForFluid(chemPlant, fluid1)).buffer(buffers, RenderType::entityTranslucent)
            val color = fluid1.fluid.attributes.color
            val red = (color shr 16 and 0xFF) / 255F
            val green = (color shr 8 and 0xFF) / 255F
            val blue = (color and 0xFF) / 255F
            val alpha = (color shr 24 and 0xFF) / 255F
            val count = chemPlant.inputTank1.fluidAmount * 16 / chemPlant.inputTank1.capacity
            for (i in 0 until count) {
                if (i == count - 1) modelRenderer.renderModelAlpha(matrix.last(), renderBuffer, null, fluidCap, red, green, blue, alpha, light, overlay, EmptyModelData.INSTANCE)
                else modelRenderer.renderModelAlpha(matrix.last(), renderBuffer, null, fluidPart, red, green, blue, alpha, light, overlay, EmptyModelData.INSTANCE)
                matrix.translate(.0, .125, .0)
            }
            matrix.popPose()
        }

        if (!fluid2.isEmpty) {
            matrix.pushPose()
            matrix.translate(.625, .0, .625)
            val renderBuffer = ForgeHooksClient.getBlockMaterial(getTextureForFluid(chemPlant, fluid2)).buffer(buffers, RenderType::entityTranslucent)
            val color = fluid2.fluid.attributes.color
            val red = (color shr 16 and 0xFF) / 255F
            val green = (color shr 8 and 0xFF) / 255F
            val blue = (color and 0xFF) / 255F
            val alpha = (color shr 24 and 0xFF) / 255F
            val count = chemPlant.inputTank2.fluidAmount * 16 / chemPlant.inputTank2.capacity
            for (i in 0 until count) {
                if (i == count - 1) modelRenderer.renderModelAlpha(matrix.last(), renderBuffer, null, fluidCap, red, green, blue, alpha, light, overlay, EmptyModelData.INSTANCE)
                else modelRenderer.renderModelAlpha(matrix.last(), renderBuffer, null, fluidPart, red, green, blue, alpha, light, overlay, EmptyModelData.INSTANCE)
                matrix.translate(.0, .125, .0)
            }
            matrix.popPose()
        }

        matrix.popPose()
    }

    private fun getTextureForFluid(chemPlant: ChemPlantBlockEntity, fluid: FluidStack) =
        if (chemPlant.isProgressing || chemPlant.canProgress) fluid.fluid.attributes.getFlowingTexture(fluid)
        else fluid.fluid.attributes.getStillTexture(fluid)

    override fun shouldRenderOffScreen(chemPlant: ChemPlantBlockEntity) = true
    override fun getViewDistance() = 512
}
